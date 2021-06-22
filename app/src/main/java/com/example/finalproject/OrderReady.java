package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.util.LocaleData;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;

public class OrderReady extends AppCompatActivity {
String[] quas;
String qua;
Spinner qua1;
DatePicker start,end;
String start1,end1,price;
String month,month1;
ImageView back;
int p=1,p1=1;
int year,cmonth,day,day1,cmonth1,year1;
    String name;
    Button order;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_ready);
        name=getIntent().getStringExtra("Name");


        quas=getResources().getStringArray(R.array.qua);
        qua1=(Spinner)findViewById(R.id.qua);
        start=(DatePicker) findViewById(R.id.start);
        end=(DatePicker)findViewById(R.id.end);
        order=(Button)findViewById(R.id.confirm);
        back=(ImageView)findViewById(R.id.back);
       price=getIntent().getStringExtra("Price");
       if(price!=null)
         p1=Integer.parseInt(price);
        ArrayAdapter<String> ar=new ArrayAdapter<String>(this, R.layout.education,R.id.Edu,quas);
        qua1.setAdapter(ar);
       start.setMinDate(System.currentTimeMillis()-1000);
        long now=System.currentTimeMillis()-1000;
        end.setMinDate(System.currentTimeMillis()-1000);
        start.setMaxDate(now+(1000*60*60*24*7));
        end.setMaxDate(now+(1000*60*60*24*7)+(1000*60*60*24*7));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                year=start.getYear();
                cmonth=start.getMonth();
                day=start.getDayOfMonth();
                year1=end.getYear();
                cmonth1=end.getMonth();
                day1=end.getDayOfMonth();
                if(cmonth==1-1)
                    month="January";
                else if(cmonth==2-1)
                    month="February";
                else if(cmonth==3-1)
                    month="March";
                else if(cmonth==4-1)
                    month="April";
                else if(cmonth==5-1)
                    month="May";
                else  if(cmonth==6-1)
                    month="June";
                else if(cmonth==7-1)
                    month="July";
                else   if(cmonth==8-1)
                    month="August";
                else if(cmonth==9-1)
                    month="September";
                else if(cmonth==10-1)
                    month="October";
                else if(cmonth==11-1)
                    month="November";
                else month="December";
                qua=qua1.getSelectedItem().toString();
                if(cmonth1==1-1)
                    month1="January";
                else if(cmonth1==2-1)
                    month1="February";
                else if(cmonth1==3-1)
                    month1="March";
                else if(cmonth1==4-1)
                    month1="April";
                else if(cmonth1==5-1)
                    month1="May";
                else  if(cmonth1==6-1)
                    month1="June";
                else if(cmonth1==7-1)
                    month1="July";
                else   if(cmonth1==8-1)
                    month1="August";
                else if(cmonth1==9-1)
                    month1="September";
                else if(cmonth1==10-1)
                    month1="October";
                else if(cmonth1==11-1)
                    month1="November";
                else month1="December";
                start1=""+day+" "+month+" "+year;
                end1=""+day1+" "+month1+" "+year1;
                String ne=getIntent().getStringExtra("Rn");
                Intent in=new Intent(OrderReady.this,OrderConfirmation.class);
                in.putExtra("Name",name);
                in.putExtra("Qua",qua);
                in.putExtra("Start",start1);
                in.putExtra("End",end1);
                in.putExtra("Rn",ne);
                /*Calendar st= Calendar.getInstance();
                Calendar en=Calendar.getInstance();
                st.set(year,cmonth,day);
                en.set(year1,cmonth1,day1);
                Date o=st.getTime();
                Date t=en.getTime();
                long diff=difference(o,t);*/
                LocalDate d1= LocalDate.of(year,cmonth-1,day);
                LocalDate d2= LocalDate.of(year1,cmonth1-1,day1);
                long diff= Math.abs(Period.between(d2,d1).getDays());
                if(diff==0)
                    diff=1;
                in.putExtra("Diff",""+diff);
                Toast.makeText(getApplicationContext(),""+p1+" "+diff+" "+start1+"   "+end1+" "+qua,Toast.LENGTH_LONG).show();
                p=80*p1;
                in.putExtra("Price",""+p);
             startActivity(in);
            }
        });


    }
    public long difference(Date one, Date two)
    {
        long diff=(one.getTime()-two.getTime())/86400;
        return Math.abs(diff);
    }

    @Override
    public void onBackPressed() {
        OrderReady.super.onBackPressed();
    }
}