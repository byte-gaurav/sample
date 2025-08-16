package com.lowlevel.design.lld;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.TreeSet;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProducerConsumerProblem {

    public static void main(String[] args) {
        Buffer buffer = new Buffer(5);
        Producer producer = new Producer(buffer);
        Consumer consumer = new Consumer(buffer);
        new Thread(producer).start();
        new Thread(consumer).start();
    }

}

class Buffer {
    private Queue<Integer> queue;
    private int capacity;
    private final Lock lock = new ReentrantLock();
    private final Condition notFull;
    private final Condition notEmpty;

    public Buffer(int capacity) {
        this.capacity = capacity;
        this.queue = new LinkedList<>();
        this.notFull = lock.newCondition();
        this.notEmpty = lock.newCondition();
    }

    public void produce(int item) throws InterruptedException {
        try {
            lock.lock();
            while (queue.size() == capacity) {
                notFull.await();
            }
            Object o = new Object();

            queue.add(item);
            System.out.println("Produced: " + item + ", Buffer size: " + queue.size());
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public int consume() throws InterruptedException {
        try {
            lock.lock();
            while (queue.isEmpty()) {
                notEmpty.await();
            }
            int item = queue.poll();
            System.out.println("Consumed: " + item + ", Buffer size: " + queue.size());
            notFull.signal();
            return item;
        } finally {
            lock.unlock();
        }
    }
}

class Producer implements Runnable {
    private Buffer buffer;
    private Random random = new Random();
    private int count = 0;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            while (true) {
                buffer.produce(count++);
                Thread.sleep(random.nextInt(1000));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

class Consumer implements Runnable {
    private Buffer buffer;
    private Random random = new Random();

    public Consumer(Buffer buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        try {
            while (true) {
                buffer.consume();
                Thread.sleep(random.nextInt(1000));
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}