package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Start extends JFrame implements ActionListener {
public Start() {
	this.setTitle("Team 81");
	this.setResizable(false);
	this.setSize(1800,1500);
	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	this.getContentPane().setLayout(null);
	this.getContentPane().setBackground(Color.BLACK);
	JButton go= new JButton("Let's do this");
	go.addActionListener(this);
	go.setBounds(600,200,200,200);
	this.getContentPane().add(go);
	
}
public static void main(String[]args) {
	Start go =new Start();
	go.setVisible(true);
}
@Override
public void actionPerformed(ActionEvent e) {
	if(e.getActionCommand().equals("Let's do this")) {
		try {
			Map m= new Map();
			m.setVisible(true);
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	
}
}
