package src.sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class menuController
{
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    Button backButton1;
    public void loginButtonOnAction(ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("loginUser.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);  // border-less window
            registerStage.setScene(new Scene(root, 749, 612));
            registerStage.show();
            registerStage = (Stage) loginButton.getScene().getWindow();
            registerStage.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }

    public void registerButtonOnAction(ActionEvent event)
    {
        try
        {
            Parent root = FXMLLoader.load(getClass().getResource("Register.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);  // border-less window
            registerStage.setScene(new Scene(root, 749, 612));
            registerStage.show();
            registerStage = (Stage) registerButton.getScene().getWindow();
            registerStage.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }
    public void backButton1OnAction() throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("play.fxml"));
        Stage Window = (Stage) backButton1.getScene().getWindow();
        Window.setScene(new Scene(root,749,612));
    }
}
