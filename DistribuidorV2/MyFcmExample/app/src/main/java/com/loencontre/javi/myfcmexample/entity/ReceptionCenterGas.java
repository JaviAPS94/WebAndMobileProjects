package com.loencontre.javi.myfcmexample.entity;

import java.io.Serializable;

/**
 * Created by javi on 11/01/2018.
 */

public class ReceptionCenterGas implements Serializable {

    private int idreceptioncentergas;
    private String namereceptioncentergas;
    private Double xreceptioncentercoordinate;
    private Double yreceptioncentercoordinate;
    private String tokenreceptioncentergas;
    private String passwordreceptioncentergas;

    public ReceptionCenterGas() {
    }

    public ReceptionCenterGas(int idreceptioncentergas, String namereceptioncentergas, Double xreceptioncentercoordinate, Double yreceptioncentercoordinate, String tokenreceptioncentergas, String passwordreceptioncentergas) {
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
}
