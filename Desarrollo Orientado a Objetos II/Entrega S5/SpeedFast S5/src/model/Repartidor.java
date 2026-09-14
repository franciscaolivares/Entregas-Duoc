package model;

import java.util.Random;

public class Repartidor implements Runnable {
    private String nombre;
    private ZonaDeCarga zonaDeCarga;
    private Random random = new Random();

    public Repartidor(String nombre, ZonaDeCarga zonaDeCarga) {
        this.nombre = nombre;
        this.zonaDeCarga = zonaDeCarga;
    }

    public String getNombre() {
        return nombre;
    }

    @Override
    public void run() {
        System.out.println("[" + nombre + "] Iniciando entregas.");
        while (!Thread.currentThread().isInterrupted()) {
            Pedido pedido = zonaDeCarga.retirarPedido();
            if (pedido == null) {
                System.out.println("[" + nombre + "] No quedan pedidos en la zona de carga.");
                return;
            }
            pedido.setEstado(EstadoPedido.EN_REPARTO);
            System.out.println("[" + nombre + "] Retiró pedido " + pedido.getId()
                    + " hacia " + pedido.getDireccionEntrega() + " | " + pedido.getEstado());
            try {
                // La espera ocurre fuera del bloqueo de la zona de carga.
                Thread.sleep(1000 + random.nextInt(2001));
                pedido.setEstado(EstadoPedido.ENTREGADO);
                System.out.println("[" + nombre + "] Pedido " + pedido.getId()
                        + " | " + pedido.getEstado());
            } catch (InterruptedException e) {
                System.out.println("[" + nombre + "] Entrega del pedido " + pedido.getId()
                        + " interrumpida; permanece EN_REPARTO.");
                Thread.currentThread().interrupt();
                return;
            }
        }
    }
}
