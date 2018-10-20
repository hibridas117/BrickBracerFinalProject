package lt.vaitkus.graphic;
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

public class StartWindow extends Application {

    @Override
    public void start(Stage stage) {

        initUI(stage);
    }

    private void initUI(Stage stage) {

        Button btn = new Button();
        btn.setText("Quit");
        btn.setOnAction((ActionEvent event) -> {
            Platform.exit();
        });

        Button btn2 = new Button();
        btn2.setText("Start the game");
        // Lambda expression
        btn2.setOnAction((ActionEvent event) -> {
            System.out.println("START");
        });

        Button btn3 = new Button();
        // Anonymous class
        btn3.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Anonymous!");
            }
        });



        Button btn4 = new Button();
        btn4.setOnAction(new MyHandler());

        VBox root = new VBox();
        root.setPadding(new Insets(80));
        root.getChildren().add(btn2);
        //root.getChildren().add(btn3);
        //root.getChildren().add(btn4);
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 300);

        stage.setTitle("The Brick Breaker");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch(args);
    }
}

class MyHandler implements EventHandler{

    @Override
    public void handle(Event event) {
        System.out.println("My handler");
    }

}