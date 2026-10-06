package vista;
import dao.*;
import model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.*;
import java.time.format.*;
import java.util.List;

/** Formularios CRUD. Los combos conservan objetos con sus identificadores reales. */
public class VentanaGestionCRUD extends JFrame {
 private final RepartidorDAO rd=new RepartidorDAO();
 private final PedidoDAO pd=new PedidoDAO();
 private final EntregaDAO ed=new EntregaDAO();
 private final JTextField identificador=new JTextField(8), distancia=new JTextField("1",8);
 private final JTextField nombre=new JTextField(18), direccion=new JTextField(18);
 private final JTextField fecha=new JTextField(LocalDate.now().toString(),10), hora=new JTextField("12:00",6);
 private final JComboBox<String> tipo=new JComboBox<>(new String[]{"COMIDA","ENCOMIENDA","EXPRESS"});
 private final JComboBox<String> estado=new JComboBox<>(new String[]{"PENDIENTE","EN_REPARTO","ENTREGADO"});
 private final JComboBox<Pedido> pedidos=new JComboBox<>();
 private final JComboBox<Repartidor> repartidores=new JComboBox<>();
 private final DefaultTableModel rm=modelo("ID","Nombre"), pm=modelo("ID","Dirección","Tipo","Estado","Km"), em=modelo("ID","Pedido","Repartidor","Fecha","Hora");
 private final JTable rt=new JTable(rm), pt=new JTable(pm), et=new JTable(em);
 public VentanaGestionCRUD() {
  super("SpeedFast | Gestión de pedidos — Semana 8");
  setDefaultCloseOperation(DISPOSE_ON_CLOSE); setSize(1080,620); setLocationRelativeTo(null);
  JTabbedPane tabs=new JTabbedPane();
  tabs.add("Repartidores",panel(rt,new Object[]{"Nombre",nombre},
   ()->rd.create(new Repartidor(0,texto(nombre,"Nombre"))),
   ()->rd.update(new Repartidor(id(rt),texto(nombre,"Nombre"))),()->rd.delete(id(rt)),()->{nombre.setText("");rt.clearSelection();}));
  tabs.add("Pedidos",panel(pt,new Object[]{"ID positivo (al registrar)",identificador,"Dirección",direccion,"Distancia km",distancia,"Tipo",tipo,"Estado",estado},
   ()->pd.create(pedido(nuevoId())),
   ()->pd.update(pedido(id(pt))),
   ()->pd.delete(id(pt)),()->{direccion.setText("");tipo.setSelectedIndex(0);estado.setSelectedIndex(0);pt.clearSelection();}));
  tabs.add("Entregas",panel(et,new Object[]{"Pedido",pedidos,"Repartidor",repartidores,"Fecha (AAAA-MM-DD)",fecha,"Hora (HH:mm)",hora},
   ()->ed.create(entrega(0)),()->ed.update(entrega(id(et))),()->ed.delete(id(et)),()->{et.clearSelection();fecha.setText(LocalDate.now().toString());hora.setText("12:00");}));
  add(tabs);
  rt.getSelectionModel().addListSelectionListener(e->{int r=rt.getSelectedRow();if(r>=0)nombre.setText(rt.getValueAt(r,1).toString());});
  pt.getSelectionModel().addListSelectionListener(e->{int r=pt.getSelectedRow();if(r>=0){direccion.setText(pt.getValueAt(r,1).toString());tipo.setSelectedItem(pt.getValueAt(r,2));estado.setSelectedItem(pt.getValueAt(r,3));identificador.setText(pt.getValueAt(r,0).toString());distancia.setText(pt.getValueAt(r,4).toString());}});
  et.getSelectionModel().addListSelectionListener(e->{int r=et.getSelectedRow();if(r>=0){
   elegirPedido((Integer)et.getValueAt(r,1));elegirRepartidor((Integer)et.getValueAt(r,2));
   fecha.setText(et.getValueAt(r,3).toString());hora.setText(et.getValueAt(r,4).toString());
  }});
  SwingUtilities.invokeLater(()->ejecutar(this::refrescar,false));
 }
 private static DefaultTableModel modelo(String... columnas) {
  return new DefaultTableModel(columnas,0){public boolean isCellEditable(int r,int c){return false;}};
 }
 @FunctionalInterface private interface Accion {void run() throws SQLException;}
 private JPanel panel(JTable tabla,Object[] campos,Accion crear,Accion editar,Accion borrar,Runnable limpiar) {
  JPanel p=new JPanel(new BorderLayout(8,8));p.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
  JPanel formulario=new JPanel(new GridLayout(0,2,8,8));
  for(int i=0;i<campos.length;i+=2){formulario.add(new JLabel(campos[i].toString()));formulario.add((Component)campos[i+1]);}
  JPanel superior=new JPanel(new BorderLayout(5,5));superior.add(formulario,BorderLayout.CENTER);
  JPanel busqueda=new JPanel(new FlowLayout(FlowLayout.LEFT));JTextField filtro=new JTextField(24);
  busqueda.add(new JLabel("Filtrar tabla (ID, nombre, tipo o estado):"));busqueda.add(filtro);superior.add(busqueda,BorderLayout.SOUTH);
  javax.swing.table.TableRowSorter<DefaultTableModel> sorter=new javax.swing.table.TableRowSorter<>((DefaultTableModel)tabla.getModel());tabla.setRowSorter(sorter);
  filtro.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
   private void filtrar(){String s=filtro.getText().trim();sorter.setRowFilter(s.isEmpty()?null:RowFilter.regexFilter("(?i)"+java.util.regex.Pattern.quote(s)));}
   public void insertUpdate(javax.swing.event.DocumentEvent e){filtrar();}
   public void removeUpdate(javax.swing.event.DocumentEvent e){filtrar();}
   public void changedUpdate(javax.swing.event.DocumentEvent e){filtrar();}
  });
  p.add(superior,BorderLayout.NORTH);tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);p.add(new JScrollPane(tabla),BorderLayout.CENTER);
  JPanel botones=new JPanel();
  boton(botones,"Registrar",()->ejecutar(crear,true));boton(botones,"Guardar cambios",()->ejecutar(editar,true));
  boton(botones,"Eliminar",()->{if(tabla.getSelectedRow()<0){JOptionPane.showMessageDialog(this,"Seleccione una fila primero.");return;}if(JOptionPane.showConfirmDialog(this,"¿Eliminar el registro seleccionado?","Confirmar",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION)ejecutar(borrar,true);});
  boton(botones,"Limpiar",limpiar);boton(botones,"Actualizar tablas",()->ejecutar(this::refrescar,false));
  p.add(botones,BorderLayout.SOUTH);return p;
 }
 private void boton(JPanel p,String texto,Runnable accion){JButton b=new JButton(texto);b.addActionListener(e->accion.run());p.add(b);}
 /** Se valida antes de invocar cualquier escritura SQL. */
 private String texto(JTextField f,String nombreCampo){String s=f.getText().trim();if(s.isEmpty()||s.length()>100)throw new IllegalArgumentException(nombreCampo+": ingrese entre 1 y 100 caracteres.");return s;}
 private int id(JTable t){int r=t.getSelectedRow();if(r<0)throw new IllegalArgumentException("Seleccione una fila primero.");return (Integer)t.getValueAt(r,0);}
 private Entrega entrega(int id){
  Pedido p=(Pedido)pedidos.getSelectedItem();Repartidor r=(Repartidor)repartidores.getSelectedItem();
  if(p==null||r==null)throw new IllegalArgumentException("Debe registrar y seleccionar un pedido y un repartidor.");
  try {
   LocalDate f=LocalDate.parse(fecha.getText().trim(),DateTimeFormatter.ISO_LOCAL_DATE);
   LocalTime h=LocalTime.parse(hora.getText().trim(),DateTimeFormatter.ISO_LOCAL_TIME);
   if(f.getYear()<1970||f.getYear()>2037)throw new IllegalArgumentException("La fecha debe estar entre los años 1970 y 2037 (columna TIMESTAMP de S7).");
   return new Entrega(id,p.getId(),r.getId(),f,h);
  }catch(DateTimeParseException e){throw new IllegalArgumentException("Fecha u hora inválida. Ejemplo: 2026-10-05 y 14:30.");}
 }
 private void ejecutar(Accion a,boolean escritura){
  try {a.run();if(escritura){JOptionPane.showMessageDialog(this,"Operación realizada correctamente.");refrescar();}}
  catch(IllegalArgumentException e){JOptionPane.showMessageDialog(this,e.getMessage(),"Revise los datos",JOptionPane.WARNING_MESSAGE);}
  catch(SQLException e){
   String m;
   if("23000".equals(e.getSQLState()))m="No se puede completar: hay entregas relacionadas o una referencia ya no existe. Revise las entregas y actualice las tablas.";
   else if("28000".equals(e.getSQLState()))m="Usuario o contraseña de MySQL incorrectos. Revise dao/ConexionBD.java o las variables de entorno.";
   else m="No se pudo consultar o actualizar MySQL. Revise que esté iniciado, el script SQL y dao/ConexionBD.java o las variables de entorno. Detalle: "+e.getMessage();
   JOptionPane.showMessageDialog(this,m,"Error de base de datos",JOptionPane.ERROR_MESSAGE);
  }
 }
 /** Lee primero las tres listas: una lectura fallida no deja una tabla a medio cargar. */
 private void refrescar() throws SQLException {
  List<Repartidor> rs=rd.readAll();List<Pedido> ps=pd.readAll();List<Entrega> es=ed.readAll();
  rm.setRowCount(0);pm.setRowCount(0);em.setRowCount(0);pedidos.removeAllItems();repartidores.removeAllItems();
  for(Repartidor r:rs){rm.addRow(new Object[]{r.getId(),r.getNombre()});repartidores.addItem(r);}
  for(Pedido p:ps){pm.addRow(new Object[]{p.getId(),p.getDireccionEntrega(),(p instanceof PedidoExpress ? "EXPRESS" : p.getTipoPedido().toUpperCase(java.util.Locale.ROOT)),p.getEstado().name(),p.getDistanciaKm()});pedidos.addItem(p);}
  for(Entrega e:es)em.addRow(new Object[]{e.getId(),e.getIdPedido(),e.getIdRepartidor(),e.getFecha(),e.getHora()});
 }
 private void elegirPedido(int id){for(int i=0;i<pedidos.getItemCount();i++)if(pedidos.getItemAt(i).getId()==id){pedidos.setSelectedIndex(i);return;}}
 private void elegirRepartidor(int id){for(int i=0;i<repartidores.getItemCount();i++)if(repartidores.getItemAt(i).getId()==id){repartidores.setSelectedIndex(i);return;}}

 private int nuevoId(){try{int i=Integer.parseInt(identificador.getText().trim());if(i<=0)throw new NumberFormatException();return i;}catch(NumberFormatException e){throw new IllegalArgumentException("El ID debe ser un entero positivo y no repetido.");}}
 private Pedido pedido(int id){
  double km;try{km=Double.parseDouble(distancia.getText().trim().replace(',','.'));}catch(NumberFormatException e){throw new IllegalArgumentException("Ingrese una distancia numérica.");}
  if(!Double.isFinite(km)||km<=0)throw new IllegalArgumentException("La distancia debe ser mayor que cero.");
  String d=texto(direccion,"Dirección");Pedido p;
  switch((String)tipo.getSelectedItem()){
   case "COMIDA":p=new PedidoComida(id,d,km);break;
   case "ENCOMIENDA":p=new PedidoEncomienda(id,d,km);break;
   default:p=new PedidoExpress(id,d,km);
  }
  p.setEstado((String)estado.getSelectedItem());return p;
 }
}
