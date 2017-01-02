package com.example.kangjisung.likeroom.inventory.statistics.ChangeStat.Graph;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.inventory.calc;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class Graph3 extends Fragment {

    double slope,intercept;
    calc c;
    LineChart chart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.graph3, container, false);

        c = calc.getInstance();

        chart = (LineChart)view.findViewById(R.id.graph3);

        chart.setTouchEnabled(false);
        chart.setDoubleTapToZoomEnabled(false);

        chart.getAxisLeft().setDrawGridLines(false);
        chart.getXAxis().setDrawGridLines(false);
        //chart.getXAxis().setDrawLabels(false);
        chart.getAxisRight().setDrawLabels(false);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getAxisLeft().setDrawLabels(false);
        chart.getAxisLeft().setDrawGridLines(false);

        RadioGroup rd = (RadioGroup)view.findViewById(R.id.graph3_radiogroup);
        rd.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i){
                    case R.id.graph3_radio1:ShowGraph1();break;
                    case R.id.graph3_radio2:ShowGraph2();break;
                    case R.id.graph3_radio3:ShowGraph3();break;
                }
            }
        }); // 라디오버튼을 눌렸을때의 반응

        ShowGraph1();

        return view;
    }

    double LinearRegressionFunc(int i){
        return slope*i+intercept;
    }

    void ShowGraph1(){
        ArrayList<String> x = new ArrayList<>();
        ArrayList<Entry> y = new ArrayList<>();
        String[] label = new String[7];

        for(int i=0; i<c.dAvg.length; i++) y.add(new Entry(c.dAvg[i],i));
        x.add(0,"월");
        x.add(1,"화");
        x.add(2,"수");
        x.add(3,"목");
        x.add(4,"금");
        x.add(5,"토");
        x.add(6,"일");

        for(int i=0; i<7; i++) label[i]=x.get(i).toString();

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

        LineDataSet lineDataSet = new LineDataSet(y,"요일별그래프");
        lineDataSet.setDrawCircles(false);
        lineDataSet.setColor(Color.GREEN);
        lineDataSet.setDrawValues(false);

        lineDataSets.add(lineDataSet);

        chart.setData(new LineData(x,lineDataSets));
        chart.invalidate();
    }

    void ShowGraph2(){
        ArrayList<String> x = new ArrayList<>();
        ArrayList<Entry> y = new ArrayList<>();
        String[] label = new String[12];

        for(int i=0; i<c.monAvg.length; i++) y.add(new Entry(c.monAvg[i],i));
        x.add(0,"1월");
        x.add(1,"2월");
        x.add(2,"3월");
        x.add(3,"4월");
        x.add(4,"5월");
        x.add(5,"6월");
        x.add(6,"7월");
        x.add(7,"8월");
        x.add(8,"9월");
        x.add(9,"10월");
        x.add(10,"11월");
        x.add(11,"12월");

        for(int i=0; i<12; i++) label[i]=x.get(i).toString();

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

        LineDataSet lineDataSet = new LineDataSet(y,"월별그래프");
        lineDataSet.setDrawCircles(false);
        lineDataSet.setColor(Color.MAGENTA);
        lineDataSet.setDrawValues(false);

        lineDataSets.add(lineDataSet);

        chart.setData(new LineData(x,lineDataSets));
        chart.invalidate();
    }

    void ShowGraph3(){
        int[] tmp = new int[c.Recent16_WeekSale.length];
        for(int i=0; i<c.Recent16_WeekSale.length; i++) tmp[i]=i+1;
        calc.LinearRegression linearRegression = c.calcT();
        slope = linearRegression.slope();            //"a"x+b
        intercept = linearRegression.intercept();    //ax+"b"

        ArrayList<String> x = new ArrayList<>();
        ArrayList<Entry> y1 = new ArrayList<>();
        ArrayList<Entry> y2 = new ArrayList<>();

        String[] label = new String[c.Recent16_WeekSale.length];
        for(int i=0; i<c.Recent16_WeekSale.length; i++){
            y1.add(new Entry(c.Recent16_WeekSale[i],i));
            y2.add(new Entry((float)LinearRegressionFunc(i),i));
            x.add(i,""+i+"주");
            label[i]=x.get(i).toString();
        }

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

        LineDataSet lineDataSet1 = new LineDataSet(y1,"최근16주그래프");
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setColor(Color.BLUE);
        lineDataSet1.setDrawValues(false);
        //lineDataSet1.setDrawCubic(true);

        LineDataSet lineDataSet2 = new LineDataSet(y2,"추세그래프");
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setColor(Color.RED);
        lineDataSet2.setDrawValues(false);

        lineDataSets.add(lineDataSet1);
        lineDataSets.add(lineDataSet2);

        chart.setData(new LineData(x,lineDataSets));
        chart.invalidate();
    }
}