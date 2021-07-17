package com.example.finalproject;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class hotelRegister4 extends AppCompatActivity {
    Button next, login;
    ImageView back;
    TextInputLayout phone;
    FirebaseAuth auth;
    String phone1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_hotel_register4);
        auth=FirebaseAuth.getInstance();
        next = (Button) findViewById(R.id.next);
        back = (ImageView) findViewById(R.id.back);
        phone = (TextInputLayout) findViewById(R.id.phone);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToPhoneAuth();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



    }

    public void goToPhoneAuth() {
        alreadyUse();
    }

    public boolean validatePhone() {

        if(phone1.length()==11)
            return true;
        else{
            phone.setError("Need 11 characters");
            phone.requestFocus();
            return false; }

    }
    public void alreadyUse()
    {
        phone1 = phone.getEditText().getText().toString().trim();

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Hotels");
        userRef
                .orderByChild("phone")
                .equalTo("+88"+phone1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot d) {
                        if (d.exists()) {
                            phone.setError("Phone Already in use");
                            phone.requestFocus();




                        } else {

                            Toast.makeText(getApplicationContext(), "+88" + phone1, Toast.LENGTH_LONG).show();
                            String name1=getIntent().getStringExtra("Name");
                            String email1=getIntent().getStringExtra("Email");
                            String username1=getIntent().getStringExtra("UserName");
                            String pass1=getIntent().getStringExtra("Password");

                            String des=getIntent().getStringExtra("Des");
                            String star=getIntent().getStringExtra("Star");
                            Intent in=new Intent(hotelRegister4.this, phoneauth1.class);
                            in.putExtra("Name",name1);
                            in.putExtra("UserName",username1);
                            in.putExtra("Password",pass1);
                            in.putExtra("Email",email1);

                            in.putExtra("Star",star);
                            in.putExtra("Des",des);

                            in.putExtra("Phone",phone1);
                            Pair pair[] = new Pair[1];
                            pair[0] = new Pair<View, String>(next, "nextTras3");
                            ActivityOptions ac = ActivityOptions.makeSceneTransitionAnimation(hotelRegister4.this, pair);
                            startActivity(in,ac.toBundle());
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

    }




    @Override
    public void onBackPressed() {
        hotelRegister4.super.onBackPressed();
    }
}