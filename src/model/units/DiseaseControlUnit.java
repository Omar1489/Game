package model.units;

import exceptions.CannotTreatException;

import exceptions.IncompatibleTargetException;
import model.disasters.Infection;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;

public class DiseaseControlUnit extends MedicalUnit {
	@Override
	public String toString() {
		return "DiseaseControlUnit : State=" + getState() +'\n'+ "Location=" + getLocation() + '\n'+" UnitID="
				+ getUnitID() +'\n'+ " Target=" + getTarget() +'\n'+ "StepsPerCycle=" + getStepsPerCycle() + ".";
	}

	private CannotTreatException cte = new CannotTreatException(this,this.getTarget(),"Can not be treated");
	private IncompatibleTargetException ite= new IncompatibleTargetException(this,this.getTarget(),"Wrong target type");
	public DiseaseControlUnit(String unitID, Address location,
			int stepsPerCycle, WorldListener worldListener) {
		super(unitID, location, stepsPerCycle, worldListener);
	}

	@Override
	public void treat() throws CannotTreatException,IncompatibleTargetException {
		if(this.getTarget() instanceof ResidentialBuilding)
			throw ite;
		if(!this.canTreat(this.getTarget()))
			throw cte;
		
		
		getTarget().getDisaster().setActive(false);
		Citizen target = (Citizen) getTarget();
		if (target.getHp() == 0) {
			jobsDone();
			return;
		} else if (target.getToxicity() > 0) {
			target.setToxicity(target.getToxicity() - getTreatmentAmount());
			if (target.getToxicity() == 0)
				target.setState(CitizenState.RESCUED);
		}

		else if (target.getToxicity() == 0)
			heal();

	}

	public void respond(Rescuable r) throws IncompatibleTargetException, CannotTreatException {
		if(r instanceof ResidentialBuilding)
			throw ite;
		else if(r instanceof Citizen) {
			 if(!this.canTreat((Citizen)r))
				throw cte;
			 if(!(r.getDisaster() instanceof Infection))
				 throw cte;
			}
		if (getTarget() != null && ((Citizen) getTarget()).getToxicity() > 0
				&& getState() == UnitState.TREATING)
			reactivateDisaster();
		finishRespond(r);
	}

}
