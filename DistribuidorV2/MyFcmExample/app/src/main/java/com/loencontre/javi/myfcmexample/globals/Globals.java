package com.loencontre.javi.myfcmexample.globals;

/**
 * Created by javi on 05/01/2018.
 */

public class Globals {
    private static String url_base;
    private static String aut;

    private int  idreceptioncentergas;
    private String namereceptioncentergas;
    private String xreceptioncentercoordinate;
    private String yreceptioncentercoordinate;
    private String tokenreceptioncentergas;
    private String passwordreceptioncentergas;
    private String gascustomerfirstname;
    private String gascustomerlastname;
    private String gascustomerphone;
    private String referenceorder;
    private String gascylindername;
    private String quantitygasorder;
    private int idgasorder;
    private String tokengascustomer;



    public Globals() {
        url_base="http://18.221.245.249:8080/eniv2/api/";
        aut="Basic ZW5pOmVuaTIwMTg=";
    }

    public Globals(int idreceptioncentergas, String namereceptioncentergas, String xreceptioncentercoordinate, String yreceptioncentercoordinate, String tokenreceptioncentergas, String passwordreceptioncentergas, String gascustomerfirstname, String gascustomerlastname, String gascustomerphone, String referenceorder, String gascylindername, String quantitygasorder, int idgasorder, String tokengascustomer) {
        this.idreceptioncentergas = idreceptioncentergas;
        this.namereceptioncentergas = namereceptioncentergas;
        this.xreceptioncentercoordinate = xreceptioncentercoordinate;
        this.yreceptioncentercoordinate = yreceptioncentercoordinate;
        this.tokenreceptioncentergas = tokenreceptioncentergas;
        this.passwordreceptioncentergas = passwordreceptioncentergas;
        this.gascustomerfirstname = gascustomerfirstname;
        this.gascustomerlastname = gascustomerlastname;
        this.gascustomerphone = gascustomerphone;
        this.referenceorder = referenceorder;
        this.gascylindername = gascylindername;
        this.quantitygasorder = quantitygasorder;
        this.idgasorder = idgasorder;
        this.tokengascustomer = tokengascustomer;
    }

    public int getIdreceptioncentergas() {
        return idreceptioncentergas;
    }

    public void setIdreceptioncentergas(int idreceptioncentergas) {
        this.idreceptioncentergas = idreceptioncentergas;
    }

    public String getNamereceptioncentergas() {
        return namereceptioncentergas;
    }

    public void setNamereceptioncentergas(String namereceptioncentergas) {
        this.namereceptioncentergas = namereceptioncentergas;
    }

    public String getXreceptioncentercoordinate() {
        return xreceptioncentercoordinate;
    }

    public void setXreceptioncentercoordinate(String xreceptioncentercoordinate) {
        this.xreceptioncentercoordinate = xreceptioncentercoordinate;
    }

    public String getYreceptioncentercoordinate() {
        return yreceptioncentercoordinate;
    }

    public void setYreceptioncentercoordinate(String yreceptioncentercoordinate) {
        this.yreceptioncentercoordinate = yreceptioncentercoordinate;
    }

    public String getTokenreceptioncentergas() {
        return tokenreceptioncentergas;
    }

    public void setTokenreceptioncentergas(String tokenreceptioncentergas) {
        this.tokenreceptioncentergas = tokenreceptioncentergas;
    }

    public String getPasswordreceptioncentergas() {
        return passwordreceptioncentergas;
    }

    public void setPasswordreceptioncentergas(String passwordreceptioncentergas) {
        this.passwordreceptioncentergas = passwordreceptioncentergas;
    }

    public String getGascustomerfirstname() {
        return gascustomerfirstname;
    }

    public void setGascustomerfirstname(String gascustomerfirstname) {
        this.gascustomerfirstname = gascustomerfirstname;
    }

    public String getGascustomerlastname() {
        return gascustomerlastname;
    }

    public void setGascustomerlastname(String gascustomerlastname) {
        this.gascustomerlastname = gascustomerlastname;
    }

    public String getGascustomerphone() {
        return gascustomerphone;
    }

    public void setGascustomerphone(String gascustomerphone) {
        this.gascustomerphone = gascustomerphone;
    }

    public String getReferenceorder() {
        return referenceorder;
    }

    public void setReferenceorder(String referenceorder) {
        this.referenceorder = referenceorder;
    }

    public String getGascylindername() {
        return gascylindername;
    }

    public void setGascylindername(String gascylindername) {
        this.gascylindername = gascylindername;
    }

    public String getQuantitygasorder() {
        return quantitygasorder;
    }

    public void setQuantitygasorder(String quantitygasorder) {
        this.quantitygasorder = quantitygasorder;
    }

    public int getIdgasorder() {
        return idgasorder;
    }

    public String getTokengascustomer() {
        return tokengascustomer;
    }

    public void setTokengascustomer(String tokengascustomer) {
        this.tokengascustomer = tokengascustomer;
    }

    public static String getAut() {
        return aut;
    }

    public static void setAut(String aut) {
        Globals.aut = aut;
    }

    public void setIdgasorder(int idgasorder) {
        this.idgasorder = idgasorder;
    }

    public static String getUrl_base() {
        return url_base;
    }

    public static void setUrl_base(String url_base) {
        Globals.url_base = url_base;
    }
}
