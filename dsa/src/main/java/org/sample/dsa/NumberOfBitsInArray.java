package org.sample.dsa;

/**
 * Given an integer n, return an array ans of length  such that for each i (0 <= i <= n), ans[i] is the number of 1's in the binary representation of i.
 * Example 1:
 *
 * Input: n = 2
 * Output: [0,1,1]
 * Explanation:
 * 0 --> 0
 * 1 --> 1
 * 2 --> 10
 * Example 2:
 *
 * Input: n = 5
 * Output: [0,1,1,2,1,2]
 * Explanation:
 * 0 --> 0
 * 1 --> 1
 * 2 --> 10
 * 3 --> 11
 * 4 --> 100
 * 5 --> 101
 *
 *
 * Constraints:
 *
 * 0 <= n <= 105
 */
public class NumberOfBitsInArray {

    public static void main(String[] args) {
        int n = 5;
        int[] bitCountArray = countBits(n);
        for (int i = 0; i<n+1; i++) {
            System.out.print(bitCountArray[i] + " ");
        }
    }

    public static int[] countBits(int n) {
        int[] arr = new int[n+1];
        arr[0] = 0; // base case
        if (n >= 1) {
            arr[1] = 1;
        }
        if (n >= 2) {
            arr[2] = 1;
        }
        for (int i = 3; i <= n; i++) {
            int logValue = calculateLogBase2(i);
            int powerOfTwo = (int) Math.pow(2, logValue);
            if (powerOfTwo == i) {
                arr[i] = 1; // if i is a power of 2
            } else {
                arr[i] = arr[i - powerOfTwo] + 1; // count bits based on previous values
            }
        }
        return arr;
    }

    private static int calculateLogBase2(int number) {
        return (int) (Math.log(number) / Math.log(2));
    }
}
