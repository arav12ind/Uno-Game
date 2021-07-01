package waitingScreen;

import OpeningScreen.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WaitScreenController implements Initializable {
   WaitForReadySignal wrs;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        wrs= new WaitForReadySignal();
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
}
