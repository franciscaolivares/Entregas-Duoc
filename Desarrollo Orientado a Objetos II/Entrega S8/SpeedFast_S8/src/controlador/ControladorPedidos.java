package controlador;

import dao.*;
import model.*;
import java.sql.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.SwingUtilities;

public class ControladorPedidos {
    private final PedidoDAO pedidoDAO = new PedidoDAO();
    private final RepartidorDAO repartidorDAO = new RepartidorDAO();
    private final EntregaDAO entregaDAO = new EntregaDAO();
    private final ExecutorService entregas = Executors.newFixedThreadPool(3);

    public ControladorPedidos() {
        Runtime.getRuntime().addShutdownHook(new Thread(entregas::shutdownNow));
    }

    public void verificarConexion() throws SQLException {
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            if (!conexion.isValid(3)) throw new SQLException("No se pudo conectar con MySQL.");
        }
    }

    public void registrar(int id, String direccion, String tipo, double distancia) throws SQLException {
        if (id <= 0 || direccion == null || direccion.trim().isEmpty() ||
                !Double.isFinite(distancia) || distancia <= 0)
            throw new IllegalArgumentException("Ingresa un ID positivo, dirección y distancia mayor que cero.");
        Pedido pedido;
        switch (tipo) {
            case "Comida": pedido = new PedidoComida(id, direccion.trim(), distancia); break;
            case "Encomienda": pedido = new PedidoEncomienda(id, direccion.trim(), distancia); break;
            case "Express": pedido = new PedidoExpress(id, direccion.trim(), distancia); break;
            default: throw new IllegalArgumentException("Tipo de pedido no válido.");
        }
        pedidoDAO.guardar(pedido);
    }

    public List<Pedido> listar() throws SQLException { return pedidoDAO.listarTodos(); }
    public int registrarRepartidor(String nombre) throws SQLException {
        if (nombre == null || nombre.trim().isEmpty())
            throw new IllegalArgumentException("Ingresa el nombre del repartidor.");
        return repartidorDAO.guardar(nombre.trim());
    }
    public List<Repartidor> listarRepartidores() throws SQLException { return repartidorDAO.listarTodos(); }

    public void iniciarEntrega(int idPedido, Repartidor repartidor, Runnable alActualizar) throws SQLException {
        if (repartidor == null) throw new IllegalArgumentException("Selecciona un repartidor.");
        Pedido elegido = null;
        for (Pedido pedido : listar()) if (pedido.getId() == idPedido) elegido = pedido;
        if (elegido == null || elegido.getEstado() != EstadoPedido.PENDIENTE)
            throw new IllegalArgumentException("Selecciona un pedido pendiente.");
        ZonaDeCarga zona = new ZonaDeCarga();
        zona.agregarPedido(elegido);
        elegido.asignarRepartidor(repartidor.getNombre());
        final int entregaId;
        try (Connection conexion = ConexionBD.obtenerConexion()) {
            conexion.setAutoCommit(false);
            try {
                new PedidoDAO().cambiarEstado(conexion, idPedido, EstadoPedido.EN_REPARTO);
                entregaId = entregaDAO.guardar(conexion, new Entrega(idPedido, repartidor.getId()));
                conexion.commit();
            } catch (SQLException e) { conexion.rollback(); throw e; }
        }
        final Pedido pedidoEnSimulacion = elegido;
        entregas.execute(() -> {
            new Repartidor(repartidor.getNombre(), zona).run();
            if (pedidoEnSimulacion.getEstado() == EstadoPedido.ENTREGADO) {
                try { entregaDAO.finalizar(entregaId, idPedido); }
                catch (SQLException e) { SwingUtilities.invokeLater(() -> javax.swing.JOptionPane.showMessageDialog(null,
                        "No se pudo guardar el fin de la entrega: " + e.getMessage())); }
            }
            SwingUtilities.invokeLater(alActualizar);
        });
    }

}
