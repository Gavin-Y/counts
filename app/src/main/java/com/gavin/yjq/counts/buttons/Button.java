package com.gavin.yjq.counts.buttons;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gavin.yjq.counts.R;

/**
 * Created by Gavin_Y on 2017/3/24.
 */
public class Button extends RelativeLayout {

    private RelativeLayout layout;
//    private Button btn;
    private TextView text;
    private ImageView img;


    public Button(Context context) {
        super(context,null);
    }

    public Button(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.button, this,true);

        layout  = (RelativeLayout)  findViewById(R.id.layout);
//        btn     = (Button)          findViewById(R.id.button);
        text    = (TextView)        findViewById(R.id.text);
        img     = (ImageView)       findViewById(R.id.image);

        this.setClickable(true);
        this.setFocusable(true);
    }

    public RelativeLayout getLayout(){
        return layout;
    }

    public void setBackground(int color){
        GradientDrawable myGrad = (GradientDrawable)layout.getBackground();
        myGrad.setColor(color);
    }

    public void setRadius(float radius){
        GradientDrawable myGrad = (GradientDrawable)layout.getBackground();
        myGrad.setCornerRadius(radius);
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public void setTextSise(float size){
        //sp
        this.text.setTextSize(TypedValue.COMPLEX_UNIT_SP,size);
    }

    public void setTextColor(int color){
        this.text.setTextColor(color);
    }

    public void setImg(int resource){
        this.img.setImageResource(resource);
    }
}
