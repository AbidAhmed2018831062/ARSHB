package com.example.finalproject;

import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LogIn_Or_SignUp extends AppCompatActivity {
 Button b1,b2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_log_in__or__sign_up);
        b1=(Button)findViewById(R.id.login1);
        b2=(Button)findViewById(R.id.signup1);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getApplicationContext(),LogIn.class);
                 Pair[] pairs=new Pair[1];
                 pairs[0]=new Pair<View,String>(b1,"transitionLo");
                ActivityOptions ac=ActivityOptions.makeSceneTransitionAnimation(LogIn_Or_SignUp.this,pairs);
                startActivity(in,ac.toBundle());


            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pair[] p=new Pair[1];
                p[0]=new Pair<View,String>(b2,"transitionSign");
                ActivityOptions ac=ActivityOptions.makeSceneTransitionAnimation(LogIn_Or_SignUp.this,p);
                startActivity(new Intent(getApplicationContext(),CutomerOrOwner.class),ac.toBundle());
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(FirebaseAuth.getInstance().getCurrentUser()!=null) {
            startActivity(new Intent(LogIn_Or_SignUp.this, DashBoard.class));
            finish();
        }
        else
        {
            AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                    this);

// Setting Dialog Title
            alertDialog2.setTitle("Exit");

// Setting Dialog Message
            alertDialog2.setMessage("Are You Sure Want To Exit??");
            alertDialog2.setIcon(R.drawable.apptitle);
            alertDialog2.setPositiveButton("YES",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Write your code here to execute after dialog
                            Intent a = new Intent(Intent.ACTION_MAIN);
                            a.addCategory(Intent.CATEGORY_HOME);
                            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(a);

                        }
                    });
// Setting Negative "NO" Btn
            alertDialog2.setNegativeButton("NO",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

// Showing Alert Dialog
            alertDialog2.show();

        }
    }
}