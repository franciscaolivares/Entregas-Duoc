package model;

public class PedidoEncomienda extends Pedido {

    public PedidoEncomienda() {
    }

    public PedidoEncomienda(String idPedido, String direccionEntrega) {
        super(idPedido, direccionEntrega, "Encomienda");
    }

    @Override
    public void asignarRepartidor() {
        System.out.println("Asignando repartidor para pedido de encomienda...");
        System.out.println("Validando peso y embalaje... OK");
    }

    @Override
    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("Peso y embalaje validados para el traslado.");
        System.out.println("Pedido de encomienda asignado a " + nombreRepartidor + ".");
    }
}

