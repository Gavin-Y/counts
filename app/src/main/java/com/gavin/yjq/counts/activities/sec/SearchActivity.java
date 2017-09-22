package com.gavin.yjq.counts.activities.sec;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.gavin.yjq.counts.R;
import com.gavin.yjq.counts.activities.WelcomeActivity;
import com.gavin.yjq.counts.adaters.MainAdapter;
import com.gavin.yjq.counts.buttons.*;
import com.gavin.yjq.counts.gen.SearchHistoryDao;
import com.gavin.yjq.counts.include.LFRecyclerView;
import com.gavin.yjq.counts.model.SearchHistory;
import com.gavin.yjq.counts.model.classify;
import com.gavin.yjq.counts.util.GreenDaoUtils;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.gavin.yjq.counts.PublicData.appsInfo;
import static com.gavin.yjq.counts.PublicData.nowApp;


/**
 * Created by Gavin_Y on 2017/4/14.
 * www.yejiaquan.com
 */

@EActivity(R.layout.activity_general)
public class SearchActivity extends Activity {

    @ViewById
    LinearLayout body;
    @ViewById
    ScrollView center;
    @ViewById
    RelativeLayout top;

    private TextView text;

    private SearchBar searchBar;

    private FlowLayout comment;

    private LFRecyclerView searchList;

    private MainAdapter adapter;

    private SearchHistoryDao dao;
    private List<SearchHistory> histories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
        show();
    }

    @UiThread
    void init(){

        center.setBackgroundResource(R.color.search_bg);
        text = new TextView(this);
        text.setText("热门搜索");
//        text.setTextColor(Color.BLUE);
        searchBar = new SearchBar(this);
        searchBar.onClickBack(this);
        top.addView(searchBar);
        comment = new FlowLayout(this);
        List<classify>list = new ArrayList<>();
        list.add(new classify("克鲁塞德战记"));
        list.add(new classify("盗墓长生印"));
        list.add(new classify("不思议迷宫"));
        list.add(new classify("王者荣耀"));
        list.add(new classify("TWoM"));
        list.add(new classify("王权"));
        list.add(new classify("炉石传说"));
        list.add(new classify("魔女之泉2"));

        comment.removeAllViews();
        for(int i = 0; i < list.size(); i ++){
            final Item item = new Item(this,null).setText(list.get(i).getKey())
                    .setTextBg(R.drawable.dialog_search).setTextColor(Color.BLACK);
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    searchBar.setSearch(item.getText());
                    update();
                }
            });
            comment.addView(item);
        }

        searchList = new LFRecyclerView(this);
        searchList.setRefresh(false);
        searchList.setFootTextColor(Color.BLACK);
        adapter = new MainAdapter(this,new ArrayList<ViewGroup>());
        searchList.setAdapter(adapter);

        searchList.getFootText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.removeAllData();
                dao.deleteAll();
                searchList.setFootText("");
            }
        });
        dao = GreenDaoUtils.getSingleTon().getmDaoSession(SearchActivity.this).getSearchHistoryDao();
        histories = dao.loadAll();

        int min = histories.size()<5?0:histories.size()-5;

        final List<SearchHistory>historyList = new ArrayList<>();
        for (int i = histories.size()-1; i >= min; i--) {
            historyList.add(histories.get(i));
        }

        for (int i = 0; i < historyList.size(); i++) {
            final SearchHistoryView searchHistory = new SearchHistoryView(this).setText(historyList.get(i).getKey());
            searchHistory.setId(i);
            searchHistory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    searchBar.setSearch(searchHistory.getText());
                    update();
                }
            });
            final int finalI = i;
            searchHistory.getDelete().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dao.deleteByKey(historyList.get(finalI).getId());
                    adapter.removeData(searchHistory.getId());
                    for (int i = 0; i < adapter.getItemCount(); i++) {
                        adapter.getItem(i).setId(i);
                    }
                    if (adapter.getItemCount()==0){
                        searchList.setFootText("");
                    }
                }
            });
            adapter.addData(searchHistory);
        }
        if (adapter.getItemCount()==0) {
            searchList.setFootText("");
        }else {
            searchList.setFootText("清空历史记录");
        }

        searchBar.getGo().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!searchBar.getSearch().isEmpty()) {
                    update();
                }
            }
        });
        searchBar.getEditText().setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!searchBar.getSearch().isEmpty()) {
                        update();
                    }
                }
                return true;
            }
        });
    }

    @UiThread
    void show(){
        body.removeAllViews();
        body.addView(text);
        body.addView(comment);
        body.addView(searchList);
    }

    Handler h;
    @UiThread
    void update(){
        searchBar.getGo().setClickable(false);
        histories = dao.loadAll();
        for (int i = 0; i < histories.size(); i++) {
            if (histories.get(i).getKey().equals(searchBar.getSearch())){
                dao.delete(histories.get(i));
            }
        }
        SearchHistory history = new SearchHistory(null,searchBar.getSearch());
        dao.insert(history);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(searchBar.getEditText().getWindowToken(), 0);
        body.removeAllViews();
        searchList = new LFRecyclerView(this);
        body.addView(searchList);
        searchList.setRefresh(false);
        searchList.setNoDateShow();
        searchList.setFootTextColor(Color.BLACK);
        adapter = new MainAdapter(this,new ArrayList<ViewGroup>());
        searchList.setAdapter(adapter);

        h = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                adapter.addData((SearchResultApp)msg.obj);
            }
        };
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    final SearchResultApp app = new SearchResultApp(SearchActivity.this);
                    app.setHead(R.color.red).setName("搜索到第"+i+"个").setFollow(R.mipmap.unfollow).setStars(8.6);
//                            .setContents(JSON.parseArray(appsInfo.get(nowApp).getClassify(),classify.class),R.drawable.dialog_search_result,Color.BLACK);
                    app.getFollow().setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            app.setFollow(R.mipmap.follow);
                        }
                    });
                    Message msg = new Message();
                    msg.obj=app;
                    h.sendMessage(msg);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                searchBar.getGo().setClickable(true);
            }
        }).start();
    }
}
