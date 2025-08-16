package org.sample.dsa;

import java.util.HashMap;
import java.util.TreeSet;

public class ShortestWordDistance {

    private String[] words;

    private HashMap<String, TreeSet<Integer>> map;

    public ShortestWordDistance(String[] words) {
        this.words = words;
        map = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            String word = words[i];
            if (!map.containsKey(word)) {
                map.put(word, new TreeSet<>());
            }
            map.get(word).add(i);
        }
    }


    public static void main(String[] args) {
        ShortestWordDistance swd = new ShortestWordDistance(new String[]{"practice", "makes", "perfect", "coding", "makes"});
        System.out.println(swd.shortestDistance("practice", "coding"));
    }

    public int shortestDistance(String word1, String word2) {
        if (!this.map.containsKey(word1) || !this.map.containsKey(word2)) {
            return -1;
        }
        if (word1.equals(word2)) {
            return 0;
        }
        TreeSet<Integer> set1 = this.map.get(word1);
        TreeSet<Integer> set2 = this.map.get(word2);
        int min = Integer.MAX_VALUE;
        for (Integer index1 : set1) {
            for (Integer index2 : set2) {
                min = Math.min(min, Math.abs(index1 - index2));
            }
        }
        return min;
    }
}
