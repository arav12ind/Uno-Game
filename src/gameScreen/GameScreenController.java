package gameScreen;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
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

 //  ImageView[] cardHolders;

    BufferedReader in;
    PrintWriter out;
    static BooleanProperty topCardChanged;
    static String topCardPath;
    static ArrayList<UnoCard> initialCards;

    public GameScreenController()
    {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initialCards = WaitScreenController.myCards;
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
        for(UnoCard card : initialCards)
        {
            flowpane.getChildren().add(new UnoCardView(card));
        }
       // flowpane.getChildren().add(new UnoCardView(UnoCard.Colour.Blue,UnoCard.Number.Seven));
       // flowpane.getChildren().add(new UnoCardView(UnoCard.Colour.Blue,UnoCard.Number.Six));
       // flowpane.getChildren().add(new UnoCardView(UnoCard.Colour.Blue,UnoCard.Number.Reverse));
       // flowpane.getChildren().add(new UnoCardView(UnoCard.Colour.Blue,UnoCard.Number.DrawTwo));
       // flowpane.getChildren().add(new UnoCardView(UnoCard.Colour.Blue,UnoCard.Number.Skip));
        anchorPane.getChildren().add(flowpane);
    }

    public static void initVariables(BufferedReader in, PrintWriter out) throws IOException
    {
        new Thread(new ClientUnoGame(in, out)).start();
    }

    public static void setTopCardOnScreen(UnoCard topCard)
    {
        try {
                topCardPath ="/resources/" + topCard + ".jpg" ;
            System.out.println("changing");
            topCardChanged.set(true);
            System.out.println("changed");
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }


}
