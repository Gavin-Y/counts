package com.gavin.yjq.counts.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.widget.*;
import com.alibaba.fastjson.JSON;
import com.amulyakhare.textdrawable.TextDrawable;
import com.gavin.yjq.counts.R;
import com.gavin.yjq.counts.activities.sec.*;
import com.gavin.yjq.counts.adaters.MainAdapter;
import com.gavin.yjq.counts.buttons.*;
import com.gavin.yjq.counts.include.LFRecyclerView;
import com.gavin.yjq.counts.model.classify;
import com.nostra13.universalimageloader.core.ImageLoader;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import android.support.v7.widget.Toolbar;

import static com.gavin.yjq.counts.PublicData.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gavin_Y on 2017/4/4.
 * www.yejiaquan.com
 */
@EActivity(R.layout.activity_main)
public class TestActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @ViewById
    RelativeLayout all;
    @ViewById
    LFRecyclerView body;

    @ViewById
    TextView title;
    @ViewById
    ImageView btnRight;

    @ViewById(R.id.drawer_layout)
    DrawerLayout drawer;
    @ViewById
    Toolbar toolbar;
    @ViewById(R.id.nav_view)
    NavigationView navView;

    private Info        info;
    private Time        time;
    private PieChart    pieChart;
    private LineChart   lineChart1;
    private LineChart   lineChart2;
    private LineChart   lineChart3;
    private Comment     comment;
    private HappyLine   happy;
    private HappyLine   unhappy;

    private List<ViewGroup> groupList;
    private ArrayList<String> Apps;
    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUser();
        if (appsInfo!=null&&appsInfo.size()!=0){
            updateUi(nowApp);
            show();
        }else{
            all.removeView(body);
        }
    }

    @UiThread
    void show(){
        groupList = new ArrayList<>();
        adapter = new MainAdapter(this, groupList);
        adapter.addData(info);
        adapter.addData(time);
        adapter.addData(pieChart);
        adapter.addData(lineChart1);
        adapter.addData(lineChart2);
        adapter.addData(lineChart3);
        adapter.addData(comment);
        adapter.addData(happy);
        adapter.addData(unhappy);

        body.setRecyclerViewHeaderColor(Color.argb(0,0,0,0));
        body.setAdapter(adapter);
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
    void updateUser(){
        View view=navView.getHeaderView(0);
        ImageView userHead = (ImageView) view.findViewById(R.id.userHead);
        userHead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestActivity.this,ChangeUserHead_.class);
                startActivity(intent);
            }
        });
        TextView username = (TextView)view.findViewById(R.id.username);
        TextView usermail = (TextView)view.findViewById(R.id.usermail);
//        if (userHeadBitmap==null) {
//            userHeadBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.default_userhead);
//        }
//        userHead.setImageBitmap(userHeadBitmap);
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(userInfo.getAccount().substring(0,1), Color.RED);
        userHead.setImageDrawable(drawable);
        username.setText(userInfo.getAccount());
        usermail.setText(userInfo.getEmail());

//        navView.setBackgroundResource(R.color.colorPrimary_a);
//        navView.setBackgroundColor(Color.argb(220,219,188,135));
    }

    @UiThread
    void updateUi(final int NApp){
        TextDrawable drawable = TextDrawable.builder()
                .buildRound(appsInfo.get(NApp).getName().substring(0,1), color[0]);

        info.setName(appsInfo.get(NApp).getName()).setHead(drawable).setEName(appsInfo.get(NApp).getEnName())
//                .setAName(appsInfo.get(NApp).getHolder()).setStar(Double.parseDouble(appsInfo.get(NApp).getStarLevel()))
                .setAName(appsInfo.get(NApp).getHolder()).setStar(8.6)
                .setUp(true).isSetChange(true,null).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestActivity.this,ContentListActivity_.class);
                Bundle bundle = new Bundle();
                bundle.putInt("app",NApp);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        imageLoader.displayImage("http://115.159.118.111/images/"+appsInfo.get(NApp).getHeadPic(), info.getHead());


        Apps = new ArrayList<>();
        if (appsInfo.size()!=0) {
            for (int i = 0; i < appsInfo.size(); i++) {
                Apps.add(appsInfo.get(i).getName());
            }
        }
        final ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_single_choice, Apps);
        info.getChanges().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(TestActivity.this)
                        .setTitle("切换应用")
                        .setSingleChoiceItems(adapter, nowApp, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                selectApp = which;
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                selectApp = nowApp;
                            }
                        })
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                nowApp = selectApp;
                                updateUi(nowApp);
                            }
                        })
                        .setCancelable(false)
                        .show();
            }
        });

        pieChart.setChart1(pie).setChart2(pie);

        lineChart1.setChartTv3("下载量变化").addLine(line1,color[0]).addLine(line2,color[1]).addLine(line3,color[2])
                .addLine(line4,color[3]).addLine(line5,color[4]).setAxis(axisX,axisXV,axisY,axisYV).setChart();

        lineChart2.setChartTv3("评论数变化").addLine(line1,color[0]).addLine(line2,color[1]).addLine(line3,color[2])
                .addLine(line4,color[3]).addLine(line5,color[4]).setAxis(axisX,axisXV,axisY,axisYV).setChart();

        lineChart3.setChartTv3("评分变化").addLine(line1,color[0]).addLine(line2,color[1]).addLine(line3,color[2])
                .addLine(line4,color[3]).addLine(line5,color[4]).setAxis(axisX,axisXV,axisY,axisYV).setChart();

//        comment.setContents(JSON.parseArray(appsInfo.get(NApp).getClassify(),classify.class),R.drawable.dialog_content,Color.WHITE);

        float scale = getResources().getDisplayMetrics().density;
        int len = (int)(124 * scale + 0.5f);
        happy.setHappyH(R.mipmap.happy).setHappyId("2800")
                .setHappyI((int)((screenWidth-len)*(double) 2800/(double)(2800+329)));
        unhappy.setHappyH(R.mipmap.unhappy).setHappyId("329")
                .setHappyI((int)((screenWidth-len)*(double) 329/(double)(2800+329)));
    }

    @UiThread
    void init(){
        info        = new Info(this);
        time        = new Time(this);
        pieChart    = new PieChart(this);
        lineChart1  = new LineChart(this);
        lineChart2  = new LineChart(this);
        lineChart3  = new LineChart(this);
        comment     = new Comment(this);
        happy       = new HappyLine(this);
        unhappy     = new HappyLine(this);
        all.setBackgroundResource(R.mipmap.beijing);

        toolbar.setTitle("");
        title.setText("数据大师");
        title.setTextColor(Color.WHITE);
        btnRight.setImageResource(R.mipmap.search);
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestActivity.this,SearchActivity_.class);
                startActivity(intent);
            }
        });

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView)findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        screenWidth = getWindowManager().getDefaultDisplay().getWidth();
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Intent intent = null;

        if (id == R.id.nav_sy) {
            drawer.closeDrawer(Gravity.LEFT);
        } else if (id == R.id.nav_bbfx) {

        } else if (id == R.id.nav_plxq) {
            intent = new Intent(this,AppListActivity_.class);
        } else if (id == R.id.nav_yygl) {
            intent = new Intent(this,CareAppList_.class);
        } else if (id == R.id.nav_glyxx) {

        } else if (id == R.id.nav_zhxx) {
            intent = new Intent(this,UserInfoActivity.class);
        }

        if (intent!=null){
            startActivity(intent);
        }
        return true;
    }
}
