package server;

import uno.UnoGame;

import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class PlayerQueue
{
  private static Queue<Socket> playerQueue = new LinkedList<Socket>();
  private static ArrayList<Socket> sendPlayerList = new ArrayList<Socket>();
  private static final int playersPerGame = 1;
  private static boolean gameOver = false;
  private static boolean gameStarted = false;
  public static void AddPlayerToQueue(Socket player) throws InterruptedException {
    int i;
      playerQueue.add(player);
      System.out.println("player queue size : " + playerQueue.size());
      if(playerQueue.size()==playersPerGame)
      {
        for(i=0;i<playersPerGame;++i)
          sendPlayerList.add(playerQueue.remove());

        System.out.println("Starting new game");
        gameStarted=true;
        new UnoGame(sendPlayerList,playersPerGame);
        gameOver = true;
        System.out.println("COMPLETED GAME...");
      }
      sendPlayerList.clear();
      System.out.println("play queue cleared..");
  }

  public static int getPlayersInQueue()
  {
    return playerQueue.size();
  }
  public static void removePlayer(Socket playerID)
  {
    playerQueue.remove(playerID);
  }
  public static boolean getGameStatus()
  {
    return gameOver;
  }
  public static boolean isGameStarted(){ return gameStarted;}
}
