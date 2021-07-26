package com.example.finalproject;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DivisionWiseHotels extends AppCompatActivity {
    private RecyclerView rl;
    List<String> li = new ArrayList<>();
    List<String> image = new ArrayList<>();
    Rl3Adapter rl3;
    SearchView s;
    ImageView backB;
    TextView nameOfDiv;
    Users it;
    int count;
    List<Users> listI = new ArrayList<>();
    ProgressDialog pr;
    String name;
    TextView empty;
    String HNAME,HURL;
    int c34=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();
        pr=new ProgressDialog(this);
        pr.show();
        pr.setContentView(R.layout.progress);
        pr.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        setContentView(R.layout.activity_all__hotels);
        rl = (RecyclerView) findViewById(R.id.rl3);
        backB = (ImageView) findViewById(R.id.backButton);
        nameOfDiv = (TextView) findViewById(R.id.nameOfDiv);
        empty = (TextView) findViewById(R.id.empty);
        name=getIntent().getStringExtra("name");
        nameOfDiv.setText(name);
        s = (SearchView) findViewById(R.id.serachView);
        //s.setIconified(false);
        //s.setIconifiedByDefault(false)
        EditText txtSearch = ((EditText)s.findViewById(androidx.appcompat.R.id.search_src_text));
        txtSearch.setHint(getResources().getString(R.string.search_hint));
        txtSearch.setHintTextColor(Color.LTGRAY);
        txtSearch.setTextColor(Color.BLACK);
        rl.setLayoutManager(new LinearLayoutManager(this));
        rl3 = new Rl3Adapter(this, listI);
        rl.setAdapter(rl3);
      /*  s.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                rl3.getFilter().filter(s.toString());
                Toast.makeText(getApplicationContext(),s+"   DIl se sun priya",Toast.LENGTH_LONG).show();
                return false;
            }
        });*/
        DatabaseReference db= FirebaseDatabase.getInstance().getReference("Hotels").child("Address");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                li.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                  String na=ds.getValue().toString();
                  String n="";
                  for(int i=na.length()-1;i>=0;i--)
                  {
                      if(Character.isWhitespace(na.charAt(i))||na.charAt(i)==',')
                      {
                                break;
                      }
                      n+=na.charAt(i);
                  }
                  String comp1="";
                  for(int i=0;i<name.length();i++)
                  {
                      if(name.charAt(i)=='-')
                      {
                          break;
                      }
                      comp1+=name.charAt(i);
                  }

                    StringBuilder sb=new StringBuilder(n);
                    sb.reverse();
                    n=sb.toString();
                    String h=n.toLowerCase();
                    String com=comp1.toLowerCase();
               //     Toast.makeText(getApplicationContext(),h+"/"+com, Toast.LENGTH_LONG).show();

                    if(com.equals(h))
                    {
                        c34=1;
                        Toast.makeText(getApplicationContext(),c34+"", Toast.LENGTH_LONG).show();
                        String hname="";
                        for(int i=0;i<na.length();i++)
                        {
                            if(na.charAt(i)==',')
                            {
                                break;
                            }
                            hname+=na.charAt(i);
                        }
String hname1="";
                  for(int i=hname.length()-2;i>=0;i--)
                  {
                     hname1+=hname.charAt(i);
                  }
                        StringBuilder sb1=new StringBuilder(hname1);
                        sb1.reverse();
                        hname=sb1.toString();

                      Query db1= FirebaseDatabase.getInstance().getReference("Hotels").orderByChild("name").equalTo(hname);
                        final String finalHname = hname;
                        final String finalHname1 = hname;
                        db1.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists()) {
                             //       Toast.makeText(getApplicationContext(), finalHname, Toast.LENGTH_LONG).show();
                              HNAME=dataSnapshot.child(finalHname1).child("name").getValue().toString();
                             HURL=dataSnapshot.child(finalHname1).child("url").getValue().toString();
                                Users it1 = new Users(HURL, HNAME, "Expand All");
                             //   Toast.makeText(getApplicationContext(),HNAME, Toast.LENGTH_LONG).show();
                                listI.add(it1);
                                rl3.notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }

                    pr.dismiss();


                }

              //  rl3.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

     /*   if(c34==0)
        { Toast.makeText(getApplicationContext(),c34+"", Toast.LENGTH_LONG).show();

            empty.setVisibility(View.VISIBLE);
            empty.setText("There is no hotel registered in this division. Sorry!! Try again later.");
        }*/

        search();
        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



    }

    private void search() {
        s.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //  rl3.getFilter().filter(s.toString());
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                rl3.getFilter().filter(s.toString());
                // Toast.makeText(getApplicationContext(),s+"   DIl se sun priya",Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        DivisionWiseHotels.super.onBackPressed();
    }
}