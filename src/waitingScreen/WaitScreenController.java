package waitingScreen;

import OpeningScreen.Main;
import gameScreen.GameScreenController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class WaitScreenController implements Initializable {
   @FXML
   private static Button cancel;
   GameScreenController gs;
   public static java.util.ArrayList<uno.UnoCard> myCards;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
       // test.setText("SURPRISE ");
        //System.out.println("in initialize");// always first no matter what...
    }

    public static void initVariables(BufferedReader in, PrintWriter out) throws IOException, InterruptedException {
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
    public static WaitScreenController.CancelManager cl;

        public void setCancelOccured(ActionEvent event)
        {
               try {
                   Main.scene.setRoot(FXMLLoader.load(getClass().getResource("/OpeningScreen/openingScreen.fxml")));
                   cl.setIsCancelled();
               } catch (IOException e) {
               e.printStackTrace();
                }
        }

    public void readySignalOccured(BufferedReader in, PrintWriter out)
    {
        System.out.println("in ready func");

        receiveMyCards(in);
        try {
            System.out.println("making loader");



            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gameScreen/UnoGameScreen.fxml"));
           // gs = loader.getController();

            Main.scene.setRoot(loader.load());
            System.out.println("calling gscontroller");
            GameScreenController.initVariables(in,out);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void receiveMyCards(BufferedReader in)
    {
        myCards = new java.util.ArrayList<>();
        String cardDetail,tokens[];
        uno.UnoCard card;
        try{
            while(true)
            {
                cardDetail = in.readLine();
                if(cardDetail==null)
                    continue;

                else if(cardDetail.equals("-EOF-"))
                    break;
                tokens = cardDetail.split("-");
                card = new uno.UnoCard(uno.UnoCard.Colour.valueOf(tokens[1]), uno.UnoCard.Number.valueOf(tokens[0]));
                myCards.add(card);

            }
            System.out.println("Total cards with me : " + myCards.size());
        }
        catch(java.io.IOException ie)
        {
            ie.printStackTrace();
        }
    }//  END OF receiveMyCards function

}
