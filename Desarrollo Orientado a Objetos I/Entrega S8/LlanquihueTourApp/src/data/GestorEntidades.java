package data;

import model.ColaboradorExterno;
import model.Guia;
import model.Registrable;
import model.Vehiculo;

import java.util.ArrayList;

public class GestorEntidades {

    private ArrayList<Registrable> entidades;

    public GestorEntidades() {
        entidades = new ArrayList<>();
    }

    public void agregarEntidad(Registrable entidad) {
        entidades.add(entidad);
    }

    public ArrayList<Registrable> getEntidades() {
        return entidades;
    }

    public String obtenerResumenEntidades() {

        if (entidades.isEmpty()) {
            return "No existen entidades registradas.";
        }

        String resultado = "Entidades registradas\n\n";

        for (Registrable entidad : entidades) {

            if (entidad instanceof Guia) {
                resultado += "[Guía]\n";
                resultado += entidad.mostrarResumen() + "\n\n";

            } else if (entidad instanceof Vehiculo) {
                resultado += "[Vehículo]\n";
                resultado += entidad.mostrarResumen() + "\n\n";

            } else if (entidad instanceof ColaboradorExterno) {
                resultado += "[Colaborador externo]\n";
                resultado += entidad.mostrarResumen() + "\n\n";
            }
        }

        return resultado;
    }
}
