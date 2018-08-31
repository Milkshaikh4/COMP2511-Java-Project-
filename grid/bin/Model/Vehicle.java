package Model;

public class Vehicle implements MapObject {
	private int id;
	private final int length;
    private enum Directions {
        UP, DOWN, LEFT, RIGHT, NONE
    }
    private final Directions direction;
	private Location front;
	private Location top_left;

    /**
     * Creates a new vehicle based which will appear on the map.
     * @param id of the map object
     * @param length of the vehicle
     * @param front location of the vehicle
     * @param bearing is the forward direction of the vehicle
     */
	public Vehicle(int id, int length, Location front, int bearing) {
		this.id = id;
		this.length = length;
		this.front = front;
        switch (bearing) {
            case 0:
                this.direction = Directions.UP;
                this.top_left = new Location(front);
                break;
            case 90:
                this.direction = Directions.RIGHT;
                this.top_left = new Location(front, 0, -length);
                break;
            case 180:
                this.direction = Directions.DOWN;
                this.top_left = new Location(front, -length, 0);
                break;
            case 270:
                this.direction = Directions.LEFT;
                this.top_left = new Location(front);
                break;
            default:
                this.direction = Directions.NONE;
                break;
        }
		
	}

    /**
     * Creates a new vehicle based which will appear on the map.
     * @param id of the map object
     * @param length of the vehicle
     * @param front location of the vehicle
     * @param direction is the forward direction of the vehicle
     */
    public Vehicle(int id, int length, Location front, String direction) {
        this.id = id;
        this.length = length;
        this.front = front;
        direction = direction.toLowerCase();
        switch (direction) {
            case "up":
                this.direction = Directions.UP;
                this.top_left = new Location(front);
                break;
            case "right":
                this.direction = Directions.RIGHT;
                this.top_left = new Location(front, 0, -length);
                break;
            case "down":
                this.direction = Directions.DOWN;
                this.top_left = new Location(front, -length, 0);
                break;
            case "left":
                this.direction = Directions.LEFT;
                this.top_left = new Location(front);
                break;
            default:
                this.direction = Directions.NONE;
                break;
        }

    }

    /**
     * Retrieves the location of the object in the map
     *
     * @return the location of the object
     */
    @Override
    public Location getLocation() {
        return front;
    }

    /**
     * Moves the map object to a new location in the map.
     *
     * @param new_loc is the new location in the map.
     * @return whether the object could be moved to the
     * specified location or not
     */
    @Override
    public boolean moveObject(Location new_loc) {
        if (new_loc.getRow() < 0 || new_loc.getCol() < 0) {
            return false;
        }
        switch (this.direction) {
            case RIGHT:
                this.top_left = new Location(this.front, 0, -this.length);
                break;
            case DOWN:
                this.top_left = new Location(this.front, -this.length, 0);
                break;
            default:
                this.top_left = new Location(this.front);
                break;
        }
        this.front = new_loc;
        return true;
    }

    /**
     * Gets the size of the object.
     *
     * @return the size of the object.
     */
    @Override
    public int getSize() {
        return length;
    }

    /**
     * Returns the object's ID
     *
     * @return the object's ID
     */
    @Override
    public int getID() {
        return id;
    }

    /**
     * Returns the bearing/direction that the object will be moving
     * in if it moves forward.
     *
     * @return a value x where 0 <= x < 360
     */
    @Override
    public int getBearing() {
        switch (this.direction) {
            case UP:
                return 0;
            case RIGHT:
                return 90;
            case DOWN:
                return 180;
            case LEFT:
                return 270;
            default:
                return -1;
        }
    }
}
