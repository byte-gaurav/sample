package org.sample.dsa;

public class HappyNumber {

    public static void main(String[] args) {
        int n = 29; // Example input
        boolean result = isHappy(n);
        System.out.println(result);
    }

    private static boolean isHappy(int n) {
        Boolean[] arr = new Boolean[729];
        arr[1] = true;
        return isHappy(n, arr, new boolean[arr.length]);
    }

    private static boolean isHappy(int n, Boolean[] arr, boolean[] visited) {
        if (n == 1) {
            return true;
        }
        if (visited[n] && arr[n] == null) {
            return false; // Cycle detected
        }
        visited[n] = true;
        if (arr[n] != null) {
            return arr[n];
        }
        int sum = 0;
        while (n > 0) {
            int digit = n % 10;
            sum += digit * digit;
            n /= 10;
        }
        arr[n] = isHappy(sum, arr, visited);
        return arr[n];
    }
}
