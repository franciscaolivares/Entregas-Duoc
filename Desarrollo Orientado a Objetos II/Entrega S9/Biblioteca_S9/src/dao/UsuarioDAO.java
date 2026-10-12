package dao;
import model.*;
import java.sql.*;
import java.util.*;
public final class UsuarioDAO {
 private final DatabaseConnection db=DatabaseConnection.getInstance();
 private static final String SELECT="SELECT u.*,e.id estudiante_id,e.rut,e.curso,e.correo FROM usuarios u LEFT JOIN estudiantes e ON e.usuario_id=u.id";
 public static Usuario map(ResultSet r) throws SQLException {
  if("BIBLIOTECARIO".equals(r.getString("rol")))return new Bibliotecario(r.getInt("id"),r.getString("nombre"),r.getString("login"));
  return new Estudiante(r.getInt("estudiante_id"),r.getInt("id"),r.getString("nombre"),r.getString("login"),r.getString("rut"),r.getString("curso"),r.getString("correo"));
 }
 public String hash(String login) throws SQLException {return db.consultar(c->{try(PreparedStatement s=c.prepareStatement("SELECT clave_hash FROM usuarios WHERE login=?")){s.setString(1,login);try(ResultSet r=s.executeQuery()){return r.next()?r.getString(1):null;}}});}
 public Usuario buscar(String login) throws SQLException {return db.consultar(c->{try(PreparedStatement s=c.prepareStatement(SELECT+" WHERE u.login=?")){s.setString(1,login);try(ResultSet r=s.executeQuery()){return r.next()?map(r):null;}}});}
 public List<Usuario> readAll() throws SQLException {return db.consultar(c->{List<Usuario> out=new ArrayList<>();try(PreparedStatement s=c.prepareStatement(SELECT+" ORDER BY u.id");ResultSet r=s.executeQuery()){while(r.next())out.add(map(r));}return out;});}
 public int create(Usuario u,String hash) throws SQLException {return db.transaccion(c->insertar(c,u,hash));}
 static int insertar(Connection c,Usuario u,String hash) throws SQLException {
  try(PreparedStatement s=c.prepareStatement("INSERT INTO usuarios(nombre,login,clave_hash,rol) VALUES(?,?,?,?)",Statement.RETURN_GENERATED_KEYS)){
   s.setString(1,u.getNombre());s.setString(2,u.getLogin());s.setString(3,hash);s.setString(4,u.getRol());s.executeUpdate();try(ResultSet r=s.getGeneratedKeys()){r.next();return r.getInt(1);}
  }
 }
 public void update(Usuario u) throws SQLException {db.transaccion(c->{actualizar(c,u,null);return null;});}
 static void actualizar(Connection c,Usuario u,String hash) throws SQLException {
  try(PreparedStatement s=c.prepareStatement("UPDATE usuarios SET nombre=?,login=?"+(hash==null?"":",clave_hash=?")+" WHERE id=?")){
   s.setString(1,u.getNombre());s.setString(2,u.getLogin());int n=3;if(hash!=null)s.setString(n++,hash);
   s.setInt(n,u instanceof Estudiante?((Estudiante)u).getUsuarioId():u.getId());if(s.executeUpdate()!=1)throw new SQLException("La cuenta ya no existe.");
  }
 }
 public void delete(int id) throws SQLException {db.transaccion(c->{try(PreparedStatement s=c.prepareStatement("DELETE FROM usuarios WHERE id=?")){s.setInt(1,id);s.executeUpdate();}return null;});}
}
