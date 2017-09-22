package com.gavin.yjq.counts.buttons;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.gavin.yjq.counts.R;

/**
 * Created by Gavin_Y on 2017/4/6.
 * www.yejiaquan.com
 */
public class userBottom extends LinearLayout {
    private TextView xxfk;
    private TextView close;
    public userBottom(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.user_bottom, this,true);

        xxfk = (TextView)findViewById(R.id.xxfk);
        close = (TextView)findViewById(R.id.close);

        this.setClickable(true);
        this.setFocusable(true);
    }
}
