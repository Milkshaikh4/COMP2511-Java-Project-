package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A state refers to a configuration vehicles in the grid. Every configuration should be unique.
 */
public class State {

    private Map<Integer, MapObject> vehicles;
    private int total_moves = 0;
    private final int GRID_SIZE = 6;
    private static int EMPTY = -1;
    private int weights[][] = new int[GRID_SIZE][GRID_SIZE];
    private int grid[][] = new int [GRID_SIZE][GRID_SIZE];
    private static final int RIGHT = 90;
    private static final int DOWN = 180;
    private Move last_move = null;
    private State prev_state = null;
    private static final int MIN_SIZE = 2;
    private List<Move> moves = new ArrayList<>();
    private int max = 3;

    /**
     * Generates a state from the base case where the map can be solved in one move
     * @param grid      denotes the positions of vehicles in the grid.
     * @param vehicles  is a list which can be used to find extra information about a vehicle.
     * @param start_row is the first row which was inserted into.
     */
    public State(int grid[][], Map<Integer, MapObject> vehicles, int start_row) {
        int front = 0, offset = -1;
        this.vehicles = new HashMap<>(vehicles);
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                this.weights[i][j] = 0;
                this.grid [i][j] = grid[i][j];
            }
        }
        this.grid = new int[GRID_SIZE][GRID_SIZE];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                this.grid[i][j] = grid[i][j];
            }
        }
        for (int i = GRID_SIZE - 1; i >= 0; i--) {
            if (this.grid[start_row][i] != EMPTY) {
                front = i;
                break;
            }
            weights[start_row][i]++;
            checkVertical(0, start_row, i, total_moves + weights[start_row][i]);
        }
        for (int i = front - MIN_SIZE; i >= 0; i--) {
            moves.add(new Move(this, 0, offset, ++weights[start_row][i]));
        }
    }

    /**
     * Create a new state based off a new move and its previous state.
     * @param next_move is the next move from the previous state
     */
    public State(Move next_move) {
        int old_grid[][] = next_move.getPrev().getGrid();
        int old_weights[][] = next_move.getPrev().getWeights();
        this.vehicles = new HashMap<>(next_move.getPrev().getVehicles());
        this.prev_state = next_move.getPrev();
        this.last_move = next_move;
        this.total_moves = next_move.getPrev().getTotalMoves();

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                this.grid[i][j] = old_grid[i][j];
                this.weights[i][j] = old_weights[i][j];
            }
        }

        // Move vehicle
        moveVehicle(next_move.getID(), next_move.getOffset());
        // Find squares which can be blocked and add moves to queue
    }

    /**
     * Checks a column in the grid to see if there are any vehicles which could block a specified location
     * @param id        of the vehicle which needs to be blocked
     * @param row       of the block/square
     * @param col       column of the block/square
     * @param weight    refers to the weighting given to a specified block which can act as a potential heuristic
     */
    private void checkVertical(int id, int row, int col, int weight) {
        int temp_row = row + 1;
        while (temp_row < GRID_SIZE) {
            if (this.grid[temp_row][col] != EMPTY && this.grid[temp_row][col] != id && vehicles.get(this.grid[temp_row][col]).getBearing() == DOWN) {
                int offset = row - temp_row;
                for (int i = 0; i < vehicles.get(this.grid[temp_row][col]).getSize(); i++) {
                    if (hasPath(this.grid[temp_row][col], offset - i))
                        moves.add(new Move(this, this.grid[temp_row][col], offset - i, weight));
                }
                break;
            }
            temp_row++;
        }
        temp_row = row - 1;
        while (temp_row >= 0) {
            if (this.grid[temp_row][col] != EMPTY && this.grid[temp_row][col] != id && vehicles.get(this.grid[temp_row][col]).getBearing() == DOWN) {
                int offset = row - temp_row;
                for (int i = 0; i < vehicles.get(this.grid[temp_row][col]).getSize(); i++) {
                    if (hasPath(this.grid[temp_row][col], offset + i))
                        moves.add(new Move(this, this.grid[temp_row][col], offset + i, weight));
                }
                break;
            }
            temp_row--;
        }
    }

    /**
     * Checks a row in the grid to see if there are any vehicles which could block a specified location
     * @param id        of the vehicle which needs to be blocked
     * @param row       of the block/square
     * @param col       column of the block/square
     * @param weight    refers to the weighting given to a specified block which can act as a potential heuristic
     */
    private void checkHorizontal(int id, int row, int col, int weight) {
        int temp_col = col + 1;
        while (temp_col < GRID_SIZE) {
            if (this.grid[row][temp_col] != EMPTY && this.grid[row][temp_col] != id && vehicles.get(this.grid[row][temp_col]).getBearing() == RIGHT) {
                int offset = col - temp_col;
                for (int i = 0; i < vehicles.get(this.grid[row][temp_col]).getSize(); i++) {
                    if (hasPath(this.grid[row][temp_col], offset - i))
                        moves.add(new Move(this, this.grid[row][temp_col], offset - i, weight));
                }
                break;
            }
            temp_col++;
        }
        temp_col = col - 1;
        while (temp_col >= 0) {
            if (this.grid[row][temp_col] != EMPTY && this.grid[row][temp_col] != id && vehicles.get(this.grid[row][temp_col]).getBearing() == RIGHT) {
                int offset = col - temp_col;
                for (int i = 0; i < vehicles.get(this.grid[row][temp_col]).getSize(); i++) {
                    if (hasPath(this.grid[row][temp_col], offset + i))
                        moves.add(new Move(this, this.grid[row][temp_col], offset + i, weight));
                }
                break;
            }
            temp_col--;
        }
    }

    /**
     * Gets the list of potential moves from a given state
     * @return a list of moves
     */
    public List<Move> getMoves() {
        return moves;
    }

    /**
     * Checks if there is a path for a vehicle to move to a given location
     * @param id        of the vehicle being moved
     * @param offset    which the vehicle is being moved too.
     * @return whether there is a valid path or not
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
                        if (!inGrid(row, col) || this.grid[row][col] != EMPTY) {
                            return false;
                        }
                    }
                } else {
                    offset = Math.abs(offset);
                    col = col - length + 1;
                    for (int i = 0; i < offset; i++) {
                        col--;
                        if (!inGrid(row, col) || this.grid[row][col] != EMPTY) {
                            return false;
                        }
                    }
                }
                return true;
            case DOWN:
                if (offset > 0) {
                    for (int i = 0; i < offset; i++) {
                        row++;
                        if (!inGrid(row, col) || this.grid[row][col] != EMPTY) {
                            return false;
                        }
                    }
                } else {
                    offset = Math.abs(offset);
                    row = row - length + 1;
                    for (int i = 0; i < offset; i++) {
                        row--;
                        if (!inGrid(row, col) || this.grid[row][col] != EMPTY) {
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
     * Checks if a given location is within the bounds of the grid
     * @param row
     * @param col
     * @return
     */
    private boolean inGrid(int row, int col) {
        return row >= 0 && row < GRID_SIZE && col >= 0 && col < GRID_SIZE;
    }

    /**
     * Gets the layout of the map of the current state
     * @return the 2D array of the layout of vehicles in the grid.
     */
    public int[][] getGrid() {
        return this.grid;
    }

    /**
     * Gets the map of weights in a state
     * @return the 2D array of weights which can be used as a heuristic
     */
    public int[][] getWeights() {
        return this.weights;
    }

    /**
     * Gets the total number of moves which have been done to get to the current state
     * @return the number of moves.
     */
    public int getTotalMoves() {
        return this.total_moves;
    }

    /**
     * Gets a set of all the vehicles and relevant information about each one.
     * @return the map of vehicles
     */
    public Map<Integer, MapObject> getVehicles() {
        return vehicles;
    }

    /**
     * Moves a vehicle to a specified location
     * @param id        of the vehicle being moved
     * @param offset    which the vehicle is being moved too.
     * @return whether the vehicle was successfully moved or not.
     */
    private boolean moveVehicle(int id, int offset) {
        total_moves++;
        int row = EMPTY, col = EMPTY;
        boolean is_vert = false, is_found = false;
        for (int i = GRID_SIZE - 1; i >= 0; i--) {
            for (int j = GRID_SIZE - 1; j >= 0; j--) {
                if (this.grid[i][j] == id) {
                    row = i;
                    col = j;
                    is_found = true;
                    break;
                }
            }
            if (is_found) break;
            else return false;
        }
        int length = vehicles.get(id).getSize();
        switch (vehicles.get(id).getBearing()) {
            case RIGHT:
                for (int i = col; i > col - length; i--) {
                    this.grid[row][i] = EMPTY;
                }
                col += offset;
                for (int i = col; i > col - length; i--) {
                    this.grid[row][i] = id;
                }
                if (total_moves > max) return true;
                // Space behind
                for (int i = col - (length); i > col - 2 * length; i--) {
                    if (i < 0 || this.grid[row][i] != EMPTY) {
                        break;
                    }
                    checkHorizontal(id, row, i, ++this.weights[row][i]);
                    checkVertical(id, row, i, this.weights[row][i]);
                }
                // Space in front
                for (int i = col + 1; i < col + length; i++) {
                    if (i >= GRID_SIZE || this.grid[row][i] != EMPTY) {
                        break;
                    }
                    checkHorizontal(id, row, i, ++this.weights[row][i]);
                    checkVertical(id, row, i, this.weights[row][i]);
                }
                break;
            case DOWN:
                for (int i = row; i > row - length; i--) {
                    this.grid[i][col] = EMPTY;
                }
                row += offset;
                for (int i = row; i > row - length; i--) {
                    this.grid[i][col] = id;
                }
                if (total_moves > max) return true;
                // Space behind
                for (int i = row - (length); i > row - 2 * length; i--) {
                    if (i < 0 || this.grid[i][col] != EMPTY) {
                        break;
                    }
                    checkHorizontal(id, i, col, ++this.weights[i][col]);
                    checkVertical(id, i, col, this.weights[i][col]);
                }
                // Space in front
                for (int i = row + 1; i < row + length; i++) {
                    if (i >= GRID_SIZE || this.grid[i][col] != EMPTY) {
                        break;
                    }
                    checkHorizontal(id, i, col, ++this.weights[i][col]);
                    checkVertical(id, i, col, this.weights[i][col]);
                }
                break;
        }
        return true;
    }

    /**
     * Checks if the current state is "completed"
     * @return whether the state is a completed map
     */
    public boolean isComplete() {
        return total_moves >= max;
    }
}
