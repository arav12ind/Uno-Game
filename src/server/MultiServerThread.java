package server;

import java.net.*;
import java.io.*;

public class MultiServerThread extends Thread
{
    private final Socket socket;
  public MultiServerThread(Socket socket)
  {
  	super("MultiServerThread");
  	this.socket = socket;
  }

  public void run()
  {
  	System.out.println("New client with address : "+socket.getInetAddress());
  	try {
  	    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String inputLine;
        PlayerQueue.AddPlayerToQueue(socket);
        while (!PlayerQueue.isGameStarted()) {
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
    }
     catch (IOException | InterruptedException e)
     {
	      e.printStackTrace();
     }
    finally
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
