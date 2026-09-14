package ui;

import model.PedidoComida;
import model.PedidoEncomienda;
import model.PedidoExpress;
import model.Repartidor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Repartidor repartidor1 = new Repartidor("Juan Pérez");
        Repartidor repartidor2 = new Repartidor("Camila Soto");
        Repartidor repartidor3 = new Repartidor("Luis Díaz");

        repartidor1.agregarPedido(new PedidoComida("C003", "Av. Providencia 123", 4.5));
        repartidor1.agregarPedido(new PedidoExpress("X003", "Av. Pajaritos 789", 6.2));

        repartidor2.agregarPedido(new PedidoEncomienda("E003", "Gran Avenida 456", 8.0));
        repartidor2.agregarPedido(new PedidoComida("C004", "Av. Vicuña Mackenna 850", 3.5));

        repartidor3.agregarPedido(new PedidoExpress("X004", "Los Leones 320", 5.0));
        repartidor3.agregarPedido(new PedidoEncomienda("E004", "Santa Rosa 1540", 7.3));

        ExecutorService ejecutor = Executors.newFixedThreadPool(3);

        System.out.println("=== SISTEMA DE PEDIDOS SPEEDFAST - SEMANA 4 ===");
        System.out.println("Iniciando entregas concurrentes...\n");

        ejecutor.execute(repartidor1);
        ejecutor.execute(repartidor2);
        ejecutor.execute(repartidor3);

        ejecutor.shutdown();

        try {
            if (ejecutor.awaitTermination(1, TimeUnit.MINUTES)) {
                System.out.println("\n=== TODOS LOS REPARTIDORES FINALIZARON ===");
            } else {
                System.out.println("La simulación superó el tiempo de espera.");
                ejecutor.shutdownNow();
            }
        } catch (InterruptedException e) {
            System.out.println("La simulación fue interrumpida.");
            ejecutor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("=== PROCESO FINALIZADO ===");
    }
}
