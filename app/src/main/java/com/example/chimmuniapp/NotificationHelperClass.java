package com.example.chimmuniapp;

public class NotificationHelperClass {

    String time, message;

    public NotificationHelperClass() {
    }

    public NotificationHelperClass(String time, String message) {
        this.time = time;
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
