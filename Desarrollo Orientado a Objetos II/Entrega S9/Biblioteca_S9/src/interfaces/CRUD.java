package interfaces;
import java.sql.SQLException;
import java.util.List;
public interface CRUD<T extends Identificable> {
 int create(T entidad) throws SQLException;
 List<T> readAll() throws SQLException;
 void update(T entidad) throws SQLException;
 void delete(int id) throws SQLException;
}
