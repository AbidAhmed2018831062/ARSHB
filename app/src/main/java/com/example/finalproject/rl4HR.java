package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class rl4HR extends RecyclerView.Adapter<rl4HR.RoomsAd> {

 List<Rooms> list;
    Context c;
    String name1,price,name,n;

    public rl4HR( Context c, List<Rooms> list, String n) {
this.c=c;
        this.list = list;
        this.n=n;
    }

    @NonNull
    @Override
    public rl4HR.RoomsAd onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.roomsshowcase, parent, false);


        return new RoomsAd(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RoomsAd holder, final int position) {

           holder.price.setText("$"+list.get(position).getPrice());
           holder.service.setText(list.get(position).getServices());
           holder.rn.setText(list.get(position).getRoomname());
            name1=list.get(position).getRoomname();
            price=list.get(position).getPrice();
        holder.ca1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(c,OrderReady.class);
                in.putExtra("Name",list.get(position).getRoomname());
                in.putExtra("Price",list.get(position).getPrice());
               in.putExtra("Rn",n);
                c.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class RoomsAd extends RecyclerView.ViewHolder {

        TextView price, service,rn;
        CardView ca1;
        public RoomsAd(@NonNull View itemView) {
            super(itemView);
            price=(TextView)itemView.findViewById(R.id.price);
           service=(TextView)itemView.findViewById(R.id.service);
           rn=(TextView)itemView.findViewById(R.id.rn);
           ca1=(CardView)itemView.findViewById(R.id.ca1);

        }
    }
}
