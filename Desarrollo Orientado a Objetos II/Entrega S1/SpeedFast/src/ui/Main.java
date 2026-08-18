package ui;

import model.Pedido;
import model.PedidoComida;
import model.PedidoEncomienda;
import model.PedidoExpress;

public class Main {

    public static void main(String[] args) {

        Pedido[] pedidos = {
                new PedidoComida("C001", "Av. Providencia 123"),
                new PedidoEncomienda("E001", "Gran Avenida 456"),
                new PedidoExpress("X001", "Av. Pajaritos 789")
        };

        String[] repartidores = {
                "Juan Pérez",
                "Camila Soto",
                "Luis Díaz"
        };

        System.out.println("=== SISTEMA DE ASIGNACIÓN SPEEDFAST ===");

        for (int i = 0; i < pedidos.length; i++) {

            Pedido pedido = pedidos[i];

            System.out.println("\n[Pedido " + pedido.getTipoPedido() + "]");
            System.out.println("ID: " + pedido.getIdPedido());
            System.out.println("Dirección: " + pedido.getDireccionEntrega());

            pedido.asignarRepartidor();
            pedido.asignarRepartidor(repartidores[i]);
        }

        System.out.println("\n=== ASIGNACIÓN FINALIZADA ===");
    }
}

