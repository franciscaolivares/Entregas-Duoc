package vista;

import controlador.ControladorPedidos;
import model.Pedido;
import model.Repartidor;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

public class VentanaListaPedidos extends JFrame {
    private final ControladorPedidos controlador;
    private final DefaultTableModel modelo = new DefaultTableModel(
            new Object[]{"ID", "Tipo", "Dirección", "Km", "Tiempo (min)", "Estado"}, 0) {
        @Override public boolean isCellEditable(int fila, int columna) { return false; }
    };
    private final JTable tabla = new JTable(modelo);

    public VentanaListaPedidos(ControladorPedidos controlador) {
        this.controlador = controlador;
        setTitle("Pedidos guardados en MySQL");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.setRowHeight(24);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        JButton refrescar = new JButton("Actualizar desde MySQL");
        JButton iniciar = new JButton("Asignar repartidor / Iniciar entrega");
        refrescar.addActionListener(e -> actualizarTabla());
        iniciar.addActionListener(e -> iniciarEntrega());
        JPanel botones = new JPanel(); botones.add(refrescar); botones.add(iniciar);
        add(botones, BorderLayout.SOUTH);
        setSize(800, 390); setLocationRelativeTo(null);
        actualizarTabla();
        Timer temporizador = new Timer(1500, e -> actualizarTabla());
        temporizador.start();
        addWindowListener(new java.awt.event.WindowAdapter() {
            @Override public void windowClosed(java.awt.event.WindowEvent e) { temporizador.stop(); }
        });
    }

    private void actualizarTabla() {
        Integer idSeleccionado = null;
        int seleccion = tabla.getSelectedRow();
        if (seleccion >= 0) idSeleccionado = (Integer) modelo.getValueAt(tabla.convertRowIndexToModel(seleccion), 0);
        try {
            java.util.List<Pedido> pedidos = controlador.listar();
            modelo.setRowCount(0);
            for (Pedido pedido : pedidos) modelo.addRow(new Object[]{pedido.getId(), pedido.getTipoPedido(),
                    pedido.getDireccionEntrega(), pedido.getDistanciaKm(), pedido.calcularTiempoEntrega(), pedido.obtenerEstado()});
            if (idSeleccionado != null) for (int i = 0; i < modelo.getRowCount(); i++) {
                if (idSeleccionado.equals(modelo.getValueAt(i, 0))) { tabla.setRowSelectionInterval(i, i); break; }
            }
        } catch (SQLException ex) {
            setTitle("Pedidos guardados en MySQL - Sin conexión (revisa la base de datos)");
        }
    }

    private void iniciarEntrega() {
        int fila = tabla.getSelectedRow();
        if (fila < 0) { JOptionPane.showMessageDialog(this, "Selecciona un pedido de la tabla."); return; }
        int id = (Integer) modelo.getValueAt(tabla.convertRowIndexToModel(fila), 0);
        try {
            java.util.List<Repartidor> repartidores = controlador.listarRepartidores();
            if (repartidores.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Primero registra un repartidor en la ventana principal."); return;
            }
            String[] opciones = new String[repartidores.size()];
            for (int i = 0; i < opciones.length; i++) opciones[i] = repartidores.get(i).getId() + " - " + repartidores.get(i).getNombre();
            String opcion = (String) JOptionPane.showInputDialog(this, "Selecciona repartidor:", "Asignar pedido " + id,
                    JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[0]);
            if (opcion == null) return;
            int indice = java.util.Arrays.asList(opciones).indexOf(opcion);
            controlador.iniciarEntrega(id, repartidores.get(indice), this::actualizarTabla);
            actualizarTabla();
            JOptionPane.showMessageDialog(this, "Entrega iniciada y registrada en MySQL.");
        } catch (IllegalArgumentException | SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "No se pudo iniciar", JOptionPane.WARNING_MESSAGE);
        }
    }
}
