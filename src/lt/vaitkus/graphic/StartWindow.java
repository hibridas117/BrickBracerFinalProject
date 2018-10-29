package lt.vaitkus.graphic;



import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import lt.vaitkus.*;
import javafx.scene.control.*;


public class StartWindow extends Application {

	public Scene scene1, scene2, scene3;
	public static Stage myStage;
	public static Player player;

	public static void main(String[] args) {
		launch(args);
	}

	/*
	 * public StartWindow(Stage stage) { start (stage); }
	 */

	@Override
	public void start(Stage stage) {
		myStage = stage;
		// Labels
		Label label1 = new Label("Menu");
		Label label2 = new Label("Select level to start:");

		/*
		 * Scene scene1 = new Scene(layout1, 300, 300);
		 * 
		 * Scene scene2 = new Scene(layout2, 300, 300);
		 */
		// Start the game
		Button btn1 = new Button();
		btn1.setText("Start the game");
		btn1.setOnAction((ActionEvent event) -> {
			stage.setScene(scene3);
			/*
			 * MainWindow mw = new MainWindow(); mw.startGame();
			 */
		});

		Button btn2 = new Button();
		btn2.setText("Quit");
		btn2.setOnAction((ActionEvent event) -> {
			Platform.exit();
		});
		
		
		///correct
		
	/*	Button btn7 = new Button();
		btn7.setText("Test");
		btn7.setOnAction((ActionEvent event) -> {
			editFile e = new editFile();
			e.openFile("scores.txt");
			//e.addRecords(player.getName() +" "+Player.pointsScored, score);
			
		});
*/
		Button btn6 = new Button();
		btn6.setText("Highscores");
		btn6.setOnAction((ActionEvent event) -> {
			
			readFile r = new readFile();
			r.openFile("scores.txt");
			r.readFile();
			r.closeFile();
		});

		Button btn3 = new Button("Level 1");
		btn3.setOnAction((ActionEvent event) -> {
			// lvl1
			MainWindow mw = new MainWindow();
			mw.startGame(1);
		});

		Button btn4 = new Button("Level 2");
		btn4.setOnAction((ActionEvent event) -> {
			// lvl2
			MainWindow mw = new MainWindow();
			mw.startGame(2);
		});

		Button btn5 = new Button("Level 3");
		btn5.setOnAction((ActionEvent event) -> {
			// lvl3
			MainWindow mw = new MainWindow();
			mw.startGame(3);
		});
		
		

		// Layout 1
		VBox layout1 = new VBox();
		layout1.setPadding(new Insets(80));
		layout1.getChildren().addAll(label1, btn1, btn6, btn2);

		// Layout 2
		VBox layout2 = new VBox();
		layout2.setPadding(new Insets(80));
		layout2.getChildren().addAll(label2, btn3, btn4, btn5);

		// Layout 3
		/*VBox layout3 = new VBox();
		layout3.setPadding(new Insets(80));
		layout3.getChildren().addAll(label2, btn3, btn4, btn5);*/

		scene1 = new Scene(layout1, 300, 300);

		scene2 = new Scene(layout2, 300, 300);

		//scene3 = new Scene(layout3, 300, 300);
/////////////////////
		

        //Name Label - constrains use (child, column, row)
		Label nameLabel = new Label("Enter your name:");
		GridPane.setConstraints(nameLabel, 0, 0);

        //Name Input
        TextField nameInput = new TextField();
        nameInput.setPromptText("Name");
        GridPane.setConstraints(nameInput, 0, 1);


        //Login
        Button submitButton = new Button("Submit");
        GridPane.setConstraints(submitButton, 0, 2);
        submitButton.setOnAction((ActionEvent event) -> {
        	player= new Player();
        	player.setName(nameInput.getText());
			stage.setScene(scene2);
			/*
			 * MainWindow mw = new MainWindow(); mw.startGame();
			 */
		});


        //Add everything to grid
        //grid.getChildren().addAll(nameLabel, nameInput, passLabel, passInput, loginButton);
        //GridPane with 10px padding around edge
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        grid.setVgap(8);
        grid.setHgap(10);
        
        grid.getChildren().addAll(nameLabel, nameInput, submitButton);
        scene3 = new Scene(grid, 300, 300);
		
		
		
///////////////////////////
		stage.setTitle("The Brick Breaker");
		stage.setScene(scene1);
		stage.show();

	}

}
