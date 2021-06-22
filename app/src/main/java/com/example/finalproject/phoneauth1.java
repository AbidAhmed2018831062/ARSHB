package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class phoneauth1 extends AppCompatActivity {
    TextView phoneNum;
    String codeBySystem;
    PinView pin;
    ImageView cancel;
    String name, password, email, phone, username, DO, star, des;
    Button verify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_phoneauth1);
        phoneNum = (TextView) findViewById(R.id.phoneNum);
        pin = (PinView) findViewById(R.id.pin);
        verify = (Button) findViewById(R.id.verify);
        cancel = (ImageView) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), LogIn_Or_SignUp.class));
            }
        });
        phone = "+88" + getIntent().getStringExtra("Phone");

        phoneNum.setText(phone);


verify.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        verify();
    }
});
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
                    Toast.makeText(phoneauth1.this, e.getMessage(), Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);
                    codeBySystem = s;
                }
            };

    private void verifyCode(String code) {
        if (code != null||!code.equals("")) {
            PhoneAuthCredential ph = PhoneAuthProvider.getCredential(codeBySystem, code);
            signInWithPhoneAuthCredential(ph);
        }

    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        FirebaseAuth abid = FirebaseAuth.getInstance();
        abid.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                           String phone3 = phone;
                            name = getIntent().getStringExtra("Name");
                            password = getIntent().getStringExtra("Password");
                            email = getIntent().getStringExtra("Email");
                            des = getIntent().getStringExtra("Des");
                            star= getIntent().getStringExtra("Star");
                            username = getIntent().getStringExtra("UserName");

                            Intent in=new Intent(getApplicationContext(), hotelRegister3.class);
                            in.putExtra("Name",name);
                            in.putExtra("UserName",username);
                            in.putExtra("Password",password);
                            in.putExtra("Email",email);

                            in.putExtra("Star",star);
                            in.putExtra("Des",des);

                            in.putExtra("Phone",phone3);
                            Toast.makeText(getApplicationContext(), phone3, Toast.LENGTH_LONG).show();
                            startActivity(in);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "UnSuccessful", Toast.LENGTH_LONG).show();

                        }

                    }
                });
    }

    public void verify() {
        String code=pin.getText().toString();
        verifyCode(code);

    }


}