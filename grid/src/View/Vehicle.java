package View;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class Vehicle  implements Sprite {
    private ImageView image;
    private final int spriteID;
    private int row;
    private int col;
    private final String direction;
    private final int length;
    private double width;
    private double height;

    private static final int CELL_SIZE = 50;

    /**
     * Creates a Vehicle class of the length and direction needed. Length must be >0 and
     * Direction must be eiterh "Horizontal" or "Vertical"
     *
     * @param length length of sprite starting from 1 to 3 inclusive
     * @param direction Orientation of the sprite => "Horizontal" or "Vertical"
     * @param spriteID identification number of sprite
     */
    public Vehicle(int length, String direction, int spriteID) {
        this.direction = direction;
        this.length = length;
        this.spriteID = spriteID;
    }

    /**
     * returns the ID of the sprite
     *
     * @return spriteID field
     */
    @Override
    public int getID() {
        return this.spriteID;
    }

    /**
     * Returns the ImageView that's associated with this sprite. The corresponding image representation
     * in the GUI
     *
     * @return ImageView object
     */
    @Override
    public ImageView getImage() {
        return image;
    }

    /**
     * Returns Orientation of sprite
     *
     * @return "Vertical" or "Horizontal"
     */
    @Override
    public String getDirection() {
        return direction;
    }

    /**
     * length of sprite
     *
     * @return length>1
     */
    @Override
    public int getLength() {
        return length;
    }

    /**
     * returns the row of the sprite
     *
     * @return 0<row<GRID_SIZE
     */
    @Override
    public int getRow() {
        return row;
    }

    /**
     * returns the column of the sprite
     *
     * @return  0<col<GRID_SIZE
     */
    @Override
    public int getCol() {
        return col;
    }

    /**
     * Given an Image, it creates an ImageView to store for later use
     *
     * @param i Image object
     */
    @Override
    public void setImage(Image i) {
        image = new ImageView(i);
        if (length == 2) {
            if (this.direction.equals("Horizontal")) {
                height = 50;
                width = 105;
            } else {
                height = 105;
                width = 50;
            }
        } else {
            if (this.direction.equals("Horizontal")) {
                height = 50;
                width = 155;
            } else {
                height = 155;
                width = 50;
            }
        }
        image.setFitWidth(width);
        image.setFitHeight(height);
    }

    /**
     * Given a filename, it creates an ImageView to store for later use
     *
     * @param filename path of the image + filename
     */
    @Override
    public void setImage(String filename) {
        Image i = new Image(filename);
        setImage(i);
    }

    /**
     * Stores the Sprite's grid location defined by rows and columns
     *
     * @param row 0<row<GRID_SIZE of sprite
     * @param col 0<col<GRID_SIZE of sprite
     */
    @Override
    public void setGridLocation(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * renders the Sprite to a grid location defined by the rows and columns
     * stored within the object
     *
     * @pre row and col must be set within the object
     * @param grid GridPane object
     */
    @Override
    public void render(GridPane grid) {
        if (direction.equals("Horizontal")) {
            grid.add(image, col, row, length, 1);
        } else {
            grid.add(image, col, row, 1, length);
        }
    }

    /**
     * Updates the position of the sprite within the grid.
     *
     * @pre row and col must be set within the object
     * @param grid GridPane object
     */
    @Override
    public void update(GridPane grid) {
        if (direction.equals("Horizontal")) {
        		image.setLayoutX(col * CELL_SIZE);
            GridPane.setConstraints(image, col, row, length, 1);
        } else {
        		image.setLayoutY(row * CELL_SIZE);
            GridPane.setConstraints(image, col, row, 1, length);
        }
    }

    @Override
    public String toString()
    {
        return "Position in grid: [" + row + "," + col + "]";
    }

}
