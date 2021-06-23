package OpeningScreen;

public class OpeningScreenController implements javafx.fxml.Initializable {
    @javafx.fxml.FXML
    private javafx.scene.control.Label error;

    String host = "localhost", keyboardInput;
    java.net.InetAddress server = null;
    java.net.Socket client = null;
    java.io.BufferedReader in;
    java.io.PrintWriter out;
    waitingScreen.WaitScreenController wsController;

    @Override
    public void initialize(java.net.URL url, java.util.ResourceBundle resourceBundle) {

    }

    public void playGame(javafx.event.ActionEvent event) throws java.io.IOException {
      try {
          startClient();
          javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/waitingScreen/WaitScreen.fxml"));
          javafx.stage.Stage stage = (javafx.stage.Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
          stage.getScene().setRoot(loader.load());
          stage.show();
         // wsController = loader.getController();
          System.out.println("calling wscontroller");
          waitingScreen.WaitScreenController.initVariables(in,out,stage);

      }
      catch(java.io.IOException | InterruptedException ie) {
        error.setText(ie.getMessage());
      }



        //  wsController.

    }

    public void exitGame(javafx.event.ActionEvent event) {
        javafx.application.Platform.exit();
    }

    public void startClient() throws java.io.IOException {


            server = java.net.InetAddress.getByName(host);
            client = new java.net.Socket(server, 4444);
            in = new java.io.BufferedReader(new java.io.InputStreamReader(client.getInputStream()));
            out = new java.io.PrintWriter(new java.io.OutputStreamWriter(client.getOutputStream()));



    }
}






