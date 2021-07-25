package com.example.finalproject;

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
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Fav extends AppCompatActivity {
    private RecyclerView rl;
    List<String> li = new ArrayList<>();
    List<String> image = new ArrayList<>();
    Rl3Adapter rl3;
    SearchView s;
    ImageView backB;
    TextView Danger;
    Users it;
    int count;
    List<Users> listI = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_fav);
        rl = (RecyclerView) findViewById(R.id.rl3);
        backB = (ImageView) findViewById(R.id.backButton);
        s = (SearchView) findViewById(R.id.serachView);
        Danger = (TextView) findViewById(R.id.Danger);
        //s.setIconified(false);
        //s.setIconifiedByDefault(false)
        EditText txtSearch = ((EditText) s.findViewById(androidx.appcompat.R.id.search_src_text));
        txtSearch.setHint(getResources().getString(R.string.search_hint));
        txtSearch.setHintTextColor(Color.LTGRAY);
        txtSearch.setTextColor(Color.BLACK);
        rl.setLayoutManager(new LinearLayoutManager(this));
        rl3 = new Rl3Adapter(this, listI);
        rl.setAdapter(rl3);
        SessionManager sh = new SessionManager(Fav.this, SessionManager.USERSESSION);

        HashMap<String, String> hm = sh.returnData();
        final String phone = hm.get(SessionManager.PHONE);

        DatabaseReference db = FirebaseDatabase.getInstance().getReference("Users").child(phone).child("fav");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                li.clear();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                  SaveAndFav sf=ds.getValue(SaveAndFav.class);
                 final String name=sf.getName();
                    li.add(name);
                    count++;
                    Toast.makeText(getApplicationContext(), li.get(0), Toast.LENGTH_LONG).show();
                    DatabaseReference db1 = FirebaseDatabase.getInstance().getReference("Hotels").child(name);
                    db1.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot1) {
                            String url = dataSnapshot1.child("url").getValue().toString();
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
                if (li.size() != 0) {
                    Danger.setText(" ");
                } else
                    Danger.setText("You have not added any hotels in your favorite section. Add few hotels and then come back to watch them");
            }

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
       Fav.super.onBackPressed();
    }
}