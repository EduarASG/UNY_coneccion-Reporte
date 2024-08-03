
package Clases;

import java.sql.CallableStatement;
import com.toedter.calendar.JDateChooser;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;


public class CEstudiantes {
    
    // Variables creadas para establecer el id en los combo boxes
    int Id_Genero;
    int Id_Estado;
    int Id_EstadoCivil;

    // metodos de constructores
    public void establecerIdGenero (int Id_Genero) {
        this.Id_Genero = Id_Genero;
    }

    public void establecerIdEstadoCivil (int Id_EstadoCivil) {
        this.Id_EstadoCivil = Id_EstadoCivil;
    }
    public void establecerIdEstado (int Id_Estado) {
        this.Id_Estado = Id_Estado;
    }

    
    
    
    //Metodo para mostrar los estados en su respectivo ComboBox
   public void MostrarEstado (JComboBox ComboEstado) {
       //creacion del objeto para la conexion
       CConexion Conexion = new CConexion();
       
       // Creacion de la variable que tendra la consulta sql.
       String sql = "select * from Estado";
       
       // creacion del objeto Statement para poder utulizar el ResultSet
       Statement st;
       
       try {
           //Conexion del statement con la base de datos mediante el objeto Conexion
           st = Conexion.estableceConexion().createStatement();
           // Utilizacion del objeto Statement para ejecutar la consulta sql
           ResultSet rs = st.executeQuery(sql);
           // Limpieza del combo Box.
           ComboEstado.removeAllItems();
           
           // Se crea un bucle para obtener todos los nombres de los estados y luego establecerle el id
           while (rs.next()){
               String nombreEstado = rs.getString("Nombre_Estado");
               // Obtencion del id 
               this.establecerIdEstado(rs.getInt("Id_Estado"));
               
               //Agregamos el nombre del estado al combo box
               ComboEstado.addItem(nombreEstado);
               // agregamos el valor del id a cada nombre.
               ComboEstado.putClientProperty(nombreEstado, Id_Estado);
                            
           }
           
          
       } 
       catch (Exception e) {
           //En caso de que ocurra un error mostraremos e.
           JOptionPane.showMessageDialog(null, e);
       }
       // cerramos la conexion
       finally {
           Conexion.cerrarConexion();
       }
}
   
    //Repetimos los pasos anteriores para mostrar el Estado civil.
   public void MostrarEstado_Civil (JComboBox comboEstadoCivil){
        CConexion Conexion = new CConexion();
       String sql = "select * from Estado_Civil";
       Statement st;
       
        try {
            st = Conexion.estableceConexion().createStatement();
           ResultSet rs = st.executeQuery(sql);
           comboEstadoCivil.removeAllItems();
           
           while (rs.next()){
               String EstadoCivil = rs.getString("Estado_Civil");
               this.establecerIdEstadoCivil(rs.getInt("Id_EstadoCivil"));
               
               comboEstadoCivil.addItem(EstadoCivil);
               comboEstadoCivil.putClientProperty(EstadoCivil, Id_EstadoCivil);
           }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
        finally{
            Conexion.cerrarConexion();
        }
        
    }
    //Repetimos los pasos anteriores para mostrar el Genero
   public void MostrarGenero (JComboBox ComboGenero){
       CConexion conexion = new CConexion();
       String sql = "select * from Genero";
       Statement st;
       
       try {
           
           st = conexion.estableceConexion().createStatement();
           ResultSet rs = st.executeQuery(sql);
           ComboGenero.removeAllItems();
           while (rs.next()){
               
               String Genero = rs.getString("Genero");
               this.establecerIdGenero(rs.getInt("Id_Genero"));
               
               ComboGenero.addItem(Genero);
               ComboGenero.putClientProperty(Genero, Id_Genero);
               }
           
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null, e);
       }
       finally{
           conexion.cerrarConexion();
       }
       
       
   }
   
   //Creacion del metodo para agregar estudiantes
   public void AgregarEstudiantes (JTextField Nombre, JTextField Apellido,JTextField Cedula,JTextField Num_Telf, JTextField Email, JTextField Direccion, JDateChooser Fecha_Nac, JComboBox comboEstado, JComboBox comboEstado_Civil, JComboBox comboGenero){
       // Objeto conexion
       CConexion conexion = new CConexion();
       
       // Consulta para insertar los estudiantes
       String consulta = "insert into Estudiantes(Nombre_Estudiante, Apellido_Estudiante,Cedula,Num_telf,email,Direccion,Fecha_Nac,Fk_Genero,Fk_Estado,Fk_EstadoCivil) values (?,?,?,?,?,?,?,?,?,?);";
       
       
       try {
           // Creamos un objeto callableStatement para utilizar procedimientos de almacenado.
           //Lo conectamos a la base de datos mediante el objeto Conexion
           
           CallableStatement cs = conexion.estableceConexion().prepareCall(consulta);
           
           /*Hay que realizar un conteo de los valores que vamos a insertar para saber en 
           que posicion va cada uno*/
           
          cs.setString(1, Nombre.getText());
          cs.setString(2,Apellido.getText());
           
          cs.setString(3,Cedula.getText());
          cs.setString(4,Num_Telf.getText());
           
          cs.setString(5,Email.getText());
           
           cs.setString(6,Direccion.getText());
           
           /* creamos un objeto de tipo Date para optener la fecha seleccionada y mas adelante 
           pasarla a un formato sql. */
           Date fechaSelecionada = Fecha_Nac.getDate();
           //pasamos la fecha seleccionada a formato sql
           java.sql.Date fechasql = new java.sql.Date(fechaSelecionada.getTime());
            cs.setDate(7, fechasql);
           
            //para los combo boxes primero tendremos que obtener su valor en int y luego pasarlos al parametro
            Id_Estado = (int) comboEstado.getClientProperty(comboEstado.getSelectedItem());
           cs.setInt(9, Id_Estado);
           
           Id_EstadoCivil = (int) comboEstado_Civil.getClientProperty(comboEstado_Civil.getSelectedItem());
           cs.setInt(10, Id_EstadoCivil);
           
           Id_Genero = (int) comboGenero.getClientProperty(comboGenero.getSelectedItem());
           cs.setInt(8,Id_Genero);
           
           //finalmente ejecutamos el callablestatement
           cs.execute();
           
           JOptionPane.showMessageDialog(null,"Estudiante agregado correctamente");
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null, e);
       }
       finally{
            conexion.cerrarConexion();
        }
   }
   // metodo para visualizar a los estudiantes en la tabla
   public void MostrarEstudiantes(JTable tablaEstudiantes){
       
       CConexion conexion = new CConexion();
       
       //Creamos un objeto modelo para la tabla
       
       DefaultTableModel modelo = new DefaultTableModel();
       
       String sql;
       //agregamos las columnas que tiene nuestra view
       modelo.addColumn("ID");
       modelo.addColumn("Nombre");
       modelo.addColumn("Apellido");
       modelo.addColumn("Fecha_Nac");
       modelo.addColumn("Cedula");
       modelo.addColumn("Num_telf");
       modelo.addColumn("Email");
       modelo.addColumn("Direccion");
       modelo.addColumn("Genero");
       modelo.addColumn("Estado");
       modelo.addColumn("Estado Civil");
       
       // Le insertamos el modelo creado a la tabla estudiante
       tablaEstudiantes.setModel(modelo);
       
       sql = " select * from GeneralEstudiantes;";
       
       try {
           //creacion del statement y conexion a la base de datos
           Statement st = conexion.estableceConexion().createStatement();
           //creacion del resultset 
           ResultSet rs = st.executeQuery(sql);
           
           //creacion de buble para insertar los valores a cada fila
           while(rs.next()){
               //insercion 
               String id = rs.getString("Id");
               String Nombre = rs.getString("Nombre");
               String Apellido = rs.getString("Apellido");
               // se cambia el formato de la fecha a la que utilizamos 
               SimpleDateFormat sdf = new SimpleDateFormat("dd//MM//yyyy");
                java.sql.Date fechaSQL = rs.getDate("Fecha_Naca");
                String nuevaFecha = sdf.format(fechaSQL);
               
               String Cedula = rs.getString("Cedula");
               String Num_Telf = rs.getString("Num_telf");
               String Email = rs.getString("Email");
               String Direccion = rs.getString("Direccion");
               String Genero = rs.getString("Genero");
               String Estado = rs.getString("Estado");
               String Estado_Civil = rs.getString("Estado_Civil");
               
               // se agregan a una lista 
               modelo.addRow(new Object[]{id,Nombre,Apellido,nuevaFecha,Cedula,Num_Telf,Email,Direccion,Genero,Estado,Estado_Civil});
               tablaEstudiantes.setModel(modelo);
               
           }
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null, e);
       }
       finally{
           conexion.cerrarConexion();
       }
   }
   
   //Reporte Individual lo estamos haciendo mediante la cedula.
   public void ReportePorCedula(JTable tablaEstudiantes, JTextField Cedu){
       
       CConexion conexion = new CConexion();
       
       //Creamos un objeto modelo para la tabla
       
       DefaultTableModel modelo = new DefaultTableModel();
       
       String sql;
       //agregamos las columnas que tiene nuestra view
       modelo.addColumn("ID");
       modelo.addColumn("Nombre");
       modelo.addColumn("Apellido");
       modelo.addColumn("Fecha_Nac");
       modelo.addColumn("Cedula");
       modelo.addColumn("Num_telf");
       modelo.addColumn("Email");
       modelo.addColumn("Direccion");
       modelo.addColumn("Genero");
       modelo.addColumn("Estado");
       modelo.addColumn("Estado Civil");
       
       // Le insertamos el modelo creado a la tabla estudiante
       tablaEstudiantes.setModel(modelo);
       
       //Aqui esta el cambio, obtenemos la cedula para agregarla a la consulta sql
       String ced= Cedu.getText();
       sql = " select * from GeneralEstudiantes Where Cedula="+ced+";";
       
       try {
           //creacion del statement y conexion a la base de datos
           Statement st = conexion.estableceConexion().createStatement();
           //creacion del resultset 
           ResultSet rs = st.executeQuery(sql);
           
           //creacion de buble para insertar los valores a cada fila
           while(rs.next()){
               //insercion 
               String id = rs.getString("Id");
               String Nombre = rs.getString("Nombre");
               String Apellido = rs.getString("Apellido");
               // se cambia el formato de la fecha a la que utilizamos 
               SimpleDateFormat sdf = new SimpleDateFormat("dd//MM//yyyy");
                java.sql.Date fechaSQL = rs.getDate("Fecha_Naca");
                String nuevaFecha = sdf.format(fechaSQL);
               
               String Cedula = rs.getString("Cedula");
               String Num_Telf = rs.getString("Num_telf");
               String Email = rs.getString("Email");
               String Direccion = rs.getString("Direccion");
               String Genero = rs.getString("Genero");
               String Estado = rs.getString("Estado");
               String Estado_Civil = rs.getString("Estado_Civil");
               
               // se agregan a una lista 
               modelo.addRow(new Object[]{id,Nombre,Apellido,nuevaFecha,Cedula,Num_Telf,Email,Direccion,Genero,Estado,Estado_Civil});
               tablaEstudiantes.setModel(modelo);
               
           }
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null, e);
       }
       finally{
           conexion.cerrarConexion();
       }
   }
   
   public void ReportePorEstado(JTable tablaEstudiantes, JComboBox Estad){
       
       CConexion conexion = new CConexion();
       
       //Creamos un objeto modelo para la tabla
       
       DefaultTableModel modelo = new DefaultTableModel();
       
       String sql;
       //agregamos las columnas que tiene nuestra view
       modelo.addColumn("ID");
       modelo.addColumn("Nombre");
       modelo.addColumn("Apellido");
       modelo.addColumn("Fecha_Nac");
       modelo.addColumn("Cedula");
       modelo.addColumn("Num_telf");
       modelo.addColumn("Email");
       modelo.addColumn("Direccion");
       modelo.addColumn("Genero");
       modelo.addColumn("Estado");
       modelo.addColumn("Estado Civil");
       
       // Le insertamos el modelo creado a la tabla estudiante
       tablaEstudiantes.setModel(modelo);
       //obtenemos el para agregarla a la consulta sql
       String Es= Estad.getSelectedItem().toString();
       sql = "select * from GeneralEstudiantes where Estado = \""+Es+"\";";
       
       try {
           //creacion del statement y conexion a la base de datos
           Statement st = conexion.estableceConexion().createStatement();
           //creacion del resultset 
           ResultSet rs = st.executeQuery(sql);
           
           //creacion de buble para insertar los valores a cada fila
           while(rs.next()){
               //insercion 
               String id = rs.getString("Id");
               String Nombre = rs.getString("Nombre");
               String Apellido = rs.getString("Apellido");
               // se cambia el formato de la fecha a la que utilizamos 
               SimpleDateFormat sdf = new SimpleDateFormat("dd//MM//yyyy");
                java.sql.Date fechaSQL = rs.getDate("Fecha_Naca");
                String nuevaFecha = sdf.format(fechaSQL);
               
               String Cedula = rs.getString("Cedula");
               String Num_Telf = rs.getString("Num_telf");
               String Email = rs.getString("Email");
               String Direccion = rs.getString("Direccion");
               String Genero = rs.getString("Genero");
               String Estado = rs.getString("Estado");
               String Estado_Civil = rs.getString("Estado_Civil");
               
               // se agregan a una lista 
               modelo.addRow(new Object[]{id,Nombre,Apellido,nuevaFecha,Cedula,Num_Telf,Email,Direccion,Genero,Estado,Estado_Civil});
               tablaEstudiantes.setModel(modelo);
               
           }
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null, e);
       }
       finally{
           conexion.cerrarConexion();
       }
   }
   
   // metodo que realizara la seleccion del estudiante para luego poder modificarlo o eliminarlo.
   public void SeleccionarEstudiantes(JTable Estudiantes, JTextField id, JTextField Nombre, JTextField Apellido,JDateChooser Fecha_Nac, JTextField Cedula, JTextField Num_telf, JTextField email, JTextField Direccion, JComboBox Estado, JComboBox Estado_civil, JComboBox Genero ){
       int fila = Estudiantes.getSelectedRow();
       
       if(fila>=0){
           id.setText(Estudiantes.getValueAt(fila, 0).toString());
           Nombre.setText(Estudiantes.getValueAt(fila, 1).toString());
           Apellido.setText(Estudiantes.getValueAt(fila, 2).toString());
           
           String fechaString = Estudiantes.getValueAt(fila, 3).toString();
           try {
               SimpleDateFormat sdf = new SimpleDateFormat("dd//MM//yyyy");
               Date fechaDate = sdf.parse(fechaString);
               
               Fecha_Nac.setDate(fechaDate);
               
           } catch (Exception e) {
               JOptionPane.showConfirmDialog(null,e);
           }
           
           Cedula.setText(Estudiantes.getValueAt(fila, 4).toString());
           Num_telf.setText(Estudiantes.getValueAt(fila, 5).toString());
           email.setText(Estudiantes.getValueAt(fila, 6).toString());
           Direccion.setText(Estudiantes.getValueAt(fila, 7).toString());
           Estado.setSelectedItem(Estudiantes.getValueAt(fila,9));
           Estado_civil.setSelectedItem(Estudiantes.getValueAt(fila,10));
           Genero.setSelectedItem(Estudiantes.getValueAt(fila,8));
           
       }
       
       }
   //metodo de modificacion
   public void Modificar(JTextField Id,JTextField Nombre, JTextField Apellido,JTextField Cedula,JTextField Num_Telf, JTextField Email, JTextField Direccion, JDateChooser Fecha_Nac, JComboBox comboEstado, JComboBox comboEstado_Civil, JComboBox comboGenero){
       CConexion conexion = new CConexion();
       //consulta que se realizara
       String consulta = "update Estudiantes set Estudiantes.Nombre_Estudiante=?, Estudiantes.Apellido_Estudiante=?, Estudiantes.Fecha_Nac=?, Estudiantes.Cedula=?, Estudiantes.Num_telf=?, Estudiantes.email=?,Estudiantes.Direccion=?, Estudiantes.Fk_Genero=?, Estudiantes.Fk_Estado=?,Estudiantes.Fk_EstadoCivil=? where Estudiantes.Id=?;";
      
       try {
           //preparamos la consulta con la creacion de un objeto callablestatement
           CallableStatement cs = conexion.estableceConexion().prepareCall(consulta);
           
           //realizamos los mismos pasos que en el agregado de los alumnos.
           cs.setString(1, Nombre.getText());
           cs.setString(2, Apellido.getText());
           
           Date fechaselected = Fecha_Nac.getDate();
           
           java.sql.Date fechaSQL = new java.sql.Date(fechaselected.getTime());
           
           cs.setDate(3, fechaSQL);
           
           cs.setString(4, Cedula.getText());
           cs.setString(5, Num_Telf.getText());
           cs.setString(6, Email.getText());
           cs.setString(7, Direccion.getText());
           
           int idGenero = (int) comboGenero.getClientProperty(comboGenero.getSelectedItem());
           cs.setInt(8, idGenero);
           int idEstado = (int) comboEstado.getClientProperty(comboEstado.getSelectedItem());
           cs.setInt(9, idEstado);
           
           int idEstadoCivil = (int) comboEstado_Civil.getClientProperty(comboEstado_Civil.getSelectedItem());
           cs.setInt(10, idEstadoCivil);
                   
           
           cs.setInt(11, Integer.parseInt(Id.getText()));

           cs.execute();
           JOptionPane.showMessageDialog(null,"Estudiante modificado correctamente");
           
           
       } catch (Exception e) {
           JOptionPane.showMessageDialog(null, e);
       }
       finally{
           conexion.cerrarConexion();
       }
   }
 
   public void EliminarEstudiante(JTextField id){
       
       CConexion conexion = new CConexion();
       
       String consulta = "delete from Estudiantes where Id=?";
       
       try {
           CallableStatement cs = conexion.estableceConexion().prepareCall(consulta);
           
           cs.setInt(1,Integer.parseInt(id.getText()));
           cs.execute();
           JOptionPane.showMessageDialog(null,"Estudiante eliminado correctamente");
       } catch (Exception e) {
           JOptionPane.showConfirmDialog(null, e);
       }
       finally{
           conexion.cerrarConexion();
       }
           
   }
   // metodo para limpiar las celdas cada que se ejecute los metodos anteriores
   public void Limpiar(JTextField Id,JTextField Nombre, JTextField Apellido,JTextField Cedula,JTextField Num_Telf, JTextField Email, JTextField Direccion, JDateChooser Fecha_Nac){
       Id.setText("");
       Nombre.setText("");
       Apellido.setText("");
       Cedula.setText("");
       Num_Telf.setText("");
       Email.setText("");
       Direccion.setText("");
       
       Calendar calendario = Calendar.getInstance();
       
       Fecha_Nac.setDate(calendario.getTime());
             
   }
}
