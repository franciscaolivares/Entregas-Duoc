package vista;
import controlador.ControladorAutenticacion;
import model.Usuario;
import javax.swing.*;
import java.awt.*;
import java.util.concurrent.ExecutionException;
public final class VentanaAutenticacion extends JFrame {
 private static final long serialVersionUID=1L;
 private final JTextField usuario=new JTextField(20);private final JPasswordField clave=new JPasswordField(20);
 private final JButton ingresar=new JButton("Ingresar");private final JLabel estado=new JLabel(" ");
 public VentanaAutenticacion(){
  super("Biblioteca Escolar | Acceso");setDefaultCloseOperation(EXIT_ON_CLOSE);
  JPanel p=new JPanel(new BorderLayout(16,16));p.setBorder(BorderFactory.createEmptyBorder(24,28,24,28));
  JLabel titulo=new JLabel("Biblioteca Escolar");titulo.setFont(titulo.getFont().deriveFont(Font.BOLD,23));p.add(titulo,BorderLayout.NORTH);
  JPanel campos=new JPanel(new GridLayout(0,2,12,12));campos.add(new JLabel("Usuario"));campos.add(usuario);campos.add(new JLabel("Contraseña"));campos.add(clave);p.add(campos,BorderLayout.CENTER);
  JPanel pie=new JPanel(new BorderLayout(8,8));pie.add(ingresar,BorderLayout.EAST);pie.add(estado,BorderLayout.SOUTH);p.add(pie,BorderLayout.SOUTH);add(p);
  ingresar.addActionListener(e->ingresar());getRootPane().setDefaultButton(ingresar);pack();setMinimumSize(new Dimension(460,245));setLocationRelativeTo(null);
 }
 private void ingresar(){
  String login=usuario.getText();char[] password=clave.getPassword();ingresar.setEnabled(false);estado.setText("Validando acceso…");
  new SwingWorker<Usuario,Void>(){
   protected Usuario doInBackground() throws Exception{return new ControladorAutenticacion().ingresar(login,password);}
   protected void done(){try{Usuario u=get();new VentanaPrincipal(u).setVisible(true);dispose();}
    catch(InterruptedException e){Thread.currentThread().interrupt();estado.setText("Operación interrumpida.");}
    catch(ExecutionException e){estado.setText("Acceso no completado.");VentanaPrincipal.error(VentanaAutenticacion.this,e.getCause());}
    finally{ingresar.setEnabled(true);clave.setText("");}
   }
  }.execute();
 }
}
