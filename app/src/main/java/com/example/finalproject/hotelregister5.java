package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class hotelregister5 extends AppCompatActivity {
    CheckBox wifi,tv,ac,fa,fia,rlc,sp,hd,sc,plc,rs,rc;
    String name1;
    Button fi;
    TextInputLayout ad;
    String ad1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotelregister5);
        name1=getIntent().getStringExtra("Name").toString();
        wifi=(CheckBox)findViewById(R.id.wifi);
        tv=(CheckBox)findViewById(R.id.tv);
        ac=(CheckBox)findViewById(R.id.ac);
        fa=(CheckBox)findViewById(R.id.fa);
        fia=(CheckBox)findViewById(R.id.fia);
        hd=(CheckBox)findViewById(R.id.hd);
        rs=(CheckBox)findViewById(R.id.rs);
        plc=(CheckBox)findViewById(R.id.plc);
        rc=(CheckBox)findViewById(R.id.rc);
        ad=(TextInputLayout) findViewById(R.id.ad);

        DatabaseReference db1=FirebaseDatabase.getInstance().getReference("Hotels").child(name1);
        HashMap hm1=new HashMap();
        hm1.put("ad",ad1);
        db1.updateChildren(hm1);
        sp=(CheckBox)findViewById(R.id.sp);
        sc=(CheckBox)findViewById(R.id.sc);
        fi=(Button)findViewById(R.id.finish);
        fi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wifi.isChecked())
                {
                    DatabaseReference db= FirebaseDatabase.getInstance().getReference("Hotels").child(name1);
                    HashMap hm=new HashMap();
                    hm.put("wifi","yes");
                    db.updateChildren(hm);
                }
                else
                {
                    DatabaseReference db= FirebaseDatabase.getInstance().getReference("Hotels").child(name1);
                    HashMap hm=new HashMap();
                    hm.put("wifi","no");
                    db.updateChildren(hm);
                }
                if(tv.isChecked())
                {
                    DatabaseReference db= FirebaseDatabase.getInstance().getReference("Hotels").child(name1);
                    HashMap hm=new HashMap();
                    hm.put("tv","yes");
                    db.updateChildren(hm);
                }
                else
                {
                    DatabaseReference db= FirebaseDatabase.getInstance().getReference("Hotels").child(name1);
                    HashMap hm=new HashMap();
                    hm.put("tv","no");
                    db.updateChildren(hm);
                }
                if(ac.isChecked())
                {
                    DatabaseReference db= FirebaseDatabase.getInstance().getReference("Hotels").child(name1);
                    HashMap hm=new HashMap();
                    hm.put("ac","yes");
                    db.updateChildren(hm);
                }
                else
                {
                    DatabaseReference db= FirebaseDatabase.getInstance().getReference("Hotels").child(name1);
                    HashMap hm=new HashMap();
                    hm.put("ac","no");
                    db.updateChildren(hm);
                }
                if(fa.isChecked())
                {
                    DatabaseReference db= FirebaseDatabase.getInstance().getReference("Hotels").child("name1");
                    HashMap hm=new HashMap();
                    hm.put("fa","yes");
                    db.updateChildren(hm);
                }
                else
                {
                    DatabaseReference db= FirebaseDatabase.getInstance().getReference("Hotels").child("name1");
                    HashMap hm=new HashMap();
                    hm.put("fa","no");
                    db.updateChildren(hm);
                }
                if(fia.isChecked())
                {
                    DatabaseReference db= FirebaseDatabase.getInstance().getReference("Hotels").child("name1");
                    HashMap hm=new HashMap();
                    hm.put("fia","yes");
                    db.updateChildren(hm);
                }
                else
                {
                    DatabaseReference db= FirebaseDatabase.getInstance().getReference("Hotels").child(name1);
                    HashMap hm=new HashMap();
                    hm.put("fia","no");
                    db.updateChildren(hm);
                }
                if(hd.isChecked())
                {
                    DatabaseReference db= FirebaseDatabase.getInstance().getReference("Hotels").child(name1);
                    HashMap hm=new HashMap();
                    hm.put("hd","yes");
                    db.updateChildren(hm);
                }
                else
                {
                    DatabaseReference db= FirebaseDatabase.getInstance().getReference("Hotels").child(name1);
                    HashMap hm=new HashMap();
                    hm.put("hd","no");
                    db.updateChildren(hm);
                }
                if(rs.isChecked())
                {
                    DatabaseReference db= FirebaseDatabase.getInstance().getReference("Hotels").child(name1);
                    HashMap hm=new HashMap();
                    hm.put("rs","yes");
                    db.updateChildren(hm);
                }
                else
                {
                    DatabaseReference db= FirebaseDatabase.getInstance().getReference("Hotels").child(name1);
                    HashMap hm=new HashMap();
                    hm.put("rs","no");
                }
                if(plc.isChecked())
                {
                    DatabaseReference db= FirebaseDatabase.getInstance().getReference("Hotels").child(name1);
                    HashMap hm=new HashMap();
                    hm.put("plc","yes");
                    db.updateChildren(hm);
                }
                else
                {
                    DatabaseReference db= FirebaseDatabase.getInstance().getReference("Hotels").child(name1);
                    HashMap hm=new HashMap();
                    hm.put("plc","no");
                    db.updateChildren(hm);
                }
                if(rc.isChecked())
                {
                    DatabaseReference db= FirebaseDatabase.getInstance().getReference("Hotels").child(name1);
                    HashMap hm=new HashMap();
                    hm.put("rc","yes");
                    db.updateChildren(hm);
                }
                else
                {
                    DatabaseReference db= FirebaseDatabase.getInstance().getReference("Hotels").child(name1);
                    HashMap hm=new HashMap();
                    hm.put("rc","no");db.updateChildren(hm);

                }
                if(sp.isChecked())
                {
                    DatabaseReference db= FirebaseDatabase.getInstance().getReference("Hotels").child(name1);
                    HashMap hm=new HashMap();
                    hm.put("sp","yes");
                    db.updateChildren(hm);
                }
                else
                {
                    DatabaseReference db= FirebaseDatabase.getInstance().getReference("Hotels").child(name1);
                    HashMap hm=new HashMap();
                    hm.put("sp","no");
                    db.updateChildren(hm);
                }  if(sc.isChecked())
                {
                    DatabaseReference db= FirebaseDatabase.getInstance().getReference("Hotels").child(name1);
                    HashMap hm=new HashMap();
                    hm.put("sc","yes");
                    db.updateChildren(hm);
                }
                else
                {
                    DatabaseReference db= FirebaseDatabase.getInstance().getReference("Hotels").child(name1);
                    HashMap hm=new HashMap();
                    hm.put("sc","no");
                    db.updateChildren(hm);
                }
                if(address()) {
                    Intent in = new Intent(getApplicationContext(), roomCreation.class);
                    in.putExtra("Name", name1);

                    startActivity(in);
                    finish();
                }
            }
        });


    }
    public boolean address()
    {  ad1=ad.getEditText().getText().toString();
        if(ad1.isEmpty())
        {
            ad.getEditText().setError("Addess can not be empty");
            ad.getEditText().requestFocus();
            return false;
        }
        return true;
    }
}