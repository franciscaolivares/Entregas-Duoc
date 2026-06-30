package model;

public class ExcursionCultural extends ServicioTuristico{

    private String lugarHistorico;

    public ExcursionCultural(){
    }

    public ExcursionCultural(String nombre, int duracionHoras, String lugarHistorico){
        super(nombre, duracionHoras);
        this.lugarHistorico = lugarHistorico;
    }

    public String getLugarHistorico() {
        return lugarHistorico;
    }

    public void setLugarHistorico(String lugarHistorico) {
        this.lugarHistorico = lugarHistorico;
    }

    @Override
    public String toString(){
        return "Excursión Cultural | " +
                super.toString() +
                " | Lugar histórico: " + lugarHistorico;
    }
}
