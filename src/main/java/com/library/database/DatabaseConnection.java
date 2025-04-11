/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.library.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author arman
 */
public class DatabaseConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/library";
    private static final String USER = "cst8288";
    private static final String PASSWORD = "cst8288";

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(URL, USER, PASSWORD);
            return con;
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("cannot connect to database; check file databaseConnection");
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("class not founfd");
            return null;
        }
    }

}
