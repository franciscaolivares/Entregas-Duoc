package model;

import java.util.ArrayList;
import java.util.Random;

public class Repartidor implements Runnable {
    private String nombre;
    private ArrayList<Pedido> pedidosAsignados;
    private Random random;

    public Repartidor(String nombre) {
        this.nombre = nombre;
        this.pedidosAsignados = new ArrayList<>();
        this.random = new Random();
    }

    public String getNombre() {
        return nombre;
    }

    public void agregarPedido(Pedido pedido) {
        pedidosAsignados.add(pedido);
    }

    @Override
    public void run() {
        System.out.println(nombre + " comenzó su jornada con "
                + pedidosAsignados.size() + " pedidos.");

        for (Pedido pedido : pedidosAsignados) {
            System.out.println("\n[" + nombre + "] Preparando pedido "
                    + pedido.getIdPedido() + " hacia " + pedido.getDireccionEntrega());

            pedido.reservar();
            pedido.asignarRepartidor(nombre);

            try {
                int pausa = 1000 + random.nextInt(2001);
                System.out.println("[" + nombre + "] Pedido " + pedido.getIdPedido()
                        + " en camino...");
                Thread.sleep(pausa);

                pedido.despachar();
                System.out.println("[" + nombre + "] Entrega finalizada. Tiempo estimado: "
                        + pedido.calcularTiempoEntrega() + " minutos.");
            } catch (InterruptedException e) {
                System.out.println("[" + nombre + "] La entrega fue interrumpida.");
                Thread.currentThread().interrupt();
                return;
            }
        }

        System.out.println("\n" + nombre + " terminó todas sus entregas.");
    }
}
