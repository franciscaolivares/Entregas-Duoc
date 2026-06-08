package model;

/** Representa a un empleado de la agencia Llanquihue Tour. Esta clase hereda de Persona.*/

public class Empleado extends Persona {

    private String cargo;
    private double sueldo;

    /** Constructor de la clase Empleado.*/

    public Empleado(String nombre, String telefono, Rut rut, Direccion direccion, String cargo, double sueldo) {
        super(nombre, telefono, rut, direccion);
        this.cargo = cargo;
        this.sueldo = sueldo;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    @Override
    public String toString() {
        return super.toString() +
                "\nCargo: " + cargo +
                "\nSueldo: $" + sueldo;
    }
}
