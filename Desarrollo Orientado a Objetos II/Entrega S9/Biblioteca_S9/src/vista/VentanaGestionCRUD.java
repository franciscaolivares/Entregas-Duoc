package vista;
import controlador.ControladorBiblioteca;
import model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
/** Mantiene la organización de gestión por pestañas y tablas de la base de Semana 8. */
public final class VentanaGestionCRUD extends JPanel {
 private static final long serialVersionUID=1L;
 private final VentanaPrincipal ventana;private final ControladorBiblioteca controlador;
 private List<Libro> libros=List.of();private List<Categoria> categorias=List.of();private List<Estudiante> estudiantes=List.of();
 private final DefaultTableModel lm=Componentes.modelo("ID","Título","Autor","Editorial","ISBN","Categoría","Disponibles","Total"),cm=Componentes.modelo("ID","Nombre"),em=Componentes.modelo("ID","Nombre","Usuario","RUT","Curso","Correo");
 private final JTable lt=new JTable(lm),ct=new JTable(cm),et=new JTable(em);
 public VentanaGestionCRUD(VentanaPrincipal ventana,ControladorBiblioteca controlador){
  super(new BorderLayout(8,8));this.ventana=ventana;this.controlador=controlador;setBorder(BorderFactory.createEmptyBorder(12,12,12,12));
  if(controlador.getUsuario().puedeGestionar()){
   JTabbedPane tabs=new JTabbedPane();tabs.add("Libros",panel(lt,()->libro(true),()->libro(false),()->eliminarLibro()));
   tabs.add("Estudiantes",panel(et,()->estudiante(true),()->estudiante(false),()->eliminarEstudiante()));
   tabs.add("Categorías",panel(ct,()->categoria(true),()->categoria(false),()->eliminarCategoria()));add(tabs);
  }else add(Componentes.tabla(lt));
 }
 private JPanel panel(JTable t,Runnable crear,Runnable editar,Runnable borrar){JPanel p=new JPanel(new BorderLayout(8,8));p.add(Componentes.tabla(t));JPanel b=new JPanel();Componentes.boton(b,"Registrar",()->ventana.proteger(crear));Componentes.boton(b,"Guardar cambios",()->ventana.proteger(editar));Componentes.boton(b,"Eliminar",()->ventana.proteger(borrar));p.add(b,BorderLayout.SOUTH);return p;}
 public void cargar(List<Libro> ls,List<Categoria> cs,List<Estudiante> es){
  libros=ls;categorias=cs;estudiantes=es;
  Componentes.filas(lm,ls.stream().map(l->new Object[]{l.getId(),l.getTitulo(),l.getAutor(),l.getEditorial(),l.getIsbn(),l.getCategoria(),l.getStock(),l.getStockTotal()}).toList());
  Componentes.filas(cm,cs.stream().map(c->new Object[]{c.getId(),c.getNombre()}).toList());
  Componentes.filas(em,es.stream().map(e->new Object[]{e.getId(),e.getNombre(),e.getLogin(),e.getRut(),e.getCurso(),e.getCorreo()}).toList());
 }
 private void libro(boolean nuevo){
  if(categorias.isEmpty())throw new IllegalArgumentException("Debe existir al menos una categoría.");
  Libro l=nuevo?null:libros.get(Componentes.seleccionado(lt));
  JTextField titulo=new JTextField(l==null?"":l.getTitulo(),22),autor=new JTextField(l==null?"":l.getAutor()),editorial=new JTextField(l==null?"":l.getEditorial()),isbn=new JTextField(l==null?"":l.getIsbn());
  JComboBox<Categoria> cat=new JComboBox<>(categorias.toArray(Categoria[]::new));if(l!=null)for(Categoria c:categorias)if(c.getId()==l.getCategoriaId())cat.setSelectedItem(c);
  JSpinner total=new JSpinner(new SpinnerNumberModel(l==null?1:l.getStockTotal(),0,100000,1));
  if(!Componentes.formulario(this,nuevo?"Registrar libro":"Modificar libro","Título",titulo,"Autor",autor,"Editorial",editorial,"ISBN",isbn,"Categoría",cat,"Total de ejemplares",total))return;
  Categoria c=(Categoria)cat.getSelectedItem();Libro valor=new Libro(l==null?0:l.getId(),titulo.getText().trim(),autor.getText().trim(),editorial.getText().trim(),isbn.getText().trim(),c.getId(),c.getNombre(),0,((Number)total.getValue()).intValue());
  ventana.modificar("Guardando libro…",()->{controlador.guardar(valor,nuevo);return null;});
 }
 private void categoria(boolean nuevo){Categoria c=nuevo?null:categorias.get(Componentes.seleccionado(ct));JTextField nombre=new JTextField(c==null?"":c.getNombre(),22);if(!Componentes.formulario(this,nuevo?"Registrar categoría":"Modificar categoría","Nombre",nombre))return;
  Categoria valor=new Categoria(c==null?0:c.getId(),nombre.getText().trim());ventana.modificar("Guardando categoría…",()->{controlador.guardar(valor,nuevo);return null;});}
 private void estudiante(boolean nuevo){
  Estudiante e=nuevo?null:estudiantes.get(Componentes.seleccionado(et));JTextField nombre=new JTextField(e==null?"":e.getNombre(),22),login=new JTextField(e==null?"":e.getLogin()),rut=new JTextField(e==null?"":e.getRut()),curso=new JTextField(e==null?"":e.getCurso()),correo=new JTextField(e==null?"":e.getCorreo());JPasswordField clave=new JPasswordField();
  if(!Componentes.formulario(this,nuevo?"Registrar estudiante":"Modificar estudiante","Nombre",nombre,"Usuario",login,nuevo?"Contraseña":"Nueva contraseña (opcional)",clave,"RUT",rut,"Curso",curso,"Correo",correo))return;
  Estudiante valor=new Estudiante(e==null?0:e.getId(),e==null?0:e.getUsuarioId(),nombre.getText().trim(),login.getText().trim(),rut.getText().trim(),curso.getText().trim(),correo.getText().trim());char[] password=clave.getPassword();
  ventana.modificar("Guardando estudiante…",()->{controlador.guardar(valor,password,nuevo);return null;});
 }
 private void eliminarLibro(){int id=libros.get(Componentes.seleccionado(lt)).getId();if(Componentes.confirmar(this))ventana.modificar("Eliminando libro…",()->{controlador.eliminarLibro(id);return null;});}
 private void eliminarCategoria(){int id=categorias.get(Componentes.seleccionado(ct)).getId();if(Componentes.confirmar(this))ventana.modificar("Eliminando categoría…",()->{controlador.eliminarCategoria(id);return null;});}
 private void eliminarEstudiante(){int id=estudiantes.get(Componentes.seleccionado(et)).getId();if(Componentes.confirmar(this))ventana.modificar("Eliminando estudiante…",()->{controlador.eliminarEstudiante(id);return null;});}
}
