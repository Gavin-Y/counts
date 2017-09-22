package com.gavin.yjq.counts;

import android.graphics.Bitmap;
import android.graphics.Color;
import com.gavin.yjq.counts.model.app;
import com.gavin.yjq.counts.model.province;
import com.gavin.yjq.counts.model.usr;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gavin_Y on 2017/4/5.
 * www.yejiaquan.com
 */
public class PublicData {

    public final static int ALBUM_REQUEST_CODE = 0;
    public final static int CROP_REQUEST_CODE = 2;


    public static int color[] = new int[]{Color.rgb(255,0,40),Color.rgb(0,182,234),
            Color.rgb(186,78,206),Color.rgb(125,215,0),
            Color.rgb(255,189,0),Color.rgb(0,144,255)};

    public static int colorR[] = new int[]{R.color.color1,R.color.color2,R.color.color3,
            R.color.color4,R.color.color5,R.color.color6};

    public static float[]pie;

    public static float[]line1;
    public static float[]line2;
    public static float[]line3;
    public static float[]line4;
    public static float[]line5;
    public static int[]axisX;
    public static int[]axisY;
    //String[]axisXV = new String[]{"1月","2月","3月","4月","5月","6月","7月","8月","9月","10月","11月","12月"};
    public static String[]axisXV;
    public static String[]axisYV;

    public static int nowApp = 0;

    public static int selectApp;

    public static usr userInfo;

    public static List<province> cityList;

    public static ImageLoader imageLoader = ImageLoader.getInstance();

    public static List<app> appsInfo;

    public static Bitmap userHeadBitmap = null;

    //屏幕宽度
    public static int screenWidth;
}
