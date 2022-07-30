import java.sql.Array;

public class FloorManager {

    public FloorManager (int floorLevel) {
        this.floorLevel = floorLevel;
    }

   private Floor floor = new Floor();
   private int floorLevel;
    public synchronized boolean findAvailabilityByVehicle(Vehicle vehicle) {
//if ()
        return true;
        }

    public  synchronized  void addVehicle (Vehicle vehicle) {
        Runnable assignVehicle = new Runnable() {
            @Override
            public void run() {
                try{
                    if(vehicle instanceof Car) {
                        for (int i = 0; i < floor.getSlotList().length; i++) {
                            if(!floor.getSlotList()[i].isOccupied()) {
                                String[] numPlates = {vehicle.getNoPlate()};
                                floor.getSlotList()[i].setVehiclePlateNo(numPlates);
                                floor.getSlotList()[i].setOccupied(true);
                                floor.getSlotList()[i].setParkedVehicleType(VehicleType.Car.name());
                                notifyAll();
                            }
                        }
                    } else if (vehicle instanceof Van){
                        for (int i = 0; i < floor.getSlotList().length; i++) {
                            if(!floor.getSlotList()[i].isOccupied()) {
                                String[] numPlates = {vehicle.getNoPlate()};
                                floor.getSlotList()[i].setVehiclePlateNo(numPlates);
                                floor.getSlotList()[i].setParkedVehicleType(VehicleType.Van.name());
                                notifyAll();
                            }
                        }
                    }else if (vehicle instanceof MotorBike){
                        boolean isBikeSlotFound = false;
                        for (int i = 0; i < floor.getSlotList().length; i++) {
                            if (floor.getSlotList()[i].getParkedVehicleType().equals(VehicleType.MotorBike.name())){
                                if (floor.getSlotList()[i].getAvailableBikeSpaces()!=0){
                                    String[] numPlates = floor.getSlotList()[i].getVehiclePlateNo();
                                    for (int j = 0; j < numPlates.length; j++) {
                                        if (numPlates[i].equals("")){
                                            numPlates[i] = vehicle.getNoPlate();
                                        }
                                    }
                                    floor.getSlotList()[i].setVehiclePlateNo(numPlates);
                                    floor.getSlotList()[i].setOccupied(true);
                                    isBikeSlotFound =true;
                                    floor.getSlotList()[i].setAvailableBikeSpaces(floor.getSlotList()[i].getAvailableBikeSpaces()-1);
                                    notifyAll();
                                }
                            }
                        }
                        if (!isBikeSlotFound) {
                            for (int i = 0; i < floor.getSlotList().length; i++) {
                                if(!floor.getSlotList()[i].isOccupied()) {
                                    String[] numPlates = {vehicle.getNoPlate()};
                                    floor.getSlotList()[i].setVehiclePlateNo(numPlates);
                                    floor.getSlotList()[i].setOccupied(true);
                                    floor.getSlotList()[i].setParkedVehicleType(VehicleType.MotorBike.name());
                                    floor.getSlotList()[i].setAvailableBikeSpaces(floor.getSlotList()[i].getAvailableBikeSpaces()-1);
                                    isBikeSlotFound =true;
                                    notifyAll();
                                }

                            }
                        }

                    }
                    Thread.sleep(1000);
                } catch (InterruptedException ie) {
                    System.out.println("");
                } finally {

                }
            }
        };
        Thread addVehicleToFloor = new Thread(assignVehicle);
        if(vehicle instanceof Car) {
            if (floorLevel == 1) {
                addVehicleToFloor.setPriority(5);
            }
        } else if(vehicle instanceof Van) {
            addVehicleToFloor.setPriority(5);
        } else if(vehicle instanceof Bus) {
            addVehicleToFloor.setPriority(5);
        } else if(vehicle instanceof Lorry) {
            addVehicleToFloor.setPriority(5);
        } else if(vehicle instanceof MotorBike) {
            addVehicleToFloor.setPriority(5);
        }

        addVehicleToFloor.start();
    }

    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }
}
