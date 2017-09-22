package com.gavin.yjq.counts.buttons;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.gavin.yjq.counts.R;
import com.gavin.yjq.counts.util.DisPlayUtil;

/**
 * Created by Gavin_Y on 2017/4/4.
 */
public class StarsView extends LinearLayout {

    private ImageView[] stars = new ImageView[5];
    private Context context;

    public StarsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.stars, this,true);

        this.context = context;
        this.stars[0]=(ImageView)findViewById(R.id.star1);
        this.stars[1]=(ImageView)findViewById(R.id.star2);
        this.stars[2]=(ImageView)findViewById(R.id.star3);
        this.stars[3]=(ImageView)findViewById(R.id.star4);
        this.stars[4]=(ImageView)findViewById(R.id.star5);
    }

    public StarsView setSize(int size){
        RelativeLayout.LayoutParams lp1 = (RelativeLayout.LayoutParams) this.getLayoutParams();
        lp1.height = DisPlayUtil.dip2px(context,size);

        LayoutParams lp = (LayoutParams) stars[0].getLayoutParams();
        lp.width =DisPlayUtil.dip2px(context,size*5/6);
        lp.height=DisPlayUtil.dip2px(context,size);
        lp.weight=1;
        for (int i = 0; i < stars.length; i++) {
            stars[i].setLayoutParams(lp);
        }
        return this;
    }

    public StarsView setStar(double num) {
        int man = (int)num/2;
        double ban = num%2;
        int i ;
        for (i = 0;i < man; i++){
            stars[i].setImageResource(R.mipmap.man);
        }
        if (ban>=0.5)
            stars[i++].setImageResource(R.mipmap.ban);
        for(;i<5;i++){
            stars[i].setImageResource(R.mipmap.no_star);
        }
        return this;
    }
}
