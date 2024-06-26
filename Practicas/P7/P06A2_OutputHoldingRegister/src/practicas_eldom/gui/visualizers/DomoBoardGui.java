package practicas_eldom.gui.visualizers;

import java.awt.Component;
import java.awt.Color;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;

import CommTransport.CommTransport;
import Utilidades.modbus.ModBus_Communications;
import eu.hansolo.steelseries.extras.LightBulb;
import modbus.Const_Modbus;
import modbus.ModBusEvent;
import practicas_eldom.config.MB_Registers;
import practicas_eldom.config.MB_Registers.MB_Discrete_Input_Contacts;
import practicas_eldom.config.MB_Registers.MB_Discrete_Output_Coils;
import practicas_eldom.config.MB_Registers.TSwitchState;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

import java.awt.Font;
import javax.swing.border.BevelBorder;
import javax.swing.SwingConstants;
import eu.hansolo.steelseries.extras.Led;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import eu.hansolo.steelseries.extras.WindDirection;
import eu.hansolo.steelseries.gauges.RadialCounter;
import eu.hansolo.steelseries.extras.WindDirectionBeanInfo;
import eu.hansolo.steelseries.gauges.Radial1Square;
import eu.hansolo.steelseries.gauges.Radial;
import eu.hansolo.steelseries.gauges.RadialBargraph;
import eu.hansolo.steelseries.gauges.Radial1Vertical;
import eu.hansolo.steelseries.gauges.Radial2Top;
import eu.hansolo.steelseries.gauges.RadialBeanInfo;
import eu.hansolo.steelseries.gauges.Radial2TopBeanInfo;

public class DomoBoardGui extends JPanel implements Visualizer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8619767299083215147L;
	private 		MouseAdapter 		ma_lightBulb;
	private final 	String 				category;
	private final 	boolean				isCategory = true;
	private final	CommTransport 		sn_Transport;
	private final 	int					address;
	private			LightBulb 			lightBulb1;
	private			LightBulb 			lightBulb2;
	private			Led 				ledBtn1;
	private			Led 				ledBtn2;
	private			Led 				ledBtnOpt;
	private			Led 				sensor;
	private boolean sensorPIRActivado=false;
	private long tiempoAsignado=1;
	private long lastAction=0;
	
	//Banco de registros para mantener sincronizada la comunicaci�n Modbus 
	private  		int 				Cregs[];
	private  		int 				Dregs[];
	private  		int 				Aregs[];
	
	private			 boolean     		stActualize = true;

	public DomoBoardGui(String category, int address, CommTransport sn_Transport) {
		
		super();
		
		this.category 		= category;
		this.address		= address;
		this.sn_Transport 	= sn_Transport;
		
		this.setLayout(null);
		
		//Crea Banco de registros para mantener sincronizada la comunicaci�n Modbus 
		Cregs = new int [MB_Registers.MB_Discrete_Output_Coils.MB_O_COILS.getReg()];
		Dregs = new int [MB_Registers.MB_Discrete_Input_Contacts.MB_I_REGS.getReg()];
		Aregs = new int [MB_Registers.MB_Analog_Output_Holding.MB_AREGS.getReg()];
		
		ma_lightBulb = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				ONOFF_Bulb(((LightBulb)e.getComponent()));
			}
		};
		
		JPanel panel = new JPanel();
		panel.setLayout(null);
		panel.setBorder(new BevelBorder(BevelBorder.LOWERED, new Color(0, 0, 255), new Color(0, 255, 0), Color.BLUE, Color.MAGENTA));
		panel.setBounds(10, 22, 172, 100);
		add(panel);
		
		JLabel label = new JLabel("Estado Pulsadores");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.RED);
		label.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		label.setBounds(0, 75, 166, 14);
		panel.add(label);
		
		ledBtn1 = new Led();
		ledBtn1.setBounds(11, 11, 36, 36);
		panel.add(ledBtn1);
		
		ledBtn2 = new Led();
		ledBtn2.setBounds(67, 11, 36, 36);
		panel.add(ledBtn2);
		
		ledBtnOpt = new Led();
		ledBtnOpt.setBounds(120, 11, 36, 36);
		panel.add(ledBtnOpt);
		
		sensor = new Led();
		sensor.setBounds(88, 181, 36, 36);
		add(sensor);
		
		JLabel label_1 = new JLabel("BTN 1");
		label_1.setForeground(Color.BLUE);
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_1.setBounds(11, 43, 36, 14);
		panel.add(label_1);
		
		JLabel label_2 = new JLabel("BTN 2");
		label_2.setForeground(Color.BLUE);
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_2.setBounds(67, 43, 36, 14);
		panel.add(label_2);
		
		JLabel label_3 = new JLabel("BTN_OPT");
		label_3.setForeground(Color.BLUE);
		label_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		label_3.setBounds(110, 43, 56, 14);
		panel.add(label_3);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 255), 2));
		panel_1.setBounds(199, 11, 215, 123);
		add(panel_1);
		panel_1.setLayout(null);
		
		lightBulb1 = new LightBulb();
		lightBulb1.setOn(true);
		lightBulb1.setGlowColor(Color.RED);
		lightBulb1.setBounds(10, 11, 78, 78);
		panel_1.add(lightBulb1);
		lightBulb1.addMouseListener(ma_lightBulb);
		
		JLabel lblNewLabel = new JLabel("REL\u00C9");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel.setBounds(20, 92, 76, 24);
		panel_1.add(lblNewLabel);
		
		lightBulb2 = new LightBulb();
		lightBulb2.setOn(true);
		lightBulb2.setGlowColor(Color.YELLOW);
		lightBulb2.setBounds(111, 11, 78, 78);
		panel_1.add(lightBulb2);
		lightBulb2.addMouseListener(ma_lightBulb);
		
		JLabel lblRel = new JLabel("TRIAC");
		lblRel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblRel.setBounds(119, 92, 70, 24);
		panel_1.add(lblRel);
		JCheckBox chckbxNewCheckBox = new JCheckBox("SENSOR");
		chckbxNewCheckBox.setBounds(70, 213, 93, 21);
		add(chckbxNewCheckBox);
	     chckbxNewCheckBox.addChangeListener(new ChangeListener() {
	            @Override
	            public void stateChanged(ChangeEvent e) {
	                // Acciones a realizar cuando cambia el estado del JCheckBox
	                if (chckbxNewCheckBox.isSelected()) {
	                	buildModBus(1, Const_Modbus.READ_COILS, MB_Registers.MB_Discrete_Output_Coils.MB_RELE.getReg(), MB_Registers.MB_Discrete_Output_Coils.MB_O_COILS.getReg(), Cregs);
	                	
	                } else {
	                    sensorPIRActivado = false;
	                   
	                    
	                    
	                }
	            }
	        });
		
		List<String> elementos = new ArrayList<>();
        elementos.add("1");
        elementos.add("2");
        elementos.add("5");
        SpinnerListModel spinnerModel = new SpinnerListModel(elementos);
        
        JSpinner spinner = new JSpinner(spinnerModel);
		spinner.setBounds(23, 155, 117, 27);
		add(spinner);
		
		Radial2Top radial2Top = new Radial2Top();
		radial2Top.setTitle("Amp 1");
		radial2Top.setBounds(181, 181, 212, 205);
		add(radial2Top);
		
		Radial2Top radial2Top_1 = new Radial2Top();
		radial2Top_1.setTitle("Amp 2");
		radial2Top_1.setBounds(394, 181, 210, 205);
		add(radial2Top_1);
		
		 spinnerModel.addChangeListener(new ChangeListener() {
	            @Override
	            public void stateChanged(ChangeEvent e) {
	                
	            	String valor = (String)spinner.getValue();
	            	tiempoAsignado = 1000 *(long)Integer.parseInt(valor);
	            	}
	        });
		
	}
	
	private void ONOFF_Bulb(LightBulb lightBulb){
		
		int vBulb;
		int vReg;
		
		lightBulb.setOn(!lightBulb.isOn());
		
		if(lightBulb.isOn()) vBulb = TSwitchState.ToNumber(TSwitchState.ON);
		else vBulb = TSwitchState.ToNumber(TSwitchState.OFF);
		
		if(lightBulb == lightBulb1) vReg = MB_Registers.MB_Discrete_Output_Coils.MB_RELE.getReg();
		else vReg = MB_Registers.MB_Discrete_Output_Coils.MB_TRIAC.getReg();
		
		
		buildModBus(address, Const_Modbus.WRITE_COIL, vReg, vBulb, Cregs);
	}

	@Override
	public String getCategory() {
		return category;
	}

	@Override
	public String getTitle() {
		return "Address : "+address;
	}

	@Override
	public Component getPanel() {
		return this;
	}

	@Override
	public boolean isCategory() {
		return isCategory;
	}

	@Override
	public void Actualize() {
		
		// Actualizar dispositivos modbus
		
		if(stActualize) {	
			
			//Read OutputCoils
			buildModBus(1, Const_Modbus.READ_COILS, MB_Registers.MB_Discrete_Output_Coils.MB_RELE.getReg(), MB_Registers.MB_Discrete_Output_Coils.MB_O_COILS.getReg(), Cregs);
			
			//Read Discrete Inputs
			buildModBus(1, Const_Modbus.READ_INPUT_DISCRETES, MB_Registers.MB_Discrete_Input_Contacts.MB_BTN1.getReg(),MB_Registers.MB_Discrete_Input_Contacts.MB_I_REGS.getReg(), Dregs);
			
		}
	}
	
	public void buildModBus(int address, int func, int iReg, int nReg, int[] bReg) {
		String[] args = {String.valueOf(address), String.valueOf(func), 
				String.valueOf(iReg), String.valueOf(nReg)};

		ModBus_Communications.InitModbusComunication(args, sn_Transport, this::UpdateElements, bReg);
		
	}
	
	public void UpdateElements(final ModBusEvent e){

		int addr = Integer.parseInt(e.get_Args()[2]);
		int nReg = Integer.parseInt(e.get_Args()[3]);
					
		switch(Integer.parseInt(e.get_Args()[1])){
		case Const_Modbus.READ_COILS:
			
			for(int i = addr;i<(addr+nReg); i++){
				switch(MB_Discrete_Output_Coils.values()[i]){
				case MB_RELE:							
					lightBulb1.setOn((e.getRegs()[i] == 1));
					break;
							
				case MB_TRIAC:							
					lightBulb2.setOn((e.getRegs()[i] == 1));
					break;
				default:
					break;
				
				}
			}
			break;
						
		case Const_Modbus.READ_INPUT_DISCRETES:	
			
			MB_Discrete_Input_Contacts mbDIC; //= MB_Discrete_Input_Contacts.values()[addr];
			
			for(int i = addr;i<(addr+nReg); i++){
				mbDIC = MB_Discrete_Input_Contacts.values()[i];
				switch(mbDIC){
				case MB_BTN1:
					ledBtn1.setLedOn((e.getRegs()[i] != mbDIC.getDefaultValue()));
					break;
								
				case MB_BTN2:
					ledBtn2.setLedOn((e.getRegs()[i] != mbDIC.getDefaultValue()));
					break;
							
				case MB_OPT:
					ledBtnOpt.setLedOn((e.getRegs()[i] != mbDIC.getDefaultValue()));
					break;
				default:
					break;
				}
			}
			break;
		}				
	}
	
	@Override
	public void setActualize(boolean st) {
		stActualize = st;		
	}

	@Override
	public boolean getActualize() {
		
		return stActualize;
	}

	@Override
	public void vlog(String message) {
		// TODO Auto-generated method stub
		
	}
}
