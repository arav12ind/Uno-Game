package gameScreen;

import OpeningScreen.Main;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.event.ActionEvent;
import uno.*;

import java.net.URL;
import java.util.ResourceBundle;

public class GameScreenController implements Initializable {


    @FXML AnchorPane anchorPane;
    @FXML ImageView topCard;
    @FXML
    Button drawCard;
    static BooleanProperty topCardChanged;
    static String topCardPath;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        drawCard.setVisible(false);
        topCardChanged = new SimpleBooleanProperty(false);
        FlowPane flowpane = new FlowPane(117, 800);
        flowpane.setLayoutX(23);
        flowpane.setLayoutY(194);
        flowpane.setHgap(10);
        flowpane.setPrefWrapLength(700);
        topCardChanged.addListener((observableValue, oldValue, newValue) -> {

            if(newValue.equals(true))
            {
                topCard.setImage(new Image(topCardPath));

                topCardChanged.set(false);
            }

        });
        anchorPane.getChildren().add(flowpane);

        new Thread(new ClientUnoGame(drawCard, flowpane)).start();
    }

    public static void setTopCardOnScreen(UnoCard topCard) {
        try {
            topCardPath = topCard.toPath();
            System.out.println("changing");
            topCardChanged.set(true);
            System.out.println("changed");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void drawCardController(ActionEvent actionEvent) {
        String msg = drawCard.getText();
        if(msg.equals("draw card"))
        {
            drawCard.setText("pass");
        }
        else
        {
            drawCard.setText("draw card");
            drawCard.setVisible(false);
        }
        Main.out.println(msg);
    }
}
