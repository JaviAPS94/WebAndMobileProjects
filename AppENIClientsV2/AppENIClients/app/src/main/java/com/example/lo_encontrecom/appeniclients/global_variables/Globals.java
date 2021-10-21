package com.example.lo_encontrecom.appeniclients.global_variables;

/**
 * Created by lo-encontre.com on 15/01/2018.
 */

public class Globals {

    /*Atributos*/
    private static String url_base;
    private static String aut;

    int idClient;
    String gascustomerfirstname,gascustomerlastname,gascustomercedruc,gascustomeremail,gascustomerphone,gascustomerpassword;

    private String tokengascustomer;
    private int idgranelcustomer;
    private int idgasorder;
    private String granelcustomercode;
    private String granelcustomerruc;
    private String granelcustomerbusinesname;
    private String granelcustomeremail;
    private String granelcustomerphonenumber;
    private String granelcustomeraddress;
    private String granelcustomerpassword;
    private boolean gascustomerstatus;

    private int idgranelorder;
    private int idevaluategranelorderservice;


    /*Constructores*/

    public Globals() {
        url_base="http://192.168.100.5:8080/api/";
        aut="Basic ZW5pOmVuaTIwMTg=";
    }

    public Globals(int idClient, String gascustomerfirstname, String gascustomerlastname, String gascustomercedruc, String gascustomeremail, String gascustomerphone, String gascustomerpassword, String tokengascustomer, int idgranelcustomer, int idgasorder, String granelcustomercode, String granelcustomerruc, String granelcustomerbusinesname, String granelcustomeremail, String granelcustomerphonenumber, String granelcustomeraddress, String granelcustomerpassword, boolean gascustomerstatus, int idgranelorder, int idevaluategranelorderservice) {
        this.idClient = idClient;
        this.gascustomerfirstname = gascustomerfirstname;
        this.gascustomerlastname = gascustomerlastname;
        this.gascustomercedruc = gascustomercedruc;
        this.gascustomeremail = gascustomeremail;
        this.gascustomerphone = gascustomerphone;
        this.gascustomerpassword = gascustomerpassword;
        this.tokengascustomer = tokengascustomer;
        this.idgranelcustomer = idgranelcustomer;
        this.idgasorder = idgasorder;
        this.granelcustomercode = granelcustomercode;
        this.granelcustomerruc = granelcustomerruc;
        this.granelcustomerbusinesname = granelcustomerbusinesname;
        this.granelcustomeremail = granelcustomeremail;
        this.granelcustomerphonenumber = granelcustomerphonenumber;
        this.granelcustomeraddress = granelcustomeraddress;
        this.granelcustomerpassword = granelcustomerpassword;
        this.gascustomerstatus = gascustomerstatus;
        this.idgranelorder = idgranelorder;
        this.idevaluategranelorderservice = idevaluategranelorderservice;
    }

   /*Métodos*/

    public int getIdClient() {
        return idClient;
    }

    public void setIdClient(int idClient) {
        this.idClient = idClient;
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

    public String getGascustomerpassword() {
        return gascustomerpassword;
    }

    public void setGascustomerpassword(String gascustomerpassword) {
        this.gascustomerpassword = gascustomerpassword;
    }

    public int getIdgranelcustomer() {
        return idgranelcustomer;
    }

    public void setIdgranelcustomer(int idgranelcustomer) {
        this.idgranelcustomer = idgranelcustomer;
    }

    public String getGranelcustomercode() {
        return granelcustomercode;
    }

    public void setGranelcustomercode(String granelcustomercode) {
        this.granelcustomercode = granelcustomercode;
    }

    public String getGranelcustomerruc() {
        return granelcustomerruc;
    }

    public void setGranelcustomerruc(String granelcustomerruc) {
        this.granelcustomerruc = granelcustomerruc;
    }

    public String getGranelcustomerbusinesname() {
        return granelcustomerbusinesname;
    }

    public void setGranelcustomerbusinesname(String granelcustomerbusinesname) {
        this.granelcustomerbusinesname = granelcustomerbusinesname;
    }

    public String getGranelcustomerphonenumber() {
        return granelcustomerphonenumber;
    }

    public void setGranelcustomerphonenumber(String granelcustomerphonenumber) {
        this.granelcustomerphonenumber = granelcustomerphonenumber;
    }

    public String getGranelcustomeraddress() {
        return granelcustomeraddress;
    }

    public void setGranelcustomeraddress(String granelcustomeraddress) {
        this.granelcustomeraddress = granelcustomeraddress;
    }

    public String getGranelcustomerpassword() {
        return granelcustomerpassword;
    }

    public void setGranelcustomerpassword(String granelcustomerpassword) {
        this.granelcustomerpassword = granelcustomerpassword;
    }

    public String getGranelcustomeremail() {
        return granelcustomeremail;
    }

    public void setGranelcustomeremail(String granelcustomeremail) {
        this.granelcustomeremail = granelcustomeremail;
    }

    public boolean isGascustomerstatus() {
        return gascustomerstatus;
    }

    public void setGascustomerstatus(boolean gascustomerstatus) {
        this.gascustomerstatus = gascustomerstatus;
    }

    public int getIdgasorder() {
        return idgasorder;
    }


    public void setIdgasorder(int idgasorder) {
        this.idgasorder = idgasorder;
    }


    public String getTokengascustomer() {
        return tokengascustomer;
    }

    public void setTokengascustomer(String tokengascustomer) {
        this.tokengascustomer = tokengascustomer;
    }

    public int getIdgranelorder() {
        return idgranelorder;
    }

    public void setIdgranelorder(int idgranelorder) {
        this.idgranelorder = idgranelorder;
    }

    public int getIdevaluategranelorderservice() {
        return idevaluategranelorderservice;
    }

    public void setIdevaluategranelorderservice(int idevaluategranelorderservice) {
        this.idevaluategranelorderservice = idevaluategranelorderservice;
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
