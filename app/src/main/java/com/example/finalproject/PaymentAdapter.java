package com.example.finalproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.Show> {
    List<OrderShow> list;
    Context c;public PaymentAdapter(Context c, List<OrderShow> list) {
        this.c = c;
        this.list = list;

    }

    @NonNull
    @Override
    public PaymentAdapter.Show onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orderadapter, parent, false);


        return new PaymentAdapter.Show(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PaymentAdapter.Show holder, final int i) {
        if (i + 1 == 1)
            holder.count.setText("1stOrder\nSeeMore...");
        else if (i + 1 == 2)
            holder.count.setText("2ndOrder\nSeeMore...");
        else if (i + 1 == 3)
            holder.count.setText("3rdOrder\nSeeMore...");
        else
            holder.count.setText(i + 1 + "thOrder\nSeeMore...");
        holder.cdate.setText(list.get(i).getIdate());
        holder.end.setText(list.get(i).getEndDate());
        holder.start.setText(list.get(i).getIssueDate());
        holder.cdes.setText(list.get(i).getCname());
        holder.hdes.setText(list.get(i).getHname());
        holder.total.setText(list.get(i).getPrice());
        Toast.makeText(c, list.get(i).getCname() + "" + list.get(i).getHname() + "" + list.get(i).getRname(), Toast.LENGTH_LONG).show();
        holder.rname.setText(list.get(i).getRname());
        holder.qua.setText(list.get(i).getRoomscount());
        holder.count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.visi.setVisibility(View.VISIBLE);
                holder.count.setVisibility(View.GONE);
                holder.pay.setVisibility(View.VISIBLE);
                holder.pay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                         c.startActivity(new Intent(c, OrderConfirmation.class).
                                 putExtra("rn12",list.get(i).getHname()).putExtra("Qua",list.get(i).getRoomscount()).
                                 putExtra("Start",list.get(i).getIssueDate()).putExtra("End",list.get(i).getEndDate())
                         .putExtra("Name",list.get(i).getHname())
                         .putExtra("Price",list.get(i).getPrice())
                         .putExtra("Cdate",list.get(i).getIdate()));
                         list.remove(i);
                         notifyItemRemoved(i);
                    }
                });
            }
        });
        holder.off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.visi.setVisibility(View.GONE);
                holder.count.setVisibility(View.VISIBLE);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class Show extends RecyclerView.ViewHolder {
        TextView cdate, start, end, hdes, cdes, rname, qua, total, count, off;
        CardView ca1;
        LinearLayout visi;
        Button pay;

        public Show(@NonNull View itemView) {
            super(itemView);
            cdate = (TextView) itemView.findViewById(R.id.cdate);
            start = (TextView) itemView.findViewById(R.id.start);
            end = (TextView) itemView.findViewById(R.id.end);
            hdes = (TextView) itemView.findViewById(R.id.hdes);
            cdes = (TextView) itemView.findViewById(R.id.cdes);
            rname = (TextView) itemView.findViewById(R.id.rname);
            qua = (TextView) itemView.findViewById(R.id.qua);
            total = (TextView) itemView.findViewById(R.id.total);
            visi = (LinearLayout) itemView.findViewById(R.id.visi);
            count = (TextView) itemView.findViewById(R.id.count);
            off = (TextView) itemView.findViewById(R.id.off);
            pay = (Button) itemView.findViewById(R.id.pay);

        }
    }
}
