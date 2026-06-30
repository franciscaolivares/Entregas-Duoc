package ui;
import data.GestorDatos;
import data.GestorServicios;
import model.Tour;
import java.util.ArrayList;

public class Main {

  public static void main (String[] args) {

    /*Semana 6
    Herencia*/
    GestorServicios gestorServicios = new GestorServicios();

    System.out.println("\n Servicios Turísticos: ");
    gestorServicios.mostrarServicios();

    GestorDatos gestor = new GestorDatos();
    ArrayList<Tour> tours = gestor.cargarTours("resources/tours.txt");

    System.out.println("\n LISTA COMPLETA DE TOURS: ");
    for (Tour tour : tours) {
      System.out.println(tour);
    }
    System.out.println("\n Tours gastronómicos:");
    for (Tour tour : tours)
      if (tour.getTipo().equalsIgnoreCase("gastronomico")) {
        System.out.println(tour);
      }
    System.out.println ("\n Tours sobre $20.000");
    for(Tour tour : tours){
      if (tour.getPrecio() > 20000) {
        System.out.println(tour);
      }
    }
  }
}

