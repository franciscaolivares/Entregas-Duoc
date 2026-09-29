package controlador;

import model.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ControladorPedidos {
    private final List<Pedido> pedidos = new ArrayList<>();
    private final ExecutorService entregas = Executors.newFixedThreadPool(3);

    public synchronized void registrar(int id, String direccion, String tipo, double distancia) {
        if (id <= 0 || direccion == null || direccion.trim().isEmpty() ||
                !Double.isFinite(distancia) || distancia <= 0) {
            throw new IllegalArgumentException("Ingresa un ID positivo, dirección y distancia mayor que cero.");
        }
        for (Pedido pedido : pedidos) {
            if (pedido.getId() == id) throw new IllegalArgumentException("Ya existe un pedido con ese ID.");
        }
        Pedido pedido;
        switch (tipo) {
            case "Comida": pedido = new PedidoComida(id, direccion.trim(), distancia); break;
            case "Encomienda": pedido = new PedidoEncomienda(id, direccion.trim(), distancia); break;
            case "Express": pedido = new PedidoExpress(id, direccion.trim(), distancia); break;
            default: throw new IllegalArgumentException("Tipo de pedido no válido.");
        }
        pedidos.add(pedido);
    }

    public synchronized List<Pedido> listar() { return new ArrayList<>(pedidos); }

    public synchronized void iniciarEntrega(int id, String nombre, Runnable alActualizar) {
        if (nombre == null || nombre.trim().isEmpty())
            throw new IllegalArgumentException("Escribe el nombre del repartidor.");
        Pedido elegido = null;
        for (Pedido pedido : pedidos) if (pedido.getId() == id) elegido = pedido;
        if (elegido == null) throw new IllegalArgumentException("Selecciona un pedido registrado.");
        if (elegido.getEstado() != EstadoPedido.PENDIENTE)
            throw new IllegalArgumentException("Este pedido ya inició su entrega.");
        ZonaDeCarga zona = new ZonaDeCarga();
        zona.agregarPedido(elegido);
        elegido.asignarRepartidor(nombre.trim());
        elegido.setEstado(EstadoPedido.EN_REPARTO);
        try {
            entregas.execute(() -> {
                try { new Repartidor(nombre.trim(), zona).run(); }
                finally { javax.swing.SwingUtilities.invokeLater(alActualizar); }
            });
        } catch (RuntimeException e) {
            elegido.setEstado(EstadoPedido.PENDIENTE);
            throw e;
        }
    }
}
