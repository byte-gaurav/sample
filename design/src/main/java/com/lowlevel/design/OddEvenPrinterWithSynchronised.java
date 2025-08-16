package com.lowlevel.design;

public class OddEvenPrinterWithSynchronised { // Renamed the class

    private int counter = 1; // The shared counter
    private final int MAX_NUMBER; // The maximum number to print
    private final Object lock = new Object(); // The shared lock object for synchronized blocks
    // Removed Condition objects as they are not used with synchronized
    // private final Condition oddCondition = lock.newCondition();
    // private final Condition evenCondition = lock.newCondition();

    // Flag to determine whose turn it is
    private boolean isOddTurn = true;

    public OddEvenPrinterWithSynchronised(int maxNumber) { // Constructor updated with new class name
        this.MAX_NUMBER = maxNumber;
    }

    /**
     * Method for the thread that prints odd numbers.
     */
    public void printOdd() throws InterruptedException {
        while (counter <= MAX_NUMBER) {
            synchronized (lock) { // Acquire the intrinsic lock of the 'lock' object
                // If it's not the odd thread's turn, wait
                while (!isOddTurn && counter <= MAX_NUMBER) {
                    try {
                        lock.wait(); // Release lock and wait for notification
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return; // Exit if interrupted
                    }
                }

                // If we've reached the max number while waiting, exit
                if (counter > MAX_NUMBER) {
                    break;
                }
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + ": " + counter);
                counter++;
                isOddTurn = false; // It's now the even thread's turn
                lock.notify(); // Notify one waiting thread
            } // Lock is automatically released here
        }
        // After loop, signal the other thread one last time in case it's waiting
        // This is important to prevent deadlock if one thread finishes before the other
        synchronized (lock) {
            lock.notify(); // Final notify to ensure the other thread wakes up and terminates
        }
    }

    /**
     * Method for the thread that prints even numbers.
     */
    public void printEven() {
        while (counter <= MAX_NUMBER) {
            synchronized (lock) { // Acquire the intrinsic lock of the 'lock' object
                // If it's not the even thread's turn, wait
                while (isOddTurn && counter <= MAX_NUMBER) {
                    try {
                        lock.wait(); // Release lock and wait for notification
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        return; // Exit if interrupted
                    }
                }

                // If we've reached the max number while waiting, exit
                if (counter > MAX_NUMBER) {
                    break;
                }

                System.out.println(Thread.currentThread().getName() + ": " + counter);
                counter++;
                isOddTurn = true; // It's now the odd thread's turn
                lock.notify(); // Notify one waiting thread
            } // Lock is automatically released here
        }
        // After loop, signal the other thread one last time in case it's waiting
        synchronized (lock) {
            lock.notify(); // Final notify to ensure the other thread wakes up and terminates
        }
    }

    public static void main(String[] args) {
        int max = 20; // Print numbers up to 20
        OddEvenPrinterWithSynchronised printer = new OddEvenPrinterWithSynchronised(max); // Updated class name

        // Create and start the odd thread
        Thread oddThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        printer.printOdd();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }, "OddThread");
        // Create and start the even thread
        Thread evenThread = new Thread(printer::printEven, "EvenThread");

        oddThread.start();
        evenThread.start();



        try {
            oddThread.join(); // Wait for odd thread to finish
            evenThread.join(); // Wait for even thread to finish
        } catch (InterruptedException e) {
            System.err.println("Main thread interrupted: " + e.getMessage());
        }

        System.out.println("Printing complete!");
    }
}