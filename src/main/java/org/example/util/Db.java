package org.example.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {

    public static final String url = "jdbc:postgresql://localhost:5432/postgres";
    public static final String user = "postgres";
    public static final String password = "@18012004@";

    public static Connection connection() {

        Connection conn = null;

        try {
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("connection success");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

}
