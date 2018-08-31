package View;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

public class DiffScene implements IScene{
    private List<Button> buttons;
    private Scene scene;
    
    public DiffScene() {
        this.buttons = new ArrayList<>();

        createScene();
    }


    private void createScene() {
        // scene 2 for select game level
        Button eazyBtn = new Button("Easy");
        Button medBtn = new Button("Medium");
        Button hardBtn = new Button ("Hard");
        Button backBtn = new Button ("BACK");

        buttons.add(eazyBtn);
        buttons.add(medBtn);
        buttons.add(hardBtn);
        buttons.add(backBtn);

        //CSS styling
        eazyBtn.getStyleClass().addAll("game-button");
        medBtn.getStyleClass().addAll("game-button");
        hardBtn.getStyleClass().addAll("game-button");
        backBtn.getStyleClass().addAll("game-button-2");


        Label txt3 = new Label("Difficulty");
        txt3.getStyleClass().addAll("game-label","game-subtitle");

        VBox vBox2 = new VBox();
        vBox2.setAlignment(Pos.CENTER);
        vBox2.setSpacing(10);

        vBox2.setMargin(txt3, new Insets(0, 20, 10, 20));
        vBox2.setMargin(eazyBtn, new Insets(10, 20, 5, 20));
        vBox2.setMargin(medBtn, new Insets(5, 20, 5, 20));
        vBox2.setMargin(hardBtn, new Insets(5, 20, 30, 20));
        vBox2.getChildren().addAll(txt3, eazyBtn, medBtn, hardBtn, backBtn);
        vBox2.setId("second-page");

        vBox2.setMaxSize(GRID_WIDTH + PADDING, TOP_HEIGHT+GRID_WIDTH + PADDING);
        vBox2.setMinSize(GRID_WIDTH + PADDING, TOP_HEIGHT+GRID_WIDTH + PADDING);
        vBox2.setPrefSize(GRID_WIDTH + PADDING, TOP_HEIGHT+GRID_WIDTH + PADDING);

        this.scene = new Scene(vBox2);
        scene.getStylesheets().addAll(this.getClass().getResource("application.css").toExternalForm());
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
        return this.buttons;
    }
}
