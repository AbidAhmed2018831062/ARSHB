package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Reviews extends AppCompatActivity {
ImageView arrowb;
Button submit;
TextInputLayout comment;
RecyclerView allR;
List<CommentShow> list=new ArrayList<>();
ALLR rl;
int C=2;
LinearLayout visi;
    int k,E;
    String url;
    RatingBar rating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_reviews);
        arrowb=(ImageView)findViewById(R.id.back);
        submit=(Button)findViewById(R.id.submit);
        allR=(RecyclerView)findViewById(R.id.allR);
        visi=(LinearLayout)findViewById(R.id.visi);
        rating=(RatingBar)findViewById(R.id.rating);
        final String hotelName=getIntent().getStringExtra("name");
        comment=(TextInputLayout)findViewById(R.id.comment);
        SessionManager sh=new SessionManager(this,SessionManager.USERSESSION);

        HashMap<String,String> hm=sh.returnData();
        final String fullname=hm.get(SessionManager.FULLNAME);
        final String phone=hm.get(SessionManager.PHONE);
        rl = new ALLR(Reviews.this, list, hotelName);
        allR.setHasFixedSize(true);
        LinearLayoutManager li = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        allR.setLayoutManager(li);
        allR.setAdapter(rl);
        arrowb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String review=comment.getEditText().getText().toString();
                Random rn=new Random();
                final int jk= rn.nextInt(10000000);
                Query c = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phone").equalTo(phone);
                c.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot d) {
                         url = d.child(phone).child("url").getValue(String.class);
                        CommentShow show=new CommentShow(fullname,review,phone, url,rating.getNumStars()+"");
                        DatabaseReference db= FirebaseDatabase.getInstance().getReference("Hotels").child(hotelName).child("Review").child("Reveiw"+jk);
                        db.setValue(show);
                        list.add(show);
                        rl.notifyDataSetChanged();
                        submit.setVisibility(View.GONE);
                        comment.setVisibility(View.GONE);
                        Random rn=new Random();
                        long kl=rn.nextInt(1000000);
                        HashMap h=new HashMap();
                        h.put("rating"+kl,rating.getRating()+"");

                        DatabaseReference dgh=  FirebaseDatabase.getInstance().getReference("Hotels").child(hotelName).child("Rating");
                        dgh.updateChildren(h);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });
        final int[] k = {0};
        final int[] yu = { 0 };
        FirebaseDatabase.getInstance().getReference("Users").child("Order").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot d:dataSnapshot.getChildren())
                {
                    OrderShow or=d.getValue(OrderShow.class);
                    String hdes=or.getHname();
                    if(hdes.contains(hotelName))
                    {
                        k[0]++;
                        FirebaseDatabase.getInstance().getReference("Hotels").child("Order").addValueEventListener(new ValueEventListener(){

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                               for(DataSnapshot ds: dataSnapshot1.getChildren())
                               {
                                   CommentShow cs=ds.getValue(CommentShow.class);
                                   String Cname=cs.getName();
                                   if(Cname.contains(fullname))
                                   {
                                          yu[0]++;
                                   }

                               }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        if(k[0] !=0&&yu[0]==0)
          visi.setVisibility(View.VISIBLE);
        Random rn=new Random();
        int jk= rn.nextInt(10000000);
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Hotels").child(hotelName).child("Review");


        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                   CommentShow roo= ds.getValue(CommentShow.class);
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
        Reviews.super.onBackPressed();
    }
}