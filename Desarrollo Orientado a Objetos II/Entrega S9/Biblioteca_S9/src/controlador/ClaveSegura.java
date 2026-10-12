package controlador;
import java.security.*;
import java.util.*;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
public final class ClaveSegura {
 private ClaveSegura() { }
 public static String crear(char[] clave) {
  byte[] sal=new byte[16];new SecureRandom().nextBytes(sal);
  return "120000$"+Base64.getEncoder().encodeToString(sal)+"$"+Base64.getEncoder().encodeToString(derivar(clave,sal,120000));
 }
 public static boolean verificar(char[] clave,String hash) {
  try { String[] p=hash.split("\\$");int n=Integer.parseInt(p[0]);
   return MessageDigest.isEqual(Base64.getDecoder().decode(p[2]),derivar(clave,Base64.getDecoder().decode(p[1]),n));
  }catch(RuntimeException e){return false;}
 }
 private static byte[] derivar(char[] clave,byte[] sal,int iteraciones) {
  PBEKeySpec spec=new PBEKeySpec(clave,sal,iteraciones,256);
  try{return SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256").generateSecret(spec).getEncoded();}
  catch(GeneralSecurityException e){throw new IllegalStateException("No se pudo proteger la contraseña.",e);}finally{spec.clearPassword();}
 }
}
