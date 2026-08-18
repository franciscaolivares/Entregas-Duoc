package model;

public class PedidoExpress extends Pedido {

    public PedidoExpress() {
    }

    public PedidoExpress(String idPedido, String direccionEntrega) {
        super(idPedido, direccionEntrega, "Compra Express");
    }

    @Override
    public void asignarRepartidor() {
        System.out.println("Asignando repartidor para pedido express...");
        System.out.println("Buscando al repartidor más cercano con disponibilidad inmediata... OK");
    }

    @Override
    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println(nombreRepartidor + " es el repartidor disponible más cercano.");
        System.out.println("Pedido express asignado a " + nombreRepartidor + ".");
    }
}

