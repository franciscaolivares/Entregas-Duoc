package model;

import java.util.ArrayList;

public abstract class Pedido {
    private String idPedido;
    private String direccionEntrega;
    private String tipoPedido;
    private double distanciaKm;
    private boolean reservado;
    private boolean despachado;
    private boolean cancelado;
    private final ArrayList<String> historial = new ArrayList<>();

    public Pedido() {
    }

    public Pedido(String idPedido, String direccionEntrega, String tipoPedido, double distanciaKm) {
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.tipoPedido = tipoPedido;
        this.distanciaKm = distanciaKm;
    }

    public String getIdPedido() {

        return idPedido;
    }

    public void setIdPedido(String idPedido) {

        this.idPedido = idPedido;
    }

    public String getDireccionEntrega() {

        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {

        this.direccionEntrega = direccionEntrega;
    }

    public String getTipoPedido() {

        return tipoPedido;
    }

    public void setTipoPedido(String tipoPedido) {

        this.tipoPedido = tipoPedido;
    }

    public double getDistanciaKm() {

        return distanciaKm;
    }

    public void setDistanciaKm(double distanciaKm) {

        this.distanciaKm = distanciaKm;
    }

    public void mostrarResumen() {
        System.out.println("\n[Pedido " + tipoPedido + "]");
        System.out.println("ID: " + idPedido);
        System.out.println("Dirección: " + direccionEntrega);
        System.out.println("Distancia: " + distanciaKm + " km");
        System.out.println("Estado: " + obtenerEstado());
    }

    public abstract int calcularTiempoEntrega();

    public void asignarRepartidor() {
        System.out.println("Asignando un repartidor al pedido...");
    }

    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("Pedido asignado a " + nombreRepartidor + ".");
    }

    // Registra la reserva antes de permitir el despacho del pedido.
    public void reservar() {
        if (reservado) {
            System.out.println("El pedido " + idPedido + " ya se encuentra reservado.");
            return;
        }

        reservado = true;
        historial.add("Pedido reservado.");
        System.out.println("Pedido " + idPedido + " reservado correctamente.");
    }

    public void despachar() {
        if (cancelado) {
            System.out.println("El pedido " + idPedido + " está cancelado y no puede despacharse.");
            return;
        }

        if (!reservado) {
            System.out.println("El pedido " + idPedido + " debe reservarse antes de ser despachado.");
            return;
        }

        despachado = true;
        historial.add("Pedido despachado hacia " + direccionEntrega + ".");
        System.out.println("Pedido " + idPedido + " despachado correctamente.");
    }

    public void cancelar() {
        if (despachado) {
            System.out.println("El pedido " + idPedido + " ya fue despachado y no puede cancelarse.");
            return;
        }

        cancelado = true;
        historial.add("Pedido cancelado.");
        System.out.println("Pedido " + idPedido + " cancelado correctamente.");
    }

    // Muestra cada cambio de estado guardado durante la simulación.
    public void verHistorial() {
        System.out.println("Historial del pedido " + idPedido + ":");

        if (historial.isEmpty()) {
            System.out.println("- No existen movimientos registrados.");
            return;
        }

        for (String movimiento : historial) {
            System.out.println("- " + movimiento);
        }
    }

    public String obtenerEstado() {
        if (cancelado) {
            return "Cancelado";
        }

        if (despachado) {
            return "Despachado";
        }

        if (reservado) {
            return "Reservado";
        }

        return "Creado";
    }
}
