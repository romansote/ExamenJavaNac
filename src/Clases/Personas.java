/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Clases;

import Enum.TipoParto;
import Gui.FormLogin;
import Gui.FormPrincipal;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author roman
 */
public class Personas {
    
    //login form
    private JTextField txtUser;
    private JTextField txtPass;
    private JButton btnAcceder;
    private JButton btnLimpiar;
    FormLogin formLogin;
    FormPrincipal  formPrincipal;
    
    // objeto conexion
    private Conexion conexion;
    
    //personas
    private JTextField txtNombres;
    private JTextField txtRut;
    private JTextField txtFechaNac;
    
    //user login temporal en segunda entrega se creara con base de datos
    public static final String USUARIO = "asd"; 
    public static final String PASSWORD = "asd123";

    public Personas() {
        
    }
    
    // constructor para el login
    public Personas(JTextField txtUser, JTextField txtPass, JButton btnAcceder, JButton btnLimpiar) {
        this.txtUser = txtUser;
        this.txtPass = txtPass;
        this.btnAcceder = btnAcceder;
        this.btnLimpiar = btnLimpiar;
    }

    //constructor principal de persona(atributos en comun de todas las clases)
    public Personas(JTextField txtNombres, JTextField txtRut, JTextField txtFechaNac) {
        this.txtNombres = txtNombres;
        this.txtRut = txtRut;
        this.txtFechaNac = txtFechaNac;
        conexion = new Conexion();
    }  
    
    // constructor ficha ingreso
    public Personas(JTextField txtNombres, JTextField txtRut) {
        this.txtNombres = txtNombres;
        this.txtRut = txtRut;
    }

    public Conexion getConexion() {
        return conexion;
    }
    
 
    public void login(JFrame frame){
        formPrincipal= new FormPrincipal();
        
        if(txtUser.getText().equals(USUARIO)){
            if(txtPass.getText().equals(PASSWORD)){
                formPrincipal.setVisible(true);
                frame.setVisible(false);                  
            }else{
                JOptionPane.showMessageDialog(btnAcceder, "contraseña incorrecta");
                txtPass.setText("");
                txtPass.requestFocus();
            }
        }else{
            JOptionPane.showMessageDialog(btnAcceder, "Usuario incorrecto");
            txtUser.setText("");
            txtUser.requestFocus();
        }
    }

    public JTextField getTxtNombres() {
        return txtNombres;
    }

    public JTextField getTxtRut() {
        return txtRut;
    }

    public JTextField getTxtFechaNac() {
        return txtFechaNac;
    }

    

    /***************************************************************************************************/
    public static class Paciente extends Personas{
        private JRadioButton rbFalleceSi;
        private JRadioButton rbFalleceNo;
        private JTextField txtPeso;
        private JTextField txtTalla;
        private JComboBox comboTipoParto;
        private JTextField txtHoraIngreso;
        private JTextArea txtObservaciones;
        private JButton btnGuardarFicha;
        private JButton btnLimpiarFicha;
        private JButton btnModificar;
        private JTable tableFichas;
        private JTextField txtApellidoP;
        private JTextField txtApellidoM;
        private JRadioButton rbHombre;
        private JRadioButton rbMujer;

        public Paciente() {
            
        }

        public Paciente(JRadioButton rbFalleceSi, JRadioButton rbFalleceNo, JTextField txtPeso, JTextField txtTalla, JComboBox comboTipoParto, JButton btnGuardarFicha, JButton btnLimpiarFicha, JButton btnModificar, JTable tableFichas, JTextField txtApellidoP, JTextField txtApellidoM, JRadioButton rbHombre, JRadioButton rbMujer, JTextField txtNombres, JTextField txtRut, JTextField txtFechaNac) {
            super(txtNombres, txtRut, txtFechaNac);
            this.rbFalleceSi = rbFalleceSi;
            this.rbFalleceNo = rbFalleceNo;
            this.txtPeso = txtPeso;
            this.txtTalla = txtTalla;
            this.comboTipoParto = comboTipoParto;
            this.btnGuardarFicha = btnGuardarFicha;
            this.btnLimpiarFicha = btnLimpiarFicha;
            this.btnModificar =  btnModificar;
            this.tableFichas = tableFichas;
            this.txtApellidoP = txtApellidoP;
            this.txtApellidoM = txtApellidoM;
            this.rbHombre = rbHombre;
            this.rbMujer = rbMujer;
        }

        public Paciente(JTextField txtHoraIngreso, JTextArea txtObservaciones, JButton btnGuardarFicha, JTable tableFichas, JTextField txtNombres, JTextField txtRut) {
            super(txtNombres, txtRut);
            this.txtHoraIngreso = txtHoraIngreso;
            this.txtObservaciones = txtObservaciones;
            this.btnGuardarFicha = btnGuardarFicha;
            this.tableFichas = tableFichas;
        }

        public Paciente(JTextField txtHoraIngreso, JTextArea txtObservaciones, JButton btnGuardarFicha, JTable tableFichas) {
            this.txtHoraIngreso = txtHoraIngreso;
            this.txtObservaciones = txtObservaciones;
            this.btnGuardarFicha = btnGuardarFicha;
            this.tableFichas = tableFichas;
        }
        
        
        
        public void cargarTipoParto(){

            String [] parto = new String[TipoParto.values().length];

            for(TipoParto t : TipoParto.values()){
                parto[t.ordinal()] = t.getTipoParto();
            }

            DefaultComboBoxModel dcm = new DefaultComboBoxModel(parto);
            getComboTipoParto().setModel(dcm);

        }
        
        public void cargarPopMenu(java.awt.event.MouseEvent evt){
            getTableFichas().setRowSelectionAllowed(true);
            getTableFichas().setColumnSelectionAllowed(false);

            JPopupMenu popmenu = new JPopupMenu();

            if (evt.getButton() == MouseEvent.BUTTON3) {
                JMenuItem editar = new JMenuItem("Editar");
                JMenuItem eliminar = new JMenuItem("eliminar");

                editar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        btnGuardarFicha.setEnabled(false);
                        btnModificar.setEnabled(true);
                        getTxtRut().setEditable(false);

                        String rut = String.valueOf(tableFichas.getValueAt(tableFichas.getSelectedRow(),0));  
                        cargarCamposform(rut);
                    }
                });

                eliminar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                      String rut = String.valueOf(tableFichas.getValueAt(tableFichas.getSelectedRow(),0));  
                        eliminarPaciente(rut);
                    }
                });

                popmenu.add(editar);
                popmenu.addSeparator();
                popmenu.add(eliminar);

                getTableFichas().setComponentPopupMenu(popmenu);
            }

        }
        
        public void insertarPaciente(){
        String genero ="";
        String fallece ="";
        if(getTxtNombres().getText().length()>0){
            if(getTxtApellidoP().getText().length()>0){
                if(getTxtApellidoM().getText().length()>0){
                  //  if(rbHombre.isSelected() || rbMujer.isSelected()){
                        if(getTxtPeso().getText().length()>0){
                            if(getRbFalleceSi().isSelected() || getRbFalleceNo().isSelected()){
                                if(getTxtRut().getText().length()>0){
                                    if(getTxtFechaNac().getText().length()>0){
                                        if(getTxtTalla().getText().length()>0){
                                            if(getComboTipoParto().getSelectedIndex()>0){
                                               if(getRbFalleceSi().isSelected()){fallece = "si";}
                                                    else if(getRbFalleceNo().isSelected()){fallece = "no";}
                                                        if(verificarExistenciaRut(getTxtRut().getText()) == false){
                                                            String sql1 = "INSERT INTO Enfermero(rut_enfermero,nacionalida,estudios,es_admin) VALUES ('"+getTxtRut().getText()+"','','',0)";
                                                            String sql3 = "INSERT INTO Persona (rut,nombres,fecha_nacimiento,Paciente_rut_paciente,Enfermero_rut_enfermero) "
                                                                    + "VALUES('"+getTxtRut().getText()+"','"+getTxtNombres().getText()+"','"+getTxtFechaNac().getText()+"','"+getTxtRut().getText()+"','"+getTxtRut().getText()+"')";
                                                            String sql2 = "INSERT INTO Paciente(rut_paciente,apellido_paterno,genero,tipo_parto,peso,talla,fallece) "
                                                                  + "VALUES('"+ getTxtRut().getText() +"','"+ getTxtApellidoP().getText() +"','Hombre','"+getComboTipoParto().getSelectedItem()+"',"+ getTxtPeso().getText() +","+ getTxtTalla().getText() +",'"+fallece +"')";

                                                            int res1 =   getConexion().actualizar(getConexion().conectar(), sql1);
                                                            int res2 =   getConexion().actualizar(getConexion().conectar(), sql2);
                                                            int res3 =   getConexion().actualizar(getConexion().conectar(), sql3);
                                                           
                                                            if(res1 >0 && res2 >0 && res3 >0){
                                                                JOptionPane.showMessageDialog(formLogin, "Datos insertados exitosamente");
                                                            }else{
                                                                JOptionPane.showMessageDialog(formLogin, "Error, no se insertaron los datos");
                                                            }
                                                        }else{
                                                            JOptionPane.showMessageDialog(formLogin, "Este rut ya existe");
                                                        }
                                                    
                                                    }else{
                                                         JOptionPane.showMessageDialog(null, "Debe ingresar tipo de parto para continuar...");
                                                         getComboTipoParto().requestFocus();
                                                    }
                                                }else{
                                                    JOptionPane.showMessageDialog(null, "Debe ingresar su apellido materno para continuar...");
                                                    getTxtApellidoM().requestFocus();
                                                }
                                            }else{
                                                 JOptionPane.showMessageDialog(null, "Debe ingresar su fecha nacimiento para continuar...");
                                                 getTxtFechaNac().requestFocus();
                                            }
                                        }else{
                                              JOptionPane.showMessageDialog(null, "Debe ingresar su rut para continuar...");
                                              getTxtRut().requestFocus();
                                        }
                                    }else{
                                       JOptionPane.showMessageDialog(null, "Debe ingresar su estado para continuar...");

                                    }
                                }else{
                                    JOptionPane.showMessageDialog(null, "Debe ingresar su peso para continuar...");
                                    getTxtPeso().requestFocus();
                                }
                        }else{
                            JOptionPane.showMessageDialog(null, "Debe ingresar su apellido materno para continuar...");
                            getTxtApellidoM().requestFocus();
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Debe ingresar su apellido paterno para continuar...");
                        getTxtApellidoP().requestFocus();
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Debe ingresar su nombre para continuar");
                    getTxtNombres().requestFocus();
               }                                               
        }
        
        public void cargarCamposform(String rut){
        
            ResultSet res = getConexion().consultar(getConexion().conectar(), "Select Paciente.rut_paciente,Persona.nombres, Paciente.apellido_paterno, Persona.fecha_nacimiento, Paciente.genero, Paciente.tipo_parto, Paciente.peso,Paciente.talla "
                        + "FROM persona INNER JOIN Paciente on Persona.Paciente_rut_paciente = '"+String.valueOf(rut)+"'");

             try {
                res.first();
                    getTxtRut().setText(res.getString(1));
                    getTxtNombres().setText(res.getString(2));
                    getTxtFechaNac().setText(res.getString(4));
                    getTxtApellidoP().setText(res.getString(3));
                    getTxtPeso().setText(res.getString(7));
                    getTxtTalla().setText(res.getString(8));

            } catch (SQLException ex) {
                Logger.getLogger(Personas.Enfermero.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        public void editarPaciente(String rut){
        String fallece ="";
        if(getTxtNombres().getText().length()>0){
            if(getTxtApellidoP().getText().length()>0){
                if(getTxtApellidoM().getText().length()>0){
                  //  if(rbHombre.isSelected() || rbMujer.isSelected()){
                        if(getTxtPeso().getText().length()>0){
                            if(getRbFalleceSi().isSelected() || getRbFalleceNo().isSelected()){
                               // if(getTxtRut().getText().length()>0){
                                    if(getTxtFechaNac().getText().length()>0){
                                        if(getTxtTalla().getText().length()>0){
                                            if(getComboTipoParto().getSelectedIndex()>0){
                                               if(getRbFalleceSi().isSelected()){fallece = "si";}
                                                    else if(getRbFalleceNo().isSelected()){fallece = "no";}
                                                        
                                                            String sql1 = "UPDATE Paciente pa INNER JOIN Persona pe ON pe.Paciente_rut_paciente = pa.rut_paciente "
                                                                  + "SET pe.nombres='"+ getTxtNombres().getText() +"',pe.fecha_nacimiento='"+ getTxtFechaNac().getText() +"',pa.apellido_paterno='"+getTxtApellidoP().getText()+"',pa.tipo_parto='"+getComboTipoParto().getSelectedItem()+"',pa.peso = "+getTxtPeso().getText()+",pa.talla="+getTxtTalla().getText()+",pa.fallece='"+fallece+"' WHERE pa.rut_paciente = '"+rut+"' and pe.Paciente_rut_paciente = '"+rut+"'";      
                                                            int res =   getConexion().actualizar(getConexion().conectar(), sql1);
                                                            System.out.println(sql1);
                                                            if(res >0 ){
                                                                JOptionPane.showMessageDialog(formLogin, "Datos insertados exitosamente");
                                                            }else{
                                                                JOptionPane.showMessageDialog(formLogin, "Error, no se insertaron los datos");
                                                            }
                                                       
                                                    }else{
                                                         JOptionPane.showMessageDialog(null, "Debe ingresar tipo de parto para continuar...");
                                                         getComboTipoParto().requestFocus();
                                                    } 
                                                }else{
                                                    JOptionPane.showMessageDialog(null, "Debe ingresar su apellido materno para continuar...");
                                                    getTxtApellidoM().requestFocus();
                                                }
                                            }else{
                                                 JOptionPane.showMessageDialog(null, "Debe ingresar su fecha nacimiento para continuar...");
                                                 getTxtFechaNac().requestFocus();
                                            }
                                    }else{
                                       JOptionPane.showMessageDialog(null, "Debe ingresar su estado para continuar...");

                                    }
                                }else{
                                    JOptionPane.showMessageDialog(null, "Debe ingresar su peso para continuar...");
                                    getTxtPeso().requestFocus();
                                }
                        }else{
                            JOptionPane.showMessageDialog(null, "Debe ingresar su apellido materno para continuar...");
                            getTxtApellidoM().requestFocus();
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Debe ingresar su apellido paterno para continuar...");
                        getTxtApellidoP().requestFocus();
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Debe ingresar su nombre para continuar");
                    getTxtNombres().requestFocus();
               }                                               
        }
        
        public boolean verificarExistenciaRut(String rut){
            boolean existencia = true;
            try {
                String query = "SELECT * FROM Paciente WHERE rut_paciente = '"+rut+"'";
                
                ResultSet res =  new Conexion().consultar(new Conexion().conectar(),query);
                
                res.last();
                
                int datos =res.getRow();
                
                if(datos > 0){
                    existencia = true;
                }else{
                    existencia = false;
                }            
            } catch (SQLException ex) {
                Logger.getLogger(Personas.Enfermero.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            return existencia;
        }
        
        
        public void eliminarPaciente(String rut){
            
            String sql = "DELETE FROM Paciente WHERE rut_paciente = '"+rut+"'";
            
            int res = getConexion().actualizar(getConexion().conectar(),sql);
            
            if(res > 0 ){
                JOptionPane.showMessageDialog(formLogin,"Paciente eliminado exitosamente");
            }else{
                 JOptionPane.showMessageDialog(formLogin,"Error, no se elimino el paciente");
            }
        }

        public Paciente(JRadioButton rbFalleceSi, JRadioButton rbFalleceNo, JTextField txtPeso, JTextField txtTalla, JComboBox comboTipoParto, JTextField txtHoraIngreso, JTextArea txtObservaciones, JButton btnGuardarFicha, JButton btnLimpiarFicha, JTable tableFichas, JTextField txtApellidoP, JTextField txtApellidoM, JRadioButton rbHombre, JRadioButton rbMujer, JTextField txtNombres, JTextField txtRut, JTextField txtFechaNac) {
            // construcor heredado de la clase principal(personas)
            super(txtNombres, txtRut, txtFechaNac);
            
            // componentes propios de la clase paciente
            this.rbFalleceSi = rbFalleceSi;
            this.rbFalleceNo = rbFalleceNo;
            this.txtPeso = txtPeso;
            this.txtTalla = txtTalla;
            this.comboTipoParto = comboTipoParto;
            this.txtHoraIngreso = txtHoraIngreso;
            this.txtObservaciones = txtObservaciones;
            this.btnGuardarFicha = btnGuardarFicha;
            this.btnLimpiarFicha = btnLimpiarFicha;
            this.tableFichas = tableFichas;
            this.txtApellidoP = txtApellidoP;
            this.txtApellidoM = txtApellidoM;
            this.rbHombre = rbHombre;
            this.rbMujer = rbMujer;
        }
        
        public void cargarTablaPacientes(JTable table){
            String[] tituloPacientes= {"Rut","nombre","Apellido","fecha nacimiento","Genero","tipo de parto","Peso","talla"};

            ResultSet rs = new Conexion().consultar(new Conexion().conectar(), "Select Paciente.rut_paciente,Persona.nombres, Paciente.apellido_paterno, Persona.fecha_nacimiento, Paciente.genero, Paciente.tipo_parto, Paciente.peso,Paciente.talla "
                    + "FROM persona INNER JOIN Paciente on Persona.Paciente_rut_paciente = Paciente.rut_paciente");

            new Conexion().cargarTablas(rs,table,tituloPacientes);
        }
        
        public JButton getBtnModificar() {
            return btnModificar;
        }
        
        public JRadioButton getRbFalleceSi() {
            return rbFalleceSi;
        }

        public JRadioButton getRbFalleceNo() {
            return rbFalleceNo;
        }

        public JTextField getTxtPeso() {
            return txtPeso;
        }

        public JTextField getTxtTalla() {
            return txtTalla;
        }

        public JComboBox getComboTipoParto() {
            return comboTipoParto;
        }

        public JTextField getTxtHoraIngreso() {
            return txtHoraIngreso;
        }

        public JTextArea getTxtObservaciones() {
            return txtObservaciones;
        }

        public JButton getBtnGuardarFicha() {
            return btnGuardarFicha;
        }

        public JButton getBtnLimpiarFicha() {
            return btnLimpiarFicha;
        }

        public JTable getTableFichas() {
            return tableFichas;
        }

        public JTextField getTxtApellidoP() {
            return txtApellidoP;
        }

        public JTextField getTxtApellidoM() {
            return txtApellidoM;
        }

        public JRadioButton getRbHombre() {
            return rbHombre;
        }

        public JRadioButton getRbMujer() {
            return rbMujer;
        }
        
    }
    
    

    /***************************************************************************************************/
    public static class Enfermero extends Personas{
        private JTextField txtNacionalidad;
        private JTextField txtInstitutoEgresado;
        private JTable tablaEnfermeros;
        private JButton btnAgregar;
        private JButton btnModificar;
        private JComboBox comboEnfermero;
        Conexion conexion = new Conexion(); 

        public Enfermero() {
            
        }

        public Enfermero(JComboBox comboEnfermero) {
            this.comboEnfermero = comboEnfermero;
        }
 
        
        

        public Enfermero(JTextField txtNacionalidad, JTextField txtInstitutoEgresado, JTable tablaEnfermeros, JTextField txtNombres, JTextField txtRut, JTextField txtFechaNac, JButton btnAgregar, JButton btnModificar) {
             // construcor heredado de la clase principal(personas)
            super(txtNombres, txtRut, txtFechaNac);
            this.txtNacionalidad = txtNacionalidad;
            this.txtInstitutoEgresado = txtInstitutoEgresado;
            this.tablaEnfermeros = tablaEnfermeros;
            this.btnAgregar = btnAgregar;
            this.btnModificar = btnModificar;
        }

        public void limpiarCampos(){
             getTxtNombres().setText("");
             getTxtRut().setText("");
             getTxtFechaNac().setText("");
             this.txtInstitutoEgresado.setText("");
             this.txtInstitutoEgresado.setText("");
        }
        
        public boolean verificarExistenciaRut(String rut){
        boolean existencia = true;
        try {
            String query = "Select * from Enfermero where rut_enfermero = '"+rut+"'";
            ResultSet res =  conexion.consultar(conexion.conectar(),query);
            res.last();
            int datos =res.getRow();
            if(datos > 0){
                existencia = true;
            }else{
                existencia = false;
            }            
        } catch (SQLException ex) {
            Logger.getLogger(Personas.Enfermero.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existencia;
        }
        
        public void insertarEnfermero(){
            if(getTxtNombres().getText().length()>0){
                if(getTxtRut().getText().length()>0){
                    if(getTxtFechaNac().getText().length()>0){
                        if(getTxtNacionalidad().getText().length()>0){
                            if(getTxtInstitutoEgresado().getText().length()>0){
                                if(verificarExistenciaRut(getTxtRut().getText()) == false){
                                  
                                    String sql1 = "INSERT INTO Paciente(rut_paciente,apellido_paterno,genero,tipo_parto,peso,talla,fallece) VALUES('"+ getTxtRut().getText() +"','','','',0,0,'')";
                                    
                                    String sql2 = "INSERT INTO Enfermero(rut_enfermero,nacionalida,estudios,es_admin) VALUES ('"+getTxtRut().getText()+"','"+getTxtNacionalidad().getText()+"','"+getTxtInstitutoEgresado().getText()+"',0)";
                                    
                                    String sql3 = "INSERT INTO Persona (rut,nombres,fecha_nacimiento,Paciente_rut_paciente,Enfermero_rut_enfermero) VALUES('"+getTxtRut().getText()+"','"+getTxtNombres().getText()+"','"+getTxtFechaNac().getText()+"','"+getTxtRut().getText()+"','"+getTxtRut().getText()+"')";
                                    
                                    int res1 = conexion.actualizar(conexion.conectar(), sql1);
                                    int res2 = conexion.actualizar(conexion.conectar(), sql2);
                                    int res3 = conexion.actualizar(conexion.conectar(), sql3);

                                    if(res1>0 && res2>0 && res3>0){
                                        JOptionPane.showMessageDialog(getTxtInstitutoEgresado(), "Datos insertados exitosamente");
                                    }else{
                                        JOptionPane.showMessageDialog(getTxtInstitutoEgresado(), "Error, datos no insertados");
                                    }
                                }else{
                                   JOptionPane.showMessageDialog(getTxtInstitutoEgresado(), "Este rut ya existe");
                                   getTxtRut().requestFocus();
                                }
                              
                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "Debe ingresar la nacionalidad para continuar");
                            getTxtNacionalidad().requestFocus();
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Debe ingresar la fecha de nacimiento para continuar");
                        getTxtFechaNac().requestFocus();
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Debe ingresar el rut para continuar");
                    getTxtRut().requestFocus();
                }
            }else{
                JOptionPane.showMessageDialog(null, "Debe ingresar el nombre para continuar");
                getTxtNombres().requestFocus();
            }
        }
        
        public String obtenerRutEnfermero(JComboBox combo){
           
            String resultado = "";
            
            String cadena = (String) combo.getSelectedItem();
            
            int pos =cadena.indexOf(":");
            
            String res = cadena.substring(0, pos);
            
            System.out.println(res);
            
            resultado = String.valueOf(res.replace(" ",""));
            return resultado;
         }
        
        public void cargarComboEnfermeros(JComboBox combobx){
     
            DefaultComboBoxModel value;
        
            try {
                ResultSet row = new Conexion().consultar(new Conexion().conectar(), "Select Persona.nombres, Enfermero.rut_enfermero FROM Persona,Enfermero WHERE Persona.Enfermero_rut_enfermero = Enfermero.rut_enfermero");

                ResultSetMetaData rsm = null;
                try {
                    row.first();
                    rsm = row.getMetaData();
                } catch (SQLException ex) {
                    Logger.getLogger(Personas.Paciente.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                row.last();
                
                int col = row.getRow();
                
                System.out.println("columnas"+col);
                
                String[] libros = new String[col+2];
                
                System.out.println("largo libros = "+libros.length);
                
                row.beforeFirst();
           
                int contador = 1;
                
                libros[0] = "Seleccione";
            
                while(row.next()){
                    System.out.println("contador = "+contador);
                    
                    libros[contador]= row.getString(2)+" : "+row.getString(1);
                    
                    contador += 1;
                }
            
              DefaultComboBoxModel dcm = new DefaultComboBoxModel(libros);
              combobx.setModel(dcm);
              
           } catch (SQLException ex) {
            Logger.getLogger(Personas.Paciente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        
        
        public void cargarCamposform(String rut){
        
            ResultSet res = conexion.consultar(conexion.conectar(), "SELECT Enfermero.rut_enfermero,Persona.nombres,Persona.fecha_nacimiento,Enfermero.nacionalida,Enfermero.estudios From Persona,Enfermero where Persona.Enfermero_rut_enfermero = '"+String.valueOf(rut)+"'");

             try {
                res.first();
                    getTxtRut().setText(res.getString(1));
                    getTxtNombres().setText(res.getString(2));
                    getTxtFechaNac().setText(res.getString(3));
                    getTxtNacionalidad().setText(res.getString(4));
                    getTxtInstitutoEgresado().setText(res.getString(5));

            } catch (SQLException ex) {
                Logger.getLogger(Personas.Enfermero.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        public void editarEnfermero(String rut){
            if(getTxtNombres().getText().length()>0){
                if(getTxtRut().getText().length()>0){
                    if(getTxtFechaNac().getText().length()>0){
                        if(getTxtNacionalidad().getText().length()>0){
                            if(getTxtInstitutoEgresado().getText().length()>0){
                               
                                    String sql1 = "UPDATE Persona p INNER JOIN Enfermero e ON e.rut_enfermero = p.Enfermero_rut_enfermero "
                                            + "SET p.nombres='"+ getTxtNombres().getText() +"',p.fecha_nacimiento='"+ getTxtFechaNac().getText() +"',e.nacionalida='"+ getTxtNacionalidad().getText() +"',e.estudios='"+ getTxtInstitutoEgresado().getText() +"' WHERE p.Enfermero_rut_enfermero = '"+String.valueOf(rut)+"' and e.rut_enfermero = '"+String.valueOf(rut)+"'";

                                    int res1 = conexion.actualizar(conexion.conectar(), sql1);
                                    
                                    if(res1>0){
                                        JOptionPane.showMessageDialog(getTxtInstitutoEgresado(), "Datos actualizados exitosamente");
                                    }else{
                                        JOptionPane.showMessageDialog(getTxtInstitutoEgresado(), "Error, datos no insertados");
                                    }
                            }
                        }else{
                            JOptionPane.showMessageDialog(null, "Debe ingresar la nacionalidad para continuar");
                            getTxtNacionalidad().requestFocus();
                        }
                    }else{
                        JOptionPane.showMessageDialog(null, "Debe ingresar la fecha de nacimiento para continuar");
                        getTxtFechaNac().requestFocus();
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "Debe ingresar el rut para continuar");
                    getTxtRut().requestFocus();
                }
            }else{
                JOptionPane.showMessageDialog(null, "Debe ingresar el nombre para continuar");
                getTxtNombres().requestFocus();
            }
        }
       
        public void cargarTablaEnfermeros(JTable table){
            String[] tituloEnfermero= {"rut","nombre","fecha nacimiento","nacionalidad","estudio"};

            ResultSet rs = conexion.consultar(conexion.conectar(), "SELECT Enfermero.rut_enfermero,Persona.nombres,Persona.fecha_nacimiento,Enfermero.nacionalida,Enfermero.estudios From Persona,Enfermero where Persona.Enfermero_rut_enfermero = Enfermero.rut_enfermero");

            conexion.cargarTablas(rs,table,tituloEnfermero);
        }
        
        /**
         * 
         * @param rut 
         */
        public void eliminarEnfermero(String rut){
            String sql = "delete from Enfermero where rut_enfermero = '"+rut+"'";

            int res =  conexion.actualizar(conexion.conectar(), sql);

            if(res > 0){
              JOptionPane.showMessageDialog(formLogin,"Enfermero eliminado exitosamente");
            }else{
              JOptionPane.showMessageDialog(formLogin,"No se pudo eliminar el enfermero"); 
            }
        } 
         
        /**
         * 
         * @param evt 
         */
        public void cargarPopMenu(java.awt.event.MouseEvent evt){
            tablaEnfermeros.setRowSelectionAllowed(true);
            tablaEnfermeros.setColumnSelectionAllowed(false);

            JPopupMenu popmenu = new JPopupMenu();

            if (evt.getButton() == MouseEvent.BUTTON3) {
                JMenuItem editar = new JMenuItem("Editar");
                JMenuItem eliminar = new JMenuItem("eliminar");

                editar.addActionListener(new ActionListener() {
                   @Override
                   public void actionPerformed(ActionEvent e) {
                       String rut = String.valueOf(tablaEnfermeros.getValueAt(tablaEnfermeros.getSelectedRow(),0));

                       btnAgregar.setEnabled(false);

                       btnModificar.setEnabled(true);

                       getTxtRut().setEditable(false);
                       System.out.println(rut);
                       cargarCamposform(rut);
                   }
                });

            eliminar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String rut = String.valueOf(tablaEnfermeros.getValueAt(tablaEnfermeros.getSelectedRow(),0));
                    eliminarEnfermero(rut);
                    cargarTablaEnfermeros(tablaEnfermeros);
                }
            });

               popmenu.add(editar);
               popmenu.addSeparator();
               popmenu.add(eliminar);

               tablaEnfermeros.setComponentPopupMenu(popmenu);
            }
        }

        public JComboBox getComboEnfermero() {
            return comboEnfermero;
        }

        public JTextField getTxtNacionalidad() {
            return txtNacionalidad;
        }

        public JTextField getTxtInstitutoEgresado() {
            return txtInstitutoEgresado;
         }
    }

    /***************************************************************************************************/
    public static class Administrador extends Personas{
        
        private  JComboBox comboBox;
        private  JTable  table;
        private JTextField nombre;
        
        public Administrador() {
            
        }

        public Administrador(JComboBox comboBox, JTable table, JTextField nombre) {
            this.comboBox = comboBox;
            this.table = table;
            this.nombre = nombre;
        }

        public void cargarPopMenu(java.awt.event.MouseEvent evt){
            table.setRowSelectionAllowed(true);
            table.setColumnSelectionAllowed(false);

            JPopupMenu popmenu = new JPopupMenu();

            if(evt.getButton() == MouseEvent.BUTTON3) {
                JMenuItem seleccionar = new JMenuItem("Seleccionar paciente");

                seleccionar.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JOptionPane.showMessageDialog(null, "aca va el metodo cargar datos al formulario");
                    }
                });

                popmenu.add(seleccionar);

                table.setComponentPopupMenu(popmenu);
            }
        }

        public JComboBox getComboBox() {
            return comboBox;
        }

        public JTable getTable() {
            return table;
        }

        public JTextField getNombre() {
            return nombre;
        }  
    }
/***************************************************************************************************/
}
