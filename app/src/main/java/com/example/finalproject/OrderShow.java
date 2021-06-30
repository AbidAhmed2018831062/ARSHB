package com.example.finalproject;

public class OrderShow {
    String Cname,Hname,price,roomscount,issueDate,endDate,Rname;


    OrderShow()
    {

    }
    OrderShow(String Cname, String Hname, String price, String roomscount, String issueDate, String endDate, String Rname)
    {
        this.Cname=Cname;
        this.Hname=Hname;
        this.price=price;
        this.roomscount=roomscount;
        this.issueDate=issueDate;
        this.endDate=endDate;
        this.Rname=Rname;

    }

    public void setRname(String rname) {
        this.Rname = rname;
    }

    public void setHname(String hname) {
        Hname = hname;
    }

    public void setCname(String cname) {
        this.Cname = cname;
    }

    public String getCname() {
        return Cname;
    }

    public String getHname() {
        return Hname;
    }

    public String getPrice() {
        return price;
    }

    public String getRoomscount() {
        return roomscount;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getRname() {
        return Rname;
    }
}
