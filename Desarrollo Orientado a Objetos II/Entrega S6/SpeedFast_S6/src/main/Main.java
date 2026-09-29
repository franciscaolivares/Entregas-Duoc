package main;

import controlador.ControladorPedidos;
import vista.VentanaPrincipal;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrincipal(new ControladorPedidos()).setVisible(true));
    }
}
