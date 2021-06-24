package uno;

public class UnoGame
{
  private int noOfPlayers;
  private static final int cardsPerGame = 7;
  private java.util.ArrayList<java.net.Socket> players;
  private java.util.ArrayList<java.io.PrintWriter> outList;
  private java.util.ArrayList<java.io.BufferedReader> inList;
  private java.util.ArrayList<Integer> playerCardCount;
  private java.util.ArrayList<UnoCard> returnToDeckCards;
  private String specialMessageCode;
  private boolean setSplMessage;
  private uno.UnoDeck deck;

    public UnoGame(java.util.ArrayList<java.net.Socket> playersList, int noOfPlayers)  // constructor
    {
      this.noOfPlayers = noOfPlayers;
      players = new java.util.ArrayList<java.net.Socket>();
      players = playersList;
      outList = new java.util.ArrayList<java.io.PrintWriter>();
      inList = new java.util.ArrayList<java.io.BufferedReader>();
      playerCardCount = new java.util.ArrayList<Integer>();
      returnToDeckCards = new java.util.ArrayList<UnoCard>();
      deck = new uno.UnoDeck();
      setSplMessage = false;
      startUnoGame();
    }

    public void initializeConnections()
    {
      try{
        java.io.PrintWriter out;
        java.io.BufferedReader in;
        for(java.net.Socket socket : players)
        {
          in = new java.io.BufferedReader(new java.io.InputStreamReader(socket.getInputStream()));
          out = new java.io.PrintWriter(socket.getOutputStream(), true);
          outList.add(out);
          inList.add(in);
        }
      }
      catch(java.io.IOException ie)
      {
        ie.printStackTrace();
      }

    }

    public void sendMessage(String msg)
    {
      for(java.io.PrintWriter out : outList)
        {out.println(msg);
        out.flush();}
    }

    public void sendSplMessage(String message, int player)
    {
      int i;
      for(i=0;i<noOfPlayers;++i)
      {
        if(i==player)
          outList.get(i).println(message);
        else outList.get(i).println("nothing");

        outList.get(i).flush();
      }
    }

    public void wildDrawFourMessage(int current, int next)
    {
      int i;
      for(i=0;i<noOfPlayers;++i)
      {
        if(i==current)
          outList.get(i).println("wild");
        else if(i==next)
          outList.get(i).println("draw");
        else outList.get(i).println("draw");
      }
    }


    public void sendPlayerCards(int noOfCards)
    {
      deck.newDeck();
      deck.shuffle();
  //    System.out.println("Cards in deck : " + deck.getCardsInDeck());
      java.util.List<UnoCard> playerCards;
      for(java.io.PrintWriter out : outList)
      {
        playerCards = deck.drawMultipleCards(noOfCards);
        returnToDeckCards.addAll(playerCards);
        for(uno.UnoCard card : playerCards)
        {
    //      System.out.println("Card : " + card.toString());
          out.println(card);
        }
        out.println("-EOF-");
        out.flush();
      }
    }

    public uno.UnoCard sendTopCard()   // SENT TO ALL PLAYERS
    {
      uno.UnoCard topCard = deck.drawCard();
  //    System.out.println(topCard);
      for(java.io.PrintWriter out : outList)
      {
          out.println(topCard);   // SENDING THE OPENING CARD...
          out.flush();
      }
      returnToDeckCards.add(topCard);
      return topCard;
    }

    public void sendNewTopCard(uno.UnoCard card) // TO SEND WHAT THE PLAYER PLAYED WHICH BECOMES TOP CARD
    {
    //    System.out.println("Sending the card : " + card.toString());
      for(java.io.PrintWriter out : outList)
      {
          out.println(card);   // SENDING THE TOP CARD...
          out.flush();
      }
      returnToDeckCards.add(card);
    }

    public void whomToPlay(int playerNumber)
    {
      int i=0;
      for(java.io.PrintWriter out : outList)
      {
        if(i==playerNumber)
          out.println("play");
        else out.println("wait");

        ++i;
      }
    }

    public uno.UnoCard cardFromPlayerCase1(int playerNumber, uno.UnoCard topCard)
    {

      uno.UnoCard drawCard = deck.drawCard();
      if(drawCard==null)
      {
        deck.replaceDeckWith(returnToDeckCards);
        deck.shuffle();
        returnToDeckCards.clear();
        drawCard = deck.drawCard();
      }

      System.out.println("card drawn : " + drawCard);
      if(topCard.equals(drawCard))
      {
        outList.get(playerNumber).println("played");
        outList.get(playerNumber).flush();
        setSplMessage = true;
        returnToDeckCards.add(drawCard);
        return drawCard;
      }
      else
      {
        outList.get(playerNumber).println("keep");
        outList.get(playerNumber).println(drawCard + "\n-EOF-");
        outList.get(playerNumber).flush();
        updateCardCount(1,playerNumber);
        setSplMessage = false;
        return topCard;
      }
    }

    public uno.UnoCard cardFromPlayerCase2(int playerNumber, uno.UnoCard topCard, String message)
    {
      uno.UnoCard.Colour colour; uno.UnoCard.Number number;
      String tokens[] = message.split("-");
      colour = uno.UnoCard.Colour.valueOf(tokens[1]);
      number = uno.UnoCard.Number.valueOf(tokens[0]);
      setSplMessage = true;
      if(topCard.equalsCol(colour) || topCard.equalsNum(number))
      {
        outList.get(playerNumber).println("ok");
        outList.get(playerNumber).flush();
        updateCardCount(-1,playerNumber);
        return new uno.UnoCard(colour,number);
      }
      else
      {
        outList.get(playerNumber).println("wrong card");
        outList.get(playerNumber).flush();
        return null;
      }

    }


public void setSpecialMessage(uno.UnoCard.Number num)
{
  switch(num)
  {
    case DrawTwo:
      specialMessageCode = "drawtwo";
    break;
    case Wild:
      specialMessageCode = "wild";
    break;
    case WildDrawFour:
      specialMessageCode = "wilddrawfour";
    break;
    case Skip:
      specialMessageCode = "skip";
    break;
    case Reverse:
      specialMessageCode = "reverse";
    break;
    default:
      specialMessageCode = "nothing";
  }
//  System.out.println("special message set to " + specialMessageCode);
}

    public uno.UnoCard getCardFromPlayer(int playerNumber, uno.UnoCard topCard)
    {
      String message;
      //int nxtPlayer = (playerNumber+1)%noOfPlayers,i;
      uno.UnoCard card = null;
//      System.out.println("player : " + playerNumber);
      try
      {
        while(true)
        {
          message = inList.get(playerNumber).readLine();
          System.out.println("message : " + message);
          if(message.equals("drawcard"))
          {
            card = cardFromPlayerCase1(playerNumber,topCard);
            System.out.println("new top card : " + card);
            break;
          }
          else
          {
            card = cardFromPlayerCase2(playerNumber,topCard,message);
            System.out.println("new played card : " + card);
            if(card!=null)
              break;
          }
        }
        if(setSplMessage)
          setSpecialMessage(card.getNumber());
        else specialMessageCode = "nothing";

        returnToDeckCards.add(card);
      }
      catch(java.io.IOException ie)
      {
        ie.printStackTrace();
      }

      return card;
    }// end of getCardFromPlayer function

    public void initializeCardCount(int count)
    {
      int i;
      for(i=0;i<noOfPlayers;++i)
        playerCardCount.add(count);

    }

    public void updateCardCount(int count, int playerNumber)
    {
      int setNum = playerCardCount.get(playerNumber) + count;
      playerCardCount.set(playerNumber,setNum);

    }

    public int checkGameOver(int currentPlayer)
    {
      return playerCardCount.get(currentPlayer);
    }


    // GAME LOOP STARTS HERE...
    public void startUnoGame()
    {
        int currentPlayer,nextPlayer,i;
        uno.UnoCard topCard=null,newTopCard=null,sendCard=null;
        boolean gameOver = false, reverse = false;
        String clientMessage;

        initializeConnections();    // creating the out and in for each player socket.
        initializeCardCount(cardsPerGame);  // Sets the number of cards for each player to cardsPerGame
        sendMessage("start");   // sending game start message
        sendPlayerCards(cardsPerGame);
        System.out.println("deck cards :" + deck.getCardsInDeck());
        topCard = sendTopCard();   // OPENING card.

// STARTING THE GAME NOW...
    currentPlayer = 0;
      while(true)
      {

        while(true)
        {
          System.out.println("currentPlayer is :" + currentPlayer);
              whomToPlay(currentPlayer);
              newTopCard = getCardFromPlayer(currentPlayer,topCard);

              if(checkGameOver(currentPlayer)==0)
              {
                gameOver = true;
                System.out.println(currentPlayer + " has finished");
                sendMessage("nothing");
                break;
              }

            // check and send special messages here...

              switch(specialMessageCode)
              {
                case "skip":
                  sendMessage("nothing");
                  if(reverse)
                    currentPlayer=(Math.abs(currentPlayer-1))%noOfPlayers;
                  else currentPlayer=(currentPlayer+1)%noOfPlayers;
                break;

                case "reverse":
                  sendMessage("nothing");
                  if(reverse)
                    reverse = false;
                  else reverse = true;
                break;

                case "drawtwo":
                    if(reverse)
                      nextPlayer = (currentPlayer+1)%noOfPlayers;
                    else nextPlayer = (Math.abs(currentPlayer-1))%noOfPlayers;
                    sendSplMessage("draw", nextPlayer);
                    for(i=0;i<2;++i)
                    {
                      sendCard = deck.drawCard();
                      outList.get(nextPlayer).println(sendCard);
                    }
                    outList.get(nextPlayer).println("-EOF-");
                    updateCardCount(2,nextPlayer);
                    //send for others as nothing..
                break;

                case "wilddrawfour":
                // DRAW FOUR FOR NEXT PLAYER
                  if(reverse)
                    nextPlayer = (currentPlayer+1)%noOfPlayers;
                  else nextPlayer = (Math.abs(currentPlayer-1))%noOfPlayers;
                    wildDrawFourMessage(currentPlayer, nextPlayer);
                    for(i=0;i<4;++i)
                    {
                      sendCard = deck.drawCard();
                      outList.get(nextPlayer).println(sendCard);
                    }
                      outList.get(nextPlayer).println("-EOF-");
                    updateCardCount(4,nextPlayer);
            // WILD CASE MEANS PLAYER SHOULD PLAY ANOTHER CARD.
                    if(reverse)   // this step ensures the player who played wild plays again.
                      currentPlayer=(currentPlayer+1)%noOfPlayers;
                    else currentPlayer=(Math.abs(currentPlayer-1))%noOfPlayers;
                break;
                case "wild":
                    sendSplMessage("wild", currentPlayer);
                    if(reverse)   // this step ensures the player who played wild plays again.
                      currentPlayer=(currentPlayer+1)%noOfPlayers;
                    else currentPlayer=(Math.abs(currentPlayer-1))%noOfPlayers;
                break;

                default:
                  sendMessage("nothing");
              }

            // end of spl message checks..
//System.out.println("newTopCard : " + newTopCard);
              if(newTopCard==null)
                sendNewTopCard(topCard);
              else
              {
                sendNewTopCard(newTopCard);
                topCard = newTopCard;
              }
              //      System.out.println("Going to send top card");

// ITERATOR...
          if(reverse)
            currentPlayer=(Math.abs(currentPlayer-1))%noOfPlayers;
          else currentPlayer=(currentPlayer+1)%noOfPlayers;
        }// end of while 2 loop

        if(gameOver)
          break;
      }// end of while 1 loop

      System.out.println("sending won or lost");
      for(i=0;i<noOfPlayers;++i)
      {
        if(i==currentPlayer)
          outList.get(i).println("WON");
        else outList.get(i).println("LOST");

        outList.get(i).flush();
      }

    }// end of start uno game.
}
