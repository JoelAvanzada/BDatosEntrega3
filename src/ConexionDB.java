package src;

import java.sql.*;

public class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/DonacionesDB";
    private static final String USER = "root";
    private static final String PASSWORD = "<pass>";

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
