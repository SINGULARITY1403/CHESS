package src.sample;

import src.engine.Alliance;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import src.gui.Login;
import src.gui.Login2;
import src.gui.Table;
import src.gui.Table2;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class finalController
{
    /*String alliance1, alliance2, m1, m2, Winner;*/


    @FXML
    Button playerVsPlayerButton, playerVsComputerButton, backPlayMenuButton;
    /*public static String promptUserAlliance() throws Exception
    {
        String[] alliance = new String[2];
        alliance[0] = "Black";
        alliance[1] = "White";

        Object selectAlliance = JOptionPane.showInputDialog(null, "Choose Alliance", "Alliance Selection", JOptionPane.QUESTION_MESSAGE, null,alliance,"White");
        return (String) selectAlliance;
    }
    */
    /*
    public static String promptUserDifficultyLevel() throws Exception
    {
        String[] difficultyLevel = new String[6];
        difficultyLevel[0] = "Beginner";
        difficultyLevel[1] = "Amateur";
        difficultyLevel[2] = "Professional";
        difficultyLevel[3] = "World Class";
        difficultyLevel[4] = "Legendary";
        difficultyLevel[5] = "Ultimate";

        Object selectDifficultyLevel = JOptionPane.showInputDialog(null, "Choose Difficulty", "Difficulty Selection", JOptionPane.QUESTION_MESSAGE, null,difficultyLevel,"Beginner");
        return (String) selectDifficultyLevel;
    }
    */
    /*
    public String getUser1(){
        return this.m1;
    }

    public String getUser2(){
        return this.m2;
    }

    public void setWinner(final String winner){
        this.Winner = winner;
    }

    public Alliance getUserAlliance1(){
        if(this.alliance1.equals("Black") ){
            return Alliance.BLACK;
        }
        else return Alliance.WHITE;
    }

    public Alliance getUserAlliance2(){
        if(this.alliance2.equals("Black") ){
            return Alliance.BLACK;
        }
        else return Alliance.WHITE;
    }
    */
    public void playerVsComputerButtonOnAction(ActionEvent event) throws Exception
    {
        /*
        m1 = JOptionPane.showInputDialog("Enter Your User-Name again: ");
        alliance1 = promptUserAlliance();
        String DifficultyLevel = promptUserDifficultyLevel();
        // It will provide two parameters: 1] User-name, 2] Alliance.
        */
        Login.get().promptUser();
    }

    public void PlayerVsPlayerButtonOnAction(ActionEvent event) throws Exception
    {
        /*
        m1 = JOptionPane.showInputDialog("Enter Your Player 1 User-Name again: ");
        verifyLoginOfUser(m1);
        alliance1 = promptUserAlliance();
        m2 = JOptionPane.showInputDialog("Enter Your Player 2 User-Name again: ");
        verifyLoginOfUser(m2);
        alliance2 = promptUserAlliance();

        if(alliance1 == alliance2)
        {
            JOptionPane.showMessageDialog(null, "Please Select different Alliance !!!",
                    "Same Alliance!!", JOptionPane.ERROR_MESSAGE);
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Match is ready Press OK to continue");
            //It will provide four parameters: 1] User-Name of Player 1, User-Name of Player 2, 3]Alliance of Player 1, 4]Alliance of Player 2
            Table2.get().show();
        }
        */
        Login2.get().promptUser();
    }
    public void backPlayMenuButtonOnAction(ActionEvent event) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("game.fxml"));
        Stage Window = (Stage) backPlayMenuButton.getScene().getWindow();
        Window.setScene(new Scene(root, 749, 612));
    }
    /*
    public void verifyLoginOfUser(String loginName)
    {
        databaseConnection connectNow = new databaseConnection();
        Connection connectDB = connectNow.getConnection();
        Connection con = connectNow.getConnection();

        String verifyLogin = "SELECT * FROM user_account WHERE username = '" + loginName + "'";
        try{
            Statement statement = connectDB.createStatement();
            try {
                Statement st = connectDB.createStatement();
                ResultSet rs = st.executeQuery("USE chessdbms");
            }catch (SQLException e)
            {
                e.printStackTrace();
            }
            ResultSet queryResult = statement.executeQuery(verifyLogin);
            while(queryResult.next())
            {
                if(queryResult.getBoolean(1) == true)
                {
                    JOptionPane.showMessageDialog(null,"Correct Login. PRESS OK to continue.");
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Please Put Correct user-name !!!",
                            "WRONG USER-NAME PROVIDED !!", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }*/
}
