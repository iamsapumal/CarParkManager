package Controllers;

import Models.Vehicles.Vehicle;

import java.util.Comparator;

public class VehicleComparator implements Comparator<Vehicle> {
    public int compare(Vehicle v1, Vehicle v2)
    {
        return Integer.compare(v1.getEntrancePriority(), v2.getEntrancePriority());

    }
}
