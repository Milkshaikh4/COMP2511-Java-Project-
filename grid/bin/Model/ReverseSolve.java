package Model;

import java.util.*;

/**
 * This map generation algorithm is based on the ability to randomly place vehicles onto a map,
 * then choosing random moves to increase the difficulty of the map.
 */
public class ReverseSolve implements MapGeneration {
    private Random rand = new Random();
    private Grid grid;
    private Map<Integer, MapObject> vehicles;
    private int num_vehicles = 0;
    private int difficulty = 0;
    private int start_row = 0;
    private int grid_size;
    private static final int MAX_SEARCHES = 200;
    private static final int MIN_SIZE = 2;
    private static final int RIGHT = 90;
    private static final int DOWN = 180;
    private static final int SIZES = 2;
    private static final int DIRECTIONS = 2;
    private static final int HORI = 0, MAX_VERTS = 2;
    private PriorityQueue<Move> open = new PriorityQueue<>(new MoveComparator());
    private State initial;

    /**
     * Fills a grid with vehicles but also ensures the puzzle is solvable
     *
     * @param grid       refers to the 2D map which the vehicles can move on
     * @param vehicles   refers to the list of vehicles in the grid
     * @param start_row  the first row a car is inserted into and will also have an exit in that row
     * @param difficulty is a number between 1 and 3 which denotes the difficulty of the map being generated
     */
    @Override
    public void generateMap(Grid grid, Map<Integer, MapObject> vehicles, int start_row, int difficulty) {
        this.grid = grid;
        this.vehicles = vehicles;
        this.difficulty = difficulty;
        this.start_row = start_row;
        this.grid_size = this.grid.getGridSize();
        int max_moves = difficulty * grid_size / 2 + rand.nextInt(grid_size);
        boolean complete = false;

        for (int i = 0; i < MAX_SEARCHES; i++) {
            resetMap();
            buildTraffic();
            while (!open.isEmpty()) {
                Move next_move = open.poll();
                State s = new State(next_move);
                if (s.isComplete()) {
                    System.out.println("Found a puzzle.");
                    grid.setGrid(s.getGrid());
                    complete = true;
                    break;
                }
                for (Move m: s.getMoves()) {
                    open.offer(m);
                }
            }
            if (complete) break;
        }
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

    /**
     * Build a vehicle in the map.
     * @param length    of the vehicle
     * @param row       of the front of the vehicle
     * @param col       column of the back of the vehicle
     * @param bearing   of the vehicle (0, 90, 180, 270)
     * @return whether a vehicle could be successfully inserted at the specified location
     */
    private boolean buildVehicle(int length, int row, int col, int bearing) {
        if (!grid.insertVehicle(num_vehicles, length, row, col, bearing)) {
            //System.out.print("Attempted to print: ");
            //System.out.print(row);
            //System.out.print(", ");
            //System.out.print(col);
            //System.out.print(" -> ID: ");
            //System.out.println(num_vehicles);
            return false;
        }
        Location front = new Location(row, col);
        MapObject new_object = new Vehicle(num_vehicles, length, front, bearing);
        vehicles.put(new_object.getID(), new_object);
        num_vehicles++;
        return true;
    }

    /**
     * Insert multiple vehicles into the grid which do not obstruct the main vehicle
     */
    private void buildTraffic() {
        int bearing = 0, verts = 0;
        int head_col = rand.nextInt(grid_size - MIN_SIZE) + 1;
        buildVehicle(MIN_SIZE, this.start_row, head_col, RIGHT);
        int total_vehicles = this.grid_size / 2 + difficulty;
        int length = rand.nextInt(SIZES) + MIN_SIZE;
        int row = this.start_row - 1;
        if (length > row + 1) {
            row = this.grid_size - 1;
        }
        int col = rand.nextInt(this.grid_size - 1 - head_col) + head_col + 1;
        buildVehicle(length, row, col, DOWN);
        int i = 0;
        while (i < total_vehicles) {
            if (rand.nextInt(DIRECTIONS) == HORI) {
                bearing = RIGHT;
                length = rand.nextInt(SIZES) + MIN_SIZE;
                row = rand.nextInt(this.grid_size - 1);
                if (row >= this.start_row) row++;
                col = rand.nextInt(this.grid_size - length) + length - 1;
            } else if (verts < MAX_VERTS) {
                bearing = DOWN;
                length = rand.nextInt(SIZES) + MIN_SIZE;
                row = this.start_row - 1;
                if (length > row + 1) {
                    row = this.grid_size - 1;
                }
                col = rand.nextInt(this.grid_size);
            }
            if (buildVehicle(length, row, col, bearing)) {
                i++;
                if (bearing == DOWN) {
                    verts++;
                }
            }
        }
        scramble();
    }

    /**
     * Redundant function which was intended to move vehicles during testing
     * @param id        of the vehicle being moved
     * @param offset    which the vehicle will be moved
     * @return whether the vehicle could be moved to the specified location or not
     */
    private boolean moveVehicle(int id, int offset) {
        if (!hasPath(id, offset)) {
            return false;
        }
        Location front = vehicles.get(id).getLocation();
        int row = front.getRow();
        int col = front.getCol();
        int bearing = vehicles.get(id).getBearing();
        int length = vehicles.get(id).getSize();
        switch (bearing) {
            case RIGHT:
                col += offset;
                this.grid.removeVehicle(id);
                this.grid.insertVehicle(id, length, row, col, bearing);
                break;
            case DOWN:
                row += offset;
                this.grid.removeVehicle(id);
                this.grid.insertVehicle(id, length, row, col, bearing);
                break;
        }
        return true;
    }

    /**
     * Checks if a vehicle can move to a specified position
     * @param id        of the vehicle being moved
     * @param offset    which the vehicle will be moved
     * @return whether there is space for the vehicle to move to the specified location or not
     */
    private boolean hasPath(int id, int offset) {
        if (offset == 0) return true;
        Location front = vehicles.get(id).getLocation();
        int row = front.getRow();
        int col = front.getCol();
        int bearing = vehicles.get(id).getBearing();
        int length = vehicles.get(id).getSize();
        switch (bearing) {
            case RIGHT:
                if (offset > 0) {
                    for (int i = 0; i < offset; i++) {
                        col++;
                        if (this.grid.isSquareFilled(row, col)) {
                            return false;
                        }
                    }
                } else {
                    offset = Math.abs(offset);
                    col = col - length + 1;
                    for (int i = 0; i < offset; i++) {
                        col--;
                        if (this.grid.isSquareFilled(row, col)) {
                            return false;
                        }
                    }
                }
                return true;
            case DOWN:
                if (offset > 0) {
                    for (int i = 0; i < offset; i++) {
                        row++;
                        if (this.grid.isSquareFilled(row, col)) {
                            return false;
                        }
                    }
                } else {
                    offset = Math.abs(offset);
                    row = row - length + 1;
                    for (int i = 0; i < offset; i++) {
                        row--;
                        if (this.grid.isSquareFilled(row, col)) {
                            return false;
                        }
                    }
                }
                return true;
            default:
                return false;
        }
    }

    /**
     * Reset entire map, emptying all vehicles from the grid and erasing any memory of previous vehicles.
     */
    private void resetMap() {
        this.num_vehicles = 0;
        this.grid.resetGrid();
        this.vehicles.clear();
    }

    /**
     * Shifts vehicles in the grid around randomly to generate a gridlock.
     * @return
     */
    private void scramble() {
        this.initial = new State(this.grid.getGrid(), this.vehicles, this.start_row);
        for (Move m: initial.getMoves()) {
            open.offer(m);
        }
    }

    public static void main(String arg[]) {
        MapGeneration map_gen = new ReverseSolve();
        Grid grid = new Grid();
        Map<Integer, MapObject> vehicles = new HashMap<>();
        map_gen.generateMap(grid, vehicles, 2, 2);
        grid.showGrid();
        System.out.println(((ReverseSolve) map_gen).hasPath(2, -1));
        System.out.println(((ReverseSolve) map_gen).open);
    }
}
