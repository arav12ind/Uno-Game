package gameScreen;

public class ClientUnoGame extends javafx.concurrent.Task<Void> {

    java.io.BufferedReader in;
    java.io.PrintWriter out;
    static java.util.ArrayList<uno.UnoCard> myCards;
    uno.UnoCard topCard;
    String serverMessage;
    GameScreenController gsc;

    public ClientUnoGame(java.io.BufferedReader in, java.io.PrintWriter out)
    {
        System.out.println("client game thread started");
        this.in = in;
        this.out = out;
        gsc = new GameScreenController();
        myCards = new java.util.ArrayList<>();
    }

    @Override
    protected Void call() throws Exception {
        startUnoGame();
        return null;
    }



    public void receiveMyCards()
    {
        String cardDetail,tokens[];
        uno.UnoCard card;
        try{
            while(true)
            {
                cardDetail = in.readLine();
                if(cardDetail==null)
                    continue;

                else if(cardDetail.equals("-EOF-"))
                    break;
                tokens = cardDetail.split("-");
                card = new uno.UnoCard(uno.UnoCard.Colour.valueOf(tokens[1]), uno.UnoCard.Number.valueOf(tokens[0]));
                myCards.add(card);

            }
            System.out.println("Total cards with me : " + myCards.size());
        }
        catch(java.io.IOException ie)
        {
            ie.printStackTrace();
        }
    }//  END OF receiveMyCards function

    public String receiveTopCard()
    {
        String topCardDetail,tokens[],finish;

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

    public void printMyCards()
    {
        int i=0;
        for(uno.UnoCard card : myCards)
            System.out.println((++i) + "." + card);

    }

    public void playOrWait()
    {
        uno.UnoCard playMyCard = null;
        int option;
        try
        {
            String message = in.readLine();

            if(message.equals("play"))
            {
                printMyCards();
                while(true)
                {
                    option = utility.Validator1.getInt("Enter Card Number to Play(0 if no card) : ");
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
        catch(java.io.IOException i)
        {
            i.printStackTrace();
        }

    }// end of funciton


    // GAME LOOP STARTS HERE...
    public void startUnoGame() throws java.io.IOException {
        String topCardString, specialMessage;
        //UnoCard topCard, playMyCard;
        String gameStatus;

        System.out.println("starting game");
        receiveMyCards(); // receive first set of cards
        System.out.println("received cards");
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
                        receiveMyCards();
                        break;
                    case "wild":
                        System.out.println("PLAY ANOTHER CARD!...");
                }


            }

//******** game ends  ***********//



    }// end of StartUnoGame


}// end of class
