package dao;

import model.Entrega;
import java.sql.*;

public class EntregaDAO {
    public void guardar(Entrega entrega) throws SQLException {
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            conexion.setAutoCommit(false);
            try {
                guardar(conexion, entrega);
                new PedidoDAO().cambiarEstado(conexion, entrega.getIdPedido(), model.EstadoPedido.EN_REPARTO);
                conexion.commit();
            } catch (SQLException e) { conexion.rollback(); throw e; }
        }
    }

    public int guardar(Connection conexion, Entrega entrega) throws SQLException {
        try (PreparedStatement ps = conexion.prepareStatement(
                "INSERT INTO entregas (id_pedido, id_repartidor) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, entrega.getIdPedido()); ps.setInt(2, entrega.getIdRepartidor()); ps.executeUpdate();
            try (ResultSet claves = ps.getGeneratedKeys()) {
                if (claves.next()) return claves.getInt(1);
                throw new SQLException("No se obtuvo el ID de la entrega.");
            }
        }
    }

    public void finalizar(int idEntrega, int idPedido) throws SQLException {
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            conexion.setAutoCommit(false);
            try {
                try (PreparedStatement ps = conexion.prepareStatement(
                        "UPDATE entregas SET estado = 'ENTREGADO', fecha_fin = CURRENT_TIMESTAMP WHERE id_entrega = ? AND id_pedido = ? AND estado = 'EN_REPARTO'")) {
                    ps.setInt(1, idEntrega); ps.setInt(2, idPedido);
                    if (ps.executeUpdate() != 1) throw new SQLException("Entrega no disponible para finalizar.");
                }
                new PedidoDAO().cambiarEstado(conexion, idPedido, model.EstadoPedido.ENTREGADO);
                conexion.commit();
            } catch (SQLException e) { conexion.rollback(); throw e; }
        }
    }
}
