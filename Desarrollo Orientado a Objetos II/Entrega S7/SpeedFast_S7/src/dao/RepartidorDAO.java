package dao;

import model.Repartidor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RepartidorDAO {
    public int guardar(String nombre) throws SQLException {
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement("INSERT INTO repartidores (nombre) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, nombre); ps.executeUpdate();
            try (ResultSet claves = ps.getGeneratedKeys()) {
                if (claves.next()) return claves.getInt(1);
                throw new SQLException("No se obtuvo el ID del repartidor.");
            }
        }
    }

    public List<Repartidor> listarTodos() throws SQLException {
        List<Repartidor> repartidores = new ArrayList<>();
        try (Connection conexion = ConexionBD.obtenerConexion();
             PreparedStatement ps = conexion.prepareStatement("SELECT id_repartidor, nombre FROM repartidores ORDER BY id_repartidor");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) repartidores.add(new Repartidor(rs.getInt("id_repartidor"), rs.getString("nombre")));
        }
        return repartidores;
    }
}
