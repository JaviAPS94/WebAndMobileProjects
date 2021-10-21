package com.example.lo_encontrecom.loencontreeniv2.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lo-encontre.com on 05/01/2018.
 */

public class Order implements Serializable {

    //Atributos
    private int idgasorder;
    private int idgascylindertype;
    private int idevaluategasservice;
    private int idgascustomer;
    private int idreceptioncentergas;
    private int quantitygasorder;
    private String ordergascode;
    private String statusgasorder;
    private float xgascoordinate;
    private float ygascoordinate;
    private String gasorderdate;
    private String referenceorder;

    public Order() {
    }

    public Order(int idgasorder, int idgascylindertype, int idevaluategasservice, int idgascustomer, int idreceptioncentergas, int quantitygasorder, String ordergascode, String statusgasorder, float xgascoordinate, float ygascoordinate, String gasorderdate, String referenceorder) {
        this.idgasorder = idgasorder;
        this.idgascylindertype = idgascylindertype;
        this.idevaluategasservice = idevaluategasservice;
        this.idgascustomer = idgascustomer;
        this.idreceptioncentergas = idreceptioncentergas;
        this.quantitygasorder = quantitygasorder;
        this.ordergascode = ordergascode;
        this.statusgasorder = statusgasorder;
        this.xgascoordinate = xgascoordinate;
        this.ygascoordinate = ygascoordinate;
        this.gasorderdate = gasorderdate;
        this.referenceorder = referenceorder;
    }

    public int getIdgasorder() {
        return idgasorder;
    }

    public void setIdgasorder(int idgasorder) {
        this.idgasorder = idgasorder;
    }

    public int getIdgascylindertype() {
        return idgascylindertype;
    }

    public void setIdgascylindertype(int idgascylindertype) {
        this.idgascylindertype = idgascylindertype;
    }

    public int getIdevaluategasservice() {
        return idevaluategasservice;
    }

    public void setIdevaluategasservice(int idevaluategasservice) {
        this.idevaluategasservice = idevaluategasservice;
    }

    public int getIdreceptioncentergas() {
        return idreceptioncentergas;
    }

    public void setIdreceptioncentergas(int idreceptioncentergas) {
        this.idreceptioncentergas = idreceptioncentergas;
    }

    public int getQuantitygasorder() {
        return quantitygasorder;
    }

    public void setQuantitygasorder(int quantitygasorder) {
        this.quantitygasorder = quantitygasorder;
    }

    public String getOrdergascode() {
        return ordergascode;
    }

    public void setOrdergascode(String ordergascode) {
        this.ordergascode = ordergascode;
    }

    public String getStatusgasorder() {
        return statusgasorder;
    }

    public void setStatusgasorder(String statusgasorder) {
        this.statusgasorder = statusgasorder;
    }

    public float getXgascoordinate() {
        return xgascoordinate;
    }

    public void setXgascoordinate(float xgascoordinate) {
        this.xgascoordinate = xgascoordinate;
    }

    public float getYgascoordinate() {
        return ygascoordinate;
    }

    public void setYgascoordinate(float ygascoordinate) {
        this.ygascoordinate = ygascoordinate;
    }

    public String getGasorderdate() {
        return gasorderdate;
    }

    public void setGasorderdate(String gasorderdate) {
        this.gasorderdate = gasorderdate;
    }


    public int getIdgascustomer() {
        return idgascustomer;
    }

    public void setIdgascustomer(int idgascustomer) {
        this.idgascustomer = idgascustomer;
    }

    public String getReferenceorder() {
        return referenceorder;
    }

    public void setReferenceorder(String referenceorder) {
        this.referenceorder = referenceorder;
    }
}
