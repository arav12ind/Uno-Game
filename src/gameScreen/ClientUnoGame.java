package gameScreen;

import OpeningScreen.Main;
import javafx.scene.layout.FlowPane;

import java.io.IOException;

public class ClientUnoGame extends javafx.concurrent.Task<Void> {

    java.io.BufferedReader in;
    java.io.PrintWriter out;
    uno.UnoCard topCard;
    ReceiveCardsEventHandler rcevt;
    FlowPane flowpane;

    public ClientUnoGame(ReceiveCardsEventHandler rcevt,FlowPane flowpane)
    {
        System.out.println("client game thread started");
        this.in = in;
        this.out = out;
        this.rcevt=rcevt;
        this.flowpane=flowpane;
    }

    @Override
    protected Void call() throws Exception {
        startUnoGame();
        return null;
    }



    public String receiveTopCard()
    {
        String topCardDetail,tokens[];

        System.out.println("waiting for top card\n");
        try
        {
            topCardDetail = in.readLine();
            if(topCardDetail.equals("WON") || topCardDetail.equals("LOST"))
                return topCardDetail;


            tokens = topCardDetail.split("-");
            topCard = new uno.UnoCard(uno.UnoCard.Colour.valueOf(tokens[1]), uno.UnoCard.Number.valueOf(tokens[0]));
            System.out.println("Current Top Card : " + topCard.toString());

        }

        catch(java.io.IOException ie)
        {
            ie.printStackTrace();
        }
        return null;
    } // end of receiveTopCard function.

    public void playOrWait() throws IOException {
        String playMyCard = null;
        int option;
        String message = in.readLine();
        if(message.equals("play"))
        {
            rcevt.setClickEnabled(true);
        }
        else
        {
            rcevt.setClickEnabled(false);
        }

    }// end of funciton


    // GAME LOOP STARTS HERE...
    public void startUnoGame() throws java.io.IOException {
        String topCardString, specialMessage;
        //UnoCard topCard, playMyCard;
        String gameStatus;

        System.out.println("starting game");

       // printMyCards();
       // GameScreenController gsc = new gameScreen.GameScreenController();

        //**** starting game   ****//
            while(true)
            {
                gameStatus = receiveTopCard();

                if(gameStatus!=null)
                {
                    System.out.println("YOU " + gameStatus + " THE GAME!!");
                    break;
                }
                GameScreenController.setTopCardOnScreen(topCard);
                playOrWait();
                //RECEIVE SPECIAL MESSAGE ...
                specialMessage = in.readLine();
                switch(specialMessage)
                {
                    case "draw":
                       flowpane.fireEvent(new ClientSideEvent(ClientSideEvent.RECEIVE_CARD_EVENT_TYPE));
                        break;
                    case "wild":
                        rcevt.setClickEnabled(true);
                }


            }

//******** game ends  ***********//



    }// end of StartUnoGame


}// end of class
