package com.lowlevel.design;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class OddEvenPrinterWithLock {

    private int counter = 1; // The shared counter
    private final int MAX_NUMBER; // The maximum number to print
    private final Lock lock = new ReentrantLock(); // The shared lock
    // Conditions for signaling specific threads
    private final Condition oddCondition = lock.newCondition();
    private final Condition evenCondition = lock.newCondition();

    // Flag to determine whose turn it is
    private boolean isOddTurn = true;

    public OddEvenPrinterWithLock(int maxNumber) {
        this.MAX_NUMBER = maxNumber;
    }

    /**
     * Method for the thread that prints odd numbers.
     */
    public void printOdd() {
        while (counter <= MAX_NUMBER) {
            lock.lock(); // Acquire the lock
            try {
                // If it's not the odd thread's turn, wait
                while (!isOddTurn && counter <= MAX_NUMBER) {
                    try {
                        oddCondition.await(); // Release lock and wait for signal
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
                isOddTurn = false; // It's now the even thread's turn
                evenCondition.signal(); // Signal the even thread
            } finally {
                lock.unlock(); // Release the lock
            }
        }
        // After loop, signal the other thread one last time in case it's waiting
        // This is important to prevent deadlock if one thread finishes before the other
        lock.lock();
        try {
            evenCondition.signal();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Method for the thread that prints even numbers.
     */
    public void printEven() {
        while (counter <= MAX_NUMBER) {
            lock.lock(); // Acquire the lock
            try {
                // If it's not the even thread's turn, wait
                while (isOddTurn && counter <= MAX_NUMBER) {
                    try {
                        evenCondition.await(); // Release lock and wait for signal
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
                oddCondition.signal(); // Signal the odd thread
            } finally {
                lock.unlock(); // Release the lock
            }
        }
        // After loop, signal the other thread one last time in case it's waiting
        lock.lock();
        try {
            oddCondition.signal();
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) {
        int max = 20; // Print numbers up to 20
        OddEvenPrinterWithLock printer = new OddEvenPrinterWithLock(max);

        // Create and start the odd thread
        Thread oddThread = new Thread(printer::printOdd, "OddThread");
        // Create and start the even thread
        Thread evenThread = new Thread(printer::printEven, "EvenThread");

        oddThread.start();
        evenThread.start();


        System.out.println("Printing complete!");
    }
}
