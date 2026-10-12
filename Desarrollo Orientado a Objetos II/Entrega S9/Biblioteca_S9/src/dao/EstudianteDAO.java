package dao;
import model.*;
import java.sql.*;
import java.util.*;
public final class EstudianteDAO {
 private final DatabaseConnection db=DatabaseConnection.getInstance();
 public List<Estudiante> readAll() throws SQLException {return db.consultar(c->{List<Estudiante> out=new ArrayList<>();try(PreparedStatement s=c.prepareStatement("SELECT u.*,e.id estudiante_id,e.rut,e.curso,e.correo FROM estudiantes e JOIN usuarios u ON u.id=e.usuario_id ORDER BY e.id");ResultSet r=s.executeQuery()){while(r.next())out.add((Estudiante)UsuarioDAO.map(r));}return out;});}
 public int create(Estudiante e,String hash) throws SQLException {return db.transaccion(c->{int uid=UsuarioDAO.insertar(c,e,hash);try(PreparedStatement s=c.prepareStatement("INSERT INTO estudiantes(usuario_id,rut,curso,correo) VALUES(?,?,?,?)",Statement.RETURN_GENERATED_KEYS)){s.setInt(1,uid);s.setString(2,e.getRut());s.setString(3,e.getCurso());s.setString(4,e.getCorreo());s.executeUpdate();try(ResultSet r=s.getGeneratedKeys()){r.next();return r.getInt(1);}}});}
 public void update(Estudiante e) throws SQLException {update(e,null);}
 public void update(Estudiante e,String hash) throws SQLException {db.transaccion(c->{UsuarioDAO.actualizar(c,e,hash);try(PreparedStatement s=c.prepareStatement("UPDATE estudiantes SET rut=?,curso=?,correo=? WHERE id=?")){s.setString(1,e.getRut());s.setString(2,e.getCurso());s.setString(3,e.getCorreo());s.setInt(4,e.getId());if(s.executeUpdate()!=1)throw new SQLException("El estudiante ya no existe.");}return null;});}
 public void delete(int id) throws SQLException {db.transaccion(c->{int uid;try(PreparedStatement s=c.prepareStatement("SELECT usuario_id FROM estudiantes WHERE id=? FOR UPDATE")){s.setInt(1,id);try(ResultSet r=s.executeQuery()){if(!r.next())throw new SQLException("El estudiante ya no existe.");uid=r.getInt(1);}}
  try(PreparedStatement s=c.prepareStatement("DELETE FROM estudiantes WHERE id=?")){s.setInt(1,id);s.executeUpdate();}
  try(PreparedStatement s=c.prepareStatement("DELETE FROM usuarios WHERE id=?")){s.setInt(1,uid);s.executeUpdate();}return null;});}
}
