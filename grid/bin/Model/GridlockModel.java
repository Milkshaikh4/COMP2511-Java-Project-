package Model;

import java.util.HashMap;
import java.util.Map;

/**
 * This will be the model (M) of the MVC application. This will contain functions
 * which can be called by the controller (C) and make function calls to the view (V).
 */
public class GridlockModel implements IModel{
    private Grid grid = new Grid();
    private Map<Integer, MapObject> vehicles = new HashMap<>();
    private int num_vehicles = 0;
    private MapGeneration generator;

    /**
     * Restarts the gridlock game
     */
    public void restartGame() {
        grid.resetGrid();
        vehicles.clear();
        startGame(1);
    }

    /**
     * Start a new game by reseting the grid and generate 3 new vehicles
     */
    public void startGame(int difficulty) {
        if (difficulty > 2) {
            generator = new DummyMap();
        } else {
            generator = new ReverseSolve();
        }
        generator.generateMap(this.grid, this.vehicles, 2, difficulty);
    }

    /**
     * Moves an object of a specified ID a certain distance forward or backwards.
     * @param id is the ID of the vehicle
     * @param dist is the distance forward (or backwards if negative)
     * @return whether the vehicle could be moved to that location or not
     */
    public boolean moveVehicle(int id, int dist) {
        MapObject mobile_vehicle = vehicles.get(id);
        return false;
    }

    /**
     * Builds a new vehicle only if there is enough space on the grid for it
     * @param length of the vehicle
     * @param row is the vertical coordinate on the grid (units from the top)
     * @param col is the horizontal coordinate on the grid (units from the left)
     * @param bearing is the direction the vehicle is face (should be 0, 90, 180 or 270)
     */
    private void buildVehicle(int length, int row, int col, int bearing) {
        if (!grid.insertVehicle(num_vehicles, length, row, col, bearing)) return;
        Location front = new Location(row, col);
        MapObject new_object = new Vehicle(num_vehicles, length, front, bearing);
        vehicles.put(new_object.getID(), new_object);
        num_vehicles++;
    }

    public Grid getGrid() {
        return grid;
    }

    public static void main(String arg[]) {
        GridlockModel system = new GridlockModel();
        system.startGame(1);
        system.grid.showGrid();
    }
}
