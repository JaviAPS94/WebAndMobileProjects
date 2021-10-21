package com.example.lo_encontrecom.appeniclients.entity;

/**
 * Created by lo-encontre.com on 15/01/2018.
 */

public class Client {

    /*Atributos*/
    private int idgascustomer;
    private String gascustomerfirstname;
    private String gascustomerlastname;
    private String gascustomercedruc;
    private String gascustomeremail;
    private String gascustomerphone;
    private String gascustomertimestamp;
    private String gascustomerpassword;
    private boolean gascustomerstatus;
    private String tokengascustomer;

    //Contructores
    public Client() {
    }

    public Client(int idgascustomer, String gascustomerfirstname, String gascustomerlastname, String gascustomercedruc, String gascustomeremail, String gascustomerphone, String gascustomertimestamp, String gascustomerpassword, boolean gascustomerstatus, String tokengascustomer) {
        this.idgascustomer = idgascustomer;
        this.gascustomerfirstname = gascustomerfirstname;
        this.gascustomerlastname = gascustomerlastname;
        this.gascustomercedruc = gascustomercedruc;
        this.gascustomeremail = gascustomeremail;
        this.gascustomerphone = gascustomerphone;
        this.gascustomertimestamp = gascustomertimestamp;
        this.gascustomerpassword = gascustomerpassword;
        this.gascustomerstatus = gascustomerstatus;
        this.tokengascustomer = tokengascustomer;
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

    public boolean isGascustomerstatus() {
        return gascustomerstatus;
    }

    public void setGascustomerstatus(boolean gascustomerstatus) {
        this.gascustomerstatus = gascustomerstatus;
    }

    public String getTokengascustomer() {
        return tokengascustomer;
    }

    public void setTokengascustomer(String tokengascustomer) {
        this.tokengascustomer = tokengascustomer;
    }
}
