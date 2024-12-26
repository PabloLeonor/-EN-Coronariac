package coronariac;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import control.Control;
import control.Flags;
import coronariac.partesOrdenador.ContadorDePrograma;
import coronariac.partesOrdenador.Memoria;
import entradaSalida.EntradaSalida;

import javax.swing.JMenuBar;
import javax.swing.JToolBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JTextPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Rectangle;
import javax.swing.border.LineBorder;
import java.awt.Label;
import java.awt.Component;

public class VentanaPrincipal extends JFrame {
	
	final String version ="Version 1 English";
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel contentPane_1;
	private String ubicacionSalida;

	/**
	 * Create the frame.
	 */
	public VentanaPrincipal(Memoria memoria, Flags flag) {
		//objetos
		ContadorDePrograma contador= new ContadorDePrograma();
		EntradaSalida io = new EntradaSalida();
		Control cu = new Control(flag,memoria,contador,io);
		
		VentanaAyuda frameAyuda = new VentanaAyuda();
		VentanaMemoria frameMemo = new VentanaMemoria(memoria,flag);
		frameMemo.setVisible(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaPrincipal.class.getResource("/img/iconoPequenno.png")));
		
		this.ubicacionSalida=null;
		
		
		
		//======================================botones y funciones
		
		
		
		setTitle("Coronariac "+version);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 680, 450);
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("File");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmAbrir = new JMenuItem("Import memory");
		mntmAbrir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				io.cargarArchivo(mntmAbrir,memoria);
				System.out.println("File is gonna be loaded");
				frameMemo.actualizarVista(memoria);
			}
		});
		
		JMenuItem mntmUbicacionEntrada = new JMenuItem("Stablishing input file");
		mnNewMenu.add(mntmUbicacionEntrada);
		mntmUbicacionEntrada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				io.buscarTarjetaEntrada();
				
			}
		});
		
		JMenuItem mntmUbicacionSalida = new JMenuItem("Stabilishing output location");
		mnNewMenu.add(mntmUbicacionSalida);
		mntmUbicacionSalida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ubicacionSalida=io.ubicacionSalida();
				cu.setUbicacionSalida(ubicacionSalida);
				System.out.println("Output location: "+ubicacionSalida);
				JOptionPane.showMessageDialog(mntmUbicacionSalida, "An output location setted: \n"+ubicacionSalida);
			}
		});
		mnNewMenu.add(mntmAbrir);
		contentPane = new JPanel();
		
		JMenuItem mntmGuardar = new JMenuItem("Export memory");
		mnNewMenu.add(mntmGuardar);
		
		mntmGuardar.addActionListener(e -> {
			io.guardarMemo(mntmGuardar,memoria);
			System.out.println("File will be saved");
		});
		
		JMenuItem mntmSalir = new JMenuItem("Exit");
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		mnNewMenu.add(mntmSalir);
		
		JMenu mnVer = new JMenu("View");
		menuBar.add(mnVer);
		
		JMenuItem mntmContenidoEntrada = new JMenuItem("View input contents");
		mntmContenidoEntrada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String contenidoEntrada="";
				
				if(io.getEntrada().isEmpty()) {
					contenidoEntrada="\nThe input is empty";
				}else {
					for (int i = io.getEntrada().size() - 1; i >= 0; i--) {
						contenidoEntrada+="\n["+i+"] "+io.getEntrada().get(i);
					}
					
				}
				
				JOptionPane.showMessageDialog(mntmContenidoEntrada, "Input card contents: "+contenidoEntrada);
			}
		});
		mnVer.add(mntmContenidoEntrada);
		
		JMenuItem mntmContenidoSalida = new JMenuItem("View output contents");
		mntmContenidoSalida.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String contenidoSalida="";
				
				if(io.getSalida().isEmpty()) {
					contenidoSalida="\nThe output is empty";
				}else {
					for (int i = io.getSalida().size() - 1; i >= 0; i--) {
						contenidoSalida+="\n["+i+"] "+io.getSalida().get(i);
					}
					
				}
				String salidaTexto="";
				if(cu.getUbicacionSalida()==null) {
					salidaTexto="No output location setted";
				}else {
					salidaTexto= cu.getUbicacionSalida();
				}
				
				
				JOptionPane.showMessageDialog(mntmContenidoEntrada, "Output card setted: \n"+salidaTexto+contenidoSalida);
				
				
			}
		});
		mnVer.add(mntmContenidoSalida);
		
		JMenu mnAyuda = new JMenu("Help");
		menuBar.add(mnAyuda);
		
		JMenuItem mntmAcercaDe = new JMenuItem("About..");
		mntmAcercaDe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(mntmAcercaDe, "Thanks for using Coronariac :)\n"+version+"\nCoronariac © 2024 by Pablo Leonor is licensed under CC BY-NC-SA 4.0.\nTo view a copy of this license, visit https://creativecommons.org/licenses/by-nc-sa/4.0/");
			}
		});
		mnAyuda.add(mntmAcercaDe);
		
		JMenuItem mntmAyuda = new JMenuItem("Help");
		mntmAyuda.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//JOptionPane.showMessageDialog(mntmAyuda, "Soy una ayuda");
				frameAyuda.setVisible(true);

			}
		});
		mnAyuda.add(mntmAyuda);
		contentPane_1 = new JPanel();
		
		
		contentPane_1.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane_1);
		contentPane_1.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/img/nuevoLogo.png")));
		lblNewLabel.setBounds(157, 17, 593, 90);
		contentPane_1.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/img/parada.png")));
		lblNewLabel_1.setBounds(538, 202, 64, 64);
		contentPane_1.add(lblNewLabel_1);
		
		JLabel lblInstruccin = new JLabel("Instruction decoder");
		lblInstruccin.setBounds(434, 89, 208, 36);
		contentPane_1.add(lblInstruccin);
		
		JLabel labelInstruccionDecodificada = new JLabel("");
		labelInstruccionDecodificada.setBackground(new Color(255, 255, 255));
		labelInstruccionDecodificada.setBounds(341, 136, 315, 55);
		contentPane_1.add(labelInstruccionDecodificada);
		
		JLabel lblNewLabel_3 = new JLabel("Instruction\r register");
		lblNewLabel_3.setBounds(434, 310, 208, 14);
		contentPane_1.add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("000");
		lblNewLabel_4.setBounds(511, 335, 49, 14);
		contentPane_1.add(lblNewLabel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Acumulador");
		lblNewLabel_5.setBounds(236, 279, 84, 14);
		contentPane_1.add(lblNewLabel_5);
		
		JLabel acumuladorPrimerOperando = new JLabel("000");
		acumuladorPrimerOperando.setHorizontalAlignment(SwingConstants.RIGHT);
		acumuladorPrimerOperando.setBounds(246, 304, 49, 14);
		contentPane_1.add(acumuladorPrimerOperando);
		
		JLabel acumuladorSegundoOperando = new JLabel("000");
		acumuladorSegundoOperando.setHorizontalAlignment(SwingConstants.RIGHT);
		acumuladorSegundoOperando.setBounds(246, 323, 49, 14);
		contentPane_1.add(acumuladorSegundoOperando);
		
		JLabel acumuladorResultado = new JLabel("000");
		acumuladorResultado.setHorizontalAlignment(SwingConstants.RIGHT);
		acumuladorResultado.setBounds(246, 345, 49, 14);
		contentPane_1.add(acumuladorResultado);
		
		JLabel lblNewLabel_6 = new JLabel("Input");
		lblNewLabel_6.setBounds(33, 304, 49, 14);
		contentPane_1.add(lblNewLabel_6);
		
		JTextPane textoEntrada = new JTextPane();
		textoEntrada.setEditable(false);
		textoEntrada.setBorder(new LineBorder(new Color(0, 0, 0)));
		textoEntrada.setBounds(new Rectangle(1, 1, 1, 1));
		textoEntrada.setForeground(new Color(0, 0, 0));
		textoEntrada.setBounds(33, 329, 49, 20);
		contentPane_1.add(textoEntrada);
		
		JLabel lblNewLabel_7 = new JLabel("0=INP");
		lblNewLabel_7.setBounds(33, 55, 49, 14);
		contentPane_1.add(lblNewLabel_7);
		
		JLabel lblNewLabel_8 = new JLabel("1=CLA");
		lblNewLabel_8.setBounds(33, 70, 49, 14);
		contentPane_1.add(lblNewLabel_8);
		
		JLabel lblNewLabel_9 = new JLabel("2=ADD");
		lblNewLabel_9.setBounds(33, 85, 49, 14);
		contentPane_1.add(lblNewLabel_9);
		
		JLabel lblNewLabel_10 = new JLabel("3=TAC");
		lblNewLabel_10.setBounds(33, 100, 49, 14);
		contentPane_1.add(lblNewLabel_10);
		
		JLabel lblNewLabel_11 = new JLabel("4=SFT");
		lblNewLabel_11.setBounds(33, 115, 49, 14);
		contentPane_1.add(lblNewLabel_11);
		
		JLabel lblNewLabel_12 = new JLabel("5=OUT");
		lblNewLabel_12.setBounds(95, 55, 49, 14);
		contentPane_1.add(lblNewLabel_12);
		
		JLabel lblNewLabel_13 = new JLabel("6=STO");
		lblNewLabel_13.setBounds(95, 70, 49, 14);
		contentPane_1.add(lblNewLabel_13);
		
		JLabel lblNewLabel_14 = new JLabel("7=SUB");
		lblNewLabel_14.setBounds(95, 85, 49, 14);
		contentPane_1.add(lblNewLabel_14);
		
		JLabel lblNewLabel_15 = new JLabel("8=JMP");
		lblNewLabel_15.setBounds(95, 100, 49, 14);
		contentPane_1.add(lblNewLabel_15);
		
		JLabel lblNewLabel_16 = new JLabel("9=HRS");
		lblNewLabel_16.setBounds(95, 115, 49, 14);
		contentPane_1.add(lblNewLabel_16);
		
		JLabel posicionContador = new JLabel(Integer.toString(contador.getPosicion()));
		posicionContador.setBounds(174, 252, 49, 14);
		contentPane_1.add(posicionContador);
		
		JLabel labelFlagSigno = new JLabel("+");
		labelFlagSigno.setHorizontalAlignment(SwingConstants.CENTER);
		labelFlagSigno.setBounds(174, 345, 49, 14);
		contentPane_1.add(labelFlagSigno);
		
		JLabel lblFlechaDireccional = new JLabel("");
		lblFlechaDireccional.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/img/flechaArriba.png")));
		lblFlechaDireccional.setBounds(430, 202, 64, 64);
		contentPane_1.add(lblFlechaDireccional);
		
		JButton botonStep = new JButton("STEP");
		botonStep.setBounds(33, 218, 90, 23);
		contentPane_1.add(botonStep);
		
		botonStep.addActionListener(e -> {
		    try {
		    	System.out.println("\n---INSTRUCTION LOOP---");
		    	
		    	System.out.print("	-Saving contents to MAR: ");
		        cu.setMAR(contador.getPosicion());
		        frameMemo.escribirTextoUbicacionActual(contador.getPosicion());
		        System.out.print("Done, cell: "+ cu.getMAR()+"\n");
		        
		        lblNewLabel_4.setText(memoria.getRam(contador.getPosicion()));
		    	System.out.print("	-Advance one position on program counter: ");
		    	System.out.print(" Test["+ contador.getPosicion()+"]");
		    	contador.annadirStep(); // Incrementa el contador de programa +1
		    	posicionContador.setText(Integer.toString(contador.getPosicion())); // Actualiza el label
		    	System.out.print(" Stablish["+ contador.getPosicion()+"]\n");
		    	
		    	System.out.print("	-Getting data from cell: [");
		    	cu.setInstruccion(memoria.getRam(cu.getMAR()));
		    	System.out.print(cu.getInstruccion()+"]\n");
		    	System.out.print("	-Testing acc sign: [");
		    	System.out.print(flag.getFlagSigno()+"]\n");
		    	
		    	System.out.print("	-Docodifyng instruction: \n");
		    	cu.decodificar();
		    	
		    	System.out.print("	-Refreshing screen\n");
		    	
		    	System.out.print("  -Output: "+io.getSalida());
		    	labelInstruccionDecodificada.setText(cu.getTextoEntradaDescodificada());
		    	acumuladorPrimerOperando.setText(Integer.toString(cu.getPrimerOperandoAcc()));
		    	acumuladorSegundoOperando.setText(Integer.toString(cu.getSegundoOperandoAcc()));
		    	acumuladorResultado.setText(Integer.toString(cu.getResultadoAcc()));
		    	labelFlagSigno.setText(Character.toString(flag.getFlagSigno()));
		    	frameMemo.actualizarVista(memoria);
		    	if(flag.getFlagHLT()==0) {
		    		lblNewLabel_1.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/img/parada.png")));
		    	}else {
		    		lblNewLabel_1.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/img/paradaActiva.png")));
		    		botonStep.setEnabled(false);
		    	}
		    	String intruccionAhora= memoria.getRam(cu.getMAR());
		    	if(intruccionAhora.startsWith("9")) {
		    		lblFlechaDireccional.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/img/flechaAlLado.png")));
		    	}else if(intruccionAhora.startsWith("3") && flag.getFlagSigno()=='+'){
		    		System.out.println("Running");
		    		lblFlechaDireccional.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/img/flechaAbajo.png")));
		    	}else{
		    		lblFlechaDireccional.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/img/flechaArriba.png")));
		    	}
		    	
		    	textoEntrada.setText(io.getPrimerValor());
		    	frameMemo.actualizarTarjetaSalida(io);
		    	

		       
		        
		    } catch (NumberFormatException ex) {
		        System.err.println("Error at casting instrucction : " + ex.getMessage());
		    }
		});
		
		JButton botonReset = new JButton("RESET");
		botonReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contador.setPosicion(0);// se establece el contador de programa a cero
				posicionContador.setText(Integer.toString(contador.getPosicion()));//se actualiza el label
				botonStep.setEnabled(true);
				flag.setFlagHLT(0);
				lblNewLabel_1.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/img/parada.png")));
			}
		});
		botonReset.setBounds(133, 218, 90, 23);
		contentPane_1.add(botonReset);
		
		JLabel lblFlechaDireccional_1 = new JLabel("");
		lblFlechaDireccional_1.setAlignmentY(Component.BOTTOM_ALIGNMENT);
		lblFlechaDireccional_1.setIcon(new ImageIcon(VentanaPrincipal.class.getResource("/img/flechaLarga.png")));
		lblFlechaDireccional_1.setBounds(630, 161, 26, 219);
		contentPane_1.add(lblFlechaDireccional_1);
		
		JLabel lblInstruccin_1 = new JLabel("Program counter");
		lblInstruccin_1.setBounds(33, 241, 208, 36);
		contentPane_1.add(lblInstruccin_1);
		
		
		

		
	}
}