package com.example.finalproject;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class HotelsOverview extends AppCompatActivity implements OnChartValueSelectedListener {
    LineChart line;
    String name, phone;
    float da[] = new float[32];
    float value[] = new float[32];
    int va = 0;
    int po = 0;
    LineData d;
    String month = "";
    LineDataSet ar = new LineDataSet(null, null);
    ArrayList<ILineDataSet> abc = new ArrayList<>();
    LineData ld;
    HashMap<String,Integer> hm345=new HashMap<>();
    BarChart b;
     int cmonth;
     float avg=0;
     TextView orders,head,earning,avgO,avgE;
    int cday,cy;
    float avg1;
    long earning1,totalOrders;
    Earning ds;
    String months[]={"january","february","march","april","may","june","july","august","september","october","november","december"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_hotels_overview);
        line = (LineChart) findViewById(R.id.line);
        //line.setOnChartGestureListener(this);
        line.setOnChartValueSelectedListener(this);
        line.setDragEnabled(true);
        line.setScaleEnabled(false);
        b=(BarChart)findViewById(R.id.bar);
        head=(TextView)findViewById(R.id.head);
        orders=(TextView)findViewById(R.id.orders);
        earning=(TextView)findViewById(R.id.earning);
        avgO=(TextView)findViewById(R.id.avgO);
        avgE=(TextView)findViewById(R.id.avgE);

        SessionManagerHotels sh = new SessionManagerHotels(this, SessionManagerHotels.USERSESSION);
        HashMap<String, String> hm = sh.returnData();
        name = hm.get(SessionManagerHotels.FULLNAME);
        FirebaseDatabase.getInstance().getReference("Hotels").child(name).child("Earning").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              ds=dataSnapshot.getValue(Earning.class);
                totalOrders=ds.getOrders();
                earning1=ds.getEarning();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        head.setText(name);

        phone = hm.get(SessionManagerHotels.PHONE);
        Calendar c23 = Calendar.getInstance();
        cmonth = c23.get(Calendar.MONTH);
        cy = c23.get(Calendar.YEAR);
        cday = c23.get(Calendar.DAY_OF_MONTH);

        if (cmonth == 1 - 1)
            month = "January";
        else if (cmonth == 2 - 1)
            month = "February";
        else if (cmonth == 3 - 1)
            month = "March";
        else if (cmonth == 4 - 1)
            month = "April";
        else if (cmonth == 5 - 1)
            month = "May";
        else if (cmonth == 6 - 1)
            month = "June";
        else if (cmonth == 7 - 1)
            month = "July";
        else if (cmonth == 8 - 1)
            month = "August";
        else if (cmonth == 9 - 1)
            month = "September";
        else if (cmonth == 10 - 1)
            month = "October";
        else if (cmonth == 11 - 1)
            month = "November";
        else month = "December";
        FirebaseDatabase.getInstance().getReference("Hotels").child(name).child("Opening").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              long omon= (long) dataSnapshot.child("omon").getValue();
              long oyear= (long) dataSnapshot.child("oyear").getValue();
              if(omon>cmonth)
              {
                  avg=earning1/(omon+cmonth);
                  avg1=totalOrders/(omon+cmonth);
              }
              else
              {
                  if(oyear==cy)
                  {
                      if(cmonth==omon)
                      {
                          avg=earning1;
                          avg1=totalOrders;
                      }
                      else {
                          avg =earning1 / (cmonth - omon);
                          avg1 =totalOrders / (cmonth - omon);
                      }
                  }
                  else
                  {
                      avg=earning1/(cmonth-omon)*((cy-oyear));
                      avg1=totalOrders/(cmonth-omon)*((cy-oyear));
                  }
              }
              avgO.setText(avg1+"");
              avgE.setText(avg+"");
                orders.setText(totalOrders+"");
                earning.setText(earning1+"");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
            FirebaseDatabase.getInstance().getReference("Hotels").child(name).child("OrderMonths").addValueEventListener(new ValueEventListener() {

                ArrayList<BarEntry> bar = new ArrayList<>();
                ArrayList<String> label=new ArrayList<>();
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    bar.clear();
                    for(DataSnapshot ds:dataSnapshot.getChildren()) {
                        ShowingData shdt = ds.getValue(ShowingData.class);
                        hm345.put(shdt.getMonth(),shdt.getOrders());
                        //Toast.makeText(getApplicationContext(),shdt.getMonth()+" "+shdt.getOrders(),Toast.LENGTH_LONG).show();
                    }
                    for(int i=0;i<cmonth+1;i++)
                    {
                        if(hm345.get(months[i])!=null)
                        {
                          bar.add(new BarEntry(i,hm345.get(months[i])));
                          Toast.makeText(getApplicationContext(),months[i]+" "+hm345.get(months[i]),Toast.LENGTH_LONG).show();


                        }
                        else
                            bar.add(new BarEntry(i,0));
                        label.add(months[i]);   Log.d(months[i],hm345.get(months[i])+"");

                    }
                    showBarGraph(bar,label);
                }



                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


   /*
    final ArrayList<ILineDataSet> ar=new ArrayList<>();
    ar.add(lin);
 d=new LineData(ar);
    line.setData(d);*/
        for (int i = 0; i < 31; i++)
            da[i] = 0;
        month = month.toLowerCase();
        final String finalMonth = month;
        FirebaseDatabase.getInstance().getReference("Hotels").child(name).child("DataSet").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Entry> data = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    ShowingData sho = ds.getValue(ShowingData.class);
                    int tday = sho.getDay();
                    String tmonth = sho.getMonth().toLowerCase();
                    float ord = sho.getOrders();
                    if (tmonth.equals(finalMonth)) {

                        da[tday] = ord;
                    }

                }
                for (int i = 1; i <= cday; i++) {
                    if (da[i] == 0)
                        data.add(new Entry(i, 0));
                    else
                        data.add(new Entry(i, da[i]));
                }
                showChart(data);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
   /* for(int i=0;i<po;i++) {
        data.clear();

        float c=da[i];
        float d1=value[i];

        Toast.makeText(getApplicationContext(),c+" "+d1,Toast.LENGTH_LONG).show();





    }*/
    }
    private void showBarGraph(ArrayList<BarEntry> bar, ArrayList<String> label) {
        BarDataSet barD=new BarDataSet(bar,"Monthly Orders");
        barD.setColors(ColorTemplate.COLORFUL_COLORS);
        Description des=new Description();
        des.setText("Year "+2021);
       b.setDescription(des);
       BarData ba=new BarData(barD);
       b.setData(ba);
        XAxis xa=b.getXAxis();
        xa.setValueFormatter(new IndexAxisValueFormatter(label));
        xa.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xa.setDrawAxisLine(false);
        xa.setDrawGridLines(false);
        xa.setGranularity(1f);
        xa.setLabelCount(cmonth+1);
        xa.setLabelRotationAngle(0);
        b.animateY(2000);
        b.getXAxis().setTextColor(Color.WHITE);
        b.getBarData().setValueTextColor(Color.WHITE);
        b.getBarData().setValueTextSize(10f);
        b.getLegend().setTextColor(Color.WHITE);

        b.getDescription().setTextColor(Color.WHITE);
        b.getDescription().setPosition(320,80);
        b.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                String x = e.getX() + "";
                String x1 = "";
                String y1 = "";
                String y = e.getY() + "";
                for (int i = 0; i < x.length(); i++) {
                    if (x.charAt(i) == '.')
                        break;
                    x1 += x.charAt(i);
                }
                for (int i = 0; i < y.length(); i++) {
                    if (y.charAt(i) == '.')
                        break;
                    y1 += y.charAt(i);
                }
                View view = LayoutInflater.from(HotelsOverview.this).inflate(R.layout.hotelline, null, false);
                TextView date = (TextView) view.findViewById(R.id.date);
                TextView re = (TextView) view.findViewById(R.id.re);
                String s = date.getText().toString();
                String s1 = re.getText().toString();
                date.setText(s + x + " " + month);

                if (!y1.equals("0")) {

                    re.setText(s1 + y1 + "");
                } else {
                    re.setText("You have recieved no orders on the month of " + x);
                }

                AlertDialog.Builder al = new AlertDialog.Builder(HotelsOverview.this);
                al.setView(view);
                al.setCancelable(true);
                AlertDialog alertDialog;
                alertDialog = al.create();
                al.show();


            }

            @Override
            public void onNothingSelected() {

            }
        });
        b.invalidate();
    }
    public void showChart(ArrayList<Entry> data) {
        ar.setValues(data);
        ar.setFillAlpha(110);
        ar.setLineWidth(5f);
        ar.setFormSize(20f);
        ar.setCircleColor(R.color.makeUplight);
        ar.setLabel("Current Month Order: ");
        abc.clear();
        abc.add(ar);
        Description des=new Description();

        des.setText("Report of "+month);

        line.setDescription(des);
        line.getDescription().setPosition(220,80);
        line.getDescription().setTextColor(Color.WHITE);
         line.getXAxis().setTextColor(Color.WHITE);
        line.getLegend().setTextColor(Color.WHITE);
        ld = new LineData(abc);
        YAxis rightAxis = line.getAxisRight();

        //Set the y-axis on the right of the chart to be disabled
        rightAxis.setEnabled(true);
        YAxis leftAxis = line.getAxisLeft();
        //Set the y-axis on the left of the chart to be disabled
        leftAxis.setEnabled(true);
        line.getAxisRight().setTextColor(Color.WHITE);
        line.getAxisLeft().setTextColor(Color.WHITE);
        //Set the x axis
        XAxis xAxis = line.getXAxis();
        xAxis.setTextColor(Color.parseColor("#ffffff"));
        xAxis.setTextSize(11f);
        xAxis.setAxisMinimum(0f);
        xAxis.setDrawAxisLine(true);//Whether to draw the axis
        xAxis.setDrawGridLines(false);//Set the line corresponding to each point on the x-axis
        xAxis.setDrawLabels(true);//Draw label refers to the corresponding value on the x axis
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        //Set the display position of the x axis
        xAxis.setGranularity(1f);//X-axis label redrawing is prohibited after zooming in
        line.clear();
        line.setData(ld);
        line.invalidate();

    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {
        String x = e.getX() + "";
        String x1 = "";
        String y1 = "";
        String y = e.getY() + "";
        for (int i = 0; i < x.length(); i++) {
            if (x.charAt(i) == '.')
                break;
            x1 += x.charAt(i);
        }
        for (int i = 0; i < y.length(); i++) {
            if (y.charAt(i) == '.')
                break;
            y1 += y.charAt(i);
        }
        View view = LayoutInflater.from(this).inflate(R.layout.hotelline, null, false);
        TextView date = (TextView) view.findViewById(R.id.date);
        TextView re = (TextView) view.findViewById(R.id.re);
        String s = date.getText().toString();
        String s1 = re.getText().toString();
        date.setText(s + x1 + " " + month);

        if (!y1.equals("0")) {

            re.setText(s1 + y1 + "");
        } else {
            re.setText("You have recieved no orders on " + date.getText().toString());
        }

        AlertDialog.Builder al = new AlertDialog.Builder(this);
        al.setView(view);
        al.setCancelable(true);
        AlertDialog alertDialog;
        alertDialog = al.create();
        al.show();


    }

    @Override
    public void onNothingSelected() {

    }
}