package com.example.finalproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

public class OrderConfirmation extends AppCompatActivity {
TextView cdate,start,end,rname,qua,total,total1,hdes,cdes;
String cdes1,start1,end1,rname1,qua1,total2,total3,hdes1,name;
long tot;
ImageView back;
Button confirm;
String diff;
long di;
String n1;
int q;
String phone;
    Calendar cal=Calendar.getInstance();
String rn12,HNAME=" ";
String email;
String fullname;
    String price;
    static int totalOrders;
    long jk;
    long ear=0,oRd=0;
    private static final int PAYPAL_REQUEST_CODE = 7171;
    private static PayPalConfiguration config=new PayPalConfiguration().
            environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).
            clientId(Config.PAYPAL_CLIENT_ID);
    static long earning=0;
    @Override
    protected void onDestroy() {
        stopService(new Intent(this,PayPalService.class));
        super.onDestroy();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_order_confirmation);
        SessionManager sh=new SessionManager(this,SessionManager.USERSESSION);
        Intent I=new Intent(this,PayPalService.class);
        I.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
        startService(I);
        HashMap<String,String> hm=sh.returnData();
        final String n=hm.get(SessionManager.FULLNAME);
         phone=hm.get(SessionManager.PHONE);

        cdate=(TextView)findViewById(R.id.cdate);
        confirm=(Button)findViewById(R.id.confirm);

rn12=getIntent().getStringExtra("rn12");
        Query c = FirebaseDatabase.getInstance().getReference("Hotels").orderByChild("name").equalTo(rn12);
        c.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot d) {
                 fullname = d.child(rn12).child("name").getValue(String.class);

                 email = d.child(rn12).child("email").getValue(String.class);

                String address=d.child(rn12).child("address").getValue(String.class);
                hdes.setText(rn12+"\n"+address);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        start=(TextView)findViewById(R.id.start);
        end=(TextView)findViewById(R.id.end);
        rname=(TextView)findViewById(R.id.rname);
        qua=(TextView)findViewById(R.id.qua);
        total=(TextView)findViewById(R.id.total);
        total1=(TextView)findViewById(R.id.total1);
        hdes=(TextView)findViewById(R.id.hdes);
        cdes=(TextView)findViewById(R.id.cdes);
        cdes.setText(n+"\n"+"Phone: "+phone);
        back=(ImageView)findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
       qua1=getIntent().getStringExtra("Qua");
       start1=getIntent().getStringExtra("Start");
       end1=getIntent().getStringExtra("End");
       start.setText(start1);
       end.setText(end1);
name=getIntent().getStringExtra("Name");
        q=Integer.parseInt(qua1);
       qua.setText(qua1);
        price=getIntent().getStringExtra("Price");
 tot=Integer.parseInt(price);

        rname.setText(name);
total.setText(price+"TK.");
total1.setText("Total: "+price+"TK.");
        cdate.setText(getIntent().getStringExtra("Cdate"));


        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                process();


            }
        });

    }


public void process()
{
    float t1=tot/(80*10);
    String gh=""+t1;
    PayPalPayment pp=new PayPalPayment(new BigDecimal(String.valueOf(gh)) ,
            "USD","Advance Payment", PayPalPayment.PAYMENT_INTENT_SALE);

    Intent in= new Intent(this,PaymentActivity.class);
    in.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,config);
    in.putExtra(PaymentActivity.EXTRA_PAYMENT,pp);
    startActivityForResult(in,PAYPAL_REQUEST_CODE);

}
    @SuppressLint("MissingSuperCall")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == PAYPAL_REQUEST_CODE) {
            if(resultCode==RESULT_OK)
            {
                PaymentConfirmation confirmation=data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                if(confirmation!=null)
                {
                    try {

                        Random r12n=new Random();
                        long yui=r12n.nextInt(10000000);
                  FirebaseDatabase.getInstance().getReference("Hotels").child(fullname).child("Earning").addListenerForSingleValueEvent(new ValueEventListener() {
                          @Override
                          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                             Earning ds=dataSnapshot.getValue(Earning.class);
                              oRd=ds.getOrders();
                              ear=ds.getEarning();
                              ear+=tot;
                              oRd+=1;
                              HashMap uio=new HashMap();
                              uio.put("earning",ear);
                              uio.put("orders",oRd);
                              FirebaseDatabase.getInstance().getReference("Hotels").child(fullname).child("Earning").updateChildren(uio);


                          }

                          @Override
                          public void onCancelled(@NonNull DatabaseError databaseError) {

                          }
                      });
                       OrderShow or=new OrderShow(cdes.getText().toString(),hdes.getText().toString(),tot+"TK.",q+"",start1+"",end1+"",rname.getText().toString(),cdate.getText().toString(),"ahmedabid3409@gmail.com");
                        DatabaseReference d=FirebaseDatabase.getInstance().getReference("Users").child(phone).child("Order");
                        or.setCname(cdes.getText().toString());
                        or.setHname(hdes.getText().toString());
                        or.setRname(rname.getText().toString());
                        Toast.makeText(getApplicationContext(),fullname,Toast.LENGTH_LONG).show();
                        DatabaseReference ho=FirebaseDatabase.getInstance().getReference("Hotels").child(fullname).child("Order");
                        Toast.makeText(getApplicationContext(),fullname,Toast.LENGTH_LONG).show();
                        ho.child("order"+yui).setValue(or);
                        d.child("order"+yui).setValue(or);


                        final int cmonth=cal.get(Calendar.MONTH);
                        String month="";
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
                        month=month.toLowerCase();
                        Query orM=FirebaseDatabase.getInstance().getReference("Hotels").child(fullname).child("OrderMonths").orderByChild("month").equalTo(month);
                        final String finalMonth = month;
                        orM.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists())
                                {
                                    long orders= (long) dataSnapshot.child(finalMonth).child("orders").getValue();
                                    orders++;

                                    HashMap o=new HashMap();
                                    o.put("orders",orders);
                                    FirebaseDatabase.getInstance().getReference("Hotels").child(fullname).child("OrderMonths").child(finalMonth).updateChildren(o);
                                }
                                else
                                {
                                          ShowingData shp=new ShowingData(finalMonth,1);
                                          FirebaseDatabase.getInstance().getReference("Hotels").child(fullname).child("OrderMonths").child(finalMonth).setValue(shp);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        Query c = FirebaseDatabase.getInstance().getReference("Hotels").child(fullname).child("DataSet").orderByChild("date").equalTo(cal.get(Calendar.DAY_OF_MONTH)+" "+cal.get(Calendar.MONTH)+" "+cal.get(Calendar.YEAR));
                        c.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists())
                                {
                                    long orders= (long) dataSnapshot.child(cal.get(Calendar.DAY_OF_MONTH)+" "+cal.get(Calendar.MONTH)+" "+cal.get(Calendar.YEAR)).child("orders").getValue();
                                    orders++;

                                    HashMap o=new HashMap();
                                    o.put("orders",orders);

                                    FirebaseDatabase.getInstance().getReference("Hotels").child(fullname).child("DataSet").child(cal.get(Calendar.DAY_OF_MONTH)+" "+cal.get(Calendar.MONTH)+" "+cal.get(Calendar.YEAR)).updateChildren(o);
                                }
                                else
                                {


                                       ShowingData sho=new ShowingData(cdate.getText().toString(),1, cal.get(Calendar.DAY_OF_MONTH),finalMonth);
                                    FirebaseDatabase.getInstance().getReference("Hotels").child(fullname).child("DataSet").child(cdate.getText().toString()).setValue(sho);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                     FirebaseDatabase.getInstance().getReference("Users").child(phone).child("Payment").child(q+start1+end1+getIntent().getStringExtra("Price")+""+getIntent().getStringExtra("Cdate")).removeValue();
                  //  Toast.makeText(getApplicationContext(),q+start1+end1+getIntent().getStringExtra("Price")+""+getIntent().getStringExtra("Cdate"),Toast.LENGTH_LONG).show();
                        String paymentDetails =confirmation.toJSONObject().toString(4);
                        startActivity(new Intent(this, CheckoutActivityJava.class).
                                putExtra("paymentDetails",paymentDetails).
                                putExtra("paymentAmount",tot+" ").
                        putExtra("name",fullname).
                                putExtra("rname",name).putExtra("rCount",q+" ").putExtra("User",phone).putExtra("Email",email));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                //   HNAME=getIntent().getStringExtra("HNAME");



                }
                else if(resultCode== Activity.RESULT_CANCELED)
                {
                    Toast.makeText(getApplicationContext(),"Sorry!! Payment Did not occur!!",Toast.LENGTH_LONG).show();
                }
            }
            else if(resultCode==PaymentActivity.RESULT_EXTRAS_INVALID)
            {
                Toast.makeText(getApplicationContext(),resultCode+" ",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getApplicationContext(),requestCode+" ",Toast.LENGTH_LONG).show();
            }


        }
    }
    public void send()
    {

    }

}