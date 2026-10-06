package vista;

import controlador.ControladorPedidos;
import model.Repartidor;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

public class VentanaRepartidores extends JFrame {
    private final ControladorPedidos controlador;
    private final DefaultTableModel modelo = new DefaultTableModel(new String[]{"ID", "Nombre"}, 0) {
        @Override public boolean isCellEditable(int fila, int columna) { return false; }
    };

    public VentanaRepartidores(ControladorPedidos controlador) {
        this.controlador = controlador;
        setTitle("Repartidores - MySQL"); setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JTextField nombre = new JTextField(20);
        JButton guardar = new JButton("Registrar repartidor");
        JButton actualizar = new JButton("Actualizar lista");
        JPanel formulario = new JPanel(); formulario.add(new JLabel("Nombre:")); formulario.add(nombre); formulario.add(guardar);
        add(formulario, BorderLayout.NORTH); add(new JScrollPane(new JTable(modelo)), BorderLayout.CENTER);
        JPanel inferior = new JPanel(); inferior.add(actualizar); add(inferior, BorderLayout.SOUTH);
        guardar.addActionListener(e -> {
            try {
                controlador.registrarRepartidor(nombre.getText());
                nombre.setText(""); cargar();
                JOptionPane.showMessageDialog(this, "Repartidor registrado en MySQL.");
            } catch (IllegalArgumentException | SQLException ex) { mostrarError(ex); }
        });
        actualizar.addActionListener(e -> cargar());
        setSize(510, 360); setLocationRelativeTo(null); cargar();
    }

    private void cargar() {
        try {
            modelo.setRowCount(0);
            for (Repartidor repartidor : controlador.listarRepartidores())
                modelo.addRow(new Object[]{repartidor.getId(), repartidor.getNombre()});
        } catch (SQLException ex) { mostrarError(ex); }
    }
    private void mostrarError(Exception ex) {
        JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
    }
}
