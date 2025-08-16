package org.sample.dsa;

import java.util.ArrayList;
import java.util.List;

public class MergeSort {

    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(3);list.add(6);list.add(9);list.add(4);list.add(8);
        list.add(5);list.add(2);list.add(7);list.add(1);list.add(10);

        List<String> stringList = new ArrayList<>();
        stringList.add("banana");stringList.add("apple");stringList.add("cherry");
        stringList.add("date");stringList.add("elderberry");stringList.add("burberry");

        System.out.println("Sorted integer list: " + mergeSort(list));
        System.out.println("Sorted string list: " + mergeSort(stringList));
    }

    private static <E extends Comparable<E>> List<E>  mergeSort(List<E> list) {
        if (list.size() <= 1) {
            return list;
        }
        int mid = list.size()/2;
        List<E> left = list.subList(0, mid);
        List<E> right = list.subList(mid, list.size());
        return merge(mergeSort(left), mergeSort(right));
    }

    private static <E extends Comparable<E>> List<E> merge(List<E> left, List<E> right) {
        List<E> result = new ArrayList<>();
        int leftIndex = 0;
        int rightIndex = 0;

        // Create copies to avoid modifying the original lists
        List<E> leftCopy = new ArrayList<>(left);
        List<E> rightCopy = new ArrayList<>(right);

        // Compare elements from both lists and add the smaller one to result
        while (leftIndex < leftCopy.size() && rightIndex < rightCopy.size()) {
            if (leftCopy.get(leftIndex).compareTo(rightCopy.get(rightIndex)) < 0) {
                result.add(leftCopy.get(leftIndex));
                leftIndex++;
            } else {
                result.add(rightCopy.get(rightIndex));
                rightIndex++;
            }
        }

        // Add remaining elements from left list
        while (leftIndex < leftCopy.size()) {
            result.add(leftCopy.get(leftIndex));
            leftIndex++;
        }

        // Add remaining elements from right list
        while (rightIndex < rightCopy.size()) {
            result.add(rightCopy.get(rightIndex));
            rightIndex++;
        }

        return result;
    }
}
