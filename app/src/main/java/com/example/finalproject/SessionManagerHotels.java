package com.example.finalproject;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

import static com.example.finalproject.SessionManager.DOB;

public class SessionManagerHotels {

    SharedPreferences sh;
    SharedPreferences.Editor ed;
    Context c;
    public static final String USERSESSION = "userLogIn";
    public static final String REMEMBERME = "userRemember";


    public static final String ISLOGGED = "ISLOGGEDIN";
    public static final String FULLNAME = "FULLNAME";
    public static final String EMAIL = "EMAIL";
    public static final String PHONE = "PHONE";
    public static final String DES = "DES";
    public static final String PASS = "PASS";
    public static final String RATING = "RATING";
    public static final String USERNAME = "USERNAME";


    public static final String ISREMEMBERME = "REMEMBERME";
    public static final String REMEMBERMEPHONE = "PHONE";
    public static final String REMEMBERMEPASS = "PASS";
    public static final String URL = "URL";

    public SessionManagerHotels(Context c, String session) {
        this.c = c;
        sh = c.getSharedPreferences(session, Context.MODE_PRIVATE);
        ed = sh.edit();
    }

    public void loginSession(String fullname, String email, String rating, String phone, String pass, String des, String username,String url) {

        ed.putBoolean(ISLOGGED, true);

        ed.putString(FULLNAME, fullname);
        ed.putString(EMAIL, email);
        ed.putString(RATING, rating);
        ed.putString(PHONE, phone);
        ed.putString(PASS, pass);
        ed.putString(DES, des);
        ed.putString(USERNAME, username);
        ed.putString(URL, url);

        ed.commit();
    }

    public HashMap<String, String> returnData() {
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put(FULLNAME, sh.getString(FULLNAME, null));
        hm.put(EMAIL, sh.getString(EMAIL, null));
        hm.put(PHONE, sh.getString(PHONE, null));
        hm.put(DES, sh.getString(DES, null));
        hm.put(RATING, sh.getString(RATING, null));
        hm.put(PASS, sh.getString(PASS, null));
        hm.put(USERNAME, sh.getString(USERNAME, null));
        hm.put(URL, sh.getString(URL, null));

        return hm;
    }

    public boolean checkLogin() {
        if (sh.getBoolean(ISLOGGED, true)) {
            return true;
        } else
            return false;
    }

    public void logOut() {
        ed.clear();
        ed.commit();
    }

    public void rememberMeSession(String phone, String pass) {

        ed.putBoolean(ISREMEMBERME, true);


        ed.putString(REMEMBERMEPHONE, phone);
        ed.putString(REMEMBERMEPASS, pass);

        ed.commit();
    }

    public HashMap<String, String> returnDataRememberMe() {
        HashMap<String, String> hm = new HashMap<String, String>();

        hm.put(REMEMBERMEPHONE, sh.getString(REMEMBERMEPHONE, null));

        hm.put(REMEMBERMEPASS, sh.getString(REMEMBERMEPASS, null));

        return hm;
    }

    public boolean checkRememberMe() {
        if (sh.getBoolean(ISREMEMBERME, true)) {
            return true;
        } else
            return false;
    }

}
