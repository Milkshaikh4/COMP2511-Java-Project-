package View;

import java.util.ArrayList;
import java.util.List;

import javafx.geometry.Insets;
	import javafx.geometry.Pos;
	import javafx.scene.Scene;
	import javafx.scene.control.Button;
	import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
	
public class SelectPlayerScene implements IScene{
	private Scene scene;
	private List<Button> buttons;
	
	public SelectPlayerScene() {
        this.buttons = new ArrayList<>();
        createScene();
    }
	
	private void createScene() {
		Button one = new Button("1 Player");
		Button two = new Button("2 Players");
		Button three = new Button("3 Players");
		Button four = new Button ("4 Players");
		Button back = new Button("BACK");
		buttons.add(one);
		buttons.add(two);
		buttons.add(three);
		buttons.add(four);
		buttons.add(back);
		
		one.getStyleClass().addAll("game-button");
		two.getStyleClass().addAll("game-button");
		three.getStyleClass().addAll("game-button");
		four.getStyleClass().addAll("game-button");
		back.getStyleClass().addAll("game-button-2");
		
		Label txt4 = new Label("How many player?");
		txt4.getStyleClass().addAll("game-label","game-get-player");
		
		HBox lBox = new HBox();
		VBox rBox = new VBox();
		VBox vBox3 = new VBox();
		VBox vBox4 = new VBox();
		
		vBox4.setMaxSize(GRID_WIDTH + PADDING, TOP_HEIGHT+GRID_WIDTH + PADDING);
		vBox4.setMinSize(GRID_WIDTH + PADDING, TOP_HEIGHT+GRID_WIDTH + PADDING);
		vBox4.setPrefSize(GRID_WIDTH + PADDING, TOP_HEIGHT+GRID_WIDTH + PADDING);
		
		vBox3.setAlignment(Pos.BOTTOM_CENTER);
		lBox.setAlignment(Pos.CENTER_LEFT);
		rBox.setAlignment(Pos.CENTER);
		
		
		vBox4.setMargin(lBox, new Insets(60, 20, 10, 20));
		vBox4.setMargin(rBox, new Insets(15, 20, 10, 20));
		rBox.setMargin(one, new Insets(10, 20, 10, 20));
		rBox.setMargin(two, new Insets(10, 20, 10, 20));
		rBox.setMargin(three, new Insets(10, 20, 10, 20));
		rBox.setMargin(four, new Insets(10, 20, 10, 20));
		vBox4.setMargin(vBox3, new Insets(20, 20, 10, 20));
		
		lBox.getChildren().addAll(txt4);
		rBox.getChildren().addAll(one, two, three, four);
		vBox3.getChildren().addAll(back);
		vBox4.getChildren().addAll(lBox, rBox, vBox3);
		vBox4.setId("third-page");
		
		this.scene = new Scene(vBox4);
		scene.getStylesheets().addAll(this.getClass().getResource("application.css").toExternalForm());
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
}
