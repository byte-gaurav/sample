package org.sample.dsa;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class PowerofNumber {

    public static void main(String args[]) {
        PowerofNumber powerofNumber = new PowerofNumber();
        Map<Integer, Integer> map =new HashMap<>();
        for (int i = 12; i<=15; i++) {
            System.out.println("Steps for "+ i + " is " + powerofNumber.getStep(i, map));
        }
    }

    public int getKth(int lo, int hi, int k) {
        List<String> powerList = new ArrayList<>();
        Map<Integer, Integer> map = new HashMap<>();
        for (int i=0;i<hi-lo+1;i++) {
            int step = getStep(lo+i, map);
            powerList.add(""+step+"|"+(lo+i));
        }
        printList(powerList);
        System.out.println();
        Collections.sort(powerList);
        printList(powerList);
        System.out.println();
        String s = powerList.get(k-1);
        return Integer.parseInt(s.substring(s.indexOf("|")+1))+2;
    }

    public int getStep(int num, Map<Integer, Integer> powerMap) {
        if (num == 1) {
            return 0;
        }
        if (num == 2) {
            return 1;
        }
        if (powerMap.containsKey(num)) {
            return powerMap.get(num);
        }
        if (num %2 == 0) {
            int power2Step = isPowerOfTwo(num);
            if (power2Step > 0) {
                powerMap.put(num, power2Step);
            } else {
                powerMap.put(num, getStep(num/2, powerMap)+1);
            }
        } else {
            powerMap.put(num, getStep((3*num)+1, powerMap)+1);
        }
        return powerMap.get(num);
    }

    private int isPowerOfTwo(int num) {
        if (num>0 && (num & (num-1)) == 0) {
            return (int) (Math.log(num)/Math.log(2));
        } else {
            return -1;
        }
    }

    private void printList(List<String> list) {
        for (String s : list) {
            System.out.print(s + " ");
        }
    }
}
