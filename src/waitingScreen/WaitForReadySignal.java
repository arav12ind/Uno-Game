package waitingScreen;

import OpeningScreen.Main;

public class WaitForReadySignal extends javafx.concurrent.Task<Void> {

    static boolean serverReadySignal = false;
    private WaitScreenController  wsc;
    int cases = 0;

    private boolean isCancelled;
    private final Object isCancelledSYNC;
    public boolean getIsCancelled() {
        synchronized (isCancelledSYNC) {
            return isCancelled;
        }
    }
    public void setIsCancelled()
    {
        synchronized (isCancelledSYNC)
        {
            isCancelled=true;
        }
    }
    public WaitForReadySignal(WaitScreenController wsc)
    {
        this.wsc=wsc;
        isCancelled=false;
        isCancelledSYNC= new Object();
    }
    public Void call() {
        while(!getIsCancelled()) {
            try {
                    if (Main.in.ready()) {
                        serverReadySignal = true;
                        String msg = Main.in.readLine();
                        System.out.println("server is ready : " + msg);
                        wsc.readySignalOccured();
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
