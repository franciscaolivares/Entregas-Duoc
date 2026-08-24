package model;

public class PedidoExpress extends Pedido {
    public PedidoExpress() {
    }

    public PedidoExpress(String idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, "Compra Express", distanciaKm);
    }

    @Override
    public int calcularTiempoEntrega() {
        int tiempoBase = 10;

        if (getDistanciaKm() > 5) {
            tiempoBase += 5;
        }

        return tiempoBase;
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
