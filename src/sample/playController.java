package src.sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class playController{
    @FXML
    private Button playButton;
    @FXML
    Button backButton1;
    @FXML
    private Button closeButton;

    public void playButtonOnAction(ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("user.fxml"));
            Stage primaryStage = new Stage();
            primaryStage.initStyle(StageStyle.UNDECORATED);
            primaryStage.setScene(new Scene(root, 749, 612));
            primaryStage.show();
            primaryStage = (Stage) playButton.getScene().getWindow();
            primaryStage.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void closeButtonOnAction(ActionEvent event)
    {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}