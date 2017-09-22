package com.gavin.yjq.counts.buttons;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gavin.yjq.counts.R;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import static com.gavin.yjq.counts.PublicData.color;

/**
 * Created by Gavin_Y on 2017/4/5.
 * www.yejiaquan.com
 */
public class Info extends RelativeLayout {

    private ImageView head;
    private TextView name;
    private TextView EName;
    private TextView AName;
    private StarsView stars;
    private TextView num;
    private ImageView upDown;
    private TextView change;
    private ImageView changeI;
    private LinearLayout changes;

    public Info(Context context) {
        super(context);

        LayoutInflater.from(context).inflate(R.layout.info, this,true);
        head    = (ImageView)       findViewById(R.id.head);
        name    = (TextView)        findViewById(R.id.name);
        EName   = (TextView)        findViewById(R.id.EName);
        AName   = (TextView)        findViewById(R.id.AName);
        stars   = (StarsView)       findViewById(R.id.stars);
        num     = (TextView)        findViewById(R.id.num);
        upDown  = (ImageView)       findViewById(R.id.upDown);
        change  = (TextView)        findViewById(R.id.change);
        changeI = (ImageView)       findViewById(R.id.changeI);
        changes = (LinearLayout)    findViewById(R.id.changes);

        name.setTextColor(Color.rgb(255,150,0));
        name.setTextSize(24);
        EName.setTextColor(Color.rgb(100,100,100));
        EName.setTextSize(15);
        AName.setTextColor(color[5]);
        AName.setTextSize(15);
        num.setTextColor(color[5]);
        num.setTextSize(20);

        this.setClickable(true);
        this.setFocusable(true);
    }

    public Info isSetChange(boolean b,String num){
        if (b){
            change.setText("切换应用");
            change.setTextSize(10);
            changeI.setImageResource(R.mipmap.change);
        }else{
            change.setText(num);
            change.setTextSize(10);
        }
        return this;
    }

    public ImageView getHead() {
        return head;
    }

    public Info setHead(Bitmap bitmap){
        head.setImageBitmap(bitmap);
        return this;
    }
    public Info setHead(Drawable drawable){
        head.setImageDrawable(drawable);
        return this;
    }
    public Info setHead(int resource){
        head.setImageResource(resource);
        return this;
    }
    public Info setName(String name){
        this.name.setText(name);
        return this;
    }
    public Info setEName(String name){
        this.EName.setText(name);
        return this;
    }
    public Info setAName(String name){
        this.AName.setText(name);
        return this;
    }
    public Info setStar(double num){
        this.stars.setStar(num);
        this.num.setText(num+"");
        return this;
    }
    public Info setUp(boolean b){
        if (b)upDown.setBackgroundResource(R.mipmap.up);
        return this;
    }
    public LinearLayout getChanges(){
        return changes;
    }
}
