package vista;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import java.awt.*;
import java.util.regex.Pattern;
final class Componentes {
 private Componentes() { }
 static DefaultTableModel modelo(String... columnas){return new DefaultTableModel(columnas,0){private static final long serialVersionUID=1L;public boolean isCellEditable(int r,int c){return false;}};}
 static JPanel tabla(JTable tabla){
  JPanel p=new JPanel(new BorderLayout(8,8));tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);tabla.setRowHeight(25);
  TableRowSorter<DefaultTableModel> sorter=new TableRowSorter<>((DefaultTableModel)tabla.getModel());tabla.setRowSorter(sorter);
  JTextField filtro=new JTextField(24);JPanel barra=new JPanel(new FlowLayout(FlowLayout.LEFT));barra.add(new JLabel("Buscar:"));barra.add(filtro);
  filtro.getDocument().addDocumentListener(new DocumentListener(){private void filtrar(){String s=filtro.getText().trim();sorter.setRowFilter(s.isEmpty()?null:RowFilter.regexFilter("(?i)"+Pattern.quote(s)));}public void insertUpdate(DocumentEvent e){filtrar();}public void removeUpdate(DocumentEvent e){filtrar();}public void changedUpdate(DocumentEvent e){filtrar();}});
  p.add(barra,BorderLayout.NORTH);p.add(new JScrollPane(tabla),BorderLayout.CENTER);return p;
 }
 static JButton boton(JPanel p,String texto,Runnable accion){JButton b=new JButton(texto);b.addActionListener(e->accion.run());p.add(b);return b;}
 static boolean formulario(Component owner,String titulo,Object... campos){
  JPanel p=new JPanel(new GridLayout(0,2,10,10));for(int i=0;i<campos.length;i+=2){p.add(new JLabel(campos[i].toString()));p.add((Component)campos[i+1]);}
  return JOptionPane.showConfirmDialog(owner,p,titulo,JOptionPane.OK_CANCEL_OPTION,JOptionPane.PLAIN_MESSAGE)==JOptionPane.OK_OPTION;
 }
 static void filas(DefaultTableModel modelo,java.util.List<Object[]> filas){modelo.setRowCount(0);for(Object[] fila:filas)modelo.addRow(fila);}
 static int seleccionado(JTable tabla){int fila=tabla.getSelectedRow();if(fila<0)throw new IllegalArgumentException("Seleccione una fila primero.");return tabla.convertRowIndexToModel(fila);}
 static boolean confirmar(Component owner){return JOptionPane.showConfirmDialog(owner,"¿Eliminar el registro seleccionado?","Confirmar eliminación",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION;}
}
