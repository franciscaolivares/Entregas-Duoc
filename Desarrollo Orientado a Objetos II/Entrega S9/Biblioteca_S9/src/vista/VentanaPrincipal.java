package vista;
import controlador.ControladorBiblioteca;
import model.*;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;
public final class VentanaPrincipal extends JFrame {
 private static final long serialVersionUID=1L;
 private final ControladorBiblioteca controlador;
 private final VentanaGestionCRUD gestion;private final VentanaPrestamos prestamos;private final VentanaReportes reportes;
 private final JLabel estado=new JLabel(" ");private boolean ocupado;
 public VentanaPrincipal(Usuario usuario){
  super("Biblioteca Escolar | Gestión — Semana 9");controlador=new ControladorBiblioteca(usuario);
  setDefaultCloseOperation(EXIT_ON_CLOSE);setSize(1100,680);setMinimumSize(new Dimension(880,560));setLocationRelativeTo(null);
  JPanel encabezado=new JPanel(new BorderLayout(8,8));encabezado.setBorder(BorderFactory.createEmptyBorder(12,16,12,16));
  JLabel titulo=new JLabel("Biblioteca Escolar");titulo.setFont(titulo.getFont().deriveFont(Font.BOLD,22));encabezado.add(titulo,BorderLayout.WEST);
  JPanel acceso=new JPanel();acceso.add(new JLabel(usuario.getNombre()+" | "+usuario.getRol()));Componentes.boton(acceso,"Cerrar sesión",()->{if(!ocupado){new VentanaAutenticacion().setVisible(true);dispose();}});encabezado.add(acceso,BorderLayout.EAST);
  add(encabezado,BorderLayout.NORTH);JTabbedPane tabs=new JTabbedPane();
  gestion=new VentanaGestionCRUD(this,controlador);prestamos=new VentanaPrestamos(this,controlador);reportes=new VentanaReportes(this,controlador);
  tabs.add(usuario.puedeGestionar()?"Gestión del catálogo y estudiantes":"Consulta del catálogo",gestion);tabs.add("Préstamos y devoluciones",prestamos);tabs.add("Reportes",reportes);add(tabs);
  JPanel pie=new JPanel(new BorderLayout());pie.setBorder(BorderFactory.createEmptyBorder(8,16,8,16));pie.add(estado,BorderLayout.CENTER);JPanel acciones=new JPanel(new FlowLayout(FlowLayout.RIGHT,0,0));Componentes.boton(acciones,"Actualizar",this::refrescar);pie.add(acciones,BorderLayout.EAST);add(pie,BorderLayout.SOUTH);
  SwingUtilities.invokeLater(this::refrescar);
 }
 @FunctionalInterface public interface Trabajo<T>{T run() throws Exception;}
 public void proteger(Runnable accion){if(ocupado)return;try{accion.run();}catch(RuntimeException e){error(this,e);}}
 public <T> void ejecutar(String mensaje,Trabajo<T> trabajo,Consumer<T> completar){
  if(ocupado)return;ocupado=true;estado.setText(mensaje);setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
  setEnabledTree(getContentPane(),false);
  new SwingWorker<T,Void>(){
   protected T doInBackground() throws Exception{return trabajo.run();}
   protected void done(){
    ocupado=false;setEnabledTree(getContentPane(),true);setCursor(Cursor.getDefaultCursor());
    try{T resultado=get();estado.setText("Operación completada.");completar.accept(resultado);}
    catch(InterruptedException e){Thread.currentThread().interrupt();estado.setText("Operación interrumpida.");}
    catch(ExecutionException e){estado.setText("Operación no completada.");error(VentanaPrincipal.this,e.getCause());}
    catch(RuntimeException e){error(VentanaPrincipal.this,e);}
   }
  }.execute();
 }
 private void setEnabledTree(Component c,boolean enabled){c.setEnabled(enabled);if(c instanceof Container)for(Component hijo:((Container)c).getComponents())setEnabledTree(hijo,enabled);}
 public void modificar(String mensaje,Trabajo<Void> trabajo){ejecutar(mensaje,trabajo,v->{JOptionPane.showMessageDialog(this,"Operación realizada correctamente.");refrescar();});}
 private record Datos(List<Libro> libros,List<Categoria> categorias,List<Estudiante> estudiantes,List<Prestamo> prestamos,List<Object[]> ranking) { }
 public void refrescar(){ejecutar("Actualizando datos…",()->{
  List<Estudiante> es=controlador.getUsuario().puedeGestionar()?controlador.estudiantes():List.of((Estudiante)controlador.getUsuario());
  return new Datos(controlador.libros(),controlador.categorias(),es,controlador.prestamos(null,false),controlador.masPrestados());
 },d->{gestion.cargar(d.libros,d.categorias,d.estudiantes);prestamos.cargar(d.libros,d.estudiantes,d.prestamos);reportes.cargar(d.estudiantes,d.prestamos,d.ranking);});}
 public static void error(Component owner,Throwable e){
  if(e instanceof SQLException)e.printStackTrace(System.err);
  String m=e.getMessage();if(e instanceof SQLException){SQLException sql=(SQLException)e;String s=sql.getSQLState();
   if(s!=null&&s.startsWith("23"))m="No se puede completar la operación: hay un dato duplicado, una referencia inválida o un historial relacionado.";
   else if(s!=null&&s.startsWith("08"))m="No se pudo conectar con la base de datos.";
   else if(s!=null&&s.startsWith("28"))m="Acceso a la base de datos rechazado.";
  }
  JOptionPane.showMessageDialog(owner,m==null?"No se pudo completar la operación.":m,"Biblioteca Escolar",JOptionPane.WARNING_MESSAGE);
 }
}
