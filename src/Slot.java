import java.util.List;

public class Slot {
    private int index;
    private boolean isOccupied;
    private int nextIndex;

    private String parkedVehicleType;

    private String[] vehiclePlateNo;

    private int availableBikeSpaces;


    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    public int getNextIndex() {
        return nextIndex;
    }

    public void setNextIndex(int nextIndex) {
        this.nextIndex = nextIndex;
    }

    public String getParkedVehicleType() {
        return parkedVehicleType;
    }

    public void setParkedVehicleType(String parkedVehicleType) {
        this.parkedVehicleType = parkedVehicleType;
    }

    public String[] getVehiclePlateNo() {
        return vehiclePlateNo;
    }

    public void setVehiclePlateNo(String[] vehiclePlateNo) {
        this.vehiclePlateNo = vehiclePlateNo;
    }


    public int getAvailableBikeSpaces() {
        return availableBikeSpaces;
    }

    public void setAvailableBikeSpaces(int availableBikeSpaces) {
        this.availableBikeSpaces = availableBikeSpaces;
    }
}
