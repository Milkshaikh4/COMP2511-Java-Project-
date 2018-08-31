package View;

import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.image.ImageView;

public interface Sprite {

    /**
    * returns the ID of the sprite
    *
    * @return spriteID field
    */
    int getID();

    /**
    * Returns the ImageView that's associated with this sprite. The corresponding image representation
    * in the GUI
    *
    * @return ImageView object
    */
    ImageView getImage();

    /**
    * Returns Orientation of sprite
    *
    * @return "Vertical" or "Horizontal"
    */
    String getDirection();

    /**
    * length of sprite
    *
    * @return length>1
    */
    int getLength();

    /**
    * returns the row of the sprite
    *
    * @return 0<row<GRID_SIZE
    */
    int getRow();

    /**
    * returns the column of the sprite
    *
    * @return  0<col<GRID_SIZE
    */
    int getCol();

    /**
    * Given an Image, it creates an ImageView to store for later use
    *
    * @param i Image object
    */
    void setImage(Image i);

    /**
    * Given a filename, it creates an ImageView to store for later use
    *
    * @param filename path of the image + filename
    */
    void setImage(String filename);

    /**
    * Stores the Sprite's grid location defined by rows and columns
    *
    * @param row 0<row<GRID_SIZE of sprite
    * @param col 0<col<GRID_SIZE of sprite
    */
    void setGridLocation(int row, int col);

    /**
    * renders the Sprite to a grid location defined by the rows and columns
    * stored within the object
    *
    * @pre row and col must be set within the object
    * @param grid GridPane object
    */
    void render(GridPane grid);

    /**
    * Updates the position of the sprite within the grid.
    *
    * @pre row and col must be set within the object
    * @param grid GridPane object
    */
    void update(GridPane grid);

    String toString();
}
