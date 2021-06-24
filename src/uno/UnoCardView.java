package uno;

import gameScreen.GameScreenController;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;


public class UnoCardView extends ImageView {
    private UnoCard card;
    public UnoCardView(UnoCard c)
    {
        this.card = new uno.UnoCard(c.getColour(), c.getNumber());
        System.out.println(this.card);
       // this.card = card;
        setImage(new Image(card.toPath()));
        setOnMouseClicked(evt -> {
           GameScreenController.flowpane.getChildren().remove(this);
        });

        this.setCursor(javafx.scene.Cursor.OPEN_HAND);
        setOnMouseEntered(evt ->{
            this.setStyle("-fx-effect: dropshadow( gaussian , rgba(194,29,143,0.6) , 5, 5 , 0 , 1 );");
        });
        setOnMouseExited(evt ->{
            this.setStyle(null);
        });
        setOnMousePressed(evt ->{
            this.setCursor(javafx.scene.Cursor.CLOSED_HAND);
        });
    }
}
