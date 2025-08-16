package org.sample.dsa;

public class KokosEatingBananas {

    /**
     Koko loves to eat bananas. There are n piles of bananas, the ith pile has piles[i] bananas.
     The guards have gone and will come back in h hours.
     Koko can decide her bananas-per-hour eating speed of k. Each hour, she chooses some pile of bananas and eats k bananas from that pile.
     If the pile has less than k bananas, she eats all of them instead and will not eat any more bananas during this hour.

     Koko likes to eat slowly but still wants to finish eating all the bananas before the guards return.
     Example 1:



     Input: piles = [3,6,7,11], h = 8
     Output: 4
     Example 2:

     Input: piles = [30,11,23,4,20], h = 5
     Output: 30
     Example 3:

     Input: piles = [30,11,23,4,20], h = 6
     Output: 23

     **/



    public static void main(String[] args) {
        int[] piles1 = {3, 6, 7, 11};
        int h1 = 8;
        System.out.println("Test Case 1: Expected 4, Got " + minEatingSpeed(piles1, h1));

        int[] piles2 = {30, 11, 23, 4, 20};
        int h2 = 5;
        System.out.println("Test Case 2: Expected 30, Got " + minEatingSpeed(piles2, h2));

        int[] piles3 = {30, 11, 23, 4, 20};
        int h3 = 6;
        System.out.println("Test Case 3: Expected 23, Got " + minEatingSpeed(piles3, h3));
    }

    public static int minEatingSpeed(int[] piles, int h) {
        int left = 1;
        int right = Integer.MAX_VALUE;
        int minSpeed = 0;
        while (left <= right) {
            int currSpeed = left + (right - left) / 2;
            int hours = 0;
            for (int pile : piles) {
                hours += (pile + currSpeed - 1) / currSpeed;
            }
            if (hours <= h) {
                minSpeed = currSpeed;
                right = currSpeed - 1;
            } else {
                left = currSpeed + 1;
            }
        }
        return minSpeed;
    }
}
