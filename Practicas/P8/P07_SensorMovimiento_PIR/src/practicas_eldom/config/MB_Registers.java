package practicas_eldom.config;

public class MB_Registers {

	// Defininición de comando de configuración
	public static final int P1_PULSADORES 		= 0x11;
	public static final int P1_INTERRUPTOR 		= 0x12;
	public static final int P1_CONMUTADOR 		= 0x13;
	public static final int P3_CONMUTADOR 		= 0x32;
	public static final int P4_PULSADORES 		= 0x41;
	public static final int P4_INTERRUPTOR 		= 0x42;
	public static final int P4_CONMUTADOR 		= 0x43;
	public static final int PRACTICAS_MODBUS 	= 0x50;
	public static final int P5_INTERRUPTOR 		= 0x54;
	public static final int P6_INTERRUPTOR 		= 0x62;
	public static final int P07_PIR				= 0x70;

	// Selección configuración Práctica
	public static final int SELPRACT[] = { P1_PULSADORES, P1_INTERRUPTOR, P1_CONMUTADOR, P3_CONMUTADOR, P4_PULSADORES,
			P4_INTERRUPTOR, P4_CONMUTADOR, P5_INTERRUPTOR, P6_INTERRUPTOR, P07_PIR };

	public static int PRACTICE_SELECTED = 0x00;

	// Analog Output Holding Registers
	public enum MB_Analog_Output_Holding {

		// Discrete Output Coils
		MB_PRACT(0), // Registro para indicar la práctica con la que trabajamos
		MB_TMP_PIR(1),			// Registro para controlar el tiempo activo del sensor PIR (Segundos)
		MB_AREGS(2);

		int reg;

		MB_Analog_Output_Holding(int rg) {
			reg = rg;
		}

		public int getReg() {
			return reg;
		}

		public void setReg(int rg) {
			reg = rg;
		}
	}

	public enum MB_Analog_Input_Holding {

		// Discrete Input Coils
		MB_AMP1(0),
		MB_AMP2(1),
		MB_IAREGS(2);

		int reg;

		MB_Analog_Input_Holding(int rg) {
			reg = rg;
		}

		public int getReg() {
			return reg;
		}

		public void setReg(int rg) {
			reg = rg;
		}
	}
	
	public enum MB_Discrete_Output_Coils {
		
		//Discrete Output Coils
		MB_RELE(0),
		MB_TRIAC(1),
		MB_ACTPIR(2),					//Salida virtual para Activar/Desactivar PIR
		MB_O_COILS(3);
		
		int reg;
		
		MB_Discrete_Output_Coils(int rg){
			reg = rg;
		}
		
		public int getReg(){
			return reg;
		}
		
		public void setReg(int rg) {
			reg = rg;
		}
	}
	
	public enum MB_Discrete_Input_Contacts {
		
		//Discrete Output Coils
		MB_BTN1(0),
		MB_BTN2(1),
		MB_OPT(2),
		MB_PIR(3),			//Sensor de movimiento
		MB_I_REGS(4);
		
		int reg;
		
		MB_Discrete_Input_Contacts(int rg){
			reg = rg;
		}
		
		public int getReg(){
			return reg;
		}
		
		public void setReg(int rg) {
			reg = rg;
		}
		
		public int getDefaultValue() {
			switch(this) {
			case MB_BTN1:
				return 0;
			case MB_BTN2:
				return 0;
			case MB_OPT:
				return 1;
			case MB_PIR:
				return 0;
			default:
				return -1;
			}
		}
	}
	
	public enum TSwitchState { 
		OFF, 
		ON,  
		TOGGLE;
		
		public static String ToNumberString(TSwitchState x) {
	        switch(x) {
	        case OFF:
	            return "0";
	        case ON:
	        	return "1";
	        case TOGGLE:
	        	return "2";
	        default:
	        	return "0";
	        }
	    }
		
		public static int ToNumber(TSwitchState x) {
	        switch(x) {
	        case OFF:
	            return 0;
	        case ON:
	        	return 1;
	        case TOGGLE:
	        	return 2;
	        default:
	        	return 0;
	        }
	    }
		
	}
}
