package uno;

import gameScreen.GameScreenController;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;


public class UnoCardView extends ImageView {
    private UnoCard card;
    public UnoCardView(UnoCard.Colour c,UnoCard.Number n)
    {
        card = new UnoCard(c,n);
        setImage(new Image(card.toPath()));
        setOnMouseClicked(evt -> {
           GameScreenController.flowpane.getChildren().remove(this);
        });
    }
}
