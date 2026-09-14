package model;

import java.util.ArrayList;

public class ZonaDeCarga {
    private final ArrayList<Pedido> pedidos = new ArrayList<>();
    private final ArrayList<Integer> idsRegistrados = new ArrayList<>();

    public synchronized void agregarPedido(Pedido p) {
        if (p == null || p.getEstado() != EstadoPedido.PENDIENTE) {
            throw new IllegalArgumentException("Solo se pueden agregar pedidos pendientes.");
        }
        if (idsRegistrados.contains(p.getId())) {
            throw new IllegalArgumentException("El pedido ya fue registrado.");
        }
        pedidos.add(p);
        idsRegistrados.add(p.getId());
    }

    public synchronized Pedido retirarPedido() {
        if (pedidos.isEmpty()) {
            return null;
        }
        // La consulta y el retiro se hacen bajo el mismo bloqueo.
        return pedidos.remove(0);
    }
}
