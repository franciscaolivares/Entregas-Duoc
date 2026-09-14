package model;

import interfaces.Cancelable;
import interfaces.Despachable;
import interfaces.Rastreable;

public class PedidoEncomienda extends Pedido implements Despachable, Cancelable, Rastreable {
    public PedidoEncomienda() {
    }

    public PedidoEncomienda(int idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, "Encomienda", distanciaKm);
    }

    @Override
    public int calcularTiempoEntrega() {

        return (int) Math.round(20 + (1.5 * getDistanciaKm()));
    }

    @Override
    public void asignarRepartidor() {
        System.out.println("Asignando repartidor para pedido de encomienda...");
        System.out.println("Verificando capacidad para transportar el paquete... OK");
    }

    @Override
    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("Capacidad de transporte de " + nombreRepartidor + " verificada.");
        System.out.println("Pedido de encomienda asignado a " + nombreRepartidor + ".");
    }
}
