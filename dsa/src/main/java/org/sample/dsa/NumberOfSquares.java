package org.sample.dsa;

public class NumberOfSquares {

    public static void main(String[] args) {
        NumberOfSquares numberOfSquares = new NumberOfSquares();
        int[][] matrix1 = {{0,1,1,1},{1,1,1,1},{0,1,1,1}};
        System.out.println("Result : " + numberOfSquares.maxSizeSquare(matrix1));

    }

    private int maxSizeSquare(int[][] matrix) {
        int memo[][] = new int[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    continue;
                }
                memo[i][j] = memo[i][j] + 1;
                if (i<1 || j<1) {
                    continue;
                }
                if (matrix[i-1][j] == 0 || matrix[i][j-1] == 0 || matrix[i-1][j-1] == 0) {
                    continue;
                }
                memo[i][j] = Math.min(memo[i-1][j], Math.min(memo[i][j-1], memo[i-1][j-1])) + 1;
            }
        }

        int count = 0;
        for (int i = 0; i < memo.length; i++) {
            for (int j = 0; j < memo[0].length; j++) {
                count += memo[i][j];
            }
        }
        return count;
    }
}