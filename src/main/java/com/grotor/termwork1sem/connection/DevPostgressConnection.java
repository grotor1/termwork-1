package com.grotor.termwork1sem.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DevPostgressConnection {
    public static final String HOST = "jdbc:postgresql://localhost:5432/neet_shelter?characterEncoding=utf8";
    public static final String USER = "postgres";
    public static final String PASSWORD = "23102004";

    private static Connection conn;

    public static Connection getInstance() {
        if (conn == null) {
            try {
                Class.forName("org.postgresql.Driver");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                conn = DriverManager.getConnection(HOST, USER, PASSWORD);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return conn;
    }
}
