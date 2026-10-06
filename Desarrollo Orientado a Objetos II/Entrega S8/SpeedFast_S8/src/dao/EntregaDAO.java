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

    /** CRUD administrativo: la simulación S7 conserva sus transacciones y estados. */
    public void create(Entrega e) throws SQLException {
        try(Connection c=ConexionDB.getConnection();PreparedStatement s=c.prepareStatement("INSERT INTO entregas (id_pedido,id_repartidor,fecha_inicio) VALUES (?,?,?)")){
            s.setInt(1,e.getIdPedido());s.setInt(2,e.getIdRepartidor());s.setTimestamp(3,Timestamp.valueOf(e.getFecha().atTime(e.getHora())));s.executeUpdate();
        }
    }
    public java.util.List<Entrega> readAll() throws SQLException {
        java.util.List<Entrega> lista=new java.util.ArrayList<>();
        try(Connection c=ConexionDB.getConnection();PreparedStatement s=c.prepareStatement("SELECT id_entrega,id_pedido,id_repartidor,fecha_inicio FROM entregas ORDER BY id_entrega");ResultSet r=s.executeQuery()){
            while(r.next()) {java.time.LocalDateTime f=r.getTimestamp("fecha_inicio").toLocalDateTime();lista.add(new Entrega(r.getInt("id_entrega"),r.getInt("id_pedido"),r.getInt("id_repartidor"),f.toLocalDate(),f.toLocalTime()));}
        }return lista;
    }
    public void update(Entrega e) throws SQLException {
        try(Connection c=ConexionDB.getConnection();PreparedStatement s=c.prepareStatement("UPDATE entregas SET id_pedido=?,id_repartidor=?,fecha_inicio=? WHERE id_entrega=?")){
            s.setInt(1,e.getIdPedido());s.setInt(2,e.getIdRepartidor());s.setTimestamp(3,Timestamp.valueOf(e.getFecha().atTime(e.getHora())));s.setInt(4,e.getId());if(s.executeUpdate()!=1)throw new SQLException("Entrega no encontrada.");
        }
    }
    public void delete(int id) throws SQLException {
        try(Connection c=ConexionDB.getConnection();PreparedStatement s=c.prepareStatement("DELETE FROM entregas WHERE id_entrega=?")){
            s.setInt(1,id);if(s.executeUpdate()!=1)throw new SQLException("Entrega no encontrada.");
        }
    }

}
