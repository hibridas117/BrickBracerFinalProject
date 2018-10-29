package lt.vaitkus.graphic;


import java.awt.Label;
import java.awt.Window;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lt.vaitkus.MainWindow;

public class HighScorePopUp extends Application {

	public Scene scene1,scene2;
	public Stage myStage;
	
	/*public static void main(String[] args) {
		launch(args);
	}*/
	
	public HighScorePopUp(Stage stage) {
	start (stage);
	}

	@Override
	public void start(Stage stage) {
		myStage=stage;
		//Labels
		javafx.scene.control.Label label1 = new javafx.scene.control.Label("Menu");
		javafx.scene.control.Label label2 = new javafx.scene.control.Label("Select level to start:");

/*Scene scene1 = new Scene(layout1, 300, 300);
		
		Scene scene2 = new Scene(layout2, 300, 300);*/
		// Start the game
		Button btn1 = new Button();
		btn1.setText("Start the game");
		btn1.setOnAction((ActionEvent event) -> {
			stage.setScene(scene2);
			/*MainWindow mw = new MainWindow();
			mw.startGame();*/
		});
		
		Button btn2 = new Button();
		btn2.setText("Quit");
		btn2.setOnAction((ActionEvent event) -> {
			Platform.exit();
		});
		
		Button btn3 = new Button("Level 1");
		btn3.setOnAction((ActionEvent event) -> {
			//lvl1
			MainWindow mw = new MainWindow();
			mw.startGame(1);
		});
		
		Button btn4 = new Button("Level 2");
		btn4.setOnAction((ActionEvent event) -> {
			//lvl2
			MainWindow mw = new MainWindow();
			mw.startGame(2);
		});
		
		Button btn5 = new Button("Level 3");
		btn5.setOnAction((ActionEvent event) -> {
			//lvl3
			MainWindow mw = new MainWindow();
			mw.startGame(3);
		});
	
		//Layout 1
		VBox layout1 = new VBox();
		layout1.setPadding(new Insets(80));
		layout1.getChildren().addAll(label1,btn1,btn2);
		
		//Layout 2
		VBox layout2 = new VBox();
		layout2.setPadding(new Insets(80));
		layout2.getChildren().addAll(label2,btn3,btn4,btn5);
		
		scene1 = new Scene(layout1, 300, 300);
		
		scene2 = new Scene(layout2, 300, 300);

		stage.setTitle("The Brick Breaker");
		stage.setScene(scene1);
		stage.show();

	}

}

