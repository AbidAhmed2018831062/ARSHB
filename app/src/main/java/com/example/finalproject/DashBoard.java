package com.example.finalproject;

import android.Manifest;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Pair;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DashBoard extends AppCompatActivity {
    int rl1img[]={R.drawable.rl1,R.drawable.rl2,R.drawable.rl3,R.drawable.rl4};
    int rl2img[]={R.drawable.star31,R.drawable.star41,R.drawable.star52};
    String rl1h[],rl1des[],rl2h[],rl2des[];
    List<String> ad=new ArrayList<>();
    int y=0;
     String phoneUser;
    DrawerLayout dr;
    NavigationView nav;
    RecyclerView rl1,rl2;
    ImageView menui,plusicon,map;
    RecyclerView division;
    divisionAdapter da;
    private FusedLocationProviderClient f;
     double lng2,lat2;
    boolean isP=false;
    ImageView sear;
    Dialog d1;
    int img[]={R.drawable.tree,R.drawable.dhaka,R.drawable.barishal,R.drawable.chittagong,R.drawable.khulna,R.drawable.rajshahi,R.drawable.rangpur,R.drawable.mymensingh};
     String str;
String[] list;
int H=0;
    FirebaseUser u;
    AutoCompleteTextView editText;
    ArrayAdapter<String> adapter;
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
        division=(RecyclerView)findViewById(R.id.division);
        rl2=(RecyclerView)findViewById(R.id.rl2);
        dr=(DrawerLayout) findViewById(R.id.drawer);
        menui=(ImageView) findViewById(R.id.menuicon);
        plusicon=(ImageView) findViewById(R.id.plusicon);
        sear=(ImageView) findViewById(R.id.sear);
        map=(ImageView) findViewById(R.id.map);
        nav=(NavigationView) findViewById(R.id.navigation);
        u= FirebaseAuth.getInstance().getCurrentUser();
        list=getResources().getStringArray(R.array.Division);
        division.setHasFixedSize(true);
        division.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        da=new divisionAdapter(this,list,img);
        division.setAdapter(da);
        editText = findViewById(R.id.actv);
        sear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isWiConnected(DashBoard.this)) {

                    showCustomDialog();

                }
                else {
                    startActivity(new Intent(getApplicationContext(), All_Hotels.class).putExtra("com",editText.getText().toString()));
                }
            }
        });


        editText.setThreshold(1);
        SessionManager sh=new SessionManager(this,SessionManager.USERSESSION);
       FirebaseDatabase.getInstance().getReference("Hotels").child("Hotel Names").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> hotelsName=new ArrayList<>();
           for(DataSnapshot ds:dataSnapshot.getChildren())
           {
               HotelShow hs=ds.getValue(HotelShow.class);
             hotelsName.add(hs.getName());

           }
                adapter = new ArrayAdapter<String>(DashBoard.this,
                        android.R.layout.simple_list_item_1, hotelsName);
                editText.setAdapter(adapter);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        checkPermission();

        //editText.setThreshold(1);
        if(isP==true) {
            FirebaseDatabase.getInstance().getReference("Hotels").child("Address").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        ad.add(ds.getValue().toString());
                        //    Toast.makeText(getApplicationContext(),ds.getValue().toString(),Toast.LENGTH_LONG).show();
                        y++;

                    }
                    StringBuilder strbul = new StringBuilder();
                    final String op[] = new String[y];
                    y = 0;
                    for (String str : ad) {
                        strbul.append(str);
                        //for adding comma between elements
                        strbul.append(",");
                        op[y] = str;
                        y++;

                    }
                    str = strbul.toString();

                    map.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!isWiConnected(DashBoard.this)) {

                                showCustomDialog();

                            }
                            else
                            startActivity(new Intent(getApplicationContext(), ActivityBeforeMap.class).putExtra("name", str).putExtra("op", op).putExtra("na","Dashboard"));
                        }
                    });


                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
        HashMap<String,String>  hm=sh.returnData();
        phoneUser = hm.get(SessionManager.PHONE);

        SessionManagerHotels sh1=new SessionManagerHotels(this,SessionManagerHotels.USERSESSION);
        HashMap<String,String>  hm1=sh1.returnData();
        final String phoneHotel = hm1.get(SessionManagerHotels.RATING);
        plusicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                             if(phoneUser!=null&&phoneHotel==null){
                                 if (!isWiConnected(DashBoard.this)) {

                                     showCustomDialog();

                                 }
                                 else {
                                     Toast.makeText(getApplicationContext(), phoneUser + "/" + phoneHotel, Toast.LENGTH_LONG).show();
                                     Intent in = new Intent(DashBoard.this, UserPrfofilew.class);
                                     Pair pair[] = new Pair[1];
                                     pair[0] = new Pair<View, String>(plusicon, "nextTra");
                                     ActivityOptions ac = ActivityOptions.makeSceneTransitionAnimation(DashBoard.this, pair);
                                     startActivity(in, ac.toBundle());
                                 }



                                } else if(phoneHotel!=null) {
                                    Intent in = new Intent(DashBoard.this, HotelRooms.class);
                                    Pair pair[] = new Pair[1];
                                    pair[0] = new Pair<View, String>(plusicon, "nextTra");
                                    ActivityOptions ac = ActivityOptions.makeSceneTransitionAnimation(DashBoard.this, pair);
                                    startActivity(in, ac.toBundle());

                                } else {
                                 if (!isWiConnected(DashBoard.this)) {

                                     showCustomDialog();

                                 }
                                 else {
                                     Intent in = new Intent(DashBoard.this, LogIn_Or_SignUp.class);
                                     Pair pair[] = new Pair[1];
                                     pair[0] = new Pair<View, String>(plusicon, "nextTra");
                                     ActivityOptions ac = ActivityOptions.makeSceneTransitionAnimation(DashBoard.this, pair);
                                     startActivity(in, ac.toBundle());
                                 }




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
              if(item.getItemId()==R.id.allHotels) {
                  if (!isWiConnected(DashBoard.this)) {

                      showCustomDialog();

                  }
                  else {
                      startActivity(new Intent(getApplicationContext(), All_Hotels.class).putExtra("com","no"));
                  }
              }

                  else if(item.getItemId()==R.id.Profile) {
                      if (!isWiConnected(DashBoard.this)) {

                  showCustomDialog();

              }
                      else if(phoneUser==null)
                      {
                          AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                                  DashBoard.this);

// Setting Dialog Title
                          alertDialog2.setTitle("Log In.");

// Setting Dialog Message
                          alertDialog2.setMessage("You are not logged in. Log In To View Profile.");
                          alertDialog2.setIcon(R.drawable.apptitle);
                          alertDialog2.setPositiveButton("YES",
                                  new DialogInterface.OnClickListener() {
                                      public void onClick(DialogInterface dialog, int which) {
                                          // Write your code here to execute after dialog
                                          startActivity(new Intent(DashBoard.this,LogIn_Or_SignUp.class));

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
                      }else {
                  startActivity(new Intent(getApplicationContext(), UserPrfofilew.class));
              }
              }
                  else if(item.getItemId()==R.id.nav_serach)
              {
                  if (!isWiConnected(DashBoard.this)) {

                      showCustomDialog();

                  }
                  else
                      startActivity(new Intent(getApplicationContext(), All_Hotels.class));


              }
                  else if(item.getItemId()==R.id.Fav) {
                  {
                      if (!isWiConnected(DashBoard.this)) {

                          showCustomDialog();

                      }
                      else if(phoneUser==null)
                      {
                          AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                                  DashBoard.this);

// Setting Dialog Title
                          alertDialog2.setTitle("Log In.");

// Setting Dialog Message
                          alertDialog2.setMessage("You are not logged in. Log In and see your favorite hotels.");
                          alertDialog2.setIcon(R.drawable.apptitle);
                          alertDialog2.setPositiveButton("YES",
                                  new DialogInterface.OnClickListener() {
                                      public void onClick(DialogInterface dialog, int which) {
                                          // Write your code here to execute after dialog
                                          startActivity(new Intent(DashBoard.this,LogIn_Or_SignUp.class));

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
                      }else
                          startActivity(new Intent(getApplicationContext(), Fav.class));
                  }
              }
                  else if(item.getItemId()==R.id.Saved)
              {
                  if (!isWiConnected(DashBoard.this)) {

                      showCustomDialog();

                  }
                  else if(phoneUser==null)
                  {
                      AlertDialog.Builder alertDialog2 = new AlertDialog.Builder(
                              DashBoard.this);

// Setting Dialog Title
                      alertDialog2.setTitle("Log In.");

// Setting Dialog Message
                      alertDialog2.setMessage("You are not logged in. Log In and book your saved hotels.");
                      alertDialog2.setIcon(R.drawable.apptitle);
                      alertDialog2.setPositiveButton("YES",
                              new DialogInterface.OnClickListener() {
                                  public void onClick(DialogInterface dialog, int which) {
                                      // Write your code here to execute after dialog
                                      startActivity(new Intent(DashBoard.this,LogIn_Or_SignUp.class));

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
                  else
                      startActivity(new Intent(getApplicationContext(),WatchLater.class));
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
    @Override
    public void onBackPressed() {
        if(editText.hasFocus())
        {
            editText.clearFocus();
        }
       else if(dr.isDrawerVisible(GravityCompat.START))
            dr.closeDrawer(GravityCompat.START);
        else {
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

    public void recyclerView1()
    {
        rl1.setHasFixedSize(true);
        rl1.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        FirebaseDatabase.getInstance().getReference("Hotels").child("Rating").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int opiu=0;
                ArrayList<RatingClass> ars=new ArrayList<>();
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    if(opiu==3)
                        break;
                    RatingClass rs=ds.getValue(RatingClass.class);
                    if(rs.getRating()>=4.0)
                    {
                    ars.add(rs);
                    opiu++;
                    }
                }
                rl1Adapter rl=new rl1Adapter(DashBoard.this,rl1img,ars,rl1des);
                rl1.setAdapter(rl);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
    public boolean isWiConnected(DashBoard l) {
        ConnectivityManager c = (ConnectivityManager) l.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wi = c.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mi = c.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if ((wi != null && wi.isConnected()) || (mi != null && mi.isConnected())) {
            return true;
        } else
            return false;

    }

    private void showCustomDialog() {
        String x="LogIn_Or_SignUp";
        d1 = new Dialog(DashBoard.this);
        d1.setContentView(R.layout.custom);
        d1.getWindow().setBackgroundDrawable(getDrawable(R.drawable.customdraw));
        d1.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        d1.setCancelable(false);
        d1.getWindow().getAttributes().windowAnimations = R.style.animate;
        d1.show();
        Button b1 = d1.findViewById(R.id.cancel);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashBoard.this, DashBoard.class));
                finish();
            }

        });
        Button b2 = d1.findViewById(R.id.connect);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
            }
        });
    }
    public void vintage(View view) {
    }

    public void cara(View view) {
    }

    public void aubearge(View view) {
    }

}