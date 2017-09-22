package com.gavin.yjq.counts.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import com.alibaba.fastjson.JSON;
import com.gavin.yjq.counts.R;
import com.gavin.yjq.counts.gen.UserDao;
import com.gavin.yjq.counts.model.*;
import com.gavin.yjq.counts.services.TestService;
import com.gavin.yjq.counts.util.GreenDaoUtils;
import com.gavin.yjq.counts.util.OkHttpClientManager;

import java.util.ArrayList;
import java.util.List;

import static com.gavin.yjq.counts.PublicData.appsInfo;
import static com.gavin.yjq.counts.PublicData.cityList;
import static com.gavin.yjq.counts.PublicData.userInfo;

/**
 * Created by Gavin_Y on 2017/4/6.
 * www.yejiaquan.com
 */
public class WelcomeActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(250);
                    String result = OkHttpClientManager.getAsString("http://115.159.118.111/province/get");
                    System.out.println("UserInfoActivity:"+result);
                    http re = JSON.parseObject(result,http.class);
                    if (re.getStatus()==200) {
                        cityList = JSON.parseArray(re.getData(), province.class);
                    }
                    UserDao dao= GreenDaoUtils.getSingleTon().getmDaoSession(WelcomeActivity.this).getUserDao();
                    User user = dao.load((long)1);
                    Intent intent;
                    if(user==null){
                        intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                    }else {
                        result = OkHttpClientManager.postAsString("http://115.159.118.111/user/login",
                                new OkHttpClientManager.Param("account", user.getAccount()),
                                new OkHttpClientManager.Param("password", user.getPassword()));
                        System.out.println(result);
                        re = JSON.parseObject(result, http.class);
                        if (re.getStatus() == 200) {
                            String userStr = re.getData();
                            userInfo = JSON.parseObject(userStr,usr.class);
                            appsInfo = new ArrayList<>();
                            if (!userInfo.getAppCare().equals("[]")&&!userInfo.getAppCare().equals("")) {
                                List<careList> list = JSON.parseArray(userInfo.getAppCare(), careList.class);
                                for (int i = 0; i < list.size(); i++) {
                                    result = OkHttpClientManager.getAsString("http://115.159.118.111/app/appinfo?id=" + list.get(i).getKey());
                                    System.out.println("WelcomeActivity:"+result);
                                    re = JSON.parseObject(result, http.class);
                                    String appStr = re.getData();
                                    app app=JSON.parseObject(appStr, app.class);
                                    if (app!=null) {
                                        appsInfo.add(app);
                                    }
                                }
                            }
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(WelcomeActivity.this, TestService.class);
                                    startService(intent);
                                }
                            }).start();
                            intent = new Intent(WelcomeActivity.this, TestActivity_.class);
                        } else {
                            intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                        }
                    }
                    startActivity(intent);
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
