package Controllers;

import Models.Vehicles.Vehicle;

import java.util.concurrent.PriorityBlockingQueue;

public class EntryConsumer implements Runnable {

    PriorityBlockingQueue<Vehicle> carParkGateNorthGate1Que;
    PriorityBlockingQueue<Vehicle> carParkGateNorthGate2Que;
    PriorityBlockingQueue<Vehicle> carParkGateSouthGate1Que;
    PriorityBlockingQueue<Vehicle> carParkGateSouthGate2Que;

    public EntryConsumer(PriorityBlockingQueue<Vehicle> carParkGateNorthGate1Que, PriorityBlockingQueue<Vehicle> carParkGateNorthGate2Que, PriorityBlockingQueue<Vehicle> carParkGateSouthGate1Que, PriorityBlockingQueue<Vehicle> carParkGateSouthGate2Que) {
        this.carParkGateNorthGate1Que = carParkGateNorthGate1Que;
        this.carParkGateNorthGate2Que = carParkGateNorthGate2Que;
        this.carParkGateSouthGate1Que = carParkGateSouthGate1Que;
        this.carParkGateSouthGate2Que = carParkGateSouthGate2Que;
    }

    @Override public void run()
    {

    }
}