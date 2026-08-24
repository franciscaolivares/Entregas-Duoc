package ui;

import model.Pedido;
import model.PedidoComida;
import model.PedidoEncomienda;
import model.PedidoExpress;

public class Main {
    public static void main(String[] args) {
        Pedido[] pedidos = {
                new PedidoComida("C002", "Av. Providencia 123", 4.5),
                new PedidoEncomienda("E002", "Gran Avenida 456", 8.0),
                new PedidoExpress("X002", "Av. Pajaritos 789", 6.2)
        };

        String[] repartidores = {
                "Juan Pérez",
                "Camila Soto",
                "Luis Díaz"
        };

        System.out.println("=== SISTEMA DE PEDIDOS SPEEDFAST - SEMANA 2 ===");

        for (int i = 0; i < pedidos.length; i++) {
            Pedido pedido = pedidos[i];

            pedido.mostrarResumen();
            pedido.asignarRepartidor();
            pedido.asignarRepartidor(repartidores[i]);
            System.out.println("Tiempo estimado de entrega: "
                    + pedido.calcularTiempoEntrega() + " minutos");
        }

        System.out.println("\n=== RESUMEN COMPARATIVO ===");

        for (Pedido pedido : pedidos) {
            System.out.println(pedido.getTipoPedido() + ": "
                    + pedido.calcularTiempoEntrega() + " minutos");
        }

        System.out.println("\n=== PROCESO FINALIZADO ===");
    }
}
