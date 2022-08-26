package Controllers;
import Models.Vehicles.Vehicle;
import Util.DateTime;

public interface CarParkManager {
	
	public static final int MAX=60; //Number of slots available in the Park
	
	public void addVehicle(Vehicle obj) throws InterruptedException;
	public void deleteVehicle(String noPlate);
	public void printcurrentVehicles();
	public void printVehiclePercentage();
	public void printLongestPark();
	public void printLatestPark();
	public void printVehicleByDay(DateTime entryTime);
	public void calculateChargers(String plateID, DateTime currentTime);
	
}
