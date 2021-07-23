package com.example.finalproject;

import android.os.Bundle;

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

public class Payment extends AppCompatActivity {
RecyclerView rl3;
PaymentAdapter rl;
String name;
    List<OrderShow> list=new ArrayList<>();
    List<OrderShow> list1=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_payment);
        rl3=(RecyclerView)findViewById(R.id.rl3);
        rl = new PaymentAdapter(this, list);
        SessionManager sh= new SessionManager(this,SessionManager.USERSESSION);
        rl3.setHasFixedSize(true);
        HashMap<String,String> hm=sh.returnData();
        name=hm.get(SessionManagerHotels.PHONE);
        LinearLayoutManager li = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rl3.setLayoutManager(li);
        rl3.setAdapter(rl);
        FirebaseDatabase.getInstance().getReference("Users").child(name).child("Payment").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    OrderShow or=ds.getValue(OrderShow.class);
                    list.add(or);
                }
                rl.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}