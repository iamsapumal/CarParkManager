

public class MotorBike extends Vehicle {
	private String engineSize;
	
	public MotorBike(String noPlate, String brand, String model, DateTime entryTime, String engineSize) {
		super(noPlate, brand, model, entryTime);
		this.engineSize=engineSize;
	}
	
	public String getEngineSize() {
		return engineSize;
	}
	public void setEngineSize(String engineSize) {
		this.engineSize=engineSize;
	}

}
