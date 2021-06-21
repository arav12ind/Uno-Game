package gameScreen;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import uno.UnoCard;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class GameScreenController implements Initializable {

    @FXML
    private Button card1;


    BufferedReader in;
    PrintWriter out;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public static void initVariables(BufferedReader in, PrintWriter out) throws IOException
    {
       new Thread(new ClientUnoGame(in,out)).start();
    }

    public void displayCards(ArrayList<UnoCard> cards)  {
        //card1.setGraphic(new ImageView(new Image("/resources/Eight-Red.jpeg")));
    }
}



