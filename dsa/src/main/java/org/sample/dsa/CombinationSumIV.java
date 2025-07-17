package org.sample.dsa;

public class CombinationSumIV {

    public static void main(String[] args) {
        CombinationSumIVInterface combinationSumIV = new CombinationSumIVRecursionMemo();
        int[] nums = {2,1,3};
        int target = 35;
        Long startTime = System.currentTimeMillis();
        System.out.println("Result : " + combinationSumIV.combinationSum4(nums, target));
        System.out.println("Computation took : " + (System.currentTimeMillis() - startTime) + " ms");
    }
}

interface CombinationSumIVInterface {
    int combinationSum4(int[] nums, int target);
}

class CombinationSumIVRecursion implements CombinationSumIVInterface{
    public int combinationSum4(int[] nums, int target) {
        return countCombinationRec(nums, target, 0);
    }

    public int countCombinationRec(int[] nums, int target, int currentSum) {
        if (target == currentSum) {
            return 1;
        }
        if (currentSum > target) {
            return 0;
        }
        int totalCount = 0;
        for (int i = 0; i < nums.length; i++) {
            int count = countCombinationRec(nums, target, currentSum + nums[i]);
            if (count == 0) {
                continue;
            }
            totalCount += count;
        }
        return totalCount;
    }
}

class CombinationSumIVRecursionMemo implements CombinationSumIVInterface {
    public int combinationSum4(int[] nums, int target) {
        Integer[] memo = new Integer[target + 1];
        return countCombinationMemo(nums, target, 0, memo);
    }

    private int countCombinationMemo(int[] nums, int target, int currentSum, Integer[] memo) {
        if (currentSum == target) {
            return 1;
        }
        if (currentSum > target) {
            return 0;
        }
        if (memo[currentSum] != null) {
            return memo[currentSum];
        }
        int totalCount = 0;
        for (int num : nums) {
            totalCount += countCombinationMemo(nums, target, currentSum + num, memo);
        }
        memo[currentSum] = totalCount;
        return totalCount;
    }
}
