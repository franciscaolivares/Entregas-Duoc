package model;


public class PaseoLacustre extends ServicioTuristico{

    private String tipoEmbarcacion;

    public PaseoLacustre(){
    }

    public PaseoLacustre(String nombre, int duracionHoras, String tipoEmbarcacion){
        super(nombre, duracionHoras);
        this.tipoEmbarcacion = tipoEmbarcacion;
    }

    public String getTipoEmbarcacion() {

        return tipoEmbarcacion;
    }

    public void setTipoEmbarcacion(String tipoEmbarcacion) {

        this.tipoEmbarcacion = tipoEmbarcacion;
    }

    @Override
    public void mostrarInformacion() {
        System.out.println(
                "Paseo Lacustre | Nombre: " + getNombre() +
                        " | Duración: " + getDuracionHoras() + " horas" +
                        " | Tipo de embarcación: " + tipoEmbarcacion
        );
    }

    @Override
    public String toString(){
        return "Paseo Lacustre | " +
                super.toString() +
                " | Tipo de embarcación: " + tipoEmbarcacion;
    }

}
