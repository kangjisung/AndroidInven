package com.example.kangjisung.likeroom.inventory.statistics.ChangeStat.Graph;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.inventory.calc;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

import io.apptik.widget.MultiSlider;

public class Graph4 extends Fragment {

    calc c;
    TextView tv1;
    TextView tv2,tv3;
    int slider_width=0;
    int s_max,s_min,s_val;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.graph4, container, false);

        MultiSlider multiSlider = (MultiSlider)view.findViewById(R.id.graph4_multiSlider);
        c = calc.getInstance();
        tv1 = (TextView)view.findViewById(R.id.graph4_txt1);
        tv2 = (TextView)view.findViewById(R.id.graph4_tv1);
        tv3 = (TextView)view.findViewById(R.id.graph4_tv2);

        s_max=(int)(c.Q*3/(float)2);
        s_min=(int)(c.Q/(float)2);
        s_val=(int)(c.Q*2/(float)3);

        multiSlider.setMax(s_max);
        multiSlider.setMin(s_min);
        //multiSlider.getThumb(1).setValue(c.max+10);
        multiSlider.getThumb(0).setValue(s_val);
        tv3.setText(""+c.Q+"\n최적");
        tv2.setText(String.valueOf(s_val)+"\n선택");
        //tv2.setX();
        slider_width=multiSlider.getWidth();

        multiSlider.setOnThumbValueChangeListener(new MultiSlider.OnThumbValueChangeListener() {
            @Override
            public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int thumbIndex, int value) {
                if(thumbIndex==0){
                    float val = -13+(multiSlider.getWidth()-60)*((value-s_min)/(float)(s_max-s_min));
                    tv1.setText(String.valueOf(c.calcProfit(c.Q))+"\n"+String.valueOf(c.calcProfit(value)));
                    //tv1.setText(String.valueOf(val)+"\n"+String.valueOf(value));
                    tv2.setText(String.valueOf(value)+"\n선택");
                    tv2.setX(val);
                }
            }
        });

        return view;
    }
}