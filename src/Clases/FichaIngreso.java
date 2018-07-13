/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Enum.TipoEstado;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author roman
 */
public class FichaIngreso {
    
    private Personas.Paciente pacientes;
    private Personas.Enfermero enfermero;
    private JComboBox comboEstado;
    
    // Componentes del reporte Por rut
    private JButton btnBuscar;
    private JTextField txtBuscar;
    private JTable tabla;
    
    //componentes reporte listado
    
        

    public FichaIngreso() {
      
    }
    
    public void generarReporteExamen(){
        
        String sql = "Select * from Paciente Where rut_paciente = '"+ txtBuscar.getText() +"'";
        
        ResultSet res  = new Conexion().consultar(new Conexion().conectar(), sql);
        
        String[] columnas ={"rut","apellido","genero","tipo de parto","peso","talla","fallece"};
        
        new Conexion().cargarTablas(res, tabla, columnas);
    
    }
    
    public void generarReporteLista(JTable table){
        
        String sql = " Select Paciente.rut_paciente, Persona.nombres,paciente.genero,Persona.fecha_nacimiento from Persona,Paciente WHERE Persona.Paciente_rut_paciente = Persona.Paciente_rut_paciente";
        
        ResultSet res  = new Conexion().consultar(new Conexion().conectar(), sql);
        
        String[] columnas = {"rut","nombres","genero","fecha nacimiento"};
        
        new Conexion().cargarTablas(res, table, columnas);
    }

    public FichaIngreso(JButton btnBuscar, JTextField txtBuscar, JTable tabla) {
        this.btnBuscar = btnBuscar;
        this.txtBuscar = txtBuscar;
        this.tabla = tabla;
    }
    
    

    public FichaIngreso(Personas.Paciente pacientes) {
        this.pacientes = pacientes;
    }

    public FichaIngreso(Personas.Enfermero enfermero) {
        this.enfermero = enfermero;
    }

    public FichaIngreso(Personas.Paciente pacientes, Personas.Enfermero enfermero) {
        this.pacientes = pacientes;
        this.enfermero = enfermero;
    }

    public FichaIngreso(Personas.Paciente pacientes, Personas.Enfermero enfermero, JComboBox comboEstado) {
        this.pacientes = pacientes;
        this.enfermero = enfermero;
        this.comboEstado = comboEstado;
    }
    
    
    
  
   
 
    public void insertarFichaIngreso(){
        if(pacientes.getTxtNombres().getText().length()>0){
            if(pacientes.getTxtRut().getText().length()>0){
                if(pacientes.getTxtHoraIngreso().getText().length()>0){
                    if(pacientes.getTxtObservaciones().getText().length()>0){
                       String rut = enfermero.obtenerRutEnfermero(enfermero.getComboEnfermero());

                       String sql =  "INSERT INTO ficha_paciente(hora_Inicio,observaciones,estado_ficha,paciente_rut_paciente,enfermero_rut_enfermero) "
                               + "VALUES('"+ pacientes.getTxtHoraIngreso().getText() +"','"+pacientes.getTxtObservaciones().getText()+"','"+comboEstado.getSelectedItem()+"','"+ pacientes.getTxtRut().getText() +"','"+rut+"')";
                       int res =  new Conexion().actualizar(new Conexion().conectar(), sql);
                       if(res > 0){
                           JOptionPane.showMessageDialog(comboEstado, "Datos insertados exitosamente");
                       }else{
                           JOptionPane.showMessageDialog(comboEstado, "error, no se insertaron los datos");
                       }
                       
                    }else{
                      JOptionPane.showMessageDialog(null, "Debe ingresar la observacion para continuar...");
                      pacientes.getTxtObservaciones().requestFocus();
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Debe ingresar la hora para continuar...");
                    pacientes.getTxtHoraIngreso().requestFocus();
                }                           
            }else{
                  JOptionPane.showMessageDialog(null, "Debe ingresar su rut para continuar...");
                  pacientes.getTxtRut().requestFocus();
            }
        }else{
            JOptionPane.showMessageDialog(null, "Debe ingresar su nombre para continuar");
            pacientes.getTxtNombres().requestFocus();
       }                                               
    }

    public void cargarPaciente(String rut){
        ResultSet res = new Conexion().consultar(new Conexion().conectar(), "SELECT Persona.nombres,Paciente.rut_paciente from Persona,Paciente WHERE Persona.Paciente_rut_paciente =  Paciente.rut_paciente and paciente.rut_paciente = '"+rut+"'");
    
         try {
            res.first();
            pacientes.getTxtNombres().setText(res.getString(1));
            pacientes.getTxtRut().setText(res.getString(2));
        } catch (SQLException ex) {
            Logger.getLogger(Personas.Enfermero.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void cargarComboEstadoFicha(){
           
       
            String [] estado = new String[TipoEstado.values().length];

            for(TipoEstado t : TipoEstado.values()){
                estado[t.ordinal()] = t.getEstado();
            }

             DefaultComboBoxModel dcm = new DefaultComboBoxModel(estado);
             comboEstado.setModel(dcm);
        }
    
     public void cargarPopMenu(java.awt.event.MouseEvent evt){
         pacientes.getTableFichas().setRowSelectionAllowed(true);
         pacientes.getTableFichas().setColumnSelectionAllowed(false);
         
         JPopupMenu popmenu = new JPopupMenu();
         
          if (evt.getButton() == MouseEvent.BUTTON3) {
              JMenuItem seleccionar = new JMenuItem("Seleccionar paciente");
              JMenuItem editar = new JMenuItem("Editar");
              JMenuItem eliminar = new JMenuItem("Eliminar");
              
              seleccionar.addActionListener(new ActionListener() {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                       String rut = String.valueOf(pacientes.getTableFichas().getValueAt(pacientes.getTableFichas().getSelectedRow(),0));
                       cargarPaciente(rut);
                  }
              });
              
              editar.addActionListener(new ActionListener() {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                      JOptionPane.showMessageDialog(null, "aca va el metodo editar");
                  }
              });
              
              eliminar.addActionListener(new ActionListener() {
                  @Override
                  public void actionPerformed(ActionEvent e) {
                      JOptionPane.showMessageDialog(null, "aca va el metodo eliminar");
                  }
              });
              
              popmenu.add(seleccionar);
              popmenu.addSeparator();
              popmenu.add(editar);
              popmenu.add(eliminar);
              
              pacientes.getTableFichas().setComponentPopupMenu(popmenu);
          }
        
        }
   

  /*  public void modificarEstadoFichaIngreso(){
        if(administrador.getNombre().getText().length() > 0 ){
            if(administrador.getComboBox().getSelectedIndex() > 0){
                // aca va la query
                JOptionPane.showMessageDialog(null, "Aca va el metodo cambiar estado de las fichas de ingreso");
            }else{
                JOptionPane.showMessageDialog(null, "Debe Seleccionar un estado para continuar");
                administrador.getComboBox().requestFocus();
            }
        }else{
            JOptionPane.showMessageDialog(null, "Debe seleccionar una ficha de ingreso para continuar");
        }
    }
    
    public Personas.Paciente getPacientes() {
        return pacientes;
    }

    public Personas.Administrador getAdministrador() {
        return administrador;
    }

    public Personas.Enfermero getEnfermero() {
        return enfermero;
    }

*/
    
    
    
}
