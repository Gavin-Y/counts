package com.gavin.yjq.counts.buttons;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.gavin.yjq.counts.R;

/**
 * Created by Gavin_Y on 2017/4/14.
 * www.yejiaquan.com
 */
public class SearchBar extends RelativeLayout {

    private ImageView back;
    private EditText search;
    private ImageView go;

    public SearchBar(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.search_bar, this,true);
        back = (ImageView) findViewById(R.id.back);
        search = (EditText) findViewById(R.id.search);
        go = (ImageView)findViewById(R.id.go);
    }

    public EditText getEditText(){
        return this.search;
    }

    public void setSearch(String search) {
        this.search.setText(search);
    }

    public String getSearch() {
        return search.getText().toString();
    }

    public ImageView getGo() {
        return go;
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
}
