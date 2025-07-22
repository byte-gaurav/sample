package com.lowlevel.design.lld.snapshot;


import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class SnapshotMap {
    private Map<String, TreeMap<Integer, Integer>> map;
    private int currentSnapId;

    public SnapshotMap() {
        this.map = new HashMap<>();
        this.currentSnapId = 0;
    }

    public void set(String key, int value) {
        map.putIfAbsent(key, new TreeMap<>());
        map.get(key).put(currentSnapId, value);
    }

    public int snap() {
        return currentSnapId++;
    }

    public int get(String key, int snapshotId) {
        if (!map.containsKey(key)) return 0;
        Map.Entry<Integer, Integer> entry = map.get(key).floorEntry(snapshotId);
        return entry != null ? entry.getValue() : 0;
    }


    public static void main(String[] args) {
        SnapshotMap snapshotMap = new SnapshotMap();
        snapshotMap.set("a", 5);        // set key "a" to 5
        int snap0 = snapshotMap.snap(); // take snapshot -> id = 0
        snapshotMap.set("a", 6);        // change key "a" to 6
        int snap1 = snapshotMap.snap(); // take another snapshot -> id = 1
        System.out.println(snapshotMap.get("a", 0)); // prints 5
        System.out.println(snapshotMap.get("b", 1)); // prints 6
    }
}