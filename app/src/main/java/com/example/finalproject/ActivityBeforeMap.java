package com.example.finalproject;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.maps.android.SphericalUtil;

import java.util.ArrayList;
import java.util.List;

public class ActivityBeforeMap extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {
    //   MapView map;
    boolean isP = false;
    GoogleMap m;
public static final int CHECK_SETTINGS=1001;
    private static final float DEFAULT_ZOOM = 15f;
    private EditText ed;
    private ImageView gps;
 String hname;
 LatLng cr;
    private FusedLocationProviderClient f;
double lng2,lat2;
    Double km;
     Task l;
 String[] op;
    List<Address> l1;
    private static final LatLngBounds ltb=new LatLngBounds(new LatLng(-40,-168),new LatLng(71,136));
    GoogleApiClient mg;
    Button bt;
    RelativeLayout s;
    LocationRequest locationRequest;
    ProgressDialog pr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_before_map);
        ed=(EditText) findViewById(R.id.ed);
        s=(RelativeLayout)findViewById(R.id.s);
        s.setVisibility(View.GONE);
        bt=(Button) findViewById(R.id.street);
        pr=new ProgressDialog(this);
      //  Toast.makeText(getApplicationContext(),"First Click On Search View and Then Enter.",Toast.LENGTH_LONG).show();
        gps=(ImageView)findViewById(R.id.gps);
        hname=getIntent().getStringExtra("name");
        op=getIntent().getStringArrayExtra("op");

        SupportMapFragment mp = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mp.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull GoogleMap googleMap) {
                m = googleMap;

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                m.setMyLocationEnabled(true);
                m.getUiSettings().setMyLocationButtonEnabled(false);
              //  check();
                gps.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                         startActivity(new Intent(getApplicationContext(),ActivityBeforeMap.class).putExtra("name", hname).putExtra("op", op));
                         finish();
                    }
                });
                check();

             //   ed.setText(hname);

            }
        });

        }

    private void geoLocate() {
          pr.dismiss();
        Geocoder g = new Geocoder(ActivityBeforeMap.this);
        List<Address> addresses = new ArrayList<>();
        try {
            addresses = g.getFromLocationName(hname, 100);

        } catch (Exception e) {
            e.printStackTrace();
        }
       Toast.makeText(getApplicationContext(), op.length + " ", Toast.LENGTH_LONG).show();

        if (addresses.size() > 0) {



            f = LocationServices.getFusedLocationProviderClient(ActivityBeforeMap.this);
            try {
                if (ActivityCompat.checkSelfPermission(ActivityBeforeMap.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ActivityBeforeMap.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }

                //   Toast.makeText(getApplicationContext(),f.getLastLocation()+" ",Toast.LENGTH_LONG).show();

                final List<Address> finalAddresses = addresses;
                f.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Toast.makeText(getApplicationContext(),"Zoom Out to see nearby places",Toast.LENGTH_LONG).show();
                        if (location != null) {
                            Toast.makeText(getApplicationContext(),"Zoom Out to see nearby places",Toast.LENGTH_LONG).show();
                            lat2 = location.getLatitude();
                            lng2 = location.getLongitude();
                            moveCamera(new LatLng(lat2,lng2), DEFAULT_ZOOM, "My Location");
                            int j = 0;
                            for (Address address : finalAddresses) {
                                 if(j>=op.length)
                                     break;
                                float[] dist=new float[10];
                                double lat1 = address.getLatitude();
                                double lng1 = address.getLongitude();
                                //  Location.distanceBetween(lat2,lng2,lat1,lng1,dist);

                                LatLng oa=new LatLng(lat1,lng1);
                                cr=new LatLng(lat2,lng2);
                                km = SphericalUtil.computeDistanceBetween(cr, oa);
                                Toast.makeText(getApplicationContext(),j+" ",Toast.LENGTH_LONG).show();
                                if(getIntent().getStringExtra("na").equals("Dashboard")) {
                                    if (Math.abs(km / 1000) <= 10) {

                                        moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, op[j]);

                                    }

                                }
                                else
                                    moveCamera(new LatLng(address.getLatitude(), address.getLongitude()), DEFAULT_ZOOM, op[0]);
                                j++;
                            }

                        } else
                            Toast.makeText(getApplicationContext(), location + "", Toast.LENGTH_LONG).show();

                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }


        }
    }



private void moveCamera(final LatLng ll, float sum,String p)
{
    m.moveCamera(CameraUpdateFactory.newLatLngZoom(ll,sum));
    MarkerOptions n=new MarkerOptions().position(ll).title(p);
    m.addMarker(n);
    Toast.makeText(getApplicationContext(),hname+" ",Toast.LENGTH_LONG).show();
    hideSoftKeyboard();
}



  private void hideSoftKeyboard()
  {
      this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
  }
public void check()
{
    locationRequest = LocationRequest.create();
    locationRequest.setInterval(1000);
    locationRequest.setFastestInterval(500);
    locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);


// ......

    SettingsClient client = LocationServices.getSettingsClient(this);
    Task<LocationSettingsResponse> task = client.checkLocationSettings(builder.build());
    task.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
        @Override
        public void onComplete(@NonNull Task<LocationSettingsResponse> task) {
            try {
                LocationSettingsResponse r=task.getResult(ApiException.class);
                geoLocate();
            } catch (ApiException e) {
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    ResolvableApiException resolvable = (ResolvableApiException) e;
                    resolvable.startResolutionForResult(ActivityBeforeMap.this,
                            CHECK_SETTINGS);
                } catch (IntentSender.SendIntentException sendEx) {
                    // Ignore the error.
                }
            }
        }});
}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    if(requestCode==CHECK_SETTINGS) {
        switch (resultCode) {
            case Activity.RESULT_OK:
                startActivity(new Intent(getApplicationContext(),ActivityBeforeMap.class).putExtra("name", hname).putExtra("op", op));
                finish();
                break;
            case Activity.RESULT_CANCELED:
                Toast.makeText(getApplicationContext(),"Location needs to be turned on",Toast.LENGTH_LONG).show();
                break;
        }
    }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}