package app;
import model.Direccion;
import model.Empleado;
import model.Rut;
import util.RutInvalidoException;

/**Clase principal para probar el modelo de Llanquihue Tour.*/

public class Main {

    public static void main(String[] args) {

        try {
            Direccion direccion1 = new Direccion("Av. Costanera 123", "Llanquihue", "Los Lagos");
            Rut rut1 = new Rut("12345678-K");
            Empleado empleado1 = new Empleado("Juan Perez", "912345678", rut1, direccion1, "Guia turistico", 750000);

            Direccion direccion2 = new Direccion("Calle Los Volcanes 456", "Puerto Varas", "Los Lagos");
            Rut rut2 = new Rut("98765432-1");
            Empleado empleado2 = new Empleado("Maria Soto", "923456789", rut2, direccion2, "Coordinadora de tours", 850000);

            Direccion direccion3 = new Direccion("Pasaje Lago Azul 789", "Frutillar", "Los Lagos");
            Rut rut3 = new Rut("11222333-4");
            Empleado empleado3 = new Empleado("Pedro Gonzalez", "934567890", rut3, direccion3, "Operador turistico", 700000);

            System.out.println("REGISTRO DE EMPLEADOS LLANQUIHUE TOUR ");
            System.out.println();
            System.out.println(empleado1);
            System.out.println("-----------------------------------------------");
            System.out.println(empleado2);
            System.out.println("-----------------------------------------------");
            System.out.println(empleado3);

        } catch (RutInvalidoException e) {
            System.out.println("Error al registrar empleado: " + e.getMessage());
        }
    }
}
