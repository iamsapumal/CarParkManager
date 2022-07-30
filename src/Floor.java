import java.util.List;

public class Floor {
    private int floorNo;
    private Slot[] slotList;

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
