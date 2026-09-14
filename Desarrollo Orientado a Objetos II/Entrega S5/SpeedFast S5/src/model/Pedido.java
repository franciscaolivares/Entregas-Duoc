package model;

import java.util.ArrayList;

public abstract class Pedido {
    private int id;
    private EstadoPedido estado = EstadoPedido.PENDIENTE;
    private String direccionEntrega;
    private String tipoPedido;
    private double distanciaKm;
    private boolean reservado;
    private boolean despachado;
    private boolean cancelado;
    private final ArrayList<String> historial = new ArrayList<>();

    public Pedido() {
    }

    public Pedido(int id, String direccionEntrega, String tipoPedido, double distanciaKm) {
        this.id = id;
        this.direccionEntrega = direccionEntrega;
        this.tipoPedido = tipoPedido;
        this.distanciaKm = distanciaKm;
    }

    public int getIdPedido() {

        return id;
    }

    public void setIdPedido(int id) {

        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public synchronized EstadoPedido getEstado() {
        return estado;
    }

    public synchronized void setEstado(EstadoPedido nuevoEstado) {
        if (nuevoEstado == null) {
            throw new IllegalArgumentException("El estado no puede ser nulo.");
        }
        estado = nuevoEstado;
        historial.add("Estado actualizado a " + nuevoEstado + ".");
    }

    public void setEstado(String nuevoEstado) {
        setEstado(EstadoPedido.valueOf(nuevoEstado));
    }

    @Override
    public String toString() {

        return "Pedido " + id + " | " + direccionEntrega + " | " + getEstado();
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
        System.out.println("ID: " + id);
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
            System.out.println("El pedido " + id + " ya se encuentra reservado.");
            return;
        }

        reservado = true;
        historial.add("Pedido reservado.");
        System.out.println("Pedido " + id + " reservado correctamente.");
    }

    public void despachar() {
        if (cancelado) {
            System.out.println("El pedido " + id + " está cancelado y no puede despacharse.");
            return;
        }

        if (!reservado) {
            System.out.println("El pedido " + id + " debe reservarse antes de ser despachado.");
            return;
        }

        despachado = true;
        setEstado(EstadoPedido.EN_REPARTO);
        historial.add("Pedido despachado hacia " + direccionEntrega + ".");
        System.out.println("Pedido " + id + " despachado correctamente.");
    }

    public void cancelar() {
        if (despachado) {
            System.out.println("El pedido " + id + " ya fue despachado y no puede cancelarse.");
            return;
        }

        cancelado = true;
        historial.add("Pedido cancelado.");
        System.out.println("Pedido " + id + " cancelado correctamente.");
    }

    // Muestra cada cambio de estado guardado durante la simulación.
    public void verHistorial() {
        System.out.println("Historial del pedido " + id + ":");

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

        return getEstado().toString();
    }
}
