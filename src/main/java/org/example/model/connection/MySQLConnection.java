package org.example.model.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnection {
    private static Connection con = null;
    private static final String URL = "jdbc:mysql://localhost:3306/eco";
    private static final String USER = "root";
    private static final String PASS = "";

    private MySQLConnection(){}
    public static Connection getConnection(){
        if (con == null){
            try {
                con = DriverManager.getConnection(URL, USER, PASS);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Conexión establecida");
        return con;
    }

    public static void closeConnection(){
        if (con != null){
            try {
                con.close();
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }
}
