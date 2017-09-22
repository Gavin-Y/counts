package com.gavin.yjq.counts.buttons;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gavin.yjq.counts.R;
import lecho.lib.hellocharts.model.*;
import lecho.lib.hellocharts.view.LineChartView;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import static com.gavin.yjq.counts.PublicData.*;

/**
 * Created by Gavin_Y on 2017/4/5.
 * www.yejiaquan.com
 */
public class LineChart extends RelativeLayout {
    private LineChartView chart3;
    private TextView chartTv3;

    private List<Line> lines = new ArrayList<>();
    private LineChartData lineChartData;

    public LineChart(Context context) {
        super(context);
        LayoutInflater.from(context).inflate(R.layout.line_chart, this,true);

        chart3 = (LineChartView)findViewById(R.id.chart3);
        chartTv3 = (TextView)findViewById(R.id.chartTv3);

        chartTv3.setTextSize(10);
        chartTv3.setTextColor(color[5]);
    }

    public LineChart setChartTv3(String name) {
        this.chartTv3.setText(name);
        return this;
    }

    public LineChart addLine(float[] num,int color){
        List<PointValue> valueList = new ArrayList<>();
        for (int i = 0; i < num.length; i++) {
            valueList.add(new PointValue(i,num[i]));
        }
        lines.add((new Line(valueList)).setColor(color).setPointRadius(2).setStrokeWidth(1).setHasLabelsOnlyForSelected(true));
        return this;
    }

    public LineChart setAxis(int[]valueX,String[]labelX,int[]valueY,String[]labelY){
        List<AxisValue> axisValueX = new ArrayList<>();
        List<AxisValue> axisValueY = new ArrayList<>();
        int x = valueX.length>labelX.length?labelX.length:valueX.length;
        for (int i = 0; i < x; i++) {
            axisValueX.add((new AxisValue(valueX[i])).setLabel(labelX[i]));
        }
        int y = valueY.length>labelY.length?labelY.length:valueY.length;
        for (int i = 0; i < y; i++) {
            axisValueY.add((new AxisValue(valueY[i])).setLabel(labelY[i]));
        }
        Axis axisX = new Axis(axisValueX);
        Axis axisY = new Axis(axisValueY);
        lineChartData = new LineChartData(lines);
        lineChartData.setAxisXBottom(axisX);
        lineChartData.setAxisYLeft(axisY);
        return this;
    }

    public void setChart(){
        chart3.setLineChartData(lineChartData);
    }
}
