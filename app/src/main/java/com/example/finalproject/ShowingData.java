package com.example.finalproject;

public class ShowingData {
    String date,month;
    int orders, day;

    public String getDate() {
        return date;
    }
public ShowingData(){

}
    public ShowingData(String month, int orders){
        this.orders=orders;
        this.month=month;

    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public int getOrders() {
        return orders;
    }

    public ShowingData(String date, int orders,int day, String month) {
        this.date = date;
        this.orders = orders;
        this.day = day;
        this.month = month;
    }



}
