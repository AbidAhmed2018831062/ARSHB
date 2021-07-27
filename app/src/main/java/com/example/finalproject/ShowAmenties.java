package com.example.finalproject;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class ShowAmenties extends AppCompatActivity {
ImageView img;
String name,name1;
ListView li;
Button add;
    HashMap<String,String> hm;
    SessionManagerHotels sh;
    ArrayList<String> am1=new ArrayList<>();
    ArrayList<String> list = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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
        Toast.makeText(getApplicationContext(),name,Toast.LENGTH_LONG).show();
       Query c = FirebaseDatabase.getInstance().getReference("Hotels").orderByChild("name").equalTo(name);
        c.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot d) {
                if (d.exists()) {
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
                    String wifi = d.child(name).child("wifi").getValue(String.class);
                    String sp = d.child(name).child("sp").getValue(String.class);
                    String plc = d.child(name).child("plc").getValue(String.class);
                    String rs = d.child(name).child("rs").getValue(String.class);
                    String rc = d.child(name).child("rc").getValue(String.class);
                    String hd = d.child(name).child("hd").getValue(String.class);
                    String sc = d.child(name).child("sc").getValue(String.class);
                  //  Toast.makeText(getApplicationContext(),wifi+" "+ac, Toast.LENGTH_LONG).show();

                    if (ac.equals("yes")) {
                        list.add("AC");
                    }
                    if (wifi.equals("yes")) {
                        list.add("Wifi");
                        Toast.makeText(getApplicationContext(),wifi+" "+ac, Toast.LENGTH_LONG).show();  }
                    if (tv.equals("yes"))
                        list.add("TV");
                    if (sp.equals("yes"))
                        list.add("Swimming Pool");
                    if (sc.equals("yes"))
                        list.add("Security Cameras On Hall");
                    if (plc.equals("yes"))
                        list.add("Play Ground For Children");
                    if (fi.equals("yes"))
                        list.add("Fire Alarm");
                    if (fia.equals("yes"))
                        list.add("First Aid");
                    if (rs.equals("yes"))
                        list.add("Software To Call Room Staff");
                    if (rc.equals("yes"))
                        list.add("Everyday Room Cleaning");
                    if (hd.equals("yes"))
                        list.add("Hair Dryer");
                FirebaseDatabase.getInstance().getReference("Hotels").child(name).child("ShowAme").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot34) {
                        for(DataSnapshot ds: dataSnapshot34.getChildren())
                        {
                            list.add(ds.getValue().toString());
                        }
                        ArrayAdapter<String> ar = new ArrayAdapter<String>(ShowAmenties.this, R.layout.amenities, R.id.text, list);
                        li.setAdapter(ar);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        sh= new SessionManagerHotels(ShowAmenties.this,SessionManagerHotels.USERSESSION);

         hm=sh.returnData();
        name1=hm.get(SessionManagerHotels.RATING);
        if(name1!=null)
        {
           add=(Button)findViewById(R.id.add);
           add.setVisibility(View.VISIBLE);
           add.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   li.setVisibility(View.GONE);
                   addView();
               }
           });
        }



    }

    @Override
    public void onBackPressed() {
        ShowAmenties.super.onBackPressed();
    }
 public void   addView()
    {
        add.setVisibility(View.GONE);
        View view=getLayoutInflater().inflate(R.layout.addame, null,false);
        final TextInputLayout te=view.findViewById(R.id.rn);
        final Button add1=view.findViewById(R.id.add);
        final LinearLayout linear=(LinearLayout)findViewById(R.id.linear);
        linear.addView(view);
        add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!te.getEditText().getText().toString().equals("")||te.getEditText().getText().toString()!=null||!te.getEditText().
                        getText().toString().isEmpty())
                {
                    String n=hm.get(SessionManagerHotels.FULLNAME);
                    HashMap t=new HashMap();
                   t.put(te.getEditText().getText().toString(),te.getEditText().getText().toString());
                   am1.add(te.getEditText().getText().toString());
                   FirebaseDatabase.getInstance().getReference("Hotels").child(n).child("ShowAme").updateChildren(t);
                   add1.setVisibility(View.GONE);
                   Toast.makeText(getApplicationContext(),"Amenity added successfully",Toast.LENGTH_LONG).show();
                   add.setVisibility(View.VISIBLE);
                    li.setVisibility(View.VISIBLE);
                   te.setVisibility(View.GONE);
                }
            }
        });

    }

}