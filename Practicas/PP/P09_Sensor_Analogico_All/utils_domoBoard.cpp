/*
 * utils_domoBoard.cpp
 *
 *  Created on: 10/03/2015
 *      Author: jctejero
 *
 *  Modified on: 16/03/2022
 */

/****************************************************************************/
/***        Include files                                                 ***/
/****************************************************************************/
#include "utils_domoBoard.h"
#include "HardwareSerial.h"

/******************************************************************/
/***                      Variables Locales                     ***/
/******************************************************************/
//Lista de sensores temporizados
QueueList<TpmbSensor> SensoresTemporizados;

/*============================================*/
/*		 		  INTERRUPTOR                 */
/*============================================*/
void Interruptor(void *Sensor) {
	if (((ptsSensor) Sensor)->valor_Df == ((ptsSensor) Sensor)->valor) {
		Serial.print(((ptsSensor) Sensor)->name);
		Serial.print(" : Interruptor --> ");
		if (((ptsSensor) Sensor)->Aux == OFF) {
			((ptsSensor) Sensor)->Aux = ON;
			DEBUGLNF("ON");
		} else {
			((ptsSensor) Sensor)->Aux = OFF;
			DEBUGLNF("OFF");
		}
	}

	DomoBoard::manageSensorActuators(&((ptsSensor) Sensor)->managerActuators,
			((ptsSensor) Sensor)->Aux);
}

/*============================================*/
/*		 		    PULSADOR                  */
/*============================================*/
void Pulsado_Soltado(void *Sensor) {
	ptsSensor sensor = reinterpret_cast<ptsSensor>(Sensor);

	Serial.print(sensor->name);
	if (sensor->valor_Df == sensor->valor) {
		DEBUGLNF(" --> Soltado");
	} else {
		DEBUGLNF(" --> Pulsado");
	}

	DomoBoard::manageSensorActuators(&(sensor->managerActuators),
			!(sensor->valor_Df == sensor->valor));
}

/*============================================*/
/*		     	   CONMUTADOR                 */
/*============================================*/
void conmutador(void *Sensor) {
	static int valor = OFF;

	ptsSensor sensor = reinterpret_cast<ptsSensor>(Sensor);

	if (sensor->valor_Df == sensor->valor) {
		DEBUGF("Conmutador --> ");
		if (valor == OFF) {
			valor = ON;
			DEBUGLNF("ON");
		} else {
			valor = OFF;
			DEBUGLNF("OFF");
		}

		//Actualiza Actuadores
		//DomoBoard::setActuator(&domoboard.RELE, valor);

		DomoBoard::manageSensorActuators(&(sensor->managerActuators), valor);
	}
}

/*============================================*/
/*		      INTERRUPTOR MODBUS              */
/*============================================*/
void mbInterruptor(void *mbSensor) {
	TpmbSensor sensor = reinterpret_cast<TpmbSensor>(mbSensor);

	if (sensor->Sensor->valor_Df == sensor->Sensor->valor) {
		mbDomoboard.manager_mbActuators(&(sensor->mbActuators), TOGGLE);
	}
}

/*============================================*/
/*		 	   CONMUTADOR MODBUS              */
/*============================================*/
void mbConmutador(void *mbSensor) {
	TpmbSensor sensor = reinterpret_cast<TpmbSensor>(mbSensor);

	if (sensor->Sensor->valor_Df == sensor->Sensor->valor) {
		//Estado del sensor ha cambiado
		mbDomoboard.manager_mbActuators(&(sensor->mbActuators), TOGGLE);
	}
}

/*========================================================*/
/*		      INTERRUPTOR TEMPORIZADO MODBUS              */
/*========================================================*/

/*
 * Este sensor será activado mediante Sensor (Variable de control) y aunque se vuelva inactivo,
 * el interruptor, permanecerá activo durante el tiempo que indique el temporizador.
 * Si durante el tiempo que el interruptor está activo, la variable de control permanece inactiva,
 * o se activa de nuevo, el tiempo que el interruptor permanece activo se
 * irá actualizando, de tal forma que el tiempo que el interruptor permanece ativo,
 * siempre se cuenta desde la última vez que el sensor se activo.
 *
 */

void mbInterruptorTemporizado(void *Sensor) {

	TpmbSensor sensor = reinterpret_cast<TpmbSensor>(Sensor);

	if (sensor->Sensor->valor_Df != sensor->Sensor->valor) {
		sensor->asyncWait->restart();

		mbDomoboard.manager_mbActuators(&(sensor->mbActuators),
				(TStateDigitalDev) ON);

	} else {
		if (!sensor->asyncWait->isWaiting()
				&& !sensor->asyncWait->isVerified()) {

			DEBUGLNF("ASYNWAIT TERMINADO");

			sensor->asyncWait->setVerified();
			mbDomoboard.manager_mbActuators(&(sensor->mbActuators),
					(TStateDigitalDev) OFF);
		}
	}

}

/*****************************************************************************************/
/***********************************  Leer Temperatura ***********************************/
/*****************************************************************************************/
//mas/menos 1 - 2ºC de exactitud para el TMP36, por lo que escribimos valores decimales
void Calc_Temperatura(void *Sensor) {
	float valTMP;
	int temp;

	TpmbSensor sensor = reinterpret_cast<TpmbSensor>(Sensor);

	valTMP = sensor->Sensor->valor * .004882812;//Conviere resultado convertidor a voltios

	valTMP = (valTMP - .5) * 100; //Convierte Temperatura de 10 mV por grado con 500 mV de Offset

	temp = (valTMP - (int) valTMP) * 100;

	if (temp < 50)
		temp = 0;
	//else if ((25 < temp)&&(temp < 75)) temp = 5;
	//else if ((75 < temp)&&(temp <= 99)){
	else {
		temp = 0;
		valTMP = (int) valTMP + 1;
	}

	*(sensor->mbRegs) = (((int) valTMP & 0xff) << 8) | (temp & 0xff);

#ifdef DEBUG_TEMP
    static float vT = 0;

    if(vT != valTMP){
    	vT = valTMP;
    	/*
    	DEBUGF("Temperatura = " );
    	Serial.print((int)valTMP,DEC);
    	Serial.print(".");
    	Serial.println(temp,DEC);
    	*/
    	DEBUGLOG("Temperatura = %d.%d\n", (int)valTMP, temp);

    //	Serial.print("Temperatura F -> ");
    //	Serial.println(Sensor->Regs_App[Sensor->MBReg_App],BIN);
    }
#endif
}

/*============================================*/
/*			  INTERRUPTOR LUMINOSIDAD         */
/*============================================*/
/*
 * Interruptor por nivel de luminosidad. funcionará con una histeresis, es decir,
 * Si el interruptor está desactivado, se activará cuando alcance el "highlevel".
 *
 * Si el interruptor está ativado, se desactivará cuando alcanze el "lowlevel".
 */
void interruptor_SRC(void *Sensor) {
	TpmbSensor sensor = reinterpret_cast<TpmbSensor>(Sensor);

	if ((int) *(sensor->ctrlLevelPtr.lInf) > (int) *(sensor->ctrlLevelPtr.lSup))
		return;

	int state = sensor->Sensor->Aux;

	switch (state) {
	case 1:
		if (sensor->Sensor->valor <= (int) *(sensor->ctrlLevelPtr.lInf)) {
			state = 2;
			//Estado del sensor ha cambiado
			mbDomoboard.manager_mbActuators(&(sensor->mbActuators),
					(TStateDigitalDev) ON);
		}

		break;

	case 2:
		if (sensor->Sensor->valor >= (int) *(sensor->ctrlLevelPtr.lSup)) {
			state = 1;
			mbDomoboard.manager_mbActuators(&(sensor->mbActuators),
					(TStateDigitalDev) OFF);
		}

		break;

	default:
		state = 2;
		mbDomoboard.set_coilRegister(MB_TRIAC, OFF);
	}

	sensor->Sensor->Aux = state;
}

/*============================================*/
/*			  TRIGGER WITH LUMINOSIDAD        */
/*============================================*/
/*
 * tRIGGER por nivel de luminosidad. Dispara un evento cuando el nivel del sensor
 * se encuentra por debajo de un determinado nivel
 */
void trigger_Level(void *Sensor) {
	TpmbSensor sensor = reinterpret_cast<TpmbSensor>(Sensor);

	uint16_t limInfe = *(sensor->ctrlLevelPtr.lInf);

	if (((uint16_t) sensor->Sensor->valor <= limInfe)) {
		if (sensor->Sensor->Aux == 0) {
			mbDomoboard.manager_mbActuators(&(sensor->mbActuators),
					(TStateDigitalDev) ON);
			sensor->Sensor->Aux = 1;
		}
	} else
		sensor->Sensor->Aux = 0;
}

void Persiana(void *Sensor) {
	TpmbSensor sensor = reinterpret_cast<TpmbSensor>(Sensor);
	//if(sensor->Sensor->pin == 3)// BTn1
	//else if (sensor->Sensor->pin == 10)//BTN 2

	if (sensor->Sensor->valor_Df == sensor->Sensor->valor) {
		//Estado del sensor ha cambiado
		if (sensor->Sensor->pin == 3) {
			mbDomoboard.BOTON1.Aux = 0;
			//Cregs[MB_PWRP] = 0;
		} else if (sensor->Sensor->pin == 10) {
			mbDomoboard.BOTON2.Aux = 0;
			//Cregs[MB_TURNP] = 0;
		}
	} else {
		if (*(mbDomoboard.PWRP.mbRegs) == OFF) {
			if (sensor->Sensor->pin == 3) {
				mbDomoboard.BOTON1.Aux = 1;
				//Cregs[MB_PWRP] = 1;
			} else if (sensor->Sensor->pin == 10) {
				mbDomoboard.BOTON2.Aux = 1;
				//Cregs[MB_TURNP] = 1;
			}

		}

	}

	mbDomoboard.upDown();
}

/*====================================================================================
 * Llamada regular usada para gestionar los temporizadores usados por la aplicación. =
 * es llamada cada LOOP_TIME (10 ms)
 *====================================================================================*/
void AccionesTemporizadas(void) {

	TpmbSensor pmbSensor;

	for (int n = 0; n < SensoresTemporizados.count(); n++) {

		pmbSensor = SensoresTemporizados.peek(n);

		pmbSensor->mbSensorEvent(pmbSensor);
	}
}

