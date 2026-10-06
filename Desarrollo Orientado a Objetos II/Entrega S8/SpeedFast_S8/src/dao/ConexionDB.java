package dao;
import java.sql.*;
/** Nombre solicitado por S8; reutiliza la configuración y conexión de S7. */
public final class ConexionDB {
 private ConexionDB(){}
 public static Connection getConnection() throws SQLException{return ConexionBD.obtenerConexion();}
}
