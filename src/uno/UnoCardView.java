package uno;

import gameScreen.GameScreenController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;


public class UnoCardView extends ImageView {
    private UnoCard card;
    public UnoCardView(UnoCard c)
    {
        this.card = new UnoCard(c.getColour(), c.getNumber());
        System.out.println(this.card);
       // this.card = card;
        setImage(new Image(card.toPath()));
        this.setUserData(card.toString());
        EventHandler<MouseEvent> clicked = (MouseEvent t) ->{
            if(GameScreenController.getYourTurn()) {
                gameScreen.GameScreenController.setCardToBePlayed((String)((ImageView)t.getSource()).getUserData());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(gameScreen.GameScreenController.remove)
                    GameScreenController.flowpane.getChildren().remove(this);
            };

            //System.out.println(GameScreenController);
        };
        setOnMouseClicked(clicked);
     //   setOnMouseClicked(evt -> {
           //GameScreenController.flowpane.getChildren().remove(this);

       // });

        this.setCursor(Cursor.OPEN_HAND);
        setOnMouseEntered(evt ->{
            this.setStyle("-fx-effect: dropshadow( gaussian , rgba(194,29,143,0.6) , 5, 5 , 0 , 1 );");
        });
        setOnMouseExited(evt ->{
            this.setStyle(null);
        });
        setOnMousePressed(evt ->{
            this.setCursor(Cursor.CLOSED_HAND);
        });
    }
}
