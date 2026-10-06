package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBD {
    private static final String URL = System.getenv().getOrDefault("SPEEDFAST_DB_URL",
            "jdbc:mysql://localhost:3306/speedfast_db?serverTimezone=America/Santiago&connectTimeout=5000&socketTimeout=10000");
    private static final String USUARIO = System.getenv().getOrDefault("SPEEDFAST_DB_USER", "root");
    private static final String CLAVE = System.getenv().getOrDefault("SPEEDFAST_DB_PASSWORD", "");

    public static Connection obtenerConexion() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }
}
