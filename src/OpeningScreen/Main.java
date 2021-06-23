package OpeningScreen;

public class Main extends javafx.application.Application {

    @Override
    public void start(javafx.stage.Stage primaryStage) throws Exception{
        javafx.scene.Parent root = javafx.fxml.FXMLLoader.load(getClass().getResource("openingScreen.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new javafx.scene.Scene(root, 700, 700));
        primaryStage.initStyle(javafx.stage.StageStyle.UNDECORATED);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
