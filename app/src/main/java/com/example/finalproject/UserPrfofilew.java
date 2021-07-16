package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class UserPrfofilew extends AppCompatActivity {
TextView name,study,date,place,aboutme,fb;
ImageView profile_image;
CardView fav,save,order,upcoming;
String phone;

List<OrderShow> list=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_user_prfofilew);
        name=(TextView)findViewById(R.id.name);
        fb=(TextView)findViewById(R.id.fb);
        profile_image=(ImageView) findViewById(R.id.profile_image);
        fav=(CardView)findViewById(R.id.fav);
        SessionManager sh=new SessionManager(this,SessionManager.USERSESSION);

        HashMap<String,String>  hm=sh.returnData();
        order=(CardView)findViewById(R.id.order);
        upcoming=(CardView)findViewById(R.id.upcoming);
        save=(CardView)findViewById(R.id.save);

        fav.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserPrfofilew.this,Fav.class));
                finish();
            }
        });
        save.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(UserPrfofilew.this,WatchLater.class));
                finish();
            }
        });
        profile_image.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
              final  BottomSheetDialog bsd=new BottomSheetDialog(UserPrfofilew.this, R.style.BottomSheetDialogTheme);
                View bs= LayoutInflater.from(getApplicationContext()).inflate(R.layout.bottomsheet,(LinearLayout)findViewById(R.id.bottomsheet));
                bs.findViewById(R.id.view).setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        startActivity(new Intent(getApplicationContext(),UserImage.class));
                        finish();
                    }
                });
                bsd.setContentView(bs);
                bsd.show();
            }
        });
        study=(TextView)findViewById(R.id.study);
        date=(TextView)findViewById(R.id.date);
        place=(TextView)findViewById(R.id.place);
        aboutme=(TextView)findViewById(R.id.aboutme);

        String fullname=hm.get(SessionManager.FULLNAME);
        String dob=hm.get(SessionManager.DOB);
        String email=hm.get(SessionManager.EMAIL);
         phone=hm.get(SessionManager.PHONE);
        String username=hm.get(SessionManager.USERNAME);
        String gender=hm.get(SessionManager.GENDER);
        String pass=hm.get(SessionManager.PASS);
        String socialmedia=hm.get(SessionManager.SOCIALMEDIA);
        String bio=hm.get(SessionManager.BIO);
        String edu=hm.get(SessionManager.EDUCATION);
        String location=hm.get(SessionManager.LOCATION);
        String signupdate=hm.get(SessionManager.signupdate);
        final String[] url = new String[1];
        Query c = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phone").equalTo(phone);
        c.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot d) {
               url[0] = d.child(phone).child("url").getValue(String.class);
                Picasso.with(UserPrfofilew.this).load(url[0]).fit().centerCrop().into(profile_image);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        name.setText("    "+fullname);
        study.setText("    "+edu);
        place.setText("    "+location);
        aboutme.setText("    "+bio);
        fb.setText("    "+fullname);


        int cday = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        upcoming.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in= new Intent(getApplicationContext(),OrderGiven.class);
                in.putExtra("phone",phone);
                in.putExtra("name","upcoming");

                startActivity(in);
            }
        });
        if(signupdate==null) {

            date.setText("   10 April 2021");
        }
        else
            date.setText("    "+signupdate);
order.setOnClickListener(new OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent in= new Intent(getApplicationContext(),OrderGiven.class);
        in.putExtra("phone",phone);
        in.putExtra("name","given");

startActivity(in);
    }
});
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),DashBoard.class));
        finish();
    }
}