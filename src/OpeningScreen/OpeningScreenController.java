package OpeningScreen;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import waitingScreen.WaitScreenController;

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

    String host = "localhost", keyboardInput;
    InetAddress server = null;
    Socket client = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void playGame(ActionEvent event) throws IOException {

      try {
          startClient();
          Main.scene.setRoot(FXMLLoader.load(getClass().getResource("/waitingScreen/WaitScreen.fxml")));
         // wsController = loader.getController();
          System.out.println("calling wscontroller");
      }
      catch(IOException ie) {
        error.setText(ie.getMessage());
        error.setWrapText(true);
      }
    }

    public void exitGame(ActionEvent event) {
        Platform.exit();
    }

    public void startClient() throws IOException {
            server = InetAddress.getByName(host);
            client = new Socket(server, 4444);
            Main.in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            Main.out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
    }
}






