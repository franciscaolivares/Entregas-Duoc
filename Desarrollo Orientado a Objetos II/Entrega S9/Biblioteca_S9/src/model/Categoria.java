package model;
import interfaces.Identificable;
public final class Categoria implements Identificable {
 private final int id; private final String nombre;
 public Categoria(int id,String nombre) { this.id=id; this.nombre=nombre; }
 public int getId() { return id; } public String getNombre() { return nombre; }
 public String toString() { return nombre; }
}
