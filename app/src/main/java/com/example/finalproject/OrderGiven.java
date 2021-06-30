package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderGiven extends AppCompatActivity {
OrderAdapter rl;
ImageView back;
List<OrderShow> list=new ArrayList<>();
RecyclerView allR;
String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_order_given);
        allR=(RecyclerView)findViewById(R.id.orders);
      //  back=(ImageView)findViewById(R.id.back);
      //  back.setOnClickListener(new View.OnClickListener() {
        phone=getIntent().getStringExtra("phone").toString();
        rl = new OrderAdapter(OrderGiven.this, list);
        allR.setHasFixedSize(true);
        LinearLayoutManager li = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        allR.setLayoutManager(li);
        allR.setAdapter(rl);
        DatabaseReference db;
      if(phone.contains("+")) {
     db= FirebaseDatabase.getInstance().getReference("Users").child(phone).child("Order");
      }
      else
          db= FirebaseDatabase.getInstance().getReference("Hotels").child(phone).child("Order");

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for(DataSnapshot d: dataSnapshot.getChildren())
                {
                    OrderShow or=d.getValue(OrderShow.class);
                    list.add(or);

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
        OrderGiven.super.onBackPressed();
    }
}