package gameScreen;

import OpeningScreen.Main;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import uno.CardClickedEventHandler;
import uno.UnoCard;
import uno.UnoCardView;
import waitingScreen.WaitScreenController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameScreenController implements Initializable {


    @FXML AnchorPane anchorPane;
    public static FlowPane flowpane;
    @FXML ImageView topCard;

    static BooleanProperty topCardChanged;
    static String topCardPath;
    static boolean yourTurn = false;
    static String cardToBePlayed;
    public static boolean remove = false;

    public static void setCardToBePlayed(String source) {
        cardToBePlayed = source;
        System.out.println("SetCardToBBePlayed"+cardToBePlayed);
    }

    public static String getCardToBePlayed() {
        return cardToBePlayed;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        topCardChanged = new SimpleBooleanProperty(false);
        final ChangeListener changeListener = new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {

                if(newValue.equals(true))
                {
                    topCard.setImage(new Image(topCardPath));

                    topCardChanged.set(false);
                }

            }
        };
        flowpane = new FlowPane(117,800);
        flowpane.setLayoutX(23);
        flowpane.setLayoutY(194);
        flowpane.setHgap(10);
        flowpane.setPrefWrapLength(700);
        topCardChanged.addListener(changeListener);
        anchorPane.getChildren().add(flowpane);
        ReceiveCardsEventHandler rcevt = new ReceiveCardsEventHandler(new CardClickedEventHandler());
        flowpane.addEventHandler(ClientSideEvent.RECEIVE_CARD_EVENT_TYPE,rcevt);
        System.out.println("Before flowpane fireevent");
        flowpane.fireEvent(new ClientSideEvent(ClientSideEvent.RECEIVE_CARD_EVENT_TYPE));
        new Thread(new ClientUnoGame(rcevt,flowpane)).start();

    }

    public static void setTopCardOnScreen(UnoCard topCard) {
        try {
            topCardPath = "/resources/" + topCard + ".jpg";
            System.out.println("changing");
            topCardChanged.set(true);
            System.out.println("changed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   public static boolean getYourTurn()
   {
       return yourTurn;
   }

    public static void setYourTurn(boolean bool)
    {
        yourTurn = bool;
    }
}
