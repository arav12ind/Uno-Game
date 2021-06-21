package OpeningScreen;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Objects;

public class Main extends Application {
    public static final Scene scene = new Scene(new Button(), 270, 400);
    @Override
    public void start(Stage stage) throws Exception{
        scene.setRoot(FXMLLoader.load(Objects.requireNonNull(getClass().getResource("openingScreen.fxml"))));
        stage.setTitle("Hello World");
        stage.setScene(scene);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
