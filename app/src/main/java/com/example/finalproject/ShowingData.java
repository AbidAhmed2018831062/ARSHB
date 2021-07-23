package com.example.finalproject;

public class ShowingData {
    String date,orders,day,month;
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setOrders(String orders) {
        this.orders = orders;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getOrders() {
        return orders;
    }

    public ShowingData(String date, String orders,String day, String month) {
        this.date = date;
        this.orders = orders;
        this.day = day;
        this.month = month;
    }



}
