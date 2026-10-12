package main;
import vista.VentanaAutenticacion;
import dao.DatabaseConnection;
import javax.swing.*;
public final class Main {
 private Main() { }
 public static void main(String[] args){
  Runtime.getRuntime().addShutdownHook(new Thread(()->{try{DatabaseConnection.getInstance().close();}catch(java.sql.SQLException e){System.err.println("No se pudo cerrar la conexión.");}},"cierre-biblioteca"));
  SwingUtilities.invokeLater(()->{try{UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());}catch(Exception e){System.err.println("Estilo predeterminado de Swing.");}new VentanaAutenticacion().setVisible(true);});
 }
}
