package Model;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class InsertBlock implements MapGeneration {

    private Random rand = new Random();
    private Grid grid;
    private Map<Integer, MapObject> vehicles;
    private int num_vehicles = 0;
    private static int MAX_DIR = 2;
    private static int MIN_SIZE = 2;
    private enum Orientation {
        VERTICAL,
        HORIZONTAL,
        NONE
    }
    private int cars_to_insert = 10;

    /**
     * Fills a grid with vehicles but also ensures the puzzle is solvable
     *
     * @param grid     refers to the 2D map which the vehicles can move on
     * @param vehicles refers to the list of vehicles in the grid
     */
    @Override
    public void generateMap(Grid grid, Map<Integer, MapObject> vehicles, int start_row, int difficulty) {

        // Insert first red car in a random position with at least
        // one square between the car and the exit
        this.grid = grid;
        this.grid.resetGrid();
        this.vehicles = vehicles;
        this.vehicles.clear();
        this.num_vehicles = 0;
        int col = rand.nextInt(grid.getGridSize() - MIN_SIZE) + 1;
        buildVehicle(MIN_SIZE, start_row, col, 90);
        cars_to_insert--;

        // Pick a random square to be blocked which is in front of the car
        int block_square = col + 1 + (rand.nextInt(grid.getGridSize() - 1 - col));

        // Block square
        // If square was not blocked, try to block another square
        while (!blockSquare(new Location(start_row, block_square), Orientation.VERTICAL)) {
            block_square = col + 1 + (rand.nextInt(grid.getGridSize() - 1 - col));
        }

    }

    private boolean blockSquare(Location to_block, Orientation orientation) {
        if (this.cars_to_insert <= 0) {
            return true;
        }

        // Block when the distance to the next object is greater or equal to the length of the car
        int length, offset, row = to_block.getRow(), col = to_block.getCol(), diff = 1;

        // If behind is not blocked
        // Block behind
        // If in front is not blocked
        // Block in front
        switch (orientation) {
            case VERTICAL:
                //offset = rand.nextInt(MIN_SIZE);
                //length = MIN_SIZE + offset;
                length = MIN_SIZE;
                for (int i = 0; i < length - 1; i++) {
                    if (!grid.isSquareFilled(row + 1, col))
                        row++;
                }
                buildVehicle(length, row, col, 180);
                this.cars_to_insert--;
                if (cars_to_insert <= 0) return true;

                // Check if behind is blocked
                int back_row = row - length + 1;
                boolean unblocked = true;
                for (int i = to_block.getRow() - length; i < back_row; i++) {
                    if (grid.isSquareFilled(i, col)) {
                        unblocked = false;
                        break;
                    }
                }

                // If not blocked, place a car behind
                while (unblocked) {
                    offset = rand.nextInt(length);
                    to_block = new Location(back_row - offset - 1, col);
                    orientation = choseDirection(to_block);
                    if (orientation == Orientation.NONE) {
                        return false;
                    }
                    if (blockSquare(to_block, orientation)) unblocked = false;
                }

                // Check if in front is blocked
                unblocked = true;
                for (int i = to_block.getRow() + length; i > row; i--) {
                    if (grid.isSquareFilled(i, col)) {
                        unblocked = false;
                        break;
                    }
                }

                // If unblocked, place a car in front
                while (unblocked) {
                    offset = rand.nextInt(length);
                    to_block = new Location(row + offset + 1, col);
                    orientation = choseDirection(to_block);
                    if (orientation == Orientation.NONE) {
                        return false;
                    }
                    if (blockSquare(to_block, orientation)) unblocked = false;
                }
                break;
            case HORIZONTAL:
                //offset = rand.nextInt(MIN_SIZE);
                //length = MIN_SIZE + offset;
                length = MIN_SIZE;
                for (int i = 0; i < length - 1; i++) {
                    if (!grid.isSquareFilled(row, col + 1))
                        col++;
                }
                buildVehicle(length, row, col, 90);

                this.cars_to_insert--;
                if (cars_to_insert <= 0) return true;

                // Check if behind is blocked
                int back_col = col - length + 1;
                unblocked = true;
                for (int i = to_block.getCol() - length; i < back_col; i++) {
                    if (grid.isSquareFilled(row, i)) {
                        unblocked = false;
                        break;
                    }
                }

                // If not blocked, place a car behind
                while (unblocked) {
                    offset = rand.nextInt(length);
                    to_block = new Location(row, back_col - offset - 1);
                    orientation = choseDirection(to_block);
                    if (orientation == Orientation.NONE) {
                        return false;
                    }
                    if (blockSquare(to_block, orientation)) unblocked = false;
                }

                // Check if in front is blocked
                unblocked = true;
                for (int i = to_block.getCol() + length; i > col; i--) {
                    if (grid.isSquareFilled(i, col)) {
                        unblocked = false;
                        break;
                    }
                }

                // If unblocked, place a car in front
                while (unblocked) {
                    offset = rand.nextInt(length);
                    to_block = new Location(row,col + offset + 1);
                    orientation = choseDirection(to_block);
                    if (orientation == Orientation.NONE) {
                        return false;
                    }
                    if (blockSquare(to_block, orientation)) unblocked = false;
                }
                break;
            default:
                return false;
        }

        return true;
    }

    /**
     * Populates a list of IDs and locations which is a potential solution to the puzzle.
     *
     * @param id_list   is the MapObject ID which is being moved.
     * @param move_list is the list of locations which the vehicles are moved to.
     */
    @Override
    public void solveMap(List<Integer> id_list, List<Location> move_list) {

    }

    private void buildVehicle(int length, int row, int col, int bearing) {
        if (!grid.insertVehicle(num_vehicles, length, row, col, bearing)) {
            System.out.print("Attempted to print: ");
            System.out.print(row);
            System.out.print(", ");
            System.out.print(col);
            System.out.print(" -> ID: ");
            System.out.println(num_vehicles);
            return;
        }
        Location front = new Location(row, col);
        MapObject new_object = new Vehicle(num_vehicles, length, front, bearing);
        vehicles.put(new_object.getID(), new_object);
        num_vehicles++;
    }


    private int countVertical(Location to_block) {
        int row = to_block.getRow();
        int col = to_block.getCol();

        if (this.grid.isSquareFilled(row, col)) {
            return 0;
        }

        int count = 1;
        row++;

        while (!this.grid.isSquareFilled(row, col)) {
            count++;
            row++;
        }

        row = to_block.getRow() - 1;

        while (!this.grid.isSquareFilled(row, col)) {
            count++;
            row--;
        }
        return count;
    }

    private int countHorizontal(Location to_block) {
        int row = to_block.getRow();
        int col = to_block.getCol();

        if (this.grid.isSquareFilled(row, col)) {
            return 0;
        }

        int count = 1;
        col++;

        while (!this.grid.isSquareFilled(row, col)) {
            count++;
            col++;
        }

        col = to_block.getCol() - 1;

        while (!this.grid.isSquareFilled(row, col)) {
            count++;
            col--;
        }
        return count;
    }

    private Orientation choseDirection(Location to_block) {
        Orientation orientation;
        switch (rand.nextInt(2)) {
            case (0):
                orientation = Orientation.HORIZONTAL;
                break;
            case (1):
                orientation = Orientation.VERTICAL;
                break;
            default:
                return Orientation.NONE;
        }

        // Choose vertical or horizontal
        // Check if there is space for the minimum vehicle size
        // If not, swap orientation and test again
        // If fail again, return false

        switch (orientation) {
            case HORIZONTAL:
                if (countHorizontal(to_block) < MIN_SIZE) {
                    if (countVertical(to_block) < MIN_SIZE) {
                        return Orientation.NONE;
                    } else {
                        orientation = Orientation.VERTICAL;
                    }
                }
                break;
            case VERTICAL:
                if (countVertical(to_block) < MIN_SIZE) {
                    if (countHorizontal(to_block) < MIN_SIZE) {
                        return Orientation.NONE;
                    } else {
                        orientation = Orientation.HORIZONTAL;
                    }
                }
                break;
            default:
                return Orientation.NONE;
        }

        return orientation;
    }
}
