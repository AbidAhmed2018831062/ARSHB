package com.example.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class OnBoarding extends AppCompatActivity {
  ViewPager vp;
  Button next,skip,start;
LinearLayout dots;
private TextView[] mdots;
Animation nan;
int item;
sliderAdapter sd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      //  requestWindowFeature(Window.FEATURE_NO_TITLE);
       getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //getSupportActionBar().hide();
        setContentView(R.layout.activity_on_boarding);
        next=(Button)findViewById(R.id.start);
        start=(Button)findViewById(R.id.next);
        skip=(Button)findViewById(R.id.skip);
        vp=(ViewPager)findViewById(R.id.vp);

        dots=(LinearLayout)findViewById(R.id.l1);
        sd=new sliderAdapter(this);
        vp.setAdapter(sd);
        addDots(0);
        vp.addOnPageChangeListener(ch);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              startActivity(new Intent(getApplicationContext(),LogIn_Or_SignUp.class));
              finish();
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item!=2)
                vp.setCurrentItem(item+1);
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),LogIn_Or_SignUp.class));
            }
        });
    }
   private void addDots(int pos)
    {
         mdots=new TextView[3];
        dots.removeAllViews();
        for(int i=0;i<3;i++)
        {
            mdots[i]=new TextView(this);
            mdots[i].setText(Html.fromHtml("&#8226"));
            mdots[i].setTextSize(35);
            dots.addView(mdots[i]);
        }
        if(pos>0)
        {
            mdots[pos].setTextColor(getResources().getColor(R.color.makeUplight));
        }
    }

    ViewPager.OnPageChangeListener ch=new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);
            item=position;
            if (position == 0) {
                next.setVisibility(View.INVISIBLE);
            }
            else if(position==1)
            {
                next.setVisibility(View.INVISIBLE);
            }
            else
            {
                nan= AnimationUtils.loadAnimation(OnBoarding.this,R.anim.bottom);
                next.setAnimation(nan);
                next.setVisibility(View.VISIBLE);
                start.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}