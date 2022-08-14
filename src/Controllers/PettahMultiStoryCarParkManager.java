package Controllers;

import Models.Park.Floor;
import Models.Park.FloorNumber;
import Models.Resources.Lift;
import Models.Resources.Lifts;
import Models.Vehicles.*;
import Util.DateTime;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PettahMultiStoryCarParkManager implements CarParkManager {
    public static  ConcurrentLinkedQueue<Vehicle> listOfVehicles = new ConcurrentLinkedQueue();
    public static  List<Lift> LIFTS = new ArrayList<>();
    private static PettahMultiStoryCarParkManager instance = null;
    private int availableSlots = 20;
    private double chargePerHour = 50;
    private double addCharge = 75;
    private double maxCharge = 1250;
    private int addFromthisHour =3;

    private int floorCount = 9;
    private int slotsForFloor = 60;

    private final int LIFT_COUNT = 4;
    private Queue<Lifts>  queue = new LinkedList<Lifts>();

    private int bikeCountForSlot = 3;
    List<Floor> pettahMultiStoryCarPark;

    private FloorManager[] floorManagers = new FloorManager[floorCount];

    private PettahMultiStoryCarParkManager() {
        this.pettahMultiStoryCarPark = createCarParkVirtualModel(); // make it syncronize
        createFloorManagers();
    }

    public static void CreateLifts() {
        Lift  lift_1 = new Lift("LIFT_1", 1, false);
        Lift  lift_2 = new Lift("LIFT_2",2, false);
        Lift  lift_3 = new Lift( "LIFT_3",3, false);
        Lift  lift_4 = new Lift( "LIFT_4",4, false);

        LIFTS.add(lift_1);
        LIFTS.add(lift_2);
        LIFTS.add(lift_3);
        LIFTS.add(lift_4);
    }

    public static List<Floor> createCarParkVirtualModel() {

        CreateLifts();
        List<Floor> pettahMultiStoryCarPark = Collections.synchronizedList(new ArrayList<>());

        List<VehicleType> groundFloorPrioritisedVehicles= Collections.synchronizedList(Arrays.asList(
                VehicleType.Van
        ));
        List<VehicleType> groundFloorAccessibleVehicles = Collections.synchronizedList(Arrays.asList(
                VehicleType.Van
        ));
        Floor groundFloor = new Floor(0, groundFloorPrioritisedVehicles,groundFloorAccessibleVehicles, 60);


        List<VehicleType> firstFloorPrioritisedVehicles= Collections.synchronizedList(Arrays.asList(
                VehicleType.Car, VehicleType.Van
        ));
        List<VehicleType> firstFloorAccessibleVehicles = Collections.synchronizedList(Arrays.asList(
                VehicleType.Car, VehicleType.Van
        ));
        Floor firstFloor = new Floor(1, firstFloorPrioritisedVehicles,firstFloorAccessibleVehicles, 60);




        List<VehicleType> secondFloorPrioritisedVehicles= Collections.synchronizedList(Arrays.asList(
                VehicleType.Car, VehicleType.Van
        ));
        List<VehicleType> secondFloorAccessibleVehicles = Collections.synchronizedList(Arrays.asList(
                VehicleType.Car, VehicleType.Van
        ));
        Floor secondFloor = new Floor(2, secondFloorPrioritisedVehicles,secondFloorAccessibleVehicles, 60);




        List<VehicleType> thirdFloorPrioritisedVehicles = Collections.synchronizedList(Arrays.asList(
                VehicleType.Car, VehicleType.Van
        ));
        List<VehicleType> thirdFloorAccessibleVehicles = Collections.synchronizedList(Arrays.asList(
                VehicleType.Car, VehicleType.Van
        ));
        Floor thirdFloor = new Floor(3, groundFloorPrioritisedVehicles,groundFloorAccessibleVehicles, 60);


        List<VehicleType> fourthFloorPrioritisedVehicles= Collections.synchronizedList(Arrays.asList(
                VehicleType.Car, VehicleType.Van
        ));
        List<VehicleType> fourthFloorAccessibleVehicles = Collections.synchronizedList(Arrays.asList(
                VehicleType.Car, VehicleType.Van
        ));
        Floor fourthFloor = new Floor(4, fourthFloorPrioritisedVehicles,fourthFloorAccessibleVehicles, 60);


        List<VehicleType> fifthFloorPrioritisedVehicles= Collections.synchronizedList(Arrays.asList(
                VehicleType.Car, VehicleType.Van
        ));
        List<VehicleType> fifthFloorAccessibleVehicles = Collections.synchronizedList(Arrays.asList(
                VehicleType.Car
        ));
        Floor fifthFloor = new Floor(5, fifthFloorPrioritisedVehicles,fifthFloorAccessibleVehicles, 60);


        List<VehicleType> sixthFloorPrioritisedVehicles= Collections.synchronizedList(Arrays.asList(
                VehicleType.Car
        ));
        List<VehicleType> sixthFloorAccessibleVehicles = Collections.synchronizedList(Arrays.asList(
                VehicleType.Car
        ));
        Floor sixthFloor = new Floor(6, sixthFloorPrioritisedVehicles,sixthFloorAccessibleVehicles, 60);



        List<VehicleType> upperThreeFloorsPrioritisedVehicles= Collections.synchronizedList(Arrays.asList(
                VehicleType.Car
        ));
        List<VehicleType> upperThreeAccessibleVehicles = Collections.synchronizedList(Arrays.asList(
                VehicleType.Car
        ));
        Floor seventhFloor = new Floor(7, upperThreeAccessibleVehicles,upperThreeFloorsPrioritisedVehicles, 60);
        Floor eightFloor = new Floor(8, upperThreeAccessibleVehicles,upperThreeFloorsPrioritisedVehicles, 60);

        pettahMultiStoryCarPark.add(groundFloor);
        pettahMultiStoryCarPark.add(firstFloor);
        pettahMultiStoryCarPark.add(secondFloor);
        pettahMultiStoryCarPark.add(thirdFloor);
        pettahMultiStoryCarPark.add(fourthFloor);
        pettahMultiStoryCarPark.add(fifthFloor);
        pettahMultiStoryCarPark.add(sixthFloor);
        pettahMultiStoryCarPark.add(seventhFloor);
        pettahMultiStoryCarPark.add(eightFloor);

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
    public void addVehicle(Vehicle vehicle) throws InterruptedException {
        //check whether the vehicle is already parked or not
        int groundFloor = 0;
        ThreadGroup threadGroup = new ThreadGroup(vehicle.getNoPlate());
        Runnable assignVehicle = new Runnable() {
            boolean isVehicleParked = false;
            @Override
            public void run() {
                try {
                    for (Vehicle item : listOfVehicles) {
                        if (item.getNoPlate().equals(vehicle.getNoPlate())) {
                            System.out.println("Vehicle number" + vehicle.getNoPlate() +" is parked at the car park.");
                            isVehicleParked = true;
                            break;
                        }
                    }
                    if(isVehicleParked || Thread.currentThread().isInterrupted()) {

                    };

                    if (vehicle instanceof Car) {
                        tryAndAddVehicleToFloor(vehicle, FloorNumber.SEVENTH_FLOOR.getValue());
                    } else if (vehicle instanceof Van) {
                        tryAndAddVehicleToFloor(vehicle, FloorNumber.FIRST_FLOOR.getValue());
                    } else if (vehicle instanceof MiniBus) {
                        tryAndAddVehicleToFloor(vehicle, FloorNumber.GROUND_FLOOR.getValue());
                    } else if (vehicle instanceof MiniLorry) {
                        tryAndAddVehicleToFloor(vehicle, FloorNumber.GROUND_FLOOR.getValue());
                    } else if (vehicle instanceof Bus) {
                        tryAndAddVehicleToFloor(vehicle, FloorNumber.GROUND_FLOOR.getValue());
                    } else if (vehicle instanceof Lorry) {
                        tryAndAddVehicleToFloor(vehicle, FloorNumber.GROUND_FLOOR.getValue());
                    } else if (vehicle instanceof MotorBike) {
                        tryAndAddVehicleToFloor(vehicle, FloorNumber.FIRST_FLOOR.getValue());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                }
            }
        };
        final Lifts pc = Lifts.getInstance();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    pc.produce();
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        t1.start();
        Thread addVehicleToFloor = new Thread(threadGroup, assignVehicle, vehicle.getNoPlate());
        addVehicleToFloor.start();
        //   addVehicleToFloor.join();
        // t1.join();
    }

    public void tryAndAddVehicleToFloor(Vehicle vehicle, int floorNumber) {
        final Lifts pc = Lifts.getInstance();
        try {


            // Enable to use LIFTS with names
         /*   Integer liftNumber = 0;
            while (true) {
                liftNumber = tryAndOccupyALift();
                if(liftNumber > 0) {
                    System.out.println("Lift_"+liftNumber+" is occupied by vehicle number " + vehicle.getNoPlate());
                    Thread.sleep(5000);
                    releaseTheLift(liftNumber);
                    break;
                }
            } **/

            if(floorNumber > 6) {
                pc.consume();
            }

            int nextFloorNum = setThreadPriorityAndTryToAddVehicle(vehicle, pettahMultiStoryCarPark.get(floorNumber));

            if (nextFloorNum > 0) {
                tryAndAddVehicleToFloor(vehicle, nextFloorNum);

            } else if (nextFloorNum == -1) {
                // synchronized ()listOfVehicles.add(vehicle);
            } else if (nextFloorNum == -2) {
                System.out.println("No space available to park " +vehicle.getBrand() +" "+vehicle.getModel()+" | "+ vehicle.getNoPlate() + " in car park");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

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


    private synchronized int setThreadPriorityAndTryToAddVehicle(Vehicle vehicle, Floor floor) {

        if(vehicle instanceof Car) {
            if(floor.accessibleVehicles.contains(VehicleType.Car) && floor.prioritizedVehicles.contains(VehicleType.Car)){
                Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
            } else {
                Thread.currentThread().setPriority(Thread.NORM_PRIORITY);
            }
        } else if (vehicle instanceof Van) {
            if( floor.accessibleVehicles.contains(VehicleType.Van) && floor.prioritizedVehicles.contains(VehicleType.Van)){
                Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
            } else {
                Thread.currentThread().setPriority(Thread.NORM_PRIORITY);
            }
        } else if (vehicle instanceof Bus) {
            if(floor.accessibleVehicles.contains(VehicleType.Bus) && floor.prioritizedVehicles.contains(VehicleType.Bus)) {
                Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
            } else {
                Thread.currentThread().setPriority(Thread.NORM_PRIORITY);
            }
        } else if (vehicle instanceof Lorry) {
            if(floor.accessibleVehicles.contains(VehicleType.Lorry) && floor.prioritizedVehicles.contains(VehicleType.Lorry)) {
                Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
            } else {
                Thread.currentThread().setPriority(Thread.NORM_PRIORITY);
            }
        } else if (vehicle instanceof MiniBus) {
            if(floor.accessibleVehicles.contains(VehicleType.MiniBus) && floor.prioritizedVehicles.contains(VehicleType.MiniBus)) {
                Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
            } else {
                Thread.currentThread().setPriority(Thread.NORM_PRIORITY);
            }
        } else if (vehicle instanceof MiniLorry) {
            if(floor.accessibleVehicles.contains(VehicleType.MiniLorry) && floor.prioritizedVehicles.contains(VehicleType.MiniLorry)) {
                Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
            } else {
                Thread.currentThread().setPriority(Thread.NORM_PRIORITY);
            }
        } else if (vehicle instanceof MotorBike) {
            if(floor.accessibleVehicles.contains(VehicleType.MotorBike) && floor.prioritizedVehicles.contains(VehicleType.MotorBike)) {
                Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
            } else {
                Thread.currentThread().setPriority(Thread.NORM_PRIORITY);
            }
        }
        return floorManagers[floor.getFloorNo()].addVehicle(vehicle);
//            }
//        }
    }

    private synchronized void slotAllocationFinished(ThreadGroup group) {
        group.interrupt();
        Thread.currentThread().interrupt();
        System.out.println(Thread.currentThread().getName() + " Interrupted");
        //latch.countDown();
    }

    public Integer tryAndOccupyALift() {
        Lock lock = new ReentrantLock();
        Integer operationResult = 0;
        try {
            lock.lock();
            Iterator<Lift> lifts = LIFTS.iterator();
            while (lifts.hasNext()) {
                Lift lift = lifts.next();
                if(!lift.isOccupied()) {
                    operationResult = lift.getNumber();
                    lift.setOccupied(true);
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            return operationResult;
        }

    }

    public void releaseTheLift(Integer liftNumber) {
        Lock lock = new ReentrantLock();
        try {
            lock.lock();
            Iterator<Lift> lifts = LIFTS.iterator();
            while (lifts.hasNext()) {
                Lift lift = lifts.next();
                if(Objects.equals(lift.getNumber(), liftNumber)) {
                    lift.setOccupied(false);
                    System.out.println(lift.getName() + " is now available");
                    break;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


    @Override
    public void printcurrentVehicles() {
        //   Collections.sort(listOfVehicles, Collections.reverseOrder());
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
        //  Collections.sort(listOfVehicles);
        System.out.println("The longest parked vehicle is : ");
        System.out.println("................................................");
        System.out.println("ID Plate : "+listOfVehicles.peek().getNoPlate());
        if(listOfVehicles.peek() instanceof Car) {
            System.out.println("Models.Resources.Vehicles.Vehicle Type is a Models.Resources.Vehicles.Car.");
        }else if(listOfVehicles.peek() instanceof Van){
            System.out.println("Models.Resources.Vehicles.Vehicle Type is a Models.Resources.Vehicles.Van.");
        }else {
            System.out.println("Models.Resources.Vehicles.Vehicle Type is a Models.Resources.Vehicles.MotorBike.");
        }
        System.out.println("Parked Time : "+listOfVehicles.peek().getEntryDate().getHours()
                +":"+listOfVehicles.peek().getEntryDate().getMinutes()
                +":"+listOfVehicles.peek().getEntryDate().getSeconds());
        System.out.println("Parked Date  : "+listOfVehicles.peek().getEntryDate().getDate()
                +"/"+listOfVehicles.peek().getEntryDate().getMonth()
                +"/"+listOfVehicles.peek().getEntryDate().getYear());
    }

    @Override
    public void printLatestPark() {
        // sort to the descending order
        // Collections.sort(listOfVehicles, Collections.reverseOrder());
        System.out.println("The latest parked vehicle is : ");
        System.out.println("..............................................");
        System.out.println("ID Plate : "+listOfVehicles.peek().getNoPlate());
        if(listOfVehicles.peek() instanceof Car) {
            System.out.println("Models.Resources.Vehicles.Vehicle Type is a Models.Resources.Vehicles.Car.");
        }else if(listOfVehicles.peek() instanceof Van){
            System.out.println("Models.Resources.Vehicles.Vehicle Type is a Models.Resources.Vehicles.Van.");
        }else {
            System.out.println("Models.Resources.Vehicles.Vehicle Type is a Models.Resources.Vehicles.MotorBike.");
        }
        System.out.println("Parked Time : "+listOfVehicles.peek().getEntryDate().getHours()
                +":"+listOfVehicles.peek().getEntryDate().getMinutes()
                +":"+listOfVehicles.peek().getEntryDate().getSeconds());
        System.out.println("Parked Date  : "+listOfVehicles.peek().getEntryDate().getDate()
                +"/"+listOfVehicles.peek().getEntryDate().getMonth()
                +"/"+listOfVehicles.peek().getEntryDate().getYear());
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
