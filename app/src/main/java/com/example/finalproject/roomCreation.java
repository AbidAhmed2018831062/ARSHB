package com.example.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class roomCreation extends AppCompatActivity {
 LinearLayout layoutList;
 Button rooms,submit;
 ArrayList<Rooms> list=new ArrayList<>();
 DatabaseReference db,db1;
 FirebaseDatabase fb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_room_creation);
        rooms=(Button) findViewById(R.id.rooms);
        layoutList=(LinearLayout) findViewById(R.id.linear);
        submit=(Button) findViewById(R.id.submit);
        rooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addView();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(check()){
                    Intent in=new Intent(roomCreation.this, HotelRooms.class);

                    in.putExtra("Name",getIntent().getStringExtra("Name").toString());

                   startActivity(in);
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Something went wrong, Please try again", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    public void addView()
    {
        View roomView=getLayoutInflater().inflate(R.layout.roomcreation1, null,false);
        TextInputLayout se=(TextInputLayout) roomView.findViewById(R.id.service);
        TextInputLayout pr=(TextInputLayout)roomView.findViewById(R.id.price);
        TextInputLayout rn=(TextInputLayout)roomView.findViewById(R.id.rn);
        Button img=(Button) roomView.findViewById(R.id.cancel);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               layoutList.removeView(view);
        }});


     layoutList.addView(roomView);
    }
    public boolean check()
    {
        list.clear();
        boolean r=true;
        db=fb.getInstance().getReference("Hotels").child(getIntent().getStringExtra("Name").toString()).child("rooms");
        for(int i=0;i<layoutList.getChildCount();i++)
        {
           View view=layoutList.getChildAt(i);
            TextInputLayout se=(TextInputLayout) view.findViewById(R.id.service);

            Rooms roo=new Rooms();
           db1=db.child("rooms"+ i);
           if(!se.getEditText().getText().toString().equals(""))
           {
           roo.setServices(se.getEditText().getText().toString());
           }
           else {
               Toast.makeText(getApplicationContext(),"All field needs to be filled", Toast.LENGTH_LONG).show();
               r = false;
              break;
           }

            TextInputLayout price=(TextInputLayout)view.findViewById(R.id.price);
            if(!price.getEditText().getText().toString().equals(""))
            {
                roo.setPrice(price.getEditText().getText().toString());
            }
            else {
                Toast.makeText(getApplicationContext(),"All field needs to be filled", Toast.LENGTH_LONG).show();
                r = false;
                break;
            }
            TextInputLayout rn=(TextInputLayout)view.findViewById(R.id.rn);
            if(!rn.getEditText().getText().toString().equals(""))
            {
                roo.setRoomname(rn.getEditText().getText().toString());
            }
            else {
                Toast.makeText(getApplicationContext(),"All field needs to be filled", Toast.LENGTH_LONG).show();
                r = false;
                break;
            }

           db1.setValue(roo);
            list.add(roo);

        }
        if(list.size()==0)
        {
            Toast.makeText(getApplicationContext(),"Add Rooms First", Toast.LENGTH_LONG).show();
            r=false;
        }
        else if (!r)
        {
            Toast.makeText(getApplicationContext(),"All field needs to be filled", Toast.LENGTH_LONG).show();
        }
        return r;
    }
}