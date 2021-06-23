package gameScreen;

public class GameScreenController implements javafx.fxml.Initializable {

    @javafx.fxml.FXML javafx.scene.control.Button reveal;
    @javafx.fxml.FXML javafx.scene.layout.HBox hbox;
    @javafx.fxml.FXML javafx.scene.image.ImageView card1;
    @javafx.fxml.FXML javafx.scene.image.ImageView card2;
    @javafx.fxml.FXML javafx.scene.image.ImageView card3;
    @javafx.fxml.FXML javafx.scene.image.ImageView card4;
    @javafx.fxml.FXML javafx.scene.image.ImageView card5;
    @javafx.fxml.FXML javafx.scene.image.ImageView card6;
    @javafx.fxml.FXML javafx.scene.image.ImageView card7;
    @javafx.fxml.FXML javafx.scene.image.ImageView topCard;

 //  ImageView[] cardHolders;

    java.io.BufferedReader in;
    java.io.PrintWriter out;
    static javafx.beans.property.BooleanProperty topCardChanged;
    static String topCardPath;

    public GameScreenController()
    {

    }

    @Override
    public void initialize(java.net.URL url, java.util.ResourceBundle resourceBundle) {
        topCardChanged = new javafx.beans.property.SimpleBooleanProperty(false);
        final javafx.beans.value.ChangeListener changeListener = new javafx.beans.value.ChangeListener() {
            @Override
            public void changed(javafx.beans.value.ObservableValue observableValue, Object oldValue, Object newValue) {
               // System.out.println("oldValue:"+ oldValue + ", newValue = " + newValue);
                if(newValue.equals(true))
                {
                    topCard.setImage(new javafx.scene.image.Image(topCardPath));

                    topCardChanged.set(false);
                }

            }
        };
        topCardChanged.addListener(changeListener);
    }

    public void imageClick()
    {
        hbox.getChildren().remove(card4);
    }

    public static void initVariables(java.io.BufferedReader in, java.io.PrintWriter out) throws java.io.IOException
    {
        new Thread(new gameScreen.ClientUnoGame(in, out)).start();
    }

    public static void setTopCardOnScreen(uno.UnoCard topCard)
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

