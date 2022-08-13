package Models.Vehicles;

import Util.DateTime;

public class MiniLorry extends Vehicle {
	//Properties
	private double cargoVolume;
    private double length;
	
	//Constructor
	public MiniLorry(String noPlate, String brand, String model, DateTime entryTime) {
		super(noPlate, brand, model, entryTime);
		this.cargoVolume=cargoVolume;
        this.length=length;
	}
	
	public double getCargoVolume() {
		return cargoVolume;
	}
	public void setCargoVolume(double cargoVolume) {
		this.cargoVolume=cargoVolume;
	}


	public double getLength() {
		return length;
	}
	public void setLength(double length) {
		this.length=length;
	}

}

