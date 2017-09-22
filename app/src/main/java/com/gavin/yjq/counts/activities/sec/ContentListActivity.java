package com.gavin.yjq.counts.activities.sec;

/**
 * Created by Gavin_Y on 2017/4/18.
 * www.yejiaquan.com
 */

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.gavin.yjq.counts.R;
import com.gavin.yjq.counts.activities.TestActivity;
import com.gavin.yjq.counts.adaters.MainAdapter;
import com.gavin.yjq.counts.buttons.Content;
import com.gavin.yjq.counts.include.LFRecyclerView;
import com.gavin.yjq.counts.model.content;
import com.gavin.yjq.counts.model.http;
import com.gavin.yjq.counts.util.OkHttpClientManager;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.gavin.yjq.counts.PublicData.*;

@EActivity(R.layout.activity)
public class ContentListActivity extends AppCompatActivity {
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
    @ViewById
    TextView rightText;

    private int i;

    private int num=0;

    private MainAdapter adapter;

    private List<ViewGroup>list;

    private int allI = 0;
    private Handler h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        i = getIntent().getIntExtra("app",0);
        init();
        update();
        h = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                System.out.println("ContentListActivity:"+msg.obj);
                http re = JSON.parseObject((String)msg.obj, http.class);
                if (re.getStatus() == 200) {
                    List<content>contents = JSON.parseArray(re.getData(),content.class);
                    if (contents.size()==0){
                        body.setFootText("没有啦>.<");
                    }else {
                        final String yuanyin[] = new String[]{"内容不健康","内容不正确，与应用无关","无价值信息"};
                        for (int i = 0; i < contents.size(); i++,allI++) {
                            final boolean state[] = new boolean[]{false,false,false};
                            final Content content = new Content(ContentListActivity.this);
                            content.setContent(contents.get(i).getData()).setNum(contents.get(i).getStarLevel()+"分")
                                    .setUsername(contents.get(i).getUsername()).setId(allI);
                            if (userInfo.getPermission() == 1) {
                                content.setDelete(R.mipmap.delete);
                                content.getDelete().setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        new AlertDialog.Builder(ContentListActivity.this)
                                                .setTitle("删除原因")
                                                .setMultiChoiceItems(yuanyin,state,new DialogInterface.OnMultiChoiceClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                                                        state[i]=true;
                                                    }
                                                })
                                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        for (int j = 0; j < state.length; j++) {
                                                            state[j] = false;
                                                        }
                                                    }
                                                })
                                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        int num=0;
                                                        for (int j = 0; j < state.length; j++) {
                                                            if (!state[j]){
                                                                num++;
                                                            }
                                                        }
                                                        if (num==state.length)return;
                                                        adapter.notifyItemRemoved(content.getId());
                                                        list.remove(content);
                                                        for (int j = 0; j < list.size(); j++) {
                                                            list.get(j).setId(j);
                                                        }
                                                        allI--;
                                                    }
                                                })
//                                              .setCancelable(false)
                                                .show();
                                    }
                                });
                            }
                            list.add(content);
                            adapter.notifyItemRangeInserted(list.size() - 1, 1);
                        }

                    }
                }
            }
        };
    }

    @UiThread
    void init(){
        list = new ArrayList<>();
        adapter = new MainAdapter(this,list);
        body.setAdapter(adapter);
        all.setBackgroundResource(R.mipmap.beijing);
        toolbar.setTitle("");
        title.setText(appsInfo.get(i).getName());
        title.setTextColor(Color.WHITE);

        rightText.setText("筛选");
        rightText.setTextColor(Color.WHITE);

        rightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertView("筛选条件" +
                        "", null, "取消", new String[]{"查找关键字"},
                        new String[]{"最新评论", "按评分从高到低"}, ContentListActivity.this,
                        AlertView.Style.ActionSheet, new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                switch (position){
                                    case 0:
                                        showToast("输入关键字");break;
                                    case 1:
                                        showToast("获取最新评论");break;
                                    case 2:
                                        showToast("获取评分高低");break;
                                }
                            }
                        }).show();
            }
        });

//        btnRight.setImageResource(R.mipmap.search);
//        btnRight.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(ContentListActivity.this,SearchActivity_.class);
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
        body.setRefresh(false);
        body.setLoadMore(true);
        body.setNoDateShow();
        body.setNoDateShow();
        body.setLFRecyclerViewListener(new LFRecyclerView.LFRecyclerViewListener() {
            @Override
            public void onRefresh() {}
            @Override
            public void onLoadMore() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        body.stopLoadMore();
                        update();
                    }
                }, 500);
            }
        });
    }

    @UiThread
    void update(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    int up = 5;
                    if (num != 0) up = 3;
                    String result = OkHttpClientManager.getAsString("http://115.159.118.111/item/detail?key="
                            + appsInfo.get(i).getId() + "&start=" + num + "&rows=" + up);
                    result = result.replace("<br>", "\n");
                    num += up;
                    Message msg = new Message();
                    msg.obj = result;
                    h.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
