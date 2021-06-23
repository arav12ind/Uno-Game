package server;

public class MultiServer {

    public static void main(String[] args) throws java.io.IOException
    {
        java.net.ServerSocket serverSocket = null;
        boolean listening = true,quit;
        try {
            serverSocket = new java.net.ServerSocket(4444);
            while (listening)
    	       new MultiServerThread(serverSocket.accept()).start();
        } catch (java.io.IOException e) {
            System.err.println("Could not listen on port: 4444.");
            System.exit(-1);
        }
        serverSocket.close();
    }
}
