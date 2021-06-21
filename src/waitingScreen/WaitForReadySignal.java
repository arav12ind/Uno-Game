package waitingScreen;

import javafx.concurrent.Task;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class WaitForReadySignal extends Task<Void> {
    BufferedReader in;
    PrintWriter out;
    Stage stage;

    static boolean serverReadySignal = false;
    int cases = 0;
    public WaitForReadySignal(BufferedReader in, PrintWriter out) throws IOException, InterruptedException {
        this.in = in;
        this.out = out;
    }



    public Void call() {
        WaitScreenController wsc = new waitingScreen.WaitScreenController();
        WaitScreenController.cl = new WaitScreenController.CancelManager();
        while(!WaitScreenController.cl.getIsCancelled()) {
            try {
                    if (in.ready()) {
                        serverReadySignal = true;
                        System.out.println("server is ready");
                        wsc.readySignalOccured();
                        break;
                    }
                    Thread.sleep(500);
                } catch (IOException | InterruptedException e) {
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
