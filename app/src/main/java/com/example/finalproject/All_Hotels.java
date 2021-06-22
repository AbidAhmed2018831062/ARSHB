package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.widget.LinearLayout.VERTICAL;

public class All_Hotels extends AppCompatActivity {
    private RecyclerView rl;
    List<String> li = new ArrayList<>();
    List<String> image = new ArrayList<>();
    Rl3Adapter rl3;
    SearchView s;
    ImageView backB;
    Users it;
    int count;
    List<Users> listI = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_all__hotels);
        rl = (RecyclerView) findViewById(R.id.rl3);
        backB = (ImageView) findViewById(R.id.backButton);
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
        DatabaseReference db= FirebaseDatabase.getInstance().getReference("Hotels").child("Hotel Names");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                li.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren())
                {
                    final String name=ds.getValue().toString();
                    li.add(name);
                    count++;

                    DatabaseReference db1=FirebaseDatabase.getInstance().getReference("Hotels").child(name);
                    db1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                            String url=dataSnapshot1.child("url").getValue().toString();
                            image.add(url);
                           Users it1 = new Users(url, name, "Expand All");
                            listI.add(it1);
                            rl3.notifyDataSetChanged();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
        All_Hotels.super.onBackPressed();
    }
}