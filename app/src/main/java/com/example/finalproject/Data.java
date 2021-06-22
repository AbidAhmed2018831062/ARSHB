package com.example.finalproject;

public class Data {
    String name,password,email,phone,dob,gender,username,count;
    public Data(String name, String password, String email, String phone, String dob, String gender, String username,String count){
        this.name=name;
        this.password=password;
        this.email=email;
        this.phone=phone;
        this.dob=dob;
        this.gender=gender;
        this.username=username;
        this.count=count;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getDob() {
        return dob;
    }

    public String getGender() {
        return gender;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }
}
