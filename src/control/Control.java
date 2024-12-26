package control;

import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import coronariac.partesOrdenador.ContadorDePrograma;
import coronariac.partesOrdenador.Memoria;
import entradaSalida.EntradaSalida;

public class Control {
	
	private int MAR;//memory adress register
	private int primerOperandoAcc;
	private int segundoOperandoAcc;
	private int resultadoAcc;
	private String instruccion;//el contenido del MAR
	private Flags flag; 
	private Memoria memo;
	private ContadorDePrograma contador;
	private EntradaSalida io;
	private String textoEntradaDescodificada;
	private String ubicacionSalida;
	
	public Control(Flags flag, Memoria memo,ContadorDePrograma contador,EntradaSalida io) {
		this.primerOperandoAcc=0;
		this.segundoOperandoAcc=0;
		this.resultadoAcc=0;
		this.MAR=0;
		this.instruccion="0";
		this.flag = flag;
		this.memo = memo;
		this.contador = contador;
		this.io = io;
		this.textoEntradaDescodificada="";
		this.ubicacionSalida=null;
	}
	
	public String getUbicacionSalida() {
		return ubicacionSalida;
	}

	public void setUbicacionSalida(String ubicacionSalida) {
		this.ubicacionSalida = ubicacionSalida;
	}

	public String getTextoEntradaDescodificada() {
		return textoEntradaDescodificada;
	}

	public void setTextoEntradaDescodificada(String textoEntradaDescodificada) {
		this.textoEntradaDescodificada = textoEntradaDescodificada;
	}

	public int getPrimerOperandoAcc() {
		return primerOperandoAcc;
	}

	public void setPrimerOperandoAcc(int primerOperandoAcc) {
		this.primerOperandoAcc = primerOperandoAcc;
	}

	public int getSegundoOperandoAcc() {
		return segundoOperandoAcc;
	}

	public void setSegundoOperandoAcc(int segundoOperandoAcc) {
		this.segundoOperandoAcc = segundoOperandoAcc;
	}

	public int getResultadoAcc() {
		return resultadoAcc;
	}

	public void setResultadoAcc(int resultadoAcc) {
		this.resultadoAcc = resultadoAcc;
	}

	public String getInstruccion() {
		return instruccion;
	}

	public void setInstruccion(String instruccion) {
		this.instruccion = instruccion;
	}

	public int getMAR() {
		return MAR;
	}

	public void setMAR(int MAR) {
		this.MAR = MAR;
	}
	
	
	public void decodificar() {
		//primero se desglosa la instrucción tal cual y el dato
		
		char signo;
		String rawInstruccion=this.instruccion;
		int instruccion = Integer.valueOf(rawInstruccion.substring(0,1));
		int dato = Integer.valueOf(rawInstruccion.substring(1));
		System.out.println("		["+dato+"]["+instruccion+"]");
		switch(instruccion) {
			case 0:
				System.out.println("		OPcode Zero: INP and the data is: "+dato);
				if(io.getEntrada().isEmpty()) {
					JOptionPane.showInternalMessageDialog(null, "There is no input stablished.\nPlease, load a new data card.");
					System.out.print("\n			There is no input");
				}else {
					System.out.print("\n			Input content: "+io.getEntrada());
					System.out.print("\n			Loading input "+io.getPrimerValor());
					System.out.print("\n			saving in: "+dato);
					memo.setRam(dato, io.getPrimerValor());
					io.eliminarPrimerValor();
					System.out.print("\n			Input content: "+io.getEntrada());
					this.setTextoEntradaDescodificada("<html>"
				            + "Copy contents of input "
				            + "into cell ["+dato+"] and advance card"
				            + "</html>");
				}
				break;
				
			case 1:
				System.out.println("		OPcode one: CLA and the data is: "+dato);
				System.out.print("			Setting the content of cell "+dato+" in the accumulator");
				
				this.primerOperandoAcc=0;
				this.segundoOperandoAcc=0;
				this.resultadoAcc=0;
				flag.setFlagSigno('+');
				
				this.primerOperandoAcc= Integer.valueOf(memo.getRam(dato));
				System.out.println("\n			First number acc: "+this.primerOperandoAcc);
				this.setTextoEntradaDescodificada("<html>"
			            + "Set accumulator to contents of "
			            + "cell ["+dato+"]"
			            + "</html>");
				
				break;
				
			case 2:
				System.out.println("		OPcode three: ADD and the data is: "+dato);
				System.out.print("			Setting the content of cell "+dato+" in the accumulator");
				System.out.print("\n			Adding accumulator");
				
				this.segundoOperandoAcc= Integer.valueOf(memo.getRam(dato));
				System.out.print("\n			Second number" +this.segundoOperandoAcc);
				this.resultadoAcc = this.primerOperandoAcc+this.segundoOperandoAcc;
				System.out.print("\n			Add result:" +this.resultadoAcc);
				System.out.print("\n			Setting flag as the result sign");
				
				if(this.resultadoAcc>=0) {
					this.flag.setFlagSigno('+');
				}else{
					this.flag.setFlagSigno('-');
				}
				System.out.print("Signo ["+ this.flag.getFlagSigno()+"]");
				this.setTextoEntradaDescodificada("<html>"
			            + "Add contents of cell ["+dato+"]"
			            + "to accumulator "
			            + "</html>");
				break;
				
			case 3:
				System.out.println("		Opcode three: TAC and the data is: "+dato);
				System.out.print("\n			sign value: ["+this.flag.getFlagSigno()+"]");
				
				if(this.flag.getFlagSigno()=='+') {
					System.out.print("\n			The program will continue");
				}else {
					System.out.print("\n			The program will jump to "+dato);
					contador.setPosicion(dato);
				}
				this.setTextoEntradaDescodificada("<html>"
			            + "Move bug to cell ["+dato+"]"
			            + "</html>");
				
				break;
				
			case 4:
				System.out.println("		OPcode four: SFT and the data is: "+dato);
				int datoIzq;
				int datoDcha;
				//si el dato es menor que 10, (entre 0 y 9) se tomará en consideración sólo el desplazamiento a la derecha
				if(dato<9 && dato>=0) {
					datoDcha=dato;
					datoIzq=0;
				}else {
					
					datoDcha=dato%10;
					datoIzq = dato/10;
				}
				
				System.out.println("Splitting: "+dato+" "+datoIzq+" "+datoDcha);
				//ahora se produce el desplazamiento a la izquierda en función del número de desplazamientos dado, primero a la izquierda y luego a la derecha:
				String accString= Integer.toString(this.resultadoAcc);
				for (int i = 1; i <= datoIzq; i++) {
				    accString+='0';
				}
			
				if (accString.length() > 4) {
				    accString = accString.substring(accString.length() - 4);
				}

				System.out.println("Shifted " + datoIzq + " places to left: " + this.resultadoAcc + " is: " + accString);
				
				//ahora a la derecha
				for (int i = 1; i <= datoDcha; i++) {
				    accString='0'+accString;
				}
				
				// Asegúrate de que accString tiene al menos 4 caracteres
				if (accString.length() < 4) {
				    // Completar con ceros a la izquierda
				    while (accString.length() < 4) {
				        accString = '0' + accString;
				    }
				} else {
				    // Tomar los 4 caracteres más a la izquierda
				    accString = accString.substring(0, 4);
				}
				
				this.resultadoAcc=Integer.valueOf(accString);
				this.setTextoEntradaDescodificada("<html>"
			            + "Shift accumulator ["+datoIzq+"] places  "
			            + "left then ["+datoDcha+"] places right"
			            + "</html>");

				System.out.println("Shifted " + datoDcha + " places to right: " + this.resultadoAcc + " is: " + accString);
				
				
				break;
				
			case 5:
				System.out.println("		OPcode five: OUT and the data is: "+dato);
				System.out.print("		Loading data of cell"+dato);
				String contenido = memo.getRam(dato);
				System.out.print("		moving "+contenido+" at output");
				io.setSalida(contenido);
				
				this.setTextoEntradaDescodificada("<html>"
			            + "Cooy contents of cell ["+dato+"] to"
			            + "output card and advance card"
			            + "</html>");
				
				break;
				
			case 6:
			    System.out.println("    OPcode six: STO and the data is: " + dato);
			    System.out.print("        Storing accumulator result into cell: " + dato);

			    // Si el resultado es negativo, manejamos el formato para los números negativos
			    if (this.resultadoAcc < 0) {
			        // Formatear el número negativo manteniendo el signo
			        int valorAbs = Math.abs(this.resultadoAcc);
			        if (valorAbs < 10) {
			            memo.setRam(dato, "-00" + valorAbs);
			        } else if (valorAbs < 100) {
			            memo.setRam(dato, "-0" + valorAbs);
			        } else {
			            memo.setRam(dato, String.format("-%03d", valorAbs));
			        }
			    } else {
			        // Para números positivos, formateamos normalmente
			        if (this.resultadoAcc < 10) {
			            memo.setRam(dato, "00" + this.resultadoAcc);
			        } else if (this.resultadoAcc < 100) {
			            memo.setRam(dato, "0" + this.resultadoAcc);
			        } else if (this.resultadoAcc > 999) {
			            int digitosASubir = this.resultadoAcc % 1000;
			            memo.setRam(dato, String.format("%03d", digitosASubir));
			        } else {
			            memo.setRam(dato, Integer.toString(this.resultadoAcc));
			        }
			    }
			    this.setTextoEntradaDescodificada("<html>"
			            + "Copy accumulator to cell ["+dato+"]"
			            + "</html>");
				//memo.setRam(dato, Integer.toString(this.resultadoAcc));
				
				break;
				
			case 7:
				System.out.println("		OPcode seven: SUB and the data is: "+dato);
				System.out.print("			Setting contents of cell "+dato+" into accumulator");
				System.out.print("\n			Adding accumulator");
				
				this.segundoOperandoAcc= Integer.valueOf(memo.getRam(dato));
				System.out.print("\n			Second number is" +this.segundoOperandoAcc);
				this.resultadoAcc = this.primerOperandoAcc-this.segundoOperandoAcc;
				System.out.print("\n			Substraction result is" +this.resultadoAcc);
				System.out.print("\n			Setting flag sign");
				
				if(this.resultadoAcc>=0) {
					this.flag.setFlagSigno('+');
				}else{
					this.flag.setFlagSigno('-');
				}
				this.setTextoEntradaDescodificada("<html>"
			            + "Subtract contents of cell ["+dato+"] "
			            + "from accumulator"
			            + "</html>");
				System.out.print("Signo ["+ this.flag.getFlagSigno()+"]");
				break;

				
			case 8:
				System.out.println("		OPcode eight: JMP and the data is: "+dato);
				
				System.out.print("\n			Storing current position : ("+contador.getPosicion()+") in cell 99");
				if (contador.getPosicion()<=9) {
					memo.setRam(99, "80"+contador.getPosicion());
				}else {
					memo.setRam(99, "8"+contador.getPosicion());
				}
				this.setTextoEntradaDescodificada("<html>"
			            + "Move bug to cell ["+dato+"] and add "
			            + "original location to cell [99]"
			            + "</html>");
				System.out.print("\n			The program will jump to "+dato);
				contador.setPosicion(dato);
				
				break;
				
			case 9:
				System.out.println("		OPcode nine: HRS y and the data is: "+dato);
				this.setTextoEntradaDescodificada(" ");
				System.out.print("\n			Saving output");
				//se formatea la salida:
				
				
				String guardar="";
				if(ubicacionSalida!=null) {
					for (int i = 0; i < io.getSalida().size(); i++) { // Cambié <= por <
					    guardar += "[" + i + "] " + io.getSalida().get(i) + "\n";
					}

					
					
					try {
						FileWriter archivo = new FileWriter(ubicacionSalida+"/salida.tjd");
						archivo.write(guardar);
						archivo.close();
					} catch (IOException e) {
						e.printStackTrace();
						System.err.println("Error saving output: " + e.getMessage());
						
					}
				}else {
					JOptionPane.showInternalMessageDialog(null, "No output location stablished.");
		
					System.out.print("\n			No output location stablished");
				}
				
				System.out.print("\n			Setting HLT");
				flag.setFlagHLT(1);
				contador.setPosicion(0);
				this.setTextoEntradaDescodificada("<html>"
			            + "Advance card"
			            + "Move bug to cell [0]"
			            + " machine stopped, press reset to start again"
			            + "</html>");
				
				break;
		}
	}

}