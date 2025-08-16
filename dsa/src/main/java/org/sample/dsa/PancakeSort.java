package org.sample.dsa;

import java.util.ArrayList;
import java.util.List;

public class PancakeSort {

    public static void main(String[] args) {

        PancakeSort obj = new PancakeSort();
        int[] arr = {3,2,4,1};
        obj.pancakeSort(arr);
        System.out.println("Sorted array: " + java.util.Arrays.toString(arr));


    }

    public List<Integer> pancakeSort(int[] arr) {
        int pointer =arr.length-1;
        int[] indexes = new int[arr.length + 1];
        for (int i = 0;i <arr.length;i++) {
            indexes[arr[i]] = i;
        }
        boolean flag = false;
        while (pointer!=0) {
            if (arr[pointer] == pointer+1) {
                pointer--;
                continue;
            }
            flag = flip(arr, indexes[pointer+1], indexes);
            flip(arr, pointer, indexes);
            pointer--;
        }
        if (!flag) {
            return new ArrayList<>();
        }
        List<Integer> result = new ArrayList<>();
        for (int j : arr) {
            result.add(j);
        }
        return result;
    }

    public boolean flip(int[] arr, int k, int[] indexes) {
        for (int i =0; i<k/2; i++) {
            int temp = arr[i];
            arr[i] = arr[k-i];
            arr[k-i] = temp;

            indexes[arr[k-i]] = i;
            indexes[arr[i]] = k-i;
        }
        return true;
    }
}
