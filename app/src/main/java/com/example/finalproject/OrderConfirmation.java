package com.example.finalproject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
String rn12,HNAME=" ";
String email;
String fullname;
    String price;
    long jk;
    private static final int PAYPAL_REQUEST_CODE = 7171;
    private static PayPalConfiguration config=new PayPalConfiguration().
            environment(PayPalConfiguration.ENVIRONMENT_SANDBOX).
            clientId(Config.PAYPAL_CLIENT_ID);
    @Override
    protected void onDestroy() {
        stopService(new Intent(this,PayPalService.class));
        super.onDestroy();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
                        OrderShow or=new OrderShow(cdes.getText().toString(),hdes.getText().toString(),tot+"TK.",q+"",start1+"",end1+"",rname.getText().toString(),cdate.getText().toString());
                        DatabaseReference d=FirebaseDatabase.getInstance().getReference("Users").child(phone).child("Order");
                        or.setCname(cdes.getText().toString());
                        or.setHname(hdes.getText().toString());
                        or.setRname(rname.getText().toString());
                        Toast.makeText(getApplicationContext(),fullname,Toast.LENGTH_LONG).show();
                        DatabaseReference ho=FirebaseDatabase.getInstance().getReference("Hotels").child(fullname).child("Order");
                        Toast.makeText(getApplicationContext(),fullname,Toast.LENGTH_LONG).show();
                        ho.child("order"+jk).setValue(or);
                        d.child("order"+jk).setValue(or);

                        Random r12n=new Random();
                        long yui=r12n.nextInt(10000000);
                        Query c = FirebaseDatabase.getInstance().getReference("Hotels").child(fullname).child("DataSet").orderByChild("date").equalTo(start1);
                        c.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists())
                                {
                                    String total=dataSnapshot.child("Orders").getValue().toString();
                                    int ord;
                                    if(total==null){
                                        ord=1;
                                    }
                                    else {
                                        ord = Integer.parseInt(total);
                                        ord++;
                                    }


                                    HashMap o=new HashMap();
                                    o.put("orders",ord+"");

                                    FirebaseDatabase.getInstance().getReference("Hotels").child(fullname).child("DataSet").child(cdate.getText().toString()).updateChildren(o);
                                }
                                else
                                {
                                    String mon="";
                                    String day1="";
                                    day1+=cdate.getText().toString().charAt(0);
                                    day1=+cdate.getText().toString().charAt(1)+"";
                                    for(int i=2;i<cdate.getText().toString().length();i++)
                                    {
                                        if(Character.isWhitespace(cdate.getText().toString().charAt(i)))
                                            break;
                                        else
                                            mon+=cdate.getText().toString().charAt(i);
                                    }
                                    mon=mon.toLowerCase();
                                    if(mon=="january")
                                        mon=1+"";
                                    else if(mon=="february")
                                        mon=2+"";
                                    else if(mon=="march")
                                        mon=3+"";
                                    else if(mon=="april")
                                        mon=4+"";
                                    else if(mon=="may")
                                        mon=5+"";
                                    else if(mon=="june")
                                        mon=6+"";
                                    else if(mon=="july")
                                        mon=7+"";
                                    else if(mon=="august")
                                        mon=8+"";
                                    else if(mon=="september")
                                        mon=9+"";
                                    else if(mon=="october")
                                        mon=10+"";
                                    else if(mon=="november")
                                        mon=11+"";
                                    else if(mon=="december")
                                        mon=12+"";
                                    else
                                        mon=7+"";

                                       ShowingData sho=new ShowingData(cdate.getText().toString(),"1", day1,mon);
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