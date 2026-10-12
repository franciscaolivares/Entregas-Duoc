package pruebas;
import vista.*;
import model.*;
import dao.*;
import controlador.*;
import javax.swing.*;
import java.awt.*;
import javax.imageio.ImageIO;
import java.io.File;
import java.sql.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BooleanSupplier;
/** Comprueba acceso por rol y disponibilidad del EDT durante una transacción bloqueada. */
public final class PruebasInterfaz {
 private static void esperar(BooleanSupplier condicion)throws Exception{long limite=System.currentTimeMillis()+15000;while(!condicion.getAsBoolean()){if(System.currentTimeMillis()>limite){try{ImageIO.write(new Robot().createScreenCapture(new Rectangle(0,0,1200,800)),"png",new File("/tmp/error-ui.png"));for(Window w:Window.getWindows())if(w.isVisible())for(JLabel l:componentes(w,JLabel.class))System.out.println("PANTALLA: "+l.getText());}catch(Exception e){e.printStackTrace();}System.exit(1);throw new AssertionError("Tiempo agotado");};Thread.sleep(50);}}
 private static <T extends Component> List<T> componentes(Container c,Class<T> tipo){List<T> out=new ArrayList<>();for(Component x:c.getComponents()){if(tipo.isInstance(x))out.add(tipo.cast(x));if(x instanceof Container)out.addAll(componentes((Container)x,tipo));}return out;}
 private static VentanaPrincipal principal(){for(Frame f:Frame.getFrames())if(f instanceof VentanaPrincipal&&f.isVisible())return (VentanaPrincipal)f;return null;}
 private static void login(String usuario,String clave)throws Exception{
  SwingUtilities.invokeAndWait(()->{VentanaAutenticacion v=new VentanaAutenticacion();v.setVisible(true);List<JTextField> campos=componentes(v,JTextField.class);for(JTextField t:campos){if(t instanceof JPasswordField)t.setText(clave);else t.setText(usuario);}componentes(v,JButton.class).stream().filter(b->b.getText().equals("Ingresar")).findFirst().orElseThrow().doClick();});
  esperar(()->principal()!=null);
  esperar(()->{final boolean[] listo={false};try{SwingUtilities.invokeAndWait(()->listo[0]=componentes(principal(),JTable.class).stream().anyMatch(t->t.getRowCount()>=4));}catch(Exception e){throw new RuntimeException(e);}return listo[0];});
 }
 public static void main(String[] args)throws Exception{
  login("bibliotecario","Biblioteca2026!");VentanaPrincipal admin=principal();
  SwingUtilities.invokeAndWait(()->{if(componentes(admin,JTabbedPane.class).size()!=2)throw new AssertionError("Navegación de gestión incompleta");});System.out.println("OK | Acceso bibliotecario y pestañas de gestión");
  ImageIO.write(new Robot().createScreenCapture(new Rectangle(0,0,1200,800)),"png",new File("/tmp/biblioteca-admin.png"));
  LibroDAO dao=new LibroDAO();int libro=dao.create(new Libro(0,"Prueba de hilo","Autor","Editorial","TEST-UI",1,"Literatura",1,1));
  String url=System.getenv().getOrDefault("BIBLIOTECA_DB_URL","jdbc:mysql://127.0.0.1:3306/biblioteca_db");
  try(Connection bloqueo=DriverManager.getConnection(url,"biblioteca_app","Biblioteca2026!")){
   bloqueo.setAutoCommit(false);try(PreparedStatement s=bloqueo.prepareStatement("SELECT id FROM libros WHERE id=? FOR UPDATE")){s.setInt(1,libro);try(ResultSet r=s.executeQuery()){r.next();}}
   AtomicInteger latidos=new AtomicInteger();javax.swing.Timer reloj=new javax.swing.Timer(30,e->latidos.incrementAndGet());
   ControladorBiblioteca c=new ControladorBiblioteca(new ControladorAutenticacion().ingresar("bibliotecario","Biblioteca2026!".toCharArray()));
   SwingUtilities.invokeAndWait(()->{reloj.start();admin.modificar("Registrando préstamo…",()->{c.prestar(libro,1);return null;});});
   Thread.sleep(800);if(latidos.get()<10)throw new AssertionError("EDT bloqueado");bloqueo.rollback();
   javax.swing.Timer cerrar=new javax.swing.Timer(50,e->{for(Window w:Window.getWindows())if(w instanceof JDialog&&w.isVisible())w.dispose();});
   SwingUtilities.invokeAndWait(cerrar::start);esperar(()->{try{return DatabaseConnection.getInstance().consultar(conn->{try(PreparedStatement s=conn.prepareStatement("SELECT COUNT(*) FROM prestamos WHERE libro_id=?")){s.setInt(1,libro);try(ResultSet r=s.executeQuery()){r.next();return r.getInt(1)==1;}}});}catch(SQLException e){throw new RuntimeException(e);}});
   Thread.sleep(400);SwingUtilities.invokeAndWait(()->{cerrar.stop();reloj.stop();admin.dispose();});System.out.println("OK | SwingWorker mantiene el EDT activo mientras SQL espera un bloqueo");
  }
  DatabaseConnection.getInstance().transaccion(c->{try(PreparedStatement s=c.prepareStatement("DELETE FROM prestamos WHERE libro_id=?")){s.setInt(1,libro);s.executeUpdate();}return null;});dao.delete(libro);
  login("ana","Estudiante2026!");VentanaPrincipal estudiante=principal();
  SwingUtilities.invokeAndWait(()->{if(componentes(estudiante,JTabbedPane.class).size()!=1)throw new AssertionError("Gestión expuesta a estudiante");});
  ImageIO.write(new Robot().createScreenCapture(new Rectangle(0,0,1200,800)),"png",new File("/tmp/biblioteca-estudiante.png"));
  System.out.println("OK | Acceso estudiante limitado a catálogo, préstamos y reportes propios");
  SwingUtilities.invokeAndWait(()->{for(Window w:Window.getWindows())w.dispose();});DatabaseConnection.getInstance().close();System.out.println("RESULTADO: 3 comprobaciones de interfaz aprobadas.");System.exit(0);
 }
}
