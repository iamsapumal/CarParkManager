package Models.Vehicles;
import Util.DateTime;

public abstract class Vehicle extends Object implements Comparable<Vehicle> {
	
	@Override
	public int compareTo(Vehicle o) {
		return this.entryTime.compareTo(o.entryTime);
	}
	
	//Models.Resources.Vehicles.Vehicle Properties
	private String noPlate;
	private String brand;
	private String model;
	public DateTime entryTime;
	private int parkedSlotNumber;
	private int parkedFloorNumber;
	
	//Constructor
	public Vehicle(String noPlate, String brand, String model, DateTime entryTime ){
		this.noPlate=noPlate;
		this.brand=brand;
		this.model=model;
		this.entryTime=entryTime;
	}
	
	public String getNoPlate() {
		return noPlate;
	}
	public void setNoPlate(String noPlate) {
		this.noPlate=noPlate;
	}
	
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand=brand;
	}
	
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model=model;
	}
	
	public DateTime getEntryDate() {
		return entryTime;
	}
	public void setEntryDate(DateTime entryTime) {
		this.entryTime = entryTime;
	}

	@Override
	public String toString() {
		return "Vehicle{" +
				"noPlate='" + noPlate + '\'' +
				", brand='" + brand + '\'' +
				", model='" + model + '\'' +
				", entryTime=" + entryTime +
				'}';
	}


	public int getParkedSlotNumber() {
		return parkedSlotNumber;
	}

	public void setParkedSlotNumber(int parkedSlotNumber) {
		this.parkedSlotNumber = parkedSlotNumber;
	}

	public int getParkedFloorNumber() {
		return parkedFloorNumber;
	}

	public void setParkedFloorNumber(int parkedFloorNumber) {
		this.parkedFloorNumber = parkedFloorNumber;
	}
}
