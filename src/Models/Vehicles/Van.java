package Models.Vehicles;
import Util.DateTime;

public class Van extends Vehicle {
	//Properties
	private double cargoVolume;
	
	//Constructor
	public Van(String noPlate, String brand, String model, DateTime entryTime, double cargoVolume,int entrancePriority) {
		super(noPlate, brand, model, entryTime,entrancePriority);
		this.cargoVolume=cargoVolume;
	}
	
	public double getCargoVolume() {
		return cargoVolume;
	}
	public void setCargoVolume(double cargoVolume) {
		this.cargoVolume=cargoVolume;
	}

}
