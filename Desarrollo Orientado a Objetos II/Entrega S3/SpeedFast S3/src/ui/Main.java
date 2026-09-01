package ui;

import interfaces.Cancelable;
import interfaces.Despachable;
import interfaces.Rastreable;
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

        System.out.println("=== SISTEMA DE PEDIDOS SPEEDFAST - SEMANA 3 ===");

        for (int i = 0; i < pedidos.length; i++) {
            Pedido pedido = pedidos[i];

            pedido.mostrarResumen();
            pedido.reservar();
            pedido.asignarRepartidor();
            pedido.asignarRepartidor(repartidores[i]);
            System.out.println("Tiempo estimado de entrega: "
                    + pedido.calcularTiempoEntrega() + " minutos");

            // Se cancela una encomienda para demostrar un caso diferente.
            if (pedido instanceof PedidoEncomienda) {
                Cancelable pedidoCancelable = (Cancelable) pedido;
                pedidoCancelable.cancelar();
            } else {
                Despachable pedidoDespachable = (Despachable) pedido;
                pedidoDespachable.despachar();
            }

            Rastreable pedidoRastreable = (Rastreable) pedido;
            pedidoRastreable.verHistorial();
            System.out.println("Estado final: " + pedido.obtenerEstado());
        }

        System.out.println("\n=== RESUMEN FINAL DE PEDIDOS ===");

        for (Pedido pedido : pedidos) {
            System.out.println(pedido.getIdPedido() + " - "
                    + pedido.getTipoPedido() + " - "
                    + pedido.calcularTiempoEntrega() + " minutos - "
                    + pedido.obtenerEstado());
        }

        System.out.println("\n=== PROCESO FINALIZADO ===");
    }
}
