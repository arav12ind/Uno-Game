package waitingScreen;

import OpeningScreen.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import uno.UnoCard;
import uno.UnoCardImageView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class WaitScreenController implements Initializable {
   @FXML
   private static Button cancel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       // test.setText("SURPRISE ");
        //System.out.println("in initialize");// always first no matter what...
    }

    public static void initVariables(BufferedReader in, PrintWriter out) throws IOException, InterruptedException {
        //System.out.println("in initvar");
        new Thread(new WaitForReadySignal(in,out)).start();
    }

    public static class CancelManager{
        private boolean isCancelled=false;
        private final Object isCancelledSYNC = new Object();
        public boolean getIsCancelled() {
            synchronized (isCancelledSYNC) {
                return isCancelled;
            }
        }
        public void setIsCancelled()
        {
            synchronized (isCancelledSYNC)
            {
                isCancelled=true;
            }
        }
    }
    public static CancelManager cl;

        public void setCancelOccured(ActionEvent event)
        {
               try {
                   Main.scene.setRoot(FXMLLoader.load(getClass().getResource("/OpeningScreen/openingScreen.fxml")));
                   //stage.show();
                   cl.setIsCancelled();
               } catch (IOException e) {
               e.printStackTrace();
                }
        }

    public void readySignalOccured()
    {
        //Main.scene.setRoot(FXMLLoader.load(getClass().getResource("/gameScreen/GameScreen.fxml")));
        UnoCardImageView ib = new UnoCardImageView(new UnoCard(UnoCard.Colour.Red,UnoCard.Number.Eight));
        Group g =new Group();
        ib.toFront();
        ib.setVisible(true);
        g.getChildren().add(ib);
        Main.scene.setRoot(g);
    }
}
