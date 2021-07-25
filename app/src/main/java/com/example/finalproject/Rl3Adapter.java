package com.example.finalproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Rl3Adapter extends RecyclerView.Adapter<Rl3Adapter.MyViewHolder> implements Filterable {
    Context c;
    List<Users> li;
    List<Users> listI;
    Activity ac;
    public Rl3Adapter(Context c, List<Users> h)
    {
        this.c=c;

        this.li=h;
        this.listI=(h);
        ac= (Activity) c;
    }
    @NonNull
    @Override
    public Rl3Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.rl3, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Rl3Adapter.MyViewHolder holder, int i) {
        Picasso.with(c).load(li.get(i).getImg()).fit().centerCrop().into(holder.im);
                  holder.t1.setText(li.get(i).getT1());
                  final String na= holder.t1.getText().toString();
                  holder.b1.setText(li.get(i).getB());
        if(i%3==0)
        {
            holder.rl3c.setCardBackgroundColor(Color.parseColor("#BA3935"));
            holder.t1.setTextColor(Color.WHITE);
            holder.b1.setTextColor(Color.WHITE);
        }
        else if(i%2==4) {
            holder.rl3c.setCardBackgroundColor(Color.parseColor("#242424"));
            holder.t1.setTextColor(Color.WHITE);
            holder.b1.setTextColor(Color.WHITE);
        }
        else if(i%2==0)
        {
            holder.rl3c.setCardBackgroundColor(Color.parseColor("#F3CB0D"));
        }


        else{ holder.rl3c.setCardBackgroundColor(Color.parseColor("#3A449F"));
            holder.t1.setTextColor(Color.WHITE);
            holder.b1.setTextColor(Color.WHITE);
        }
        holder.rl3c.setOnClickListener(new View.OnClickListener() {
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
    public Filter getFilter() {
        return filter;
    }
        Filter filter=new Filter() {
            //run on background thread
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                List<Users> fi=new ArrayList<>();
                int d=0;
                String abid=charSequence.toString().toLowerCase();
                if(abid.length()==0||abid.isEmpty())
                {
                    d++;
                    fi.addAll(listI);

                }
                else
                {

                    d++;
                    Toast.makeText(c,charSequence,Toast.LENGTH_LONG).show();
                    for(Users users: listI){
                        if(users.getT1().toLowerCase().contains(charSequence.toString().toLowerCase()))
                        {
                            fi.add(users);

                        }
                    }


                }
                FilterResults fil=new FilterResults();
                fil.values=fi;
                return fil;
            }
           //https://docs.google.com/forms/u/0/d/e/1FAIpQLScNeFegVLByGW2Z9bXZyQsS3oryi2CQhD5buKR96jrDv-t1bQ/formrestricted
            //run on ui thread
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                li.clear();
                li.addAll((Collection<? extends Users>) filterResults.values);
              //  Toast.makeText(c,li.size(),Toast.LENGTH_LONG).show();


                notifyDataSetChanged();

            }
        };





    @Override
    public int getItemCount() {
        return li.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
ImageView im;
TextView t1;
Button b1;
CardView rl3c;
        public MyViewHolder(@NonNull View view) {
            super(view);
            im=view.findViewById(R.id.rl3img);
            rl3c=view.findViewById(R.id.rl3c1);
            t1=view.findViewById(R.id.rl3h);
            b1=view.findViewById(R.id.rl3b1);

        }
    }
}
