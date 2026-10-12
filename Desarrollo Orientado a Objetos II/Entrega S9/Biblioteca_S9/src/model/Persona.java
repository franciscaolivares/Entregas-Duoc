package model;
import interfaces.Identificable;
public abstract class Persona implements Identificable {
 private final int id;
 private final String nombre;
 protected Persona(int id, String nombre) { this.id=id; this.nombre=nombre; }
 public int getId() { return id; }
 public String getNombre() { return nombre; }
 public abstract String getRol();
 public abstract boolean puedeGestionar();
 public String toString() { return nombre + " (" + id + ")"; }
}
