package uno;

import OpeningScreen.Main;
import gameScreen.GameScreenController;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public  class CardClickedEventHandler implements EventHandler<MouseEvent> {
    private boolean enabled=false;
    @Override
    public void handle(MouseEvent mouseEvent) {
        UnoCardView ucv = (UnoCardView)mouseEvent.getSource();
        String message;
        if(enabled&&ucv.isChoosable()) {
            Main.out.println(ucv.getCard());
            Main.out.flush();
            try {
                message = Main.in.readLine();
                System.out.println("server says : " + message);
                if(message.equals("ok"))
                {
                    GameScreenController.flowpane.getChildren().remove(ucv);
                    setEnabled(false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isEnabled() {
        return enabled;
    }
}
