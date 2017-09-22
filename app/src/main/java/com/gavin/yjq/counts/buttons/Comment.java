package com.gavin.yjq.counts.buttons;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import com.gavin.yjq.counts.R;
import com.gavin.yjq.counts.model.classify;

import java.util.List;

/**
 * Created by Gavin_Y on 2017/4/5.
 * www.yejiaquan.com
 */
public class Comment extends RelativeLayout {

    private Context context;
    private FlowLayout comments;
    public Comment(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.cnm, this,true);
        this.comments = (FlowLayout)findViewById(R.id.comments);
        this.context = context;
    }

    public void setContents(List<classify> contents,int bg,int textColor) {
        comments.removeAllViews();
        for(int i = 0; i < contents.size(); i ++){
            Item view = new Item(this.context,null);
            view.setText(contents.get(i).getKey());
            view.setTextBg(bg);
            view.setTextColor(textColor);
            this.comments.addView(view);
        }
    }
}
