package model;

public class Reserva {

    private int numeroReserva;
    private Cliente cliente;
    private ServicioTuristico servicioTuristico;
    private Guia guia;
    private int cantidadPersonas;

    public Reserva() {
    }

    public Reserva(
            int numeroReserva,
            Cliente cliente,
            ServicioTuristico servicioTuristico,
            Guia guia,
            int cantidadPersonas
    ) {
        this.numeroReserva = numeroReserva;
        this.cliente = cliente;
        this.servicioTuristico = servicioTuristico;
        this.guia = guia;
        this.cantidadPersonas = cantidadPersonas;
    }

    public int getNumeroReserva() {
        return numeroReserva;
    }

    public void setNumeroReserva(int numeroReserva) {
        this.numeroReserva = numeroReserva;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public ServicioTuristico getServicioTuristico() {
        return servicioTuristico;
    }

    public void setServicioTuristico(
            ServicioTuristico servicioTuristico
    ) {
        this.servicioTuristico = servicioTuristico;
    }

    public Guia getGuia() {
        return guia;
    }

    public void setGuia(Guia guia) {
        this.guia = guia;
    }

    public int getCantidadPersonas() {
        return cantidadPersonas;
    }

    public void setCantidadPersonas(int cantidadPersonas) {
        this.cantidadPersonas = cantidadPersonas;
    }

    @Override
    public String toString() {
        return "Reserva N°: " + numeroReserva
                + "\nCliente: " + cliente.getNombre()
                + "\nServicio: " + servicioTuristico.getNombre()
                + "\nGuía: " + guia.getNombre()
                + "\nCantidad de personas: " + cantidadPersonas;
    }
}