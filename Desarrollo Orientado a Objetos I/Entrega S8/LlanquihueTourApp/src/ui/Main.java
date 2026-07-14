package ui;

import data.GestorEntidades;
import model.ColaboradorExterno;
import model.Guia;
import model.Vehiculo;

import javax.swing.JOptionPane;

public class Main {

  public static void main(String[] args) {

    GestorEntidades gestorEntidades = new GestorEntidades();

    int opcion = -1;

    do {

      String menu = """
             
                       AGENCIA LLANQUIHUE TOUR
                 

                    Seleccione una opción:

                    1. Registrar guía turístico
                    2. Registrar vehículo
                    3. Registrar colaborador externo
                    4. Mostrar entidades registradas
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
}