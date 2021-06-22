package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.recyclerview.widget.RecyclerView;

import static android.graphics.Color.CYAN;

public class rl2Adapte extends RecyclerView.Adapter<rl2Adapte.MyAdapter> {
    Context c;
    int rl2img[];
    String rl2h[],rl2des[];
    public rl2Adapte(Context c,int rl2img[],String rl2h[],String rl2des[])
    {
        this.c=c;
        this.rl2img=rl2img;
        this.rl2h=rl2h;
        this.rl2des=rl2des;
    }
    @NonNull
    @Override
    public rl2Adapte.MyAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li=LayoutInflater.from(c);
        View view=li.inflate(R.layout.rl2d,parent,false);
        return new MyAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull rl2Adapte.MyAdapter holder, int i) {
        holder.img.setImageResource(rl2img[i]);
        holder.t1.setText(rl2h[i]);
        final String na= holder.t1.getText().toString();

        holder.t2.setText(rl2des[i]);
        if(i==2)
        {
            holder.rl2c.setCardBackgroundColor(Color.parseColor("#F5D0C5"));
        }
        else if(i==1)
        {
            holder.rl2c.setCardBackgroundColor(Color.parseColor("#87cefa"));
        }
        else if(i==0)
        {
            holder.rl2c.setCardBackgroundColor(Color.YELLOW);
        }
        holder.rl2c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(c,StarHotels.class);
                in.putExtra("Star",na);
                c.startActivity(in);
            }
        });


    }

    @Override
    public int getItemCount() {
        return rl2img.length;
    }
    class MyAdapter extends RecyclerView.ViewHolder{
           ImageView img;
           TextView t1,t2;
           CardView rl2c;
        public MyAdapter(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.rl2img);
            rl2c=itemView.findViewById(R.id.rl2c);
            t1=itemView.findViewById(R.id.rl2h);
            t2=itemView.findViewById(R.id.rl2des);
        }
    }
}
