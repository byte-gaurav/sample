package org.sample.dsa;

import java.util.Stack;

import static java.lang.Math.abs;

public class AsteroidCollision {

    public static void main(String[] args) {
        AsteroidCollision ac = new AsteroidCollision();
        int[] asteroids = {5,-5};
        System.out.println();
        for (int i :  ac.asteroidCollision(asteroids)) {
            System.out.print(i);
        }
    }

    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> stack = new Stack<>();
        for (int j = 0; j<asteroids.length;) {
            int i = asteroids[j];
            if (stack.isEmpty()) { //base condition to avoid checking on empty stack
                stack.push(i);
                j++;
                continue;
            }
            if ( i < 0 ) { //asteroid going left
                if (stack.peek() > 0 ) { //check if any possibility of asteroid collision i.e. any right going asteroid on a left coming asturd
                    if (Math.abs(i) == stack.peek()) { //if both asteroids of equal size
                        stack.pop();
                        j++;
                        continue;
                    } else if (Math.abs(i) > stack.peek()) { //since keep popping till
                        stack.pop();
                        continue;
                    } else if (!stack.isEmpty() && Math.abs(i) < stack.peek()) {
                        j++;
                        continue;
                    } else if (!stack.isEmpty() && Math.abs(i) == stack.peek()) {
                        stack.pop();
                        continue;
                    }
                    stack.push(i);
                    j++;
                } else {
                    stack.push(i);
                    j++;
                }
            } else {
                stack.push(i);
                j++;
            }
        }
        if (stack.isEmpty()) {
            return new int[0];
        } else {
            int[] response = new int[stack.size()];
            for (int i = stack.size()-1;i>=0;i--) {
                response[i] = stack.pop();
            }
            return response;
        }

    }
}
