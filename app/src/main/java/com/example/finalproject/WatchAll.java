package com.example.finalproject;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class WatchAll extends AppCompatActivity {
String name;
TextView prot,hallt,outt,swimt,main;
ImageView hall,pro,out,swim;
int k=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_all);
            prot=(TextView)findViewById(R.id.prot);
            hallt=(TextView)findViewById(R.id.hallt);
            outt=(TextView)findViewById(R.id.outt);
            swimt=(TextView)findViewById(R.id.swimt);
            main=(TextView)findViewById(R.id.main);
            pro=(ImageView)findViewById(R.id.pro);
            hall=(ImageView)findViewById(R.id.hall);
            out=(ImageView)findViewById(R.id.out);
            swim=(ImageView)findViewById(R.id.swim);
            name=getIntent().getStringExtra("name");
            main.setText("Photos of hotel "+name);

            FirebaseDatabase.getInstance().getReference("Hotels").child("Hotel Names").child(name).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    HotelShow hs=dataSnapshot.getValue(HotelShow.class);
                    Picasso.with(WatchAll.this).load(hs.getUrl()).into(pro);
                    prot.setText(name);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        FirebaseDatabase.getInstance().getReference("Hotels").child(name).child("Photos").addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    for(DataSnapshot ds:dataSnapshot.getChildren())
                    {
                        if(k==1) {
                            Picasso.with(WatchAll.this).load(ds.getValue().toString()).into(hall);
                            hallt.setText("Hall of" + " " + name);
                        }
                        else if(k==2)
                        {
                            Picasso.with(WatchAll.this).load(ds.getValue().toString()).into(out);
                            outt.setText("Outside view of" + " " + name);
                        }
                        else if(k==3)
                        {
                            Picasso.with(WatchAll.this).load(ds.getValue().toString()).into(swim);
                            swimt.setText("Swimmingpool of" + " " + name);
                        }
                        k++;

                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}