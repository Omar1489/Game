package model.units;

import exceptions.CannotTreatException;

import exceptions.IncompatibleTargetException;
import model.disasters.Injury;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import model.people.CitizenState;
import simulation.Address;
import simulation.Rescuable;

public class Ambulance extends MedicalUnit {
	private CannotTreatException cte = new CannotTreatException(this,this.getTarget(),"Can not be treated");
	private IncompatibleTargetException ite= new IncompatibleTargetException(this,this.getTarget(),"Wrong target type");
	public Ambulance(String unitID, Address location, int stepsPerCycle,
			WorldListener worldListener) {
		super(unitID, location, stepsPerCycle, worldListener);
	}

	@Override
	public void treat() throws CannotTreatException,IncompatibleTargetException{
		if(this.getTarget() instanceof ResidentialBuilding)
			throw ite;
		if(!this.canTreat(this.getTarget()))
			throw cte;
		getTarget().getDisaster().setActive(false);

		Citizen target = (Citizen) getTarget();
		if (target.getHp() == 0) {
			jobsDone();
			return;
		} else if (target.getBloodLoss() > 0) {
			target.setBloodLoss(target.getBloodLoss() - getTreatmentAmount());
			if (target.getBloodLoss() == 0)
				target.setState(CitizenState.RESCUED);
		}

		else if (target.getBloodLoss() == 0)

			heal();

	}

	public void respond(Rescuable r) throws CannotTreatException, IncompatibleTargetException {
		if(r instanceof ResidentialBuilding)
			throw ite;
		else if(r instanceof Citizen) {
			 if(!this.canTreat((Citizen)r))
				throw cte;
			 if(!(r.getDisaster() instanceof Injury))
				 throw cte;
			}
		if (getTarget() != null && ((Citizen) getTarget()).getBloodLoss() > 0
				&& getState() == UnitState.TREATING)
			reactivateDisaster();
		finishRespond(r);
	}

	@Override
	public String toString() {
		return "Ambulance : State=" + getState() +'\n'+ " Location=" + getLocation() +'\n'+" UnitID="
				+ getUnitID() + '\n'+" Target" + getTarget() + '\n'+" StepsPerCycle=" + getStepsPerCycle() + ".";
	}

}
