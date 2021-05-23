package src.sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class registerController
{
    @FXML
    Button backButton2,RegisterButton;
    @FXML
    private TextField usernameTextField, firstnameTextField, lastnameTextField;
    @FXML
    private Label registerLabel, firstnameMessageLabel, lastnameMessageLabel, usernameMessageLabel;

    public void registerButtonOnAction(ActionEvent event)
    {
        int f;
        f = registerUserValidation();
        if(f == 1)
        {
            registerLabel.setText("User-Name already exists.");
        }
        else
        {
            if(firstnameTextField.getText().isBlank() == true)
            {
                firstnameMessageLabel.setText("First-Name is blank.");
            }
            else if(lastnameTextField.getText().isBlank() == true)
            {
                lastnameMessageLabel.setText("Last-Name is blank.");
            }
            else if(usernameTextField.getText().isBlank() == true)
            {
                usernameMessageLabel.setText("User-Name is blank.");
            }
            else
            {
                registerUserDatabase();
            }
        }
    }

    public int registerUserValidation()
    {
        databaseConnection connectNow = new databaseConnection();
        Connection connectDB = connectNow.getConnection();
        int x = -1;
        String verifyRegister = "SELECT * FROM user_account WHERE username = '" + usernameTextField.getText() + "'";
        try{

            Statement statement = connectDB.createStatement();
            try {
                Statement st1 = connectDB.createStatement();
                ResultSet rs1 = st1.executeQuery("USE chessdbms");
            }catch (SQLException e)
            {
                e.printStackTrace();
            }
            ResultSet queryResult1 = statement.executeQuery(verifyRegister);
            while(queryResult1.next())
            {
                if(queryResult1.getBoolean(1) == true)
                {
                    x = 1;
                }
                else
                {
                    x = 0;
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return x;
    }
    public void registerUserDatabase()
    {
        databaseConnection connectNow = new databaseConnection();
        Connection connectDB = connectNow.getConnection();

        String firstname = firstnameTextField.getText();
        String lastname = lastnameTextField.getText();
        String username = usernameTextField.getText();

        String insertFields = "Insert into user_account (firstname, lastname, username, matches_won, matches_lost, matches_drawn, points) Values ('";
        String insertValues = firstname + "','" + lastname + "','" + username + "'," + 0 + "," + 0 + "," + 0 + "," + 0 +")";
        String insertToRegister = insertFields + insertValues;

        try
        {
            Statement st1 = connectDB.createStatement();
            ResultSet rs1 = st1.executeQuery("USE chessdbms");
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);
            registerLabel.setText("User is Registered Successfully !!");
        }
        catch(Exception e)
        {
            e.printStackTrace();
            e.getCause();
        }
    }
    public void backBtn2OnAction() throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("user.fxml"));
        Stage Window = (Stage) backButton2.getScene().getWindow();
        Window.setScene(new Scene(root, 749,612));
    }
}
