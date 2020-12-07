package jm.task.core.jdbc.util;

import com.mysql.jdbc.Driver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.TimeZone;

public class Util {

    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/dd?serverTimezone=" + TimeZone.getDefault().getID();
    private static final String USER = "root";
    private static final String PASSWORD = "0dae1feeg";

    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Ошибка соединения");
            e.printStackTrace();
        }
        return connection;
    }


}


// реализуйте настройку соеденения с БД

