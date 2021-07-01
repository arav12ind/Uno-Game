package server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class MultiServerThread extends Thread
{
    private boolean gameStarted;
    private final Socket socket;
  public MultiServerThread(Socket socket)
  {
  	super("MultiServerThread");
  	gameStarted = false;
  	this.socket = socket;
  }
  public void GameStarted()
  {
      gameStarted = true;
  }
  public Socket getSocket()
  {
      return socket;
  }
  public void run()
  {
  	System.out.println("New client with address : "+socket.getInetAddress());
  	try {
  	    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String inputLine;
        PlayerQueue.AddPlayerToQueue(this);
        while (!gameStarted) {
            if(in.ready())
            {
                inputLine = in.readLine();
                if(inputLine.equalsIgnoreCase("cancel"))
                {
                    PlayerQueue.removePlayer(socket);
                    System.out.println(socket.getInetAddress()+" : left");
                    break;
                }
            }
            Thread.sleep(500);
        }
    } catch (Exception e)
    {
         e.printStackTrace();
    } finally
    {
  		try
      {
  		    socket.close();
  		}
      catch(Exception e)
      {
  		    e.printStackTrace();
      }
  	}
  }// end of run function

}// end of class definition
