package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class rl4HR extends RecyclerView.Adapter<rl4HR.RoomsAd> {

 List<Rooms> list;
    Context c;
    String name1,price,name,n,what;
    static String[] dele=new String[100];
 static int d=0;

    public rl4HR( Context c, List<Rooms> list, String n,String what) {
this.c=c;
        this.list = list;
        this.n=n;
        this.what=what;
    }

    @NonNull
    @Override
    public rl4HR.RoomsAd onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.roomsshowcase, parent, false);


        return new RoomsAd(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RoomsAd holder, final int position) {
        if(what!="Update")
        {
            holder.update.setVisibility(View.GONE);
            holder.delete.setVisibility(View.GONE);
        }
           holder.price.setText("$"+list.get(position).getPrice());
           holder.service.setText(list.get(position).getServices());
           holder.rn.setText(list.get(position).getRoomname());
        Picasso.with(c).load(list.get(position).getUrl()).fit().centerCrop().into(holder.Ab);
            name1=list.get(position).getRoomname();
            price=list.get(position).getPrice();

            holder.delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dele[d]=list.get(position).getRoomname();
                    d++;
                    list.remove(position);
                    notifyItemRemoved(position);

                    Toast.makeText(c,"KI"+d, Toast.LENGTH_LONG).show();

                }
            });
            holder.update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent in=new Intent(c,ChangeRoomInfo.class);

                          in. putExtra("price",list.get(position).getPrice());
                            in.putExtra("roomname",name1);
                            in.putExtra("services",list.get(position).getServices());
                            in.putExtra("hname",n);
                    c.startActivity(in);
                }
            });
            if(what=="ShowCase") {
                holder.ca1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent in = new Intent(c, OrderReady.class);
                        in.putExtra("Name", list.get(position).getRoomname());
                        in.putExtra("Price", list.get(position).getPrice());
                        in.putExtra("Rn", n);
                        c.startActivity(in);
                    }
                });
            }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class RoomsAd extends RecyclerView.ViewHolder {

        TextView price, service,rn;
        Button update,delete;
        CardView ca1;
        ImageView Ab;
        public RoomsAd(@NonNull View itemView) {
            super(itemView);
            price=(TextView)itemView.findViewById(R.id.price);
           service=(TextView)itemView.findViewById(R.id.service);
           rn=(TextView)itemView.findViewById(R.id.rn);
           ca1=(CardView)itemView.findViewById(R.id.ca1);
           delete=(Button)itemView.findViewById(R.id.delete);
           update=(Button)itemView.findViewById(R.id.update);
           Ab=(ImageView) itemView.findViewById(R.id.Ab);

        }
    }

}
