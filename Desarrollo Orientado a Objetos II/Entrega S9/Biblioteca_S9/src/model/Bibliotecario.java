package model;
public final class Bibliotecario extends Usuario {
 public Bibliotecario(int id,String nombre,String login) { super(id,nombre,login); }
 public String getRol() { return "BIBLIOTECARIO"; }
 public boolean puedeGestionar() { return true; }
}
