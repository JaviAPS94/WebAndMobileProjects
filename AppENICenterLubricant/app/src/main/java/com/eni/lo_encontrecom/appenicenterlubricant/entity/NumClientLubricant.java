package com.eni.lo_encontrecom.appenicenterlubricant.entity;

import java.io.Serializable;

/**
 * Created by lo-encontre.com on 24/01/2018.
 */

public class NumClientLubricant implements Serializable {

    /*Atributos*/
    private int idnumclientlubricant;
    private Boolean tipoorderlubricant;
    private String dateorderlubricant;

    /*Constructor*/

    public NumClientLubricant() {

    }

    public NumClientLubricant(int idnumclientlubricant, Boolean tipoorderlubricant, String dateorderlubricant) {
        this.idnumclientlubricant = idnumclientlubricant;
        this.tipoorderlubricant = tipoorderlubricant;
        this.dateorderlubricant = dateorderlubricant;
    }

    /*Getter and Setter*/

    public int getIdnumclientlubricant() {
        return idnumclientlubricant;
    }

    public void setIdnumclientlubricant(int idnumclientlubricant) {
        this.idnumclientlubricant = idnumclientlubricant;
    }

    public Boolean getTipoorderlubricant() {
        return tipoorderlubricant;
    }

    public void setTipoorderlubricant(Boolean tipoorderlubricant) {
        this.tipoorderlubricant = tipoorderlubricant;
    }

    public String getDateorderlubricant() {
        return dateorderlubricant;
    }

    public void setDateorderlubricant(String dateorderlubricant) {
        this.dateorderlubricant = dateorderlubricant;
    }
}
