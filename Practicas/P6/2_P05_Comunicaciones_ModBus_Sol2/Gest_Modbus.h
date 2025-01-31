/*
 * Gest_Modbus.h
 *
 *  Created on: 16/05/2014
 *      Author: jctejero
 *
 *  Modified on: 16/03/2022
 */

#ifndef GEST_MODBUS_H_
#define GEST_MODBUS_H_

/****************************************************************************/
/***        Include files                                                 ***/
/****************************************************************************/
#include 	"ModbusSlave.h"
#include 	"domoBoard.h"
#include    "ModbusDomoboard.h"

/****************************************************************************/
/***        Macro Definitions                                             ***/
/****************************************************************************/
//Configuración de la conexión MODBUS
#define	ADDR_SLAVE		1		//Dirección Dispositivo Esclavo
#define	SERIAL_BPS		115200  //Velocidad Comunicación serie
#define	SERIAL_PARITY	'n'		//Paridad comunicación serie
#define	TX_EN_PIN		EN_485		//Pin usado para la transmisión RS485; 0 No usado


/****************************************************************************/
/***        DEFINICIÓN DE FUNCIONES    **************************************/
/****************************************************************************/
void Init_RTU_Modbus();
void RTU_ModBus();

extern ModbusSlave mbs;


#endif /* GEST_MODBUS_H_ */
