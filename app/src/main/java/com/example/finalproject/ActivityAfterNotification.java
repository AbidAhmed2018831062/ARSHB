package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.paypal.android.sdk.payments.PayPalConfiguration;

import java.util.HashMap;

public class ActivityAfterNotification extends AppCompatActivity {
    private static final int PAYPAL_REQUEST_CODE = 7171;
    private static PayPalConfiguration config=new PayPalConfiguration().
            environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).
            clientId(Config.PAYPAL_CLIENT_ID);
    TextView user,hotel;
    Button ok;

    float tot;
    String name,fullname,q,phone,email,cdes,hdes,start1,end1,rname,cdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_after_notification);
        SessionManager sh=new SessionManager(this,SessionManager.USERSESSION);
        HashMap<String,String> hm=sh.returnData();
        final String n=hm.get(SessionManager.FULLNAME);
        ok=(Button)findViewById(R.id.OK);
        user=(TextView)findViewById(R.id.user);
        hotel=(TextView)findViewById(R.id.hotel);
        phone=hm.get(SessionManager.PHONE);

        if(phone!=null)
        {
            gotoUserActivity();
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(),HotelRooms.class));
                    startActivity(new Intent(getApplicationContext(),HotelRooms.class));
                }
            });
        }
        else {
            gotoHotelActivity();
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(),UserPrfofilew.class));
                }
            });
        }


    }

    private void gotoHotelActivity() {
        user.setVisibility(View.VISIBLE);
    }

    private void gotoUserActivity() {
        hotel.setVisibility(View.VISIBLE);
    }


}