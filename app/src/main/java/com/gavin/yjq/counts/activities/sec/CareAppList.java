package com.gavin.yjq.counts.activities.sec;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.alibaba.fastjson.JSON;
import com.amulyakhare.textdrawable.TextDrawable;
import com.gavin.yjq.counts.R;
import com.gavin.yjq.counts.adaters.MainAdapter;
import com.gavin.yjq.counts.buttons.CareListItem;
import com.gavin.yjq.counts.include.LFRecyclerView;
import com.gavin.yjq.counts.model.app;
import com.gavin.yjq.counts.model.careList;
import com.gavin.yjq.counts.model.http;
import com.gavin.yjq.counts.model.usr;
import com.gavin.yjq.counts.util.OkHttpClientManager;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.gavin.yjq.counts.PublicData.*;

/**
 * Created by Gavin_Y on 2017/5/3.
 */
@EActivity(R.layout.activity)
public class CareAppList extends AppCompatActivity {
    @ViewById
    RelativeLayout all;
    @ViewById
    LFRecyclerView body;
    @ViewById
    Toolbar toolbar;
    @ViewById
    TextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        updata();
    }

    @UiThread
    void init(){
        toolbar.setTitle("");
        toolbar.setBackgroundResource(R.color.colorPrimary);
        title.setText("已关注的应用");
        title.setTextColor(Color.WHITE);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        body.setRecyclerViewHeaderColor(Color.argb(0,0,0,0));
        body.setRefresh(false);
        body.setLoadMore(false);
        body.setNoDateShow();
        body.setLFRecyclerViewListener(new LFRecyclerView.LFRecyclerViewListener() {
            @Override
            public void onRefresh() {}
            @Override
            public void onLoadMore() {}
        });
    }

    @UiThread
    void updata(){
        final MainAdapter adapter = new MainAdapter(this,new ArrayList<ViewGroup>());
        for (int i = 0; i < appsInfo.size(); i++) {
            TextDrawable drawable = TextDrawable.builder()
                    .buildRound(appsInfo.get(i).getName().substring(0,1), color[0]);
            final CareListItem item = new CareListItem(this).setHead(drawable).setName(appsInfo.get(i).getName())
                    .setComments(appsInfo.get(i).getDescpt()).setStarsView(8.6).setFollow(true);
            item.setId(appsInfo.get(i).getId());
            imageLoader.displayImage("http://115.159.118.111/images/"+appsInfo.get(i).getHeadPic(), item.getHead());
            item.setI(i);
            item.getFollow().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapter.removeData(item.getI());
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                String result = OkHttpClientManager.postAsString("http://115.159.118.111/user/androiddeletecare",
                                        new OkHttpClientManager.Param("account",userInfo.getAccount()),
                                        new OkHttpClientManager.Param("password",userInfo.getPassword()),
                                        new OkHttpClientManager.Param("id",item.getId()+""));
                                System.out.println("CareAppList:"+result);
                                http re = JSON.parseObject(result,http.class);
                                if (re.getStatus()==200) {
                                    userInfo = JSON.parseObject(re.getData(),usr.class);
                                    appsInfo = new ArrayList<>();
                                    if (!userInfo.getAppCare().equals("[]")&&!userInfo.getAppCare().equals("")) {
                                        List<careList> list = JSON.parseArray(userInfo.getAppCare(), careList.class);
                                        for (int i = 0; i < list.size(); i++) {
                                            result = OkHttpClientManager.getAsString("http://115.159.118.111/app/appinfo?id=" + list.get(i).getKey());
                                            System.out.println("CareAppList:" + result);
                                            re = JSON.parseObject(result, http.class);
                                            String appStr = re.getData();
                                            app app=JSON.parseObject(appStr, app.class);
                                            if (app!=null) {
                                                appsInfo.add(app);
                                            }
                                        }
                                    }
                                }else{
                                    showToast("连接失败");
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            });
            adapter.addData(item);
        }
        body.setAdapter(adapter);
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}
