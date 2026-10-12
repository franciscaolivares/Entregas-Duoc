package vista;
import controlador.ControladorBiblioteca;
import model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
public final class VentanaPrestamos extends JPanel {
 private static final long serialVersionUID=1L;
 private final VentanaPrincipal ventana;private final ControladorBiblioteca controlador;
 private final JComboBox<Libro> libro=new JComboBox<>();private final JComboBox<Estudiante> estudiante=new JComboBox<>();
 private final DefaultTableModel modelo=Componentes.modelo("ID","Libro","Estudiante","Préstamo","Vencimiento","Devolución","Estado");private final JTable tabla=new JTable(modelo);
 private List<Prestamo> prestamos=List.of();
 public VentanaPrestamos(VentanaPrincipal ventana,ControladorBiblioteca controlador){
  super(new BorderLayout(8,8));this.ventana=ventana;this.controlador=controlador;setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
  JPanel formulario=new JPanel(new GridLayout(0,2,8,8));formulario.add(new JLabel("Libro"));formulario.add(libro);formulario.add(new JLabel("Estudiante"));formulario.add(estudiante);add(formulario,BorderLayout.NORTH);add(Componentes.tabla(tabla));
  JPanel botones=new JPanel();Componentes.boton(botones,"Registrar préstamo",()->ventana.proteger(this::prestar));Componentes.boton(botones,"Registrar devolución",()->ventana.proteger(this::devolver));add(botones,BorderLayout.SOUTH);
 }
 public void cargar(List<Libro> ls,List<Estudiante> es,List<Prestamo> ps){
  Integer li=libro.getSelectedItem()==null?null:((Libro)libro.getSelectedItem()).getId();Integer ei=estudiante.getSelectedItem()==null?null:((Estudiante)estudiante.getSelectedItem()).getId();
  libro.removeAllItems();for(Libro l:ls){libro.addItem(l);if(li!=null&&l.getId()==li)libro.setSelectedItem(l);}
  estudiante.removeAllItems();for(Estudiante e:es){estudiante.addItem(e);if(ei!=null&&e.getId()==ei)estudiante.setSelectedItem(e);}
  prestamos=ps;Componentes.filas(modelo,ps.stream().map(VentanaPrestamos::fila).toList());
 }
 static Object[] fila(Prestamo p){return new Object[]{p.getId(),p.getLibro(),p.getEstudiante(),p.getFecha(),p.getVencimiento(),p.getDevolucion()==null?"":p.getDevolucion(),p.getEstado()};}
 private void prestar(){Libro l=(Libro)libro.getSelectedItem();Estudiante e=(Estudiante)estudiante.getSelectedItem();if(l==null||e==null)throw new IllegalArgumentException("Seleccione un libro y un estudiante.");
  // Las selecciones se capturan en el EDT; JDBC y la transacción se ejecutan en SwingWorker.
  int lid=l.getId(),eid=e.getId();ventana.modificar("Registrando préstamo…",()->{controlador.prestar(lid,eid);return null;});}
 private void devolver(){Prestamo p=prestamos.get(Componentes.seleccionado(tabla));if(p.getDevolucion()!=null)throw new IllegalArgumentException("Este préstamo ya fue devuelto.");
  if(JOptionPane.showConfirmDialog(this,"¿Registrar devolución de «"+p.getLibro()+"»?","Devolución",JOptionPane.YES_NO_OPTION)!=JOptionPane.YES_OPTION)return;
  int id=p.getId();ventana.modificar("Registrando devolución…",()->{controlador.devolver(id);return null;});}
}
