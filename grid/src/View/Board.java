package View;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

import Controller.GameWorld;

public class Board implements IScene{
    private GridPane grid;
    private Scene scene;
    private List<Button> buttons;
    private GameWorld game;
    private Label timeLabel = new Label();
    private DoubleProperty timeSeconds = new SimpleDoubleProperty();
    private Duration time = Duration.ZERO;
    Timeline timeline;
    
    private static int BUTTON_SIZE = 25;

    public Board(int gridSize, GameWorld game) {
        grid = new GridPane();
        buttons = new ArrayList<>();
        this.game = game;
        createBoard(gridSize);
    }

    private void createBoard( int n) {
        //VGame will contain both HTop and HMid
        //HMid contains the grid
        //HTop will contain the scores and other info
        VBox VGame =  new VBox();
        HBox HTop = new HBox();
        HBox HMid  = new HBox();
        HBox HBottom = new HBox();

        VGame.setAlignment(Pos.CENTER);
        HMid.setAlignment(Pos.CENTER);
        HTop.setAlignment(Pos.CENTER);
        HBottom.setAlignment(Pos.CENTER);

        HBox.setHgrow(HTop, Priority.ALWAYS);
        VBox.setVgrow(VGame, Priority.ALWAYS);
        HBox.setHgrow(HMid, Priority.ALWAYS);
        HBox.setHgrow(HBottom, Priority.ALWAYS);

        VGame.getStyleClass().addAll("game-VGame");
        HTop.getStyleClass().addAll("game-HTop");
        HBottom.getStyleClass().addAll("game-HBottom");

        //setting the dimensions for the top bar
        HTop.setMinSize(GRID_WIDTH, TOP_HEIGHT);
        HTop.setPrefSize(GRID_WIDTH, TOP_HEIGHT);
        HTop.setMaxSize(GRID_WIDTH, TOP_HEIGHT);

        //setting the dimensions for the bottom bar
        HBottom.setMinSize(GRID_WIDTH, BOTTOM_HEIGHT);
        HBottom.setPrefSize(GRID_WIDTH, BOTTOM_HEIGHT);
        HBottom.setMaxSize(GRID_WIDTH, BOTTOM_HEIGHT);

        VGame.setMargin(HBottom, new Insets(GAP_HEIGHT, 0, 0, 0));

        //setting the dimensions for the whole window
        int padding = 10;
        VGame.setMaxSize(GRID_WIDTH + padding, TOP_HEIGHT+GRID_WIDTH + padding + BOTTOM_HEIGHT + GAP_HEIGHT);
        VGame.setMinSize(GRID_WIDTH + padding, TOP_HEIGHT+GRID_WIDTH + padding+ BOTTOM_HEIGHT+ GAP_HEIGHT);
        VGame.setPrefSize(GRID_WIDTH + padding, TOP_HEIGHT+GRID_WIDTH + padding+ BOTTOM_HEIGHT+ GAP_HEIGHT);

        //creates the actual grid with tiles
        grid.setVgap(GRID_GAP);
        grid.setHgap(GRID_GAP);
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(5, 5, 5,5 ));
        Rectangle tile;
        int row=0, col=0;
        for (int i=0; i<n*n; i++ ) {
            tile =  new Rectangle();
            tile.setArcHeight(10);
            tile.setArcWidth(10);
            tile.setHeight(CELL_SIZE);
            tile.setWidth(CELL_SIZE);
            tile.getStyleClass().addAll("game-grid-cell");

            grid.add(tile, row, col);
            if (row%(n-1)==0 && row!=0) {
                col++;
                row=0;
            } else {
                row++;
            }
        }
        grid.getStyleClass().addAll("game-grid");
        
        time = Duration.ZERO;
        timeLabel.textProperty().bind(timeSeconds.asString());
        timeLabel.getStyleClass().addAll("game-label" ,"game-scoreboard-text");
        timeline = new Timeline(
                new KeyFrame(Duration.millis(100),
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent t) {
                    		if(game.getReachGoal() == true) {
//                    			game.setTime(timeSeconds.floatValue());
                    			timeline.stop();
                    			time = Duration.ZERO;
                    		} else {
                    			Duration duration = ((KeyFrame)t.getSource()).getTime();
                    			time = time.add(duration);
                    			timeSeconds.set(time.toSeconds());
                    			game.setTime(timeSeconds.floatValue());
                    		}
                    }
                })
            );
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
            
        
        if (game.getNPlayer() == 1) {
        		Label ngame = new Label("Game " + (game.getScore()+1) + "     ");
        		ngame.getStyleClass().addAll("game-label" ,"game-scoreboard-text");
        		
        		Label time = new Label("Time : " );
        		time.getStyleClass().addAll("game-label" ,"game-scoreboard-text");
        		
        		HTop.getChildren().addAll(ngame, time, timeLabel);
        } else {
        		Label nplayer = new Label("Player " + game.getPlayer() + "     ");
        		nplayer.getStyleClass().addAll("game-label" ,"game-scoreboard-text");
        		Label time = new Label("Time : " );
        		time.getStyleClass().addAll("game-label" ,"game-scoreboard-text");
        		HTop.getChildren().addAll(nplayer, time, timeLabel);
        }
        //add all the buttons to the bottom bar
        
        //create home button
        Button home = new Button();
        home.getStyleClass().addAll("game-bottom-button", "home-button");
        home.setId("home");
        home.setMinSize(BUTTON_SIZE, BUTTON_SIZE);
        
        //create back button
        Button back = new Button();
        back.getStyleClass().addAll("game-bottom-button", "back-button");
        back.setId("back");
        back.setMinSize(BUTTON_SIZE, BUTTON_SIZE);

        //create restart button
        Button restart = new Button();
        restart.getStyleClass().addAll("game-bottom-button", "restart-button");
        restart.setId("restart");
        restart.setMinSize(BUTTON_SIZE, BUTTON_SIZE);

        //create quit button
        Button quit = new Button();
        quit.getStyleClass().addAll("game-bottom-button", "quit-button");
        quit.setId("quit");
        quit.setMinSize(BUTTON_SIZE, BUTTON_SIZE);

        //add buttons to bottom bar
        HBottom.getChildren().addAll(home, back, restart, quit);

        //add buttons to list
        buttons.add(quit);
        buttons.add(home);
        buttons.add(back);
        buttons.add(restart);


        //set margins for all the buttons
        HBottom.setMargin(home  , new Insets(padding, padding, padding, padding));
        HBottom.setMargin(quit  , new Insets(padding, padding, padding, padding));
        HBottom.setMargin(restart, new Insets(padding, padding, padding, padding));
        HBottom.setMargin(back  , new Insets(padding, padding, padding, padding));

        HMid.getChildren().add(grid);
        HBottom.getChildren().addAll();
        VGame.getChildren().addAll(HTop, HMid, HBottom);
        scene = new Scene(VGame);

        //Has to be an easy way to add CSS
        scene.getStylesheets().add(getClass().getResource("../View/application.css").toExternalForm());
    }

    public GridPane getGrid() {
        return grid;
    }

    /**
     * returns the scene object to be put into the primary Stage
     *
     * @return Scene object
     */
    @Override
    public Scene getScene() {
        return this.scene;
    }

    /**
     * returns list of buttons to observe
     *
     * @return list of Buttons
     */
    @Override
    public List<Button> getButtons() {
        return buttons;
    }
}
