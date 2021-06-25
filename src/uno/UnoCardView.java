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
    private boolean choosable=true;
    public UnoCardView(UnoCard.Colour valueOf, UnoCard.Number valueOf1)
    {
        this.card = new UnoCard(valueOf, valueOf1);
        System.out.println(this.card);
        setImage(new Image(card.toPath()));
        this.setUserData(card.toString());
     //   setOnMouseClicked(evt -> {
           //GameScreenController.flowpane.getChildren().remove(this);

       // });

        this.setCursor(Cursor.OPEN_HAND);
        /*setOnMouseEntered(evt ->{
            this.setStyle("-fx-effect: dropshadow( gaussian , rgba(194,29,143,0.6) , 5, 5 , 0 , 1 );");
        });*/
        /*ssetOnMouseExited(evt ->{
            this.setStyle(null);
        });*/
        setOnMousePressed(evt ->{
            this.setCursor(Cursor.CLOSED_HAND);
        });
    }

    public void setChoosable(boolean choosable)
    {
        this.choosable=choosable;
    }
    public UnoCard getCard()
    {
        return card;
    }
    public boolean isChoosable() {
        return choosable;
    }
}
