package model;

public class Guia extends Persona {

    private String especialidad;

    public Guia() {
    }

    //Constructor antiguo
    public Guia(String nombre, String telefono) {
        super(nombre, telefono, null, null);
        this.especialidad = "No especificada";
    }

    public Guia(
            String nombre,
            String telefono,
            Rut rut,
            Direccion direccion,
            String especialidad
    ) {
        super(nombre, telefono, rut, direccion);
        this.especialidad = especialidad;
    }


    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    @Override
    public String mostrarResumen() {
        return "GUÍA | "
                + super.toString()
                + " | Especialidad: " + especialidad;
    }

    @Override
    public String toString() {
        return mostrarResumen();
    }
}