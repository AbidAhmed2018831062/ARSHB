package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import java.util.HashMap;
import java.util.Objects;

public class NewPassword extends AppCompatActivity {
    TextInputLayout pass, cpass;
    String pass1, cpass1,phone1;
    Button next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        pass = (TextInputLayout) findViewById(R.id.password);
        next = (Button) findViewById(R.id.next);
        cpass = (TextInputLayout) findViewById(R.id.confirmPassword);

        phone1=getIntent().getStringExtra("Phone");
        Toast.makeText(getApplicationContext(),phone1,Toast.LENGTH_LONG).show();
        if (validatePassword()) {
            if(getIntent().getStringExtra("name").equals("hotel")){
                Query c1 = FirebaseDatabase.getInstance().getReference("Hotels").child("HotelsPassword").orderByChild("phone").equalTo(phone1);

                c1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists()) {
                                final String n=dataSnapshot.child(phone1).child("name").getValue(String.class);
                                FirebaseDatabase.getInstance().getReference("Hotels").child(n).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {

                                        String des=dataSnapshot1.child("des").getValue().toString();
                                        String email=dataSnapshot1.child("email").getValue().toString();
                                        String user=dataSnapshot1.child("username").getValue().toString();
                                        String url=dataSnapshot1.child("url").getValue().toString();
                                        String star=dataSnapshot1.child("star").getValue().toString();
                                        SessionManagerHotels sh = new SessionManagerHotels(NewPassword.this, SessionManagerHotels.USERSESSION);
                                        Toast.makeText(getApplicationContext(),star+"",Toast.LENGTH_LONG).show();
                                        sh.loginSession(n,email,star,phone1,pass1,des,user,url);
                                        FirebaseInstanceId.getInstance().getInstanceId()
                                                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                                        if (task.isSuccessful()) {
                                                            String token = Objects.requireNonNull(task.getResult()).getToken();
                                                            HashMap t=new HashMap();
                                                            t.put("token",token);
                                                            FirebaseDatabase.getInstance().getReference("Hotels").child(n).child("Token").updateChildren(t);

                                                        }

                                                    }
                                                });
                                        HashMap hmp=new HashMap();
                                        hmp.put("password",pass1);
                                        FirebaseDatabase.getInstance().getReference("Hotels").child("HotelsPassword").child(n).updateChildren(hmp);

                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {

                                    }
                                });
                        } else {
                            Toast.makeText(getApplicationContext(), "Phone and Password does not match"+phone1+"/"+phone1+"Please enter right informations", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
            else
            {
                DatabaseReference db = FirebaseDatabase.getInstance().getReference("Users");
                db.child(phone1).child("password").setValue(pass1);
           db.child(phone1).addListenerForSingleValueEvent(new ValueEventListener() {
               @Override
               public void onDataChange(@NonNull DataSnapshot d) {

                   String email = d.child(phone1).child("email").getValue(String.class);
                   String dob = d.child(phone1).child("dob").getValue(String.class);
                   String gender = d.child(phone1).child("gender").getValue(String.class);
                   String fullname = d.child(phone1).child("name").getValue(String.class);
                   final String phone2 = d.child(phone1).child("phone").getValue(String.class);
                   String username = d.child(phone1).child("username").getValue(String.class);
                   String edu = d.child(phone1).child("education").getValue(String.class);
                   String location = d.child(phone1).child("location").getValue(String.class);
                   String bio = d.child(phone1).child("bio").getValue(String.class);
                   String signupdate = d.child(phone1).child("signupdate").getValue(String.class);
                   String socialmedia = d.child(phone1).child("socialmedia").getValue(String.class);
                   SessionManager sh = new SessionManager(NewPassword.this, SessionManager.USERSESSION);
                   FirebaseInstanceId.getInstance().getInstanceId()
                           .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                               @Override
                               public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                   if (task.isSuccessful()) {
                                       String token = Objects.requireNonNull(task.getResult()).getToken();
                                       HashMap t=new HashMap();
                                       t.put("token",token);
                                       FirebaseDatabase.getInstance().getReference("Users").child(phone2).child("Token").updateChildren(t);

                                   }

                               }
                           });
                   sh.loginSession(fullname, email, gender, phone2, pass1, dob, username, location, socialmedia, bio, edu, signupdate);
               }

               @Override
               public void onCancelled(@NonNull DatabaseError databaseError) {

               }
           });
            }
        }
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getIntent().getStringExtra("name").equals("hotel")){
                    startActivity(new Intent(NewPassword.this, HotelRooms.class));
                    finish();
                }
                else {
                    startActivity(new Intent(NewPassword.this, Success.class));
                    finish();
                }
            }
        });

    }

    public boolean validatePassword() {
        pass1 = pass.getEditText().getText().toString().trim();
        cpass1 = cpass.getEditText().getText().toString().trim();
        if (pass1.isEmpty()) {
            pass.setError("This field needs to be filled");
            pass.requestFocus();
            return false;
        } else if (pass1.length() < 6) {
            pass.setError("Password has to be at least of length 6");
            pass.requestFocus();
            return false;
        } else if (!pass1.equals(cpass1)) {
            pass.setError("Password and Confirm Password have to be same");
            pass.requestFocus();
            return false;
        } else
            return true;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(NewPassword.this, LogIn.class));
        finish();
    }
}