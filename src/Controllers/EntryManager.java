package Controllers;

import Models.Vehicles.Vehicle;

import java.util.ArrayList;
import java.util.concurrent.PriorityBlockingQueue;

public class EntryManager {
    java.util.concurrent.PriorityBlockingQueue<Vehicle> carParkPriorityBlockingQueue = new PriorityBlockingQueue<>();
    ArrayList<Vehicle> entryVehicleList = new ArrayList<>();


    public void carParkEntryManagement(){

        carParkPriorityBlockingQueue.drainTo(entryVehicleList,4);

    }
    }


