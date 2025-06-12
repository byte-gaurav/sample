package org.sample.bundle;

public class NoOfIslands {
    public static int numIslands(char[][] grid) {
        int row = grid.length;
        int column = grid[0].length;
        int islands = 0;
        for (int i = 0; i< row; i++) {
            for (int j = 0; j< row; j++) {
                if (grid[i][j] == '0' || grid[i][j] == '5') {
                    continue;
                }
                if (grid[i][j] == '1') {
                    markIsland(grid, row, column, i, j);
                    islands = islands + 1;
                }
            }
        }
        return islands;

    }

    private static void markIsland(char[][] grid, int row, int columns, int i, int j) {
        if (i<0 || j<0 || i == row || j == columns || grid[i][j]=='0' || grid[i][j]=='5')  {
            return;
        }
        grid[i][j] = '5';
        markIsland(grid, row, columns, i, j+1);
        markIsland(grid, row, columns, i+1, j);
        markIsland(grid, row, columns, i, j-1);
        markIsland(grid, row, columns, i-1, j);
    }

    public static void main(String[] args) {
        char[][] grid = {
                        {'1','1','1'},
                        {'0','1','0'},
                        {'1','1','1'}
        };
        int islands = numIslands(grid);
        System.out.println(islands);
    }
}
