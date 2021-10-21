package com.eni.lo_encontrecom.appenicenterlubricant.global_variables;

/**
 * Created by lo-encontre.com on 15/01/2018.
 */

public class Globals {

    /*Atributos*/
    private static String url_base;
    private static String aut;

    private int idnumclientlubricant;
    private Boolean tipoorderlubricant;
    private String dateorderlubricant;


    /*Constructores*/

    public Globals() {
        url_base="http://18.221.245.249:8080/eniv2/api/";
        aut="Basic ZW5pOmVuaTIwMTg=";
    }

    public Globals(int idnumclientlubricant, Boolean tipoorderlubricant, String dateorderlubricant) {
        this.idnumclientlubricant = idnumclientlubricant;
        this.tipoorderlubricant = tipoorderlubricant;
        this.dateorderlubricant = dateorderlubricant;
    }

    /*Métodos*/

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

    public static String getAut() {
        return aut;
    }

    public static void setAut(String aut) {
        Globals.aut = aut;
    }

    public static String getUrl_base() {
        return url_base;
    }

    public static void setUrl_base(String url_base) {
        Globals.url_base = url_base;
    }

}
