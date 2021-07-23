package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class forgot extends AppCompatActivity {
    Button next;
    ImageView back;
    TextInputLayout phone;
    String phone1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        next = (Button) findViewById(R.id.next);
        back = (ImageView) findViewById(R.id.back);
        phone = (TextInputLayout) findViewById(R.id.phone);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoUserFinding();
            }
        });


    }  public boolean validatePhone() {

        if(phone1.length()==11)
            return true;
        else{
            phone.setError("Need 11 characters");
            phone.requestFocus();
            return false; }
    }

  public void  gotoUserFinding()
    {
        phone1=phone.getEditText().getText().toString().trim();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users");
        userRef
                .orderByChild("phone")
                .equalTo("+88"+phone1)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot d) {
                        if (d.exists()) {
                         Intent in=new Intent(forgot.this,phoneAuth.class);
                         in.putExtra("Phone",phone1);
                         in.putExtra("Do","update");
                         startActivity(in);




                        } else {

                            phone.setError("No such user exist");
                            phone.requestFocus();
                        }

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });

        }
    public void onBackPressed() {
        forgot.super.onBackPressed();
    }
}