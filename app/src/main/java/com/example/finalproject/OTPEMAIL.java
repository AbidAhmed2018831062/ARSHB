package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class OTPEMAIL extends AppCompatActivity {
    PinView pin;
    ImageView cancel;
    String otp1,otp,email1,username1,rating1,name;
    Button verify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_o_t_p_e_m_a_i_l);
         otp=getIntent().getStringExtra("OTP").toString();
         username1=getIntent().getStringExtra("USERNAME").toString();
         email1=getIntent().getStringExtra("EMAIL").toString();
         rating1=getIntent().getStringExtra("STAR").toString();
        SessionManagerHotels sh= new SessionManagerHotels(OTPEMAIL.this,SessionManagerHotels.USERSESSION);

        HashMap<String,String> hm=sh.returnData();
        name=hm.get(SessionManager.FULLNAME);
        final String fullname1=hm.get(SessionManagerHotels.DES);
        final String fullname2=hm.get(SessionManagerHotels.EMAIL);
        final String fullname3=hm.get(SessionManagerHotels.PHONE);
        String fullname4=hm.get(SessionManagerHotels.USERNAME);
        String fullname5=hm.get(SessionManagerHotels.RATING);
        final String fullname6=hm.get(SessionManagerHotels.PASS);
        final String fullname7=hm.get(SessionManagerHotels.URL);
         pin = (PinView) findViewById(R.id.pin);
        verify = (Button) findViewById(R.id.verify);
        cancel = (ImageView) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HotelRooms.class));
            }
        });

        verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                otp1=pin.getText().toString();

                if(otp1.equals(otp))
                {
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("Hotels");
                    HashMap hm1 = new HashMap<>();
                    hm1.put("email", email1);
                    hm1.put("username", username1);
                    hm1.put("star", rating1);
                    //   Hotel d=new Hotel(name,fullname6,email.getEditText().getText(),fullname3,fullname1, rating.getEditText().getText(),username.getEditText(),getText(),fullname7);
                    db.child(name).updateChildren(hm1).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            SessionManagerHotels sh = new SessionManagerHotels(OTPEMAIL.this, SessionManagerHotels.USERSESSION);

                            sh.loginSession(name, email1, rating1, fullname3, fullname6, fullname1, username1, fullname7);
                            Toast.makeText(getApplicationContext(), "Successfully Updated", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),HotelRooms.class));
                        }
                    });
                }
                else
                {
                    Toast.makeText(getApplicationContext(),otp+" Abid"+otp1,Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}