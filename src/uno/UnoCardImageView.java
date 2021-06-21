package uno;

import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class UnoCardImageView extends ImageView {
    public UnoCardImageView(UnoCard card)
    {
        this.setImage( new Image(card.getPath()));
        //this.setOnMousePressed(evt -> iv.setImage());
        this.setOnMouseEntered(evt -> this.setEffect(new Glow()));
        this.setOnMouseExited(evt -> this.setEffect(null));
    }
}
