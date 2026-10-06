package vista;

import controlador.ControladorPedidos;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class VentanaRegistroPedido extends JFrame {
    public VentanaRegistroPedido(ControladorPedidos controlador) {
        setTitle("Registrar pedido");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JTextField id = new JTextField();
        JTextField direccion = new JTextField();
        JComboBox<String> tipo = new JComboBox<>(new String[]{"Comida", "Encomienda", "Express"});
        JTextField distancia = new JTextField();
        JPanel campos = new JPanel(new GridLayout(4, 2, 10, 10));
        campos.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        campos.add(new JLabel("ID:")); campos.add(id);
        campos.add(new JLabel("Dirección:")); campos.add(direccion);
        campos.add(new JLabel("Tipo:")); campos.add(tipo);
        campos.add(new JLabel("Distancia (km):")); campos.add(distancia);
        JButton guardar = new JButton("Guardar");
        guardar.addActionListener(e -> {
            try {
                int numero = Integer.parseInt(id.getText().trim());
                double km = Double.parseDouble(distancia.getText().trim().replace(',', '.'));
                controlador.registrar(numero, direccion.getText(), (String) tipo.getSelectedItem(), km);
                JOptionPane.showMessageDialog(this, "Pedido registrado correctamente.");
                id.setText(""); direccion.setText(""); distancia.setText(""); id.requestFocusInWindow();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID debe ser entero y distancia debe ser numérica.", "Datos inválidos", JOptionPane.WARNING_MESSAGE);
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Datos inválidos", JOptionPane.WARNING_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "No se pudo guardar en MySQL: " + ex.getMessage(), "Error de base de datos", JOptionPane.ERROR_MESSAGE);
            }
        });
        add(campos, BorderLayout.CENTER);
        JPanel acciones = new JPanel(); acciones.add(guardar); add(acciones, BorderLayout.SOUTH);
        setSize(420, 270); setLocationRelativeTo(null);
    }
}
