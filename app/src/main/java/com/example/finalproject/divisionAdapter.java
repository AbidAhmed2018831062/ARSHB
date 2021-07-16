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

public class divisionAdapter extends RecyclerView.Adapter<divisionAdapter.Division> {
private String[] list;
private int img[];
String name;
Context c;
public divisionAdapter(Context c,String[] list,int img[])
{
    this.list=list;
    this.c=c;
    this.img=img;
}
    @NonNull
    @Override
    public Division onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.divisionlayout, parent, false);
        return new Division(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Division holder, final int i) {
    holder.text.setText(list[i]);

    holder.syl.setImageResource(img[i]);
        holder.ca1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(c,DivisionWiseHotels.class);
                in.putExtra("name",list[i]);
                c.startActivity(in);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.length;
    }

    public class Division extends RecyclerView.ViewHolder {
TextView text;
ImageView syl;
CardView ca1;
        public Division(@NonNull View v) {
            super(v);
           text=(TextView)v.findViewById(R.id.text);
           syl=(ImageView)v.findViewById(R.id.syl);
           ca1=(CardView)v.findViewById(R.id.ca1);
        }
    }
}
