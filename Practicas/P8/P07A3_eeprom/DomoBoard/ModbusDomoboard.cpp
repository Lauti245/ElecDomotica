/*
 * ModbusDomoboard.cpp
 *
 *  Created on: 11 mar. 2020
 *      Author: jctejero
 *
 *  Modified: 03/04/2021
 *  Modified on: 16/03/2022
 */

#include <ModbusDomoboard.h>

/****************************************************************************/
/***        Variables Locales                                             ***/
/****************************************************************************/

/***************************************************
 * Definici�n Bancos de registros usados en ModBus *
 ***************************************************/
uint16_t	Cregs[MB_O_COILS];		//Registros para "Dicrete Output Coils"
uint16_t	Dregs[MB_I_CONTATCS];	//Registros para "Dicrete Input Contacts"
uint16_t	Aregs[MB_A_REGS];		//Registros para "Analog Output Holding Registers"
uint16_t	Iregs[MB_I_REGS];

ModbusDomoboard mbDomoboard;

ModbusDomoboard::ModbusDomoboard():DomoBoard() {
	//*****  Initialize ModBus Sensors  ****

	//Initialize BOTON1 for ModBus
	BOTON1.Sensor 		= &(DomoBoard::BOTON1);
	BOTON1.mbRegs 		= &Dregs[MB_BOTON1];
	Dregs[MB_BOTON1]	= BOTON1.Sensor->valor;

	//Initialize BOTON2 for ModBus
	BOTON2.Sensor 		= &(DomoBoard::BOTON2);
	BOTON2.mbRegs 		= &Dregs[MB_BOTON2];
	Dregs[MB_BOTON2]	= BOTON2.Sensor->valor;

	//Initialize BOTON OPTOCOPLADO for ModBus
	BTN_OPT.Sensor 		= &(DomoBoard::BTN_OPT);
	BTN_OPT.mbRegs 		= &Dregs[MB_BTNOPT];
	Dregs[MB_BTNOPT]	= BTN_OPT.Sensor->valor;

	//Inicializamos el registros ModBus Sensor PIR
	PIR_MOV.Sensor 			= &(DomoBoard::PIR_MOV);
	PIR_MOV.Sensor->Activo 	= false;					//Inicialmente, Configuramos el sensor como no activo
	Cregs[MB_ACTPIR] 		= 0x00; 					//Actualizamos el registro ModBus que monitoriza el PIR
	PIR_MOV.mbRegs 			= &Dregs[MB_PIRMOV];
	Dregs[MB_PIRMOV] 		= PIR_MOV.Sensor->valor;	//Actualizamos el registro ModBus con el estado del sensor
	Aregs[MB_TMP_PIR] 		= 0x03;						//Configuraci�n inicial 3 Segundos activo

	AMP1.Sensor 			= &(DomoBoard::AMP1);
	AMP1.mbRegs 			= &Iregs[MB_AMP1];

	AMP2.Sensor 			= &(DomoBoard::AMP2);
	AMP2.mbRegs 			= &Iregs[MB_AMP2];



	RELE.actuator = &(DomoBoard::RELE);
	RELE.mbRegs = &Cregs[MB_RELE];

	TRIAC.actuator = &(DomoBoard::TRIAC);
	TRIAC.mbRegs = &Cregs[MB_TRIAC];
}

void ModbusDomoboard::leerAllSensor(void){
	leerSensor(&BOTON1);
	leerSensor(&BOTON2);
	leerSensor(&BTN_OPT);
	leerSensor(&PIR_MOV);
	leerSensor(&AMP1);
	leerSensor(&AMP2);
}

void ModbusDomoboard::leerSensor(TpmbSensor Sensor){

	DomoBoard::leerSensor(Sensor->Sensor);

	//compueba si el valor le�do por el sensor difiere del valor almacenado en el registro correspondiente
	//del banco de registros
	if((int16_t)(*(Sensor->mbRegs)) != Sensor->Sensor->valor){
		//Estado Sensor ha cambiado
		//Se actualiza el registro correspondiente con el nuevo valor le�do en el sensor.
		*(Sensor->mbRegs) = Sensor->Sensor->valor;
		//Se in�cia el evento asociado a la actualizaci�n del banco de registro correpondiente
		if(Sensor->mbSensorEvent != NULL){
			Sensor->mbSensorEvent(Sensor);
		}
	}
}

void	ModbusDomoboard::clear_Actuators(){
	//DomoBoard::clear_Actuators();

	BOTON1.mbActuators.clear();
	BOTON1.mbSensorEvent = NULL;
	BOTON2.mbActuators.clear();
	BOTON2.mbSensorEvent = NULL;
	BTN_OPT.mbActuators.clear();
	BTN_OPT.mbSensorEvent = NULL;
	PIR_MOV.mbActuators.clear();
	PIR_MOV.mbSensorEvent = NULL;
	AMP1.mbActuators.clear();
	AMP1.mbSensorEvent = NULL;
	AMP2.mbActuators.clear();
	AMP2.mbSensorEvent = NULL;
}


void  ModbusDomoboard::setmbActuator(TmbActuator *Actuator, TStateDigitalDev val){
	bool newVal = (bool)val;

	if(val == TOGGLE){

		newVal = !(*Actuator->mbRegs);
	}

	if(*(Actuator->mbRegs) != newVal){
		*(Actuator->mbRegs) = newVal;

		setActuator(Actuator->actuator, *(Actuator->mbRegs));

	}
}

void ModbusDomoboard::setModBusSlave(ModbusSlave *mbSlave){
	mbs = mbSlave;
}

void ModbusDomoboard::manager_mbActuators(TmbActuators *Actuators, TStateDigitalDev val){

	for(int n = 0; n < Actuators->count(); n++){
		setmbActuator(Actuators->peek(n), val);
	}

}
