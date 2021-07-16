package com.example.finalproject;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HotelShowCase extends AppCompatActivity {
    ImageView back, save;
    String name;
    TextView ad,HNAME;
    Button amenities;
    TextView lo;
    int E=0;
    float k=0;
    TextView des;
    String count;
    TextView na;
    RecyclerView rl4HR1;
    MeowBottomNavigation bm;
    List<Rooms> list;
    ImageView fav;
    rl4HR rl;
    List<String> am = new ArrayList<>();
    TextView house;
    TextView review,profileName;
    ImageView profile_Image,map;
    Button allreviews;
    RatingBar rating;
    List<CommentShow>list1=new ArrayList<>();
    TextView count1;
    int co = 1, cow = 1;
    boolean isP=false;

    int rate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_hotel_show_case);
        back = (ImageView) findViewById(R.id.back);
        map = (ImageView) findViewById(R.id.map);
        ad = (TextView) findViewById(R.id.address);
        lo = (TextView) findViewById(R.id.location);
        na = (TextView) findViewById(R.id.name);
        count1 = (TextView) findViewById(R.id.count);
        HNAME = (TextView) findViewById(R.id.name);
        rating = (RatingBar) findViewById(R.id.rating);
        rate=rating.getNumStars();

        name = getIntent().getStringExtra("name");
        review = (TextView) findViewById(R.id.review);
        profile_Image = (ImageView) findViewById(R.id.profile_image);
        allreviews = (Button) findViewById(R.id.allreview);
        allreviews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getApplicationContext(),Reviews.class);
                in.putExtra("name",name);

                startActivity(in);
                finish();
            }
        });
        DatabaseReference dgh=  FirebaseDatabase.getInstance().getReference("Hotels").child(name).child("Rating");
        dgh.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot e: dataSnapshot.getChildren())
                {
                    E++;
                    k+=Integer.parseInt(e.getValue(String.class));
                    Toast.makeText(getApplicationContext(),e.getValue(String.class),Toast.LENGTH_LONG).show();
                }
                float r= (float) (k/E);
                //Toast.makeText(getApplicationContext(),r+" "+k+" "+E,Toast.LENGTH_LONG).show();
                rating.setRating( r);
                Toast.makeText(getApplicationContext()," "+rating.getNumStars(),Toast.LENGTH_LONG).show();
                count1.setText(E+" reviews");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        profileName = (TextView) findViewById(R.id.profileName);
        des = (TextView) findViewById(R.id.des);

        bm = (MeowBottomNavigation) findViewById(R.id.bottomnav);
        house = (TextView) findViewById(R.id.house);
        rl4HR1 = (RecyclerView) findViewById(R.id.rl4HR);
        list = new ArrayList<>();
        SessionManager sh = new SessionManager(HotelShowCase.this, SessionManager.USERSESSION);

        HashMap<String, String> hm = sh.returnData();
        final String phone = hm.get(SessionManager.PHONE);
        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Hotels").child(name).child("Review");


        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
             //   list.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    CommentShow roo= ds.getValue(CommentShow.class);
                    Picasso.with(HotelShowCase.this).load(roo.getUrl()).fit().centerCrop().into(profile_Image);
                    profileName.setText(roo.getName());
                    review.setText(roo.getComment());
                    break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });


        fav = (ImageView) findViewById(R.id.fav);
        save = (ImageView) findViewById(R.id.save);
        fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (co % 2 == 1) {

                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("Users").child(phone);
                    HashMap hm1 = new HashMap();
                    hm1.put("fav" + co, name);
                    db.child("fav").updateChildren(hm1);
                    Toast.makeText(getApplicationContext(), "Hotel Added To Your Favorite List", Toast.LENGTH_LONG).show();
                    co++;
                    fav.setImageResource(R.drawable.fav);
                } else {
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("Users").child(phone).child("fav").child(name);
                    db.removeValue();
                    Toast.makeText(getApplicationContext(), "Hotel Removed From Your Favorite List", Toast.LENGTH_LONG).show();
                    fav.setImageResource(R.drawable.download);
                }
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cow % 2 == 1) {

                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("Users").child(phone);
                    HashMap hm1 = new HashMap();
                    hm1.put("save" + cow, name);
                    db.child("save").updateChildren(hm1);
                    Toast.makeText(getApplicationContext(), "Hotel Added To Your Save List", Toast.LENGTH_LONG).show();
                    cow++;
                    save.setImageResource(R.drawable.save);
                    //  fav.setBackgroundColor(getResources().getColor(R.color.DarkBlue));
                } else {
                    DatabaseReference db = FirebaseDatabase.getInstance().getReference("Users").child(phone).child("fav").child(name);
                    db.removeValue();
                    Toast.makeText(getApplicationContext(), "Hotel Removed From Your Save List", Toast.LENGTH_LONG).show();
                    save.setImageResource(R.drawable.do1);
                }
            }
        });

        rl = new rl4HR(HotelShowCase.this, list, name,"ShowCase");
        rl4HR1.setHasFixedSize(true);
        LinearLayoutManager li = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        rl4HR1.setLayoutManager(li);
        rl4HR1.setAdapter(rl);
        house.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final BottomSheetDialog bsd = new BottomSheetDialog(HotelShowCase.this, R.style.BottomSheetDialogTheme);
                View bs = LayoutInflater.from(getApplicationContext()).inflate(R.layout.houserules, (LinearLayout) findViewById(R.id.houserules));
                bsd.setContentView(bs);
                bsd.show();
            }
        });

        amenities = (Button) findViewById(R.id.amenities);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        bm.add(new MeowBottomNavigation.Model(1, R.drawable.search));
        bm.add(new MeowBottomNavigation.Model(2, R.drawable.fav));
        bm.add(new MeowBottomNavigation.Model(3, R.drawable.save));
        bm.add(new MeowBottomNavigation.Model(4, R.drawable.profile));
        bm.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                if (item.getId() == 1) {
                    startActivity(new Intent(HotelShowCase.this, All_Hotels.class));
                    finish();
                } else if (item.getId() == 2) {
                    startActivity(new Intent(HotelShowCase.this, Fav.class));
                    finish();
                } else if (item.getId() == 4) {
                    startActivity(new Intent(HotelShowCase.this, UserPrfofilew.class));
                    finish();
                }
            }
        });


        na.setText(name);
        amenities.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), ShowAmenties.class);
                in.putExtra("name", name);
                startActivity(in);
            }
        });
        DatabaseReference db34 = FirebaseDatabase.getInstance().getReference("Hotels").child(name).child("rooms");


        db34.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    Rooms roo = ds.getValue(Rooms.class);

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
                String email = d.child(name).child("email").getValue(String.class);
                String star = d.child(name).child("star").getValue(String.class);
                String des1 = d.child(name).child("des").getValue(String.class);
                String fullname = d.child(name).child("name").getValue(String.class);
                String phone2 = d.child(name).child("phone").getValue(String.class);
                String username = d.child(name).child("username").getValue(String.class);
                String ac = d.child(name).child("ac").getValue(String.class);
                String address = d.child(name).child("address").getValue(String.class);
                String url = d.child(name).child("url").getValue(String.class);
                String tv = d.child(name).child("tv").getValue(String.class);
                String fi = d.child(name).child("fi").getValue(String.class);
                String fia = d.child(name).child("fia").getValue(String.class);
                String wifi = d.child(name).child("wifi").getValue(String.class);
                String sp = d.child(name).child("sp").getValue(String.class);
                String plc = d.child(name).child("plc").getValue(String.class);
                String rs = d.child(name).child("rs").getValue(String.class);
                String rc = d.child(name).child("rc").getValue(String.class);
                String hd = d.child(name).child("hd").getValue(String.class);
                String sc = d.child(name).child("sc").getValue(String.class);
                ad.setText(address);
                des.setText(des1);
                lo.setText(address);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkPermission();
                if(isP==true)
                startActivity(new Intent(getApplicationContext(),ActivityBeforeMap.class).putExtra("name",ad.getText().toString()+", "+na.getText().toString()));
                else
                    Toast.makeText(getApplicationContext(),"Permission was not given",Toast.LENGTH_LONG).show();
            }
        });

    }
    private void checkPermission() {
        Dexter.withContext(this).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                Toast.makeText(getApplicationContext(), "Granted", Toast.LENGTH_LONG).show();
                isP = true;
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
                Intent in = new Intent();
                in.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), " ");
                in.setData(uri);
                startActivity(in);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                permissionToken.continuePermissionRequest();
            }
        }).check();
    }
    @Override
    public void onBackPressed() {
        HotelShowCase.super.onBackPressed();
    }
}