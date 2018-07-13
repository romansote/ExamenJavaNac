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
public enum TipoParto {
    //Seleccione,Espontaneo,Segmentaria,Corporal;
    parto0("-Seleccione-"),
    parto1("Espontaneo"),
    parto2("Cesaria Segmentaria"),
    parto3("cesaria Corporal");
    
    private String tipoParto;

    private TipoParto(String tipoParto) {
        this.tipoParto = tipoParto;
    }

    public String getTipoParto() {
        return tipoParto;
    }

}
