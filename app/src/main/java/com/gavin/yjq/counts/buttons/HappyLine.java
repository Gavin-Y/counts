package com.gavin.yjq.counts.buttons;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.gavin.yjq.counts.R;

/**
 * Created by Gavin_Y on 2017/4/5.
 * www.yejiaquan.com
 */
public class HappyLine extends RelativeLayout {
    private TextView happyId;
    private ImageView b;
    private ImageView a;
    private FrameLayout fl;
    private ImageView happyH;

    public HappyLine(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.happy_line, this,true);
        b = (ImageView)findViewById(R.id.b);
        a = (ImageView)findViewById(R.id.a);
        fl = (FrameLayout)findViewById(R.id.happyI);
        happyH = (ImageView)findViewById(R.id.happyH);
        happyId = (TextView)findViewById(R.id.happyId);

        happyId.setTextColor(Color.WHITE);
    }

//    public HappyLine setGravity(boolean b){
//        if (!b) {
//            FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) a.getLayoutParams();
//            lp.gravity = Gravity.RIGHT;
//            a.setLayoutParams(lp);
//        }
//        return this;
//    }


    public HappyLine setHappyI(int width) {
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) a.getLayoutParams();
        lp.width = width;
        a.setLayoutParams(lp);
        return this;
    }

    public HappyLine setHappyId(String happyId) {
        this.happyId.setText(happyId);
        return this;
    }

    public HappyLine setHappyH(int resId) {
        this.happyH.setImageResource(resId);
        return this;
    }
}
