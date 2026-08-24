package model;

public class PedidoComida extends Pedido {
    public PedidoComida() {
    }

    public PedidoComida(String idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, "Comida", distanciaKm);
    }

    @Override
    public int calcularTiempoEntrega() {

        return (int) Math.round(15 + (2 * getDistanciaKm()));
    }

    @Override
    public void asignarRepartidor() {
        System.out.println("Asignando repartidor para pedido de comida...");
        System.out.println("Verificando que cuente con mochila térmica... OK");
    }

    @Override
    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("Mochila térmica de " + nombreRepartidor + " verificada.");
        System.out.println("Pedido de comida asignado a " + nombreRepartidor + ".");
    }
}
