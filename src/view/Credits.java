package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Credits extends JFrame{
public static void main(String[]args) {
	Credits cr = new Credits();
	cr.setVisible(true);
}
public Credits() {
	this.setTitle("Credits");
	this.setSize(1800,1500);
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	this.getContentPane().setLayout(null);
	this.getContentPane().setBackground(Color.BLACK);
	JTextArea jt = new JTextArea();
	jt.setEditable(false);
	jt.setFont(new Font(Font.MONOSPACED,Font.PLAIN,40));
	jt.setBounds(0,0,1600,1000);
	jt.setBackground(Color.LIGHT_GRAY);
	jt.setText("Design:Mickey"+'\n'+"Code:Mickey"+'\n'+"Fun:Mickey"+'\n'+"Being sexy: Mickey"+'\n'+"Hardworking teammates:Roro and Sarah fadel ad eh"+'\n'+" we net5arag"+'\n'+"For teaching us how to play and enjoy life,"+'\n'+"THANK YOU SLIM");
	this.getContentPane().add(jt);
}
}
