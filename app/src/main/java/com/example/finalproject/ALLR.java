package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ALLR extends RecyclerView.Adapter<ALLR.show> {
    List<CommentShow> list;
    Context c;
    String name1,price,name,n;
    public ALLR( Context c, List<CommentShow> list, String n) {
        this.c=c;
        this.list = list;
        this.n=n;
    }

    @NonNull
    @Override
    public show onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.allr, parent, false);


        return new show(view);
    }

    @Override
    public void onBindViewHolder(@NonNull show holder, int i) {
holder.profileName.setText(list.get(i).getName());
holder.review.setText(list.get(i).getComment());
if(list.get(i).getStar()!=null)
holder.rating.setRating(Float.parseFloat(list.get(i).getStar()));
        Picasso.with(c).load(list.get(i).getUrl()).fit().centerCrop().into(holder.profile_Image);
    }

    @Override
    public int getItemCount() {

        return list == null ? 0 : list.size();
    }

    public class show extends RecyclerView.ViewHolder{
        TextView review,profileName;
        ImageView profile_Image;
        RatingBar rating;
        public show(@NonNull View itemView) {
            super(itemView);
            review=(TextView)itemView.findViewById(R.id.review);
            profileName=(TextView)itemView.findViewById(R.id.profileName);
            profile_Image=(ImageView)itemView.findViewById(R.id.profile_image);
            rating=(RatingBar) itemView.findViewById(R.id.rating);
        }
    }
}
