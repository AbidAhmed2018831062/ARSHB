package com.example.finalproject;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences sh;
    SharedPreferences.Editor ed;
    Context c;
    public static final String USERSESSION = "userLogIn";
    public static final String REMEMBERME = "userRemember";


    public static final String ISLOGGED = "ISLOGGEDIN";
    public static final String signupdate = "signupdate";
    public static final String SOCIALMEDIA = "SOCIALMEDIA";
    public static final String EDUCATION = "EDUCATION";
    public static final String LOCATION = "LOCATION";
    public static final String BIO = "BIO";
    public static final String FULLNAME = "FULLNAME";
    public static final String EMAIL = "EMAIL";
    public static final String PHONE = "PHONE";
    public static final String DOB = "DOB";
    public static final String PASS = "PASS";
    public static final String GENDER = "GENDER";
    public static final String USERNAME = "USERNAME";


    public static final String ISREMEMBERME = "REMEMBERME";
    public static final String REMEMBERMEPHONE = "PHONE";
    public static final String REMEMBERMEPASS = "PASS";

    public SessionManager(Context c, String session) {
        this.c = c;
        sh = c.getSharedPreferences(session, Context.MODE_PRIVATE);
        ed = sh.edit();
    }

    public void loginSession(String fullname, String email, String gender, String phone, String pass, String dob, String username,String location,String socialmedia, String bio, String edu, String signudate) {

        ed.putBoolean(ISLOGGED, true);

        ed.putString(FULLNAME, fullname);
        ed.putString(EMAIL, email);
        ed.putString(GENDER, gender);
        ed.putString(PHONE, phone);
        ed.putString(PASS, pass);
        ed.putString(DOB, dob);
        ed.putString(USERNAME, username);
        ed.putString(LOCATION, location);
        ed.putString(SOCIALMEDIA, socialmedia);
        ed.putString(BIO, bio);
        ed.putString(EDUCATION, edu);
        ed.putString(signupdate, signudate);

        ed.commit();
    }

    public HashMap<String, String> returnData() {
        HashMap<String, String> hm = new HashMap<String, String>();
        hm.put(FULLNAME, sh.getString(FULLNAME, null));
        hm.put(EMAIL, sh.getString(EMAIL, null));
        hm.put(PHONE, sh.getString(PHONE, null));
        hm.put(DOB, sh.getString(DOB, null));
        hm.put(GENDER, sh.getString(GENDER, null));
        hm.put(PASS, sh.getString(PASS, null));
        hm.put(USERNAME, sh.getString(USERNAME, null));
        hm.put(LOCATION, sh.getString(LOCATION, null));
        hm.put(SOCIALMEDIA, sh.getString(SOCIALMEDIA, null));
        hm.put(BIO, sh.getString(BIO, null));
        hm.put(EDUCATION, sh.getString(EDUCATION, null));
        hm.put(signupdate, sh.getString(signupdate, null));

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
