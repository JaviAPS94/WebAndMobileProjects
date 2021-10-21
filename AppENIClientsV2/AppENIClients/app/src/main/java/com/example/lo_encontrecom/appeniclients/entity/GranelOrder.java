package com.example.lo_encontrecom.appeniclients.entity;

import java.io.Serializable;

/**
 * Created by lo-encontre.com on 24/01/2018.
 */

public class GranelOrder implements Serializable {

    /*Atributos*/
    private int idgranelorder;
    private int idgranelcustomer;
    private int idreceptioncentergranel;
    private String ordergranelcode;
    private String statusgranelorder;
    private String granelorderdate;
    private int idevaluategranelorderservice;

    /*Constructor*/
    public GranelOrder() {
    }

    public GranelOrder(int idgranelorder, int idgranelcustomer, int idreceptioncentergranel, String ordergranelcode, String statusgranelorder, String granelorderdate, int idevaluategranelorderservice) {
        this.idgranelorder = idgranelorder;
        this.idgranelcustomer = idgranelcustomer;
        this.idreceptioncentergranel = idreceptioncentergranel;
        this.ordergranelcode = ordergranelcode;
        this.statusgranelorder = statusgranelorder;
        this.granelorderdate = granelorderdate;
        this.idevaluategranelorderservice = idevaluategranelorderservice;
    }

    /*Getter and Setter*/

    public int getIdgranelorder() {
        return idgranelorder;
    }

    public void setIdgranelorder(int idgranelorder) {
        this.idgranelorder = idgranelorder;
    }

    public int getIdgranelcustomer() {
        return idgranelcustomer;
    }

    public void setIdgranelcustomer(int idgranelcustomer) {
        this.idgranelcustomer = idgranelcustomer;
    }

    public int getIdreceptioncentergranel() {
        return idreceptioncentergranel;
    }

    public void setIdreceptioncentergranel(int idreceptioncentergranel) {
        this.idreceptioncentergranel = idreceptioncentergranel;
    }

    public String getOrdergranelcode() {
        return ordergranelcode;
    }

    public void setOrdergranelcode(String ordergranelcode) {
        this.ordergranelcode = ordergranelcode;
    }

    public String getStatusgranelorder() {
        return statusgranelorder;
    }

    public void setStatusgranelorder(String statusgranelorder) {
        this.statusgranelorder = statusgranelorder;
    }

    public String getGranelorderdate() {
        return granelorderdate;
    }

    public void setGranelorderdate(String granelorderdate) {
        this.granelorderdate = granelorderdate;
    }

    public int getIdevaluategranelorderservice() {
        return idevaluategranelorderservice;
    }

    public void setIdevaluategranelorderservice(int idevaluategranelorderservice) {
        this.idevaluategranelorderservice = idevaluategranelorderservice;
    }
}
