package com.example.finalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

public class sliderAdapter extends PagerAdapter {
    Context c;
    LayoutInflater lay;
    TextView t1,t2;
    ImageView im1;
    public sliderAdapter(Context c)
    {
        this.c=c;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

        container.removeView((ConstraintLayout)object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        lay=(LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view=lay.inflate(R.layout.slides,container,false);
        im1=(ImageView) view.findViewById(R.id.imageView);
        t1=(TextView) view.findViewById(R.id.head);
        t2=(TextView) view.findViewById(R.id.des);
        im1.setImageResource(images[position]);
        t1.setText(head[position]);
        t2.setText(des[position]);
        container.addView(view);
        return view;
    }

    int images[]={R.drawable.phone,R.drawable.email1,R.drawable.titleimage};
    int head[]={R.string.first,R.string.second,R.string.third};
    int des[]={R.string.first,R.string.second,R.string.third};

    @Override
    public int getCount() {
        return head.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==(ConstraintLayout) object;
    }
}
