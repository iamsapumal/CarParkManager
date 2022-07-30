package Models.Park;

import Models.Resources.EntranceDoor;
import Models.Resources.ExitDoor;
import Models.Resources.Lift;
import Models.Vehicles.Car;
import Models.Vehicles.Van;
import Models.Vehicles.Vehicle;
import Models.Vehicles.VehicleType;

import java.util.List;

public class Floor {
    private int floorNo;
    private Slot[] slotList;
    public List<VehicleType> accessibleVehicles;
    public List<VehicleType> prioritizedVehicles;
    private EntranceDoor[] availableEntranceDoors;
    private ExitDoor[] availableExitDoors;
    private Lift[] availableLifts;
    private int bikeCountForSlot = 3;
    public Floor(List<VehicleType> accessibleVehicles, List<VehicleType> prioritizedVehicles,int numberOfSlots) {
        this.accessibleVehicles = accessibleVehicles;
        this.prioritizedVehicles = prioritizedVehicles;
        this.slotList = new Slot[numberOfSlots];
        Slot[] slots = new Slot[numberOfSlots];
            for (int j = 0; j < numberOfSlots; j++){
                Slot s = new Slot();
                s.setIndex(j);
                s.setOccupied(false);
                s.setAvailableBikeSpaces(bikeCountForSlot);
                if (j+1 < numberOfSlots){
                    s.setNextIndex(j+1);
                }else {
                    s.setNextIndex(-1);
                }
                slots[j] = s;
            }
            this.setSlotList(slots);
    }

    public int getFloorNo() {
        return floorNo;
    }

    public void setFloorNo(int floorNo) {
        this.floorNo = floorNo;
    }

    public Slot[] getSlotList() {
        return slotList;
    }


    public void findSpaceForVehicleByType(Vehicle vehicle) {
        if (vehicle instanceof Car) {}
        else if (vehicle instanceof Van) {}

    }

    public void setSlotList(Slot[] slotList) {
        this.slotList = slotList;
    }
}
