package model;

public class ColaboradorExterno implements Registrable {

    private String nombre;
    private String especialidad;

    public ColaboradorExterno() {
    }

    public ColaboradorExterno(String nombre, String especialidad) {
        this.nombre = nombre;
        this.especialidad = especialidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    @Override
    public String mostrarResumen() {
        return "Colaborador externo | Nombre: " + nombre
                + " | Especialidad: " + especialidad;
    }

    @Override
    public String toString() {
        return mostrarResumen();
    }
}