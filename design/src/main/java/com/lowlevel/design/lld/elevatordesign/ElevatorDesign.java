package com.lowlevel.design.lld.elevatordesign;

import java.util.*;

public class ElevatorDesign {
    private int currentFloor = 0;
    private Direction direction = Direction.IDLE;

    // Min-heap for upward moves, max-heap for downward

    private PriorityQueue<Request> upQueue = new PriorityQueue<>((a,b)->a.targetFloor - b.targetFloor);
    private PriorityQueue<Request> downQueue = new PriorityQueue<>((a,b)->b.targetFloor - a.targetFloor);

    public void sendUpRequest(Request req) {
        if (req.location == Location.OUTSIDE) {
            // first go to pick-up floor
            upQueue.offer(new Request(req.currentFloor, req.currentFloor, Direction.UP, Location.OUTSIDE));
        }
        upQueue.offer(req);
        System.out.printf("Added UP request to floor %d (from %s)%n", req.targetFloor, req.location);
    }

    public void sendDownRequest(Request req) {
        if (req.location == Location.OUTSIDE) {
            downQueue.offer(new Request(req.currentFloor, req.currentFloor, Direction.DOWN, Location.OUTSIDE));
        }
        downQueue.offer(req);
        System.out.printf("Added DOWN request to floor %d (from %s)%n", req.targetFloor, req.location);
    }

    public void run() {
        while (!upQueue.isEmpty() || !downQueue.isEmpty()) {
            if (direction == Direction.UP || direction == Direction.IDLE) {
                processUp();
                processDown();
            } else {
                processDown();
                processUp();
            }
        }
        direction = Direction.IDLE;
        System.out.println("All requests completed.");
    }

    private void processUp() {
        while (!upQueue.isEmpty()) {
            Request r = upQueue.poll();
            // simulate movement and door operations
            System.out.printf("Moving from floor %d to %d (UP)%n", currentFloor, r.targetFloor);
            currentFloor = r.targetFloor;
            System.out.printf("Stopped at floor %d%n", currentFloor);
        }
        if (!downQueue.isEmpty()) direction = Direction.DOWN;
        else direction = Direction.IDLE;
    }

    private void processDown() {
        while (!downQueue.isEmpty()) {
            Request r = downQueue.poll();
            System.out.printf("Moving from floor %d to %d (DOWN)%n", currentFloor, r.targetFloor);
            currentFloor = r.targetFloor;
            System.out.printf("Stopped at floor %d%n", currentFloor);
        }
        if (!upQueue.isEmpty()) direction = Direction.UP;
        else direction = Direction.IDLE;
    }

    // Main to test the system
    public static void main(String[] args) {
        ElevatorDesign elevator = new ElevatorDesign();

        // Two internal passengers requesting floors 5 and 3
        elevator.sendUpRequest(new Request(elevator.currentFloor, 5, Direction.UP, Location.INSIDE));
        elevator.sendUpRequest(new Request(elevator.currentFloor, 3, Direction.UP, Location.INSIDE));

        // One outside passenger at floor 4 wanting to go down
        elevator.sendDownRequest(new Request(4, 0, Direction.DOWN, Location.OUTSIDE));

        // Two internal requests downward from current floor
        elevator.sendDownRequest(new Request(elevator.currentFloor, 1, Direction.DOWN, Location.INSIDE));
        elevator.sendDownRequest(new Request(elevator.currentFloor, 2, Direction.DOWN, Location.INSIDE));

        elevator.run();
    }

    enum Direction { UP, DOWN, IDLE }
    enum Location { INSIDE, OUTSIDE }

    static class Request {
        int currentFloor;
        int targetFloor;
        Direction direction;
        Location location;
        public Request(int cf, int tf, Direction d, Location loc) {
            this.currentFloor = cf;
            this.targetFloor = tf;
            this.direction = d;
            this.location = loc;
        }
    }
}
