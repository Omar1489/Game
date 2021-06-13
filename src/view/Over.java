package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import controller.CommandCenter;

public class Over extends JFrame implements ActionListener {
	private CommandCenter cc;
	public static void main(String[]args) throws Exception  {
	Over ov =new Over(3);
	ov.setVisible(true);
	
}
	
public Over(int o) throws Exception {
	this.setTitle("Game Over");
	this.setSize(1800,1500);
	this.setResizable(false);
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	this.getContentPane().setLayout(null);
	this.getContentPane().setBackground(Color.BLUE);
	JTextArea jt = new JTextArea();
	jt.setEditable(false);
	jt.setBounds(300,100,900,300);
	jt.setFont(new Font(Font.MONOSPACED,Font.PLAIN,72));
	System.out.println(o);
	jt.setText("Game Over"+'\n'+"Go get a life u nerd"+'\n'+"Casualties="+o+"");
	this.getContentPane().add(jt);
	JButton credits = new JButton("Credits");
	credits.addActionListener(this);
	credits.setBounds(0,0,100,100);
	this.getContentPane().add(credits);
}
public void actionPerformed(ActionEvent e) {
	
	if(e.getActionCommand().equals("Credits")) {
		Credits cr = new Credits();
		cr.setVisible(true);
	}
	
}
}
