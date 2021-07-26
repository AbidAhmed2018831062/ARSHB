package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UpdateRooms extends AppCompatActivity {

    RecyclerView rl4HR1;
String name;
    List<Rooms> list;
    rl4HR rl;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_update_rooms);
        SessionManagerHotels sh= new SessionManagerHotels(UpdateRooms.this,SessionManagerHotels.USERSESSION);
        img=(ImageView)findViewById(R.id.back);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        HashMap<String,String> hm=sh.returnData();
        name=hm.get(SessionManagerHotels.FULLNAME);
        String fullname1=hm.get(SessionManagerHotels.DES);
        String fullname2=hm.get(SessionManagerHotels.EMAIL);
        String fullname3=hm.get(SessionManagerHotels.PHONE);
        String fullname4=hm.get(SessionManagerHotels.USERNAME);
        String fullname5=hm.get(SessionManagerHotels.RATING);
        String fullname6=hm.get(SessionManagerHotels.PASS);
        rl4HR1=(RecyclerView)findViewById(R.id.rl4HR);
        list=new ArrayList<>();
        rl=new rl4HR(UpdateRooms.this, list,name,"Update");
        rl4HR1.setHasFixedSize(true);
        LinearLayoutManager li=new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        rl4HR1.setLayoutManager(li);rl4HR1.setAdapter(rl);     DatabaseReference db= FirebaseDatabase.getInstance().getReference("Hotels").child(name).child("rooms");


        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Rooms roo=ds.getValue(Rooms.class);

                    /*View roomView=getLayoutInflater().inflate(R.layout.roomsshowcase, null,false);
                    TextView se=(TextView) roomView.findViewById(R.id.service);
                    TextView pr=(TextView)roomView.findViewById(R.id.price);
                 se.setText(roo.getServices());
                 pr.setText("$"+ roo.getPrice());
                 roomView.setPadding(0,10,0,10);
                 layoutList.setPadding(0,10,0,10);
layoutList.addView(roomView);*/
                    list.add(roo);
                }
                rl.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });


    }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),HotelsOverview.class));
    }
}