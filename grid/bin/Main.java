
import javafx.application.Application;
import Controller.*;
import javafx.stage.Stage;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setResizable(false);

			primaryStage.setTitle("Grid");

			IController world = new GameWorld(primaryStage, 6);

			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}

}
