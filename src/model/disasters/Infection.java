package model.disasters;

import exceptions.BuildingAlreadyCollapsedException;
import exceptions.CitizenAlreadyDeadException;
import model.people.Citizen;
import model.people.CitizenState;


public class Infection extends Disaster {
	private CitizenAlreadyDeadException cad= new CitizenAlreadyDeadException(this, "the citizen is already dead");
	public Infection(int startCycle, Citizen target) {
		super(startCycle, target);
	}
@Override
public void strike() throws CitizenAlreadyDeadException, BuildingAlreadyCollapsedException
{
	Citizen target = (Citizen)getTarget();
	if(target.getState().equals(CitizenState.DECEASED))
		throw cad;
	target.setToxicity(target.getToxicity()+25);
	super.strike();
}
	@Override
	public void cycleStep() {
		Citizen target = (Citizen)getTarget();
		target.setToxicity(target.getToxicity()+15);
		
	}
	public String toString () {
		return "Infection";
	}

}
