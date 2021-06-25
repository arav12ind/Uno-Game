package waitingScreen;

import OpeningScreen.Main;
import gameScreen.ClientUnoGame;
import gameScreen.GameScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import uno.CardClickedEventHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class WaitScreenController implements Initializable {
   @FXML
   private static Button cancel;
   GameScreenController gs;
   WaitForReadySignal wrs;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        wrs= new WaitForReadySignal(this);
        new Thread(wrs).start();
    }

    public void setCancelOccured(ActionEvent event)
    {
           try {
               Main.scene.setRoot(FXMLLoader.load(getClass().getResource("/OpeningScreen/openingScreen.fxml")));
               wrs.setIsCancelled();
           } catch (IOException e) {
           e.printStackTrace();
            }
    }

    public void readySignalOccured()
    {
        System.out.println("in ready func");

        try {
            System.out.println("making loader");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gameScreen/UnoGameScreen.fxml"));
            System.out.println("calling gscontroller");
            Main.scene.setRoot(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
