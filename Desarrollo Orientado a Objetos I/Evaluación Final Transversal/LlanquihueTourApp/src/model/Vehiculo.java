package model;

import interfaces.Registrable;

public class Vehiculo implements Registrable {

    private String patente;
    private String tipoVehiculo;

    public Vehiculo() {
    }

    public Vehiculo(String patente, String tipoVehiculo) {
        this.patente = patente;
        this.tipoVehiculo = tipoVehiculo;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public String getTipoVehiculo() {
        return tipoVehiculo;
    }

    public void setTipoVehiculo(String tipoVehiculo) {
        this.tipoVehiculo = tipoVehiculo;
    }

    @Override
    public String mostrarResumen() {
        return "Vehículo | Patente: " + patente
                + " | Tipo: " + tipoVehiculo;
    }

    @Override
    public String toString() {
        return mostrarResumen();
    }
}