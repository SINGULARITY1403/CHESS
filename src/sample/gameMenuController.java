package src.sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class gameMenuController
{
    @FXML
    private Button playGameButton,gameStatsButton, closeDirectGameButton,modifyUserNameButton,deleteUserAccountButton;
    public void playGameButtonOnAction(ActionEvent event) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("playMenu.fxml"));
        Stage Window = (Stage) playGameButton.getScene().getWindow();
        Window.setScene(new Scene(root, 749,612));
    }

    public void gameStatsButtonOnAction(ActionEvent event) throws Exception {
        databaseConnection connectNow = new databaseConnection();
        Connection connectDB = connectNow.getConnection();
        Statement st1 = connectDB.createStatement();
        ResultSet rs1 = st1.executeQuery("USE chessdbms");
        String m = JOptionPane.showInputDialog("Enter Your User-Name again: ");
        int v = validationAccount(m);
        if(v == -1 || m == null)
        {
            JOptionPane.showMessageDialog(null, "Please Put Correct a user-name !!!",
                    "WRONG USER-NAME PROVIDED !!", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            Statement statement = connectDB.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM user_account WHERE username='" + m + "'");

            while (rs.next()) {
                if (rs.getBoolean(1) == true) {

                    String y = rs.getString("username");
                    int mw = rs.getInt("matches_won");
                    int ml = rs.getInt("matches_lost");
                    int md = rs.getInt("matches_drawn");
                    int p = rs.getInt("points");
                    String stats = "User-Name: " + y + "\n" + "Matches Won: " + mw + "\n"
                            + "Matches Lost: " + ml + "\n" + "Matches Drawn: " + md + "\n" + "Points Earned: " + p;
                    JOptionPane.showMessageDialog(null, stats);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Please Put Correct a user-name !!!",
                            "WRONG USER-NAME PROVIDED !!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

    }
    public void deleteUserAccountButtonOnAction(ActionEvent event) throws Exception
    {
        databaseConnection connectNow = new databaseConnection();
        Connection connectDB = connectNow.getConnection();
        String m = JOptionPane.showInputDialog("Enter the User-Name of the account to delete: ");
        Statement st1 = connectDB.createStatement();
        ResultSet rs1 = st1.executeQuery("USE chessdbms");
        int verify = validationAccount(m);
        if(verify == 1)
        {
            Statement statement = connectDB.createStatement();
            int rs2 = statement.executeUpdate("DELETE FROM user_account WHERE username='" + m + "'");
            JOptionPane.showMessageDialog(null,"Account Deleted Press OK to be directed to login and register page.....");
            Parent root = FXMLLoader.load(getClass().getResource("user.fxml"));
            Stage Window = (Stage) deleteUserAccountButton.getScene().getWindow();
            Window.setScene(new Scene(root, 749,612));
        }
        else
        {
            JOptionPane.showMessageDialog(null, "Wrong Account Details given. Please Check again!!",
                    "WRONG !!", JOptionPane.ERROR_MESSAGE);
        }
    }
    public void modifyUserNameButtonOnAction(ActionEvent event) throws Exception
    {
        databaseConnection connectNow = new databaseConnection();
        Connection connectDB = connectNow.getConnection();
        String m = JOptionPane.showInputDialog("Enter the current User-Name : ");
        Statement st1 = connectDB.createStatement();
        ResultSet rs1 = st1.executeQuery("USE chessdbms");
        int verify = validationAccount(m);
        if(m == null || verify == -1)
        {
            JOptionPane.showMessageDialog(null, "Please Put Correct a user-name !!!",
                    "USER-NAME BLANK OR CANCELLED IN PROCESS!!", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            Statement statement = connectDB.createStatement();
            ResultSet rs2 = statement.executeQuery("SELECT firstname, lastname, username FROM user_account WHERE username='" + m + "'");
            Statement st2 = connectDB.createStatement();
            String x = JOptionPane.showInputDialog("Enter the User-Name you want to modify: ");
            int rs3 = st2.executeUpdate("UPDATE user_account SET username='" + x + "' WHERE username='" + m + "'");
            JOptionPane.showMessageDialog(null,"USER-NAME UPDATED " + "Please use this user-name from now on: " + x);
        }
    }
    public void closeDirectGameButtonOnAction(ActionEvent event)
    {
        Stage stage = (Stage) closeDirectGameButton.getScene().getWindow();
        stage.close();
    }
    public void modifyAccountRecords(String userName,int mw,int ml,int md)
    {
    }

    public int validationAccount(String m) throws Exception {
        int v = -1;
        databaseConnection connectNow = new databaseConnection();
        Connection connectDB = connectNow.getConnection();
        Statement st1 = connectDB.createStatement();
        ResultSet rs1 = st1.executeQuery("USE chessdbms");
        String verifyAccount = "SELECT * FROM user_account WHERE username = '" + m + "'";
        ResultSet query = st1.executeQuery(verifyAccount);
        while (query.next()) {
            if (query.getBoolean(1) == true) {
                return v = 1;
            } else {
                return v = 0;
            }
        }
        return v;
    }
}

