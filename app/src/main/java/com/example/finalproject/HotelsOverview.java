package com.example.finalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class HotelsOverview extends AppCompatActivity implements OnChartValueSelectedListener {
LineChart line;
String name,phone;
int da[]=new int[32];
int value[]=new int[32];
int va=1;
int po=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotels_overview);
        line=(LineChart)findViewById(R.id.line);
        //line.setOnChartGestureListener(this);
        line.setOnChartValueSelectedListener(this);
        line.setDragEnabled(true);
        line.setScaleEnabled(false);
        final ArrayList<Entry> data=new ArrayList<>();
     SessionManagerHotels sh=new SessionManagerHotels(this,SessionManagerHotels.USERSESSION);
        HashMap<String,String> hm=sh.returnData();
        name=hm.get(SessionManagerHotels.FULLNAME);
        phone=hm.get(SessionManagerHotels.PHONE);
        Calendar c23=Calendar.getInstance();
        final int cday=Calendar.DATE;
        final int cmonth=Calendar.MONTH;
        int cyear=Calendar.YEAR;

        FirebaseDatabase.getInstance().getReference("Hotels").child(name).child("DataSet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              for(DataSnapshot ds: dataSnapshot.getChildren())
              {
                    ShowingData sho=ds.getValue(ShowingData.class);
                    int tday=Integer.parseInt(sho.getDay());
                    int tmonth=Integer.parseInt(sho.getMonth());
                        if((tday+cday)/2>cday&&Math.abs(cmonth-tmonth)<=1)
                        {
                            data.add(new Entry(tday,Float.parseFloat(sho.getOrders())));
                            da[po]=tday;
                            po++;
                        }

              }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        LineDataSet lin=new LineDataSet(data,"Hotel Overview");

        lin.setFillAlpha(110);
       lin.setLineWidth(30f);
       lin.setFormSize(20f);
        ArrayList<ILineDataSet> ar=new ArrayList<>();
         ar.add(lin);

        LineData d=new LineData(ar);
        line.setData(d);
    }


    @Override
    public void onValueSelected(Entry e, Highlight h) {
        float x=e.getX();
        float y=e.getY();
        View view= LayoutInflater.from(this).inflate(R.layout.hotelline,null,false);
        TextView date=(TextView)view.findViewById(R.id.date);
        TextView re=(TextView) view.findViewById(R.id.re);
        String s=date.getText().toString();
        String s1=re.getText().toString();
        date.setText(s+x+"");
        re.setText(s1+y+"");

        AlertDialog.Builder al=new AlertDialog.Builder(this);
        al.setView(view);
        al.setCancelable(true);
        AlertDialog alertDialog;
        alertDialog=al.create();
        al.show();



    }

    @Override
    public void onNothingSelected() {

    }
}