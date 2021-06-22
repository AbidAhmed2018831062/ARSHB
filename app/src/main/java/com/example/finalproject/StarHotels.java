package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class StarHotels extends AppCompatActivity {
    private RecyclerView rl;
    List<String> li = new ArrayList<>();
    List<String> image = new ArrayList<>();
    Rl3Adapter rl3;
    SearchView s;
    ImageView backB;
    TextView Danger,ht;
    Users it;
    String star;
    int count;
    List<Users> listI = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_star_hotels);
        rl = (RecyclerView) findViewById(R.id.rl3);
        backB = (ImageView) findViewById(R.id.backButton);
        s = (SearchView) findViewById(R.id.serachView);
        Danger = (TextView) findViewById(R.id.Danger);
        ht = (TextView) findViewById(R.id.ht);
        star = getIntent().getStringExtra("Star");
        ht.setText("All"+" "+star+" Hotels");
        Toast.makeText(getApplicationContext(),star,Toast.LENGTH_LONG).show();
        //s.setIconified(false);
        //s.setIconifiedByDefault(false)
        EditText txtSearch = ((EditText) s.findViewById(androidx.appcompat.R.id.search_src_text));
        txtSearch.setHint(getResources().getString(R.string.search_hint));
        txtSearch.setHintTextColor(Color.LTGRAY);
        txtSearch.setTextColor(Color.BLACK);
        rl.setLayoutManager(new LinearLayoutManager(this));
        rl3 = new Rl3Adapter(this, listI);
        rl.setAdapter(rl3);
        SessionManager sh = new SessionManager(StarHotels.this, SessionManagerHotels.USERSESSION);

        HashMap<String, String> hm = sh.returnData();
        final String name = hm.get(SessionManager.FULLNAME);

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Hotels").child("Star").child(star);
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                li.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    final String name=ds.getValue().toString();
                    li.add(name);
                    count++;
                    //Toast.makeText(getApplicationContext(),li.get(0),Toast.LENGTH_LONG).show();
                    DatabaseReference db1=FirebaseDatabase.getInstance().getReference("Hotels").child(name);
                    db1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                            String url=dataSnapshot1.child("url").getValue().toString();
                            image.add(url);
                            it = new Users(url, name, "Expand All");
                            listI.add(it);

                            rl3.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                if(li.size()!=0)
                {
                    Danger.setText(" ");
                }
                else {
                    Danger.setText("There is no" + star + " hotels in our app!!!");
                }            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        s.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
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
        });
        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


    }

    @Override
    public void onBackPressed() {
        StarHotels.super.onBackPressed();

    }
}