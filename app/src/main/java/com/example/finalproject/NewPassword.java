package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class NewPassword extends AppCompatActivity {
    TextInputLayout pass, cpass;
    String pass1, cpass1,phone1;
    Button next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        pass = (TextInputLayout) findViewById(R.id.password);
        next = (Button) findViewById(R.id.next);
        cpass = (TextInputLayout) findViewById(R.id.confirmPassword);

        phone1=getIntent().getStringExtra("Phone");
        Toast.makeText(getApplicationContext(),phone1,Toast.LENGTH_LONG).show();
        if (validatePassword()) {
            DatabaseReference db= FirebaseDatabase.getInstance().getReference("Users");
            db.child(phone1).child("password").setValue(pass1);
            Toast.makeText(getApplicationContext(),pass1,Toast.LENGTH_LONG).show();
        }
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 startActivity(new Intent(NewPassword.this, Success.class));
                 finish();
            }
        });

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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(NewPassword.this, LogIn.class));
        finish();
    }
}