package com.roshka.bootcamp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DBManagment {
        public static Connection con = null;
        public static void initializeDatabase()
            throws SQLException, ClassNotFoundException
        {
            String dbDriver = "org.postgresql.Driver";
            String dbURL = "jdbc:postgresql://localhost:5432/bootcamp_market";
            String dbUsername = "postgres";
            String dbPassword = "postgres";
            Class.forName(dbDriver);
            con = DriverManager.getConnection(dbURL, dbUsername, dbPassword);
        }

    }