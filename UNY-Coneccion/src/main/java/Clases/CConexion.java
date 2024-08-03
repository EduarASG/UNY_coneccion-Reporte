
package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;


public class CConexion {
    //creamos un objeto connection para poder realizar la conexion
    Connection conectar = null;
    // creamos variables string que almacenaran la informacion para el inicio de sesion en la BD
    String usuario="root";
    String contrasenia= "";
    
    // otras variables con la direcciones necesarias para conectarse a la base de datos.
    String bd = "UNY_conecction";
    String ip = "localhost";
    String puerto = "3306";
    
    String cadena = "jdbc:mysql://"+ip+":"+puerto+"/"+bd;
    //metodo para esstablecer la conexion
    public Connection estableceConexion(){
        
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            conectar = DriverManager.getConnection(cadena, usuario, contrasenia);
            
            //Este optionpane se utiliza las primeras veces para probar que se ha conectado bien
            //JOptionPane.showMessageDialog(null, "Se conecto satisfactoriamente");
        } catch (Exception e) {
            JOptionPane.showConfirmDialog(null, "no se pudo conectar error: "+e);
        }
        return conectar;
    
    }
    //metodo para cerrar la conexion
    public void cerrarConexion(){
        try {
            if (conectar != null && conectar.isClosed()){
                conectar.close();
               // JOptionPane.showMessageDialog(null,"Conexion cerrada");
                
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,"no se pudo onexion cerrada "+e);

            
        }
    }
}
