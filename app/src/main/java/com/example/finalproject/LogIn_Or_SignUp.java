package com.example.finalproject;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LogIn_Or_SignUp extends AppCompatActivity {
 Button b1,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_log_in__or__sign_up);
        b1=(Button)findViewById(R.id.login1);
        b2=(Button)findViewById(R.id.signup1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getApplicationContext(),LogIn.class);
                 Pair[] pairs=new Pair[1];
                 pairs[0]=new Pair<View,String>(b1,"transitionLo");
                ActivityOptions ac=ActivityOptions.makeSceneTransitionAnimation(LogIn_Or_SignUp.this,pairs);
                startActivity(in,ac.toBundle());


            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pair[] p=new Pair[1];
                p[0]=new Pair<View,String>(b2,"transitionSign");
                ActivityOptions ac=ActivityOptions.makeSceneTransitionAnimation(LogIn_Or_SignUp.this,p);
                startActivity(new Intent(getApplicationContext(),CutomerOrOwner.class),ac.toBundle());
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(LogIn_Or_SignUp.this,DashBoard.class));
        finish();
    }
}