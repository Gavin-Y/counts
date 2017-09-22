package com.gavin.yjq.counts.buttons;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.gavin.yjq.counts.R;

/**
 * Created by Gavin_Y on 2017/4/5.
 * www.yejiaquan.com
 */
public class Item extends LinearLayout {
    private TextView text;
    public Item(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.item, this,true);
        text = (TextView)findViewById(R.id.text);
    }

    public Item setTextSize(int size) {
        this.text.setTextSize(size);
        return this;
    }

    public Item setTextColor(int color) {
        this.text.setTextColor(color);
        return this;
    }

    public String getText() {
        return text.getText().toString();
    }

    public Item setTextBg(int bg){
        this.text.setBackgroundResource(bg);
        return this;
    }

    public Item setText(String text){
        this.text.setText(text);
        return this;
    }
}
