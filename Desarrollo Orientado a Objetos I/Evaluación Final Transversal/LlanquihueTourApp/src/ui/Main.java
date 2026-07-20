package ui;

import data.GestorEntidades;
import data.GestorDatos;
import data.GestorReservas;
import data.GestorServicios;
import model.*;
import utils.RutInvalidoException;

import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;

public class Main {

  public static void main(String[] args) {

    GestorEntidades gestorEntidades = new GestorEntidades();
    GestorDatos gestorDatos = new GestorDatos();
    GestorServicios gestorServicios = new GestorServicios();
    GestorReservas gestorReservas = new GestorReservas();

    ArrayList<Tour> tours = gestorDatos.cargarTours(
            "resources/tours.txt"
    );

    List<ServicioTuristico> servicios =
            gestorServicios.crearServicios();

    int opcion = -1;

    do {

      String menu = """
             
                       AGENCIA LLANQUIHUE TOUR
                 

                    Seleccione una opción:

                    1. Registrar guía turístico
                    2. Registrar vehículo
                    3. Registrar colaborador externo
                    4. Mostrar entidades registradas
                    5. Mostrar tours cargados desde archivo
                    6. Mostrar servicios turísticos
                    7. Registrar cliente
                    8. Registrar reserva
                    9. Mostrar reservas
                    10. Buscar reserva
                    11. Filtrar reservas por cantidad de personas
                    0. Salir

                    Ingrese una opción:
                    """;

      String entrada = JOptionPane.showInputDialog(
              null,
              menu,
              "Sistema Llanquihue Tour",
              JOptionPane.QUESTION_MESSAGE
      );

      if (entrada == null) {
        opcion = 0;
      } else {

        try {

          opcion = Integer.parseInt(entrada);

          switch (opcion) {

            case 1:
              registrarGuia(gestorEntidades);
              break;

            case 2:
              registrarVehiculo(gestorEntidades);
              break;

            case 3:
              registrarColaborador(gestorEntidades);
              break;

            case 4:
              mostrarEntidades(gestorEntidades);
              break;
            case 5:
              mostrarTours(tours);
              break;

            case 6:
              mostrarServicios(servicios);
              break;

            case 7:
              registrarCliente(gestorEntidades);
              break;

            case 8:
              registrarReserva(
                      gestorEntidades,
                      gestorReservas,
                      servicios
              );
              break;

            case 9:
              mostrarReservas(gestorReservas);
              break;

            case 10:
              buscarReserva(gestorReservas);
              break;

            case 11:
              filtrarReservas(gestorReservas);
              break;

            case 0:
              JOptionPane.showMessageDialog(
                      null,
                      "Gracias por utilizar el sistema Llanquihue Tour."
              );
              break;

            default:
              JOptionPane.showMessageDialog(
                      null,
                      "Debe seleccionar una opción válida."
              );
          }

        } catch (NumberFormatException e) {

          JOptionPane.showMessageDialog(
                  null,
                  "Debe ingresar solamente números."
          );

          opcion = -1;
        }
      }

    } while (opcion != 0);

  }

  //Métodos

  private static void buscarReserva(
          GestorReservas gestorReservas
  ) {

    String numeroIngresado =
            JOptionPane.showInputDialog(
                    "Ingrese el número de la reserva que desea buscar:"
            );

    if (numeroIngresado == null) {
      return;
    }

    try {

      int numeroReserva =
              Integer.parseInt(numeroIngresado);

      Reserva reserva =
              gestorReservas.buscarReserva(numeroReserva);

      if (reserva == null) {

        JOptionPane.showMessageDialog(
                null,
                "No se encontró una reserva con ese número."
        );

      } else {

        JOptionPane.showMessageDialog(
                null,
                "RESERVA ENCONTRADA\n\n"
                        + reserva.toString()
        );
      }

    } catch (NumberFormatException e) {

      JOptionPane.showMessageDialog(
              null,
              "Debe ingresar un número válido."
      );
    }
  }

  private static void filtrarReservas(
          GestorReservas gestorReservas
  ) {

    String cantidadIngresada =
            JOptionPane.showInputDialog(
                    "Mostrar reservas con al menos cuántas personas:"
            );

    if (cantidadIngresada == null) {
      return;
    }

    try {

      int cantidad =
              Integer.parseInt(cantidadIngresada);

      ArrayList<Reserva> reservasFiltradas =
              gestorReservas.filtrarReservasPorCantidad(
                      cantidad
              );

      if (reservasFiltradas.isEmpty()) {

        JOptionPane.showMessageDialog(
                null,
                "No existen reservas que cumplan con ese filtro."
        );

        return;
      }

      String mensaje =
              "RESERVAS CON "
                      + cantidad
                      + " O MÁS PERSONAS\n\n";

      for (Reserva reserva : reservasFiltradas) {

        mensaje = mensaje
                + reserva.toString()
                + "\n\n--------------------\n\n";
      }

      JOptionPane.showMessageDialog(
              null,
              mensaje,
              "Reservas filtradas",
              JOptionPane.INFORMATION_MESSAGE
      );

    } catch (NumberFormatException e) {

      JOptionPane.showMessageDialog(
              null,
              "Debe ingresar solamente números."
      );
    }
  }

  private static void mostrarReservas(
          GestorReservas gestorReservas
  ) {

    ArrayList<Reserva> reservas =
            gestorReservas.getReservas();

    if (reservas.isEmpty()) {

      JOptionPane.showMessageDialog(
              null,
              "No existen reservas registradas."
      );

      return;
    }

    String mensaje = "RESERVAS REGISTRADAS\n\n";

    for (Reserva reserva : reservas) {

      mensaje = mensaje
              + reserva.toString()
              + "\n\n--------------------\n\n";
    }

    JOptionPane.showMessageDialog(
            null,
            mensaje,
            "Reservas registradas",
            JOptionPane.INFORMATION_MESSAGE
    );
  }

  private static void registrarReserva(
          GestorEntidades gestorEntidades,
          GestorReservas gestorReservas,
          List<ServicioTuristico> servicios
  ) {

    ArrayList<Cliente> clientes =
            gestorEntidades.obtenerClientes();

    ArrayList<Guia> guias =
            gestorEntidades.obtenerGuias();

    if (clientes.isEmpty()) {

      JOptionPane.showMessageDialog(
              null,
              "Primero debe registrar al menos un cliente."
      );

      return;
    }

    if (guias.isEmpty()) {

      JOptionPane.showMessageDialog(
              null,
              "Primero debe registrar al menos un guía."
      );

      return;
    }

    if (servicios.isEmpty()) {

      JOptionPane.showMessageDialog(
              null,
              "No existen servicios turísticos disponibles."
      );

      return;
    }

    try {

      String numeroIngresado =
              JOptionPane.showInputDialog(
                      "Ingrese el número de la reserva:"
              );

      if (numeroIngresado == null) {
        return;
      }

      int numeroReserva =
              Integer.parseInt(numeroIngresado);

      if (gestorReservas.buscarReserva(numeroReserva) != null) {

        JOptionPane.showMessageDialog(
                null,
                "Ya existe una reserva con ese número."
        );

        return;
      }

      // Mostrar clientes
      String listaClientes = "CLIENTES DISPONIBLES\n\n";

      for (int i = 0; i < clientes.size(); i++) {

        listaClientes = listaClientes
                + (i + 1)
                + ". "
                + clientes.get(i).getNombre()
                + "\n";
      }

      String clienteIngresado =
              JOptionPane.showInputDialog(
                      listaClientes
                              + "\nSeleccione un cliente:"
              );

      if (clienteIngresado == null) {
        return;
      }

      int opcionCliente =
              Integer.parseInt(clienteIngresado);

      if (opcionCliente < 1
              || opcionCliente > clientes.size()) {

        JOptionPane.showMessageDialog(
                null,
                "Cliente no válido."
        );

        return;
      }

      Cliente cliente =
              clientes.get(opcionCliente - 1);

      // Mostrar servicios
      String listaServicios =
              "SERVICIOS TURÍSTICOS\n\n";

      for (int i = 0; i < servicios.size(); i++) {

        listaServicios = listaServicios
                + (i + 1)
                + ". "
                + servicios.get(i).getNombre()
                + "\n";
      }

      String servicioIngresado =
              JOptionPane.showInputDialog(
                      listaServicios
                              + "\nSeleccione un servicio:"
              );

      if (servicioIngresado == null) {
        return;
      }

      int opcionServicio =
              Integer.parseInt(servicioIngresado);

      if (opcionServicio < 1
              || opcionServicio > servicios.size()) {

        JOptionPane.showMessageDialog(
                null,
                "Servicio no válido."
        );

        return;
      }

      ServicioTuristico servicio =
              servicios.get(opcionServicio - 1);

      // Mostrar guías
      String listaGuias =
              "GUÍAS DISPONIBLES\n\n";

      for (int i = 0; i < guias.size(); i++) {

        listaGuias = listaGuias
                + (i + 1)
                + ". "
                + guias.get(i).getNombre()
                + "\n";
      }

      String guiaIngresada =
              JOptionPane.showInputDialog(
                      listaGuias
                              + "\nSeleccione un guía:"
              );

      if (guiaIngresada == null) {
        return;
      }

      int opcionGuia =
              Integer.parseInt(guiaIngresada);

      if (opcionGuia < 1
              || opcionGuia > guias.size()) {

        JOptionPane.showMessageDialog(
                null,
                "Guía no válido."
        );

        return;
      }

      Guia guia =
              guias.get(opcionGuia - 1);

      String cantidadIngresada =
              JOptionPane.showInputDialog(
                      "Ingrese la cantidad de personas:"
              );

      if (cantidadIngresada == null) {
        return;
      }

      int cantidadPersonas =
              Integer.parseInt(cantidadIngresada);

      if (cantidadPersonas <= 0) {

        JOptionPane.showMessageDialog(
                null,
                "La cantidad de personas debe ser mayor a 0."
        );

        return;
      }

      Reserva reserva = new Reserva(
              numeroReserva,
              cliente,
              servicio,
              guia,
              cantidadPersonas
      );

      gestorReservas.agregarReserva(reserva);

      JOptionPane.showMessageDialog(
              null,
              "Reserva registrada correctamente.\n\n"
                      + reserva.toString()
      );

    } catch (NumberFormatException e) {

      JOptionPane.showMessageDialog(
              null,
              "Debe ingresar solamente números en las opciones solicitadas."
      );
    }
  }

  private static void registrarCliente(
          GestorEntidades gestorEntidades
  ) {

    String nombre = JOptionPane.showInputDialog(
            "Ingrese el nombre del cliente:"
    );

    if (nombre == null) {
      return;
    }

    String telefono = JOptionPane.showInputDialog(
            "Ingrese el teléfono del cliente:"
    );

    if (telefono == null) {
      return;
    }

    String rutIngresado = JOptionPane.showInputDialog(
            "Ingrese el RUT sin puntos:\nEjemplo: 12345678-9"
    );

    if (rutIngresado == null) {
      return;
    }

    String calle = JOptionPane.showInputDialog(
            "Ingrese la calle:"
    );

    if (calle == null) {
      return;
    }

    String numeroIngresado = JOptionPane.showInputDialog(
            "Ingrese el número de la dirección:"
    );

    if (numeroIngresado == null) {
      return;
    }

    String comuna = JOptionPane.showInputDialog(
            "Ingrese la comuna:"
    );

    if (comuna == null) {
      return;
    }

    String correo = JOptionPane.showInputDialog(
            "Ingrese el correo del cliente:"
    );

    if (correo == null) {
      return;
    }

    if (nombre.isBlank()
            || telefono.isBlank()
            || rutIngresado.isBlank()
            || calle.isBlank()
            || numeroIngresado.isBlank()
            || comuna.isBlank()
            || correo.isBlank()) {

      JOptionPane.showMessageDialog(
              null,
              "Debe completar todos los campos."
      );

      return;
    }

    try {

      int numero = Integer.parseInt(numeroIngresado);
//Validación y excepción
      Rut rut = new Rut(rutIngresado);

      Direccion direccion = new Direccion(
              calle,
              numero,
              comuna
      );

      Cliente cliente = new Cliente(
              nombre,
              telefono,
              rut,
              direccion,
              correo
      );

      gestorEntidades.agregarEntidad(cliente);

      JOptionPane.showMessageDialog(
              null,
              "Cliente registrado correctamente.\n\n"
                      + cliente.mostrarResumen()
      );

    } catch (NumberFormatException e) {

      JOptionPane.showMessageDialog(
              null,
              "El número de la dirección debe contener solamente números."
      );

    } catch (RutInvalidoException e) {

      JOptionPane.showMessageDialog(
              null,
              e.getMessage()
      );
    }
  }

// Métodos
  private static void registrarGuia(GestorEntidades gestorEntidades) {

    String nombre = JOptionPane.showInputDialog(
            "Ingrese el nombre del guía turístico:"
    );

    if (nombre == null) {
      return;
    }

    String telefono = JOptionPane.showInputDialog(
            "Ingrese un número de contacto del guía:"
    );

    if (telefono == null) {
      return;
    }

    if (nombre.isBlank() || telefono.isBlank()) {

      JOptionPane.showMessageDialog(
              null,
              "Debe completar todos los campos."
      );

      return;
    }

    Guia guia = new Guia(nombre, telefono);

    gestorEntidades.agregarEntidad(guia);

    JOptionPane.showMessageDialog(
            null,
            "Guía turístico registrado correctamente.\n\n"
                    + guia.mostrarResumen()
    );
  }

  private static void registrarVehiculo(GestorEntidades gestorEntidades) {

    String patente = JOptionPane.showInputDialog(
            "Ingrese la patente del vehículo:"
    );

    if (patente == null) {
      return;
    }

    String tipoVehiculo = JOptionPane.showInputDialog(
            "Ingrese el tipo de vehículo:\n(Ejemplo: Van, Bus, Minibús)"
    );

    if (tipoVehiculo == null) {
      return;
    }

    if (patente.isBlank() || tipoVehiculo.isBlank()) {

      JOptionPane.showMessageDialog(
              null,
              "Debe completar todos los campos."
      );

      return;
    }

    Vehiculo vehiculo = new Vehiculo(
            patente,
            tipoVehiculo
    );

    gestorEntidades.agregarEntidad(vehiculo);

    JOptionPane.showMessageDialog(
            null,
            "Vehículo registrado correctamente.\n\n"
                    + vehiculo.mostrarResumen()
    );
  }

  private static void registrarColaborador(GestorEntidades gestorEntidades) {

    String nombre = JOptionPane.showInputDialog(
            "Ingrese el nombre del colaborador externo:"
    );

    if (nombre == null) {
      return;
    }

    String especialidad = JOptionPane.showInputDialog(
            "Ingrese la especialidad del colaborador:"
    );

    if (especialidad == null) {
      return;
    }

    if (nombre.isBlank() || especialidad.isBlank()) {

      JOptionPane.showMessageDialog(
              null,
              "Debe completar todos los campos."
      );

      return;
    }

    ColaboradorExterno colaborador =
            new ColaboradorExterno(
                    nombre,
                    especialidad
            );

    gestorEntidades.agregarEntidad(colaborador);

    JOptionPane.showMessageDialog(
            null,
            "Colaborador externo registrado correctamente.\n\n"
                    + colaborador.mostrarResumen()
    );
  }

  private static void mostrarEntidades(GestorEntidades gestorEntidades) {

    JOptionPane.showMessageDialog(
            null,
            gestorEntidades.obtenerResumenEntidades(),
            "Entidades registradas",
            JOptionPane.INFORMATION_MESSAGE
    );
  }

  private static void mostrarTours(ArrayList<Tour> tours) {

    if (tours.isEmpty()) {

      JOptionPane.showMessageDialog(
              null,
              "No fue posible cargar tours desde el archivo."
      );

      return;
    }

    String mensaje = "TOURS DISPONIBLES\n\n";

    for (Tour tour : tours) {
      mensaje = mensaje + tour.toString() + "\n\n";
    }

    JOptionPane.showMessageDialog(
            null,
            mensaje,
            "Tours cargados desde archivo",
            JOptionPane.INFORMATION_MESSAGE
    );
  }

  private static void mostrarServicios(
          List<ServicioTuristico> servicios
  ) {

    if (servicios.isEmpty()) {

      JOptionPane.showMessageDialog(
              null,
              "No existen servicios turísticos."
      );

      return;
    }

    String mensaje = "SERVICIOS TURÍSTICOS\n\n";

    for (ServicioTuristico servicio : servicios) {
      mensaje = mensaje + servicio.toString() + "\n\n";
    }

    JOptionPane.showMessageDialog(
            null,
            mensaje,
            "Servicios turísticos",
            JOptionPane.INFORMATION_MESSAGE
    );
  }
}