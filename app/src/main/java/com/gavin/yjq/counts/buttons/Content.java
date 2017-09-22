package com.gavin.yjq.counts.buttons;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gavin.yjq.counts.R;

/**
 * Created by Gavin_Y on 2017/4/18.
 * www.yejiaquan.com
 */
public class Content extends RelativeLayout {
    private TextView content;
    private TextView username;
    private TextView num;
    private ImageView delete;

    public Content(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_content, this,true);
        content = (TextView)findViewById(R.id.content);
        username = (TextView)findViewById(R.id.username);
        num = (TextView)findViewById(R.id.num);
        delete = (ImageView)findViewById(R.id.delete);

        this.setClickable(true);
        this.setFocusable(true);
    }

    public ImageView getDelete() {
        return delete;
    }

    public Content setDelete(int delete) {
        this.delete.setImageResource(delete);
        return this;
    }

    public Content setContent(String content) {
        this.content.setText(content);
        return this;
    }

    public Content setNum(String num) {
        this.num.setText(num);
        return this;
    }

    public Content setUsername(String username) {
        this.username.setText(username);
        return this;
    }
}
