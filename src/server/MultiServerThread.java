package server;

public class MultiServerThread extends Thread
{
    private final java.net.Socket socket;
  public MultiServerThread(java.net.Socket socket)
  {
  	super("MultiServerThread");
  	this.socket = socket;
  }

  public void run()
  {
  	System.out.println("New client with address : "+socket.getInetAddress());
  	try {
  	    java.io.BufferedReader in = new java.io.BufferedReader(new java.io.InputStreamReader(socket.getInputStream()));
        String inputLine;
        server.PlayerQueue.AddPlayerToQueue(socket);
        while (!server.PlayerQueue.isGameStarted()) {
            if(in.ready())
            {
                inputLine = in.readLine();
                if(inputLine.equalsIgnoreCase("cancel"))
                {
                    server.PlayerQueue.removePlayer(socket);
                    System.out.println(socket.getInetAddress()+" : left");
                    break;
                }
            }
            Thread.sleep(500);
        }
    }
     catch (java.io.IOException | InterruptedException e)
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
