package waitingScreen;

public class WaitScreenController implements javafx.fxml.Initializable {
   @javafx.fxml.FXML
   private static javafx.scene.control.Button cancel;
   private static javafx.stage.Stage stage;
   gameScreen.GameScreenController gs;

    @Override
    public void initialize(java.net.URL url, java.util.ResourceBundle resourceBundle) {
       // test.setText("SURPRISE ");
        //System.out.println("in initialize");// always first no matter what...
    }

    public static void initVariables(java.io.BufferedReader in, java.io.PrintWriter out, javafx.stage.Stage s) throws java.io.IOException, InterruptedException {
        //System.out.println("in initvar");
        stage = s;
        new Thread(new waitingScreen.WaitForReadySignal(in,out)).start();
    }

    public static class CancelManager{
        private boolean isCancelled=false;
        private final Object isCancelledSYNC = new Object();
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
    }
    public static waitingScreen.WaitScreenController.CancelManager cl;

        public void setCancelOccured(javafx.event.ActionEvent event)
        {
               try {
                   stage.getScene().setRoot(javafx.fxml.FXMLLoader.load(getClass().getResource("/OpeningScreen/openingScreen.fxml")));
                   stage.show();
                   cl.setIsCancelled();
               } catch (java.io.IOException e) {
               e.printStackTrace();
                }
        }

    public void readySignalOccured(java.io.BufferedReader in, java.io.PrintWriter out)
    {
        System.out.println("in ready func");
        try {
            System.out.println("making loader");



            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/gameScreen/UnoGameScreen.fxml"));
           // gs = loader.getController();

            stage.getScene().setRoot(loader.load());
            System.out.println("calling gscontroller");
            gameScreen.GameScreenController.initVariables(in,out);

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }
    }
}
