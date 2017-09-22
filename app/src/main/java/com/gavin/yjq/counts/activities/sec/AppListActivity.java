package com.gavin.yjq.counts.activities.sec;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.amulyakhare.textdrawable.TextDrawable;
import com.gavin.yjq.counts.R;
import com.gavin.yjq.counts.adaters.MainAdapter;
import com.gavin.yjq.counts.buttons.Info;
import com.gavin.yjq.counts.include.LFRecyclerView;
import com.nostra13.universalimageloader.core.ImageLoader;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;

import static com.gavin.yjq.counts.PublicData.*;

/**
 * Created by Gavin_Y on 2017/4/17.
 * www.yejiaquan.com
 */

@EActivity(R.layout.activity)
public class AppListActivity extends AppCompatActivity{
    @ViewById
    RelativeLayout all;
    @ViewById
    LFRecyclerView body;
    @ViewById
    Toolbar toolbar;
    @ViewById
    TextView title;
//    @ViewById
//    ImageView btnRight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateAndShow();
    }

    @UiThread
    void init(){
        all.setBackgroundResource(R.mipmap.beijing);
        toolbar.setTitle("");
        title.setText("选择应用");
        title.setTextColor(Color.WHITE);

//        btnRight.setImageResource(R.mipmap.search);
//        btnRight.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(AppListActivity.this,SearchActivity_.class);
//                startActivity(intent);
//            }
//        });
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        body.setRecyclerViewHeaderColor(Color.argb(0,0,0,0));
        body.setRefresh(true);
        body.setLoadMore(false);
        body.setNoDateShow();
        body.setLFRecyclerViewListener(new LFRecyclerView.LFRecyclerViewListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        body.stopRefresh(true);
                    }
                }, 1000);
            }
            @Override
            public void onLoadMore() {}
        });
    }

    @UiThread
    void updateAndShow(){
        MainAdapter adapter = new MainAdapter(this,new ArrayList<ViewGroup>());
        for (int i = 0; i < appsInfo.size(); i++) {
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(appsInfo.get(i).getName().substring(0,1), color[0]);

            Info info = new Info(this).setHead(drawable).setName(appsInfo.get(i).getName())
                    .setEName(appsInfo.get(i).getEnName()).setAName(appsInfo.get(i).getHolder())
                    .setStar(8.6).setUp(true).isSetChange(false,"总下载量：");
            imageLoader.displayImage("http://115.159.118.111/images/"+appsInfo.get(i).getHeadPic(), info.getHead());
            final int finalI = i;
            info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(AppListActivity.this, ContentListActivity_.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("app", finalI);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            });
            adapter.addData(info);
        }
        body.setAdapter(adapter);
    }
}
