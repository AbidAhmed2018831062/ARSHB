package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Approval extends AppCompatActivity {
RecyclerView rl;
ApprovalAdapter Arl;
String name;
    List<OrderShow> list=new ArrayList<>();
    List<OrderShow> list1=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approval);
        rl=(RecyclerView)findViewById(R.id.rl);

        Arl = new ApprovalAdapter(Approval.this, list);
        SessionManagerHotels sh= new SessionManagerHotels(Approval.this,SessionManagerHotels.USERSESSION);
        rl.setHasFixedSize(true);
        HashMap<String,String> hm=sh.returnData();
        name=hm.get(SessionManagerHotels.FULLNAME);
        LinearLayoutManager li = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rl.setLayoutManager(li);
        rl.setAdapter(Arl);
        FirebaseDatabase.getInstance().getReference("Hotels").child(name).child("needApp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                 for(DataSnapshot ds:dataSnapshot.getChildren())
                 {
                     Toast.makeText(getApplicationContext(),name,Toast.LENGTH_LONG).show();
                   OrderShow or=ds.getValue(OrderShow.class);
                   list.add(or);
                 }
                 Arl.notifyDataSetChanged();
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