package Models.Resources;

import java.util.LinkedList;

public class Lifts {
    private static Lifts instance = null;
    // Create a list shared by producer and consumer
    // Size of list is 2.
    LinkedList<String> list = new LinkedList<>();
    String[] lifts = {"Lift-1","Lift-2","Lift-3","Lift-4"};
    int capacity = 2;

    // Function called by producer thread
    public void produce() throws InterruptedException
    {
        int value = 0;
        while (true) {
            synchronized (this)
            {
                // producer thread waits while list
                // is full
                while (list.size() == capacity)  {
                    wait();
                }
                if(value == 2){
                    value = 1;
                } else {
                    value ++;
                }

                System.out.println("A LIFT became vacant");


                list.add(lifts[value]);
                notify();
                Thread.sleep(10);
            }
        }
    }

    // Function called by consumer thread
    public void consume() throws InterruptedException
    {
            synchronized (this)
            {

                while (list.size() == 0) {
                    wait();
                }

                String val = list.removeFirst();

                System.out.println("A LIFT is consuming ");
                notify();
                Thread.sleep(10);
            }
    }

    public static Lifts getInstance() {
        if(instance == null) {
            synchronized(Lifts.class){
                if(instance==null) {
                    instance = new Lifts();
                }
            }
        }
        return instance;
    }
}