package Models.Vehicles;

import Util.DateTime;

public class MiniBus extends Vehicle {
	//Properties
	private int seatCount;
	
	//Constructor
	public MiniBus(String noPlate, String brand, String model, DateTime entryTime) {
		super(noPlate, brand, model, entryTime);
		this.seatCount=seatCount;
	}
	
	public int getsetSeatCount() {
		return seatCount;
	}
	public void setSeatCount(int seatCount) {
		this.seatCount=seatCount;
	}

}