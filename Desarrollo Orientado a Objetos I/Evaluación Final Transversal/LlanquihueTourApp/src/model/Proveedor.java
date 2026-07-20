package model;

public class Proveedor extends Persona{

    private String tipoServicio;

    public Proveedor(){
    }

    public Proveedor(
            String nombre,
            String telefono,
            Rut rut,
            Direccion direccion,
            String tipoServicio
    ){
        super(nombre, telefono, rut, direccion);
        this.tipoServicio = tipoServicio;
    }

    public String getTipoServicio() {
        return tipoServicio;
    }

    public void setTipoServicio(String tipoServicio) {
        this.tipoServicio = tipoServicio;
    }

    @Override
    public String mostrarResumen() {
        return "PROVEEDOR | "
                + super.toString()
                + " | Tipo de servicio: " + tipoServicio;
    }

    @Override
    public String toString(){
        return mostrarResumen();
    }
}
