package com.example.finalproject;

public class Earning {
    long earning,orders;
    public Earning()
    {}

    public Earning(long earning,long orders) {
        this.earning = earning;
        this.orders = orders;
    }

    public long getEarning() {
        return earning;
    }

    public void setEarning(long earning) {
        this.earning = earning;
    }

    public long getOrders() {
        return orders;
    }

    public void setOrders(long orders) {
        this.orders = orders;
    }
}
