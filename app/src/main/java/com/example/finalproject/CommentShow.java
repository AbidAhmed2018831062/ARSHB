package com.example.finalproject;

public class CommentShow {
    String name,comment,phone,url;
    String star;
    CommentShow(String name,String comment, String phone,String url,String star)
    {
        this.name=name;
        this.comment=comment;
        this.phone=phone;
        this.url=url;
        this.star=star;
    }
    CommentShow(){

    }
public String getStar()
{
    return this.star;
}
    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public String getPhone() {
        return phone;
    }
    public String getUrl() {
        return url;
    }
}
