package src.sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class loginController
{
    @FXML
    TextField usernameLoginTextField;
    @FXML
    private Button loginUserButton;
    @FXML
    Button BackLoginUserButton;
    /*@FXML
    String login = usernameLoginTextField.getText();
    */
    public void loginUserButtonOnAction(ActionEvent event)
    {
        if(usernameLoginTextField.getText().isBlank() == false)
        {
            validateLogin();
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Please put an user-name !!!",
                    "USER-NAME BLANK !! !!", JOptionPane.ERROR_MESSAGE);
        }

    }

    public void validateLogin() {
        databaseConnection connectNow = new databaseConnection();
        Connection connectDB = connectNow.getConnection();
        Connection con = connectNow.getConnection();
        String verifyLogin = "SELECT * FROM user_account WHERE username = '" + usernameLoginTextField.getText() + "'";
        try {
            Statement statement = connectDB.createStatement();
            try {
                Statement st = connectDB.createStatement();
                ResultSet rs = st.executeQuery("USE chessdbms");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ResultSet queryResult = statement.executeQuery(verifyLogin);
            while (queryResult.next()) {
                if (queryResult.getBoolean(1) == true) {
                    Parent root = FXMLLoader.load(getClass().getResource("game.fxml"));
                    Stage Window = (Stage) loginUserButton.getScene().getWindow();
                    Window.setScene(new Scene(root, 749, 612));
                }
                else {
                    JOptionPane.showMessageDialog(null, "Please Put Correct user-name !!!",
                            "WRONG USER-NAME PROVIDED !!", JOptionPane.ERROR_MESSAGE);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void BackLoginUserButtonOnAction(ActionEvent event) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("user.fxml"));
        Stage Window = (Stage) BackLoginUserButton.getScene().getWindow();
        Window.setScene(new Scene(root, 749,612));
    }
}
