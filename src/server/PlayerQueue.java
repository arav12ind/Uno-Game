package server;

import uno.UnoGame;

import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class PlayerQueue
{
  private static final Queue<MultiServerThread> playerQueue = new LinkedList<>();
  private static final LinkedList<UnoGame> runningGames = new LinkedList<>();
  private static final int playersPerGame = 1;
  public static void AddPlayerToQueue(MultiServerThread player) throws Exception {
      playerQueue.add(player);
      System.out.println("player queue size : " + playerQueue.size());
      if(playerQueue.size()==playersPerGame)
      {
        System.out.println("Starting new game");
        UnoGame ug = new UnoGame(new ArrayList<>(playerQueue),7);
        ug.start();
        runningGames.add(ug);
        playerQueue.clear();
      }
  }

  public static int getPlayersInQueue()
  {
    return playerQueue.size();
  }
  public static void removePlayer(Socket playerID)
  {
    playerQueue.remove(playerID);
  }
  public static void removeGame(UnoGame ug) { runningGames.remove(ug); }
}
