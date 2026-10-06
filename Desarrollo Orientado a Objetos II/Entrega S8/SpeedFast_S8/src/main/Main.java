package main;

import controlador.ControladorPedidos;
import vista.VentanaPrincipal;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ControladorPedidos controlador = new ControladorPedidos();
            try {
                controlador.verificarConexion();
                new VentanaPrincipal(controlador).setVisible(true);
            } catch (java.sql.SQLException e) {
                javax.swing.JOptionPane.showMessageDialog(null,
                        "No se pudo conectar a MySQL. Ejecuta schema.sql y revisa los datos de conexión.\n" + e.getMessage(),
                        "SpeedFast", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
