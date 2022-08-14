package Controllers;

import Models.Park.Floor;
import Models.Vehicles.*;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class FloorManager {
    private Lock lock = new ReentrantLock();
    public FloorManager (int floorLevel, Floor floor) {
        this.floorLevel = floorLevel;
        this.floor = floor;
    }

    private Floor floor;
    private int floorLevel;
    public synchronized boolean findAvailabilityByVehicle(Vehicle vehicle) {
        return true;
    }

    public  synchronized  int addVehicle (Vehicle vehicle) {
        int operationResult = -4;
        boolean unableToPark = true;
        try{
            lock.lock();
            if (!Thread.currentThread().isInterrupted()) {
                if(vehicle instanceof Car) {
                    for (int i = 0; i < floor.getSlotList().length; i++) {
                        if(!floor.getSlotList()[i].isOccupied()) {
                            floor.getSlotList()[i].setVehiclePlateNo(vehicle.getNoPlate());
                            floor.getSlotList()[i].setOccupied(true);
                            floor.getSlotList()[i].setParkedVehicleType(VehicleType.Car.name());
                            System.out.println("Car with number " + vehicle.getNoPlate() + " Parked at " + floor.getFloorNo()+ " Slot "  + floor.getSlotList()[i].getIndex());
                            slotAllocationFinished(vehicle);
                            operationResult = -1;
                            unableToPark = false;
                             break;
                            // notifyAll();
                        }
                    }
                    if (unableToPark) {
                        if (floorLevel == 7) {
                            operationResult = 8;
                        }else if (floorLevel == 8) {
                            operationResult = 1;
                        } else if (floorLevel == 1) {
                            operationResult = 2;
                        } else if (floorLevel == 2) {
                            operationResult = 3;
                        } else if (floorLevel == 3) {
                            operationResult = 4;
                        } else if (floorLevel == 4) {
                            operationResult = 5;
                        } else if (floorLevel == 5) {
                            operationResult = 6;
                        } else {
                            operationResult = -2;
                        }
                    }
                } else if (vehicle instanceof Van){
                    for (int i = 0; i < floor.getSlotList().length; i++) {
                        if(i+1 < floor.getSlotList().length && !floor.getSlotList()[i].isOccupied() && !floor.getSlotList()[i+1].isOccupied()) {
                            floor.getSlotList()[i].setVehiclePlateNo(vehicle.getNoPlate());
                            floor.getSlotList()[i].setOccupied(true);
                            floor.getSlotList()[i].setParkedVehicleType(VehicleType.Van.name());

                            floor.getSlotList()[i+1].setVehiclePlateNo(vehicle.getNoPlate());
                            floor.getSlotList()[i+1].setOccupied(true);
                            floor.getSlotList()[i+1].setParkedVehicleType(VehicleType.Van.name());
                            vehicle.setParkedFloorNumber(floorLevel);
                            vehicle.setParkedSlotNumber(floorLevel);
                            //   listOfVehicles.add(vehicle);
                            System.out.println("Van | number plate: " +vehicle.getNoPlate()+ " Parked at " + floor.getFloorNo()+ " Slot "  + floor.getSlotList()[i].getIndex() + " By " + Thread.currentThread().getName());
                            slotAllocationFinished(vehicle);
                            operationResult = -1;
                            unableToPark = false;
                            break;
                        } }
                    if (unableToPark) {
                        if (floorLevel == 1) {
                            operationResult = 2;
                        } else if (floorLevel == 2) {
                            operationResult = 3;
                        } else if (floorLevel == 3) {
                            operationResult = 4;
                        } else if (floorLevel == 4) {
                            operationResult = 5;
                        } else if (floorLevel == 5) {
                            operationResult = 6;
                        } else {
                            operationResult = -2;
                        }
                    }
                } else if (vehicle instanceof Bus){
                    for (int i = 0; i < floor.getSlotList().length; i++) {
                        if(i+1 < floor.getSlotList().length && i+2 < floor.getSlotList().length && i+3 < floor.getSlotList().length && i+4 < floor.getSlotList().length &&!floor.getSlotList()[i].isOccupied() && !floor.getSlotList()[i+1].isOccupied() && !floor.getSlotList()[i+2].isOccupied() && !floor.getSlotList()[i+3].isOccupied() && !floor.getSlotList()[i+4].isOccupied()) {
                            floor.getSlotList()[i].setVehiclePlateNo(vehicle.getNoPlate());
                            floor.getSlotList()[i].setOccupied(true);
                            floor.getSlotList()[i].setParkedVehicleType(VehicleType.Bus.name());

                            floor.getSlotList()[i+1].setVehiclePlateNo(vehicle.getNoPlate());
                            floor.getSlotList()[i+1].setOccupied(true);
                            floor.getSlotList()[i+1].setParkedVehicleType(VehicleType.Bus.name());

                            floor.getSlotList()[i+2].setVehiclePlateNo(vehicle.getNoPlate());
                            floor.getSlotList()[i+2].setOccupied(true);
                            floor.getSlotList()[i+2].setParkedVehicleType(VehicleType.Bus.name());

                            floor.getSlotList()[i+3].setVehiclePlateNo(vehicle.getNoPlate());
                            floor.getSlotList()[i+3].setOccupied(true);
                            floor.getSlotList()[i+3].setParkedVehicleType(VehicleType.Bus.name());

                            floor.getSlotList()[i+4].setVehiclePlateNo(vehicle.getNoPlate());
                            floor.getSlotList()[i+4].setOccupied(true);
                            floor.getSlotList()[i+4].setParkedVehicleType(VehicleType.Bus.name());
                            vehicle.setParkedFloorNumber(floorLevel);
                            vehicle.setParkedSlotNumber(floorLevel);
                            //   listOfVehicles.add(vehicle);
                            System.out.println("Bus | number plate: " + vehicle.getNoPlate()+ " Parked at " + floor.getFloorNo()+ " Slot "  + floor.getSlotList()[i].getIndex() + " By " + Thread.currentThread().getName());
                            slotAllocationFinished(vehicle);
                            operationResult = -1;
                            unableToPark = false;
                            break;
                        }
                    }
                    if(unableToPark && floorLevel == 0) {
                        operationResult = -2;
                    }
                } else if (vehicle instanceof Lorry){
                    for (int i = 0; i < floor.getSlotList().length; i++) {
                        if(i+1 < floor.getSlotList().length && i+2 < floor.getSlotList().length && i+3 < floor.getSlotList().length && i+4 < floor.getSlotList().length && !floor.getSlotList()[i].isOccupied() && !floor.getSlotList()[i+1].isOccupied() && !floor.getSlotList()[i+2].isOccupied() && !floor.getSlotList()[i+3].isOccupied() && !floor.getSlotList()[i+4].isOccupied()) {
                            floor.getSlotList()[i].setVehiclePlateNo(vehicle.getNoPlate());
                            floor.getSlotList()[i].setOccupied(true);
                            floor.getSlotList()[i].setParkedVehicleType(VehicleType.Lorry.name());

                            floor.getSlotList()[i+1].setVehiclePlateNo(vehicle.getNoPlate());
                            floor.getSlotList()[i+1].setOccupied(true);
                            floor.getSlotList()[i+1].setParkedVehicleType(VehicleType.Bus.name());

                            floor.getSlotList()[i+2].setVehiclePlateNo(vehicle.getNoPlate());
                            floor.getSlotList()[i+2].setOccupied(true);
                            floor.getSlotList()[i+2].setParkedVehicleType(VehicleType.Bus.name());

                            floor.getSlotList()[i+3].setVehiclePlateNo(vehicle.getNoPlate());
                            floor.getSlotList()[i+3].setOccupied(true);
                            floor.getSlotList()[i+3].setParkedVehicleType(VehicleType.Bus.name());

                            floor.getSlotList()[i+4].setVehiclePlateNo(vehicle.getNoPlate());
                            floor.getSlotList()[i+4].setOccupied(true);
                            floor.getSlotList()[i+4].setParkedVehicleType(VehicleType.Bus.name());

                            vehicle.setParkedFloorNumber(floorLevel);
                            vehicle.setParkedSlotNumber(floorLevel);
                            //   listOfVehicles.add(vehicle);
                            System.out.println("Lorry | number plate: " + vehicle.getNoPlate()+ " Parked at " + floor.getFloorNo()+ " Slot "  + floor.getSlotList()[i].getIndex() + " By " + Thread.currentThread().getName());
                            slotAllocationFinished(vehicle);
                            operationResult = -1;
                            unableToPark = false;
                            break;
                        }
                    }
                    if(unableToPark && floorLevel == 0) {
                        operationResult = -2;
                    }
//
                } else if (vehicle instanceof MiniBus){
                    for (int i = 0; i < floor.getSlotList().length; i++) {
                        if(i+1 < floor.getSlotList().length && i+2 < floor.getSlotList().length && !floor.getSlotList()[i].isOccupied() && !floor.getSlotList()[i+1].isOccupied() && !floor.getSlotList()[i+2].isOccupied()) {
                            floor.getSlotList()[i].setVehiclePlateNo(vehicle.getNoPlate());
                            floor.getSlotList()[i].setOccupied(true);
                            floor.getSlotList()[i].setParkedVehicleType(VehicleType.MiniBus.name());

                            floor.getSlotList()[i+1].setVehiclePlateNo(vehicle.getNoPlate());
                            floor.getSlotList()[i+1].setOccupied(true);
                            floor.getSlotList()[i+1].setParkedVehicleType(VehicleType.Bus.name());

                            floor.getSlotList()[i+2].setVehiclePlateNo(vehicle.getNoPlate());
                            floor.getSlotList()[i+2].setOccupied(true);
                            floor.getSlotList()[i+2].setParkedVehicleType(VehicleType.Bus.name());
                            vehicle.setParkedFloorNumber(floorLevel);
                            vehicle.setParkedSlotNumber(floorLevel);
                            //   listOfVehicles.add(vehicle);
                            System.out.println("MiniBus | number plate: " +vehicle.getNoPlate()+ " Parked at " + floor.getFloorNo()+ " Slot "  + floor.getSlotList()[i].getIndex() + " By " + Thread.currentThread().getName());
                            slotAllocationFinished(vehicle);
                            operationResult = -1;
                            unableToPark = false;
                            break;
                        } if(unableToPark && floorLevel == 0) {
                            operationResult = -2;
                        }
                    }
                } else if (vehicle instanceof MiniLorry){
                    for (int i = 0; i < floor.getSlotList().length; i++) {
                        if(i+1 < floor.getSlotList().length && i+2 < floor.getSlotList().length && !floor.getSlotList()[i].isOccupied() && !floor.getSlotList()[i+1].isOccupied() && !floor.getSlotList()[i+2].isOccupied()) {
                            floor.getSlotList()[i].setVehiclePlateNo(vehicle.getNoPlate());
                            floor.getSlotList()[i].setOccupied(true);
                            floor.getSlotList()[i].setParkedVehicleType(VehicleType.MiniLorry.name());

                            floor.getSlotList()[i+1].setVehiclePlateNo(vehicle.getNoPlate());
                            floor.getSlotList()[i+1].setOccupied(true);
                            floor.getSlotList()[i+1].setParkedVehicleType(VehicleType.Bus.name());

                            floor.getSlotList()[i+2].setVehiclePlateNo(vehicle.getNoPlate());
                            floor.getSlotList()[i+2].setOccupied(true);
                            floor.getSlotList()[i+2].setParkedVehicleType(VehicleType.Bus.name());

                            vehicle.setParkedFloorNumber(floorLevel);
                            vehicle.setParkedSlotNumber(floorLevel);
                            //   listOfVehicles.add(vehicle);
                            System.out.println("MiniLorry | number plate: " + vehicle.getNoPlate()+ " Parked at " + floor.getFloorNo()+ " Slot "  + floor.getSlotList()[i].getIndex() + " By " + Thread.currentThread().getName());
                            slotAllocationFinished(vehicle);
                            operationResult = -1;
                            unableToPark = false;
                            break;
                        }
                    }
                    if(unableToPark && floorLevel == 0) {
                        operationResult = -2;
                    }
                } else if (vehicle instanceof MotorBike) {
                    boolean isBikeSlotFound = false;
                    for (int i = 0; i < floor.getSlotList().length; i++) {
                        if (floor.getSlotList()[i].getParkedVehicleType() != null && floor.getSlotList()[i].getParkedVehicleType().equals(VehicleType.MotorBike.name()) && floor.getSlotList()[i].getBikeSpaceArray().size() < 3) {
                            floor.getSlotList()[i].getBikeSpaceArray().add(vehicle.getNoPlate());
                            floor.getSlotList()[i].setOccupied(true);
                            System.out.println("Bike | number plate: " + vehicle.getNoPlate() + " Parked at " + floor.getFloorNo() + " Slot " + floor.getSlotList()[i].getIndex());
                            slotAllocationFinished(vehicle);
                            isBikeSlotFound = true;
                            unableToPark = false;
                            break;
                        }
                    }
                    if (!isBikeSlotFound) {
                    //    changeBikePriority();
                        for (int i = 0; i < floor.getSlotList().length; i++) {
                            if (!floor.getSlotList()[i].isOccupied()) {
                                floor.getSlotList()[i].getBikeSpaceArray().add(vehicle.getNoPlate());
                                floor.getSlotList()[i].setOccupied(true);
                                System.out.println("Bike | number plate: " + vehicle.getNoPlate() + " Parked at " + floor.getFloorNo() + " Slot " + floor.getSlotList()[i].getIndex());
                                floor.getSlotList()[i].setParkedVehicleType(VehicleType.MotorBike.name());
                                slotAllocationFinished(vehicle);
                                unableToPark = false;
                                break;
                            }
                        }
                    }
                    if (unableToPark) {
                        if (floorLevel == 1) {
                            operationResult = 2;
                        } else if (floorLevel == 2) {
                            operationResult = 3;
                        } else if (floorLevel == 3) {
                            operationResult = 4;
                        } else if (floorLevel == 4) {
                            operationResult = 5;
                        } else if (floorLevel == 5) {
                            operationResult = 6;
                        } else {
                            operationResult = -2;
                        }
                    }
                }
                //Thread.sleep(1000);
            }
        } catch (Exception ex){
            operationResult = -3;
        } finally {
            lock.unlock();
            return operationResult;
        }
        //  addVehicleToFloor.start();
    }


    public void changeBikePriority() {
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
    }



//    public  synchronized  int findSpaceAvailabilityByVehicle (Vehicle vehicle) {
//        int operationResult = -4;
//        boolean unableToFindaSlot = true;
//        try{
//            lock.lock();
//            if (!Thread.currentThread().isInterrupted()) {
//                if(vehicle instanceof Car) {
//                    for (int i = 0; i < floor.getSlotList().length; i++) {
//                        if(!floor.getSlotList()[i].isOccupied()) {
//
//                        }
//                    }
//                    if (unableToFindaSlot) {
//                        if (floorLevel == 7) {
//                            operationResult = 8;
//                        }else if (floorLevel == 8) {
//                            operationResult = 1;
//                        } else if (floorLevel == 1) {
//                            operationResult = 2;
//                        } else if (floorLevel == 2) {
//                            operationResult = 3;
//                        } else if (floorLevel == 3) {
//                            operationResult = 4;
//                        } else if (floorLevel == 4) {
//                            operationResult = 5;
//                        } else if (floorLevel == 5) {
//                            operationResult = 6;
//                        } else {
//                            operationResult = -2;
//                        }
//                    }
//                } else if (vehicle instanceof Van){
//                    for (int i = 0; i < floor.getSlotList().length; i++) {
//                        if(i+1 < floor.getSlotList().length && !floor.getSlotList()[i].isOccupied() && !floor.getSlotList()[i+1].isOccupied()) {
//
//                            operationResult = -1;
//                            unableToFindaSlot = false;
//                            break;
//                        } }
//                    if (unableToFindaSlot) {
//                        if (floorLevel == 1) {
//                            operationResult = 2;
//                        } else if (floorLevel == 2) {
//                            operationResult = 3;
//                        } else if (floorLevel == 3) {
//                            operationResult = 4;
//                        } else if (floorLevel == 4) {
//                            operationResult = 5;
//                        } else if (floorLevel == 5) {
//                            operationResult = 6;
//                        } else {
//                            operationResult = -2;
//                        }
//                    }
//                } else if (vehicle instanceof Bus){
//                    for (int i = 0; i < floor.getSlotList().length; i++) {
//                        if(i+1 < floor.getSlotList().length && i+2 < floor.getSlotList().length && i+3 < floor.getSlotList().length && i+4 < floor.getSlotList().length &&!floor.getSlotList()[i].isOccupied() && !floor.getSlotList()[i+1].isOccupied() && !floor.getSlotList()[i+2].isOccupied() && !floor.getSlotList()[i+3].isOccupied() && !floor.getSlotList()[i+4].isOccupied()) {
//                            operationResult = -1;
//                            unableToFindaSlot = false;
//                            break;
//                        }
//                    }
//                    if(unableToFindaSlot && floorLevel == 0) {
//                        operationResult = -2;
//                    }
//                } else if (vehicle instanceof Lorry){
//                    for (int i = 0; i < floor.getSlotList().length; i++) {
//                        if(i+1 < floor.getSlotList().length && i+2 < floor.getSlotList().length && i+3 < floor.getSlotList().length && i+4 < floor.getSlotList().length && !floor.getSlotList()[i].isOccupied() && !floor.getSlotList()[i+1].isOccupied() && !floor.getSlotList()[i+2].isOccupied() && !floor.getSlotList()[i+3].isOccupied() && !floor.getSlotList()[i+4].isOccupied()) {
//
//                            operationResult = -1;
//                            unableToFindaSlot = false;
//                            break;
//                        }
//                    }
//                    if(unableToFindaSlot && floorLevel == 0) {
//                        operationResult = -2;
//                    }
////
//                } else if (vehicle instanceof MiniBus){
//                    for (int i = 0; i < floor.getSlotList().length; i++) {
//                        if(i+1 < floor.getSlotList().length && i+2 < floor.getSlotList().length && !floor.getSlotList()[i].isOccupied() && !floor.getSlotList()[i+1].isOccupied() && !floor.getSlotList()[i+2].isOccupied()) {
//                            operationResult = -1;
//                            unableToFindaSlot = false;
//                            break;
//                        } if(unableToFindaSlot && floorLevel == 0) {
//                            operationResult = -2;
//                        }
//                    }
//                } else if (vehicle instanceof MiniLorry){
//                    for (int i = 0; i < floor.getSlotList().length; i++) {
//                        if(i+1 < floor.getSlotList().length && i+2 < floor.getSlotList().length && !floor.getSlotList()[i].isOccupied() && !floor.getSlotList()[i+1].isOccupied() && !floor.getSlotList()[i+2].isOccupied()) {
//
//                            operationResult = -1;
//                            unableToFindaSlot = false;
//                            break;
//                        }
//                    }
//                    if(unableToFindaSlot && floorLevel == 0) {
//                        operationResult = -2;
//                    }
//                } else if (vehicle instanceof MotorBike) {
//                    boolean isBikeSlotFound = false;
//                    for (int i = 0; i < floor.getSlotList().length; i++) {
//                        if (floor.getSlotList()[i].getParkedVehicleType() != null && floor.getSlotList()[i].getParkedVehicleType().equals(VehicleType.MotorBike.name()) && floor.getSlotList()[i].getBikeSpaceArray().size() < 3) {
//
//                            isBikeSlotFound = true;
//                            unableToFindaSlot = false;
//                            break;
//                        }
//                    }
//                    if (!isBikeSlotFound) {
//                        changeBikePriority();
//                        for (int i = 0; i < floor.getSlotList().length; i++) {
//                            if (!floor.getSlotList()[i].isOccupied()) {
//
//                                unableToFindaSlot = false;
//                                break;
//                            }
//                        }
//                    }
//                    if (unableToFindaSlot) {
//                        if (floorLevel == 1) {
//                            operationResult = 2;
//                        } else if (floorLevel == 2) {
//                            operationResult = 3;
//                        } else if (floorLevel == 3) {
//                            operationResult = 4;
//                        } else if (floorLevel == 4) {
//                            operationResult = 5;
//                        } else if (floorLevel == 5) {
//                            operationResult = 6;
//                        } else {
//                            operationResult = -2;
//                        }
//                    }
//                }
//                //Thread.sleep(1000);
//            }
//        } catch (Exception ex){
//            operationResult = -3;
//        } finally {
//            lock.unlock();
//            return operationResult;
//        }
//        //  addVehicleToFloor.start();
//    }



    private synchronized void slotAllocationFinished(Vehicle vehicle) {
        PettahMultiStoryCarParkManager.listOfVehicles.add(vehicle);
        Thread.currentThread().interrupt();
        System.out.println(Thread.currentThread().getName() + " Interrupted");
        //latch.countDown();
    }


//
//    private synchronized void finished(String threadName) {
//        if (winner == null) {
//            winner = threadName;
//        }
//        latch.countDown();
//    }

    public Floor getFloor() {
        return floor;
    }

    public void setFloor(Floor floor) {
        this.floor = floor;
    }
}
