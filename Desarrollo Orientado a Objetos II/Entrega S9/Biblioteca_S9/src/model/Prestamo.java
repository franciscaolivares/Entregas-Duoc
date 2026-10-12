package model;
import interfaces.Identificable;
import java.time.LocalDate;
public final class Prestamo implements Identificable {
 private final int id,libroId,estudianteId; private final String libro,estudiante;
 private final LocalDate fecha,vencimiento,devolucion; private final boolean atraso;
 public Prestamo(int id,int libroId,int estudianteId,String libro,String estudiante,LocalDate fecha,LocalDate vencimiento,LocalDate devolucion,boolean atraso) {
  this.id=id;this.libroId=libroId;this.estudianteId=estudianteId;this.libro=libro;this.estudiante=estudiante;this.fecha=fecha;this.vencimiento=vencimiento;this.devolucion=devolucion;this.atraso=atraso;
 }
 public int getId(){return id;} public int getLibroId(){return libroId;} public int getEstudianteId(){return estudianteId;}
 public String getLibro(){return libro;} public String getEstudiante(){return estudiante;}
 public LocalDate getFecha(){return fecha;} public LocalDate getVencimiento(){return vencimiento;} public LocalDate getDevolucion(){return devolucion;}
 public boolean isAtrasado(){return atraso||(devolucion==null&&LocalDate.now().isAfter(vencimiento));}
 public String getEstado(){return devolucion==null?(isAtrasado()?"VENCIDO":"EN PRÉSTAMO"):(isAtrasado()?"DEVUELTO CON ATRASO":"DEVUELTO");}
}
