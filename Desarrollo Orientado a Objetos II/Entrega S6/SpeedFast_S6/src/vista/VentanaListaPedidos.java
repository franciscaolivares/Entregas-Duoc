package vista;

import controlador.ControladorPedidos;
import model.Pedido;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class VentanaListaPedidos extends JFrame {
    private final ControladorPedidos controlador;
    private final DefaultTableModel modelo = new DefaultTableModel(new Object[]{"ID", "Tipo", "Dirección", "Km", "Tiempo (min)", "Estado"}, 0) {
        @Override public boolean isCellEditable(int fila, int columna) { return false; }
    };
    private final JTable tabla = new JTable(modelo);

    public VentanaListaPedidos(ControladorPedidos controlador) {
        this.controlador = controlador;
        setTitle("Pedidos y entregas");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setRowHeight(24);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        JButton refrescar = new JButton("Actualizar tabla");
        JButton iniciar = new JButton("Asignar repartidor / Iniciar entrega");
        refrescar.addActionListener(e -> actualizarTabla());
        iniciar.addActionListener(e -> iniciarEntrega());
        JPanel botones = new JPanel(); botones.add(refrescar); botones.add(iniciar);
        add(botones, BorderLayout.SOUTH);
        setSize(790, 390); setLocationRelativeTo(null);
        actualizarTabla();
        Timer temporizador = new Timer(700, e -> actualizarTabla());
        temporizador.start();
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override public void windowClosed(java.awt.event.WindowEvent e) { temporizador.stop(); }
        });
    }

    private void actualizarTabla() {
        Integer idSeleccionado = null;
        int seleccion = tabla.getSelectedRow();
        if (seleccion >= 0) idSeleccionado = (Integer) modelo.getValueAt(tabla.convertRowIndexToModel(seleccion), 0);
        modelo.setRowCount(0);
        for (Pedido pedido : controlador.listar()) {
            modelo.addRow(new Object[]{pedido.getId(), pedido.getTipoPedido(), pedido.getDireccionEntrega(),
                    pedido.getDistanciaKm(), pedido.calcularTiempoEntrega(), pedido.obtenerEstado()});
        }
        if (idSeleccionado != null) for (int i = 0; i < modelo.getRowCount(); i++) {
            if (idSeleccionado.equals(modelo.getValueAt(i, 0))) { tabla.setRowSelectionInterval(i, i); break; }
        }
    }

    private void iniciarEntrega() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Selecciona un pedido de la tabla."); return;
        }
        int id = (Integer) modelo.getValueAt(tabla.convertRowIndexToModel(fila), 0);
        String nombre = JOptionPane.showInputDialog(this, "Nombre del repartidor para el pedido " + id + ":");
        if (nombre == null) return;
        try {
            controlador.iniciarEntrega(id, nombre, this::actualizarTabla);
            actualizarTabla();
            JOptionPane.showMessageDialog(this, "Entrega iniciada. El estado se actualizará automáticamente.");
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "No se pudo iniciar", JOptionPane.WARNING_MESSAGE);
        }
    }
}
