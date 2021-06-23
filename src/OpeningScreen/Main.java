package OpeningScreen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    public static final Scene scene = new Scene(new Button(),800,600);
    @Override
    public void start(Stage primaryStage) throws Exception{
        scene.setRoot(FXMLLoader.load(getClass().getResource("openingScreen.fxml")));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
