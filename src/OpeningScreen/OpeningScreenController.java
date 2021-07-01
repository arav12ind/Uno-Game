package OpeningScreen;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

public class OpeningScreenController implements Initializable {
    @FXML
    private Label error;
    @FXML private Label gameStatus;
    String host = "localhost";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void playGame(ActionEvent event){

      try {
          Main.socket = new Socket(InetAddress.getByName(host), 4444);
          Main.in = new BufferedReader(new InputStreamReader(Main.socket.getInputStream()));
          Main.out = new PrintWriter(new OutputStreamWriter(Main.socket.getOutputStream()),true);
          System.out.println("calling wscontroller");
          Main.scene.setRoot(FXMLLoader.load(getClass().getResource("/waitingScreen/WaitScreen.fxml")));
      }
      catch(IOException ie) {
        error.setText(ie.getMessage());
        error.setWrapText(true);
      }
    }

    public void exitGame(ActionEvent event) {
        Platform.exit();
    }

    public void setGameStatus(String msg)
    {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gameStatus.setText(msg);
            }
        });
    }
}






