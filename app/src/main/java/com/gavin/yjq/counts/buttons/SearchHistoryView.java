package com.gavin.yjq.counts.buttons;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gavin.yjq.counts.R;

/**
 * Created by Gavin_Y on 2017/4/14.
 * www.yejiaquan.com
 */
public class SearchHistoryView extends RelativeLayout {

    private TextView text;
    private ImageView delete;

    public SearchHistoryView(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.search_history, this,true);
        text = (TextView) findViewById(R.id.history);
//        text.setTextColor(Color.WHITE);
        delete = (ImageView) findViewById(R.id.delete);
    }

    public String getText() {
        return text.getText().toString();
    }

    public ImageView getDelete() {
        return delete;
    }

    public SearchHistoryView setText(String text) {
        this.text.setText(text);
        return this;
    }
}
