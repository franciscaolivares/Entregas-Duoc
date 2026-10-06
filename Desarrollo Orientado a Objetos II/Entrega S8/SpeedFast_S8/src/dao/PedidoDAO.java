package dao;

import model.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {
    public void guardar(Pedido pedido) throws SQLException {
        String sql = "INSERT INTO pedidos (id_pedido, direccion_entrega, tipo_pedido, distancia_km, estado) VALUES (?, ?, ?, ?, ?)";
        try (Connection conexion = ConexionBD.obtenerConexion(); PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, pedido.getId()); ps.setString(2, pedido.getDireccionEntrega());
            ps.setString(3, pedido instanceof PedidoExpress ? "Express" : pedido.getTipoPedido());
            ps.setDouble(4, pedido.getDistanciaKm()); ps.setString(5, pedido.getEstado().name());
            ps.executeUpdate();
        }
    }

    public List<Pedido> listarTodos() throws SQLException {
        List<Pedido> pedidos = new ArrayList<>();
        String sql = "SELECT id_pedido, direccion_entrega, tipo_pedido, distancia_km, estado FROM pedidos ORDER BY id_pedido";
        try (Connection conexion = ConexionBD.obtenerConexion(); PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id_pedido"); String direccion = rs.getString("direccion_entrega");
                double km = rs.getDouble("distancia_km"); Pedido pedido;
                switch (rs.getString("tipo_pedido")) {
                    case "Comida": pedido = new PedidoComida(id, direccion, km); break;
                    case "Encomienda": pedido = new PedidoEncomienda(id, direccion, km); break;
                    case "Express": pedido = new PedidoExpress(id, direccion, km); break;
                    default: throw new SQLException("Tipo de pedido desconocido en base de datos.");
                }
                pedido.setEstado(EstadoPedido.valueOf(rs.getString("estado")));
                pedidos.add(pedido);
            }
        }
        return pedidos;
    }

    public void cambiarEstado(Connection conexion, int id, EstadoPedido estado) throws SQLException {
        try (PreparedStatement ps = conexion.prepareStatement("UPDATE pedidos SET estado = ? WHERE id_pedido = ? AND estado = ?")) {
            ps.setString(1, estado.name()); ps.setInt(2, id);
            ps.setString(3, estado == EstadoPedido.EN_REPARTO ? "PENDIENTE" : "EN_REPARTO");
            if (ps.executeUpdate() != 1) throw new SQLException("El pedido no existe o su estado ya cambió.");
        }
    }

    public void create(Pedido p) throws SQLException { guardar(p); }
    public List<Pedido> readAll() throws SQLException { return listarTodos(); }
    public void update(Pedido p) throws SQLException {
        try(Connection c=ConexionDB.getConnection();PreparedStatement s=c.prepareStatement("UPDATE pedidos SET direccion_entrega=?,tipo_pedido=?,distancia_km=?,estado=? WHERE id_pedido=?")){
            s.setString(1,p.getDireccionEntrega());s.setString(2,p instanceof PedidoExpress ? "Express" : p.getTipoPedido());s.setDouble(3,p.getDistanciaKm());s.setString(4,p.getEstado().name());s.setInt(5,p.getId());
            if(s.executeUpdate()!=1)throw new SQLException("Pedido no encontrado.");
        }
    }
    public void delete(int id) throws SQLException {
        try(Connection c=ConexionDB.getConnection();PreparedStatement s=c.prepareStatement("DELETE FROM pedidos WHERE id_pedido=?")){
            s.setInt(1,id);if(s.executeUpdate()!=1)throw new SQLException("Pedido no encontrado.");
        }
    }

}
