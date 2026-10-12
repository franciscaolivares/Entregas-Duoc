package dao;
import model.Prestamo;
import java.sql.*;
import java.time.LocalDate;
import java.util.*;
public final class PrestamoDAO {
 private final DatabaseConnection db=DatabaseConnection.getInstance();
 /** El monitor local y el bloqueo SQL protegen incluso frente a otra instancia del ejecutable. */
 public synchronized int registrar(int libroId,int estudianteId) throws SQLException {
  return db.transaccion(c->{
   try(PreparedStatement s=c.prepareStatement("SELECT stock FROM libros WHERE id=? FOR UPDATE")){s.setInt(1,libroId);try(ResultSet r=s.executeQuery()){if(!r.next())throw new SQLException("El libro ya no existe.");if(r.getInt(1)<=0)throw new SQLException("El libro no tiene ejemplares disponibles.");}}
   try(PreparedStatement s=c.prepareStatement("SELECT id FROM prestamos WHERE libro_id=? AND estudiante_id=? AND fecha_devolucion IS NULL")){s.setInt(1,libroId);s.setInt(2,estudianteId);try(ResultSet r=s.executeQuery()){if(r.next())throw new SQLException("El estudiante ya tiene un préstamo activo de este libro.");}}
   try(PreparedStatement s=c.prepareStatement("UPDATE libros SET stock=stock-1 WHERE id=? AND stock>0")){s.setInt(1,libroId);if(s.executeUpdate()!=1)throw new SQLException("No hay stock disponible.");}
   LocalDate hoy=LocalDate.now();
   try(PreparedStatement s=c.prepareStatement("INSERT INTO prestamos(libro_id,estudiante_id,fecha_prestamo,fecha_vencimiento) VALUES(?,?,?,?)",Statement.RETURN_GENERATED_KEYS)){
    s.setInt(1,libroId);s.setInt(2,estudianteId);s.setDate(3,java.sql.Date.valueOf(hoy));s.setDate(4,java.sql.Date.valueOf(hoy.plusDays(14)));s.executeUpdate();
    try(ResultSet r=s.getGeneratedKeys()){r.next();return r.getInt(1);}
   }
  });
 }
 public synchronized void devolver(int id,Integer estudiantePermitido) throws SQLException {
  db.transaccion(c->{int libro,estudiante;LocalDate vencimiento;
   // El libro se bloquea antes del préstamo para mantener un orden uniforme de bloqueos.
   try(PreparedStatement s=c.prepareStatement("SELECT libro_id FROM prestamos WHERE id=?")){s.setInt(1,id);try(ResultSet r=s.executeQuery()){if(!r.next())throw new SQLException("El préstamo ya no existe.");libro=r.getInt(1);}}
   try(PreparedStatement s=c.prepareStatement("SELECT id FROM libros WHERE id=? FOR UPDATE")){s.setInt(1,libro);try(ResultSet r=s.executeQuery()){if(!r.next())throw new SQLException("El libro ya no existe.");}}
   try(PreparedStatement s=c.prepareStatement("SELECT estudiante_id,fecha_vencimiento,fecha_devolucion FROM prestamos WHERE id=? FOR UPDATE")){s.setInt(1,id);try(ResultSet r=s.executeQuery()){
    if(!r.next())throw new SQLException("El préstamo ya no existe.");estudiante=r.getInt(1);vencimiento=r.getDate(2).toLocalDate();
    if(estudiantePermitido!=null&&estudiante!=estudiantePermitido)throw new SQLException("No puede devolver préstamos de otro estudiante.");
    if(r.getDate(3)!=null)throw new SQLException("Este préstamo ya fue devuelto.");
   }}
   LocalDate hoy=LocalDate.now();
   try(PreparedStatement s=c.prepareStatement("UPDATE prestamos SET fecha_devolucion=?,atraso=? WHERE id=?")){s.setDate(1,java.sql.Date.valueOf(hoy));s.setBoolean(2,hoy.isAfter(vencimiento));s.setInt(3,id);s.executeUpdate();}
   try(PreparedStatement s=c.prepareStatement("UPDATE libros SET stock=stock+1 WHERE id=? AND stock<stock_total")){s.setInt(1,libro);if(s.executeUpdate()!=1)throw new SQLException("El stock no permite registrar la devolución.");}return null;
  });
 }
 public List<Prestamo> readAll(Integer estudianteId,boolean activos) throws SQLException {
  return db.consultar(c->{String sql="SELECT p.*,l.titulo,u.nombre FROM prestamos p JOIN libros l ON l.id=p.libro_id JOIN estudiantes e ON e.id=p.estudiante_id JOIN usuarios u ON u.id=e.usuario_id WHERE 1=1";
   if(estudianteId!=null)sql+=" AND p.estudiante_id=?";if(activos)sql+=" AND p.fecha_devolucion IS NULL";
   List<Prestamo> out=new ArrayList<>();try(PreparedStatement s=c.prepareStatement(sql+" ORDER BY p.id DESC")){if(estudianteId!=null)s.setInt(1,estudianteId);try(ResultSet r=s.executeQuery()){while(r.next()){java.sql.Date d=r.getDate("fecha_devolucion");out.add(new Prestamo(r.getInt("id"),r.getInt("libro_id"),r.getInt("estudiante_id"),r.getString("titulo"),r.getString("nombre"),r.getDate("fecha_prestamo").toLocalDate(),r.getDate("fecha_vencimiento").toLocalDate(),d==null?null:d.toLocalDate(),r.getBoolean("atraso")));}}}return out;
  });
 }
 public List<Object[]> masPrestados() throws SQLException {return db.consultar(c->{List<Object[]> out=new ArrayList<>();try(PreparedStatement s=c.prepareStatement("SELECT l.id,l.titulo,COUNT(p.id) cantidad FROM libros l LEFT JOIN prestamos p ON p.libro_id=l.id GROUP BY l.id,l.titulo ORDER BY cantidad DESC,l.titulo");ResultSet r=s.executeQuery()){while(r.next())out.add(new Object[]{r.getInt(1),r.getString(2),r.getInt(3)});}return out;});}
}
