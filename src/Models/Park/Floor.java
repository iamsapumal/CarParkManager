package Models.Park;

import Models.Resources.EntranceDoor;
import Models.Resources.ExitDoor;
import Models.Resources.Lift;
import Models.Vehicles.*;

import java.util.ArrayList;
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
    public Floor(int floorNo, List<VehicleType> accessibleVehicles, List<VehicleType> prioritizedVehicles,int numberOfSlots) {
        this.floorNo = floorNo;
        this.accessibleVehicles = accessibleVehicles;
        this.prioritizedVehicles = prioritizedVehicles;
        this.slotList = new Slot[numberOfSlots];
        Slot[] slots = new Slot[numberOfSlots];
            for (int j = 0; j < numberOfSlots; j++){
                Slot s = new Slot();
                s.setIndex(j);
                s.setOccupied(false);
               // s.setBikeSpaceArray(bikeCountForSlot);
                ArrayList<String> bikesSpaceList = new ArrayList(3);
                s.setBikeSpaceArray(bikesSpaceList);
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


    public boolean findSpaceForVehicleByType(Vehicle vehicle) {
        if (vehicle instanceof Car) {
            for (int i = 0; i < slotList.length; i++) {
                if(!slotList[i].isOccupied()) {
                    return true;
                }
            }
            return true;
        } else if (vehicle instanceof Van) {
            for (int i = 0; i < slotList.length; i++) {
                if(!slotList[i].isOccupied() && !slotList[i+1].isOccupied()) {
                    return true;
                }
            }
        } else if (vehicle instanceof Bus) {
            for (int i = 0; i < slotList.length; i++) {
                if(!slotList[i].isOccupied() && !slotList[i+1].isOccupied() && !slotList[i+2].isOccupied() && !slotList[i+3].isOccupied() && !slotList[i+4].isOccupied()) {
                    return true;
                }
            }
        } else if (vehicle instanceof Lorry) {
            for (int i = 0; i < slotList.length; i++) {
                if(!slotList[i].isOccupied() && !slotList[i+1].isOccupied() && !slotList[i+2].isOccupied() && !slotList[i+3].isOccupied() && !slotList[i+4].isOccupied()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean isBikeSpacesAvailable(Vehicle vehicle) {
        if (vehicle instanceof Car) {
            return true;
        }
        else if (vehicle instanceof Van) {
            return true;
        }
        return false;
    }

    public void setSlotList(Slot[] slotList) {
        this.slotList = slotList;
    }
}
