package coronariac;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JScrollBar;
import java.awt.TextArea;
import java.awt.Toolkit;

public class VentanaAyuda extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	public VentanaAyuda() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(VentanaAyuda.class.getResource("/img/iconoPequenno.png")));
		setTitle("Manual de usuario");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		TextArea textArea = new TextArea();
		textArea.setText("-------CORONARIAC-------\r\n\r\n\n\nYou can download an expanded version of this \r\nmanual from the GitHub repository: \r\nhttps://github.com/PabloLeonor/Coronariac\r\n\r\n\n\n====Index====\n\n\r\nWhat is Coronariac?\r\n\nCoronariac's Windows\r\n\nHow to use Coronariac\r\n\r\n\n====What is Coronariac?====\r\n\nCoronariac is an emulator of the Cardiac\r\n computer from Bell Labs, which was \r\ndeveloped in the 1960s to teach students \r\nof that era what a computer is and how it works.\n\n\r\n\r\n====Coronariac Windows====\n\r\nWhen you start your copy of Coronariac, \r\nyou will see two windows.\nOne is the main \r\nwindow, where you will find the menu and\r\n a description of how the emulator works.\n\nThe other\r\n window displays a breakdown of the emulator's\r\n memory, as well as a text output from the computer.\r\n\r\n\n\n====How to use Coronariac====\n\r\nTo use Coronariac, the first thing you\r\n need to do is locate a data card file and\r\n set a location on your computer.\nYou can\r\n do this by navigating to:\r\n\nFile/Stablishing input input file\r\nand\r\nFile/Stablishing Output Location\r\n\r\n\n\nNext, write your program in the \r\nmemory window using the 10 \r\nopcodes of Cardiac that Coronariac\r\n can read:\r\n\n\n0 = INP Input\n\r\n1 = CLA Clear accumulator and load it\n\r\n2 = ADD Add\n\r\n3 = TAC Check the accumulator’s sign and \r\nperform a conditional jump if it is negative\n\r\n4 = SFT Shift\n\r\n5 = OUT Output\r\n\n6 = STO Store the accumulator's result\n\r\n7 = SUB Subtract\r\n\n8 = JMP Unconditional jump\r\n\n9 = HRS Halt the machine and reset\r\n\r\n\n\nYou can load the following program, \r\nwhich is included in the CARDIAC \r\nmanual, to simply add two numbers \r\nentered as input:\r\n\n\n\n[0] 024  \r\n\n[1] 025  \n\r\n[2] 124  \n\r\n[3] 225  \r\n\n[4] 626  \r\n\n[5] 526  \r\n\n[6] 900  \r\n\nThis program will add two input \r\nvalues and display the result as \r\noutput, which you can view by \r\nnavigating to:\n\r\n\r\nView/View Output Contents");
		textArea.setBounds(9, 10, 416, 243);
		contentPane.add(textArea);
	}
}
