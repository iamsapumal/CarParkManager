package Controllers;

import Models.Park.Floor;
import Models.Vehicles.*;

public class FloorManager {

    public FloorManager (int floorLevel, Floor floor) {
        this.floorLevel = floorLevel;
        this.floor = floor;
    }

   private Floor floor;
   private int floorLevel;
    public synchronized boolean findAvailabilityByVehicle(Vehicle vehicle) {
//if ()
        return true;
        }

    public  synchronized  void addVehicle (ThreadGroup threadGroup,Vehicle vehicle, Runnable assignVehicle) {

        Thread addVehicleToFloor = new Thread(threadGroup, assignVehicle, vehicle.getNoPlate()+ floorLevel);
        if(vehicle instanceof Car) {
            if(floor.accessibleVehicles.contains(VehicleType.Car) && floor.prioritizedVehicles.contains(VehicleType.Car)){
                addVehicleToFloor.setPriority(Thread.MAX_PRIORITY);
            } else {
                addVehicleToFloor.setPriority(Thread.NORM_PRIORITY);
            }
        } else if (vehicle instanceof Van) {
            if( floor.accessibleVehicles.contains(VehicleType.Van) && floor.prioritizedVehicles.contains(VehicleType.Van)){
                addVehicleToFloor.setPriority(Thread.MAX_PRIORITY);
            } else {
                addVehicleToFloor.setPriority(Thread.NORM_PRIORITY);
            }
        } else if (vehicle instanceof Bus) {
            if(floor.accessibleVehicles.contains(VehicleType.Bus) && floor.prioritizedVehicles.contains(VehicleType.Bus)) {
                addVehicleToFloor.setPriority(Thread.MAX_PRIORITY);
            } else {
                addVehicleToFloor.setPriority(Thread.NORM_PRIORITY);
            }
        }else if (vehicle instanceof Lorry) {
            if(floor.accessibleVehicles.contains(VehicleType.Lorry) && floor.prioritizedVehicles.contains(VehicleType.Lorry)) {
                addVehicleToFloor.setPriority(Thread.MAX_PRIORITY);
            } else {
                addVehicleToFloor.setPriority(Thread.NORM_PRIORITY);
            }
        }else if (vehicle instanceof MiniBus) {
            if(floor.accessibleVehicles.contains(VehicleType.MiniBus) && floor.prioritizedVehicles.contains(VehicleType.MiniBus)) {
                addVehicleToFloor.setPriority(Thread.MAX_PRIORITY);
            } else {
                addVehicleToFloor.setPriority(Thread.NORM_PRIORITY);
            }
        } else if (vehicle instanceof MiniLorry) {
            if(floor.accessibleVehicles.contains(VehicleType.MiniLorry) && floor.prioritizedVehicles.contains(VehicleType.MiniLorry)) {
                addVehicleToFloor.setPriority(Thread.MAX_PRIORITY);
            } else {
                addVehicleToFloor.setPriority(Thread.NORM_PRIORITY);
            }
        } else if (vehicle instanceof MotorBike) {
            if(floor.accessibleVehicles.contains(VehicleType.MotorBike) && floor.prioritizedVehicles.contains(VehicleType.MotorBike)) {
                addVehicleToFloor.setPriority(Thread.MAX_PRIORITY);
            } else {
                addVehicleToFloor.setPriority(Thread.NORM_PRIORITY);
            }
        }

        addVehicleToFloor.start();
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
