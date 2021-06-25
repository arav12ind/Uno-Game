package uno;

import javafx.scene.layout.Pane;

public class UnoCardsHPane extends Pane {
    private int noOfCards;
    private double hGap;
    public UnoCardsHPane()
    {
        hGap=5.0;
        noOfCards=0;
    }
    public void add(UnoCardView uc)
    {
        ++noOfCards;
        getChildren().add(uc);
        uc.setLayoutX(hGap*(double)noOfCards);
    }

}
