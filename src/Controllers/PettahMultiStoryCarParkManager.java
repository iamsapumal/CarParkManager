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


    private  static final int totalSlotsInaFloor = 60;
    private static final int floorCount = 9;


    private final int LIFT_COUNT = 4;
    private Queue<Lifts>  queue = new LinkedList<Lifts>();

    private int availableSlots = totalSlotsInaFloor * floorCount;
    private double chargePerHour = 50;
    private double addCharge = 75;
    private double maxCharge = 1250;
    private int addFromthisHour =3;

    List<Floor> pettahMultiStoryCarPark;

    private FloorManager[] floorManagers = new FloorManager[floorCount];

    private PettahMultiStoryCarParkManager() {
        this.pettahMultiStoryCarPark = createCarParkVirtualModel(); // make it syncronize
        createFloorManagers();
    }

    /**
     * initialize lifsts
     */

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

    /**
     * Create car park virtual model
     * @return
     */
    public static List<Floor> createCarParkVirtualModel() {

        CreateLifts();
        List<Floor> pettahMultiStoryCarPark = Collections.synchronizedList(new ArrayList<>());

        List<VehicleType> groundFloorPrioritisedVehicles= Collections.synchronizedList(Arrays.asList(
                VehicleType.Van
        ));
        List<VehicleType> groundFloorAccessibleVehicles = Collections.synchronizedList(Arrays.asList(
                VehicleType.Van
        ));
        Floor groundFloor = new Floor(0, groundFloorPrioritisedVehicles,groundFloorAccessibleVehicles, totalSlotsInaFloor);


        List<VehicleType> firstFloorPrioritisedVehicles= Collections.synchronizedList(Arrays.asList(
                VehicleType.Car, VehicleType.Van
        ));
        List<VehicleType> firstFloorAccessibleVehicles = Collections.synchronizedList(Arrays.asList(
                VehicleType.Car, VehicleType.Van
        ));
        Floor firstFloor = new Floor(1, firstFloorPrioritisedVehicles,firstFloorAccessibleVehicles, totalSlotsInaFloor);


        List<VehicleType> secondFloorPrioritisedVehicles= Collections.synchronizedList(Arrays.asList(
                VehicleType.Car, VehicleType.Van
        ));
        List<VehicleType> secondFloorAccessibleVehicles = Collections.synchronizedList(Arrays.asList(
                VehicleType.Car, VehicleType.Van
        ));
        Floor secondFloor = new Floor(2, secondFloorPrioritisedVehicles,secondFloorAccessibleVehicles, totalSlotsInaFloor);


        List<VehicleType> thirdFloorPrioritisedVehicles = Collections.synchronizedList(Arrays.asList(
                VehicleType.Car, VehicleType.Van
        ));
        List<VehicleType> thirdFloorAccessibleVehicles = Collections.synchronizedList(Arrays.asList(
                VehicleType.Car, VehicleType.Van
        ));
        Floor thirdFloor = new Floor(3, groundFloorPrioritisedVehicles,groundFloorAccessibleVehicles, totalSlotsInaFloor);


        List<VehicleType> fourthFloorPrioritisedVehicles= Collections.synchronizedList(Arrays.asList(
                VehicleType.Car, VehicleType.Van
        ));
        List<VehicleType> fourthFloorAccessibleVehicles = Collections.synchronizedList(Arrays.asList(
                VehicleType.Car, VehicleType.Van
        ));
        Floor fourthFloor = new Floor(4, fourthFloorPrioritisedVehicles,fourthFloorAccessibleVehicles, totalSlotsInaFloor);


        List<VehicleType> fifthFloorPrioritisedVehicles= Collections.synchronizedList(Arrays.asList(
                VehicleType.Car, VehicleType.Van
        ));
        List<VehicleType> fifthFloorAccessibleVehicles = Collections.synchronizedList(Arrays.asList(
                VehicleType.Car
        ));
        Floor fifthFloor = new Floor(5, fifthFloorPrioritisedVehicles,fifthFloorAccessibleVehicles, totalSlotsInaFloor);


        List<VehicleType> sixthFloorPrioritisedVehicles= Collections.synchronizedList(Arrays.asList(
                VehicleType.Car
        ));
        List<VehicleType> sixthFloorAccessibleVehicles = Collections.synchronizedList(Arrays.asList(
                VehicleType.Car
        ));
        Floor sixthFloor = new Floor(6, sixthFloorPrioritisedVehicles,sixthFloorAccessibleVehicles, totalSlotsInaFloor);



        List<VehicleType> upperThreeFloorsPrioritisedVehicles= Collections.synchronizedList(Arrays.asList(
                VehicleType.Car
        ));
        List<VehicleType> upperThreeAccessibleVehicles = Collections.synchronizedList(Arrays.asList(
                VehicleType.Car
        ));
        Floor seventhFloor = new Floor(7, upperThreeAccessibleVehicles,upperThreeFloorsPrioritisedVehicles, totalSlotsInaFloor);
        Floor eightFloor = new Floor(8, upperThreeAccessibleVehicles,upperThreeFloorsPrioritisedVehicles, totalSlotsInaFloor);

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

    /**
     * createFloorManagers
     */
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

    /**
     * add Vehicles
     * @param vehicle
     * @throws InterruptedException
     */
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
        final Lifts liftSet = Lifts.getInstance();
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run()
            {
                try {
                    liftSet.produce();
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
        final Lifts liftSet = Lifts.getInstance();
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
            int selectedFloor = searchForAvailableSlotsInAFloor(vehicle, floorNumber);
            if (selectedFloor >= 0) {
                if(selectedFloor > 6) {
                    liftSet.consume();
                }
                boolean isVehicleParked = setThreadPriorityAndTryToAddVehicle(vehicle, pettahMultiStoryCarPark.get(selectedFloor));

                if (!isVehicleParked) {
                    int nextPrioritizedFloor = getNextPrioritizedFloorForVehicle(vehicle, floorNumber);
                    if (nextPrioritizedFloor >= 0) {
                        tryAndAddVehicleToFloor(vehicle, nextPrioritizedFloor);
                    } else  {
                        System.out.println("No space available to park " +vehicle.getBrand() +" "+vehicle.getModel()+" | "+ vehicle.getNoPlate() + " in car park");
                    }
                } else {
                    updateTotalAvailableCount();
                }
            } else if (selectedFloor == -1) {
                System.out.println("No space available to park " +vehicle.getBrand() +" "+vehicle.getModel()+" | "+ vehicle.getNoPlate() + " in car park");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * updateTotalAvailableCount
     */
    public void updateTotalAvailableCount() {
        availableSlots = availableSlots - listOfVehicles.size();
    }

    public int searchForAvailableSlotsInAFloor(Vehicle vehicle, int floorNumber) {

        boolean foundASlotInFloor = floorManagers[floorNumber].findAvailabilityByVehicle(vehicle);
        if(!foundASlotInFloor){
            int nextAvailableFloor = getNextPrioritizedFloorForVehicle(vehicle, floorNumber);
            if(nextAvailableFloor>= 0) {
                searchForAvailableSlotsInAFloor(vehicle, nextAvailableFloor);
            }
        } else {
            return floorNumber;
        }
        return -1;
    }

    /**
     * get Next Prioritized Floor For Vehicle
     * @param vehicle
     * @param floorLevel
     * @return
     */
    public static int getNextPrioritizedFloorForVehicle(Vehicle vehicle, int floorLevel) {
            int operationResult = -4;
                if (!Thread.currentThread().isInterrupted()) {
                    if(vehicle instanceof Car) {
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
                            }  else if (floorLevel == 6) {
                                operationResult = 0;
                            } else {
                                operationResult = -2;
                            }
                    } else if (vehicle instanceof Van){
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
                            }  else if (floorLevel == 6) {
                                operationResult = 0;
                            } else {
                                operationResult = -2;
                            }
                    } else if (vehicle instanceof Bus){

                        if(floorLevel == 0) {
                            operationResult = -2;
                        }
                    } else if (vehicle instanceof Lorry){
                        if(floorLevel == 0) {
                            operationResult = -2;
                        }
//
                    } else if (vehicle instanceof MiniBus){
                        if(floorLevel == 0) {
                            operationResult = -2;
                        }
                    } else if (vehicle instanceof MiniLorry){
                        if(floorLevel == 0) {
                            operationResult = -2;
                        }
                    } else if (vehicle instanceof MotorBike) {
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
                            }else if (floorLevel == 0) {
                                operationResult = 6;
                            }  else {
                                operationResult = -2;
                            }
                    }
                }
            return operationResult;
    }


    /**
     * deleteVehicle
     * @param IdPlate
     */
    @Override
    public void deleteVehicle(String IdPlate) {
        boolean isVehicleFound = false;
        for(Vehicle item: listOfVehicles) {
            //Checking for a particular vehicle with its' plate ID
            if(item.getNoPlate().equals(IdPlate)) {
                System.out.println(" Vehicles Found");
                isVehicleFound = true;
                if(item instanceof Car) {
                    availableSlots += 1;
                    System.out.println("Space cleared after deleting a  Car.\nAvailable Slots : "
                            + availableSlots);
                    if(item.getParkedFloorNumber() >= 0 && item.getParkedFloorNumber() <= 8 && item.getParkedSlotNumber() >=0 && item.getParkedSlotNumber() <= 59) {
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()].setOccupied(false);
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()].setVehiclePlateNo("");
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()].setParkedVehicleType("");
                    }

                } else if(item instanceof Van) {
                    availableSlots += 2;
                    System.out.println("Space cleared after deleting a  Van.\nAvailable Slots : "
                            + availableSlots);
                    if(item.getParkedFloorNumber() >= 0 && item.getParkedFloorNumber() <= 8 && item.getParkedSlotNumber() >=0 && item.getParkedSlotNumber() <= 59 && item.getParkedSlotNumber()+1 <= 59) {
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()].setOccupied(false);
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()].setVehiclePlateNo("");
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()].setParkedVehicleType("");

                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+1].setOccupied(false);
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+1].setVehiclePlateNo("");
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+1].setParkedVehicleType("");
                    }  else {
                        System.out.println("Error occurred in clearing the vehicle space");
                    }
                }  else if(item instanceof Bus) {
                    availableSlots += 5;
                    System.out.println("Space cleared after deleting a  Bus.\nAvailable Slots : "
                            + availableSlots);
                    if(item.getParkedFloorNumber() >= 0 && item.getParkedFloorNumber() <= 8 && item.getParkedSlotNumber() >=0 && item.getParkedSlotNumber() <= 59 && item.getParkedSlotNumber()+1 <= 59 && item.getParkedSlotNumber()+2 <= 59 && item.getParkedSlotNumber()+3 <= 59 && item.getParkedSlotNumber()+4 <= 59) {
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()].setOccupied(false);
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()].setVehiclePlateNo("");
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()].setParkedVehicleType("");

                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+1].setOccupied(false);
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+1].setVehiclePlateNo("");
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+1].setParkedVehicleType("");

                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+2].setOccupied(false);
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+2].setVehiclePlateNo("");
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+2].setParkedVehicleType("");

                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+3].setOccupied(false);
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+3].setVehiclePlateNo("");
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+3].setParkedVehicleType("");

                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+4].setOccupied(false);
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+4].setVehiclePlateNo("");
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+4].setParkedVehicleType("");
                    }  else {
                        System.out.println("Error occurred in clearing the vehicle space");
                    }
                }    else if(item instanceof Lorry) {
                    availableSlots += 5;
                    System.out.println("Space cleared after deleting a  Lorry.\nAvailable Slots : "
                            + availableSlots);
                    if(item.getParkedFloorNumber() >= 0 && item.getParkedFloorNumber() <= 8 && item.getParkedSlotNumber() >=0 && item.getParkedSlotNumber() <= 59 && item.getParkedSlotNumber()+1 <= 59 && item.getParkedSlotNumber()+2 <= 59 && item.getParkedSlotNumber()+3 <= 59 && item.getParkedSlotNumber()+4 <= 59) {
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()].setOccupied(false);
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()].setVehiclePlateNo("");
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()].setParkedVehicleType("");

                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+1].setOccupied(false);
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+1].setVehiclePlateNo("");
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+1].setParkedVehicleType("");

                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+2].setOccupied(false);
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+2].setVehiclePlateNo("");
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+2].setParkedVehicleType("");

                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+3].setOccupied(false);
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+3].setVehiclePlateNo("");
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+3].setParkedVehicleType("");

                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+4].setOccupied(false);
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+4].setVehiclePlateNo("");
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+4].setParkedVehicleType("");
                    } else {
                        System.out.println("Error occurred in clearing the vehicle space");
                    }
                }    else if(item instanceof MiniLorry) {
                    availableSlots += 3;
                    System.out.println("Space cleared after deleting a  MiniLorry.\nAvailable Slots : "
                            +availableSlots);
                    if(item.getParkedFloorNumber() >= 0 && item.getParkedFloorNumber() <= 8 && item.getParkedSlotNumber() >=0 && item.getParkedSlotNumber() <= 59 && item.getParkedSlotNumber()+1 <= 59 && item.getParkedSlotNumber()+2 <= 59) {
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()].setOccupied(false);
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()].setVehiclePlateNo("");
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()].setParkedVehicleType("");

                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+1].setOccupied(false);
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+1].setVehiclePlateNo("");
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+1].setParkedVehicleType("");

                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+2].setOccupied(false);
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+2].setVehiclePlateNo("");
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+2].setParkedVehicleType("");
                    }else {
                        System.out.println("Error occurred in clearing the vehicle space");
                    }
                }    else if(item instanceof MiniBus) {
                    availableSlots+=3;
                    System.out.println("Space cleared after deleting a  MiniBus.\nAvailable Slots : "
                            + availableSlots);
                    if(item.getParkedFloorNumber() >= 0 && item.getParkedFloorNumber() <= 8 && item.getParkedSlotNumber() >=0 && item.getParkedSlotNumber() <= 59 && item.getParkedSlotNumber()+1 <= 59 && item.getParkedSlotNumber()+2 <= 59) {
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()].setOccupied(false);
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()].setVehiclePlateNo("");
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()].setParkedVehicleType("");

                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+1].setOccupied(false);
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+1].setVehiclePlateNo("");
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+1].setParkedVehicleType("");

                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+2].setOccupied(false);
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+2].setVehiclePlateNo("");
                        pettahMultiStoryCarPark.get(item.getParkedFloorNumber()).getSlotList()[item.getParkedSlotNumber()+2].setParkedVehicleType("");
                    }else {
                        System.out.println("Error occurred in clearing the vehicle space");
                    }
                } else {
                    availableSlots++;
                    System.out.println("Space cleared after deleting a vehicle.\nAvailable Slots : "
                            + availableSlots);
                }
            }
        }
        if (!isVehicleFound) {
            System.out.println("Vehicle not found");
        }
    }

    /**
     * set Thread Priority as required a nd Try To Add Vehicle
     * @param vehicle
     * @param floor
     * @return
     */
    private synchronized boolean setThreadPriorityAndTryToAddVehicle(Vehicle vehicle, Floor floor) {

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
    }

    /**
     * slotAllocationFinished
     * @param group
     */
    private synchronized void slotAllocationFinished(ThreadGroup group) {
        group.interrupt();
        Thread.currentThread().interrupt();
        //System.out.println(Thread.currentThread().getName() + " Interrupted");
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


    /**
     * printcurrentVehicles
     */
    @Override
    public void printcurrentVehicles() {
        //   Collections.sort(listOfVehicles, Collections.reverseOrder());
        for( Vehicle item:listOfVehicles) {
            if(item instanceof Van) {
                System.out.println(" Vehicle Type is a  Van");
            }else if(item instanceof MotorBike) {
                System.out.println(" Vehicle Type is a  MotorBike");
            }else {
                System.out.println(" Vehicle Type is a  Car.");
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

    /**
     * printLongestPark
     */
    @Override
    public void printLongestPark() {
        //sort to the ascending order
        //  Collections.sort(listOfVehicles);
        System.out.println("The longest parked vehicle is : ");
        System.out.println("................................................");
        System.out.println("ID Plate : "+listOfVehicles.peek().getNoPlate());

        if(listOfVehicles.peek() instanceof Car) {
            System.out.println(" Vehicle Type is a  Car.");
        } else if(listOfVehicles.peek() instanceof MotorBike) {
            System.out.println(" Vehicle Type is a  MotorBike.");
        }else if(listOfVehicles.peek() instanceof Van){
            System.out.println(" Vehicle Type is a  Van.");
        } else if(listOfVehicles.peek() instanceof MiniBus) {
            System.out.println(" Vehicle Type is a  MiniBus.");
        } else if(listOfVehicles.peek() instanceof MiniLorry) {
            System.out.println(" Vehicle Type is a  MiniLorry.");
        } else if(listOfVehicles.peek() instanceof Bus) {
            System.out.println(" Vehicle Type is a  Bus.");
        } else if(listOfVehicles.peek() instanceof Lorry) {
            System.out.println(" Vehicle Type is a  Lorry.");
        } else {
            System.out.println(" Vehicle Type Unidentified.");
        }

        System.out.println("Parked Time : "+listOfVehicles.peek().getEntryDate().getHours()
                +":"+listOfVehicles.peek().getEntryDate().getMinutes()
                +":"+listOfVehicles.peek().getEntryDate().getSeconds());
        System.out.println("Parked Date  : "+listOfVehicles.peek().getEntryDate().getDate()
                +"/"+listOfVehicles.peek().getEntryDate().getMonth()
                +"/"+listOfVehicles.peek().getEntryDate().getYear());
    }


    /**
     * printLatestPark
     */
    @Override
    public void printLatestPark() {
        // sort to the descending order
        // Collections.sort(listOfVehicles, Collections.reverseOrder());
        System.out.println("The latest parked vehicle is : ");
        System.out.println("..............................................");
        System.out.println("ID Plate : "+listOfVehicles.peek().getNoPlate());
        if(listOfVehicles.peek() instanceof Car) {
            System.out.println(" Vehicle Type is a  Car.");
        } else if(listOfVehicles.peek() instanceof MotorBike) {
            System.out.println(" Vehicle Type is a  MotorBike.");
        }else if(listOfVehicles.peek() instanceof Van){
            System.out.println(" Vehicle Type is a  Van.");
        } else if(listOfVehicles.peek() instanceof MiniBus) {
            System.out.println(" Vehicle Type is a  MiniBus.");
        } else if(listOfVehicles.peek() instanceof MiniLorry) {
            System.out.println(" Vehicle Type is a  MiniLorry.");
        } else if(listOfVehicles.peek() instanceof Bus) {
            System.out.println(" Vehicle Type is a  Bus.");
        } else if(listOfVehicles.peek() instanceof Lorry) {
            System.out.println(" Vehicle Type is a  Lorry.");
        } else {
            System.out.println(" Vehicle Type Unidentified.");
        }
        System.out.println("Parked Time : "+listOfVehicles.peek().getEntryDate().getHours()
                +":"+listOfVehicles.peek().getEntryDate().getMinutes()
                +":"+listOfVehicles.peek().getEntryDate().getSeconds());
        System.out.println("Parked Date  : "+listOfVehicles.peek().getEntryDate().getDate()
                +"/"+listOfVehicles.peek().getEntryDate().getMonth()
                +"/"+listOfVehicles.peek().getEntryDate().getYear());
    }

    /**
     * printVehicleByDay
     * @param givenDate
     */
    @Override
    public void printVehicleByDay(DateTime givenDate) {
        for(Vehicle item:listOfVehicles) {
            if(givenDate.getYear()==item.getEntryDate().getYear() &&
                    givenDate.getMonth()==item.getEntryDate().getMonth() &&
                    givenDate.getDate() == item.getEntryDate().getDate()) {

                System.out.println("ID Plate : "+ item.getNoPlate());

                System.out.println("Parked Date and Time : "+item.getEntryDate().getDate()+"/"+
                        item.getEntryDate().getMonth()+"/"+item.getEntryDate().getYear()+"-"
                        +item.getEntryDate().getHours()+":"+item.getEntryDate().getMinutes()
                        +":"+item.getEntryDate().getSeconds());
                if (item instanceof MotorBike) {
                    System.out.println("Vehicle Type is a MotorBike.");
                } else if (item instanceof Lorry) {
                    System.out.println("Vehicle Type is a Lorry.");
                } else if (item instanceof Bus) {
                    System.out.println("Vehicle Type is a Bus.");
                } else if (item instanceof MiniBus) {
                    System.out.println("Vehicle Type is a MiniBus.");
                } else if (item instanceof MiniLorry) {
                    System.out.println("Vehicle Type is a MiniLorry.");
                } else if(item instanceof Van) {
                    System.out.println(" Vehicle Type is a  Van");
                }else {
                    System.out.println(" Vehicle Type is a  Car.");
                }
                System.out.println("--------------------------");
                System.out.println("\n");
            }
        }
    }

    /**
     * printVehiclePercentage
     */
    @Override
    public void printVehiclePercentage() {
        double numCars=0;
        double numBikes=0;
        double numVans=0;
        double numMiniLorries = 0;
        double numMiniBuses = 0;
        double numBuses = 0;
        double numLorries = 0;

        double totalSize = listOfVehicles.size();

        for(Vehicle item:listOfVehicles) {
            if(item instanceof Car) {
                numCars++;
            }else if(item instanceof MotorBike) {
                numBikes++;
            }  else if (item instanceof Van) {
                numVans++;
            } else if (item instanceof MiniBus) {
                numMiniBuses++;
            } else if (item instanceof MiniLorry) {
                numMiniLorries++;
            }  else if (item instanceof Lorry) {
                numLorries++;
            } else if (item instanceof Bus) {
                numBuses++;
            }
        }

        double carPercentage = (numCars/totalSize)*100;
        double bikePercentage = (numBikes/totalSize)*100;
        double vanPercentage = (numVans/totalSize)*100;
        double miniBusPercentage = (numMiniBuses /totalSize) * 100;
        double miniLorryPercentage = (numMiniLorries / totalSize) * 100;
        double busPercentage = (numBuses / totalSize) * 100;
        double lorryPercentage = (numLorries / totalSize) * 100;

        System.out.println("Car Percentage is : " + carPercentage + " %");
        System.out.println("\nBike Percentage is : " + bikePercentage  + " %");
        System.out.println("\n Van Percentage is : " + vanPercentage  + " %");
        System.out.println("\nMini Bus Percentage is : " + miniBusPercentage  + " %");
        System.out.println("\nMini Lorry Percentage is : " + miniLorryPercentage  + " %");
        System.out.println("\nBus Percentage is :  " + busPercentage  + " %");
        System.out.println("\nLorry Percentage is : " + lorryPercentage  + " %");

        System.out.println("\n");
    }

    /**
     * calculateChargers
     * @param plateID
     * @param currentTime
     */
    @Override
    public void calculateChargers(String plateID, DateTime currentTime) {
        boolean found = false;
        BigDecimal charges = null;
        for(Vehicle item:listOfVehicles) {
            if(item.getNoPlate().equals(plateID)) {
                System.out.println(" Vehicle found.");
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
                double totalCostForaSlot =0;
                double totalCost = 0;
                double days = differenceInHours/24;

                if(days>1) {
                    dayCharge =maxCharge;
                }
                if (differenceInHours>=3) {
                    double additional = (differenceInHours-addFromthisHour) ;
                    hourCharge=(additional*addCharge)+(addFromthisHour *chargePerHour);
                    System.out.printf("hour Charge : %.2f",hourCharge);
                } else if(differenceInHours<1) {
                    hourCharge = chargePerHour;
                } else {
                    hourCharge=(differenceInHours * chargePerHour);
                }

                totalCostForaSlot = dayCharge + hourCharge;

                if (item instanceof Car) {
                    totalCost = totalCostForaSlot;
                }  else if (item instanceof MotorBike) {
                    totalCost = totalCostForaSlot / 3;
                } else if (item instanceof Van) {
                    totalCost = totalCostForaSlot * 2;
                } else if (item instanceof MiniBus) {
                    totalCost = totalCostForaSlot * 3;
                } else if (item instanceof MiniLorry) {
                    totalCost = totalCostForaSlot * 3;
                }  else if (item instanceof Bus) {
                    totalCost = totalCostForaSlot * 5;
                } else if (item instanceof Lorry) {
                    totalCost = totalCostForaSlot * 5;
                }

                BigDecimal vehicleCharge = new BigDecimal(totalCost);

                System.out.printf("Total charge for the vehicle is LKR %.2f", vehicleCharge);
                System.out.println("\n");
            }
        }
        if(!found) {
            System.out.println("Vehicle not found\n");
        }
    }
}
