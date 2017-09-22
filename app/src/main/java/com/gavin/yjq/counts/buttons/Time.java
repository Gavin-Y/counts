package com.gavin.yjq.counts.buttons;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gavin.yjq.counts.R;
import org.androidannotations.annotations.Click;

import static com.gavin.yjq.counts.PublicData.color;

/**
 * Created by Gavin_Y on 2017/4/5.
 * www.yejiaquan.com
 */
public class Time extends LinearLayout {
    private TextView week;
    private TextView month;
    private TextView year;
    private RelativeLayout weekB;
    private RelativeLayout monthB;
    private RelativeLayout yearB;
    public Time(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.time, this,true);
        week    = (TextView)        findViewById(R.id.week);
        month   = (TextView)        findViewById(R.id.month);
        year    = (TextView)        findViewById(R.id.year);
        weekB   = (RelativeLayout)  findViewById(R.id.weekB);
        monthB  = (RelativeLayout)  findViewById(R.id.monthB);
        yearB   = (RelativeLayout)  findViewById(R.id.yearB);

        week.setText("2周");
        week.setTextColor(Color.WHITE);
        week.setTextSize(20);
        weekB.setBackgroundResource(R.drawable.dialog_time_un);

        month.setText("2月");
        month.setTextColor(color[5]);
        month.setTextSize(20);
        monthB.setBackgroundResource(R.drawable.dialog_time_ac);

        year.setText("1年");
        year.setTextColor(Color.WHITE);
        year.setTextSize(20);
        yearB.setBackgroundResource(R.drawable.dialog_time_un);

        weekB.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                weekB.setBackgroundResource(R.drawable.dialog_time_ac);
                week.setTextColor(color[5]);
                monthB.setBackgroundResource(R.drawable.dialog_time_un);
                month.setTextColor(Color.WHITE);
                yearB.setBackgroundResource(R.drawable.dialog_time_un);
                year.setTextColor(Color.WHITE);
            }
        });

        monthB.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                weekB.setBackgroundResource(R.drawable.dialog_time_un);
                week.setTextColor(Color.WHITE);
                monthB.setBackgroundResource(R.drawable.dialog_time_ac);
                month.setTextColor(color[5]);
                yearB.setBackgroundResource(R.drawable.dialog_time_un);
                year.setTextColor(Color.WHITE);
            }
        });

        yearB.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                weekB.setBackgroundResource(R.drawable.dialog_time_un);
                week.setTextColor(Color.WHITE);
                monthB.setBackgroundResource(R.drawable.dialog_time_un);
                month.setTextColor(Color.WHITE);
                yearB.setBackgroundResource(R.drawable.dialog_time_ac);
                year.setTextColor(color[5]);
            }
        });
    }
}
