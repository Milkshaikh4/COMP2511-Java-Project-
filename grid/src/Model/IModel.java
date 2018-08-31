package Model;

public interface IModel {
    /**
     * Restarts the gridlock game
     */
    void restartGame();

    /**
     * Start a new game by reseting the grid and generate 3 new vehicles
     */
    void startGame(int difficulty);

    /**
     * Gets the grid for use within the controller
     *
     * @return 2-dimensional array
     */
    Grid getGrid();

    /**
     * Moves an object of a specified ID a certain distance forward or backwards.
     * @param id is the ID of the vehicle
     * @param dist is the distance forward (or backwards if negative)
     * @return whether the vehicle could be moved to that location or not
     */
    boolean moveVehicle(int id, int dist);
}
