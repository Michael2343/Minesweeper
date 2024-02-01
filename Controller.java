package mines;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Controller {
	private Mines gameMines;
	private GridPane gridMine = new GridPane();
	private boolean EndGame = false;
	private Stage controlStage;
	
	@FXML
	private TextField widthText;

    @FXML
    private TextField heightText;

    @FXML
    private TextField minesText;

    @FXML
    private Button reset;

    @FXML
    void resetAction(ActionEvent event) {
    	gridMine.getChildren().clear();
    	int heightUser = Integer.parseInt(heightText.getText());
    	int widthUser = Integer.parseInt(widthText.getText());
    	int minesUser = Integer.parseInt(minesText.getText());
    	gameMines = new Mines(heightUser,widthUser,minesUser);
    	createGridMine(heightUser,widthUser);
    	EndGame = false;
    	controlStage.sizeToScene();
    	if(gameMines.isDone()) Win();
    }
    
    private class gridButton extends Button{
		private int x,y;
		
		public gridButton(int x, int y) {
			super(".");
			this.x = x;
			this.y = y;
		}	
	}
    
    private void UpdateGrid() {
    	for(Node s:gridMine.getChildren()) {
			gridButton tmp = ((gridButton)s);
			tmp.setText(gameMines.get(tmp.x, tmp.y));	
		}
    }
    
    private class RightClick implements EventHandler<MouseEvent>{
		@Override
		public void handle(MouseEvent event) {
			if(!EndGame){
				if(event.getButton() == MouseButton.SECONDARY) {
					gridButton pressButton = (gridButton)event.getSource();
					gameMines.toggleFlag(pressButton.x, pressButton.y);
					UpdateGrid();
				}	
			}
		}
    }
    
    private void Win() {
    	UpdateGrid();
		Alert popAlert = new Alert(AlertType.CONFIRMATION);
		popAlert.setTitle("Game Won!");
		popAlert.setHeaderText("You are the WINNER!!");
		popAlert.setContentText("Nice Play!");
		popAlert.setGraphic(new ImageView("https://emoji.gg/assets/emoji/chickendinner.png"));
		popAlert.showAndWait();
		EndGame = true;
    }
    
    private void Lose() {
    	gameMines.setShowAll(true);
		UpdateGrid();
		Alert popAlert = new Alert(AlertType.CONFIRMATION);
		popAlert.setTitle("Game Lose!");
		popAlert.setHeaderText("You are the LOSER!!");
		popAlert.setContentText("Practice more");	
		popAlert.setGraphic(new ImageView("https://i.pinimg.com/474x/9a/94/91/9a9491c9673a92c57267863bca838e04.jpg"));
		popAlert.showAndWait();
		EndGame = true;
    }
    
    private class LeftClick implements EventHandler<ActionEvent> {
		@Override
		public void handle(ActionEvent event) {
			if(!EndGame) {
				gridButton pressButton = (gridButton)event.getSource();
				boolean turn = gameMines.open(pressButton.x, pressButton.y);
				UpdateGrid();
				if(	turn == false) Lose();
				else if(gameMines.isDone()) Win();
			}
		}
	}
    
    public void createGridMine(int heightUser,int widthUser) {	  	
		for(int x=0;x<heightUser;x++) {
			for(int y=0;y<widthUser;y++) {
				gridButton newButton = new gridButton(x,y);
				newButton.setOnAction(new LeftClick());
				newButton.setOnMouseClicked(new RightClick());
				newButton.setFont(new Font(20));
				newButton.setPrefSize(40,40);
				gridMine.add(newButton, y, x);
			}
		}
		gridMine.setPadding(new Insets(10));
	}

	public GridPane getGridMine() {
		return gridMine;
	}

	public void setStage(Stage stage) {
		controlStage = stage;		
	}


}
