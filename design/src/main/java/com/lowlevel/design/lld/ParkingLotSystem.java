package com.lowlevel.design.lld;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.concurrent.ConcurrentHashMap;

enum SlotType {
    CAR, BIKE
}

class Gate {
    private final String gateId;

    public Gate(String gateId) {
        this.gateId = gateId;
    }

    public String getGateId() {
        return gateId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gateId);
    }
}

class ParkingSlot {
    private final String id;
    private final SlotType slotType;
    private final Map<Gate, Integer> distanceFromGates;

    public ParkingSlot(String id, SlotType slotType) {
        this.id = id;
        this.slotType = slotType;
        this.distanceFromGates = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public SlotType getSlotType() {
        return slotType;
    }

    public void setDistanceFromGate(Gate gate, int distance) {
        distanceFromGates.put(gate, distance);
    }

    public int getDistanceFromGate(Gate gate) {
        return distanceFromGates.getOrDefault(gate, Integer.MAX_VALUE);
    }
}

class Vehicle {
    private final String vehicleId;
    private final SlotType slotType;

    public Vehicle(String vehicleId, SlotType slotType) {
        this.vehicleId = vehicleId;
        this.slotType = slotType;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public SlotType getSlotType() {
        return slotType;
    }
}

// --- Parking Lot Service ---

class ParkingLotService {

    private final Map<SlotType, Map<Gate, PriorityQueue<ParkingSlot>>> gateWiseSlotQueues = new ConcurrentHashMap<>();
    private final List<Gate> allEntryGates = new ArrayList<>();

    public void registerGate(Gate gate) {
        allEntryGates.add(gate);
    }

    public void registerParkingSlot(ParkingSlot slot, Map<Gate, Integer> distances) {
        for (Gate gate : allEntryGates) {
            int distance = distances.getOrDefault(gate, Integer.MAX_VALUE);
            slot.setDistanceFromGate(gate, distance);

            gateWiseSlotQueues
                    .computeIfAbsent(slot.getSlotType(), k -> new ConcurrentHashMap<>())
                    .computeIfAbsent(gate, g -> new PriorityQueue<>(Comparator.comparingInt(s -> s.getDistanceFromGate(g))))
                    .add(slot);
        }
    }

    public ParkingSlot assignSlot(Vehicle vehicle, Gate entryGate) {
        Map<Gate, PriorityQueue<ParkingSlot>> typeMap = gateWiseSlotQueues.get(vehicle.getSlotType());
        if (typeMap == null) return null;

        PriorityQueue<ParkingSlot> pq = typeMap.get(entryGate);
        if (pq == null || pq.isEmpty()) return null;

        return pq.poll();
    }

    public void releaseSlot(ParkingSlot slot) {
        for (Gate gate : allEntryGates) {
            gateWiseSlotQueues.get(slot.getSlotType()).get(gate).add(slot);
        }
    }
}

// --- Demo ---

public class ParkingLotSystem {
    public static void main(String[] args) {
        ParkingLotService service = new ParkingLotService();

        Gate gate1 = new Gate("Gate1");
        Gate gate2 = new Gate("Gate2");
        service.registerGate(gate1);
        service.registerGate(gate2);

        ParkingSlot slotA = new ParkingSlot("A", SlotType.CAR);
        ParkingSlot slotB = new ParkingSlot("B", SlotType.CAR);

        Map<Gate, Integer> slotADist = Map.of(gate1, 5, gate2, 10);
        Map<Gate, Integer> slotBDist = Map.of(gate1, 7, gate2, 3);

        service.registerParkingSlot(slotA, slotADist);
        service.registerParkingSlot(slotB, slotBDist);

        Vehicle v1 = new Vehicle("KA-01", SlotType.CAR);
        Vehicle v2 = new Vehicle("KA-02", SlotType.CAR);

        ParkingSlot assignedSlot1 = service.assignSlot(v1, gate1);
        System.out.println("Assigned Slot to Vehicle 1: " + (assignedSlot1 != null ? assignedSlot1.getId() : "None"));

        ParkingSlot assignedSlot2 = service.assignSlot(v2, gate2);
        System.out.println("Assigned Slot to Vehicle 2: " + (assignedSlot2 != null ? assignedSlot2.getId() : "None"));

        // Release one slot and assign again
        service.releaseSlot(assignedSlot1);
        ParkingSlot assignedSlot3 = service.assignSlot(new Vehicle("KA-03", SlotType.CAR), gate1);
        System.out.println("Assigned Slot to Vehicle 3: " + (assignedSlot3 != null ? assignedSlot3.getId() : "None"));
    }
}
