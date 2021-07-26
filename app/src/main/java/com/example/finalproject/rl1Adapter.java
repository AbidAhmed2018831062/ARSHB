package com.example.finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class rl1Adapter extends RecyclerView.Adapter<rl1Adapter.MyAdapter> {
    Context c;
    int rl1img[];
   String rl1des[];
   ArrayList<RatingClass>ars;
    Activity ac;
    public rl1Adapter(Context c,int rl1img[], ArrayList<RatingClass>ars,String rl1des[])
    {
        this.c=c;
        this.rl1img=rl1img;
        this.ars=ars;
        this.rl1des=rl1des;
        ac= (Activity) c;
    }

    @NonNull
    @Override
    public rl1Adapter.MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li=LayoutInflater.from(c);
        View view=li.inflate(R.layout.recyclerview1d,parent,false);
        return new MyAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull rl1Adapter.MyAdapter holder, int i) {
           holder.img.setImageResource(rl1img[i]);
           holder.t1.setText(ars.get(i).getName());
           holder.r1r.setRating(ars.get(i).getRating());
           final String na= (String) holder.t1.getText();
           holder.t2.setText(rl1des[i]);
           holder.ca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(c,HotelShowCase.class);
                in.putExtra("name",na);

                c.startActivity(in);
                ac.finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return ars == null ? 0 : ars.size();
    }
     class MyAdapter extends RecyclerView.ViewHolder{
        ImageView img;
        TextView t1,t2;
        CardView ca;
        RatingBar r1r;

         public MyAdapter(@NonNull View itemView) {
             super(itemView);
             img=itemView.findViewById(R.id.rlimg);
             t1=itemView.findViewById(R.id.rlh);
             t2=itemView.findViewById(R.id.rld);
             ca=itemView.findViewById(R.id.ca);
             r1r=itemView.findViewById(R.id.rlr);

         }
     }
}
