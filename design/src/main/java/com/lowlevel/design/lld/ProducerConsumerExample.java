package com.lowlevel.design.lld;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

public class ProducerConsumerExample {

    private static final int BUFFER_CAPACITY = 5; // The maximum number of items the buffer can hold
    private final Queue<Integer> buffer = new LinkedList<>(); // The shared buffer

    // --- Producer Thread Class ---
    class Producer implements Runnable {
        private final String name;

        public Producer(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            int itemProduced = 0;
            while (true) {
                synchronized (buffer) { // Acquire the lock on the shared object
                    // Wait if the buffer is full
                    if (buffer.size() == BUFFER_CAPACITY) {
                        try {
                            System.out.println(name + ": Buffer is FULL. Waiting for consumer to make space...");
                            buffer.wait(); // Release the lock and wait
                            System.out.println(name + ": Wait ENDED");
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            System.out.println(name + ": Interrupted while waiting.");
                            return;
                        }
                    }

                    // Produce an item
                    itemProduced = ThreadLocalRandom.current().nextInt(1, 100);
                    buffer.add(itemProduced);
                    System.out.println(name + ": Produced item: " + itemProduced + ". Buffer size: " + buffer.size());

                    // Notify waiting consumers (if any) that an item is available
                    buffer.notifyAll(); // Use notifyAll to wake up all waiting consumers
                }

                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(500, 1500)); // Simulate work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println(name + ": Interrupted during production.");
                    return;
                }
            }
        }
    }

    // --- Consumer Thread Class ---
    class Consumer implements Runnable {
        private final String name;

        public Consumer(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            int itemConsumed = 0;
            while (true) {
                synchronized (buffer) { // Acquire the lock on the shared object
                    // Wait if the buffer is empty
                    if (buffer.isEmpty()) {
                        try {
                            System.out.println(name + ": Buffer is EMPTY. Waiting for producer to add items...");
                            buffer.wait(); // Release the lock and wait
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                            System.out.println(name + ": Interrupted while waiting.");
                            return;
                        }
                    }

                    // Consume an item
                    itemConsumed = buffer.remove();
                    System.out.println(name + ": Consumed item: " + itemConsumed + ". Buffer size: " + buffer.size());

                    // Notify waiting producers (if any) that space is available
                    buffer.notify(); // Use notifyAll to wake up all waiting producers
                }

                try {
                    Thread.sleep(ThreadLocalRandom.current().nextInt(800, 2000)); // Simulate work
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println(name + ": Interrupted during consumption.");
                    return;
                }
            }
        }
    }

    public static void main(String[] args) {
        ProducerConsumerExample pc = new ProducerConsumerExample();

        // Create and start producer threads
        Thread producer1 = new Thread(pc.new Producer("Producer-1"), "Producer-1-Thread");
        Thread producer2 = new Thread(pc.new Producer("Producer-2"), "Producer-2-Thread");

        // Create and start consumer threads
        Thread consumer1 = new Thread(pc.new Consumer("Consumer-1"), "Consumer-1-Thread");

        producer1.start();
        producer2.start();
        consumer1.start();

        // Let them run for a while
        try {
            Thread.sleep(15000); // Run for 15 seconds
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Interrupt threads to stop them gracefully (optional)
        producer1.interrupt();
        producer2.interrupt();
        consumer1.interrupt();
//        consumer2.interrupt();

        System.out.println("\n--- Simulation Ended ---");
    }
}