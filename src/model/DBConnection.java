package model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

/**
 *
 * @author BENDAMOU
 */
public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/cabinet_medical";
    private static final String USER = "root";
    private static final String PASSWORD = "";
    
    public static Connection getConnection() throws SQLException{
        try {
        Class.forName("com.mysql.cj.jdbc.Driver");
       } catch (ClassNotFoundException e) {
        e.printStackTrace();
        }
        return DriverManager.getConnection(URL,USER,PASSWORD);
    }
}
