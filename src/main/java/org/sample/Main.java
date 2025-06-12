package org.sample;
public class Main {
    public static void main(String[] args) {

    }

    private boolean getSum(int[] input, int i, int currentSum) {
        if (currentSum == 0) {
            return true;
        }
        if (i == input.length) {
            return false;
        }
        if (input[i] > currentSum) {
            return getSum(input, i+1, currentSum);
        } else {
            return getSum(input, i+1, currentSum-input[i]) || getSum(input, i+1, currentSum);
        }
    }
}