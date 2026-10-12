package dao;
import model.Libro;
import interfaces.CRUD;
import java.sql.*;
import java.util.*;
public final class LibroDAO implements CRUD<Libro> {
 private final DatabaseConnection db=DatabaseConnection.getInstance();
 public List<Libro> readAll() throws SQLException {return db.consultar(c->{List<Libro> out=new ArrayList<>();try(PreparedStatement s=c.prepareStatement("SELECT l.*,c.nombre categoria FROM libros l JOIN categorias c ON c.id=l.categoria_id ORDER BY l.id");ResultSet r=s.executeQuery()){while(r.next())out.add(new Libro(r.getInt("id"),r.getString("titulo"),r.getString("autor"),r.getString("editorial"),r.getString("isbn"),r.getInt("categoria_id"),r.getString("categoria"),r.getInt("stock"),r.getInt("stock_total")));}return out;});}
 public int create(Libro l) throws SQLException {return db.transaccion(c->{try(PreparedStatement s=c.prepareStatement("INSERT INTO libros(titulo,autor,editorial,isbn,categoria_id,stock,stock_total) VALUES(?,?,?,?,?,?,?)",Statement.RETURN_GENERATED_KEYS)){campos(s,l);s.setInt(6,l.getStockTotal());s.setInt(7,l.getStockTotal());s.executeUpdate();try(ResultSet r=s.getGeneratedKeys()){r.next();return r.getInt(1);}}});}
 private void campos(PreparedStatement s,Libro l) throws SQLException {s.setString(1,l.getTitulo());s.setString(2,l.getAutor());s.setString(3,l.getEditorial());s.setString(4,l.getIsbn());s.setInt(5,l.getCategoriaId());}
 public synchronized void update(Libro l) throws SQLException {db.transaccion(c->{int disponibles,total;try(PreparedStatement s=c.prepareStatement("SELECT stock,stock_total FROM libros WHERE id=? FOR UPDATE")){s.setInt(1,l.getId());try(ResultSet r=s.executeQuery()){if(!r.next())throw new SQLException("El libro ya no existe.");disponibles=r.getInt(1);total=r.getInt(2);}}
  int nuevo=l.getStockTotal()-(total-disponibles);if(nuevo<0)throw new SQLException("El total no puede ser menor que los ejemplares prestados.");
  try(PreparedStatement s=c.prepareStatement("UPDATE libros SET titulo=?,autor=?,editorial=?,isbn=?,categoria_id=?,stock=?,stock_total=? WHERE id=?")){campos(s,l);s.setInt(6,nuevo);s.setInt(7,l.getStockTotal());s.setInt(8,l.getId());s.executeUpdate();}return null;});}
 public void delete(int id) throws SQLException {db.transaccion(c->{try(PreparedStatement s=c.prepareStatement("DELETE FROM libros WHERE id=?")){s.setInt(1,id);s.executeUpdate();}return null;});}
}
