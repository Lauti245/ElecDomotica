package practicas_eldom.config;

public class MB_Registers {

	public enum ModBusRegisters {

		// Discrete Output Coils
		MB_RELE(0), MB_TRIAC(1), MB_O_COILS(2);

		int reg;

		ModBusRegisters(int rg) {
			reg = rg;
		}

		public int getReg() {
			return reg;
		}
	}

	public enum TSwitchState {
		OFF, ON, TOGGLE;

		public static String ToNumberString(TSwitchState x) {
			switch (x) {
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

	}

	public enum ModBusDiscreteInput {
		MB_BTN1(0),
		MB_BTN2(1),
		MB_OPTIC(2),
		MB_INPUTS(3);
		

		int reg;
		
		ModBusDiscreteInput(int rg){
			reg = rg;
		}
		
		public int getReg(){
			return reg;
		}
		public void SetReg(int num){
			reg = num;
		}
	}

}


