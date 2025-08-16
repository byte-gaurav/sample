package org.sample.dsa;

import java.util.HashMap;
import java.util.Map;

public class LongestNonRepeatingCharacters {

    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.length() <1) {
            return 0;
        }
        Map<Character, Integer> characterIndices = new HashMap<>();
        char[] characters = s.toCharArray();
        int maxLength = 1;
        int startingIndex=0;
        int currentLength = 0;
        for (int i=0; i < s.length(); i++) {
            char c = characters[i];
            if (characterIndices.containsKey(c)) { //if already encountered before
                int lastIndex = characterIndices.get(c);
                if (lastIndex >= startingIndex) {
                    startingIndex = lastIndex+1;
                }
                currentLength = Math.abs(startingIndex - i) + 1;
            } else { //if new character
                currentLength=currentLength+1;
            }
            characterIndices.put(c, i);
            maxLength = Math.max(currentLength, maxLength);
        }
        return maxLength;
    }
}
