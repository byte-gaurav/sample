package org.sample.dsa;

public class ProductOfArrayExceptSelf {

    private static int[] getProducts(int[] nums) {
        int zeroCount = 0;
        int product = 1;
        for (int num : nums) {
            if (num == 0) {
                zeroCount++;
            } else {
                product *= num;
            }
        }
        int[] result = new int[nums.length];
        if (zeroCount > 1) {
            return result;
        }
        for (int i = 0; i < nums.length; i++) {
            if (zeroCount == 1) {
                result[i] = nums[i] == 0 ? product : 0;
            } else {
                result[i] = product / nums[i];
            }
        }
        return result;
    }
}
