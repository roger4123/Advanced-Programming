package Homework;

public class Board {
    public static final int BOARD_SIZE = 11;
    private int[][] grid;

    public Board() {
        grid = new int[BOARD_SIZE][BOARD_SIZE];
    }

    // Place a move; returns true if the cell is valid and free.
    public boolean placeMove(int x, int y, int marker) {
        if (x < 0 || x >= BOARD_SIZE || y < 0 || y >= BOARD_SIZE)
            return false;
        if (grid[x][y] != 0)
            return false;
        grid[x][y] = marker;
        return true;
    }

    // Checks win for a given marker.
    public boolean checkWin(int marker) {
        boolean[][] visited = new boolean[BOARD_SIZE][BOARD_SIZE];
        if (marker == 1) { // For player 1: check left to right
            for (int y = 0; y < BOARD_SIZE; y++) {
                if (grid[0][y] == marker && dfs(0, y, marker, visited))
                    return true;
            }
        } else if (marker == 2) { // For player 2: check top to bottom
            for (int x = 0; x < BOARD_SIZE; x++) {
                if (grid[x][0] == marker && dfs(x, 0, marker, visited))
                    return true;
            }
        }
        return false;
    }

    // DFS for neighbor connectivity on a hex board.
    private boolean dfs(int x, int y, int marker, boolean[][] visited) {
        if (visited[x][y])
            return false;
        visited[x][y] = true;
        if (marker == 1 && x == BOARD_SIZE - 1)
            return true;  // reached right edge for player 1
        if (marker == 2 && y == BOARD_SIZE - 1)
            return true;  // reached bottom edge for player 2

        int[][] directions = { {-1, 0}, {1, 0}, {0, -1}, {0, 1}, {-1, 1}, {1, -1} };
        for (int[] d : directions) {
            int nx = x + d[0];
            int ny = y + d[1];
            if (nx >= 0 && nx < BOARD_SIZE && ny >= 0 && ny < BOARD_SIZE && grid[nx][ny] == marker) {
                if (dfs(nx, ny, marker, visited))
                    return true;
            }
        }
        return false;
    }

    // In Board.java
    public String getState() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                sb.append(grid[i][j]);
                if(j < BOARD_SIZE -1) {
                    sb.append(",");
                }
            }
            if(i < BOARD_SIZE -1) {
                sb.append(";");
            }
        }
        return sb.toString();
    }

}
