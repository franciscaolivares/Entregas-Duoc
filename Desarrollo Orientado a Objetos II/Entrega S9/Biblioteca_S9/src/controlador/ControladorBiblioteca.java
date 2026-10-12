package controlador;
import dao.*;
import model.*;
import java.sql.SQLException;
import java.util.List;
public final class ControladorBiblioteca {
 private final Usuario usuario;
 private final LibroDAO libros=new LibroDAO();private final CategoriaDAO categorias=new CategoriaDAO();
 private final EstudianteDAO estudiantes=new EstudianteDAO();private final PrestamoDAO prestamos=new PrestamoDAO();
 public ControladorBiblioteca(Usuario usuario){this.usuario=usuario;}
 public Usuario getUsuario(){return usuario;}
 private void gestion(){if(!usuario.puedeGestionar())throw new SecurityException("Esta operación requiere el rol bibliotecario.");}
 public List<Libro> libros() throws SQLException{return libros.readAll();}
 public List<Categoria> categorias() throws SQLException{return categorias.readAll();}
 public List<Estudiante> estudiantes() throws SQLException{gestion();return estudiantes.readAll();}
 public void guardar(Libro l,boolean nuevo) throws SQLException{gestion();texto(l.getTitulo(),"Título",150);texto(l.getAutor(),"Autor",100);texto(l.getEditorial(),"Editorial",100);texto(l.getIsbn(),"ISBN",20);if(l.getCategoriaId()<=0||l.getStockTotal()<0)throw new IllegalArgumentException("Seleccione una categoría y un total no negativo.");if(nuevo)libros.create(l);else libros.update(l);}
 public void eliminarLibro(int id) throws SQLException{gestion();libros.delete(id);}
 public void guardar(Categoria c,boolean nuevo) throws SQLException{gestion();texto(c.getNombre(),"Nombre",80);if(nuevo)categorias.create(c);else categorias.update(c);}
 public void eliminarCategoria(int id) throws SQLException{gestion();categorias.delete(id);}
 public void guardar(Estudiante e,char[] clave,boolean nuevo) throws SQLException{
  gestion();try {texto(e.getNombre(),"Nombre",100);texto(e.getLogin(),"Usuario",50);texto(e.getRut(),"RUT",15);texto(e.getCurso(),"Curso",50);texto(e.getCorreo(),"Correo",100);
   if(!e.getCorreo().matches("[^\\s@]+@[^\\s@]+\\.[^\\s@]+"))throw new IllegalArgumentException("El correo no tiene un formato válido.");
   if((nuevo||clave.length>0)&&clave.length<8)throw new IllegalArgumentException("La contraseña debe tener al menos 8 caracteres.");
   String hash=clave.length==0?null:ClaveSegura.crear(clave);if(nuevo)estudiantes.create(e,hash);else estudiantes.update(e,hash);
  }finally{java.util.Arrays.fill(clave,'\0');}
 }
 public void eliminarEstudiante(int id) throws SQLException{gestion();estudiantes.delete(id);}
 public int prestar(int libroId,int estudianteId) throws SQLException {
  if(libroId<=0||estudianteId<=0)throw new IllegalArgumentException("Seleccione un libro y un estudiante.");
  if(!usuario.puedeGestionar()&&usuario.getId()!=estudianteId)throw new SecurityException("Solo puede registrar sus propios préstamos.");
  return prestamos.registrar(libroId,estudianteId);
 }
 public void devolver(int prestamoId) throws SQLException{prestamos.devolver(prestamoId,usuario.puedeGestionar()?null:Integer.valueOf(usuario.getId()));}
 public List<Prestamo> prestamos(Integer estudiante,boolean activos) throws SQLException{
  return prestamos.readAll(usuario.puedeGestionar()?estudiante:Integer.valueOf(usuario.getId()),activos);
 }
 public List<Object[]> masPrestados() throws SQLException{return prestamos.masPrestados();}
 private void texto(String s,String campo,int max){if(s==null||s.isBlank()||s.length()>max)throw new IllegalArgumentException(campo+": ingrese entre 1 y "+max+" caracteres.");}
}
