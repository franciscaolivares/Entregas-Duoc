package model;

public class Guia implements Registrable {

    private String nombre;
    private String telefono;

    public Guia() {
    }

    public Guia(String nombre, String telefono) {
        this.nombre = nombre;
        this.telefono = telefono;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String mostrarResumen() {
        return "Guía turístico | Nombre: " + nombre
                + " | Teléfono: " + telefono;
    }

    @Override
    public String toString() {
        return mostrarResumen();
    }
}