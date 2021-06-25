package gameScreen;

import OpeningScreen.Main;
import javafx.event.EventHandler;
import javafx.scene.layout.FlowPane;
import uno.CardClickedEventHandler;
import uno.UnoCard;
import uno.UnoCardView;

import java.io.BufferedReader;
import java.io.IOException;

public class ReceiveCardsEventHandler implements EventHandler<ClientSideEvent> {
    private CardClickedEventHandler cardClickedEventHandler;
    public ReceiveCardsEventHandler(CardClickedEventHandler cardClickedEventHandler)
    {
        this.cardClickedEventHandler= cardClickedEventHandler;
    }
    @Override
    public void handle(ClientSideEvent receiveCardsEvent) {
        FlowPane flowpane = (FlowPane)receiveCardsEvent.getSource();
        String cardDetail,tokens[];
        UnoCardView card;

        try{
            while(true)
            {
                cardDetail = Main.in.readLine();
                if(cardDetail==null)
                    continue;

                else if(cardDetail.equals("-EOF-"))
                    break;
                tokens = cardDetail.split("-");
                card = new UnoCardView(UnoCard.Colour.valueOf(tokens[1]), UnoCard.Number.valueOf(tokens[0]));
                card.setOnMouseClicked(cardClickedEventHandler);
                System.out.println("In reciveMyCards "+card.getCard());
                flowpane.getChildren().add(card);
                System.out.println("card added");

            }
            System.out.println("card while loop ended");
        }
        catch(IOException ie)
        {
            ie.printStackTrace();
        }
        System.out.println("received cards");
    }
    public void setClickEnabled(boolean enable)
    {
        cardClickedEventHandler.setEnabled(enable);
    }
}
