package Models.Vehicles;

import Util.DateTime;

public class Bus extends Vehicle {
	//Properties
	private int seatCount;
	
	//Constructor
	public Bus(String noPlate, String brand, String model, DateTime entryTime,int entrancePriority) {
		super(noPlate, brand, model, entryTime, entrancePriority);
		this.seatCount=seatCount;
	}
	
	public int getsetSeatCount() {
		return seatCount;
	}
	public void setSeatCount(int seatCount) {
		this.seatCount=seatCount;
	}

	@Override
	public String toString() {
		return "Bus{" +
				"seatCount=" + seatCount +
				'}';
	}

}