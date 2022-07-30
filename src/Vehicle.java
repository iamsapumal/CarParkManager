

public abstract class Vehicle extends Object implements Comparable<Vehicle> {
	
	@Override
	public int compareTo(Vehicle o) {
		return this.entryTime.compareTo(o.entryTime);
	}
	
	//Vehicle Properties
	private String noPlate;
	private String brand;
	private String model;
	private DateTime entryTime;
	
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
	
	
	

}
