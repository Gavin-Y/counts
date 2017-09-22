package com.gavin.yjq.counts.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.*;
import com.alibaba.fastjson.JSON;
import com.bigkoo.alertview.AlertView;
import com.bigkoo.alertview.OnItemClickListener;
import com.gavin.yjq.counts.R;
import com.gavin.yjq.counts.buttons.Button;
import com.gavin.yjq.counts.gen.UserDao;
import com.gavin.yjq.counts.model.http;
import com.gavin.yjq.counts.model.province;
import com.gavin.yjq.counts.util.GreenDaoUtils;
import com.gavin.yjq.counts.util.OkHttpClientManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gavin.yjq.counts.PublicData.*;

/**
 * Created by Gavin_Y on 2017/4/7.
 * www.yejiaquan.com
 */
public class UserInfoActivity extends AppCompatActivity {
    private ListView listView;

    private Toolbar toolbar;

    private Button button;

    private InputMethodManager imm;
    private AlertView mAlertViewExt;
    private EditText etName;
    private List<String>cityNameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);

        toolbar.setTitle("账户信息");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        listView = (ListView) findViewById(R.id.list_view);

        button = (Button) findViewById(R.id.exit);
        button.setRadius(24);
        button.setTextSise(28);
        button.setTextColor(Color.WHITE);
        button.setText("退出登录");
        button.setBackground(Color.argb(150,255, 0, 0));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userInfo = null;
                nowApp = 0;
                UserDao dao= GreenDaoUtils.getSingleTon().getmDaoSession(UserInfoActivity.this).getUserDao();
                dao.deleteByKey((long)1);
                Intent intent = new Intent(UserInfoActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        init();
        update();
    }

    private void init(){
        cityNameList = new ArrayList<>();
        for (int i = 0; i < cityList.size(); i++) {
            cityNameList.add(cityList.get(i).getName());
        }
    }

    private void update(){
        listView.setAdapter(new SimpleAdapter(this,getData(), R.layout.list_user_info,
                new String[]{"title","info"}, new int[]{R.id.title,R.id.info}));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                OnItemClickListener listener = null;
                String title = "";
                if (i==5){
                    mAlertViewExt = new AlertView("更改省份",null, "取消", null,
                            new String[]{"完成"},UserInfoActivity.this, AlertView.Style.Alert, listener);
                    ViewGroup extView = (ViewGroup) LayoutInflater.from(UserInfoActivity.this).inflate(R.layout.alertlist,null);
                    Spinner etName = (Spinner) extView.findViewById(R.id.etName);
                    while (cityNameList==null){}
                    ArrayAdapter<String> adapter=new ArrayAdapter<>(UserInfoActivity.this,android.R.layout.simple_spinner_item, cityNameList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    //绑定 Adapter到控件
                    etName.setAdapter(adapter);
                    etName.setSelection(Integer.parseInt(userInfo.getLocation())-1);
                    mAlertViewExt.addExtView(extView);
                    mAlertViewExt.show();
                    return;
                }
                switch (i){
                    case 0:return;
                    case 1:
                        title = "更改昵称";
                        listener = new OnItemClickListener() {
                            @Override
                            public void onItemClick(Object o, int position) {
                                closeKeyboard();
                                //判断是否是拓展窗口View，而且点击的是非取消按钮
                                if(o == mAlertViewExt && position != AlertView.CANCELPOSITION){
                                    String name = etName.getText().toString();
                                    if(name.isEmpty()){}
                                    else{}
                                }
                            }
                        };
                        break;
                    case 2:title = "更改邮箱";break;
                    case 3:title = "更改公司";break;
                    case 4:title = "更改职业";break;
                }
                mAlertViewExt = new AlertView(title,null, "取消", null,
                        new String[]{"完成"},UserInfoActivity.this, AlertView.Style.Alert, listener);
                ViewGroup extView = (ViewGroup) LayoutInflater.from(UserInfoActivity.this).inflate(R.layout.alertext_form,null);
                etName = (EditText) extView.findViewById(R.id.etName);
                etName.setHint(getData().get(i).get("info"));
                mAlertViewExt.addExtView(extView);
                mAlertViewExt.show();
            }
        });
    }

    private void closeKeyboard() {
        //关闭软键盘
        imm.hideSoftInputFromWindow(etName.getWindowToken(),0);
        //恢复位置
        mAlertViewExt.setMarginBottom(0);
    }

    private ArrayList<Map<String,String>> getData(){
        ArrayList<Map<String, String>> data = new ArrayList<>();
        String titles[] = new String[]{"账号","昵称","邮箱","公司","职业","省份"};
        String infoes[] = new String[]{userInfo.getAccount(), userInfo.getUsername(), userInfo.getEmail(),
                userInfo.getCompany(), userInfo.getCareer(), cityList.get(Integer.parseInt(userInfo.getLocation())-1).getName()};

        Map<String, String> map;
        for (int i = 0; i < titles.length; i++) {
            map = new HashMap<>();
            map.put("title", titles[i]);
            map.put("info", infoes[i]);
            data.add(map);
        }
        return data;
    }

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
