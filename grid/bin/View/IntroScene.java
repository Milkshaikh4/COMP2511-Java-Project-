package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class IntroScene implements IScene {
	private Scene scene;
	private List<Button> buttons;
	
	public IntroScene(){
		buttons = new ArrayList<>();

		createScene();
	}

	/**
	 * returns list of buttons to observe
	 *
	 * @return list of Buttons
	 */
	public List<Button> getButtons() {
		return buttons;
	}

	/**
	 * returns the scene object to be put into the primary Stage
	 *
	 * @return Scene object
	 */
	public Scene getScene() {
		return this.scene;
	}

	private void createScene() {

		// scene1 1 for first visit game
		Button startButton = new Button("START GAME");
		Button quitButton = new Button("QUIT");
		buttons.add(startButton);
		buttons.add(quitButton);

		startButton.getStyleClass().addAll("game-button-2");
		quitButton.getStyleClass().addAll("game-button-2");

		Label txt = new Label("Grid\n" + "Lock");
		txt.getStyleClass().addAll("game-label" ,"game-title");

		VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		vBox.setSpacing(10);

		vBox.setMargin(txt, new Insets(-20, 10, 10, -170));
		vBox.setMargin(startButton, new Insets(20, 10, 5, 190));
		vBox.setMargin(quitButton, new Insets(5, 10, 10, 190));
		vBox.setId("first-page");

		vBox.getChildren().addAll(txt, startButton, quitButton);

		vBox.setMaxSize(GRID_WIDTH + PADDING, TOP_HEIGHT+GRID_WIDTH + PADDING);
		vBox.setMinSize(GRID_WIDTH + PADDING, TOP_HEIGHT+GRID_WIDTH + PADDING);
		vBox.setPrefSize(GRID_WIDTH + PADDING, TOP_HEIGHT+GRID_WIDTH + PADDING);

		this.scene = new Scene(vBox);
		scene.getStylesheets().addAll(this.getClass().getResource("application.css").toExternalForm());
	}
}
