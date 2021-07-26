package com.example.finalproject;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
     ImageView im1;
     TextView t1;
     FirebaseAuth auth;
     SharedPreferences shOn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_main);
        im1=(ImageView) findViewById(R.id.title1);
        t1=(TextView) findViewById(R.id.name);
        im1.animate().alpha(0f).setDuration(0);
        t1.animate().alpha(0f).setDuration(0);
        im1.animate().alpha(1f).setDuration(1000).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                t1.animate().alpha(1f).setDuration(800);
            }
        });
        auth=FirebaseAuth.getInstance();

        Thread t=new Thread(new Runnable() {
            @Override
            public void run() {
                doWork();
                newIntentr();
            }
        });
        t.start();

    }
    private void newIntentr() {

            shOn = getSharedPreferences("OnBo", MODE_PRIVATE);
            boolean firsttime = shOn.getBoolean("isFirsttime", true);
            if (firsttime) {
                SharedPreferences.Editor editor = shOn.edit();
                editor.putBoolean("isFirsttime", false);
                editor.commit();
                Intent i = new Intent(MainActivity.this, OnBoarding.class);
                startActivity(i);
                finish();
            } else {
                SessionManagerHotels sh = new SessionManagerHotels(this, SessionManagerHotels.USERSESSION);

                HashMap<String, String> hm = sh.returnData();
                final String phone2 = hm.get(SessionManagerHotels.RATING);
                SessionManager sh1 = new SessionManager(this, SessionManager.USERSESSION);

                HashMap<String, String> hm1 = sh1.returnData();
                final String phone = hm.get(SessionManager.PHONE);
                if (phone2 == null&&phone==null) {
                    startActivity(new Intent(getApplicationContext(), LogIn_Or_SignUp.class));
                    finish();
                } else if(phone2==null&&phone!=null){
                    startActivity(new Intent(getApplicationContext(), DashBoard.class));
                    finish();
                }
                else
                {
                    startActivity(new Intent(getApplicationContext(), HotelsOverview.class));
                    finish();
                }

            }

        }


    private void doWork() {
        for(int i=10;i<=60;i=i+20){
            try{
                Thread.sleep(1000);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}