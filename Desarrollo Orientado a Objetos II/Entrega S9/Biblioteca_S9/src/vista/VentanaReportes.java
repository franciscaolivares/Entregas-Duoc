package vista;
import controlador.ControladorBiblioteca;
import model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
public final class VentanaReportes extends JPanel {
 private static final long serialVersionUID=1L;
 private final JComboBox<String> tipo=new JComboBox<>(new String[]{"Libros más prestados","Historial por estudiante","Libros actualmente en préstamo"});
 private final JComboBox<Estudiante> estudiante=new JComboBox<>();private final DefaultTableModel modelo=Componentes.modelo("ID","Libro","Préstamos");private final JTable tabla=new JTable(modelo);
 private List<Prestamo> prestamos=List.of();private List<Object[]> ranking=List.of();
 public VentanaReportes(VentanaPrincipal ventana,ControladorBiblioteca controlador){
  super(new BorderLayout(8,8));setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
  JPanel barra=new JPanel(new FlowLayout(FlowLayout.LEFT));barra.add(new JLabel("Informe"));barra.add(tipo);barra.add(new JLabel("Estudiante"));barra.add(estudiante);Componentes.boton(barra,"Actualizar datos",ventana::refrescar);add(barra,BorderLayout.NORTH);add(Componentes.tabla(tabla));
  tipo.addActionListener(e->mostrar());estudiante.addActionListener(e->mostrar());
 }
 public void cargar(List<Estudiante> es,List<Prestamo> ps,List<Object[]> rs){
  prestamos=ps;ranking=rs;Integer id=estudiante.getSelectedItem()==null?null:((Estudiante)estudiante.getSelectedItem()).getId();estudiante.removeAllItems();
  for(Estudiante e:es){estudiante.addItem(e);if(id!=null&&id==e.getId())estudiante.setSelectedItem(e);}mostrar();
 }
 private void mostrar(){int t=tipo.getSelectedIndex();
  if(t==0){modelo.setColumnIdentifiers(new String[]{"ID","Libro","Total de préstamos"});Componentes.filas(modelo,ranking);return;}
  modelo.setColumnIdentifiers(new String[]{"ID","Libro","Estudiante","Préstamo","Vencimiento","Devolución","Estado"});Estudiante e=(Estudiante)estudiante.getSelectedItem();
  List<Prestamo> datos=prestamos.stream().filter(p->t==2?p.getDevolucion()==null:e!=null&&p.getEstudianteId()==e.getId()).toList();Componentes.filas(modelo,datos.stream().map(VentanaPrestamos::fila).toList());
 }
}
