package com.example.finalproject;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.stripe.android.ApiResultCallback;
import com.stripe.android.PaymentIntentResult;
import com.stripe.android.Stripe;
import com.stripe.android.model.ConfirmPaymentIntentParams;
import com.stripe.android.model.PaymentIntent;
import com.stripe.android.model.PaymentMethodCreateParams;
import com.stripe.android.view.CardInputWidget;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CheckoutActivityJava extends AppCompatActivity {
    // 10.0.2.2 is the Android emulator's alias to localhost
TextView card,postal,amount,des;
String email,aemail,fbn;
Button pa;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_stripe);
       card=(TextView) findViewById(R.id.card);
       postal=(TextView) findViewById(R.id.postal);
       pa=(Button) findViewById(R.id.pa);
       amount=(TextView) findViewById(R.id.amount);
       des=(TextView) findViewById(R.id.des);
        Toast.makeText(getApplicationContext(),email,Toast.LENGTH_LONG).show();
       pa.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               startActivity(new Intent(getApplicationContext(),UserPrfofilew.class));
               finish();
           }
       });
       Intent in=getIntent();
       aemail=in.getStringExtra("Email");
       try{
           JSONObject j=new JSONObject(in.getStringExtra("paymentDetails"));
           showDetails(j.getJSONObject("response"),in.getStringExtra("paymentAmount"),in.getStringExtra("name"),in.getStringExtra("rname"),
                   in.getStringExtra("rCount"));
       } catch (JSONException e) {
           e.printStackTrace();
       }

    }

    private void showDetails(JSONObject response, String paymentAmount,String hname,String rname,String q) {
        try{
            des.setText("Your booking for \""+hname+"\" hotel for the room named "+ rname+ "(rooms count: "+q+") was successful.");
           String j=response.getString("state");

            SessionManager sh=new SessionManager(this,SessionManager.USERSESSION);

            HashMap<String,String> hm=sh.returnData();
            email=hm.get(SessionManager.EMAIL);
            fbn=hm.get(SessionManager.FULLNAME);
            String phone=hm.get(SessionManager.PHONE);
            card.setText(response.getString("id"));
            amount.setText(paymentAmount+ "Tk.");
            postal.setText(" Approved");


            String subject= "Your Booking for hotel"+hname+" was successful"+"\n\n"+"Transaction id: "+response.getString("id")+"\nAmount: "+paymentAmount+"Taka";
            final String Email="hotelarshb7@gmail.com";
            final String pass="arshbhotelABIDRAJU";
            Properties prop = new Properties();
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", "465");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.socketFactory.port", "465");
            prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(Email, pass);
                }
            });
            try {

                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(Email));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email));
                message.setSubject("Successful Hotel Booking");
                message.setText(subject);

                new CheckoutActivityJava.SendEmail().execute(message);
                Message message1 = new MimeMessage(session);
                message1.setFrom(new InternetAddress(Email));
                message1.setRecipients(Message.RecipientType.TO, InternetAddress.parse(aemail));
                String s="Hotel "+hname+" has new orders."+"\nRoom Name: "+rname+"\nRooms Count: "+q+"\n Custromer Details: \nName: "+
                        fbn+"\nEmail: "+email+"\nPhone No: "+phone;
                message1.setSubject("New Order");
                message1.setText(s);

                new CheckoutActivityJava.SendEmail().execute(message1);

            } catch (MessagingException e) {
                e.printStackTrace();
                            /*StrictMode.ThreadPolicy po=new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(po);*/
            }
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    private class SendEmail extends AsyncTask<Message, String, String> {

        @Override
        protected String doInBackground(Message... messages) {

            try {
                Transport.send(messages[0]);
                return "success";
            } catch (MessagingException e) {
                e.printStackTrace();
                return "error";
            }


        }
    }
}
