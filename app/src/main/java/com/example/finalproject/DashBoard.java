package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class DashBoard extends AppCompatActivity {
    int rl1img[]={R.drawable.rl1,R.drawable.rl2,R.drawable.rl3,R.drawable.rl4};
    int rl2img[]={R.drawable.star31,R.drawable.star41,R.drawable.star52};
    String rl1h[],rl1des[],rl2h[],rl2des[];
    DrawerLayout dr;
    NavigationView nav;
    RecyclerView rl1,rl2;
    ImageView menui,plusicon;
    FirebaseUser u;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
       // getSupportActionBar().hide();
        setContentView(R.layout.activity_dash_board);
        rl1h=getResources().getStringArray(R.array.HotelNames);
        rl2h=getResources().getStringArray(R.array.Star);
        rl1des=getResources().getStringArray(R.array.HotelDes);
        rl2des=getResources().getStringArray(R.array.Service);
        rl1=(RecyclerView)findViewById(R.id.rl1);
        rl2=(RecyclerView)findViewById(R.id.rl2);
        dr=(DrawerLayout) findViewById(R.id.drawer);
        menui=(ImageView) findViewById(R.id.menuicon);
        plusicon=(ImageView) findViewById(R.id.plusicon);
        nav=(NavigationView) findViewById(R.id.navigation);
        u= FirebaseAuth.getInstance().getCurrentUser();
        plusicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (u != null) {
                    String phone=u.getPhoneNumber();
                    SessionManager sh = new SessionManager(DashBoard.this, SessionManager.USERSESSION);

                    HashMap<String, String> hm = sh.returnData();

                    final String p = hm.get(SessionManager.PHONE);
                    final String p1 = hm.get(SessionManager.FULLNAME);
                    boolean f = true;
                    Query c = FirebaseDatabase.getInstance().getReference("Users").orderByChild("phone").equalTo(p);
                    c.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot d) {
                            if (d.exists()) {
                               // u.getIdToken(true);
                                    Intent in = new Intent(DashBoard.this, UserPrfofilew.class);
                                    Pair pair[] = new Pair[1];
                                    pair[0] = new Pair<View, String>(plusicon, "nextTra");
                                    ActivityOptions ac = ActivityOptions.makeSceneTransitionAnimation(DashBoard.this, pair);
                                    startActivity(in, ac.toBundle());
                                    finish();


                                } else {
                                    Intent in = new Intent(DashBoard.this, HotelRooms.class);
                                    Pair pair[] = new Pair[1];
                                    pair[0] = new Pair<View, String>(plusicon, "nextTra");
                                    ActivityOptions ac = ActivityOptions.makeSceneTransitionAnimation(DashBoard.this, pair);
                                    startActivity(in, ac.toBundle());
                                    finish();
                                }
                            }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                } else {
                    Intent in = new Intent(DashBoard.this, CutomerOrOwner.class);
                    Pair pair[] = new Pair[1];
                    pair[0] = new Pair<View, String>(plusicon, "nextTra");
                    ActivityOptions ac = ActivityOptions.makeSceneTransitionAnimation(DashBoard.this, pair);
                    startActivity(in, ac.toBundle());
                    finish();
                }
            }});
        recyclerView1();
        recyclerView2();
        navigationDrawer();
    }

    private void recyclerView2() {
        rl2.setHasFixedSize(true);
        rl2.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        rl2Adapte rl2A=new rl2Adapte(this,rl2img,rl2h,rl2des);
        rl2.setAdapter(rl2A);
    }
    public void navigationDrawer()
    {
        nav.bringToFront();
        nav.setCheckedItem(R.id.home);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
              if(item.getItemId()==R.id.allHotels)
                  startActivity(new Intent(getApplicationContext(),All_Hotels.class));
              else if(item.getItemId()==R.id.nav_login)
                  startActivity(new Intent(getApplicationContext(),LogIn_Or_SignUp.class));

                return true;
            }
        });
        menui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(dr.isDrawerVisible(GravityCompat.START))
                {
                    dr.closeDrawer(GravityCompat.START);
                }
                else
                    dr.openDrawer(GravityCompat.START);
            }
        });
        animateNavDrawer();
    }
    public void animateNavDrawer(){
      dr.setScrimColor(getResources().getColor(R.color.makeUplight));
    }
    @Override
    public void onBackPressed() {
        if(dr.isDrawerVisible(GravityCompat.START))
            dr.closeDrawer(GravityCompat.START);
        else
        super.onBackPressed();
    }

    public void recyclerView1()
    {
        rl1.setHasFixedSize(true);
        rl1.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        rl1Adapter rl=new rl1Adapter(this,rl1img,rl1h,rl1des);
        rl1.setAdapter(rl);

    }



    public void vintage(View view) {
    }

    public void cara(View view) {
    }

    public void aubearge(View view) {
    }
}