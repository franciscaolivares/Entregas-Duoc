package ui;
import data.GestorDatos;
import model.Tour;
import java.util.ArrayList;

public class Main {

  public static void main (String[] args) {

    GestorDatos gestor = new GestorDatos();

    ArrayList<Tour> tours = gestor.cargarTours("resources/tours.txt");

    System.out.println("Listado completo de tours: ");
    for (Tour tour : tours) {
      System.out.println(tour);
    }
    System.out.println("\n Tours gastronómicos:");
    for (Tour tour : tours)
      if (tour.getTipo().equalsIgnoreCase("gastronomico")) {
        System.out.println(tour);
      }
  }
}

