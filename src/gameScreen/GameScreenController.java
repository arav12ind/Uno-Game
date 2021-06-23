package gameScreen;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import uno.UnoCard;
import uno.UnoCardView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;

public class GameScreenController implements Initializable {

    @FXML Button reveal;
    @FXML AnchorPane anchorPane;
    public static FlowPane flowpane;
    @FXML ImageView topCard;

 //  ImageView[] cardHolders;

    BufferedReader in;
    PrintWriter out;
    static BooleanProperty topCardChanged;
    static String topCardPath;

    public GameScreenController()
    {

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        topCardChanged = new SimpleBooleanProperty(false);
        final ChangeListener changeListener = new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
               // System.out.println("oldValue:"+ oldValue + ", newValue = " + newValue);
                if(newValue.equals(true))
                {
                    //topCard.setImage(new Image(topCardPath));

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
        flowpane.getChildren().add(new UnoCardView(UnoCard.Colour.Blue,UnoCard.Number.Seven));
        flowpane.getChildren().add(new UnoCardView(UnoCard.Colour.Blue,UnoCard.Number.Six));
        flowpane.getChildren().add(new UnoCardView(UnoCard.Colour.Blue,UnoCard.Number.Reverse));
        flowpane.getChildren().add(new UnoCardView(UnoCard.Colour.Blue,UnoCard.Number.DrawTwo));
        flowpane.getChildren().add(new UnoCardView(UnoCard.Colour.Blue,UnoCard.Number.Skip));
        anchorPane.getChildren().add(flowpane);
    }

    public static void initVariables(BufferedReader in, PrintWriter out) throws IOException
    {
        new Thread(new ClientUnoGame(in, out)).start();
    }

    public static void setTopCardOnScreen(UnoCard topCard)
    {
        try {
                topCardPath ="/resources/" + topCard + ".jpeg" ;
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

/*

  public GameScreenController()
    {
        cardHolders = new ImageView[7];
        int i=0;
        cardHolders[i++] = card1;
        cardHolders[i++] = card2;
        cardHolders[i++] = card3;
        cardHolders[i++] = card4;
        cardHolders[i++] = card5;
        cardHolders[i++] = card6;
        cardHolders[i++] = card7;

    }

   public void revealCards(javafx.event.ActionEvent e)
   {
       int i = 0;
       String url = "/resources/";
       for(uno.UnoCard card : gameScreen.ClientUnoGame.myCards)
       {
           url+=card+".jpeg";
           card1.setImage(new Image(url));
          // card2.setImage(new Image(url));
           //cardHolders[i].setImage(new Image("/resources/" + card.toString() + ".jpeg"));
       }
   }

    public void setcardImage()
    {
        card2.setImage(new Image("/resources/Eight-Red.jpeg"));
    }

 */

