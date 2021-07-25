package com.example.finalproject;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Calendar;
import java.util.List;

public class OrderGiven extends AppCompatActivity {
OrderAdapter rl;
ImageView back;
List<OrderShow> list=new ArrayList<>();
List<OrderShow> list1=new ArrayList<>();
RecyclerView allR;
int cday,cmonth,cyear;
TextView te;
String phone,name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_order_given);
        allR=(RecyclerView)findViewById(R.id.orders);
        te=(TextView)findViewById(R.id.te);
      //  back=(ImageView)findViewById(R.id.back);
      //  back.setOnClickListener(new View.OnClickListener() {
        phone=getIntent().getStringExtra("phone").toString();
        name=getIntent().getStringExtra("name").toString();

         cday = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
         cyear = Calendar.getInstance().get(Calendar.YEAR);
         cmonth = Calendar.getInstance().get(Calendar.MONTH);
         if(name.equals("upcoming")) {
             rl = new OrderAdapter(OrderGiven.this, list);
             te.setText("Your Upcoming Orders:");
         }
         else
         {
             if(phone.contains("+")) {
                 te.setText("Your Orders:");
             }
             else
                 te.setText("Your Received Orders:");
             rl = new OrderAdapter(OrderGiven.this, list1);
         }
        allR.setHasFixedSize(true);
        LinearLayoutManager li = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        allR.setLayoutManager(li);
        allR.setAdapter(rl);
        DatabaseReference db;
      if(phone.contains("+")) {
     db= FirebaseDatabase.getInstance().getReference("Users").child(phone).child("Order");
      }
      else
          db= FirebaseDatabase.getInstance().getReference("Hotels").child(name).child("Order");

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();

                    for (DataSnapshot d : dataSnapshot.getChildren()) {

                        OrderShow or = d.getValue(OrderShow.class);
                        String mai = or.getIssueDate().toString();

                        String c ="", y = "", m="";
                        for (int i = mai.length() - 1; i >=mai.length() - 4; i--) {
                            y += mai.charAt(i);
                        }
                        String d2="";
                        for(int i=y.length()-1;i>=0;i--)
                                d2+= y.charAt(i);
                        Toast.makeText(getApplicationContext(),y,Toast.LENGTH_LONG).show();
                        int wer=Integer.parseInt(d2);
                        if(wer!=cyear) {
                            list1.add(or);
                        }
                        else
                        {
                            for (int i = 3; i <mai.length(); i++) {
                                if(mai.charAt(i)=='2')
                                    break;
                                m += mai.charAt(i);
                            }
                            int l;
                            m=m.toLowerCase();
                            if(mai.equals("january"))
                                l=1;
                            else if(m.equals("february"))
                                l=2;
                            else if(m.equals("march"))
                                l=3;
                            else if(m.equals("april"))
                                l=4;
                            else if(m.equals("may"))
                                l=5;
                            else if(m.equals("june"))
                                l=6;
                            else if(m.equals("july"))
                                l=7;
                            else if(m.equals("august"))
                                l=8;
                            else if(m.equals("september"))
                               l=9;
                            else if(m.equals("october"))
                                l=10;
                            else if(m.equals("november"))
                                l=11;
                            else
                                l=12;

                            if(l>=cmonth)
                            {for(int i=0;i<2;i++)
                            {
                                char hj=mai.charAt(i);
                                if(Character.isWhitespace(hj)) {
                                    break;
                                }
                                c+= mai.charAt(i);
                            }
                            int C=Integer.parseInt(c);
                            if(C>cday)
                                list.add(or);
                            else
                                list1.add(or);
                            }
                            else
                                list1.add(or);
                        }

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