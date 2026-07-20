package model;

public class Cliente extends Persona {

    private String correo;

    public Cliente() {
    }

    public Cliente(
            String nombre,
            String telefono,
            Rut rut,
            Direccion direccion,
            String correo
    ) {
        super(nombre, telefono, rut, direccion);
        this.correo = correo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String mostrarResumen() {
        return "CLIENTE | "
                + super.toString()
                + " | Correo: " + correo;
    }

    @Override
    public String toString() {
        return mostrarResumen();
    }
}
