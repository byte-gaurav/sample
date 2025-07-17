package org.sample.dsa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Example 1:
 *
 * Input: s = "leetcode", wordDict = ["leet","code"]
 * Output: true
 * Explanation: Return true because "leetcode" can be segmented as "leet code".
 * Example 2:
 *
 * Input: s = "applepenapple", wordDict = ["apple","pen"]
 * Output: true
 * Explanation: Return true because "applepenapple" can be segmented as "apple pen apple".
 * Note that you are allowed to reuse a dictionary word.
 * Example 3:
 *
 * Input: s = "catsandog", wordDict = ["cats","dog","sand","and","cat"]
 * Output: false
 */

public class WordBreak {

    public static void main(String[] args) {
        WordBreak wordBreak = new WordBreak();
        String s = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaab";
        List<String> wordDict = new ArrayList<>();
        String last = "a";
        for (int i = 1;i<=10;i++) {
            wordDict.add(last);
            last = last + "a";
        }
        System.out.println("Result : " + wordBreak.wordBreak(s, wordDict));
    }

    public boolean wordBreak(String s, List<String> wordDict) {

        return isWordBreaking(s,0, new HashSet<>(wordDict), new Boolean[s.length()]);
    }

    private boolean isWordBreaking(String s, int i, Set<String> wordDictionary, Boolean[] memo) {
        if (i == s.length()) {
            return true;
        }
        if (memo[i] != null) {
            return memo[i]; // already computed this index
        }
        for (int j = i+1; j<=s.length(); j++) {
            if(wordDictionary.contains(s.substring(i, j)) && isWordBreaking(s, j, wordDictionary, memo)) {
                return memo[i] = true;
            }
        }
        return memo[i]=false;
    }

    private boolean isWordBreakingSort(String s, List<String> wordList) {
        if (s.length() == 0) {
            return true;
        }
        boolean value = false;
        for (int i=0; i<wordList.size(); i++) {
            while (s.contains(wordList.get(i))) {
                s = s.replaceAll(wordList.get(i), "");
                value = value || isWordBreakingSort(s, wordList);
            }
        }
        return value;
    }

}
