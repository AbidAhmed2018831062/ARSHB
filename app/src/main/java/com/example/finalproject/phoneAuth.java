package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.chaos.view.PinView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.concurrent.TimeUnit;

public class phoneAuth extends AppCompatActivity {
    TextView phoneNum;
    String codeBySystem;
    PinView pin;
    ImageView cancel;
    String name, password, email, phone, dob, gender, username, DO, star, des;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_phone_auth);
        phoneNum = (TextView) findViewById(R.id.phoneNum);
        pin = (PinView) findViewById(R.id.pin);
        cancel = (ImageView) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LogIn_Or_SignUp.class));
            }
        });
        phone = "+88" + getIntent().getStringExtra("Phone");
        name = getIntent().getStringExtra("Name");
        password = getIntent().getStringExtra("Password");
        email = getIntent().getStringExtra("Email");
        dob = getIntent().getStringExtra("Age");
        gender = getIntent().getStringExtra("Gender");
        username = getIntent().getStringExtra("UserName");
        phoneNum.setText(phone);
        DO = getIntent().getStringExtra("Do");


        sendVerification(phone);
    }

    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), LogIn_Or_SignUp.class));

    }

    public void sendVerification(String phone) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phone,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBacks
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks =
            new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
                    String code = phoneAuthCredential.getSmsCode();
                    if (code != null) {
                        pin.setText(code);
                        verifyCode(code);
                    }

                }

                @Override
                public void onVerificationFailed(FirebaseException e) {
                    Toast.makeText(phoneAuth.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem = s;
                }
            };

    private void verifyCode(String code) {
        if (code != null&&!code.equals("")) {
            PhoneAuthCredential ph = PhoneAuthProvider.getCredential(codeBySystem, code);
            signInWithPhoneAuthCredential(ph);
        }
        else
            Toast.makeText(getApplicationContext(), "Enter Code First", Toast.LENGTH_LONG).show();


    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth abid = FirebaseAuth.getInstance();
        abid.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (DO.equals("update1")) {
                                Intent in = new Intent(phoneAuth.this, NewPassword.class);
                                in.putExtra("Phone", phone);
                                in.putExtra("name", "user");
                                startActivity(in);
                                finish();
                            }
                            else  if (DO.equals("update2")) {
                                Intent in = new Intent(phoneAuth.this, NewPassword.class);
                                in.putExtra("Phone",  getIntent().getStringExtra("Phone"));
                                in.putExtra("name", "hotel");
                                startActivity(in);
                                finish();
                            }

                            else {
                                storeUserData();
                                Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_LONG).show();
                                Intent in = new Intent(phoneAuth.this,SignUp4.class);
                                in.putExtra("Phone", phone);
                                in.putExtra("Name",name);
                                in.putExtra("UserName",username);
                                in.putExtra("Password",password);
                                in.putExtra("Email",email);
                                in.putExtra("Gender",gender);
                                in.putExtra("Age",dob);
                                startActivity(in);
                                finish();

                            }


                        } else {
                            Toast.makeText(getApplicationContext(), "Wrong Code", Toast.LENGTH_LONG).show();

                        }
                    }
                });
    }

    public void verify(View view) {
        String code=pin.getText().toString();
        if(code!=null)
        verifyCode(code);

    }

    private void storeUserData()
    {
        FirebaseAuth auth=FirebaseAuth.getInstance();
        DatabaseReference re=FirebaseDatabase.getInstance().getReference("Users");
        String count="1";
        Data d=new Data(name,password,email,phone,dob,gender,username,count);


        re.child(phone).setValue(d);
    }
}