package dao;
import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.util.Properties;
/** Una instancia y una conexión; el monitor serializa su uso, incluyendo transacciones. */
public final class DatabaseConnection {
 private static final DatabaseConnection INSTANCE=new DatabaseConnection();
 private Connection connection;
 private DatabaseConnection() { }
 public static DatabaseConnection getInstance() { return INSTANCE; }
 @FunctionalInterface public interface Trabajo<T> { T ejecutar(Connection c) throws SQLException; }
 private Connection conectar() throws SQLException {
  if(connection==null||connection.isClosed()||!connection.isValid(2)) {
   Properties p=new Properties();
   Path archivo=Paths.get(System.getProperty("biblioteca.config","database.properties"));
   if(!Files.exists(archivo)) {
    try {
     Path jar=Paths.get(DatabaseConnection.class.getProtectionDomain().getCodeSource().getLocation().toURI());
     Path raiz=Files.isDirectory(jar)?jar:jar.getParent().getParent();
     archivo=raiz.resolve("database.properties");
    } catch(Exception e) { throw new SQLException("No se encontró la configuración de la base de datos.",e); }
   }
   try(InputStream in=Files.newInputStream(archivo)){p.load(in);}catch(IOException e){throw new SQLException("No se pudo leer database.properties.",e);}
   String url=System.getenv().getOrDefault("BIBLIOTECA_DB_URL",p.getProperty("db.url"));
   String user=System.getenv().getOrDefault("BIBLIOTECA_DB_USER",p.getProperty("db.user"));
   String password=System.getenv().getOrDefault("BIBLIOTECA_DB_PASSWORD",p.getProperty("db.password"));
   connection=DriverManager.getConnection(url,user,password);
  }
  return connection;
 }
 public synchronized <T> T consultar(Trabajo<T> trabajo) throws SQLException { return trabajo.ejecutar(conectar()); }
 public synchronized <T> T transaccion(Trabajo<T> trabajo) throws SQLException {
  Connection c=conectar();boolean auto=c.getAutoCommit();c.setAutoCommit(false);
  try { T resultado=trabajo.ejecutar(c);c.commit();return resultado; }
  catch(SQLException|RuntimeException e){try{c.rollback();}catch(SQLException r){e.addSuppressed(r);}throw e;}
  finally{c.setAutoCommit(auto);}
 }
 public synchronized void close() throws SQLException { if(connection!=null)connection.close(); }
}
