package model.units;

import exceptions.CannotTreatException;

import exceptions.IncompatibleTargetException;
import model.disasters.Fire;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import simulation.Address;
import simulation.Rescuable;

public class FireTruck extends FireUnit {
private CannotTreatException cte = new CannotTreatException(this,this.getTarget(),"Can not be treated");
private IncompatibleTargetException ite= new IncompatibleTargetException(this,this.getTarget(),"Wrong target type");
	public FireTruck(String unitID, Address location, int stepsPerCycle,
			WorldListener worldListener) {
		super(unitID, location, stepsPerCycle, worldListener);
	}

	@Override
	public void treat() throws CannotTreatException,IncompatibleTargetException{
		if(this.getTarget() instanceof Citizen)
			throw ite;
		if(!this.canTreat(this.getTarget()))
			throw cte;
		
		getTarget().getDisaster().setActive(false);

		ResidentialBuilding target = (ResidentialBuilding) getTarget();
		if (target.getStructuralIntegrity() == 0) {
			jobsDone();
			return;
		} else if (target.getFireDamage() > 0)

			target.setFireDamage(target.getFireDamage() - 10);

		if (target.getFireDamage() == 0)

			jobsDone();

	}
	public void respond(Rescuable r) throws IncompatibleTargetException, CannotTreatException {
		if(r instanceof Citizen)
			throw ite;
		else if(r instanceof ResidentialBuilding) {
		 if(!this.canTreat((ResidentialBuilding)r))
			throw cte;
		 if(!(r.getDisaster() instanceof Fire))
			 throw cte;
		}
		super.respond(r);
	}

	@Override
	public String toString() {
		return "FireTruck State=" + getState() +'\n'+ "Location=" + getLocation() +'\n'+ "UnitID="
				+ getUnitID() + '\n'+ "Target=" + getTarget()+'\n' + "StepsPerCycle=" + getStepsPerCycle() + ".";
	}

}
