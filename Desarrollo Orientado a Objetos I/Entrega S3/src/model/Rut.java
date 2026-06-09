package model;

import util.RutInvalidoException;

/** Representa un RUT chileno y válida que tenga el formato correcto.*/

public class Rut {

    private String numero;

    /** Constructor que recibe un RUT y válida su formato.
     * El formato esperado es XXXXXXXX-Y.
     * throws RutInvalidoException si el formato del RUT no es válido. */

    public Rut(String numeroRut) throws RutInvalidoException {
        if (!numeroRut.matches("[0-9]+-[0-9kK]")) {
            throw new RutInvalidoException("Formato de RUT no valido. Debe ser como 12345678-K.");
        }

        this.numero = numeroRut.toUpperCase();
    }

    /** Retorna el número de RUT almacenado.*/

    public String getNumero() {
        return numero;
    }

    /**Permite modificar el RUT validando primero su formato.
     * numeroRut nuevo rut ingresado
     * throws RutInvalidoException si el formato del rut no es váalido.*/

    public void setNumero(String numeroRut) throws RutInvalidoException {
        if (!numeroRut.matches("[0-9]+-[0-9kK]")) {
            throw new RutInvalidoException("Formato de RUT no válido. Debe ser como 12345678-K.");
        }

        this.numero = numeroRut.toUpperCase();
    }

    @Override
    public String toString() {

        return "RUT: " + numero;
    }
}
