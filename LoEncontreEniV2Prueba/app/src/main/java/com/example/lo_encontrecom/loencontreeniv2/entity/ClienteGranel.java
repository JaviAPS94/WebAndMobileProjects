package com.example.lo_encontrecom.loencontreeniv2.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by lo-encontre.com on 09/01/2018.
 */

public class ClienteGranel implements Serializable {
    private int idgranelcustomer;
    private String granelcustomercode;
    private String granelcustomerruc;
    private String granelcustomerbusinessname;
    private String granelcustomeremail;
    private String granelcustomerphonenumber;
    private String granelcustomeraddress;
    private Boolean granelcustomerstatus;
    private String granelcustomerpassword;
    private String granelcustomertimestamp;
    private Boolean activesessiongranel;

    public ClienteGranel() {

    }

    public ClienteGranel(int idgranelcustomer, String granelcustomercode, String granelcustomerruc, String granelcustomerbusinessname, String granelcustomeremail, String granelcustomerphonenumber, String granelcustomeraddress, Boolean granelcustomerstatus, String granelcustomerpassword, String granelcustomertimestamp, Boolean activesessiongranel) {
        this.idgranelcustomer = idgranelcustomer;
        this.granelcustomercode = granelcustomercode;
        this.granelcustomerruc = granelcustomerruc;
        this.granelcustomerbusinessname = granelcustomerbusinessname;
        this.granelcustomeremail = granelcustomeremail;
        this.granelcustomerphonenumber = granelcustomerphonenumber;
        this.granelcustomeraddress = granelcustomeraddress;
        this.granelcustomerstatus = granelcustomerstatus;
        this.granelcustomerpassword = granelcustomerpassword;
        this.granelcustomertimestamp = granelcustomertimestamp;
        this.activesessiongranel = activesessiongranel;
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

    public String getGranelcustomerbusinessname() {
        return granelcustomerbusinessname;
    }

    public void setGranelcustomerbusinessname(String granelcustomerbusinessname) {
        this.granelcustomerbusinessname = granelcustomerbusinessname;
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

    public Boolean getGranelcustomerstatus() {
        return granelcustomerstatus;
    }

    public void setGranelcustomerstatus(Boolean granelcustomerstatus) {
        this.granelcustomerstatus = granelcustomerstatus;
    }

    public String getGranelcustomerpassword() {
        return granelcustomerpassword;
    }

    public void setGranelcustomerpassword(String granelcustomerpassword) {
        this.granelcustomerpassword = granelcustomerpassword;
    }

    public String getGranelcustomertimestamp() {
        return granelcustomertimestamp;
    }

    public void setGranelcustomertimestamp(String granelcustomertimestamp) {
        this.granelcustomertimestamp = granelcustomertimestamp;
    }

    public String getGranelcustomeremail() {
        return granelcustomeremail;
    }

    public void setGranelcustomeremail(String granelcustomeremail) {
        this.granelcustomeremail = granelcustomeremail;
    }

    public Boolean getActivesessiongranel() {
        return activesessiongranel;
    }

    public void setActivesessiongranel(Boolean activesessiongranel) {
        this.activesessiongranel = activesessiongranel;
    }
}
