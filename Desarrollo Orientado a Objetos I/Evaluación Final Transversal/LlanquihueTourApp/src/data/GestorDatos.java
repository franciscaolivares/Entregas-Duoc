package data;
import model.Guia;
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

                try {
                    String[] datos = linea.split(";");
                    String nombre = datos[0];
                    String tipo = datos[1];
                    int precio = Integer.parseInt(datos[2]);
                    String nombreGuia = datos[3];
                    String telefonoGuia = datos[4];

                    Guia guia = new Guia(nombreGuia, telefonoGuia);
                    Tour tour = new Tour(nombre, tipo, precio, guia);

                    listaTours.add(tour);
                }
                catch (Exception e) {
                    System.out.println("Error al procesar una línea del archivo: " + linea);
                }
            }
            lector.close();
        }

        catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
        return listaTours;
    }
}
