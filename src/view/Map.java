package view;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import controller.CommandCenter;
import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.disasters.Disaster;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import model.units.Ambulance;
import model.units.DiseaseControlUnit;
import model.units.Evacuator;
import model.units.FireTruck;
import model.units.GasControlUnit;
import model.units.Unit;
import model.units.UnitState;
import simulation.Address;
import simulation.Rescuable;

public class Map extends JFrame implements ActionListener{
	private JPanel[][] ja;
	private int currentcycle=0;
	private Over ov;
	private JTextArea txt;
	private JTextArea txt1;
	private JTextArea txt2;
	private JTextArea txt3;
	private CommandCenter cc;
	private JPanel jp21;
	private JPanel jp22;
	private JPanel jp23;
	private JTextArea jp3;
	private JButton ambulance;
	private JButton gascontrolunit;
	private JButton diseasecontrol;
	private JButton evacuator;
	private JButton firetruck;
	private ArrayList<String> executed;
	private String active;
	private Container cp;
	private Unit unit;
	private Rescuable target;
	private ResidentialBuilding building;
	private Citizen citizen;
	private int casualties=0;
	private ArrayList<JButton> treat;
	
	
	public int getCasualties() {
		return casualties;
	}

	public Map() throws Exception {
		super();
		casualties=casualties;
		this.setResizable(false);
		executed= new ArrayList<String>();
		cc = new CommandCenter();
		this.setSize(1800, 1500);
		this.setTitle("Team 81");
		 cp = this.getContentPane();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		cp.setBackground(Color.DARK_GRAY);
		cp.setLayout(null);
		JPanel jp= new JPanel();
		jp.setLayout(new GridLayout(10,10));
		jp.setBounds(420,105 ,550,600);
		cp.add(jp);
		ja= new JPanel[10][10];
		for(int i=0; i<10; i++) {
			for(int j=0; j<10;j++) {
			ja[i][j]= new JPanel();
			JButton jb = new JButton();
			jb.setPreferredSize(new Dimension(50,100));
			jb.setBackground(Color.BLUE);
			ja[i][j].add(jb);
			jp.add(ja[i][j]);
			
		}
		}
		JPanel jp1 = new JPanel();
		jp1.setLayout(null);
		jp1.setBackground(Color.DARK_GRAY);
		jp1.setBounds(0,0,500,1000);
		cp.add(jp1);
		txt= new JTextArea();
		txt.setBounds(0, 0, 250,50);
		txt.setBackground(Color.GREEN);
		txt.setEditable(false);
		String s= "RIP:"+'\n'+"CurrentCycle:"+currentcycle;
		txt.setText(s);
		txt.setFont(new Font(Font.MONOSPACED,Font.PLAIN,13));
		jp1.add(txt);
		
		txt1 = new JTextArea();
		txt1.setBounds(0,50,250,100);
		txt1.setEditable(false);
		String s1 = "Executed Disasters:"+'\n'+"Active Disasters:";
		txt1.setText(s1);
		txt1.setFont(new Font(Font.MONOSPACED,Font.PLAIN,13));
		JScrollPane sp = new JScrollPane(txt1);
		sp.setBounds(0,50,250,100);
		sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jp1.add(sp);
		
		
		JButton nextcycle = new JButton("Next Cycle");
		nextcycle.addActionListener(this);
		nextcycle.setBounds(800, 0, 100, 100);
		cp.add(nextcycle);
		
		txt2= new JTextArea();
		String s2 = "Dead 7abayebna: ";
		txt2.setEditable(false);
		txt2.setBounds(0, 170, 380, 200);
		txt2.setText(s2);
		txt2.setFont(new Font(Font.MONOSPACED,Font.PLAIN,12));
		txt2.setBackground(Color.MAGENTA);
		JScrollPane sp2 = new JScrollPane(txt2);
		sp2.setBounds(0,170,380,200);
		sp2.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		jp1.add(sp2);
		
		
		txt3 = new JTextArea();
		txt3.setEditable(false);
		txt3.setFont(new Font(Font.MONOSPACED,Font.PLAIN,10));
		txt3.setBounds(0,400,400,300);
		jp1.add(txt3);
	
		
		jp21 = new JPanel();
		JLabel jl = new JLabel("Available Units");
		jp21.setLayout(new FlowLayout());
		jp21.add(jl);
		jp21.setBackground(Color.CYAN);
		jp21.setBounds(1050,0,300,50);
		cp.add(jp21);
		
		ambulance = new JButton("amb");
		ambulance.addActionListener(this);
		ambulance.setBounds(1050,50,100,90);
		ambulance.setIcon(new ImageIcon("C:\\Users\\Win 8\\Desktop\\ambulance.jpg"));
		cp.add(ambulance);
		
		gascontrolunit = new JButton("gcu");
		gascontrolunit.addActionListener(this);
		gascontrolunit.setIcon(new ImageIcon("C:\\Users\\Win 8\\Desktop\\gas control.jpg"));
		gascontrolunit.setBounds(1150, 50, 100,90);
		cp.add(gascontrolunit);
		
		diseasecontrol = new JButton("dcu");
		diseasecontrol.addActionListener(this);
		diseasecontrol.setIcon(new ImageIcon("C:\\Users\\Win 8\\Desktop\\disease control2.jpg"));
		diseasecontrol.setBounds(1250,50,100,90);
		cp.add(diseasecontrol);
		
		evacuator = new JButton("evc");
		evacuator.addActionListener(this);
		evacuator.setIcon(new ImageIcon("C:\\Users\\Win 8\\Desktop\\evacuator.png"));
		evacuator.setBounds(1050,150,100,90);
		cp.add(evacuator);
		
		firetruck = new JButton("ftk");
		firetruck.addActionListener(this);
		firetruck.setIcon(new ImageIcon("C:\\Users\\Win 8\\Desktop\\firetruck.png"));
		firetruck.setBounds(1150,150,100,90);
		cp.add(firetruck);
		
		
		
		JPanel jp22 = new JPanel();
		jp22.setLayout(new FlowLayout());
		jp22.setBounds(1050,250,300,50);
		jp22.setBackground(Color.CYAN);
		jp22.add(new JLabel("Responding Units"));
		
		cp.add(jp22);
		
		JPanel jp23 = new JPanel();
		jp23.setLayout(new FlowLayout());
		jp23.setBounds(1050,500,300,50);
		jp23.setBackground(Color.CYAN);
		jp23.add(new JLabel("Treating Units"));

		cp.add(jp23);
		
		jp3 = new JTextArea();
		jp3.setBounds(500,0,200,100);
		jp3.setBackground(Color.ORANGE);
		cp.add(jp3);
		
	
		
		

		
}
	
	public JPanel[][] getJa() {
		return ja;
	}

	public static void main(String[]args) throws Exception {
	Map m = new Map();
	m.setVisible(true);
}
	public void showCitizen() {
		//ArrayList<Citizen> c= cc.getVisibleCitizens();
		for(int i =0 ;i<cc.getVisibleCitizens().size();i++) {
			Citizen cw= cc.getVisibleCitizens().get(i);
//			Disaster d= cw.getDisaster();
//			executed+=d.toString()+'\n';
//			txt1.setText("Active Disasters:"+'\n'+"Executed Disasters:"+executed);
			Address a = cw.getLocation();
			int x= a.getX();
			int y= a.getY();
			if(cw.getState().equals(CitizenState.DECEASED)) {
			JButton jc = new JButton("dc");
			jc.addActionListener(this);
			jc.setIcon(new ImageIcon("C:\\Users\\Win 8\\Desktop\\dead.jpg"));
			ja[x][y].removeAll();
			ja[x][y].revalidate();
			ja[x][y].repaint();
			ja[x][y].add(jc);
			}
			else {
				JButton jc = new JButton("c");
				jc.addActionListener(this);
				jc.setIcon(new ImageIcon("C:\\Users\\Win 8\\Desktop\\images.jpg"));
				ja[x][y].removeAll();
				ja[x][y].revalidate();
				ja[x][y].repaint();
				ja[x][y].add(jc);
			}
			
		}
	}
	public void showBuilding() {
		//ArrayList<ResidentialBuilding> b= cc.getVisibleBuildings();
		for(int i =0 ;i<cc.getVisibleBuildings().size();i++) {
			ResidentialBuilding bw= cc.getVisibleBuildings().get(i);
//			Disaster d= bw.getDisaster();
//			executed=d.toString()+'\n';
//			txt1.setText("Active Disasters:"+'\n'+"Executed Disasters:"+executed);
			Address a = bw.getLocation();
			int z= a.getX();
			int y= a.getY();
			if(bw.getStructuralIntegrity()==0) {
			JButton jb = new JButton("db");
			jb.addActionListener(this);
			jb.setIcon(new ImageIcon("C:\\Users\\Win 8\\Desktop\\db.png"));
			ja[z][y].removeAll();
			ja[z][y].revalidate();
			ja[z][y].repaint();
			ja[z][y].add(jb);
			}
			else {
				JButton jb= new JButton("b");
				jb.addActionListener(this);
				jb.setIcon(new ImageIcon("C:\\Users\\Win 8\\Desktop\\building.png"));
				ja[z][y].removeAll();
				ja[z][y].revalidate();
				ja[z][y].repaint();
				ja[z][y].add(jb);
			}
			
			
			
		}
	}


	public void actionPerformed(ActionEvent e) {
		
				
	 if(e.getActionCommand().equals("b")) {
			for(int i=0;i<10;i++) {
				for(int j=0;j<10;j++) {
					JButton jb= (JButton)e.getSource();
					JPanel u = ja[i][j];
					
					if(u.getComponent(0).equals(jb)) {
						
						ResidentialBuilding rb= cc.getEngine().getBuildingByLocation(cc.getEngine().getWorld()[i][j]);
						txt3.setText(rb.toString());
						building = rb;
						String q="";
						for(int k=0;k<rb.getOccupants().size();k++) {
							 q +="  "+ rb.getOccupants().get(k).getName();
						}
						jp3.setText(q);
					}
				}
			}
			
			
		}
		 if(e.getActionCommand().equals("c")) {
			for(int i=0;i<10;i++) {
				for(int j=0;j<10;j++) {
					JButton jb= (JButton)e.getSource();
					JPanel u = ja[i][j];
					
					if(u.getComponent(0).equals(jb)) {
						
						Citizen c= cc.getEngine().getCitizenByLocation(cc.getEngine().getWorld()[i][j]);
						txt3.setText(c.toString());
						citizen= c;
					}
				}
			}
			
		}
		 if(e.getActionCommand().equals("Next Cycle")) {
			if(cc.getEngine().checkGameOver()) {
//				JOptionPane.showMessageDialog(null, "GameOver" + "\n" + "number of dead :" + cc.getEngine().calculateCasualties());
				casualties=cc.getEngine().calculateCasualties();
				
				try {
					ov = new Over(casualties);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				ov.setVisible(true);
				
				
			}
			else {
			currentcycle++;
			casualties= cc.getEngine().calculateCasualties();
			String s= "RIP:"+casualties+'\n'+"CurrentCycle:"+currentcycle;
			txt.setText(s);
			cc.getEngine().nextCycle();
			showBuilding();
			showCitizen();
			Disaster d;
		
			for(int i=0;i<cc.getEngine().getExecutedDisasters().size();i++) {
				
				d = cc.getEngine().getExecutedDisasters().get(i);
				
				executed.add(d.toString()+'\n');
				
			}
			active=" ";
			for(int i=0;i<cc.getVisibleBuildings().size();i++) {
				ResidentialBuilding rb = (ResidentialBuilding)cc.getVisibleBuildings().get(i);
				d= rb.getDisaster();
				
				active+=d.toString()+'\n';
			}
			for(int i=0;i<cc.getVisibleCitizens().size();i++) {
				Citizen cv = (Citizen)cc.getVisibleCitizens().get(i);
				d= cv.getDisaster();
				active+=d.toString()+'\n';
			}
			txt1.setText("Executed Disasters:"+executed+'\n'+"Active Disasters:"+active);
			txt1.validate();
			
			

			for(int i=0;i<cc.getEmergencyUnits().size();i++) {
				
				if(cc.getEmergencyUnits().get(i).getTarget() instanceof Citizen){
					Citizen cf = (Citizen)cc.getEmergencyUnits().get(i).getTarget();
					if( cc.getEmergencyUnits().get(i).getState().equals(UnitState.RESPONDING)&&cf.getState().equals(CitizenState.DECEASED)) {
						cc.getEmergencyUnits().get(i).setState(UnitState.IDLE);
						cc.getEmergencyUnits().get(i).setTarget(null);
					
					}
				}
				if(cc.getEmergencyUnits().get(i).getTarget() instanceof ResidentialBuilding) {
					ResidentialBuilding rf= (ResidentialBuilding)cc.getEmergencyUnits().get(i).getTarget();
					if( cc.getEmergencyUnits().get(i).getState().equals(UnitState.RESPONDING)&&rf.getStructuralIntegrity()==0) {
						cc.getEmergencyUnits().get(i).setTarget(null);
						cc.getEmergencyUnits().get(i).setState(UnitState.IDLE);
					}
				}
				if(cc.getEmergencyUnits().get(i).getTarget() instanceof Citizen){
					Citizen cf = (Citizen)cc.getEmergencyUnits().get(i).getTarget();
					if(cc.getEmergencyUnits().get(i).getState().equals(UnitState.TREATING)&&cf.getState().equals(CitizenState.DECEASED)) {
					cc.getEmergencyUnits().get(i).setTarget(null);
						cc.getEmergencyUnits().get(i).setState(UnitState.IDLE);
					}
				}
				if(cc.getEmergencyUnits().get(i).getTarget() instanceof ResidentialBuilding) {
					ResidentialBuilding rf= (ResidentialBuilding)cc.getEmergencyUnits().get(i).getTarget();
					if(cc.getEmergencyUnits().get(i).getState().equals(UnitState.TREATING)&&rf.getStructuralIntegrity()==0) {
						cc.getEmergencyUnits().get(i).setTarget(null);
						cc.getEmergencyUnits().get(i).setState(UnitState.IDLE);
					}
					
				}
				
				if(cc.getEmergencyUnits().get(i).getTarget()!=null&&cc.getEmergencyUnits().get(i).getDistanceToTarget()==0) {
					
					
				
					if(cc.getEmergencyUnits().get(i) instanceof Ambulance) {
						try {
							cc.getEmergencyUnits().get(i).treat();
						} catch (CannotTreatException e1) {
							
							e1.printStackTrace();
						} catch (IncompatibleTargetException e1) {
							
							e1.printStackTrace();
						}
						cc.getEmergencyUnits().get(i).setLocation(cc.getEmergencyUnits().get(i).getTarget().getLocation());
						cp.remove(ambulance);
						cp.revalidate();
						cp.repaint();
						ambulance.setBounds(1050, 550, 100, 80);
						ambulance.setEnabled(false);
						cc.getEmergencyUnits().get(i).setState(UnitState.TREATING);
						txt3.setText(cc.getEmergencyUnits().get(i).toString());
						//treat.add(ambulance);
						cp.add(ambulance);
					}
					if(cc.getEmergencyUnits().get(i) instanceof GasControlUnit) {
						try {
							cc.getEmergencyUnits().get(i).treat();
						} catch (CannotTreatException e1) {
							
							e1.printStackTrace();
						} catch (IncompatibleTargetException e1) {
							
							e1.printStackTrace();
						}
						cc.getEmergencyUnits().get(i).setLocation(cc.getEmergencyUnits().get(i).getTarget().getLocation());
						cp.remove(gascontrolunit);
						cp.revalidate();
						cp.repaint();
						gascontrolunit.setBounds(1150, 550, 100, 80);
						gascontrolunit.setEnabled(false);
						cc.getEmergencyUnits().get(i).setState(UnitState.TREATING);
						txt3.setText(cc.getEmergencyUnits().get(i).toString());
						//treat.add(gascontrolunit);
						cp.add(gascontrolunit);
					}
					if(cc.getEmergencyUnits().get(i) instanceof DiseaseControlUnit) {
						try {
							cc.getEmergencyUnits().get(i).treat();
						} catch (CannotTreatException e1) {
							
							e1.printStackTrace();
						} catch (IncompatibleTargetException e1) {
							
							e1.printStackTrace();
						}
						cc.getEmergencyUnits().get(i).setLocation(cc.getEmergencyUnits().get(i).getTarget().getLocation());
						cp.remove(diseasecontrol);
						cp.revalidate();
						cp.repaint();
						diseasecontrol.setBounds(1250, 550, 100, 80);
						diseasecontrol.setEnabled(false);
						cc.getEmergencyUnits().get(i).setState(UnitState.TREATING);
						txt3.setText(cc.getEmergencyUnits().get(i).toString());
						//treat.add(diseasecontrol);
						cp.add(diseasecontrol);
					}
					if(cc.getEmergencyUnits().get(i) instanceof Evacuator) {
						try {
							cc.getEmergencyUnits().get(i).treat();
						} catch (CannotTreatException e1) {
							
							e1.printStackTrace();
						} catch (IncompatibleTargetException e1) {
							
							e1.printStackTrace();
						}
						cc.getEmergencyUnits().get(i).setLocation(cc.getEmergencyUnits().get(i).getTarget().getLocation());
						cp.remove(evacuator);
						cp.revalidate();
						cp.repaint();
						evacuator.setBounds(1050, 650, 100, 80);
						evacuator.setEnabled(false);
						cc.getEmergencyUnits().get(i).setState(UnitState.TREATING);
						txt3.setText(cc.getEmergencyUnits().get(i).toString());
						//treat.add(evacuator);
						cp.add(evacuator);
					}
					
					
					if(cc.getEmergencyUnits().get(i) instanceof FireTruck) {
						try {
							cc.getEmergencyUnits().get(i).treat();
						} catch (CannotTreatException e1) {
							
							e1.printStackTrace();
						} catch (IncompatibleTargetException e1) {
							
							e1.printStackTrace();
						}
						cc.getEmergencyUnits().get(i).setLocation(cc.getEmergencyUnits().get(i).getTarget().getLocation());
						cp.remove(firetruck);
						cp.revalidate();
						cp.repaint();
						firetruck.setBounds(1150, 650, 100, 80);
						firetruck.setEnabled(false);
						cc.getEmergencyUnits().get(i).setState(UnitState.TREATING);
						txt3.setText(cc.getEmergencyUnits().get(i).toString());
						
						//treat.add(firetruck);
						cp.add(firetruck);
					}
				}
				
				
				if (cc.getEmergencyUnits().get(i).getState().equals(UnitState.IDLE)) {
					
					if(cc.getEmergencyUnits().get(i) instanceof Ambulance) {
						cc.getEmergencyUnits().get(i).jobsDone();
						cp.remove(ambulance);
						ambulance.setBounds(1050, 50,100, 90);
						ambulance.setEnabled(true);
						cp.add(ambulance);
					}
					if(cc.getEmergencyUnits().get(i) instanceof GasControlUnit) {
						cc.getEmergencyUnits().get(i).jobsDone();
						cp.remove(gascontrolunit);
						gascontrolunit.setBounds(1150, 50,100, 90);
						gascontrolunit.setEnabled(true);
						cp.add(gascontrolunit);
					}
					if(cc.getEmergencyUnits().get(i) instanceof DiseaseControlUnit) {
						cc.getEmergencyUnits().get(i).jobsDone();
						cp.remove(diseasecontrol);
						diseasecontrol.setBounds(1250, 50,100, 90);
						diseasecontrol.setEnabled(true);
						cp.add(diseasecontrol);
					}
					if(cc.getEmergencyUnits().get(i) instanceof Evacuator) {
						cc.getEmergencyUnits().get(i).jobsDone();
						cp.remove(evacuator);
						evacuator.setBounds(1050, 150,100, 90);
						evacuator.setEnabled(true);
						cp.add(evacuator);
					}
					
					if(cc.getEmergencyUnits().get(i) instanceof FireTruck) {
						cc.getEmergencyUnits().get(i).jobsDone();
						cp.remove(firetruck);
						firetruck.setBounds(1150, 150,100, 90);
						firetruck.setEnabled(true);
						cp.add(firetruck);
					}
				}
			}
			
			}	
			cp.revalidate();
			cp.repaint();
	}
		 if(e.getActionCommand().equals("ftk")){
			for(int i=0;i<cc.getEmergencyUnits().size();i++) {
				if(cc.getEmergencyUnits().get(i) instanceof FireTruck) {
					txt3.setText(cc.getEmergencyUnits().get(i).toString());
					if(building!=null) {
					try {
						cc.getEmergencyUnits().get(i).respond(building);
						cc.getEmergencyUnits().get(i).setState(UnitState.RESPONDING);
						
						cp.remove(firetruck);
						cp.revalidate();
						cp.repaint();
						firetruck.setBounds(1050, 300, 100, 80);
						firetruck.setEnabled(true);
						cp.add(firetruck);
						
					}
					catch(CannotTreatException |IncompatibleTargetException q){
						txt3.setText(q.getMessage());
					}
					}
				}
				
			}
			
		}
		 if(e.getActionCommand().equals("amb")) {
			for(int i=0;i<cc.getEmergencyUnits().size();i++) {
				if(cc.getEmergencyUnits().get(i) instanceof Ambulance) {
					Ambulance am = (Ambulance)cc.getEmergencyUnits().get(i);
					txt3.setText(am.toString());
					if(citizen!=null) {
					
					try {
						am.respond(citizen);
						am.setState(UnitState.RESPONDING);
						cp.remove(ambulance);
						cp.revalidate();
						cp.repaint();
						ambulance.setBounds(1150, 300, 100, 80);
						ambulance.setEnabled(true);
						cp.add(ambulance);
					}
					catch(CannotTreatException |IncompatibleTargetException q) {
						txt3.setText(q.getMessage());
					}
					}
				}
			}
		}
		if(e.getActionCommand().equals("dcu")) {
			for(int i=0;i<cc.getEmergencyUnits().size();i++) {
				if(cc.getEmergencyUnits().get(i) instanceof DiseaseControlUnit) {
					DiseaseControlUnit dc = (DiseaseControlUnit)cc.getEmergencyUnits().get(i);
					txt3.setText(dc.toString());
					if(citizen!=null) {
					try {
						dc.respond(citizen);
						dc.setState(UnitState.RESPONDING);
						cp.remove(diseasecontrol);
						cp.revalidate();
						cp.repaint();
						diseasecontrol.setBounds(1250, 300, 100,80);
						diseasecontrol.setEnabled(true);
						cp.add(diseasecontrol);
					}
					catch(CannotTreatException |IncompatibleTargetException q) {
						txt3.setText(q.getMessage());
					}
					}
				}
			}
		}
		 if(e.getActionCommand().equals("evc")) {
			for(int i=0;i<cc.getEmergencyUnits().size();i++) {
				if(cc.getEmergencyUnits().get(i) instanceof Evacuator) {
					Evacuator ev = (Evacuator)cc.getEmergencyUnits().get(i);
					txt3.setText(ev.toString());
					if(building!=null) {
					try {
						ev.respond(building);
						ev.setState(UnitState.RESPONDING);
						cp.remove(evacuator);
						cp.revalidate();
						cp.repaint();
						evacuator.setBounds(1050, 400, 100, 80);
						evacuator.setEnabled(true);
						cp.add(evacuator);
					}
					catch(CannotTreatException |IncompatibleTargetException q) {
						txt3.setText(q.getMessage());
					}
					}
				}
			}
		}
		 if(e.getActionCommand().equals("gcu")) {
			for(int i=0;i<cc.getEmergencyUnits().size();i++) {
				if(cc.getEmergencyUnits().get(i) instanceof GasControlUnit) {
					GasControlUnit gc= (GasControlUnit)cc.getEmergencyUnits().get(i);
					txt3.setText(gc.toString());
					if(building!=null) {
					try {
						gc.respond(building);
						gc.setState(UnitState.RESPONDING);
						cp.remove(gascontrolunit);
						cp.revalidate();
						cp.repaint();
						gascontrolunit.setBounds(1150, 400, 100, 80);
						cp.add(gascontrolunit);
					}
					catch(CannotTreatException |IncompatibleTargetException q) {
						txt3.setText(q.getMessage());
					}
					}
				}
			}
		}
		if(e.getActionCommand().equals("dc")) {
			for(int i=0;i<10;i++) {
				for(int j=0;j<10;j++) {
					JButton jb= (JButton)e.getSource();
					JPanel u = ja[i][j];
					
					if(u.getComponent(0).equals(jb)) {
						
						Citizen c= cc.getEngine().getCitizenByLocation(cc.getEngine().getWorld()[i][j]);
						txt2.setText("Dead 7abayebna: "+c.toString());

					}
				}
			}
		}
		 if(e.getActionCommand().equals("db")) {
			for(int i=0;i<10;i++) {
				for(int j=0;j<10;j++) {
					JButton jb= (JButton)e.getSource();
					JPanel u = ja[i][j];
					
					if(u.getComponent(0).equals(jb)) {
						
						ResidentialBuilding rb= cc.getEngine().getBuildingByLocation(cc.getEngine().getWorld()[i][j]);
						txt2.setText("Dead 7abayebna: "+rb.toString());
					
					}
				}
			}
			
			
		}
	revalidate();
	repaint();
	}
	


	
}


