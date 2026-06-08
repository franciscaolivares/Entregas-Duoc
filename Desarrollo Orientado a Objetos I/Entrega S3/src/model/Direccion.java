package model;

/** Representa la direcciín de una persona vinculada a la agencia.*/

public class Direccion {

    private String calle;
    private String comuna;
    private String region;

    /** Constructor de la clase Dirección.*/

    public Direccion(String calle, String comuna, String region) {
        this.calle = calle;
        this.comuna = comuna;
        this.region = region;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getComuna() {
        return comuna;
    }

    public void setComuna(String comuna) {
        this.comuna = comuna;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Override
    public String toString() {
        return calle + ", " + comuna + ", " + region;
    }
}
