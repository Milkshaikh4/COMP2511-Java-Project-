package Controller;

import View.IView;
import View.Sprite;

public interface IController {

    /**
     * Creates a n by n board using the GridPane layout.
     *
     * @param n int value
     */
    void createBoard(int n);

    /**
     * starts the animation and game
     */
    void start();

    /**
     * updates the game's Score
     */
    void updateScore();

    /**
     * The move sprite function will try to
     * @return  integer depending on the number of places moved
     */
    int moveSprite(Sprite car, String dir);
}
