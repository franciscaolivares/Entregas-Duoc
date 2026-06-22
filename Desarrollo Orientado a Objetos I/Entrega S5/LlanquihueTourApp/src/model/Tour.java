package model;

public class Tour {

    private String nombre;
    private String tipo;
    private int precio;
    private Guia guia;

    public Tour(){

    }

    public Tour(String nombre, String tipo, int precio, Guia guia){
        this.nombre = nombre;
        this.tipo = tipo;
        this.precio = precio;
        this.guia = guia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public Guia getGuia() {
        return guia;
    }

    public void setGuia(Guia guia) {
        this.guia = guia;
    }

    @Override
    public String toString(){
        return "Tour: " + nombre +
                " | Tipo: " + tipo +
                " | Precio: $" + precio +
                " | Guía: " + guia;


    }
}
