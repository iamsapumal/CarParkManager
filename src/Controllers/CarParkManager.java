package Controllers;
import Models.Vehicles.Vehicle;
import Util.DateTime;

import java.math.BigDecimal;

public interface CarParkManager {
	
	public static final int MAX=60; //Number of slots available in the Models.Resources.Vehicles.Car Park
	
	public void addVehicle(Vehicle obj);
	public void deleteVehicle(String noPlate);
	public void printcurrentVehicles();
	public void printVehiclePercentage();
	public void printLongestPark();
	public void printLatestPark();
	public void printVehicleByDay(DateTime entryTime);
	public BigDecimal calculateChargers(String plateID, DateTime currentTime);
	
}
