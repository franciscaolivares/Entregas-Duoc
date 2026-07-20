package data;

import model.Reserva;
import java.util.ArrayList;

public class GestorReservas {

    private ArrayList<Reserva> reservas;

    public GestorReservas() {
        reservas = new ArrayList<>();
    }

    public void agregarReserva(Reserva reserva) {
        reservas.add(reserva);
    }

    public ArrayList<Reserva> getReservas() {
        return reservas;
    }

    public Reserva buscarReserva(int numeroReserva) {

        for (Reserva reserva : reservas) {

            if (reserva.getNumeroReserva() == numeroReserva) {
                return reserva;
            }
        }

        return null;
    }

    public ArrayList<Reserva> filtrarReservasPorCantidad(
            int cantidadMinima
    ) {

        ArrayList<Reserva> reservasFiltradas = new ArrayList<>();

        for (Reserva reserva : reservas) {

            if (reserva.getCantidadPersonas() >= cantidadMinima) {
                reservasFiltradas.add(reserva);
            }
        }

        return reservasFiltradas;
    }
}