package com.example.finalproject;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class ApprovalAdapter extends RecyclerView.Adapter<ApprovalAdapter.NEED> {
    List<OrderShow> list;
    Context c;
    static String dele[]=new String[100];
    static int d=0;
    int len=0;
    String cname,hname,start1,end1,idate1,price,qua,rn;

    public ApprovalAdapter(Context c, List<OrderShow> list) {
        this.c = c;
        this.list = list;

    }

    @NonNull
    @Override
    public NEED onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.orderadapter, parent, false);


        return new ApprovalAdapter.NEED(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final NEED holder, final int i) {
        if(i+1==1)
            holder.count.setText("1stOrder Approval\nSeeMore...");
        else if(i+1==2)
            holder.count.setText("2ndOrder Approval\nSeeMore...");
        else if(i+1==3)
            holder.count.setText("3rdOrder Approval\nSeeMore...");
        else
            holder.count.setText(i+1+"thOrder Approval\nSeeMore...");
        holder.cdate.setText(list.get(i).getIdate());
        holder.end.setText(list.get(i).getEndDate());
        holder.start.setText(list.get(i).getIssueDate());
        holder.cdes.setText(list.get(i).getCname());
        len=list.get(i).getCname().length();
        cname=list.get(i).getCname();
        holder.hdes.setText(list.get(i).getHname());
        Toast.makeText(c,list.get(i).getHname(),Toast.LENGTH_LONG).show();
        holder.total.setText(list.get(i).getPrice());
        end1=list.get(i).getEndDate();
        hname=list.get(i).getHname();
        price=list.get(i).getPrice();
        qua=list.get(i).getRoomscount();
        start1=list.get(i).getIssueDate();
        idate1=list.get(i).getIdate();
        rn=list.get(i).getRname();
       // Toast.makeText(c, list.get(i).getCname() + "" + list.get(i).getHname() + "" + list.get(i).getRname(), Toast.LENGTH_LONG).show();
        holder.rname.setText(list.get(i).getRname());
        holder.qua.setText(list.get(i).getRoomscount());
        holder.count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.visi.setVisibility(View.VISIBLE);
                holder.count.setVisibility(View.GONE);
                holder.approve.setVisibility(View.VISIBLE);
                holder.disApprove.setVisibility(View.VISIBLE);
                holder.approve.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        SessionManagerHotels sh= new SessionManagerHotels(c,SessionManagerHotels.USERSESSION);

                        HashMap<String,String> hm=sh.returnData();
                        String name=hm.get(SessionManagerHotels.FULLNAME);
                        String emai=hm.get(SessionManagerHotels.EMAIL);

                        String subject= "Your Last Order Has Been Approved. Now go the Pay Now section of your profile to complete the booking";
                        final String Email="hotelarshb7@gmail.com";
                        final String pass="arshbhotelABIDRAJU";
                        Properties prop = new Properties();
                        prop.put("mail.smtp.host", "smtp.gmail.com");
                        prop.put("mail.smtp.port", "465");
                        prop.put("mail.smtp.auth", "true");
                        prop.put("mail.smtp.socketFactory.port", "465");
                        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                        Session session = Session.getDefaultInstance(prop, new javax.mail.Authenticator() {
                            protected PasswordAuthentication getPasswordAuthentication() {
                                return new PasswordAuthentication(Email, pass);
                            }
                        });
                        try {

                            Message message = new MimeMessage(session);
                            message.setFrom(new InternetAddress(Email));
                            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(list.get(i).getEmail()));
                            message.setSubject("Successful");
                            message.setText(subject);

                            new ApprovalAdapter.SendEmail().execute(message);

                        } catch (MessagingException e) {
                            e.printStackTrace();
                            /*StrictMode.ThreadPolicy po=new StrictMode.ThreadPolicy.Builder().permitAll().build();
                            StrictMode.setThreadPolicy(po);*/
                        }
                      FirebaseDatabase.getInstance().getReference("Hotels").child(name).child("needApp").child(start1+end1+price+rn+idate1).removeValue();
                        list.remove(i);
                        notifyItemRemoved(i);
                        int k=0;
                        String userNum="";
                        for(int j=len-1;;j--)
                        {
                            if(k==11)
                                break;
                            userNum+=cname.charAt(j);
                            k++;
                        }
                        StringBuilder input1 = new StringBuilder();
                        input1.append(userNum);
                        input1.reverse();
                        userNum=input1.toString();
                        userNum="+88"+userNum;
                        OrderShow or=new OrderShow(cname,hname,price,qua,start1,
                               end1,rn,idate1,emai);
                        FirebaseDatabase.getInstance().getReference("Users").child(userNum).
                                child("Payment").child(qua+start1+end1+price+idate1).setValue(or);
                        FirebaseDatabase.getInstance().getReference("Users").child(userNum).child("Token").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                String token=dataSnapshot.child("token").getValue().toString();
                                FcmNotificationsSender fcm=new FcmNotificationsSender(token,"Successful","Your last order has been approved.",c, (Activity) c);
                                Toast.makeText(c,token,Toast.LENGTH_LONG).show();
                                fcm.SendNotifications();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });

                    }
                });
            }
        });
        holder.disApprove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                dele[d]=list.get(i).getIssueDate()+list.get(i).getEndDate();
                d++;
                list.remove(i);
                notifyItemRemoved(i);
                int k=0;
                String userNum="";
                for(int j=len-1;;j--)
                {
                    if(k==11)
                        break;
                    userNum+=cname.charAt(j);
                    k++;
                }
                StringBuilder input1 = new StringBuilder();
                input1.append(userNum);
                input1.reverse();
                userNum=input1.toString();
                userNum="+88"+userNum;
                FirebaseDatabase.getInstance().getReference("Users").child(userNum).child("Token").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String token=dataSnapshot.child("token").getValue().toString();
                        FcmNotificationsSender fcm=new FcmNotificationsSender(token,"Successful","Your last order has been approved.",c, (Activity) c);
                        Toast.makeText(c,token,Toast.LENGTH_LONG).show();
                        fcm.SendNotifications();

                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

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
    public class NEED extends RecyclerView.ViewHolder {
        TextView cdate, start, end, hdes, cdes, rname, qua, total, count, off;
        CardView ca1;
        LinearLayout visi;
        Button approve, disApprove;

        public NEED(@NonNull View itemView) {
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
            approve = (Button) itemView.findViewById(R.id.approve);
            disApprove = (Button) itemView.findViewById(R.id.disApprove);
        }
    }
    private class SendEmail extends AsyncTask<Message, String, String> {

        @Override
        protected String doInBackground(Message... messages) {

            try {
                Transport.send(messages[0]);
                return "success";
            } catch (MessagingException e) {
                e.printStackTrace();
                return "error";
            }


        }
    }
}
