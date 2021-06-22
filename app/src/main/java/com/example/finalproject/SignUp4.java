package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class SignUp4 extends AppCompatActivity {
String[] instiname;
Spinner insti;
String name,edu,location1,socialmedia1,bio,phone,name1,password,dob,gender,email,username;
TextInputLayout location,socialmedia,aboutme;
Button finish1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up4);
        name1 = getIntent().getStringExtra("Name");
        password = getIntent().getStringExtra("Password");
        email = getIntent().getStringExtra("Email");
        dob = getIntent().getStringExtra("Age");
        gender = getIntent().getStringExtra("Gender");
        username = getIntent().getStringExtra("UserName");
        instiname=getResources().getStringArray(R.array.insti);
    insti=(Spinner)findViewById(R.id.insti);
    phone=getIntent().getStringExtra("Phone");
        ArrayAdapter<String> ar=new ArrayAdapter<String>(this, R.layout.education,R.id.Edu,instiname);
        insti.setAdapter(ar);

        location=(TextInputLayout)findViewById(R.id.location);
        socialmedia=(TextInputLayout)findViewById(R.id.socialmedia);
        aboutme=(TextInputLayout)findViewById(R.id.aboutme);
        finish1=(Button)findViewById(R.id.finish);
        finish1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateName()&&validateUserName()&&validateBio())
                {
                    edu=insti.getSelectedItem().toString();
                    storeUserData();
                    Intent in=new Intent(SignUp4.this, UserPrfofilew.class);
                    startActivity(in);
                }
            }
        });



    }
    public boolean validateName() {
       location1 = location.getEditText().getText().toString().trim();
        if (location1.isEmpty()) {
            location.getEditText().setError("This field needs to be filled");
            location.getEditText().requestFocus();
            return false;
        } else if (location1.length() > 20) {
            location.getEditText().setError(" Too Large. Name needs to be smaller than 20characters");
            location.getEditText().requestFocus();
            return false;
        } else
            return true;
    }

    public boolean validateUserName() {
        socialmedia1 = socialmedia.getEditText().getText().toString().trim();
        if (socialmedia1.isEmpty()) {
            socialmedia.getEditText().setError("This field needs to be filled");
            socialmedia.getEditText().requestFocus();
            return false;
        } else
            return true;
    }
    public boolean validateBio() {
        bio = aboutme.getEditText().getText().toString().trim();
        if (bio.isEmpty()) {
            aboutme.getEditText().setError("This field needs to be filled");
          aboutme.getEditText().requestFocus();
            return false;
        } else if (bio.length() > 200) {
            aboutme.getEditText().setError(" Too Large. Username needs to be smaller than 20characters");
            aboutme.getEditText().requestFocus();
            return false;
        } else
            return true;
    }
    private void storeUserData()
    {
        FirebaseAuth auth=FirebaseAuth.getInstance();
        DatabaseReference re= FirebaseDatabase.getInstance().getReference("Users");

        HashMap hm1=new HashMap<>();
        hm1.put("location",location1);
        hm1.put("bio",bio);
        hm1.put("education",edu);
        hm1.put("socialmedia",socialmedia1);


        re.child(phone).updateChildren(hm1);
        SessionManager sh=new SessionManager(SignUp4.this,SessionManager.USERSESSION);

        sh.loginSession(name1,email,gender,phone,password,dob,username,location1,socialmedia1,bio,edu);
    }
}