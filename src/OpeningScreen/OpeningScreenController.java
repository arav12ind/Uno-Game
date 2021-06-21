package OpeningScreen;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import waitingScreen.WaitScreenController;

import java.io.*;
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
    BufferedReader in;
    PrintWriter out;
    WaitScreenController wsController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void playGame(ActionEvent event) throws IOException {
      try {
          startClient();
          FXMLLoader loader = new FXMLLoader(getClass().getResource("/waitingScreen/WaitScreen.fxml"));
          Main.scene.setRoot(loader.load());
          wsController = loader.getController();
          wsController.initVariables(in,out);
      }
      catch(java.io.IOException | InterruptedException ie) {
        error.setText(ie.getMessage());
      }
    }

    public void exitGame(ActionEvent event) {
        Platform.exit();
    }

    public void startClient() throws IOException {
            server = InetAddress.getByName(host);
            client = new Socket(server, 4444);
            in = new BufferedReader(new InputStreamReader(client.getInputStream()));
            out = new PrintWriter(new OutputStreamWriter(client.getOutputStream()));
    }
}






