package View;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import java.util.List;

/**
 * Interface for interacting with different scenes in the game. Plus provides some comformaty to the observer pattern
 */
public interface IScene {
    int GRID_GAP = 5;
    int CELL_SIZE = 50;
    int TOP_HEIGHT = 100;
    int GAP_HEIGHT = 10;
    int BOTTOM_HEIGHT = 50;
    int PADDING = 10;
    int GRID_SIZE = 6;
    int GRID_WIDTH = GRID_SIZE*CELL_SIZE+(GRID_SIZE+2)*GRID_GAP+1;;

    /**
     * returns the scene object to be put into the primary Stage
     *
     * @return Scene object
     */
    Scene getScene();

    /**
     * returns list of buttons to observe
     *
     * @return list of Buttons
     */
    List<Button> getButtons();
}
