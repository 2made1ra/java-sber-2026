package lab7.algo.task2;

public class Solution {

    public long countRoutes(int rows, int columns) {
        long[][] routes = new long[rows][columns];
        routes[0][0] = 1;

        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                long current = routes[row][column];
                if (current == 0) {
                    continue;
                }

                addRoute(routes, row + 2, column + 1, current);
                addRoute(routes, row + 1, column + 2, current);
            }
        }

        return routes[rows - 1][columns - 1];
    }

    private void addRoute(long[][] routes, int row, int column, long value) {
        if (row < routes.length && column < routes[row].length) {
            routes[row][column] += value;
        }
    }
}
