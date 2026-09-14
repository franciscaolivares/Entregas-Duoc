package ui;

import model.*;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        ZonaDeCarga zonaDeCarga = new ZonaDeCarga();
        ArrayList<Pedido> pedidos = new ArrayList<>();
        pedidos.add(new PedidoComida(1, "Av. Providencia 123", 4.5));
        pedidos.add(new PedidoExpress(2, "Av. Pajaritos 789", 6.2));
        pedidos.add(new PedidoEncomienda(3, "Gran Avenida 456", 8.0));
        pedidos.add(new PedidoComida(4, "Av. Vicuña Mackenna 850", 3.5));
        pedidos.add(new PedidoExpress(5, "Los Leones 320", 5.0));
        pedidos.add(new PedidoEncomienda(6, "Santa Rosa 1540", 7.3));

        System.out.println("=== SPEEDFAST - SEMANA 5 ===");
        for (Pedido pedido : pedidos) {
            zonaDeCarga.agregarPedido(pedido);
            System.out.println("Registrado: " + pedido);
        }

        ExecutorService ejecutor = Executors.newFixedThreadPool(3);
        ejecutor.execute(new Repartidor("Juan Pérez", zonaDeCarga));
        ejecutor.execute(new Repartidor("Camila Soto", zonaDeCarga));
        ejecutor.execute(new Repartidor("Luis Díaz", zonaDeCarga));
        ejecutor.shutdown();

        try {
            if (!ejecutor.awaitTermination(1, TimeUnit.MINUTES)) {
                ejecutor.shutdownNow();
                System.out.println("La simulación superó el tiempo de espera.");
                return;
            }
            boolean todosEntregados = true;
            System.out.println("\n=== RESUMEN FINAL ===");
            for (Pedido pedido : pedidos) {
                System.out.println(pedido);
                if (pedido.getEstado() != EstadoPedido.ENTREGADO) {
                    todosEntregados = false;
                }
            }
            if (todosEntregados) {
                System.out.println("Todos los pedidos han sido entregados correctamente");
            } else {
                System.out.println("Hay pedidos que no pudieron ser entregados.");
            }
        } catch (InterruptedException e) {
            ejecutor.shutdownNow();
            Thread.currentThread().interrupt();
            System.out.println("La simulación fue interrumpida.");
        }
    }
}
