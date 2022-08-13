package Controllers;

import Models.Vehicles.*;
import Util.DateTime;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Simulator {
    static int vehicleCount = 100;
    static String[] carBrands = {"Toyota", "Tesla", "Hundai", "Suzuki", "Jeep", "Ferari", "Kia", "BMW"};

    public static void main(String[] args) {
        ArrayList<Vehicle> vehicles = new ArrayList<>();

        for (int i = 0; i < vehicleCount; i++) {
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
                    Vehicle car = new Car(noPlate, carBrand, "", dt);
                    vehicles.add(car);
                    break;
                case 2:
                    String brand = carBrands[new Random().nextInt(0, 7)];
                    Vehicle van = new Van(noPlate, brand, "", dt, 1500);
                    vehicles.add(van);
                    break;
                case 3:
                    String bikebrand = carBrands[new Random().nextInt(0, 7)];
                    Vehicle bike = new MotorBike(noPlate, bikebrand, "", dt, "150");
                    vehicles.add(bike);
                    break;
                case 4:
                    String busbrand = carBrands[new Random().nextInt(0, 7)];
                    Vehicle bus = new Bus(noPlate, busbrand, "", dt);
                    vehicles.add(bus);
                    break;
                case 5:
                    String lorryBrand = carBrands[new Random().nextInt(0, 7)];
                    Vehicle lorry = new Lorry(noPlate, lorryBrand, "", dt);
                    vehicles.add(lorry);
                    break;
                case 6:
                    String miniLorryBrand = carBrands[new Random().nextInt(0, 7)];
                    Vehicle miniLorry = new MiniLorry(noPlate, miniLorryBrand, "", dt);
                    vehicles.add(miniLorry);
                    break;
                case 7:
                    String miniBusBrand = carBrands[new Random().nextInt(0, 7)];
                    Vehicle miniBus = new MiniBus(noPlate, miniBusBrand, "", dt);
                    vehicles.add(miniBus);
                    break;
            }
        }
    }
}
