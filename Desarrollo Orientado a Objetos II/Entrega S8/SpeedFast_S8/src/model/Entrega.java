package model;
import java.time.*;
/** Amplía la entidad S7 manteniendo el constructor usado por la simulación. */
public class Entrega {
 private final int id, idPedido, idRepartidor;
 private final LocalDate fecha; private final LocalTime hora;
 public Entrega(int idPedido,int idRepartidor){this(0,idPedido,idRepartidor,LocalDate.now(),LocalTime.now().withNano(0));}
 public Entrega(int id,int idPedido,int idRepartidor,LocalDate fecha,LocalTime hora){this.id=id;this.idPedido=idPedido;this.idRepartidor=idRepartidor;this.fecha=fecha;this.hora=hora;}
 public int getId(){return id;} public int getIdPedido(){return idPedido;} public int getIdRepartidor(){return idRepartidor;}
 public LocalDate getFecha(){return fecha;} public LocalTime getHora(){return hora;}
}
