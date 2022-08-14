package Models.Vehicles;

import Util.DateTime;

public class MotorBike extends Vehicle {
	private String engineSize;
	
	public MotorBike(String noPlate, String brand, String model, DateTime entryTime, String engineSize,int entrancePriority) {
		super(noPlate, brand, model, entryTime,entrancePriority);
		this.engineSize=engineSize;
	}
	
	public String getEngineSize() {
		return engineSize;
	}
	public void setEngineSize(String engineSize) {
		this.engineSize=engineSize;
	}

}
