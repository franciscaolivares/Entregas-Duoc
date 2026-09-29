package vista;

import controlador.ControladorPedidos;
import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {
    private final ControladorPedidos controlador;

    public VentanaPrincipal(ControladorPedidos controlador) {
        this.controlador = controlador;
        setTitle("SpeedFast - Gestión de entregas MySQL");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(460, 310);
        setLocationRelativeTo(null);
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(18, 25, 18, 25));
        JLabel titulo = new JLabel("SpeedFast | Semana 7", SwingConstants.CENTER);
        titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 20f));
        JButton registrar = new JButton("Registrar pedido");
        JButton listar = new JButton("Listar pedidos");
        JButton asignar = new JButton("Asignar repartidor / Iniciar entrega");
        JButton repartidores = new JButton("Registrar y consultar repartidores");
        registrar.addActionListener(e -> new VentanaRegistroPedido(controlador).setVisible(true));
        listar.addActionListener(e -> new VentanaListaPedidos(controlador).setVisible(true));
        asignar.addActionListener(e -> new VentanaListaPedidos(controlador).setVisible(true));
        repartidores.addActionListener(e -> new VentanaRepartidores(controlador).setVisible(true));
        panel.add(titulo); panel.add(registrar); panel.add(listar); panel.add(asignar); panel.add(repartidores);
        add(panel, BorderLayout.CENTER);
    }
}
