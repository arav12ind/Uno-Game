package waitingScreen;

import OpeningScreen.Main;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class WaitForReadySignal extends Task<Void> {
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
    public WaitForReadySignal()
    {
        isCancelled=false;
        isCancelledSYNC= new Object();
    }
    public Void call() {
        while(!getIsCancelled()) {
            try {
                    if (Main.in.ready()) {
                        String msg = Main.in.readLine();
                        System.out.println("server is ready : " + msg);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Main.scene.setRoot(FXMLLoader.load(WaitForReadySignal.this.getClass().getResource("/gameScreen/UnoGameScreen.fxml")));
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        break;
                    }
                    Thread.sleep(500);
                } catch (IOException | InterruptedException e) {
                e.printStackTrace();
                }
        }
        return null;
    }

}
