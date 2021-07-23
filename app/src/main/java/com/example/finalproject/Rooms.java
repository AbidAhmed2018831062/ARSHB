package com.example.finalproject;

public class Rooms {
    public String services, price,roomname,url;

    public Rooms()
    {

    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getPrice() {
        return price;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Rooms(String roomname, String services , String price, String url)
    {
        this.services=services;
        this.price=price;
        this.roomname=roomname;
        this.url=url;
    }
}
