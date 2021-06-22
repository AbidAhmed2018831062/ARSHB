package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Calendar;

public class SignUp2 extends AppCompatActivity {
    Button next, login;
    ImageView back;
    RadioGroup radio;
    RadioButton ra;
    DatePicker dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up2);
        next = (Button) findViewById(R.id.next);
        back = (ImageView) findViewById(R.id.back);
        dp = (DatePicker) findViewById(R.id.date);
        radio = (RadioGroup) findViewById(R.id.radio);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSignUp3();
            }
        });


        login = (Button) findViewById(R.id.Log_In);
    }

    private void goToSignUp3() {
        if (validateGender()&&validateAge()) {
            ra=findViewById(radio.getCheckedRadioButtonId());
            String gender=ra.getText().toString();
            int year=dp.getYear();
            int month=dp.getMonth();
            int day=dp.getDayOfMonth();
            String age=day+"/"+month+"/"+year;
            String name1=getIntent().getStringExtra("Name");
            String email1=getIntent().getStringExtra("Email");
            String username1=getIntent().getStringExtra("UserName");
            String pass1=getIntent().getStringExtra("Password");
            Intent in=new Intent(getApplicationContext(), SignUp3.class);
            in.putExtra("Gender",gender);
            in.putExtra("Age",age);
            in.putExtra("Name",name1);
            in.putExtra("UserName",username1);
            in.putExtra("Password",pass1);
            in.putExtra("Email",email1);
            Pair pair[] = new Pair[1];
            pair[0] = new Pair<View, String>(next, "nextTras2");
            ActivityOptions ac = ActivityOptions.makeSceneTransitionAnimation(SignUp2.this, pair);
            startActivity(in, ac.toBundle());
        }
        }
        public boolean validateAge()
        {
            int cyear = Calendar.getInstance().get(Calendar.YEAR);
            int uyear = dp.getYear();
            int age = cyear - uyear;
            if (age < 14) {
                Toast.makeText(getApplicationContext(), "Your age needs to be more than 14 to use this app", Toast.LENGTH_LONG).show();
                return false;
            } else
                return true;
        }


    public boolean validateGender() {
    if(radio.getCheckedRadioButtonId()==-1){
        Toast.makeText(getApplicationContext(),"Please, Select Your Gender",Toast.LENGTH_LONG).show();
        return false;
    }
    else
        return true;
    }

    @Override
    public void onBackPressed() {
        SignUp2.super.onBackPressed();
    }

}