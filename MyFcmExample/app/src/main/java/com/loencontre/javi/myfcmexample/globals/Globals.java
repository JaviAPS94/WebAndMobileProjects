package com.loencontre.javi.myfcmexample.globals;

/**
 * Created by javi on 05/01/2018.
 */

public class Globals {
    private static String url_base;

    private int  idreceptioncentergas;
    private String namereceptioncentergas;
    private Double xreceptioncentercoordinate;
    private Double yreceptioncentercoordinate;
    private String tokenreceptioncentergas;
    private String passwordreceptioncentergas;

    public Globals() {
        url_base="http://192.168.0.11:8080/api/";
    }

    public Globals(int idreceptioncentergas, String namereceptioncentergas, Double xreceptioncentercoordinate, Double yreceptioncentercoordinate, String tokenreceptioncentergas, String passwordreceptioncentergas) {
        this.idreceptioncentergas = idreceptioncentergas;
        this.namereceptioncentergas = namereceptioncentergas;
        this.xreceptioncentercoordinate = xreceptioncentercoordinate;
        this.yreceptioncentercoordinate = yreceptioncentercoordinate;
        this.tokenreceptioncentergas = tokenreceptioncentergas;
        this.passwordreceptioncentergas = passwordreceptioncentergas;
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

    public Double getXreceptioncentercoordinate() {
        return xreceptioncentercoordinate;
    }

    public void setXreceptioncentercoordinate(Double xreceptioncentercoordinate) {
        this.xreceptioncentercoordinate = xreceptioncentercoordinate;
    }

    public Double getYreceptioncentercoordinate() {
        return yreceptioncentercoordinate;
    }

    public void setYreceptioncentercoordinate(Double yreceptioncentercoordinate) {
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

    public static String getUrl_base() {
        return url_base;
    }

    public static void setUrl_base(String url_base) {
        Globals.url_base = url_base;
    }
}
