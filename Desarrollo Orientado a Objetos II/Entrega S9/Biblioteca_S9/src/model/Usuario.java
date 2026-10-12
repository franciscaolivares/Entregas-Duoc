package model;
public abstract class Usuario extends Persona {
 private final String login;
 protected Usuario(int id, String nombre, String login) { super(id,nombre); this.login=login; }
 public String getLogin() { return login; }
}
