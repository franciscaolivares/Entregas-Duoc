package data;

import model.ColaboradorExterno;
import model.Guia;
import interfaces.Registrable;
import model.Vehiculo;
import model.Cliente;

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

            else if (entidad instanceof Cliente) {
                resultado += "[Cliente]\n";
                resultado += entidad.mostrarResumen() + "\n\n";
            }
        }

        return resultado;
    }
    public ArrayList<Cliente> obtenerClientes() {

        ArrayList<Cliente> clientes = new ArrayList<>();

        for (Registrable entidad : entidades) {

            if (entidad instanceof Cliente) {
                clientes.add((Cliente) entidad);
            }
        }

        return clientes;
    }

    public ArrayList<Guia> obtenerGuias() {

        ArrayList<Guia> guias = new ArrayList<>();

        for (Registrable entidad : entidades) {

            if (entidad instanceof Guia) {
                guias.add((Guia) entidad);
            }
        }

        return guias;
    }
}
