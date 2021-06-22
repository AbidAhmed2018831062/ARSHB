package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowAmenties extends AppCompatActivity {
ImageView img;
String name;
ListView li;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_amenties);
        img=(ImageView)findViewById(R.id.back);
        li=(ListView)findViewById(R.id.li);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        name=getIntent().getStringExtra("name").toString();
        Query c = FirebaseDatabase.getInstance().getReference("Hotels").orderByChild("name").equalTo(name);
        c.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot d) {
                String[] am = new String[11];
                String email = d.child(name).child("email").getValue(String.class);
                String star = d.child(name).child("star").getValue(String.class);
                String des = d.child(name).child("des").getValue(String.class);
                String fullname = d.child(name).child("name").getValue(String.class);
                String phone2 = d.child(name).child("phone").getValue(String.class);
                String username = d.child(name).child("username").getValue(String.class);
                String ac = d.child(name).child("ac").getValue(String.class);
                String tv = d.child(name).child("tv").getValue(String.class);
                String fi = d.child(name).child("fa").getValue(String.class);
                String fia = d.child(name).child("fia").getValue(String.class);
                String wifi = d.child(name).child("wifi").getValue(String.class).toString();
                String sp = d.child(name).child("sp").getValue(String.class);
                String plc = d.child(name).child("plc").getValue(String.class);
                String rs = d.child(name).child("rs").getValue(String.class);
                String rc = d.child(name).child("rc").getValue(String.class);
                String hd = d.child(name).child("hd").getValue(String.class);
                String sc = d.child(name).child("sc").getValue(String.class);
                if (wifi.isEmpty())
                    Toast.makeText(getApplicationContext(), "Abid", Toast.LENGTH_LONG).show();
                else {
                    if (ac.equals("yes")) {
                        am[0] = "AC";
                    }
                    if (wifi.equals("yes"))
                        am[1] = "Wifi";
                    if (tv.equals("yes"))
                        am[2] = "TV";
                    if (sp.equals("yes"))
                        am[3] = "Swimming Pool";
                    if (sc.equals("yes"))
                        am[4] = "Security Cameras On Hall";
                    if (plc.equals("yes"))
                        am[5] = "Play Ground For Children";
                    if (fi.equals("yes"))
                        am[6] = "Fire Alarm";
                    if (fia.equals("yes"))
                        am[7] = "First Aid";
                    if (rs.equals("yes"))
                        am[8] = "Software To Call Room Staff";
                    if (rc.equals("yes"))
                        am[9] = "Everyday Room Cleaning";
                    if (hd.equals("yes"))
                        am[10] = "Hair Dryer";
                    ArrayAdapter<String> ar = new ArrayAdapter<String>(ShowAmenties.this, R.layout.amenities, R.id.text, am);
                    li.setAdapter(ar);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        ShowAmenties.super.onBackPressed();
    }
}