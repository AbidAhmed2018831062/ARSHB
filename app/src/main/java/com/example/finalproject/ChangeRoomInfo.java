package com.example.finalproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ChangeRoomInfo extends AppCompatActivity {
TextInputLayout rn,service,price;
Button submit;
ImageView img;
String rn1,price1,service1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_room_info);
        rn=(TextInputLayout)findViewById(R.id.rn);
        rn.getEditText().setText(getIntent().getStringExtra("roomname"));
        rn.getEditText().setEnabled(false);
        price=(TextInputLayout)findViewById(R.id.price);
        submit=(Button)findViewById(R.id.submit);
        img=(ImageView)findViewById(R.id.back);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        price.getEditText().setText(getIntent().getStringExtra("price"));
        service=(TextInputLayout)findViewById(R.id.service);
        service.getEditText().setText(getIntent().getStringExtra("services"));
       submit.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               HashMap hm=new HashMap();
               hm.put("roomname",rn.getEditText().getText().toString());
               hm.put("price",price.getEditText().getText().toString());
               hm.put("services",service.getEditText().getText().toString());
               FirebaseDatabase.getInstance().getReference("Hotels").child(getIntent().getStringExtra("hname")).
                       child("rooms").child(getIntent().getStringExtra("roomname")).updateChildren(hm).addOnCompleteListener(new OnCompleteListener() {
                   @Override
                   public void onComplete(@NonNull Task task) {
                       Toast.makeText(getApplicationContext(),"Successfully Updated",Toast.LENGTH_LONG).show();
                       onBackPressed();
                   }
               }).addOnFailureListener(new OnFailureListener() {
                   @Override
                   public void onFailure(@NonNull Exception e) {
                       Toast.makeText(getApplicationContext(),"Something went wrong!! Please try again later.",Toast.LENGTH_LONG).show();
                   }
               });
           }
       });
    }

    @Override
    public void onBackPressed() {
        ChangeRoomInfo.super.onBackPressed();
    }
}