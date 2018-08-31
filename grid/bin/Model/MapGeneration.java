package Model;

import java.util.List;
import java.util.Map;

/**
 * Generate a solvable map.
 *
 * This interface allows the map generator to be modular, meaning different types
 * of map generation can be used interchangeably.
 */
public interface MapGeneration {
    /**
     * Fills a grid with vehicles but also ensures the puzzle is solvable
     * @param grid refers to the 2D map which the vehicles can move on
     * @param vehicles refers to the list of vehicles in the grid
     */
    void generateMap(Grid grid, Map<Integer, MapObject> vehicles, int start_row, int difficulty);

    /**
     * Populates a list of IDs and locations which is a potential solution to the puzzle.
     * @param id_list is the MapObject ID which is being moved.
     * @param move_list is the list of locations which the vehicles are moved to.
     */
    void solveMap(List<Integer> id_list, List<Location> move_list);
}
