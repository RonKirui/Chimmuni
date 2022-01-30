package com.example.chimmuniapp;

public class AppointmentsHelperClass {

    String date, childname, vaccination, status;

    public AppointmentsHelperClass() {
    }

    public AppointmentsHelperClass(String date, String childname, String vaccination, String status) {
        this.date = date;
        this.childname = childname;
        this.vaccination = vaccination;
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getChildname() {
        return childname;
    }

    public void setChildname(String childname) {
        this.childname = childname;
    }

    public String getVaccination() {
        return vaccination;
    }

    public void setVaccination(String vaccination) {
        this.vaccination = vaccination;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
