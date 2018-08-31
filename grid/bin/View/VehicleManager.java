package View;

import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import java.util.ArrayList;
import java.util.List;

/**
 * This class keeps a record of all sprites in the scene and manager's events and
 * renders objects on command.
 *
 */
public class VehicleManager implements IView{
    private List<Sprite> vehicles;
    private GridPane grid;
    private int GRID_SIZE;

    /**
     * creates a vehicle manager object with the grid
     *
     * @param grid GridPane Object of the game Board
     */
    public VehicleManager(GridPane grid, int GRID_SIZE) {
        vehicles = new ArrayList<>();
        this.GRID_SIZE = GRID_SIZE;
        this.grid = grid;
    }

    /**
     * resets all the sprites and resets the grid
     *
     * @param grid new GridPane
     */
    @Override
    public void resetSprites(GridPane grid) {
        this.grid = grid;
        this.vehicles = new ArrayList<>();
    }

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
    @Override
    public void addSprite(int row, int col, int spriteID, int length, String dir, String name) {
        Sprite car = new Vehicle(length, dir, spriteID);

        car.setGridLocation(row, col);
        if (spriteID == 0) {
            //Do nothing cause name="RedCar"
        } else if (dir == "Horizontal") {
            name = name.concat("H");
        } else {
            name = name.concat("V");
        }
        name = name.concat(".png");

        String path = "";
        if (length == 2) {
            path = "/View/Assets/Car/";
        } else {
            path = "/View/Assets/Truck/";
        }
        path = path.concat(name);
        //System.out.println(path);
        car.setImage(path);

        this.vehicles.add(car);
    }

    /**
     * Spritemanager will begin to render the sprites it has in it's collection to the screen.
     */
    @Override
    public void renderSprites() {
        for (Sprite sprite : vehicles) {
            sprite.render(grid);
        }
    }

    /**
     * given an ImageView it will find a Sprite that has this image
     *
     * @param image the Imageview
     * @return Sprite object if found.
     *         Null if not
     */
    public Sprite findSprite(ImageView image) {
        for (Sprite s : vehicles) {
            if (s.getImage().equals(image)) return s;
        }
        return null;
    }

    /**
     * given a spriteID, this method will return the object instance of that Sprite
     *
     * @param spriteID sprite's identification number
     * @return Sprite object
     *         null if not found
     */
    @Override
    public Sprite findSprite(int spriteID) {
        for (Sprite s : vehicles) {
            if (s.getID() == spriteID) return s;
        }
        return null;
    }

    /**
     * returns a list of sprites in the grid
     *
     * @return List<Sprite>
     */
    @Override
    public List<Sprite> getSprites() {
        return vehicles;
    }

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
    @Override
    public synchronized boolean moveSprite(Sprite sprite, String dir) {
        if (sprite.getDirection().equals("Horizontal")) {
            if (dir.equals("left") && isValidMove(sprite, "left")) {
                if (sprite.getCol()>0) {
                    sprite.setGridLocation(sprite.getRow(), sprite.getCol()-1);
                    sprite.update(grid);
                    return true;
                }
            } else if (dir.equals("right") && isValidMove(sprite, "right")) {
                if (sprite.getCol()+sprite.getLength()<GRID_SIZE) {
                    sprite.setGridLocation(sprite.getRow(), sprite.getCol()+1);
                    sprite.update(grid);
                    return true;
                }
            }
        } else {
            if (dir.equals("up") && isValidMove(sprite, "up")) {
                if (sprite.getRow()>0) {
                    sprite.setGridLocation(sprite.getRow()-1, sprite.getCol());
                    sprite.update(grid);
                    return true;
                }
            } else if (dir.equals("down") && isValidMove(sprite, "down")) {
                if (sprite.getRow()+sprite.getLength()<GRID_SIZE) {
                    sprite.setGridLocation(sprite.getRow() + 1, sprite.getCol());
                    sprite.update(grid);
                    return true;
                }
            }
        }
        sprite.update(grid);
        return false;
    }

    /**
     * Returns a true or false depending on whether or not the Sprite can perform this action
     *
     * @param sprite the sprite being controlled by the user
     * @param dir the direction the Sprite wants to go ("left", "right", "up", "down")
     * @return returns true/false
     */
    private boolean isValidMove(Sprite sprite, String dir) {
        switch (dir) {
            case "left":
                for (Sprite s : vehicles) {
                    if (s.getDirection().equals("Horizontal")) {
                        if (sprite.getRow() == s.getRow() && sprite.getCol() == s.getCol() + s.getLength()) return false;
                    } else {
                        for (int i = 0; i<s.getLength(); i++)
                            if (sprite.getRow() == s.getRow()+i && sprite.getCol()-1 == s.getCol()) return false;
                    }
                }

                break;
            case "right":
                for (Sprite s : vehicles) {
                    if (s.getDirection().equals("Horizontal")) {
                        if (sprite.getRow() == s.getRow() && sprite.getCol() + sprite.getLength() == s.getCol()) return false;
                    } else {
                        for (int i = 0; i<s.getLength(); i++)
                            if (sprite.getRow() == s.getRow()+i && sprite.getCol() + sprite.getLength() == s.getCol()) return false;
                    }
                }

                break;
            case "up":
                for (Sprite s : vehicles) {
                    if (s.getDirection().equals("Vertical")) {
                        if (sprite.getCol() == s.getCol() && sprite.getRow() == s.getRow() + s.getLength()) return false;
                    } else {
                        for (int i = 0; i<s.getLength(); i++)
                            if (sprite.getCol() == s.getCol()+i && sprite.getRow()-1 == s.getRow()) return false;
                    }
                }
                break;
            case "down":
                for (Sprite s : vehicles) {
                    if (s.getDirection().equals("Vertical")) {
                        if (sprite.getCol() == s.getCol() && sprite.getRow() + sprite.getLength() == s.getRow()) return false;
                    } else {
                        for (int i = 0; i<s.getLength(); i++)
                            if (sprite.getCol() == s.getCol()+i && sprite.getRow() + sprite.getLength() == s.getRow()) return false;
                    }
                }
                break;
        }

        return true;
    }
}
