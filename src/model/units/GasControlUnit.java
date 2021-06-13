package model.units;

import exceptions.CannotTreatException;
import exceptions.IncompatibleTargetException;
import model.disasters.GasLeak;
import model.events.WorldListener;
import model.infrastructure.ResidentialBuilding;
import model.people.Citizen;
import simulation.Address;
import simulation.Rescuable;

public class GasControlUnit extends FireUnit {
	private CannotTreatException cte = new CannotTreatException(this,this.getTarget(),"Can not be treated");
	private IncompatibleTargetException ite= new IncompatibleTargetException(this,this.getTarget(),"Wrong target type");
	public GasControlUnit(String unitID, Address location, int stepsPerCycle,
			WorldListener worldListener) {
		super(unitID, location, stepsPerCycle, worldListener);
	}

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
		} else if (target.getGasLevel() > 0) 
			target.setGasLevel(target.getGasLevel() - 10);

		if (target.getGasLevel() == 0)
			jobsDone();

	}
	public void respond(Rescuable r) throws IncompatibleTargetException, CannotTreatException {
		if(r instanceof Citizen)
			throw ite;	
		else if(r instanceof ResidentialBuilding) {
			 if(!this.canTreat((ResidentialBuilding)r))
				throw cte;
			 if(!(r.getDisaster() instanceof GasLeak))
				 throw cte;
			}
		super.respond(r);
	}

	@Override
	public String toString() {
		return "GasControlUnit State=" + getState() +'\n'+ "Location=" + getLocation() +'\n'+ "UnitID="
				+ getUnitID() + '\n'+"Target=" + getTarget() +'\n'+ "StepsPerCycle=" + getStepsPerCycle() + ".";
	}

}
