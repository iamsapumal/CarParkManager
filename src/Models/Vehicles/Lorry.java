package Models.Vehicles;

import Util.DateTime;

public class Lorry extends Vehicle {
	//Properties
	private double cargoVolume;
    private double length;
	
	//Constructor
	public Lorry(String noPlate, String brand, String model, DateTime entryTime, double cargoVolume, double length) {
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

