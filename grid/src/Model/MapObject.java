package Model;

public interface MapObject {

    /**
     * Retrieves the location of the object in the map
     * @return the location of the object
     */
    Location getLocation();

    /**
     * Moves the map object to a new location in the map.
     * @param new_loc is the new location in the map.
     * @return whether the object could be moved to the
     *         specified location or not
     */
    boolean moveObject(Location new_loc);

    /**
     * Gets the size of the object.
     * @return the size of the object.
     */
    int getSize();

    /**
     * Returns the object's ID
     * @return the object's ID
     */
    int getID();

    /**
     * Returns the bearing/direction that the object will be moving
     * in if it moves forward.
     * @return a value x where 0 <= x < 360 or -1 if the object cannot move
     */
    int getBearing();
}
