package Controllers;

import Models.Vehicles.*;
import Util.DateTime;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;



public class Simulator {
    static int vehicleCount = 1500;
    static String[] carBrands = {"Toyota", "Tesla", "Hundai", "Suzuki", "Jeep", "Ferari", "Kia", "BMW"};
    static String[] busLorryBrands = {"TATA", "Layland", "Ford", "Volkswagen", "Toyata", "Daimler", "Kia", "Isuzu"};
    private static PettahMultiStoryCarParkManager pettahCarParkManager =  PettahMultiStoryCarParkManager.getInstance();
    public static void main(String[] args) {
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        PriorityBlockingQueue<Vehicle> carParkGateNorthGate1Queue = new PriorityBlockingQueue<>(vehicleCount,new Compairing());
        PriorityBlockingQueue<Vehicle> carParkGateNorthGate2Queue = new PriorityBlockingQueue<>(vehicleCount,new Compairing());
        PriorityBlockingQueue<Vehicle> carParkGateSouthGate1Queue = new PriorityBlockingQueue<>(vehicleCount,new Compairing());
        PriorityBlockingQueue<Vehicle> carParkGateSouthGate2Queue = new PriorityBlockingQueue<>(vehicleCount,new Compairing());

        for (int i = 0; i < vehicleCount; i++) {
            Vehicle v = null;
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
            DateTime dt = new DateTime(2022, 07, 31, new Random().nextInt(0, 23), new Random().nextInt(0, 59), new Random().nextInt(0, 59));

            switch (vehicleType) {
                //car
                case 1:

                    String carBrand = carBrands[new Random().nextInt(0, 7)];
                    Vehicle car = new Car(noPlate, carBrand, "Car", dt, 1);
                    v = car;
                    break;
                case 2:
                    String brand = carBrands[new Random().nextInt(0, 7)];
                    Vehicle van = new Van(noPlate, brand, "Van", dt, 1500,2);
                    v = van;
                    break;
                case 3:
                    String bikebrand = carBrands[new Random().nextInt(0, 7)];
                    Vehicle bike = new MotorBike(noPlate, bikebrand, "Bike", dt, "150",2);
                    v = bike;
                    break;
                case 4:
                    String busbrand = busLorryBrands[new Random().nextInt(0, 7)];
                    Vehicle bus = new Bus(noPlate, busbrand, "Bus", dt,2);
                    v = bus;
                    break;
                case 5:
                    String lorryBrand = busLorryBrands[new Random().nextInt(0, 7)];
                    Vehicle lorry = new Lorry(noPlate, lorryBrand, "Lorry", dt,  2);
                    v = lorry;
                    break;
                case 6:
                    String miniLorryBrand = busLorryBrands[new Random().nextInt(0, 7)];
                    Vehicle miniLorry = new MiniLorry(noPlate, miniLorryBrand, "Mini Lorry", dt, 2);
                    v = miniLorry;
                    break;
                case 7:
                    String miniBusBrand = busLorryBrands[new Random().nextInt(0, 7)];
                    Vehicle miniBus = new MiniBus(noPlate, miniBusBrand, "Mini Bus", dt, 2);
                    v = miniBus;
                    break;
            }

            int lane = new Random().nextInt(1, 4);
            switch (lane) {
                case 1:
                    carParkGateNorthGate1Queue.add(v);
                    break;
                case 2:
                    carParkGateNorthGate2Queue.add(v);
                    break;
                case 3:
                    carParkGateSouthGate1Queue.add(v);
                    break;
                case 4:
                    carParkGateSouthGate2Queue.add(v);
                    break;
            }

        }

        Thread thread1 = new Thread(() -> {
            System.out.println("Polling...");
            while (true) {
                try {
                    Vehicle v = carParkGateNorthGate1Queue.take();
                    //System.out.println("North 1 Polled: " + v);
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
                    //System.out.println("North 2 Polled: " + v);
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
                   // System.out.println("South 1 Polled: " + v);
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
