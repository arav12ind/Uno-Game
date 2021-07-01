package uno;

import gameScreen.GameScreenController;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;


public class UnoCardView extends ImageView {
    private UnoCard card;
    private boolean choosable=true;
    private Effect defaultEffect;
    private Effect enteredEffect;
    public UnoCardView(UnoCard.Colour valueOf, UnoCard.Number valueOf1)
    {
        this.card = new UnoCard(valueOf, valueOf1);
        init();
    }
    public UnoCardView(String name) throws IllegalArgumentException {
        this.card = new UnoCard(name);
        init();
    }
    public UnoCardView(String name,CardClickedEventHandler ccevt) throws IllegalArgumentException {
        this(new UnoCard(name),ccevt);
    }

    public UnoCardView(UnoCard uc,CardClickedEventHandler ccevt) {
        this.card = uc;
        this.setOnMouseClicked(ccevt);
        init();
    }

    public boolean equals(UnoCardView uc)
    {
        return this.card.equals(uc.getCard());
    }


    private void init()
    {
        setImage(new Image(card.toPath()));
        setFitHeight(150);
        setFitWidth(80);
        defaultEffect = getEffect();
        enteredEffect = new Glow();
        this.setCursor(Cursor.OPEN_HAND);
        setOnMouseEntered(evt ->{
            this.setEffect(enteredEffect);
        });
        setOnMouseExited(evt ->{
            this.setEffect(defaultEffect);
        });
        setOnMousePressed(evt ->{
            this.setCursor(Cursor.CLOSED_HAND);
        });
        setOnMouseReleased(evt ->{
            this.setCursor(Cursor.OPEN_HAND);
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
