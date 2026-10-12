package pruebas;
import dao.PrestamoDAO;
import dao.DatabaseConnection;
import java.sql.SQLException;
/** Proceso independiente para verificar los bloqueos de fila entre dos conexiones JDBC. */
public final class PruebasConcurrencia {
 public static void main(String[] args)throws Exception{
  try{new PrestamoDAO().registrar(Integer.parseInt(args[0]),Integer.parseInt(args[1]));System.out.println("PRÉSTAMO REGISTRADO");}
  catch(SQLException e){if(!e.getMessage().contains("disponibles"))throw e;System.out.println("SIN STOCK");System.exit(2);}
  finally{DatabaseConnection.getInstance().close();}
 }
}
