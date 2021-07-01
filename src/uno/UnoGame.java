package uno;

import server.MultiServerThread;
import server.PlayerQueue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Stack;

public class UnoGame extends Thread
{

  private final int noOfPlayers;
  private final ArrayList<Player> players;
  private final Stack<UnoCard> returnToDeckCards;
  private final UnoDeck deck;
    public UnoGame(ArrayList<MultiServerThread> playersList, int cardsPerGame) throws Exception  // constructor
    {
      deck = new UnoDeck();
      deck.shuffle();
      this.noOfPlayers = playersList.size();
      players = new ArrayList<>();
      returnToDeckCards = new Stack<>();
      Socket s;
      for(MultiServerThread pl : playersList)
      {
        s= pl.getSocket();
        players.add(new Player(new PrintWriter(s.getOutputStream(), true),
                new BufferedReader(new InputStreamReader(s.getInputStream())),
                s,
                deck.drawMultipleCards(cardsPerGame,returnToDeckCards)));
      }
    }

    public void sendMessage(String msg)
    {
      for(Player player : players)
      {
        player.sendMessage(msg);
      }
    }

    public void sendTopCard(UnoCard card) // TO SEND WHAT THE PLAYER PLAYED WHICH BECOMES TOP CARD
    {
      String msg = "top card\n"+card;
      for(Player p : players)
      {
          p.sendMessage(msg);
      }
      returnToDeckCards.push(card);
    }

    public void whomToPlay(Player player)
    {
      for(Player p: players)
      {
        if(p.equals(player))
        {
          p.sendMessage("play");
        }
        else
        {
          p.sendMessage("wait");
        }
      }
    }

    @Override
    public void run()
    {
      int increment=1,currentPlayerNumber;
      UnoCard topCard=deck.drawCard(returnToDeckCards),newTopCard = null;
      Player currentPlayer;
      sendMessage("start");   // sending game start message
      for(Player p : players)
      {
        p.sendCards();
      }
      System.out.println("deck cards :" + deck.getCardsInDeck());

      currentPlayerNumber=0;
      player_loop:
      while(true)
      {
        sendTopCard(topCard);
        currentPlayer = players.get(currentPlayerNumber);
        System.out.println("currentPlayer is :" + currentPlayerNumber);
        whomToPlay(currentPlayer);
        try {

          System.out.println("Getting card");
          while_loop:
          while(true) {
            String msg = currentPlayer.readLine();
            System.out.println("Before Getcard : "+msg);
            switch (msg) {
              case "draw card" -> currentPlayer.drawCard(deck.drawCard(returnToDeckCards));
              case "card" -> {
                newTopCard = currentPlayer.getCard(topCard);
                if (newTopCard != null) {
                  topCard = newTopCard;
                  break while_loop;
                }
                System.out.println("Wrong card");
              }
              default -> {
                currentPlayerNumber = (noOfPlayers + currentPlayerNumber + increment) % noOfPlayers;
                continue player_loop;
              }
            }

          }
        } catch (Exception e) {
          e.printStackTrace();
        }

        if(currentPlayer.cardCount()==0) {
          System.out.println(currentPlayer + " has finished");
          break;
        }

        switch (topCard.getNumber()) {
          case Reverse -> {
            increment *= -1;
            currentPlayerNumber = (noOfPlayers + currentPlayerNumber + increment) % noOfPlayers;
          }

          case DrawTwo -> {
            currentPlayerNumber = (noOfPlayers + currentPlayerNumber + increment) % noOfPlayers;
            players.get(currentPlayerNumber).drawCards(deck.drawMultipleCards(2,returnToDeckCards));
          }

          case Skip -> currentPlayerNumber = (noOfPlayers + currentPlayerNumber + 2*increment) % noOfPlayers;

          case WildDrawFour -> players.get(
                  (noOfPlayers + currentPlayerNumber + increment) % noOfPlayers)
                  .drawCards(deck.drawMultipleCards(4,returnToDeckCards));
          case Wild -> {}

          default -> currentPlayerNumber = (noOfPlayers + currentPlayerNumber + increment) % noOfPlayers;
        }
      }// end of while loop
      PlayerQueue.removeGame(this);
      System.out.println("sending won or lost");
      for(Player player : players)
      {
        if(player==currentPlayer)
        {
          player.sendMessage("won");
        }
        else
        {
          player.sendMessage("lost");
        }
        try {
          player.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
}
