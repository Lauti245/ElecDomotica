package practicas_eldom.gui.visualizers;

import java.awt.Component;
import java.awt.Color;
import practicas_eldom.gui.*;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.JPanel;

import CommTransport.CommTransport;
import Utilidades.modbus.ModBus_Communications;
import eu.hansolo.steelseries.extras.LightBulb;
import practicas_eldom.config.MB_Registers;
import practicas_eldom.config.MB_Registers.TSwitchState;

import javax.swing.JLabel;

import java.awt.Font;
import eu.hansolo.steelseries.extras.Led;

public class DomoBoardGui extends JPanel implements Visualizer {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8619767299083215147L;
	
	private 		LightBulb 			lightBulb1;
	private 		LightBulb 			lightBulb2;
	private			Led ledBoton1 ;
	private			Led ledBoton2 ;
	private			Led ledBotonOpt ;
	private 		MouseAdapter 		ma_lightBulb;
	private final 	String 				category;
	private final 	boolean				isCategory = true;
	private final	CommTransport 		sn_Transport;
	private final 	String				address;
	private static int[] registers = new int[3]; 
	private static int[] buttons = new int[3]; 
	
//	public App_Connection serialConnection;
	public static int[] getRegisters(){
		return registers;
	}
	public static int[] getButtons(){
		return buttons;
	}

	public DomoBoardGui(String category, String address, CommTransport sn_Transport) {
		
		super();
		
		
		this.category 		= category;
		this.address		= address;
		this.sn_Transport 	= sn_Transport;
		
		this.setLayout(null);
		
		
		ma_lightBulb = new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {				
				ONOFF_Bulb(((LightBulb)e.getComponent()));
			}
		};
		
		
	
		
		lightBulb1 = new LightBulb();
		lightBulb1.setOn(true);
		lightBulb1.setGlowColor(Color.YELLOW);
		lightBulb1.setBounds(7, 126, 78, 78);
		lightBulb1.addMouseListener(ma_lightBulb);
		this.add(lightBulb1);
		
		lightBulb2 = new LightBulb();
		lightBulb2.setOn(true);
		lightBulb2.setGlowColor(Color.blue);
		lightBulb2.setBounds(95, 126, 78, 78);
		lightBulb2.addMouseListener(ma_lightBulb);
		this.add(lightBulb2);
		
		
		
		JLabel lblNewLabel = new JLabel("RELÉ");
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblNewLabel.setBounds(18, 92, 63, 24);
		add(lblNewLabel);
		
		JLabel lblRel = new JLabel("TRIAC");
		lblRel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblRel.setBounds(106, 92, 63, 24);
		add(lblRel);
		
		ledBoton1 = new Led();
		ledBoton1.setFont(new Font("Dialog", Font.PLAIN, 12));
		ledBoton1.setBounds(227, 73, 1, 1);
		add(ledBoton1);
		
		JLabel txtBoton1 = new JLabel("BTN_1");
		txtBoton1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		txtBoton1.setBounds(207, 92, 63, 24);
		add(txtBoton1);
		
		 ledBoton2 = new Led();
		ledBoton2.setFont(new Font("Dialog", Font.PLAIN, 12));
		ledBoton2.setBounds(307, 73, 16, 16);
		add(ledBoton2);
		
		 ledBotonOpt = new Led();
		ledBotonOpt.setFont(new Font("Dialog", Font.PLAIN, 12));
		ledBotonOpt.setBounds(386, 73, 16, 16);
		add(ledBotonOpt);
		
		JLabel lblBtn = new JLabel("BTN_2");
		lblBtn.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblBtn.setBounds(280, 92, 63, 24);
		add(lblBtn);
		
		JLabel txtBoton1_1_1 = new JLabel("BTN_OPT");
		txtBoton1_1_1.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		txtBoton1_1_1.setBounds(353, 92, 87, 24);
		add(txtBoton1_1_1);
		
		ledBoton1.enableInputMethods(MB_Registers.ModBusDiscreteInput.MB_BTN1.getReg()!=0x00);
		ledBoton2.enableInputMethods(MB_Registers.ModBusDiscreteInput.MB_BTN2.getReg()!=0x00);
		
		
		this.startContinuousAction();
		
	}
	public void startContinuousAction() {
        // Ejecuta la acción cada segundo
		
		COMSerie_MainWindow.scheduler.scheduleAtFixedRate(() -> {
           
			ModBus_Communications.readRegisters(1,MB_Registers.ModBusDiscreteInput.MB_BTN1.getReg(), MB_Registers.ModBusDiscreteInput.MB_INPUTS.getReg() , sn_Transport);
			ledBoton1.setLedOn(buttons[0]>0);
			 ledBoton2.setLedOn(buttons[1]>0);
			 ledBotonOpt.setLedOn(!(buttons[2]>0));
			ModBus_Communications.readCoils(1,MB_Registers.ModBusRegisters.MB_RELE.getReg(), MB_Registers.ModBusRegisters.MB_O_COILS.getReg() , sn_Transport);
			lightBulb1.setOn(registers[0]>0);
			lightBulb2.setOn(registers[1]>0);
			
           
        }, 0, 100, TimeUnit.MILLISECONDS); // Cambia el valor de 1 por la frecuencia deseada en segundos
    }
	
	
	
	
	
	private void ONOFF_Bulb(LightBulb lightBulb){
		TSwitchState vBulb;
		Integer vReg;
		
		lightBulb.setOn(!lightBulb.isOn());
		
		if(lightBulb.isOn()) vBulb = MB_Registers.TSwitchState.ON;
		else vBulb = MB_Registers.TSwitchState.OFF;
		
		if(lightBulb == lightBulb1) vReg = MB_Registers.ModBusRegisters.MB_RELE.getReg();
		else vReg = MB_Registers.ModBusRegisters.MB_TRIAC.getReg();
		
		ModBus_Communications.writeCoil(1, vReg, vBulb , sn_Transport);
		
		
		
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
}
