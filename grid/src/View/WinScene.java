package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

import Controller.GameWorld;

public class WinScene implements IScene{
    private Scene winScene;
    private List<Button> buttons;

    private final int GRID_SIZE;
    private final int SCORE;
    private final int GRID_WIDTH;


    public WinScene (int grid_size, int score, GameWorld game) {
        GRID_SIZE = grid_size;
        SCORE = score;
        GRID_WIDTH = GRID_SIZE*CELL_SIZE+(GRID_SIZE+2)*GRID_GAP+1;

        buttons = new ArrayList<>();
        createScene(game);
    }

    /**
     * returns list of buttons to observe
     *
     * @return list of Buttons
     */
    public List<Button> getButtons() {
        return this.buttons;
    }

    /**
     * returns the scene object to be put into the primary Stage
     *
     * @return Scene object
     */
    public Scene getScene() {
        return winScene;
    }

    private void createScene(GameWorld game) {
    	VBox vBox = new VBox();
        VBox topBox = new VBox();	// maybe for score and game logo at top of scene
        VBox midBox = new VBox();	// for clear label at middle of scene
        VBox bottomBox = new VBox();	// for buttons to other page on bottom of scene

        // if there is only 1 player, then show stage cleared
    	if (game.getNPlayer() == 1) {
			Label score = new Label("Stages Cleared : "+ Integer.toString(SCORE) + "  ");    	        score.getStyleClass().addAll("game-label" ,"game-score-text");
            Label move = new Label("Move : " + game.getNMove() + "  ");
            move.getStyleClass().addAll("game-label" ,"game-currscore-text");
            Label currTime = new Label ("Time : " + game.getTimeArray(1) + "  ");
    		currTime.getStyleClass().addAll("game-label","game-currscore-text");
			topBox.getChildren().addAll(score, currTime, move);
    	        
    		Label clear = new Label("! C L E A R !");
    	    clear.getStyleClass().addAll("game-label","game-clear-text");
    	    midBox.getChildren().addAll(clear);
    	} else {	
            // if there are more than 1 player, show previous player detail
    		if (game.getPlayer() != 1) {
    			Label prev = new Label ("Previous player score ");
    			prev.getStyleClass().addAll("game-prevscore-text", "game-label");
    			topBox.getChildren().addAll(prev);
    		}
    			
    		for (int i = 1 ; i < game.getPlayer() ; i++) {
    			Label time = new Label("Player " + i + ", time : " + game.getTimeArray(i) + ", Move : " + game.getMoveArray(i) + "  ");
    			time.getStyleClass().addAll("game-prevscore-text", "game-label");
    			topBox.getChildren().addAll(time);
    		}
    			
    		Label currTime = new Label ("Player " + game.getPlayer() 
    				+ "\nTime : " + game.getTimeArray(game.getPlayer()) 
    				+ "\nMove : " + game.getNMove() + "  ");
    		currTime.getStyleClass().addAll("game-label","game-currscore-text");
    		midBox.getChildren().addAll(currTime);
    	}
    		
    	if (game.getNPlayer() == game.getPlayer()) {
    		Button nextButton = new Button("Next Game");
	        Button mainButton = new Button("Home");
            this.buttons.add(nextButton);
            this.buttons.add(mainButton);

	        nextButton.getStyleClass().addAll("game-button-2");
   	        mainButton.getStyleClass().addAll("game-button-2");
    	        
   	        bottomBox.setMargin(nextButton, new Insets(20, 20, 15, 20));
   	        bottomBox.setMargin(mainButton, new Insets(20, 20, 20, 20));
   	        bottomBox.getChildren().addAll(nextButton, mainButton);
    	            	        
   	        game.setPlayer(1);
		} else {
			int player_no = game.getPlayer();
			game.setPlayer(player_no + 1);
			Label nextPlayer = new Label("Next game for layer " + game.getPlayer());
			nextPlayer.getStyleClass().addAll("game-label" ,"game-score-text");
			bottomBox.getChildren().addAll(nextPlayer);
			
			Button startButton = new Button("START");
			Button homeButton = new Button("Home");
			this.buttons.add(startButton);
	        this.buttons.add(homeButton);
	        startButton.getStyleClass().addAll("game-button-2");
	        homeButton.getStyleClass().addAll("game-button-2");
	        
	        bottomBox.setMargin(startButton, new Insets(10, 20, 10, 20));
	        bottomBox.setMargin(homeButton, new Insets(0, 20, 5, 20));
	        bottomBox.getChildren().addAll(startButton, homeButton);
	        vBox.setMargin(bottomBox, new Insets(30, 0, 10, 0));
		}
    		

        // set location of each box
        vBox.setAlignment(Pos.CENTER);
        topBox.setAlignment(Pos.TOP_RIGHT);
        midBox.setAlignment(Pos.CENTER);
        bottomBox.setAlignment(Pos.BOTTOM_CENTER);

        // set size of boxes
        topBox.setMinSize(GRID_WIDTH, TOP_HEIGHT);
        topBox.setPrefSize(GRID_WIDTH, TOP_HEIGHT);
        topBox.setMaxSize(GRID_WIDTH, TOP_HEIGHT);

        midBox.setMinSize(GRID_WIDTH, TOP_HEIGHT);
        midBox.setPrefSize(GRID_WIDTH, TOP_HEIGHT);
        midBox.setMaxSize(GRID_WIDTH, TOP_HEIGHT);
        
        int padding = 10;
        vBox.setMaxSize(GRID_WIDTH + padding, TOP_HEIGHT+GRID_WIDTH + padding);
        vBox.setMinSize(GRID_WIDTH + padding, TOP_HEIGHT+GRID_WIDTH + padding);
        vBox.setPrefSize(GRID_WIDTH + padding, TOP_HEIGHT+GRID_WIDTH + padding);

        vBox.getChildren().addAll(topBox, midBox, bottomBox);
        vBox.setId("win-scene");
        Scene winScene = new Scene(vBox);
        this.winScene = winScene;
        winScene.getStylesheets().addAll(this.getClass().getResource("application.css").toExternalForm());
    }
}
