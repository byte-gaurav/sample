package org.sample.dsa;

import java.util.HashMap;

public class CloseStrings {
    public boolean closeStrings(String word1, String word2) {
        HashMap<Character, Integer> charCounts1 = new HashMap<>();
        HashMap<Character, Integer> charCounts2 = new HashMap<>();
        HashMap<Integer, Integer> occurenceMap1 = new HashMap<>();

        for (char c : word1.toCharArray()) {
            charCounts1.put(c, charCounts1.getOrDefault(c, 0)+1);
        }

        for (char c : word2.toCharArray()) {
            if (!charCounts1.containsKey(c)) {
                return false;
            }
            charCounts2.put(c, charCounts2.getOrDefault(c, 0)+1);
        }

        for (int val : charCounts1.values()) {
            occurenceMap1.put(val, occurenceMap1.getOrDefault(val, 0)+1);
        }
        for (int i : charCounts2.values()) {
            if (occurenceMap1.containsKey(i)) {
                occurenceMap1.put(i, occurenceMap1.get(i) - 1);
            }
        }
        for (int i : occurenceMap1.values()) {
            if (i != 0) {
                return false;
            }
        }
        return true;
    }
}
