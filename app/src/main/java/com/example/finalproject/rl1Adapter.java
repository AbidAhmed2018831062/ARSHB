package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

public class rl1Adapter extends RecyclerView.Adapter<rl1Adapter.MyAdapter> {
    Context c;
    int rl1img[];
    String rl1h[],rl1des[];
    public rl1Adapter(Context c,int rl1img[],String rl1h[],String rl1des[])
    {
        this.c=c;
        this.rl1img=rl1img;
        this.rl1h=rl1h;
        this.rl1des=rl1des;
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
           holder.t1.setText(rl1h[i]);
           final String na= (String) holder.t1.getText();
           holder.t2.setText(rl1des[i]);
           holder.ca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(c,HotelShowCase.class);
                in.putExtra("name",na);

                c.startActivity(in);
            }
        });

    }

    @Override
    public int getItemCount() {
        return rl1img.length;
    }
     class MyAdapter extends RecyclerView.ViewHolder{
        ImageView img;
        TextView t1,t2;
        CardView ca;

         public MyAdapter(@NonNull View itemView) {
             super(itemView);
             img=itemView.findViewById(R.id.rlimg);
             t1=itemView.findViewById(R.id.rlh);
             t2=itemView.findViewById(R.id.rld);
             ca=itemView.findViewById(R.id.ca);

         }
     }
}
