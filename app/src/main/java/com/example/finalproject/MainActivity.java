package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

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
     /*   if(auth.getCurrentUser()!=null)
        {
            startActivity(new Intent(getApplicationContext(),UserProfile.class));
            finish();

        }else {*/
     shOn=getSharedPreferences("OnBo",MODE_PRIVATE);
     boolean firsttime=shOn.getBoolean("isFirsttime",true);
     if(firsttime) {
         SharedPreferences.Editor editor = shOn.edit();
         editor.putBoolean("isFirsttime", false);
         editor.commit();
         Intent i = new Intent(MainActivity.this, OnBoarding.class);
         startActivity(i);
         finish();
     }
     else
     {
         startActivity(new Intent(getApplicationContext(),DashBoard.class));
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