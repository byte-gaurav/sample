package org.sample.dsa;

public class JumpGameII {

    public int jump(int[] nums) {
        return 0;
    }

    private int jump(int[] nums, int index, int jump, int[] smallestJump) {
        if (index == nums.length - 1) {
            return Math.min(jump, smallestJump[0]);
        }
        int maxJump = nums[index];
        int maxReachableIndex = Math.min(index + maxJump, nums.length - 1);
        for (int i = maxReachableIndex; i >= index; i--) {
            smallestJump[0] = Math.min(smallestJump[0], jump(nums, i, jump++, smallestJump));
        }
        return smallestJump[0];
    }

}
