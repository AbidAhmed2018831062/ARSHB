package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class CutomerOrOwner extends AppCompatActivity {
    ImageView im1;
    RelativeLayout cus, own;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_cutomer_or_owner);
        im1 = (ImageView) findViewById(R.id.back);
        cus = (RelativeLayout) findViewById(R.id.cus);
        own = (RelativeLayout) findViewById(R.id.own);
        cus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CutomerOrOwner.this, LogIn_Or_SignUp.class));
                finish();
            }
        });
        im1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        own.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CutomerOrOwner.this, hotelRegister.class));
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(CutomerOrOwner.this, DashBoard.class));
        finish();
    }
}