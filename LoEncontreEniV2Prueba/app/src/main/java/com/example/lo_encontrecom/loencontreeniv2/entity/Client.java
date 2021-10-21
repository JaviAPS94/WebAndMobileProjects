package com.example.lo_encontrecom.loencontreeniv2.entity;

import java.io.Serializable;

/**
 * Created by lo-encontre.com on 03/01/2018.
 */

public class Client implements Serializable{
    /*Atributos*/
    private int idgascustomer;
    private String gascustomerfirstname;
    private String gascustomerlastname;
    private String gascustomercedruc;
    private String gascustomeremail;
    private String gascustomerphone;
    private String gascustomeraddress;
    private String gascustomertimestamp;
    private String gascustomerpassword;

    //Contructores
    public Client() {
    }

    public Client(int idgascustomer, String gascustomerfirstname, String gascustomerlastname, String gascustomercedruc, String gascustomeremail, String gascustomerphone, String gascustomeraddress, String gascustomertimestamp, String gascustomerpassword) {
        this.idgascustomer = idgascustomer;
        this.gascustomerfirstname = gascustomerfirstname;
        this.gascustomerlastname = gascustomerlastname;
        this.gascustomercedruc = gascustomercedruc;
        this.gascustomeremail = gascustomeremail;
        this.gascustomerphone = gascustomerphone;
        this.gascustomeraddress = gascustomeraddress;
        this.gascustomertimestamp = gascustomertimestamp;
        this.gascustomerpassword = gascustomerpassword;
    }

    //Getter and Setter

    public int getIdgascustomer() {
        return idgascustomer;
    }

    public void setIdgascustomer(int idgascustomer) {
        this.idgascustomer = idgascustomer;
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

    public String getGascustomercedruc() {
        return gascustomercedruc;
    }

    public void setGascustomercedruc(String gascustomercedruc) {
        this.gascustomercedruc = gascustomercedruc;
    }

    public String getGascustomeremail() {
        return gascustomeremail;
    }

    public void setGascustomeremail(String gascustomeremail) {
        this.gascustomeremail = gascustomeremail;
    }

    public String getGascustomerphone() {
        return gascustomerphone;
    }

    public void setGascustomerphone(String gascustomerphone) {
        this.gascustomerphone = gascustomerphone;
    }

    public String getGascustomeraddress() {
        return gascustomeraddress;
    }

    public void setGascustomeraddress(String gascustomeraddress) {
        this.gascustomeraddress = gascustomeraddress;
    }

    public String getGascustomertimestamp() {
        return gascustomertimestamp;
    }

    public void setGascustomertimestamp(String gascustomertimestamp) {
        this.gascustomertimestamp = gascustomertimestamp;
    }

    public String getGascustomerpassword() {
        return gascustomerpassword;
    }

    public void setGascustomerpassword(String gascustomerpassword) {
        this.gascustomerpassword = gascustomerpassword;
    }
}
