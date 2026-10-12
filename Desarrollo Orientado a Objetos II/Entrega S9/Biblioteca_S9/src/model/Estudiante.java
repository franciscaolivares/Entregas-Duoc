package model;
public final class Estudiante extends Usuario {
 private final int usuarioId;
 private final String rut,curso,correo;
 public Estudiante(int id,int usuarioId,String nombre,String login,String rut,String curso,String correo) {
  super(id,nombre,login); this.usuarioId=usuarioId; this.rut=rut; this.curso=curso; this.correo=correo;
 }
 public int getUsuarioId() { return usuarioId; }
 public String getRut() { return rut; }
 public String getCurso() { return curso; }
 public String getCorreo() { return correo; }
 public String getRol() { return "ESTUDIANTE"; }
 public boolean puedeGestionar() { return false; }
}
