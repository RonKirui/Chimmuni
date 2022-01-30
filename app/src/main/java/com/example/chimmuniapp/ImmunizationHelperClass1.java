package com.example.chimmuniapp;

public class ImmunizationHelperClass1 {

    String recordid, name, type, date, status, userid;

    public ImmunizationHelperClass1() {
    }

    public ImmunizationHelperClass1(String recordid, String name, String type, String date, String status, String userid) {
        this.recordid = recordid;
        this.name = name;
        this.type = type;
        this.date = date;
        this.status = status;
        this.userid = userid;
    }

    public String getRecordid() {
        return recordid;
    }

    public void setRecordid(String recordid) {
        this.recordid = recordid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
