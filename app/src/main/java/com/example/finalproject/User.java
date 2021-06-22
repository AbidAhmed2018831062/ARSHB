package com.example.finalproject;

public class User {
   private String fn,ln,pass,cpass,email,phone,age;
    public User()
    {

    }
    public User(String fn,String ln,String email,String phone,String cpass,String pass,String age)
    {
        this.fn=fn;
        this.ln=ln;
        this.age=age;
        this.email=email;
        this.pass=pass;
        this.cpass=cpass;
        this.phone=phone;
    }

}
