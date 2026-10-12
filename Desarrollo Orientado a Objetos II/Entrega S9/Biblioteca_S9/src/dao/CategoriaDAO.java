package dao;
import model.Categoria;
import interfaces.CRUD;
import java.sql.*;
import java.util.*;
public final class CategoriaDAO implements CRUD<Categoria> {
 private final DatabaseConnection db=DatabaseConnection.getInstance();
 public List<Categoria> readAll() throws SQLException {return db.consultar(c->{List<Categoria> out=new ArrayList<>();try(PreparedStatement s=c.prepareStatement("SELECT * FROM categorias ORDER BY nombre");ResultSet r=s.executeQuery()){while(r.next())out.add(new Categoria(r.getInt("id"),r.getString("nombre")));}return out;});}
 public int create(Categoria v) throws SQLException {return db.transaccion(c->{try(PreparedStatement s=c.prepareStatement("INSERT INTO categorias(nombre) VALUES(?)",Statement.RETURN_GENERATED_KEYS)){s.setString(1,v.getNombre());s.executeUpdate();try(ResultSet r=s.getGeneratedKeys()){r.next();return r.getInt(1);}}});}
 public void update(Categoria v) throws SQLException {db.transaccion(c->{try(PreparedStatement s=c.prepareStatement("UPDATE categorias SET nombre=? WHERE id=?")){s.setString(1,v.getNombre());s.setInt(2,v.getId());if(s.executeUpdate()!=1)throw new SQLException("La categoría ya no existe.");}return null;});}
 public void delete(int id) throws SQLException {db.transaccion(c->{try(PreparedStatement s=c.prepareStatement("DELETE FROM categorias WHERE id=?")){s.setInt(1,id);s.executeUpdate();}return null;});}
}
