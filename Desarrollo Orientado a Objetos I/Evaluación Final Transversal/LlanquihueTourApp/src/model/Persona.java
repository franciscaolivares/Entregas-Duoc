package model;

import interfaces.Registrable;

public abstract class Persona implements Registrable {

    private String nombre;
    private String telefono;
    private Rut rut;
    private Direccion direccion;

    public Persona() {
    }

    public Persona(
            String nombre,
            String telefono,
            Rut rut,
            Direccion direccion
    ) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.rut = rut;
        this.direccion = direccion;
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

    public Rut getRut() {
        return rut;
    }

    public void setRut(Rut rut) {
        this.rut = rut;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {

        if (rut == null || direccion == null) {
            return "Nombre: " + nombre
                    + " | Teléfono: " + telefono;
        }

        return "Nombre: " + nombre
                + " | Teléfono: " + telefono
                + " | RUT: " + rut
                + " | Dirección: " + direccion;
    }

}
