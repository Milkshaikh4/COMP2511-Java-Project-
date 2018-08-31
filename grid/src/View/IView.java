package View;

import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.util.List;

public interface IView {

    /**
     * resets all the sprites and resets the grid
     *
     *
     * @param grid new GridPane
     */
    void resetSprites(GridPane grid );

    /**
     * returns a list of sprites in the grid
     *
     * @return List<Sprite>
     */
    List<Sprite> getSprites();

    /**
     * creates a sprite to be added to the list. sprite manager will add these
     * to its personal collection of spites. It will NOT render them yet.
     *
     * @param row the top left most row of the sprite
     * @param col the top left most column of the sprite
     * @param spriteID identification number
     * @param length length of the sprite starting from 1
     * @param dir Orientation of the sprite => "Horizontal" or "Vertical"
     */
    void addSprite(int row, int col, int spriteID, int length, String dir, String name);

    /**
     * Spritemanager will begin to render the sprites it has in it's collection to the screen.
     */
    void renderSprites();

    /**
     * given an ImageView it will find a Sprite that has this image
     *
     * @param image the Imageview
     * @return Sprite object if found.
     *         Null if not
     */
    Sprite findSprite(ImageView image);

    /**
     * given a spriteID, this method will return the object instance of that Sprite
     *
     * @param spriteID sprite's identification number
     * @return Sprite object
     *         Null if not found
     */
    Sprite findSprite(int spriteID );

    /**
     * Given a spriteID and the information to move the sprite in the direction wanted. The manager
     * will move the sprite to the desired location, however, it will return a true or a false depending
     * on whether this action can or cannot be taken. This depends on whether the sprite has collided with
     * another sprite.
     *
     * @param sprite sprite instance to be moved
     * @param dir direction of movement ("left", "right", "up", "down")
     * @return true/false
     */
    boolean moveSprite(Sprite sprite, String dir);
}
