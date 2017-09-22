package com.gavin.yjq.counts.buttons;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.gavin.yjq.counts.R;
import lecho.lib.hellocharts.model.PieChartData;
import lecho.lib.hellocharts.model.SliceValue;
import lecho.lib.hellocharts.view.PieChartView;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import static com.gavin.yjq.counts.PublicData.*;

/**
 * Created by Gavin_Y on 2017/4/5.
 * www.yejiaquan.com
 */
public class PieChart extends LinearLayout {
    private PieChartView chart1;
    private PieChartView chart2;
    private TextView chartTv1;
    private TextView chartTv2;
    private TextView itemT1;
    private TextView itemT2;
    private TextView itemT3;
    private TextView itemT4;
    private TextView itemT5;
    private ImageView itemC1;
    private ImageView itemC2;
    private ImageView itemC3;
    private ImageView itemC4;
    private ImageView itemC5;
    public PieChart(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.pie_chart, this,true);

        chart1  = (PieChartView)findViewById(R.id.chart1);
        chart2  = (PieChartView)findViewById(R.id.chart2);
        chartTv1= (TextView)    findViewById(R.id.chartTv1);
        chartTv2= (TextView)    findViewById(R.id.chartTv2);
        itemT1  = (TextView)    findViewById(R.id.itemT1);
        itemT2  = (TextView)    findViewById(R.id.itemT2);
        itemT3  = (TextView)    findViewById(R.id.itemT3);
        itemT4  = (TextView)    findViewById(R.id.itemT4);
        itemT5  = (TextView)    findViewById(R.id.itemT5);
        itemC1  = (ImageView)   findViewById(R.id.itemC1);
        itemC2  = (ImageView)   findViewById(R.id.itemC2);
        itemC3  = (ImageView)   findViewById(R.id.itemC3);
        itemC4  = (ImageView)   findViewById(R.id.itemC4);
        itemC5  = (ImageView)   findViewById(R.id.itemC5);



        chartTv1.setText("评论来源分布");
        chartTv2.setText("下载量来源分布");
        chartTv1.setTextSize(10);
        chartTv2.setTextSize(10);
        chartTv1.setTextColor(color[5]);
        chartTv2.setTextColor(color[5]);

        itemC1.setBackgroundColor(color[0]);
        itemC2.setBackgroundColor(color[1]);
        itemC3.setBackgroundColor(color[2]);
        itemC4.setBackgroundColor(color[3]);
        itemC5.setBackgroundColor(color[4]);

        itemT1.setText(" TapTap");
        itemT2.setText(" 三星应用商店");
        itemT3.setText(" 小米应用商店");
        itemT4.setText(" 苹果应用商店");
        itemT5.setText(" 谷歌应用商店");
        itemT1.setTextSize(10);
        itemT2.setTextSize(10);
        itemT3.setTextSize(10);
        itemT4.setTextSize(10);
        itemT5.setTextSize(10);
    }

    private PieChartData setData(float[]value){
        List<SliceValue> values = new ArrayList<>();
        values.add((new SliceValue(value[0],color[0])).setLabel(value[0]+""));
        values.add((new SliceValue(value[1],color[1])).setLabel(value[1]+""));
        values.add((new SliceValue(value[2],color[2])).setLabel(value[2]+""));
        values.add((new SliceValue(value[3],color[3])).setLabel(value[3]+""));
        values.add((new SliceValue(value[4],color[4])).setLabel(value[4]+""));

        PieChartData data = new PieChartData(values);
        data.setHasLabelsOutside(true);
        data.setSlicesSpacing(1);
        return data;
    }

    public PieChart setChart1(float[]value){
        chart1.setPieChartData(setData(value));
        return this;
    }

    public PieChart setChart2(float[]value){
        chart2.setPieChartData(setData(value));
        return this;
    }
}
