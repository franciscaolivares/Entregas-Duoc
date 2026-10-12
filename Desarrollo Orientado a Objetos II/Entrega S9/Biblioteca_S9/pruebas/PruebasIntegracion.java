package pruebas;
import controlador.*;
import dao.*;
import model.*;
import java.sql.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
/** Pruebas de integración sobre una base de pruebas con las tablas del proyecto. */
public final class PruebasIntegracion {
 private static int comprobaciones;
 private static void comprobar(boolean valor,String caso){if(!valor)throw new AssertionError(caso);comprobaciones++;System.out.println("OK | "+caso);}
 @FunctionalInterface private interface Accion {void run()throws Exception;}
 private static void rechazar(Accion a,String caso)throws Exception{boolean rechazado=false;try{a.run();}catch(SQLException|IllegalArgumentException|SecurityException e){rechazado=true;}comprobar(rechazado,caso);}
 private static int entero(String sql)throws SQLException{return DatabaseConnection.getInstance().consultar(c->{try(Statement s=c.createStatement();ResultSet r=s.executeQuery(sql)){r.next();return r.getInt(1);}});}
 private static Libro libro(int id,int categoria,int total){return new Libro(id,"Libro de prueba","Autor","Editorial","TEST-"+categoria+"-"+id,categoria,"Prueba",total,total);}
 public static void main(String[] args)throws Exception{
  ControladorAutenticacion auth=new ControladorAutenticacion();Usuario admin=auth.ingresar("bibliotecario","Biblioteca2026!".toCharArray());
  comprobar(new ControladorBiblioteca(admin).prestamos(null,false).size()>=3,"Bibliotecario consulta el historial completo sin filtro de estudiante");
  comprobar(admin.puedeGestionar(),"Autenticación de bibliotecario y polimorfismo de permisos");
  rechazar(()->auth.ingresar("bibliotecario","incorrecta".toCharArray()),"Contraseña incorrecta rechazada");
  Usuario ana=auth.ingresar("ana","Estudiante2026!".toCharArray());comprobar(!ana.puedeGestionar(),"Autenticación y rol estudiante");
  String hash=ClaveSegura.crear("ClavePrueba2026!".toCharArray());comprobar(ClaveSegura.verificar("ClavePrueba2026!".toCharArray(),hash),"PBKDF2 con sal y verificación de contraseña");
  CategoriaDAO cd=new CategoriaDAO();LibroDAO ld=new LibroDAO();EstudianteDAO ed=new EstudianteDAO();UsuarioDAO ud=new UsuarioDAO();
  int cat=cd.create(new Categoria(0,"Categoría de prueba"));cd.update(new Categoria(cat,"Categoría modificada"));
  comprobar(cd.readAll().stream().anyMatch(c->c.getId()==cat&&c.getNombre().equals("Categoría modificada")),"CRUD de categorías: creación, lectura y edición");
  int lid=ld.create(libro(0,cat,1));Libro actualizado=new Libro(lid,"Título modificado","Autor","Editorial","TEST-EDIT",cat,"Prueba",1,1);ld.update(actualizado);
  comprobar(ld.readAll().stream().anyMatch(l->l.getId()==lid&&l.getTitulo().equals("Título modificado")),"CRUD de libros: creación, lectura y edición");
  int e1=ed.create(new Estudiante(0,0,"Estudiante Uno","test_uno","TEST-RUT-1","1A","uno@example.com"),hash);
  int e2=ed.create(new Estudiante(0,0,"Estudiante Dos","test_dos","TEST-RUT-2","1B","dos@example.com"),hash);
  Estudiante original=ed.readAll().stream().filter(e->e.getId()==e1).findFirst().orElseThrow();
  ed.update(new Estudiante(e1,original.getUsuarioId(),"Nombre modificado","test_uno","TEST-RUT-1","2A","uno@example.com"));
  comprobar(ed.readAll().stream().anyMatch(e->e.getId()==e1&&e.getCurso().equals("2A")),"CRUD de estudiantes y actualización de su cuenta vinculada");
  int usuariosAntes=ud.readAll().size();rechazar(()->ed.create(new Estudiante(0,0,"Duplicado","test_duplicado","TEST-RUT-1","1A","duplicado@example.com"),hash),"RUT duplicado rechazado");
  comprobar(ud.readAll().size()==usuariosAntes,"Rollback del estudiante evita cuentas huérfanas");
  Bibliotecario cuenta=new Bibliotecario(0,"Cuenta de prueba","test_admin");int uid=ud.create(cuenta,hash);
  ud.update(new Bibliotecario(uid,"Cuenta modificada","test_admin"));comprobar(ud.buscar("test_admin").getNombre().equals("Cuenta modificada"),"CRUD de usuarios: crear, consultar y modificar");ud.delete(uid);comprobar(ud.buscar("test_admin")==null,"CRUD de usuarios: eliminación");
  ControladorBiblioteca estudiante=new ControladorBiblioteca(auth.ingresar("test_uno","ClavePrueba2026!".toCharArray()));
  rechazar(()->estudiante.guardar(new Categoria(0,"No permitida"),true),"Controlador impide gestión desde rol estudiante");
  rechazar(()->estudiante.prestar(lid,e2),"Estudiante no puede prestar a nombre de otro");
  ExecutorService pool=Executors.newFixedThreadPool(8);CountDownLatch salida=new CountDownLatch(1);AtomicInteger exitos=new AtomicInteger();List<Future<?>> tareas=new ArrayList<>();
  for(int i=0;i<12;i++){final int eid=i%2==0?e1:e2;tareas.add(pool.submit(()->{try{salida.await();new PrestamoDAO().registrar(lid,eid);exitos.incrementAndGet();}catch(SQLException esperado){}catch(InterruptedException e){Thread.currentThread().interrupt();}}));}
  salida.countDown();for(Future<?> f:tareas)f.get(20,TimeUnit.SECONDS);pool.shutdown();
  comprobar(exitos.get()==1&&entero("SELECT stock FROM libros WHERE id="+lid)==0,"12 préstamos simultáneos: un éxito y stock cero");
  List<Prestamo> ps=new PrestamoDAO().readAll(null,false);Prestamo activo=ps.stream().filter(p->p.getLibroId()==lid).findFirst().orElseThrow();
  comprobar(activo.getVencimiento().equals(activo.getFecha().plusDays(14)),"Vencimiento automático a 14 días");
  comprobar(new ControladorBiblioteca(ana).prestamos(null,false).stream().allMatch(p->p.getEstudianteId()==ana.getId()),"Historial visible limitado al estudiante autenticado");
  int otro=activo.getEstudianteId()==e1?e2:e1;rechazar(()->new PrestamoDAO().devolver(activo.getId(),otro),"Devolución de préstamo ajeno rechazada");
  rechazar(()->ld.update(new Libro(lid,"Título modificado","Autor","Editorial","TEST-EDIT",cat,"Prueba",0,0)),"Inventario no puede reducirse por debajo de ejemplares prestados");
  new PrestamoDAO().devolver(activo.getId(),null);comprobar(entero("SELECT stock FROM libros WHERE id="+lid)==1,"Devolución restituye un ejemplar");
  rechazar(()->new PrestamoDAO().devolver(activo.getId(),null),"Devolución duplicada rechazada");comprobar(entero("SELECT stock FROM libros WHERE id="+lid)==1,"Devolución duplicada no incrementa stock");
  rechazar(()->ed.delete(activo.getEstudianteId()),"Historial protege al estudiante frente a eliminación");
  comprobar(ud.buscar("test_uno")!=null&&ud.buscar("test_dos")!=null,"Rollback de eliminación conserva las cuentas");
  int rollbackLibro=ld.create(new Libro(0,"Rollback","Autor","Editorial","TEST-ROLLBACK",cat,"Prueba",1,1));
  rechazar(()->new PrestamoDAO().registrar(rollbackLibro,9999999),"Préstamo con estudiante inexistente falla");comprobar(entero("SELECT stock FROM libros WHERE id="+rollbackLibro)==1,"Rollback del préstamo restaura stock tras error SQL");
  int procesoLibro=ld.create(new Libro(0,"Procesos","Autor","Editorial","TEST-PROCESOS",cat,"Prueba",1,1));
  String java=System.getProperty("java.home")+"/bin/java",cp=System.getProperty("java.class.path"),config=System.getProperty("biblioteca.config","database.properties");
  Process a=new ProcessBuilder(java,"-Dbiblioteca.config="+config,"-cp",cp,"pruebas.PruebasConcurrencia",String.valueOf(procesoLibro),String.valueOf(e1)).start();
  Process b=new ProcessBuilder(java,"-Dbiblioteca.config="+config,"-cp",cp,"pruebas.PruebasConcurrencia",String.valueOf(procesoLibro),String.valueOf(e2)).start();
  if(!a.waitFor(20,TimeUnit.SECONDS)||!b.waitFor(20,TimeUnit.SECONDS))throw new AssertionError("Procesos bloqueados");
  comprobar(((a.exitValue()==0&&b.exitValue()==2)||(a.exitValue()==2&&b.exitValue()==0))&&entero("SELECT stock FROM libros WHERE id="+procesoLibro)==0,"Dos procesos con conexiones distintas: un préstamo y stock consistente");
  int atrasado=new PrestamoDAO().registrar(rollbackLibro,e1);
  DatabaseConnection.getInstance().transaccion(c->{try(PreparedStatement s=c.prepareStatement("UPDATE prestamos SET fecha_prestamo=DATE_SUB(CURRENT_DATE,INTERVAL 20 DAY),fecha_vencimiento=DATE_SUB(CURRENT_DATE,INTERVAL 6 DAY) WHERE id=?")){s.setInt(1,atrasado);s.executeUpdate();}return null;});
  new PrestamoDAO().devolver(atrasado,null);comprobar(entero("SELECT atraso FROM prestamos WHERE id="+atrasado)==1,"Devolución atrasada queda registrada");
  comprobar(new PrestamoDAO().masPrestados().stream().anyMatch(r->((Integer)r[0])==lid&&((Integer)r[2])==1),"Reporte de libros más prestados conserva historial");
  comprobar(new PrestamoDAO().readAll(null,true).stream().allMatch(p->p.getDevolucion()==null),"Reporte de préstamos activos excluye devoluciones");
  DatabaseConnection.getInstance().transaccion(c->{try(Statement s=c.createStatement()){s.executeUpdate("DELETE FROM prestamos WHERE libro_id IN ("+lid+","+rollbackLibro+","+procesoLibro+")");}return null;});
  ld.delete(lid);ld.delete(rollbackLibro);ld.delete(procesoLibro);ed.delete(e1);ed.delete(e2);cd.delete(cat);
  comprobar(ld.readAll().stream().noneMatch(l->l.getId()==lid),"CRUD de libros: eliminación");
  comprobar(ed.readAll().stream().noneMatch(e->e.getId()==e1)&&ud.buscar("test_uno")==null,"CRUD de estudiantes: eliminación atómica de cuenta");
  comprobar(cd.readAll().stream().noneMatch(c->c.getId()==cat),"CRUD de categorías: eliminación");
  DatabaseConnection.getInstance().close();System.out.println("RESULTADO: "+comprobaciones+" comprobaciones aprobadas.");
 }
}
