/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Enum;

/**
 *
 * @author roman
 */
public enum TipoEstado {
    //Estado,Completa,Eliminado;
    estado0("-Seleccione-"),
    estado1("Cerrado"),
    estado2("Digitacion Completa"),
    estado3("Eliminado");
    
    private String Estado;

    private TipoEstado(String Estado) {
        this.Estado = Estado;
    }

    public String getEstado() {
        return Estado;
    }
}
