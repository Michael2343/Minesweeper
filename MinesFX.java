package mines;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MinesFX extends Application {
	@Override
	public void start(Stage Stage){
		HBox hbox;
		Controller control = new Controller();
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("MinesweeperFXML.fxml"));
			hbox =  loader.load();
			control = loader.getController();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		GridPane tmpGrid = control.getGridMine();
		hbox.getChildren().add(tmpGrid);
		Scene s = new Scene(hbox);
		Stage.setScene(s);
		Stage.setTitle("Minesweeper");
		control.setStage(Stage);
		Stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}