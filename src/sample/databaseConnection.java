package src.sample;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class databaseConnection
{
    public Connection databaseLink;

    public Connection getConnection()
    {
        String databaseName = "USE chessdbms";
        String databaseUser = "root";
        String databasePassword = "SiddhesH11$";
        String url = "jdbc:mysql://localhost:3306/?user=root/" + databaseName;

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            try {
                databaseLink = DriverManager.getConnection(url, databaseUser, databasePassword); // for connection purposes.
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        catch(ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        return databaseLink;
    }
}
