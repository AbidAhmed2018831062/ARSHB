package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.Calendar;
import java.util.HashMap;

public class UserPrfofilew extends AppCompatActivity {
TextView name,study,date,place,aboutme,fb;
ImageView profile_image;
CardView fav,save;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_prfofilew);
        name=(TextView)findViewById(R.id.name);
        fb=(TextView)findViewById(R.id.fb);
        profile_image=(ImageView) findViewById(R.id.profile_image);
        fav=(CardView)findViewById(R.id.fav);
        save=(CardView)findViewById(R.id.save);
        fav.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserPrfofilew.this,Fav.class));
                finish();
            }
        });
        save.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserPrfofilew.this,WatchLater.class));
                finish();
            }
        });
        profile_image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
              final  BottomSheetDialog bsd=new BottomSheetDialog(UserPrfofilew.this, R.style.BottomSheetDialogTheme);
                View bs= LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottomsheet,(LinearLayout)findViewById(R.id.bottomsheet));
                bs.findViewById(R.id.view).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(),UserImage.class));
                        finish();
                    }
                });
                bsd.setContentView(bs);
                bsd.show();
            }
        });
        study=(TextView)findViewById(R.id.study);
        date=(TextView)findViewById(R.id.date);
        place=(TextView)findViewById(R.id.place);
        aboutme=(TextView)findViewById(R.id.aboutme);
        SessionManager sh=new SessionManager(this,SessionManager.USERSESSION);

        HashMap<String,String>  hm=sh.returnData();
        String fullname=hm.get(SessionManager.FULLNAME);
        String dob=hm.get(SessionManager.DOB);
        String email=hm.get(SessionManager.EMAIL);
        String phone=hm.get(SessionManager.PHONE);
        String username=hm.get(SessionManager.USERNAME);
        String gender=hm.get(SessionManager.GENDER);
        String pass=hm.get(SessionManager.PASS);
        String socialmedia=hm.get(SessionManager.SOCIALMEDIA);
        String bio=hm.get(SessionManager.BIO);
        String edu=hm.get(SessionManager.EDUCATION);
        String location=hm.get(SessionManager.LOCATION);
        name.setText("    "+fullname);
        study.setText("    "+edu);
        place.setText("    "+location);
        aboutme.setText("    "+bio);
        fb.setText("    "+fullname);

        int cyear = Calendar.getInstance().get(Calendar.YEAR);
        int cmonth= Calendar.getInstance().get(Calendar.MONTH);
        String month;
        if(cmonth==1-1)
            month="January";
       else if(cmonth==2-1)
            month="February";
        else if(cmonth==3-1)
            month="March";
        else if(cmonth==4-1)
            month="April";
        else if(cmonth==5-1)
            month="May";
        else  if(cmonth==6-1)
            month="June";
        else if(cmonth==7-1)
            month="July";
        else   if(cmonth==8-1)
            month="August";
        else if(cmonth==9-1)
            month="September";
        else if(cmonth==10-1)
            month="October";
        else if(cmonth==11-1)
            month="November";
        else month="December";

        int cday = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        date.setText("   "+cday+" "+month+" "+cyear);

    }
}