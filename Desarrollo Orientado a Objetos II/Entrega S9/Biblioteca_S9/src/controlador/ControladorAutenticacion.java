package controlador;
import dao.UsuarioDAO;
import model.Usuario;
import java.sql.SQLException;
import java.util.Arrays;
public final class ControladorAutenticacion {
 public Usuario ingresar(String login,char[] clave) throws SQLException {
  try {if(login.isBlank()||clave.length==0)throw new IllegalArgumentException("Ingrese usuario y contraseña.");
   UsuarioDAO dao=new UsuarioDAO();String hash=dao.hash(login.trim());
   if(hash==null||!ClaveSegura.verificar(clave,hash))throw new IllegalArgumentException("Usuario o contraseña incorrectos.");
   Usuario u=dao.buscar(login.trim());if(u==null)throw new IllegalArgumentException("La cuenta ya no existe.");return u;
  }finally{Arrays.fill(clave,'\0');}
 }
}
