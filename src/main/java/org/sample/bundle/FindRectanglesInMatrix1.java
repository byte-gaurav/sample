package org.sample.bundle;
import java.util.ArrayList;
import java.util.List;

public class FindRectanglesInMatrix1 {
    public static void main(String[] args) {
        char[][] input = {
                {'a', 'b', 'a', 'b'},
                {'b', 'a', 'a', 'b'},
                {'a', 'a', 'b', 'b'},
                {'a', 'b', 'a', '.'}
        };
        List<FindRectanglesInMatrix1.Pair> pairs = findRectangles(input);
        for (FindRectanglesInMatrix1.Pair pair : pairs) {
            System.out.println(pair.toString());
        }
    }

    private static List<FindRectanglesInMatrix1.Pair> findRectangles(char[][] inputMatrix) {
        if (inputMatrix.length == 0) {
            return new ArrayList<>();
        }
        if (inputMatrix.length == 1 && inputMatrix[0].length == 1) {
            return new ArrayList<>();
        }
        int[][] dpMatrix = getDpMatrix(inputMatrix.length, inputMatrix[0].length);
        for (int i = 1; i < dpMatrix.length; i++) {
            for (int j = 1; j < dpMatrix[0].length; j++) {
                dpMatrix[i][j] = dpMatrix[i][j-1] + dpMatrix[i-1][j] - dpMatrix[i-1][j-1];
                if (inputMatrix[i-1][j-1] == 'a') {
                    dpMatrix[i][j] += 1;
                } else if (inputMatrix[i-1][j-1] == 'b') {
                    dpMatrix[i][j] -= 1;
                }
            }
        }
        List<FindRectanglesInMatrix1.Pair> cordinates = new ArrayList<>();
        for (int i = 1; i < dpMatrix.length; i++) {
            for (int j = 1; j < dpMatrix[0].length; j++) {
                if (dpMatrix[i][j] == 0) {
                    cordinates.add(new FindRectanglesInMatrix1.Pair(i-1, j-1));
                }
            }
        }
        return cordinates;
    }

    private static int[][] getDpMatrix(int rows, int columns) {
        int[][] dpMatrix = new int[rows+1][columns+1];
        for (int i = 0; i<columns+1; i++) {
            dpMatrix[0][i] = 0;
        }
        for (int i = 0; i<rows+1; i++) {
            dpMatrix[i][0] = 0;
        }
        return dpMatrix;
    }

    private static class Pair {
        public int x;
        public int y;

        public String toString() {
            return this.x+","+this.y;
        }

        public Pair(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
