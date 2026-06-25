package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {

    private static final String URL      = "jdbc:mysql://localhost:3306/workbridge_db?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO  = "root";       // cambia si tu usuario es diferente
    private static final String PASSWORD = "123456789";  // tu contraseña de MySQL

    public static Connection getConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }
}
