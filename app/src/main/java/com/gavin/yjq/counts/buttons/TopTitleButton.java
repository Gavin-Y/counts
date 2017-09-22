package com.gavin.yjq.counts.buttons;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gavin.yjq.counts.R;

public class TopTitleButton extends RelativeLayout {

    private ImageView back;
    private TextView title;
    private ImageView more;

    public TopTitleButton(Context context) {
        super(context,null);
    }

    public TopTitleButton(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);

        LayoutInflater.from(context).inflate(R.layout.title_top_btn, this,true);

        this.back=(ImageView)findViewById(R.id.back);
        this.title = (TextView)findViewById(R.id.title);
        this.more = (ImageView)findViewById(R.id.more);

        setBackgroundColor(ContextCompat.getColor(context,R.color.colorPrimary));
        title.setTextSize(18);
        title.setTextColor(ContextCompat.getColor(context,R.color.white));

        this.setClickable(true);
        this.setFocusable(true);
    }

    public void onClickBack(final Activity activity){
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
                activity.overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            }
        });
    }

    public ImageView getMore() {
        return more;
    }

    public void setBack(int resourceID) {
        this.back.setImageResource(resourceID);
    }

    public void setMore(int resourceID) {
        this.more.setImageResource(resourceID);
    }

    public void setTitle(int resourceID) {
        this.title.setText(resourceID);
    }

    public void setTitle(String text) {
        this.title.setText(text);
    }
}
