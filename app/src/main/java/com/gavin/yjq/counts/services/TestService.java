package com.gavin.yjq.counts.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.alibaba.fastjson.JSON;
import com.gavin.yjq.counts.model.classify;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;

import static com.gavin.yjq.counts.PublicData.*;
/**
 * Created by Gavin_Y on 2017/4/6.
 * www.yejiaquan.com
 */
public class TestService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("--------------Service绑定");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        pie = new float[]{25,10,30,15,20};
        line1=new float[]{15,15,15,45,45,45,75,45,45,45,75,45};
        line2=new float[]{40,40,90,90,75,45,45,45,90,90,90,45};
        line3=new float[]{70,70,70,15,70,15,15,75,15,75,15,15};
        line4=new float[]{90,90,100,105,90,90,90,90,90,90,90,90};
        line5=new float[]{80,80,80,80,80,80,80,80,80,80,80,80};
        axisX = new int[]{0,1,2,3,4,5,6,7,8,9,10,11};
        axisY = new int[]{0,30,60,90,120};
        axisXV = new String[]{"1","2","3","4","5","6","7","8","9","10","11","12"};
        axisYV = new String[]{"0","30","60","90","120"};

        imageLoader.init(ImageLoaderConfiguration.createDefault(getApplicationContext()));

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
