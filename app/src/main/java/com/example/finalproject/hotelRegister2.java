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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

public class hotelRegister2 extends AppCompatActivity {
    Button next, login;
    ImageView back;
    RadioGroup radio;
    RadioButton ra;
  TextInputLayout des;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_hotel_register2);
        next = (Button) findViewById(R.id.next);
        back = (ImageView) findViewById(R.id.back);
       des=(TextInputLayout) findViewById(R.id.des);

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
        if (validateStar()) {
            ra=findViewById(radio.getCheckedRadioButtonId());
            String star=ra.getText().toString();

            String des1=des.getEditText().getText().toString().trim();
            String name1=getIntent().getStringExtra("Name");
            String email1=getIntent().getStringExtra("Email");
            String username1=getIntent().getStringExtra("UserName");
            String pass1=getIntent().getStringExtra("Password");
            Intent in=new Intent(getApplicationContext(), hotelRegister4.class);
            in.putExtra("Star",star);
            in.putExtra("Des",des1);
            in.putExtra("Name",name1);
            in.putExtra("UserName",username1);
            in.putExtra("Password",pass1);
            in.putExtra("Email",email1);
            Pair pair[] = new Pair[1];
            pair[0] = new Pair<View, String>(next, "nextTras2");
            ActivityOptions ac = ActivityOptions.makeSceneTransitionAnimation(hotelRegister2.this, pair);
            startActivity(in, ac.toBundle());
        }
    }



    public boolean validateStar() {
        if(radio.getCheckedRadioButtonId()==-1){
            Toast.makeText(getApplicationContext(),"Please, Select Your Gender",Toast.LENGTH_LONG).show();
            return false;
        }
        else
            return true;
    }

    @Override
    public void onBackPressed() {
        hotelRegister2.super.onBackPressed();
    }

}