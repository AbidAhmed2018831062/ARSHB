package com.example.finalproject;

public class Rooms {
    public String services, price,roomname;

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

    public String getRoomname() {
        return roomname;
    }

    public void setRoomname(String roomname) {
        this.roomname = roomname;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Rooms(String services , String price)
    {
        this.services=services;
        this.price=price;
    }
}
