package waitingScreen;

public class WaitForReadySignal extends javafx.concurrent.Task<Void> {
    java.io.BufferedReader in;
    java.io.PrintWriter out;
    javafx.stage.Stage stage;

    static boolean serverReadySignal = false;
    int cases = 0;
    public WaitForReadySignal(java.io.BufferedReader in, java.io.PrintWriter out) throws java.io.IOException, InterruptedException {
        this.in = in;
        this.out = out;
    }



    public Void call() {
        WaitScreenController wsc = new waitingScreen.WaitScreenController();
        WaitScreenController.cl = new waitingScreen.WaitScreenController.CancelManager();
        while(!WaitScreenController.cl.getIsCancelled()) {
            try {
                    if (in.ready()) {
                        serverReadySignal = true;
                        String msg = in.readLine();
                        System.out.println("server is ready : " + msg);
                        wsc.readySignalOccured(in,out);
                        break;
                    }
                    Thread.sleep(500);
                } catch (java.io.IOException | InterruptedException e) {
                e.printStackTrace();
                }
        }
        return null;
    }

    public static boolean getReadyServerSignal()
    {
        return serverReadySignal;
    }

}
