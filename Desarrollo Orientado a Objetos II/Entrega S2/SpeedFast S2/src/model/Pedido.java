package model;

public abstract class Pedido {
    private String idPedido;
    private String direccionEntrega;
    private String tipoPedido;
    private double distanciaKm;

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
    }

    public abstract int calcularTiempoEntrega();

    public void asignarRepartidor() {

        System.out.println("Asignando un repartidor al pedido...");
    }

    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("Pedido asignado a " + nombreRepartidor + ".");
    }
}
