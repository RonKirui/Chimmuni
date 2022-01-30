package com.example.chimmuniapp;

public class RegisterChildHelperClass {

    String childname, dob, sex, mothername, fathername, cbr, ancno, healthcentre, userid;

    public RegisterChildHelperClass() {
    }

    public RegisterChildHelperClass(String childname, String dob, String sex, String mothername, String fathername, String cbr, String ancno, String healthcentre, String userid) {
        this.childname = childname;
        this.dob = dob;
        this.sex = sex;
        this.mothername = mothername;
        this.fathername = fathername;
        this.cbr = cbr;
        this.ancno = ancno;
        this.healthcentre = healthcentre;
        this.userid = userid;
    }

    public String getChildname() {
        return childname;
    }

    public void setChildname(String childname) {
        this.childname = childname;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMothername() {
        return mothername;
    }

    public void setMothername(String mothername) {
        this.mothername = mothername;
    }

    public String getFathername() {
        return fathername;
    }

    public void setFathername(String fathername) {
        this.fathername = fathername;
    }

    public String getCbr() {
        return cbr;
    }

    public void setCbr(String cbr) {
        this.cbr = cbr;
    }

    public String getAncno() {
        return ancno;
    }

    public void setAncno(String ancno) {
        this.ancno = ancno;
    }

    public String getHealthcentre() {
        return healthcentre;
    }

    public void setHealthcentre(String healthcentre) {
        this.healthcentre = healthcentre;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
