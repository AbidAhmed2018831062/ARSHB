package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;

public class HotelInfo extends AppCompatActivity {
TextInputLayout name1, username, email,phone,star;
String name;
ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_hotel_info);
        name1=(TextInputLayout)findViewById(R.id.name);
        username=(TextInputLayout)findViewById(R.id.username);
        email=(TextInputLayout)findViewById(R.id.email);
        phone=(TextInputLayout)findViewById(R.id.phone);
        star=(TextInputLayout)findViewById(R.id.star);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        SessionManagerHotels sh= new SessionManagerHotels(HotelInfo.this,SessionManagerHotels.USERSESSION);

        HashMap<String,String> hm=sh.returnData();
        name=hm.get(SessionManager.FULLNAME);
        String fullname1=hm.get(SessionManagerHotels.DES);
        String fullname2=hm.get(SessionManagerHotels.EMAIL);
        String fullname3=hm.get(SessionManagerHotels.PHONE);
        String fullname4=hm.get(SessionManagerHotels.USERNAME);
        String fullname5=hm.get(SessionManagerHotels.RATING);
        String fullname6=hm.get(SessionManagerHotels.PASS);
        name1.getEditText().setText(name);
        name1.getEditText().setEnabled(false);
        username.getEditText().setText(fullname4);
        star.getEditText().setText(fullname5);
        phone.getEditText().setText(fullname3);
        email.getEditText().setText(fullname2);
        email.getEditText().setEnabled(false);
        phone.getEditText().setEnabled(false);
        star.getEditText().setEnabled(false);
        username.getEditText().setEnabled(false);

    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),HotelsOverview.class));
    }
}