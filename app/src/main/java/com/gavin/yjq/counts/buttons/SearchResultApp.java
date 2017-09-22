package com.gavin.yjq.counts.buttons;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gavin.yjq.counts.R;
import com.gavin.yjq.counts.model.classify;

import java.util.List;

/**
 * Created by Gavin_Y on 2017/4/21.
 */
public class SearchResultApp extends RelativeLayout {

    private ImageView head;
    private TextView name;
    private StarsView stars;
    private TextView num;
    private FlowLayout comments;
    private ImageView follow;
    private Context context;

    public SearchResultApp(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.view_search_app_result, this,true);
        head = (ImageView)findViewById(R.id.head);
        name = (TextView)findViewById(R.id.name);
        stars = (StarsView)findViewById(R.id.stars);
        num = (TextView)findViewById(R.id.num);
        comments = (FlowLayout)findViewById(R.id.comments);
        follow = (ImageView)findViewById(R.id.follow);

        name.setTextSize(12);

        this.context = context;
        this.setClickable(true);
        this.setFocusable(true);
    }

    public SearchResultApp setName(String name) {
        this.name.setText(name);
        return this;
    }

    public SearchResultApp setHead(int head) {
        this.head.setImageResource(head);
        return this;
    }

    public SearchResultApp setHead(Bitmap bitmap){
        this.head.setImageBitmap(bitmap);
        return this;
    }

    public SearchResultApp setStars(double num) {
        this.stars.setStar(num).setSize(16);
        this.num.setText(num+"");
        return this;
    }

    public ImageView getFollow() {
        return follow;
    }

    public SearchResultApp setFollow(int follow){
        this.follow.setImageResource(follow);
        return this;
    }

    public SearchResultApp setContents(List<classify> contents, int bg, int textColor) {
        comments.removeAllViews();
        int tmp = contents.size()>3?3:contents.size();
        for(int i = 0; i < tmp; i ++){
            Item view = new Item(this.context,null);
            view.setText(contents.get(i).getKey()).setTextBg(bg).setTextColor(textColor).setTextSize(12);
            this.comments.addView(view);
        }
        return this;
    }

}
