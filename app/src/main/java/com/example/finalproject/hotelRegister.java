package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class hotelRegister extends AppCompatActivity {
    Button next, login;
    ImageView back;
    TextInputLayout name, username, email, pass, cpass;
    String name1, username1, email1, pass1, cpass1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_hotel_register);
        next = (Button) findViewById(R.id.next);

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        login = (Button) findViewById(R.id.Log_In);
        name = (TextInputLayout) findViewById(R.id.name);
        email = (TextInputLayout) findViewById(R.id.email);
        pass = (TextInputLayout) findViewById(R.id.password);
        cpass = (TextInputLayout) findViewById(R.id.confirmPassword);
        username = (TextInputLayout) findViewById(R.id.username);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSignUp2();
            }
        });

    }

    @Override
    public void onBackPressed() {
        hotelRegister.super.onBackPressed();
    }

    public void goToSignUp2() {
        if (validateName() && validateEmail() && validatePassword() && validateUserName()) {
            gotoEmailVeri();
        }
    }

    public boolean validateName() {
        name1 = name.getEditText().getText().toString().trim();
        if (name1.isEmpty()) {
            name.setError("This field needs to be filled");
            name.requestFocus();
            return false;
        } else if (name1.length() > 20) {
            name.setError(" Too Large. Name needs to be smaller than 20characters");
            name.requestFocus();
            return false;
        } else
            return true;
    }

    public boolean validateUserName() {
        username1 = username.getEditText().getText().toString().trim();
        if (username1.isEmpty()) {
            username.setError("This field needs to be filled");
            username.requestFocus();
            return false;
        } else if (username1.length() > 20) {
            username.setError(" Too Large. Username needs to be smaller than 20characters");
            username.requestFocus();
            return false;
        } else
            return true;
    }

    public boolean validateEmail() {
        email1 = email.getEditText().getText().toString().trim();
        if (email1.isEmpty()) {
            name.setError("This field needs to be filled");
            name.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email1).matches()) {
            email.setError("Email is not valid");
            email.requestFocus();
            return false;

        } else
            return true;
    }

    public boolean validatePassword() {
        pass1 = pass.getEditText().getText().toString().trim();
        cpass1 = cpass.getEditText().getText().toString().trim();
        if (pass1.isEmpty()) {
            pass.setError("This field needs to be filled");
            pass.requestFocus();
            return false;
        } else if (pass1.length() < 6) {
            pass.setError("Password has to be at least of length 6");
            pass.requestFocus();
            return false;
        } else if (!pass1.equals(cpass1)) {
            pass.setError("Password and Confirm Password have to be same");
            pass.requestFocus();
            return false;
        } else
            return true;
    }

    public void gotoEmailVeri() {
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Hotels");
        userRef
                .orderByChild("email")
                .equalTo(email1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot d) {
                        if (d.exists()) {
                            email.setError("Email Already in use");
                            email.requestFocus();
                        } else {


                            email.setErrorEnabled(false);
                            Intent in = new Intent(hotelRegister.this, hotelRegister2.class);
                            in.putExtra("Name", name1);
                            in.putExtra("UserName", username1);
                            in.putExtra("Password", pass1);
                            in.putExtra("Email", email1);
                            Pair pair[] = new Pair[1];
                            pair[0] = new Pair<View, String>(next, "nextTra");
                            ActivityOptions ac = ActivityOptions.makeSceneTransitionAnimation(hotelRegister.this, pair);
                            startActivity(in, ac.toBundle());
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

    }
}