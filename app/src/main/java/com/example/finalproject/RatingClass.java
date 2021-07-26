package com.example.finalproject;

public class RatingClass {

    String name;
    float rating;

    public String getName() {
        return name;
    }

    public float getRating() {
        return rating;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public RatingClass(String name, float rating) {
        this.name = name;
        this.rating = rating;
    }
    RatingClass()
    {}

}
