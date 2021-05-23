package src.sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class mainController implements Initializable {
    @FXML
    private ImageView warriorsImageView;
    @FXML
    private Button yesButton;
    @FXML
    private Button noButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File codersChessFile = new File("Images folder\\51VnBcivWbL._SX258_BO1,204,203,200_.jpg");
        Image codersChessImage = new Image(codersChessFile.toURI().toString());
        warriorsImageView.setImage(codersChessImage);
    }

    public void yesButtonOnAction(ActionEvent event) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("play.fxml"));
        Stage Window = (Stage) yesButton.getScene().getWindow();
        Window.setScene(new Scene(root, 749,612));
    }

    public void noButtonOnAction(ActionEvent event)
    {
        Stage stage = (Stage) noButton.getScene().getWindow();
        stage.close();
    }
}
