package gameScreen;

import javafx.concurrent.Task;
import uno.UnoCard;
import utility.Validator1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class ClientUnoGame extends Task<Void> {

    BufferedReader in;
    PrintWriter out;
    ArrayList<UnoCard> myCards;
    String serverMessage;
    GameScreenController gsc;

    public ClientUnoGame(BufferedReader in, PrintWriter out)
    {
        System.out.println("client game thread started");
        this.in = in;
        this.out = out;
        gsc = new GameScreenController();
        myCards = new ArrayList<UnoCard>();
    }

    @Override
    protected Void call() throws Exception {
        startUnoGame();
        return null;
    }



    public void receiveMyCards()
    {
        String cardDetail,tokens[];
        UnoCard card;
        try{
            while(true)
            {
                cardDetail = in.readLine();
                if(cardDetail==null)
                    continue;

                else if(cardDetail.equals("-EOF-"))
                    break;
                tokens = cardDetail.split("-");
                card = new UnoCard(UnoCard.Colour.valueOf(tokens[1]), UnoCard.Number.valueOf(tokens[0]));
                myCards.add(card);

            }
            System.out.println("Total cards with me : " + myCards.size());
        }
        catch(IOException ie)
        {
            ie.printStackTrace();
        }
    }//  END OF receiveMyCards function

    public String receiveTopCard()
    {
        String topCardDetail,tokens[],finish;
        UnoCard topCard = null;
        System.out.println("waiting for top card\n");
        try
        {
            topCardDetail = in.readLine();
            if(topCardDetail.equals("WON") || topCardDetail.equals("LOST"))
                return topCardDetail;


            tokens = topCardDetail.split("-");
            topCard = new UnoCard(UnoCard.Colour.valueOf(tokens[1]), UnoCard.Number.valueOf(tokens[0]));
            System.out.println("Current Top Card : " + topCard.toString());

        }

        catch(IOException ie)
        {
            ie.printStackTrace();
        }
        return null;
    } // end of receiveTopCard function.

    public void printMyCards()
    {
        int i=0;
        for(UnoCard card : myCards)
            System.out.println((++i) + "." + card);

    }

    public void playOrWait()
    {
        UnoCard playMyCard = null;
        int option;
        try
        {
            String message = in.readLine();

            if(message.equals("play"))
            {
                printMyCards();
                while(true)
                {
                    option = Validator1.getInt("Enter Card Number to Play(0 if no card) : ");
                    option-=1;
                    //        System.out.println("option : " + option);
                    if(option == -1)
                    {
                        out.println("drawcard");
                        out.flush();
                        message = in.readLine();      // RECEIVE PLAYED OR KEEP MESSAGE

                        System.out.println("played or keep? : " + message);
                        if(message.equals("keep"))    // CANNOT PLAY THE DRAWN CARD
                            receiveMyCards();

                        break;
                    }
                    else
                    {
                        if(option>=myCards.size() || option<0)
                        {
                            System.out.println("Not valid : ");
                            continue;
                        }
                        playMyCard = myCards.get(option);
                        //          System.out.println("im playing : " + playMyCard.toString());
                        //          printMyCards();
                        out.println(playMyCard);
                        out.flush();


                        message = in.readLine();

                        if(message.equals("ok"))
                        {
                            myCards.remove(option);
                            break;
                        }

                        //  else myCards.add(option,playMyCard);
                    }
                }
            }
        }
        catch(IOException i)
        {
            i.printStackTrace();
        }

    }// end of funciton


    // GAME LOOP STARTS HERE...
    public void startUnoGame() throws IOException {
        String topCardString, specialMessage;
        UnoCard topCard, playMyCard;
        String gameStatus;

        System.out.println("starting game");
        receiveMyCards(); // receive first set of cards
        System.out.println("received cards");
        printMyCards();
        try {
            gsc.displayCards(myCards);
        }
        catch (Exception f)
        {
            f.printStackTrace();
        }

        //**** starting game   ****//
            while(true)
            {
                gameStatus = receiveTopCard();
                if(gameStatus!=null)
                {
                    System.out.println("YOU " + gameStatus + " THE GAME!!");
                    break;
                }

                playOrWait();
                //RECEIVE SPECIAL MESSAGE ...
                specialMessage = in.readLine();
                switch(specialMessage)
                {
                    case "draw":
                        receiveMyCards();
                        break;
                    case "wild":
                        System.out.println("PLAY ANOTHER CARD!...");
                }


            }

//******** game ends  ***********//



    }// end of StartUnoGame


}// end of class
