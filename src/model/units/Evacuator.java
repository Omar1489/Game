package model.units;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.disasters.Collapse;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import simulation.Address;
import simulation.Rescuable;

public class Evacuator extends PoliceUnit {
	@Override
	public String toString() {
		return "Evacuator MaxCapacity=" + getMaxCapacity() + '\n'+" Passengers=" + getPassengers().toString()
				+'\n'+ "State=" + getState() +'\n'+ "Location=" + getLocation() + '\n'+"UnitID=" + getUnitID()
				+'\n'+ "Target=" + getTarget() +'\n'+ "StepsPerCycle=" + getStepsPerCycle() + ".";
	}
	private CannotTreatException cte = new CannotTreatException(this,this.getTarget(),"Can not be treated");
	private IncompatibleTargetException ite= new IncompatibleTargetException(this,this.getTarget(),"Wrong target type");
	public Evacuator(String unitID, Address location, int stepsPerCycle,
			WorldListener worldListener, int maxCapacity) {
		super(unitID, location, stepsPerCycle, worldListener, maxCapacity);

	}

	@Override
	public void treat() throws CannotTreatException,IncompatibleTargetException{
		if(this.getTarget() instanceof Citizen)
			throw ite;
		if(!this.canTreat(this.getTarget()))
			throw cte;
		ResidentialBuilding target = (ResidentialBuilding) getTarget();
		if (target.getStructuralIntegrity() == 0
				|| target.getOccupants().size() == 0) {
			jobsDone();
			return;
		}

		for (int i = 0; getPassengers().size() != getMaxCapacity()
				&& i < target.getOccupants().size(); i++) {
			getPassengers().add(target.getOccupants().remove(i));
			i--;
		}

		setDistanceToBase(target.getLocation().getX()
				+ target.getLocation().getY());

	}
	public void respond(Rescuable r) throws IncompatibleTargetException, CannotTreatException {
		if(r instanceof Citizen)
			throw ite;
		else if(r instanceof ResidentialBuilding) {
			 if(!this.canTreat((ResidentialBuilding)r))
				throw cte;
			 if(!(r.getDisaster() instanceof Collapse))
				 throw cte;
			}
		super.respond(r);
	}

}
