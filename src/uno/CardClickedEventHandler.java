package uno;

import OpeningScreen.Main;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
public  class CardClickedEventHandler implements EventHandler<MouseEvent> {
    private boolean enabled=false;
    @Override
    public void handle(MouseEvent mouseEvent) {
        UnoCardView ucv = (UnoCardView)mouseEvent.getSource();
        if(enabled&&ucv.isChoosable()) {
            enabled=false;
            Main.out.println("card\n"+ucv.getCard());
            System.out.println("\nClicked "+ucv.getCard());
        }
        mouseEvent.consume();
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
