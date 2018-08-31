package Model;

/**
 * Location on a 2D game map
 *
 *   col ->
 *
 *  row  ####
 *   |   ####
 *   V   ####
 *       ####
 */
public class Location {
	private int row;
	private int column;

	/**
	 * Create a new location based on horizontal and vertical coordinates
	 * @param row is the distance from the top
	 * @param column is the distance from the left
	 */
	public Location( int row, int column) {
		this.row = row;
		this.column = column;
	}

	public Location(Location origin, int row_offset, int col_offset) {
		this.row = origin.getRow() + row_offset;
		this.column = origin.getCol() + col_offset;
	}

	public Location(Location old) {
	    this.row = old.getRow();
	    this.column = old.getCol();
    }

	/**
	 * Gets the row value
	 * @return row
	 */
	public int getRow() {
		return this.row;
	}

	/**
	 * Gets the column value
	 * @return column
	 */
	public int getCol() {
		return this.column;
	}

    /**
     * Sets the row coordinate of a location to a new non-negative integer
     * @param row is the new row coordinate
     * @return whether the row coordinate was valid or not
     */
	public boolean setRow(int row) {
	    if (row < 0) {
	        return false;
        }
        this.row = row;
	    return true;
    }

    /**
     * Sets the column coordinate of a location to a new non-negative integer
     * @param col is the new column coordinate
     * @return whether the column coordinate was valid or not
     */
    public boolean setCol(int col) {
        if (col < 0) {
            return false;
        }
        this.column = col;
        return true;
    }
}
