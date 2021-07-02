package gameScreen;

import OpeningScreen.Main;
import OpeningScreen.OpeningScreenController;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import uno.CardClickedEventHandler;
import uno.CardPane;
import uno.UnoCard;
import uno.UnoCardView;

import java.util.ArrayList;
import java.util.Iterator;

public class ClientUnoGame extends Task<Void> {
    CardClickedEventHandler ccevt;
    final Button drawCard;
    CardPane flowpane;
    Label chance;
    public ClientUnoGame(Button drawCard, Label chance, CardPane flowpane)
    {
        System.out.println("client game thread started");
        this.ccevt = new CardClickedEventHandler();
        this.drawCard = drawCard;
        this.flowpane=flowpane;
        this.chance = chance;
    }

    @Override
    protected Void call() throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/OpeningScreen/openingScreen.fxml"));
        UnoCard topCard;
        String msg;
        System.out.println("starting game");
        while(true)
        {
            System.out.println("Server : "+(msg = Main.in.readLine()));
            switch(msg)
            {
                case "draw":
                    ArrayList<UnoCardView> ucs = new ArrayList<>();
                    while (!(msg = Main.in.readLine()).equals("-EOF-")) {
                        System.out.println("Receive cards : " + msg);
                        ucs.add(new UnoCardView(msg,ccevt));
                    }
                    Platform.runLater(() -> flowpane.addAll(ucs));
                    break;
                case "ok":
                    String finalMsg = Main.in.readLine();
                    Platform.runLater(() -> {
                        drawCard.setText("draw card");
                        drawCard.setVisible(false);
                        flowpane.remove(new uno.UnoCard(finalMsg));
                    });
                    break;
                case "wrong card":
                case "play":
                    drawCard.setVisible(true);
                    ccevt.setEnabled(true);
                    Platform.runLater(() -> chance.setText("YOUR TURN"));

                    break;
                case "wait":
                    Platform.runLater(() -> chance.setText("OPPONENTS TURN"));

                    break;
                case "top card":
                    topCard = new UnoCard(Main.in.readLine());
                    GameScreenController.setTopCardOnScreen(topCard);
                    break;
                case "won":
                    Main.scene.setRoot(loader.load());
                    ((OpeningScreenController)loader.getController()).setGameStatus("WON");
                    return null;
                case "lost":
                    Main.scene.setRoot(loader.load());
                    ((OpeningScreenController)loader.getController()).setGameStatus("LOST");
                    return null;
                default:
            }
        }
    }

}// end of class
