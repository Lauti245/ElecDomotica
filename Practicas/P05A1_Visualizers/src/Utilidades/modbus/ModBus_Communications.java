package Utilidades.modbus;

import CommTransport.CommTransport;
import Utilidades.threadpool.DefaultExecutorSupplier;
import modbus.Const_Modbus;
import modbus.Modbus;
import practicas_eldom.config.MB_Registers.TSwitchState;
import practicas_eldom.gui.visualizers.DomoBoardGui;

public class ModBus_Communications {
	private static int[] regCoils=new int[3];

	
	public static void writeCoil(int SlaveAddress, int ModBusRegister, TSwitchState coilState, CommTransport sn_Transport) {
		
		String[] args = {Integer.toString(SlaveAddress), String.valueOf(Const_Modbus.WRITE_COIL), 
				Integer.toString(ModBusRegister), 
				TSwitchState.ToNumberString(coilState)};
		
		
		//Iniciamos Comunicación
		InitModbusComunication(args, sn_Transport,regCoils);	
	}
public static void readCoils(int SlaveAddress, int ModBusRegister, int numRegisters, CommTransport sn_Transport) {
		
		String[] args = {Integer.toString(SlaveAddress), String.valueOf(Const_Modbus.READ_COILS), 
				Integer.toString(ModBusRegister), 
				Integer.toString(numRegisters)};
			
		//Iniciamos Comunicación
		InitModbusComunication(args, sn_Transport,DomoBoardGui.getRegisters());
		
	}
public static void readRegisters(int SlaveAddress, int ModBusRegister, int numRegisters, CommTransport sn_Transport) {
	
	String[] args = {Integer.toString(SlaveAddress), String.valueOf(Const_Modbus.READ_INPUT_DISCRETES), 
			Integer.toString(ModBusRegister), 
			Integer.toString(numRegisters)};
	
	
	//Iniciamos Comunicación
	InitModbusComunication(args, sn_Transport,DomoBoardGui.getButtons());	
	
}
	

	public static void InitModbusComunication(String[] args, CommTransport sCon,int[]regs) {
		
		//Iniciamos Comunicación
		
		
		DefaultExecutorSupplier.getInstance().forBackgroundTasks().execute(new Runnable() {

			@Override
			public void run() {
				
				Modbus.InitComunication(args, sCon,regs);
			}
			
		});	
	}
}
