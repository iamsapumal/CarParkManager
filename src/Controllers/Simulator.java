package Controllers;

import Models.Vehicles.*;
import Util.DateTime;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;



public class Simulator {
    static int vehicleCount = 1500;
    static String[] carBrands = {"Toyota", "Tesla", "Hundai", "Suzuki", "Jeep", "Ferari", "Kia", "BMW"};
    static String[] busLorryBrands = {"TATA", "Layland", "Ford", "Volkswagen", "Toyata", "Daimler", "Kia", "Isuzu"};
    private static PettahMultiStoryCarParkManager pettahCarParkManager =  PettahMultiStoryCarParkManager.getInstance();
    public static void simulateVehicleAddition (){
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        PriorityBlockingQueue<Vehicle> carParkGateNorthGate1Queue = new PriorityBlockingQueue<>(vehicleCount,new VehicleComparator());
        PriorityBlockingQueue<Vehicle> carParkGateNorthGate2Queue = new PriorityBlockingQueue<>(vehicleCount,new VehicleComparator());
        PriorityBlockingQueue<Vehicle> carParkGateSouthGate1Queue = new PriorityBlockingQueue<>(vehicleCount,new VehicleComparator());
        PriorityBlockingQueue<Vehicle> carParkGateSouthGate2Queue = new PriorityBlockingQueue<>(vehicleCount,new VehicleComparator());

        for (int i = 0; i < vehicleCount; i++) {
            Vehicle vehicle = null;
            int vehicleType = new Random().nextInt(1, 7);

            int letter1 = 65 + (int) (Math.random() * (90 - 65));
            int letter2 = 65 + (int) (Math.random() * (90 - 65));
            int letter3 = 65 + (int) (Math.random() * (90 - 65));

            int number1 = (int) (Math.random() * 10);
            int number2 = (int) (Math.random() * 10);
            int number3 = (int) (Math.random() * 10);
            int number4 = (int) (Math.random() * 10);

            String noPlate = (char) (letter1) + ((char) (letter2)) +
                    ((char) (letter3)) + "-" + number1 + number2 + number3 + number4;

            Random rand = new Random();
            float r = rand.nextFloat();
            float g = rand.nextFloat();
            float b = rand.nextFloat();

            Color color = new Color(r, g, b);
            int doors = new Random().nextInt(1, 5);
            DateTime dateTime = new DateTime(2022, 8, 12, new Random().nextInt(0, 23), new Random().nextInt(0, 59), new Random().nextInt(0, 59));

            switch (vehicleType) {
                case 1:
                    String carBrand = carBrands[new Random().nextInt(0, 7)];
                    vehicle = new Car(noPlate, carBrand, "Car", dateTime, 1);
                    break;
                case 2:
                    String brand = carBrands[new Random().nextInt(0, 7)];
                    vehicle = new Van(noPlate, brand, "Van", dateTime, 1500,2);
                    break;
                case 3:
                    String bikebrand = carBrands[new Random().nextInt(0, 7)];
                    vehicle = new MotorBike(noPlate, bikebrand, "Bike", dateTime, "150",2);
                    break;
                case 4:
                    String busbrand = busLorryBrands[new Random().nextInt(0, 7)];
                    vehicle = new Bus(noPlate, busbrand, "Bus", dateTime,2);
                    break;
                case 5:
                    String lorryBrand = busLorryBrands[new Random().nextInt(0, 7)];
                    vehicle = new Lorry(noPlate, lorryBrand, "Lorry", dateTime,  2);
                    break;
                case 6:
                    String miniLorryBrand = busLorryBrands[new Random().nextInt(0, 7)];
                    vehicle = new MiniLorry(noPlate, miniLorryBrand, "Mini Lorry", dateTime, 2);
                    break;
                case 7:
                    String miniBusBrand = busLorryBrands[new Random().nextInt(0, 7)];
                    vehicle = new MiniBus(noPlate, miniBusBrand, "Mini Bus", dateTime, 2);
                    break;
            }

            int lane = new Random().nextInt(1, 4);
            switch (lane) {
                case 1:
                    carParkGateNorthGate1Queue.add(vehicle);
                    break;
                case 2:
                    carParkGateNorthGate2Queue.add(vehicle);
                    break;
                case 3:
                    carParkGateSouthGate1Queue.add(vehicle);
                    break;
                case 4:
                    carParkGateSouthGate2Queue.add(vehicle);
                    break;
            }

        }

        Thread thread1 = new Thread(() -> {
            System.out.println("Polling...");
            while (true) {
                try {
                    Vehicle v = carParkGateNorthGate1Queue.take();
                    pettahCarParkManager.addVehicle(v);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread1.setPriority(10);
        thread1.start();

        Thread thread2 = new Thread(() -> {
            System.out.println("Polling...");
            while (true) {
                try {
                    Vehicle v = carParkGateNorthGate2Queue.take();
                    pettahCarParkManager.addVehicle(v);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread2.setPriority(10);
        thread2.start();


        Thread thread3 = new Thread(() -> {
            System.out.println("Polling...");
            while (true) {
                try {
                    Vehicle v = carParkGateSouthGate1Queue.take();
                    pettahCarParkManager.addVehicle(v);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });


        thread3.setPriority(5);
        thread3.start();


        Thread thread4 = new Thread(() -> {
            System.out.println("Polling...");
            while (true) {
                try {
                    Vehicle v = carParkGateSouthGate2Queue.take();
                   // System.out.println("South 2 Polled: " + v);
                    pettahCarParkManager.addVehicle(v);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        thread4.setPriority(5);
        thread4.start();

    }
}
