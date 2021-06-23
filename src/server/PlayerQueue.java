package server;

public class PlayerQueue
{
  private static java.util.Queue<java.net.Socket> playerQueue = new java.util.LinkedList<java.net.Socket>();
  private static java.util.ArrayList<java.net.Socket> sendPlayerList = new java.util.ArrayList<java.net.Socket>();
  private static final int playersPerGame = 1;
  private static boolean gameOver = false;
  private static boolean gameStarted = false;
  public static void AddPlayerToQueue(java.net.Socket player)
  {
    int i;
      playerQueue.add(player);
      System.out.println("player queue size : " + playerQueue.size());
      if(playerQueue.size()==playersPerGame)
      {
        for(i=0;i<playersPerGame;++i)
          sendPlayerList.add(playerQueue.remove());

        System.out.println("Starting new game");
        gameStarted=true;
        new uno.UnoGame(sendPlayerList,playersPerGame);
        gameOver = true;
        System.out.println("COMPLETED GAME...");
      }
      else return;
      sendPlayerList.clear();
      System.out.println("play queue cleared..");
  }

  public static int getPlayersInQueue()
  {
    return playerQueue.size();
  }
  public static void removePlayer(java.net.Socket playerID)
  {
    playerQueue.remove(playerID);
  }
  public static boolean getGameStatus()
  {
    return gameOver;
  }
  public static boolean isGameStarted(){ return gameStarted;}
}
