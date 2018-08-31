package Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DummyMap implements MapGeneration {
    private Grid grid;
    private Map<Integer, MapObject> vehicles;
    private int num_vehicles = 0;

//    public DummyMap() {
//        generateMap(new Grid(), new HashMap<Integer, MapObject>(), 0);
//    }

    /**
     * Fills a grid with vehicles but also ensures the puzzle is solvable
     *
     * @param grid     refers to the 2D map which the vehicles can move on
     * @param vehicles refers to the list of vehicles in the grid
     */
    @Override
    public void generateMap(Grid grid, Map<Integer, MapObject> vehicles, int start_row, int difficulty) {
        this.num_vehicles = 0;
        this.grid = grid;
        this.vehicles = vehicles;
        //buildVehicle(6, 0, 0, 270);
        //buildVehicle(2, 5, 3, 90);
        //buildVehicle(2, 0, 5, 0);
        buildVehicle(2, 2, 2, 90);
        buildVehicle(2, 0, 3, 90);
        buildVehicle(2, 3, 3, 90);
        buildVehicle(2, 1, 1, 180);
        buildVehicle(2, 2, 3, 180);
        buildVehicle(2, 1, 4, 180);
        buildVehicle(2, 1, 5, 180);
        buildVehicle(2, 3, 5, 180);
        buildVehicle(2, 5, 3, 180);
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
}
