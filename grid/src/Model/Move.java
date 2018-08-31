package Model;

public class Move {
    private int id;
    private int offset;
    private int weight;
    private State prev_state;

    /**
     * Stores information about a potential move
     * @param prev      is the previous state before the move
     * @param id        of the vehicle which would be moved
     * @param offset    which the vehicle would be moved to
     * @param weight    of the move (heuristic)
     */
    public Move(State prev, int id, int offset, int weight) {
        this.id = id;
        this.offset = offset;
        this.weight = weight;
        this.prev_state = prev;
    }

    /**
     * Gets the ID of the vehicle which will be moved
     * @return the int of the ID
     */
    public int getID() {
        return id;
    }

    /**
     * Gets the offset the vehicle is moved to
     * @return  the integer offset of the move
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Gets the weight of the move (total + heuristic)
     * @return the weight state and the new move combined
     */
    public int getWeight() {
        return weight;
    }

    /**
     * Strings for debugging purposes
     * @return string representing the move
     */
    @Override
    public String toString() {
        return "Vehicle ".concat(Integer.toString(id)).concat(": ").concat(Integer.toString(offset)).concat(" -> ").concat(Integer.toString(weight));
    }

    /**
     * Gets the previous state before the move
     * @return the state before the move
     */
    public State getPrev() {
        return this.prev_state;
    }
}
