import Controllers.ObjectCreator;
import Controllers.PettahMultiStoryCarParkManager;
import Controllers.Simulator;
import Models.Vehicles.Vehicle;
import Models.Vehicles.VehicleType;
import Util.DateTime;

import java.util.Scanner;

/**
 * Some codes of this class were taken from the base code
 */

class Main {

    private static PettahMultiStoryCarParkManager pettahCarParkManager =  PettahMultiStoryCarParkManager.getInstance();

    public static void main(String[] args) {
        while(true) {
            System.out.println("Select your Choice : ");
            System.out.println("-----------------------------");
            System.out.println("1. Add Vehicle : ");
            System.out.println("2. Delete Vehicle : ");
            System.out.println("3. Print the current available vehicle : ");
            System.out.println("4. Print statistics : ");
            System.out.println("5. Vehicles parked in a day : ");
            System.out.println("6. Charge for the parking : ");
            System.out.println("7. Percentage of vehicles : ");
            System.out.println("8. Hit '0' to Exit");
            System.out.println("___________________________________");
            System.out.println(">>>>");
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    try {
                        addVehicle();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    deleteVehicle();
                    break;
                case 3:
                    printVehicle();
                    break;
                case 4:
                    printStatistics();
                    break;
                case 5:
                    parkedByDay();
                    break;
                case 6:
                    calCharge();
                    break;
                case 7:
                    vehiclePercentage();
                    break;
                case 0:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid choice");
            }
        }
    }

    private static void parkedByDay() {
        //getting Date from the user
        Scanner sc=new Scanner(System.in);
        System.out.println("Enter a Date to Find (DD/MM/YYYY) : ");
        String checkThisTime = sc.next();
        String givenDateString = checkThisTime.split("-")[0];
        String[] dateString = givenDateString.split("/");
        DateTime givenDate=new DateTime(Integer.parseInt(dateString[2]),
                Integer.parseInt(dateString[1]),
                Integer.parseInt(dateString[0]),
0,0,0);
        pettahCarParkManager.printVehicleByDay(givenDate);
    }

    private static void calCharge() {
        //getting plate ID from the user
        System.out.println("Enter the Plate ID : ");
        Scanner sc = new Scanner(System.in);
        String plateID = sc.next();
        // getting Date from the user
        System.out.println("Enter the leaving time : (DD/MM/YYYY - HH:mm:ss)");
        String leavingTime =sc.next();
        String[] arr1= leavingTime.split("-");
        String[] dateString= arr1[0].split("/");
        String[] timeString=arr1[1].split(":");

        DateTime currentTime=new DateTime(Integer.parseInt(dateString[2]),
                Integer.parseInt(dateString[1]),
                Integer.parseInt(dateString[0]),
                Integer.parseInt(timeString[0]),
                Integer.parseInt(timeString[1]),
                Integer.parseInt(timeString[2]));
        pettahCarParkManager.calculateChargers(plateID, currentTime);
    }


    private static void printVehicle() {
        pettahCarParkManager.printcurrentVehicles();
    }

    private static void printStatistics() {
        pettahCarParkManager.printLongestPark();
        System.out.println("\n");
        pettahCarParkManager.printLatestPark();
    }

    private static void addVehicle() throws InterruptedException {
        //getting choice from the user
        System.out.println("Select your choice : ");
        System.out.println("-----------------------------");
        System.out.println("1. To add a Car.");
        System.out.println("2. To add a Motor Bike.");
        System.out.println("3. To add a Van.");
        System.out.println("4. To add a Bus.");
        System.out.println("5. To add a Lorry.");
        System.out.println("6. To add a MiniBus.");
        System.out.println("7. To add a MiniLorry.");
        System.out.println("8. To Simulate vehicle addition..");
        System.out.println("______________________________________");

        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        if(choice == 8) {
            simulateVehicleAddition();
        } else {
            VehicleType type = (choice == 1)?VehicleType.Car:(choice == 2)? VehicleType.MotorBike:(choice == 3)? VehicleType.Van:(choice == 4)? VehicleType.Bus:(choice == 5)? VehicleType.Lorry:(choice == 6)? VehicleType.MiniBus:(choice == 7)? VehicleType.MiniLorry: null;
            ObjectCreator creater = new ObjectCreator();
            assert type != null;
            Vehicle vehicle = creater.createVehicle(type);
            pettahCarParkManager.addVehicle(vehicle);
        }

    }

   public static void simulateVehicleAddition() {
       Simulator.simulateVehicleAddition();
    }

    private static void deleteVehicle() {
        Scanner input=new Scanner(System.in);
        System.out.println("Enter the Plate ID :");
        String plateID=input.next();
        pettahCarParkManager.deleteVehicle(plateID);
    }

    private static void vehiclePercentage() {
        pettahCarParkManager.printVehiclePercentage();
    }


}