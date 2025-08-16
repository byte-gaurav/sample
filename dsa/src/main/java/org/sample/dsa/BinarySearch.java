package org.sample.dsa;

public class BinarySearch {

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 4, 10};
        int target = 10;
        int result = binarySearch(arr, target);
        if (result != -1) {
            System.out.println("Element found at index: " + result);
        } else {
            System.out.println("Element not found in the array.");
        }
    }

    private static int binarySearch(int[] arr, int target) {
        int low = 0, high=arr.length;
        while (low<high) {
            int mid = (low+high)/2;

            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target){
                low = mid+1;
            } else {
                high = mid-1;
            }

        }
        return -1;
    }
}
