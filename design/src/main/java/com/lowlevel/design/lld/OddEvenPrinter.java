package com.lowlevel.design.lld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class OddEvenPrinter {

    final Object lock = new Object();
    int count = 0;

    public void printOdd() {
        while (true) {
            synchronized (lock) {
                if (count % 2 == 0) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        System.out.println("Ignored the error");
                    }
                }
                System.out.println(count++ + "Printed by Odd Method");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                lock.notify();
            }
        }
    }

    public void printEven() {
        while (true) {
            synchronized (lock) {
                if (count % 2 == 1) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        System.out.println("Ignored the error in Even");
                    }
                }
                System.out.println(count++ + "Printed by Even Method");
                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(1,3000));
                    List<Vehicle> vehicles = new ArrayList<>();
                    Collections.sort(vehicles, (a,b)-> a.getVehicleId().compareTo(b.getVehicleId()));
                } catch (Exception ignored) {}
                lock.notify();
            }
        }
    }

    public static void main(String[] args) {
        OddEvenPrinter oddEvenPrinter = new OddEvenPrinter();
        Thread oddThread = new Thread(oddEvenPrinter::printOdd);
        Thread evenThread = new Thread(oddEvenPrinter::printEven);

        oddThread.start();
        evenThread.start();
    }
}
