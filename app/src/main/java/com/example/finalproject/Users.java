package com.example.finalproject;

public class Users {
    private String t1,b,img;

    public Users(String i, String s,String s1) {
        this.img=i;
        this.t1=s;
        this.b=s1;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public String getImg() {
        return img;
    }

    public String getT1() {
        return t1;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setT1(String t1) {
        this.t1 = t1;
    }
}
