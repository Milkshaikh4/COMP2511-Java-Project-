package Model;

import java.util.ArrayList;
import java.util.List;

public class Grid {
	private int[][] grid;
	private final int GRID_SIZE = 6;
	private static int EMPTY = -1;


    /**
     * Creates a new grid with dimensions defined by the GRID_SIZE constant.
     * Also initialises a list to store a list of remaining cars in the grid.
     */
	public Grid() {
		this.grid = new int[GRID_SIZE][GRID_SIZE];
		this.resetGrid();
	}

    /**
     * Builds a new vehicle only if there is enough space on the grid for it
     * @param length of the vehicle
     * @param row is the vertical coordinate on the grid (units from the top)
     * @param col is the horizontal coordinate on the grid (units from the left)
     * @param bearing is the direction the vehicle is face (should be 0, 90, 180 or 270)
     * @return the ID of the vehicle created, or -1 if
     */
	public boolean insertVehicle(int id, int length, int row, int col, int bearing) {
	    if (length <= 0) return false;
        List<Location> to_fill = new ArrayList<>();
	    for (int i = 0; i < length; i++) {
            if (row < 0 || row >= GRID_SIZE || col < 0 || col >= GRID_SIZE) return false;
	        if (grid[row][col] != EMPTY) {
	            return false;
            }
            to_fill.add(new Location(row, col));
            switch (bearing) {
                case 0:
                    row++;
                    break;
                case 90:
                    col--;
                    break;
                case 180:
                    row--;
                    break;
                case 270:
                    col++;
                    break;
                default:
                    return false;
            }
        }
        for (Location l: to_fill) {
	        grid[l.getRow()][l.getCol()] = id;
        }
		return true;
	}

    /**
     * Empties the entire grid and removes all vehicles from the list
     */
	public void resetGrid() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j] = EMPTY;
            }
        }
    }

    /**
     * Shows the current state of the grid in the console
     */
    public void showGrid() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] != EMPTY) {
                    System.out.print(grid[i][j]);
                } else {
                    System.out.print("#");
                }
                System.out.print(" ");
            }
            System.out.println("\n");
        }
    }

    /**
     * Shows the current state of the grid in the console
     * @param object which is trying to move
     * @param finish location
     * @return true if there is a path from the start to finish
     */
    public boolean hasPath(MapObject object, Location finish) {
	    return false;
    }

    public void removeVehicle(int id) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] == id) {
                    grid[i][j] = EMPTY;
                }
            }
        }
    }

    public int getGridSize() {
        return this.GRID_SIZE;
    }

    public boolean isSquareFilled(int row, int col) {
        if (row >= GRID_SIZE || col >= GRID_SIZE || row < 0 || col < 0) {
            return true;
        }
        return this.grid[row][col] != EMPTY;
    }

    public int[][] getGrid() {
        return grid;
    }

    public void setGrid(int grid[][]) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                this.grid[i][j] = grid[i][j];
            }
        }
    }
}
