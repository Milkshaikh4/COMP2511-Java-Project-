package Controller;

import Model.*;
import View.*;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;
import java.util.Random;

public class GameWorld implements IController{
	public static int SCORE = 0; //keeps count of stages passed currently; placeholder value

    private static int EMPTY = -1;

    private int difficulty;
    private static int nplayer;
    private static int player;
    private float timePassed;
    private float[] timeArray;
    private boolean reachGoal;
    private int previd;
    private String prevmove;
    private int nMove;
    private int[] moveArray;

    public static int GRID_SIZE;

    private Stage primaryStage;
    private GridPane grid;
    private IModel model;
    private IView manager;


    /**
     * Given a state and a grid size it creates the board to used to play and
     * calls the backend to be used during the gameplay.
     *
     * @param primaryStage stage to be used
     * @param gridSize grid size to be used as length of cube
     */
    public GameWorld(Stage primaryStage, int gridSize) {
        this.primaryStage = primaryStage;
        GRID_SIZE = gridSize;

        this.timeArray = new float[5];
        this.moveArray = new int[5];
        this.player = 1;
        
        createIntroScene();
    }
    
    /**
     * Starts the game by creating the board and calling the Model
     * @param difficulty of game 1-3
     */
    private void startGame(int difficulty) {
        this.grid = new GridPane();
        this.manager = new VehicleManager(grid, GRID_SIZE);

        this.difficulty = difficulty;
        this.reachGoal = false;
        this.nMove = 0;

        model = new GridlockModel();

        //Create the size of the Board
        createBoard(GRID_SIZE);

        //Call model giving params
        model = new GridlockModel();
        model.startGame(difficulty);

        //Populate board with Sprites
        createSprites(model.getGrid().getGrid());

        winSceneListener();

        start();
        primaryStage.show();
    }

    private void restartGame() {
        grid = new GridPane();
        this.reachGoal = false;
        
        this.nMove = 0;
        createBoard(GRID_SIZE);

        this.manager.resetSprites(grid);

        createSprites(model.getGrid().getGrid());

        winSceneListener();
        
        start();

        primaryStage.setTitle("GRID LOCK");
        primaryStage.show();
    }

    private void newGame() {
        model.restartGame();

        grid = new GridPane();
        this.reachGoal = false;
        this.nMove = 0;
        
        createBoard(GRID_SIZE);

        this.manager.resetSprites(grid);

        createSprites(model.getGrid().getGrid());

        winSceneListener();

        start();

        primaryStage.setTitle("GRID LOCK");
        primaryStage.show();
    }

    /**
     * Generates a random name that corresponds to a image.
     *
     * @param type 2 - car, 3 - truck
     * @return name of random image
     */
    private String randomCarGernerator(int type, int spriteID) {
        Random rand = new Random();

        if (type == 2) {
            if (spriteID == 0)
                return "RedCar";
            switch (rand.nextInt(4)) {
                case 0: return "BlueCar";
                case 1: return "NavyblueCar";
                case 2: return "DarkblueCar";
                case 3: return "OrangeCar";
                case 4: return "YellowCar";
            }
        } else if (type == 3) {
            switch (rand.nextInt(4)) {
                case 0: return "GreyTruck";
                case 1: return "NavyblueTruck";
                case 2: return "RedTruck";
                case 3: return "WhiteTruck";
                case 4: return "YellowTruck";
            }
        }

        //just to please the compiler
        return "";
    }

    private void showGrid(int [][] grid) {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                if (grid[i][j] != EMPTY) {
                    System.out.print(grid[i][j]);
                } else {
                    System.out.print("#");
                }
                System.out.print(" ");
            }
            System.out.println("\n");
        }
    }

    /**
     * adds the sprites delivered from the Model into the vehicleManger(View).
     * Also adds event handlers to every sprite
     *
     * @param grid GridPane to be populated
     */
    private void createSprites(int [][] grid) {
        showGrid(grid);
        this.manager.resetSprites(this.grid);

        int start = -1;
        int length = 0;
        int lastVal = -1;
        //for loop to build horizontal cars
        for (int row = 0; row<GRID_SIZE; row++) {
            lastVal = grid[row][0];
            for (int col = 0; col<GRID_SIZE; col++) {
                if (grid[row][col] != EMPTY && grid[row][col] == lastVal) {
                    if (start == -1)
                        start = col;
                    length++;
                } else {
                    if (length>1) {
                        manager.addSprite(row, start, lastVal, length, "Horizontal", randomCarGernerator(length, lastVal));
                    }
                    start = col;
                    length = 1;
                    lastVal = grid[row][col];
                }
            }
            if (length>1) {
                manager.addSprite(row, start, lastVal, length, "Horizontal", randomCarGernerator(length, lastVal));
            }
            start = -1;
            length = 0;
        }

        start = -1;
        length = 0;
        lastVal = -1;
        for (int col = 0; col<GRID_SIZE; col++) {
            lastVal = grid[0][col];
            for (int row = 0; row<GRID_SIZE; row++) {
                if (grid[row][col] != EMPTY && grid[row][col] == lastVal) {
                    if (start == -1)
                        start = row;

                    length++;
                } else {
                    if (length>1) {
                        manager.addSprite(start, col, lastVal, length, "Vertical", randomCarGernerator(length, lastVal));
                    }
                    start = row;
                    length = 1;
                    lastVal = grid[row][col];
                }
            }
            if (length>1) {
                manager.addSprite(start, col, lastVal, length, "Vertical", randomCarGernerator(length, lastVal));
            }
            start = -1;
            length = 0;
        }

        for (Sprite sprite : this.manager.getSprites()) {
            //adds the event handlers depending on the direction of the vehicles
            if (sprite.getDirection().equals("Horizontal")) {
                sprite.getImage().addEventHandler(MouseEvent.MOUSE_DRAGGED, (MouseEvent event) -> {
                    ImageView node = (ImageView) event.getSource();
                    Sprite s = manager.findSprite(node);

                    synchronized (s) {
                        if (event.getX() >= 100) {
                            if (manager.moveSprite(s, "right") ) {
                                moveSprite(s, "right");
                            }
                        } else if (event.getX() <= 0) {
                            if ( manager.moveSprite(s, "left") ) {
                                moveSprite(s, "left");
                            }
                        }
                    }
                });
            } else {
                sprite.getImage().addEventHandler(MouseEvent.MOUSE_DRAGGED, (MouseEvent event) -> {
                    ImageView node = (ImageView) event.getSource();
                    Sprite s = manager.findSprite(node);

                    synchronized (s) {
                        if (event.getY() >= 100) {
                            if ( manager.moveSprite(s, "down") ) {
                                moveSprite(s, "down");
                            }
                        } else if (event.getY() <= 0) {
                            if ( manager.moveSprite(s, "up") ) {
                                moveSprite(s, "up");
                            }
                        }
                    }
                });
            }
        }
    }

    /**
     * starts the game by rendering the sprites on the grid
     */
    public void start() {
        manager.renderSprites();
    }

    /**
     * Creates a n by n board using the GridPane layout.
     *
     * @param n int value
     */
    @Override
    public void createBoard(int n) {
        Board board = new Board(n, this);

        for (Button b : board.getButtons()) {
            switch (b.getId()) {
                case "home":
                    b.setOnAction(e->createIntroScene());
                    break;

                case "back":
                    b.setOnAction(e->createDifficultySelectionScene());
                    break;

                case "quit":
                    b.setOnAction(e->primaryStage.close());
                    break;

                case "restart":
                    b.setOnAction(e->restartGame());
                    break;
            }
        }

        this.grid = board.getGrid();
        primaryStage.setScene(board.getScene());
    }

    /**
     * The move sprite function will try to
     *
     * @return integer depending on the number of places moved
     */
    @Override
    public int moveSprite(Sprite car, String dir) {
    	if (previd != car.getID() || prevmove != dir) {
    		nMove++;
    	}
        System.out.println("Move " + car.getID()+" " + dir);
        this.previd = car.getID();
        this.prevmove = dir;
        return 0;
    }

    /**
     * creates a introScene object to then be put onto the primaryStage
     *
     * Also adds event handlers to the buttons
     */
    private void createIntroScene() {
        IntroScene intro = new IntroScene();

        for (Button b : intro.getButtons()) {
            if (b.getText().equals("START GAME")) {
                b.setOnAction(e -> createSelectPlayerScene());
            } else if (b.getText().equals("QUIT")) {
                b.setOnAction(e -> primaryStage.close());
            }
        }

        // set primary stage
        primaryStage.setTitle("GRID LOCK");
        primaryStage.setScene(intro.getScene());
    }

    /**
     * creates a difficulty selecting scene object to then be put onto the primaryStage
     *
     * Also adds event handlers to the buttons
     */
    private void createDifficultySelectionScene() {
        IScene diff = new DiffScene();

        for (Button b : diff.getButtons()) {
            switch (b.getText()) {
                case "Easy":
                    b.setOnAction(e -> startGame(1));
                    this.difficulty = 1;
                    break;

                case "Medium":
                    b.setOnAction(e -> startGame(2));
                    this.difficulty = 2;
                    break;

                case "Hard":
                    b.setOnAction(e -> startGame(3));
                    this.difficulty = 3;
                    break;

                case "BACK":
                    b.setOnAction(e -> createSelectPlayerScene());
                    break;
            }
        }

        primaryStage.setTitle("GRID LOCK");
        primaryStage.setScene(diff.getScene());
    }

    /**
     * creates a create a select player scene object to then be put onto the primaryStage
     *
     * Also adds event handlers to the buttons
     */
    private void createSelectPlayerScene() {
    		IScene playerScene = new SelectPlayerScene();
    		
    		for (Button b : playerScene.getButtons()) {
        		switch (b.getText()) {
        			case "1 Player" :
        				b.setOnAction(e -> {
        					this.nplayer = 1;
        					this.player = 1;
        					this.SCORE = 0;
        					createDifficultySelectionScene();
        				});
        				break;
        				
        			case "2 Players" :
        				b.setOnAction(e -> {
        					this.nplayer = 2;
        					this.player = 1;
        					this.SCORE = 0;
        					createDifficultySelectionScene();
        				});
        				break;
        				
        			case "3 Players" :
        				b.setOnAction(e -> {
        					this.nplayer = 3;
        					this.player = 1;
        					this.SCORE = 0;
        					createDifficultySelectionScene();
        				});
        				break;
        				
        			case "4 Players" :
        				b.setOnAction(e -> {
        					this.nplayer = 4;
        					this.player = 1;
        					this.SCORE = 0;
        					createDifficultySelectionScene();
        				});
        				break;
        				
        			case "BACK" :
        				b.setOnAction(e -> {
        					createIntroScene();
        				});
        		}
        }

        primaryStage.setTitle("GRID LOCK");
        primaryStage.setScene(playerScene.getScene());
    }


    /**
     * Finds the redCar from manager (Assuming that the redCar will always have a spriteID of 0)
     * and register a actionListener to the object. If the car reaches the goal state then take the viewer
     * to the win scene.
     */
    public void winSceneListener() {
        Sprite redCar  = manager.findSprite(0);
        int goalState = GRID_SIZE-redCar.getLength();

        redCar.getImage().addEventFilter(MouseEvent.MOUSE_RELEASED, (MouseEvent event) -> {
            if (redCar.getCol() == goalState) {
            		if (nplayer == player) SCORE++;
            		this.reachGoal = true;
            		timeArray[player] = timePassed;
            		moveArray[player] = nMove;
                winScene();
            }
        });
    }

    /**
     * creates a win scene and adds event handlers
     *
     */
    private void winScene() {

        IScene winScene = new WinScene(GRID_SIZE,SCORE, this);

        for (Button b : winScene.getButtons()) {
            switch (b.getText()) {
            case "Next Game":
                b.setOnAction(e -> newGame());
                break;

            case "Home":
                b.setOnAction(e -> createIntroScene());
                break;

            case "START":
                b.setOnAction(e -> restartGame());
                break;
            }
        }

        primaryStage.setTitle("! C L E A R !");


        primaryStage.setScene(winScene.getScene());
        primaryStage.show();
    }

    /**
     * @return number of players for multi-player
     */
    public int getNPlayer() {
		return this.nplayer;
    }

    /**
    * @return player id that playing
    */
    public int getPlayer() {
		return this.player;
    }

    /**
    *  set player that playing next game
    */
    public void setPlayer(int player_number) {
		this.player = player_number;
    }

    /**
    * @return time of each player
    */
    public float getTimeArray(int index) {
		return timeArray[index];
    }
    
    /**
    * @return number of 'index' player move object
    */
    public int getMoveArray(int index) {
    		return moveArray[index];
    }
    
    /**
    * @return score of game
    */
    public int getScore() {
    		return SCORE;
    }
    
    /**
    *   set time from time counter in board while playing
    */
    public void setTime(float time) {
    	this.timePassed = time;
    }
    
    /**
    * @return is it reachgoal already
    */
    public boolean getReachGoal() {
    		return this.reachGoal;
    }

    /**
    *   @return number of object is moved
    */
    public int getNMove() {
    		return this.nMove;
    }

	@Override
	public void updateScore() {
		
	}
}
