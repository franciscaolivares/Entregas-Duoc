package model;


public class PaseoLacustre extends ServicioTuristico{

    private String tipoEmbarcacion;

    public PaseoLacustre(){
    }

    public PaseoLacustre(String nombre, int duracionHoras, String tipoEmbarcacion){
    }

    public String getTipoEmbarcacion() {
        return tipoEmbarcacion;
    }

    public void setTipoEmbarcacion(String tipoEmbarcacion) {
        this.tipoEmbarcacion = tipoEmbarcacion;
    }

    @Override
    public String toString(){
        return "Paseo Lacustre | " +
                super.toString() +
                " | Tipo de embarcación: " + tipoEmbarcacion;
    }

}
