package Controllers;

import Models.Park.Floor;
import Models.Park.Slot;
import Models.Vehicles.*;
import Util.DateTime;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PettahMultiStoryCarParkManager implements CarParkManager {
    public ArrayList<Vehicle> listOfVehicles = new ArrayList<Vehicle>();
    private static PettahMultiStoryCarParkManager instance = null;
    private int availableSlots = 20;
    private double chargePerHour = 50;
    private double addCharge = 75;
    private double maxCharge = 1250;
    private int addFromthisHour =3;

    private int floorCount =9;
    private int slotsForFloor = 60;

    private int bikeCountForSlot = 3;
    List<Floor> pettahMultiStoryCarPark = Collections.synchronizedList(new ArrayList<>());

    private FloorManager[] floorManagers = new FloorManager[floorCount];

        private PettahMultiStoryCarParkManager() {
            this.pettahMultiStoryCarPark = createCarParkVirtualModel();
            createFloorManagers();
    }

    public static List<Floor> createCarParkVirtualModel() {

        List<Floor> pettahMultiStoryCarPark = Collections.synchronizedList(new ArrayList<>());

        List<VehicleType> groundFloorPrioritisedVehicles= Collections.synchronizedList(Arrays.asList(
                VehicleType.Van
        ));
        List<VehicleType> groundFloorAccessibleVehicles = Collections.synchronizedList(Arrays.asList(
              VehicleType.Van
        ));
        Floor groundFloor = new Floor(groundFloorPrioritisedVehicles,groundFloorAccessibleVehicles, 60);


        List<VehicleType> firstFloorPrioritisedVehicles= Collections.synchronizedList(Arrays.asList(
                VehicleType.Car, VehicleType.Van
        ));
        List<VehicleType> firstFloorAccessibleVehicles = Collections.synchronizedList(Arrays.asList(
                VehicleType.Car, VehicleType.Van
        ));
        Floor firstFloor = new Floor(firstFloorPrioritisedVehicles,firstFloorAccessibleVehicles, 60);




        List<VehicleType> secondFloorPrioritisedVehicles= Collections.synchronizedList(Arrays.asList(
                VehicleType.Car, VehicleType.Van
        ));
        List<VehicleType> secondFloorAccessibleVehicles = Collections.synchronizedList(Arrays.asList(
                VehicleType.Car, VehicleType.Van
        ));
        Floor secondFloor = new Floor(secondFloorPrioritisedVehicles,secondFloorAccessibleVehicles, 60);




        List<VehicleType> thirdFloorPrioritisedVehicles = Collections.synchronizedList(Arrays.asList(
                VehicleType.Car, VehicleType.Van
        ));
        List<VehicleType> thirdFloorAccessibleVehicles = Collections.synchronizedList(Arrays.asList(
                VehicleType.Car, VehicleType.Van
        ));
        Floor thirdFloor = new Floor(groundFloorPrioritisedVehicles,groundFloorAccessibleVehicles, 60);


        List<VehicleType> fourthFloorPrioritisedVehicles= Collections.synchronizedList(Arrays.asList(
                VehicleType.Car, VehicleType.Van
        ));
        List<VehicleType> fourthFloorAccessibleVehicles = Collections.synchronizedList(Arrays.asList(
                VehicleType.Car, VehicleType.Van
        ));
        Floor fourthFloor = new Floor(fourthFloorPrioritisedVehicles,fourthFloorAccessibleVehicles, 60);


        List<VehicleType> fifthFloorPrioritisedVehicles= Collections.synchronizedList(Arrays.asList(
                VehicleType.Car, VehicleType.Van
        ));
        List<VehicleType> fifthFloorAccessibleVehicles = Collections.synchronizedList(Arrays.asList(
                VehicleType.Car, VehicleType.Van
        ));
        Floor fifthFloor = new Floor(fifthFloorPrioritisedVehicles,fifthFloorAccessibleVehicles, 60);


        List<VehicleType> sixthFloorPrioritisedVehicles= Collections.synchronizedList(Arrays.asList(
                VehicleType.Car, VehicleType.Van
        ));
        List<VehicleType> sixthFloorAccessibleVehicles = Collections.synchronizedList(Arrays.asList(
                VehicleType.Car, VehicleType.Van
        ));
        Floor sixthFloor = new Floor(sixthFloorPrioritisedVehicles,sixthFloorAccessibleVehicles, 60);



        List<VehicleType> upperThreeFloorsPrioritisedVehicles= Collections.synchronizedList(Arrays.asList(
                VehicleType.Car
        ));
        List<VehicleType> upperThreeAccessibleVehicles = Collections.synchronizedList(Arrays.asList(
                VehicleType.Car
        ));
        Floor upperThreeFloors = new Floor(upperThreeAccessibleVehicles,upperThreeFloorsPrioritisedVehicles, 180);


        pettahMultiStoryCarPark.add(groundFloor);
        pettahMultiStoryCarPark.add(firstFloor);
        pettahMultiStoryCarPark.add(secondFloor);
        pettahMultiStoryCarPark.add(thirdFloor);
        pettahMultiStoryCarPark.add(fourthFloor);
        pettahMultiStoryCarPark.add(fifthFloor);
        pettahMultiStoryCarPark.add(sixthFloor);
        pettahMultiStoryCarPark.add(upperThreeFloors);

        return pettahMultiStoryCarPark;
    }

    public void createFloorManagers() {
        for (int i = 0; i < this.pettahMultiStoryCarPark.size(); i++) {
            FloorManager floorManager = new FloorManager(i, this.pettahMultiStoryCarPark.get(i));
            this.floorManagers[i] = floorManager;
        }
    }

    //method which returns an object of same type
    public static PettahMultiStoryCarParkManager getInstance() {
        if(instance == null) {
            synchronized(PettahMultiStoryCarParkManager.class){
                if(instance==null) {
                    instance = new PettahMultiStoryCarParkManager();
                }
            }
        }
        return instance;
    }


    @Override
    public void addVehicle(Vehicle vehicle) {
        //check whether the vehicle is already parked or not
        for(Vehicle item : listOfVehicles) {
            if(item.equals(vehicle)) {
                System.out.println("This vehicle is already parked.");
                return;
            }
        }
        findAvailableFloorandAddVehicle(vehicle);
//        // Check whether there are sufficient space available for any vehicle to park
//        if(listOfVehicles.size()<20) {
//            if(obj instanceof Van ) {
//                if(listOfVehicle.size()<19) {
//                    listOfVehicle.add(obj);
//                    availableSlots -=2;
//                    System.out.println("Available slots : "+availableSlots);
//                    System.out.println("\n");
//                } else {
//                    System.out.println("Sorry..There are no slots available to park your Models.Resources.Vehicles.Van."+"\n");
//                }
//            }
//            if(obj instanceof MotorBike || obj instanceof Car) {
//                listOfVehicles.add(obj);
//                availableSlots --;
//                System.out.println("Available slots : "+availableSlots);
//            }
//        }else {
//            System.out.println("Sorry...There are not space availble for parking...");
//        }
    }

    @Override
    public void deleteVehicle(String IdPlate) {
        for(Vehicle item: listOfVehicles) {
            //Checking for a particular vehicle with its' plate ID
            if(item.getNoPlate().equals(IdPlate)) {
                System.out.println("Models.Resources.Vehicles.Vehicle Found.");
                if(item instanceof Van) {
                    availableSlots+=2;
                    System.out.println("Space cleared after deleting a Models.Resources.Vehicles.Van.\nAvailable Slots : "
                            +availableSlots);
                }else{
                    availableSlots++;
                    System.out.println("Space cleared after deleting a vehicle.\nAvailable Slots : "
                            +availableSlots);
                }
            }else {
                System.out.println("Models.Resources.Vehicles.Vehicle not found.");
            }
        }
    }


    private synchronized void findAvailableFloorandAddVehicle(Vehicle vehicle) {
        ThreadGroup threadGroup = new ThreadGroup(vehicle.getNoPlate());
        for (int i = 0; i < floorManagers.length; i++) {
            if(floorManagers[i].findAvailabilityByVehicle(vehicle)) {
                Floor floor = floorManagers[i].getFloor();
                Runnable assignVehicle = new Runnable() {
                    @Override
                    public void run() {
                        try{ if (!Thread.currentThread().isInterrupted()) {
                            if(vehicle instanceof Car) {
                                for (int i = 0; i < floor.getSlotList().length; i++) {
                                    if(!floor.getSlotList()[i].isOccupied() && !Thread.currentThread().isInterrupted() && !listOfVehicles.contains(vehicle)) {
                                        for(Vehicle item : listOfVehicles) {
                                            if(item.equals(vehicle)) {
                                                System.out.println("This vehicle is already parked.");
                                                return;
                                            }
                                        }
                                        String[] numPlates = {vehicle.getNoPlate()};
                                        floor.getSlotList()[i].setVehiclePlateNo(numPlates);
                                        floor.getSlotList()[i].setOccupied(true);
                                        floor.getSlotList()[i].setParkedVehicleType(VehicleType.Car.name());
                                        listOfVehicles.add(vehicle);
                                        System.out.println(vehicle.getNoPlate()+ " Parked at " + floor.getFloorNo()+ " Slot "  + floor.getSlotList()[i].getIndex() + " By " + Thread.currentThread().getName());
                                        slotAllocationFinished(threadGroup);
                                        break;
                                        // notifyAll();
                                    }
                                }
                            } else if (vehicle instanceof Van){
                                for (int i = 0; i < floor.getSlotList().length; i++) {
                                    if(!floor.getSlotList()[i].isOccupied()) {
                                        String[] numPlates = {vehicle.getNoPlate()};
                                        floor.getSlotList()[i].setVehiclePlateNo(numPlates);
                                        floor.getSlotList()[i].setParkedVehicleType(VehicleType.Van.name());
                                        // notifyAll();
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
                                            System.out.println(vehicle.getNoPlate()+ " Parked at " + floor.getFloorNo()+ " Slot "  + floor.getSlotList()[i].getIndex());
                                            slotAllocationFinished(threadGroup);
                                            isBikeSlotFound =true;
                                            floor.getSlotList()[i].setAvailableBikeSpaces(floor.getSlotList()[i].getAvailableBikeSpaces()-1);
                                            //  notifyAll();
                                        }
                                    }
                                }
                                if (!isBikeSlotFound) {
                                    for (int i = 0; i < floor.getSlotList().length; i++) {
                                        if(!floor.getSlotList()[i].isOccupied()) {
                                            String[] numPlates = {vehicle.getNoPlate()};
                                            floor.getSlotList()[i].setVehiclePlateNo(numPlates);
                                            floor.getSlotList()[i].setOccupied(true);
                                            System.out.println(vehicle.getNoPlate()+ " Parked at " + floor.getFloorNo()+ " Slot "  + floor.getSlotList()[i].getIndex());
                                            slotAllocationFinished(threadGroup);
                                            floor.getSlotList()[i].setParkedVehicleType(VehicleType.MotorBike.name());
                                            floor.getSlotList()[i].setAvailableBikeSpaces(floor.getSlotList()[i].getAvailableBikeSpaces()-1);
                                            isBikeSlotFound =true;
                                            //  notifyAll();
                                        }
                                    }
                                }

                            }
                            Thread.sleep(1000);
                        }

                        } catch (InterruptedException ie) {
                            System.out.println("");
                        } finally {

                        }
                    }
                };
                floorManagers[i].addVehicle(threadGroup, vehicle, assignVehicle);
            }
        }
    }

    private synchronized void slotAllocationFinished(ThreadGroup group) {
        group.interrupt();
        Thread.currentThread().interrupt();
        System.out.println(Thread.currentThread().getName() + " Interrupted");
        //latch.countDown();
    }


    @Override
    public void printcurrentVehicles() {
        Collections.sort(listOfVehicles, Collections.reverseOrder());
        for( Vehicle item:listOfVehicles) {
            if(item instanceof Van) {
                System.out.println("Models.Resources.Vehicles.Vehicle Type is a Models.Resources.Vehicles.Van");
            }else if(item instanceof MotorBike) {
                System.out.println("Models.Resources.Vehicles.Vehicle Type is a Models.Resources.Vehicles.MotorBike");
            }else {
                System.out.println("Models.Resources.Vehicles.Vehicle Type is a Models.Resources.Vehicles.Car.");
            }
            System.out.println("******************");
            System.out.println("ID Plate : "+item.getNoPlate());
            System.out.println("Entry Time : "
                    +item.getEntryDate().getHours()+":"+item.getEntryDate().getMinutes()
                    +":"+item.getEntryDate().getSeconds()+"-"+item.getEntryDate().getDate()
                    +"/"+item.getEntryDate().getMonth()+"/"+item.getEntryDate().getYear());
            System.out.println("\n");
        }
    }

    @Override
    public void printLongestPark() {
        //sort to the ascending order
        Collections.sort(listOfVehicles);
        System.out.println("The longest parked vehicle is : ");
        System.out.println("................................................");
        System.out.println("ID Plate : "+listOfVehicles.get(0).getNoPlate());
        if(listOfVehicles.get(0) instanceof Car) {
            System.out.println("Models.Resources.Vehicles.Vehicle Type is a Models.Resources.Vehicles.Car.");
        }else if(listOfVehicles.get(0) instanceof Van){
            System.out.println("Models.Resources.Vehicles.Vehicle Type is a Models.Resources.Vehicles.Van.");
        }else {
            System.out.println("Models.Resources.Vehicles.Vehicle Type is a Models.Resources.Vehicles.MotorBike.");
        }
        System.out.println("Parked Time : "+listOfVehicles.get(0).getEntryDate().getHours()
                +":"+listOfVehicles.get(0).getEntryDate().getMinutes()
                +":"+listOfVehicles.get(0).getEntryDate().getSeconds());
        System.out.println("Parked Date  : "+listOfVehicles.get(0).getEntryDate().getDate()
                +"/"+listOfVehicles.get(0).getEntryDate().getMonth()
                +"/"+listOfVehicles.get(0).getEntryDate().getYear());
    }

    @Override
    public void printLatestPark() {
        // sort to the descending order
        Collections.sort(listOfVehicles, Collections.reverseOrder());
        System.out.println("The latest parked vehicle is : ");
        System.out.println("..............................................");
        System.out.println("ID Plate : "+listOfVehicles.get(0).getNoPlate());
        if(listOfVehicles.get(0) instanceof Car) {
            System.out.println("Models.Resources.Vehicles.Vehicle Type is a Models.Resources.Vehicles.Car.");
        }else if(listOfVehicles.get(0) instanceof Van){
            System.out.println("Models.Resources.Vehicles.Vehicle Type is a Models.Resources.Vehicles.Van.");
        }else {
            System.out.println("Models.Resources.Vehicles.Vehicle Type is a Models.Resources.Vehicles.MotorBike.");
        }
        System.out.println("Parked Time : "+listOfVehicles.get(0).getEntryDate().getHours()
                +":"+listOfVehicles.get(0).getEntryDate().getMinutes()
                +":"+listOfVehicles.get(0).getEntryDate().getSeconds());
        System.out.println("Parked Date  : "+listOfVehicles.get(0).getEntryDate().getDate()
                +"/"+listOfVehicles.get(0).getEntryDate().getMonth()
                +"/"+listOfVehicles.get(0).getEntryDate().getYear());
    }


    @Override
    public void printVehicleByDay(DateTime givenDate) {
        for(Vehicle item:listOfVehicles) {
            if(givenDate.getYear()==item.getEntryDate().getYear() &&
                    givenDate.getMonth()==item.getEntryDate().getMonth() &&
                    givenDate.getDate() == item.getEntryDate().getDate()) {

                System.out.println("ID Plate : "+item.getNoPlate());

                System.out.println("Parked Date and Time : "+item.getEntryDate().getDate()+"/"+
                        item.getEntryDate().getMonth()+"/"+item.getEntryDate().getHours()+"-"
                        +item.getEntryDate().getHours()+":"+item.getEntryDate().getMinutes()
                        +":"+item.getEntryDate().getYear());

                if(item instanceof Van) {
                    System.out.println("Models.Resources.Vehicles.Vehicle Type is a Models.Resources.Vehicles.Van");
                }else if(item instanceof MotorBike) {
                    System.out.println("Models.Resources.Vehicles.Vehicle Type is a Motor Bike.");
                }else {
                    System.out.println("Models.Resources.Vehicles.Vehicle Type is a Models.Resources.Vehicles.Car.");
                }
                System.out.println("--------------------------");
                System.out.println("\n");
            }
        }
    }

    @Override
    public void printVehiclePercentage() {
        int numCars=0;
        int numBikes=0;
        int numVans=0;
        for(Vehicle item:listOfVehicles) {
            if(item instanceof Car) {
                numCars++;
            }else if(item instanceof MotorBike) {
                numBikes++;
            }else {
                numVans++;
            }
        }
        double carPercentage = (numCars/listOfVehicles.size())*100;
        double bikePercentage = (numBikes/listOfVehicles.size())*100;
        double vanPercentage = (numVans/listOfVehicles.size())*100;

        System.out.printf("Models.Resources.Vehicles.Car Percentage is : %.f ",carPercentage);
        System.out.printf("\nBike Percentage is : %.f ",bikePercentage);
        System.out.printf("\nModels.Resources.Vehicles.Van Percentage is : %.f ",vanPercentage);
        System.out.println("\n");
    }

    @Override
    public BigDecimal calculateChargers(String plateID, DateTime currentTime) {
        boolean found = false;
        BigDecimal charges = null;
        for(Vehicle item:listOfVehicles) {
            if(item.getNoPlate().equals(plateID)) {
                System.out.println("Models.Resources.Vehicles.Vehicle found.");
                //vehicle parked time
                System.out.println("Parked Time : "+item.getEntryDate().getDate()+"/"
                        +item.getEntryDate().getMonth()+"/"+item.getEntryDate().getDate()
                        +"-"+item.getEntryDate().getHours()+":"+item.getEntryDate().getMinutes()
                        +":"+item.getEntryDate().getSeconds());
                //making the charges
                found = true;
                DateTime entryDateTime = item.getEntryDate();
                int differenceInSeconds = currentTime.compareTo(entryDateTime);
                double differenceInHours = differenceInSeconds/(60.0*60.0);

                double dayCharge=0;
                double hourCharge = 0;
                double totalCost=0;
                double days = differenceInHours/24;

                if(days>1) {
                    dayCharge =maxCharge;
                }
                if (differenceInHours>=3) {
                    double additional = (differenceInHours-addFromthisHour) ;
                    hourCharge=(additional*addCharge)+(addFromthisHour *chargePerHour);
                    System.out.printf("hour Charge : %.2f",hourCharge);
                }else if(differenceInHours<1) {
                    hourCharge = chargePerHour;
                }else {
                    hourCharge=(differenceInHours * chargePerHour);
                }

                totalCost=dayCharge + hourCharge;
                BigDecimal vehicleCharge = new BigDecimal(totalCost);
                System.out.printf("Total charge for the vehicle is LKR %.2f", vehicleCharge);
                System.out.println("\n");
            }
        }
        if(!found) {
            System.out.println("Models.Resources.Vehicles.Vehicle not found\n");
        }
        return charges;
    }
}
