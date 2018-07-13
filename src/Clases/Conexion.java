/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author roman
 */
public class Conexion {
    
    /**
     * - Metodo conexion a la base de datos
     * - Retorna conexion de tipo Connection
     * @return 
     */
    public Connection conectar(){
        String user = "root";
        String psw = "";
        String prot = "jdbc:mysql://";
        String server = "localhost";
        String puerto = "3306";
        String db = "mydb";
        String url = prot+server+":"+puerto+"/"+db;
        Connection con = null;
        System.out.println(url);
        System.out.println("jdbc:mysql://localhost:3306/mysql?zeroDateTimeBehavior=convertToNull [root on Default schema]");
        
       try {
           //jdbc:mysql://localhost:3306/mysql?zeroDateTimeBehavior=convertToNull [root on Default schema]
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(url,user,psw);
       } catch (ClassNotFoundException ex) {
           Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
           System.out.println("error de driver"+ex.toString());
            
      
       
   }   catch (SQLException ex) {
           Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       return con;
   }
    /**
     * - Metodo para desconectar de la base de datos, es necesario desconectarse despues de cada consulta
     * - Requiere pasarle la conexion a la cual se desconectara.
     * @param cnx 
     */
    public void desconectar(Connection cnx){
       try {
           cnx.close();
       } catch (SQLException ex) {
           Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
       }
    }
    
    /**
     * - Metodo para insertar,modificar, eliminar(CRUD).
     * - Requiere la conexion y la consulta en string.
     * - Retorna el total de filas modificadas.
     * @param cnx
     * @param query
     * @return 
     */
    public int actualizar(Connection cnx, String query){
       int rows = 0;
       Statement st;
       
       try {
           st = cnx.createStatement();
            rows =st.executeUpdate(query);
       } catch (SQLException ex) {
           Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
       }
       
         return rows;
   }
    
    /**
     * - Metodo para consultar,(SELECT)
     * - Requiere la conexion y consulta en String.
     * - Retorna los datos en Resultsets
     * @param cnx
     * @param query
     * @return 
     */
    public ResultSet consultar(Connection cnx, String query){
       ResultSet res = null;
       
            Statement st;
        try {
            st = cnx.createStatement();
            res = st.executeQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }                  
       return res;
   }
    
    /**
     * - Metodo para cargar datos a una tabla
     * - Requiere resultset, y jtable.
     * @param rs
     * @param t 
     */
    public void cargarTablas(ResultSet rs,JTable t, String[] columnas)
    {
     
        try {
            
            ResultSetMetaData rsm = null;
            try {
                rsm = rs.getMetaData();
            } catch (SQLException ex) {
                Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
            }
            int c=rsm.getColumnCount();
            String[] ti= {rsm.getColumnName(1),rsm.getColumnName(2),rsm.getColumnName(3),rsm.getColumnName(4)};
            rs.last();
            int f= rs.getRow();
            String[][] datos= new String[f][c];
            
            rs.beforeFirst();
            int f1=0;
            while(rs.next())
            {
                for(int x=0;x<c;x++){
                    datos[f1][x]=rs.getString(x+1);
                }
                f1++;
            }
            
            DefaultTableModel dtm= new  DefaultTableModel(datos, ti){
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false; //To change body of generated methods, choose Tools | Templates.
                }     
            };
            t.setModel(dtm);
            
            
        } catch (SQLException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }

               
    }
    
}
