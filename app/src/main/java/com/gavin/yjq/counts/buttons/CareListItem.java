package com.gavin.yjq.counts.buttons;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gavin.yjq.counts.R;

/**
 * Created by Gavin_Y on 2017/5/3.
 */
public class CareListItem extends RelativeLayout {
    private ImageView head;
    private TextView name;
    private TextView comments;
    private TextView num;
    private TextView follow;
    private StarsView starsView;
    private Boolean isFollow;
    private int id;
    private int i;
    public CareListItem(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.care_app_item, this,true);
        head = (ImageView)findViewById(R.id.head);
        name = (TextView)findViewById(R.id.name);
        comments = (TextView) findViewById(R.id.comments);
        num = (TextView)findViewById(R.id.num);
        follow = (TextView)findViewById(R.id.follow);
        starsView = (StarsView)findViewById(R.id.stars);
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    public CareListItem setHead(Bitmap bitmap){
        head.setImageBitmap(bitmap);
        return this;
    }

    public ImageView getHead() {
        return head;
    }

    public CareListItem setHead(int r){
        head.setImageResource(r);
        return this;
    }

    public CareListItem setHead(Drawable drawable){
        head.setImageDrawable(drawable);
        return this;
    }

    public CareListItem setName(String str){
        name.setText(str);
        return this;
    }

    public CareListItem setComments(String str) {
        this.comments.setText(str);
        return this;
    }

    public CareListItem setStarsView(double star) {
        this.starsView.setStar(star).setSize(16);
        this.num.setText(star+"");
        return this;
    }

    public TextView getFollow() {
        return follow;
    }

    public Boolean isFollow() {
        return isFollow;
    }

    public CareListItem setFollow(boolean b) {
        if (b) {
            this.isFollow = b;
            this.follow.setText("取消关注");
        }else{
            this.isFollow = b;
            this.follow.setText("关注");
        }
        return this;
    }
}
