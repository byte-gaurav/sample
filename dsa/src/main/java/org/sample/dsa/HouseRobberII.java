package org.sample.dsa;

public class HouseRobberII {

    public static void main(String[] args) {
        HouseRobberII hr = new HouseRobberII();
        int[] nums = {2,3,2};
        System.out.println(hr.rob(nums));
    }

    public int rob(int[] nums){
        if (nums.length == 0) {
            return 0;
        }
        if (nums.length == 1) {
            return nums[0];
        }
        int max = rob(nums, 0, nums.length-2);
        return Math.max(max, rob(nums, 1, nums.length-1));
    }

    public int rob(int[] nums, int start, int end) {
        int len = (end-start)+1;
        int[] dp = new int[len];
        dp[0] = nums[start];
        dp[1] = Math.max(nums[start], nums[1+start]);
        for (int i = 2+start; i <= end; i++) {
            dp[i-start] = Math.max(dp[i-start - 2] + nums[i], dp[i-start - 1]);
        }
        return dp[dp.length-1];
    }
}
