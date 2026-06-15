package data;
import model.Tour;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class GestorDatos {

    public ArrayList<Tour> cargarTours(String rutaArchivo) {
        ArrayList<Tour> listaTours = new ArrayList<>();

        try {
            BufferedReader lector = new BufferedReader(new FileReader(rutaArchivo));
            String linea;

            while ((linea = lector.readLine()) != null) {
                String[] datos = linea.split(";");

                String nombre = datos[0];
                String tipo = datos[1];
                int precio = Integer.parseInt(datos[2]);

                Tour tour = new Tour(nombre, tipo, precio);
                listaTours.add(tour);
            }

            lector.close();

        }
        catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return listaTours;
    }
}
