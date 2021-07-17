package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static com.example.finalproject.rl4HR.dele;

public class HotelRooms extends AppCompatActivity {
TextView h1,h2;
String name;
ImageView dpp;
RecyclerView rl4HR1;
LinearLayout layoutList;
    DrawerLayout dr;
    NavigationView nav;
    ImageView menui;
 List<Rooms> list;
 String token;
 rl4HR rl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_hotel_rooms);
        h1=(TextView) findViewById(R.id.h1);
       // layoutList=(LinearLayout) findViewById(R.id.linear);
        h2=(TextView) findViewById(R.id.h2);
        dpp=(ImageView) findViewById(R.id.dpp);
        dr=(DrawerLayout) findViewById(R.id.drawer);
        menui=(ImageView) findViewById(R.id.menuicon);

        SessionManagerHotels sh= new SessionManagerHotels(HotelRooms.this,SessionManagerHotels.USERSESSION);

        HashMap<String,String> hm=sh.returnData();
        name=hm.get(SessionManagerHotels.FULLNAME);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()) {
                             token = Objects.requireNonNull(task.getResult()).getToken();
                             HashMap t=new HashMap();
                             t.put("token",token);
                            FirebaseDatabase.getInstance().getReference("Hotels").child(name).updateChildren(t);

                        }

                    }
                });
        String fullname1=hm.get(SessionManagerHotels.DES);
        String fullname2=hm.get(SessionManagerHotels.EMAIL);
        String fullname3=hm.get(SessionManagerHotels.PHONE);
        String fullname4=hm.get(SessionManagerHotels.USERNAME);
        String fullname5=hm.get(SessionManagerHotels.RATING);
        String fullname6=hm.get(SessionManagerHotels.PASS);
        delete();
        delete1();
        nav=(NavigationView) findViewById(R.id.navigation);
    rl4HR1=(RecyclerView)findViewById(R.id.rl4HR);
    list=new ArrayList<>();

     rl=new rl4HR(HotelRooms.this, list,name,"Rooms");
          rl4HR1.setHasFixedSize(true);
        LinearLayoutManager li=new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
        rl4HR1.setLayoutManager(li);rl4HR1.setAdapter(rl);


        navigationDrawer();
        DatabaseReference db= FirebaseDatabase.getInstance().getReference("Hotels").child(name).child("rooms");


        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
list.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    Rooms roo=ds.getValue(Rooms.class);

                    /*View roomView=getLayoutInflater().inflate(R.layout.roomsshowcase, null,false);
                    TextView se=(TextView) roomView.findViewById(R.id.service);
                    TextView pr=(TextView)roomView.findViewById(R.id.price);
                 se.setText(roo.getServices());
                 pr.setText("$"+ roo.getPrice());
                 roomView.setPadding(0,10,0,10);
                 layoutList.setPadding(0,10,0,10);
layoutList.addView(roomView);*/
                    list.add(roo);
                }
                rl.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });



        Query c = FirebaseDatabase.getInstance().getReference("Hotels").orderByChild("name").equalTo(name);
        c.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot d) {
                if (d.exists()) {
                    h1.setText(name);
                    h2.setText(name);
                    String url= d.child(name).child("url").getValue(String.class);

                    Picasso.with(HotelRooms.this).load(url).placeholder(R.drawable.star31).fit().centerCrop().into(dpp);
                } else
                    Toast.makeText(getApplicationContext(), name, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
     /*   rl4HR rl4=new rl4HR(this,name);
        rl4HR1.setHasFixedSize(true);
        rl4HR1.setAdapter(rl4);*/



    }

    private void delete1() {
        Toast.makeText(getApplicationContext(),"KI"+ApprovalAdapter.d, Toast.LENGTH_LONG).show();
        for(int i=0;i<ApprovalAdapter.d;i++)
        {

            if(ApprovalAdapter.dele[i]!=null)
            {
                Toast.makeText(getApplicationContext(),ApprovalAdapter.dele[i], Toast.LENGTH_LONG).show();
                FirebaseDatabase.getInstance().getReference("Hotels").child(name).child("needApp").child(ApprovalAdapter.dele[i]).removeValue();
                ApprovalAdapter.dele[i]=null;
            }
            else
                Toast.makeText(getApplicationContext(),dele[i], Toast.LENGTH_LONG).show();
        }
    }

    public void navigationDrawer()
    {
        nav.bringToFront();
        nav.setCheckedItem(R.id.home);
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId()==R.id.hotelinfo)
                    startActivity(new Intent(getApplicationContext(),HotelInfo.class));
                else if(item.getItemId()==R.id.update)
                    startActivity(new Intent(getApplicationContext(),UpdateAccount.class));
                else if(item.getItemId()==R.id.updaterooms) {
                    startActivity(new Intent(getApplicationContext(), UpdateRooms.class));
                    finish();
                }
                    else if(item.getItemId()==R.id.add){
                        startActivity(new Intent(getApplicationContext(), roomCreation.class).putExtra("Name",name));
                        finish();

                }
                    else if(item.getItemId()==R.id.approval)
                {
                    startActivity(new Intent(getApplicationContext(), Approval.class));
                    finish();
                }
                else if(item.getItemId()==R.id.add){
                    Intent in=new Intent(getApplicationContext(),OrderGiven.class);
                    in.putExtra("phone",name);
                    startActivity(in);
                    finish();
                }

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
    public void delete()
    {
        Toast.makeText(getApplicationContext(),"KI"+rl4HR.d, Toast.LENGTH_LONG).show();
        for(int i=0;i<rl4HR.d;i++)
        {

            if(dele[i]!=null)
            {
                Toast.makeText(getApplicationContext(),dele[i], Toast.LENGTH_LONG).show();
                FirebaseDatabase.getInstance().getReference("Hotels").child(name).child("rooms").child(dele[i]).removeValue();
                dele[i]=null;
            }
            else
                Toast.makeText(getApplicationContext(),dele[i], Toast.LENGTH_LONG).show();
        }
    }
}