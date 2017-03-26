package com.teamdk.android.bakery.inventory.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamdk.android.bakery.R;
import com.teamdk.android.bakery.utility.ColorTheme;
import com.teamdk.android.bakery.inventory.calc;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class Fragment2 extends Fragment {

    double slope,intercept;
    calc mCalc;
    LineChart chart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.inventory_fragment2, container, false);

        mCalc = calc.getInstance();

        chart = (LineChart)rootView.findViewById(R.id.chart);
        chart.setDescription("");
        //chart.setTouchEnabled(false);
        //chart.setDoubleTapToZoomEnabled(false);

        XAxis xAxis = chart.getXAxis();
        xAxis.setDrawLabels(true);
        xAxis.setDrawGridLines(true);

        YAxis leftAxis = chart.getAxisLeft();
        leftAxis.setDrawLabels(true);
        leftAxis.setDrawGridLines(true);
        leftAxis.setDrawZeroLine(true);
        leftAxis.setAxisLineWidth(2f);
        leftAxis.setZeroLineWidth(2f);

        YAxis rightAxis = chart.getAxisRight();
        rightAxis.setDrawLabels(false);
        rightAxis.setDrawGridLines(false);

        TabLayout tabLayout = (TabLayout)rootView.findViewById(R.id.tabLayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch(tab.getPosition()){
                    case 0:
                        ShowGraph1();
                        break;
                    case 1:
                        ShowGraph2();
                        break;
                    case 2:
                        ShowGraph3();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        ShowGraph1();

        return rootView;
    }

    double LinearRegressionFunc(int i){
        return slope*i+intercept;
    }

    void ShowGraph1(){
        ArrayList<String> x = new ArrayList<>();
        ArrayList<Entry> y = new ArrayList<>();
        String[] label = new String[7];

        for(int i = 1; i< mCalc.dAvg.length; i++){
            y.add(new Entry((float) mCalc.dAvg[i],i));
        }
        x.add(1,"일");
        x.add(2,"월");
        x.add(3,"화");
        x.add(4,"수");
        x.add(5,"목");
        x.add(6,"금");
        x.add(7,"토");

        for(int i=0; i<7; i++){
            label[i]=x.get(i).toString();
        }

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

        LineDataSet lineDataSet = new LineDataSet(y,"요일별그래프");
        lineDataSet = setDefaultLineDataSet(lineDataSet);
        lineDataSet.setColor(ColorTheme.getThemeColorRGB(getActivity(), R.attr.theme_color_N));
        lineDataSets.add(lineDataSet);

        chart.setData(new LineData(x,lineDataSets));
        chart.invalidate();
    }

    LineDataSet setDefaultLineDataSet(LineDataSet lineDataSet) {
        lineDataSet.setCircleRadius(4f);
        lineDataSet.setCircleColor(ColorTheme.getThemeColorRGB(getActivity(), R.attr.theme_color_L1));
        lineDataSet.setValueTextSize(12f);
        lineDataSet.setValueTextColor(ContextCompat.getColor(getContext(), R.color.gray160));
        lineDataSet.setLineWidth(2f);
        lineDataSet.setFillAlpha(65);
        lineDataSet.setFillColor(ColorTemplate.getHoloBlue());
        lineDataSet.setHighLightColor(Color.rgb(244, 117, 117));

        return lineDataSet;
    }

    void ShowGraph2(){
        ArrayList<String> x = new ArrayList<>();
        ArrayList<Entry> y = new ArrayList<>();
        String[] label = new String[12];

        for(int i = 1; i< mCalc.monAvg.length; i++) y.add(new Entry((float) mCalc.monAvg[i],i));
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
        lineDataSet = setDefaultLineDataSet(lineDataSet);
        lineDataSet.setColor(ColorTheme.getThemeColorRGB(getActivity(), R.attr.theme_color_N));

        lineDataSets.add(lineDataSet);

        chart.setData(new LineData(x,lineDataSets));
        chart.invalidate();
    }

    void ShowGraph3(){
        int[] tmp = new int[mCalc.Recent16_WeekSale.length];
        for(int i = 0; i< mCalc.Recent16_WeekSale.length; i++) tmp[i]=i+1;
        calc.LinearRegression linearRegression = mCalc.calcT();
        slope = linearRegression.slope();            //"a"x+b
        intercept = linearRegression.intercept();    //ax+"b"

        ArrayList<String> x = new ArrayList<>();
        ArrayList<Entry> y1 = new ArrayList<>();
        ArrayList<Entry> y2 = new ArrayList<>();

        String[] label = new String[mCalc.Recent16_WeekSale.length];
        for(int i = 0; i< mCalc.Recent16_WeekSale.length; i++){
            if(mCalc.Recent16_WeekSale[i]==-1) break;
            y1.add(new Entry(mCalc.Recent16_WeekSale[i],i));
            y2.add(new Entry((float)LinearRegressionFunc(i),i));
            x.add(i,""+(i+1)+"주");
            label[i]=x.get(i).toString();
        }

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

        LineDataSet lineDataSet1 = new LineDataSet(y1, "최근16주그래프");
        lineDataSet1 = setDefaultLineDataSet(lineDataSet1);
        lineDataSet1.setColor(ColorTheme.getThemeColorRGB(getActivity(), R.attr.theme_color_N));
        //lineDataSet1.setDrawCubic(true);

        LineDataSet lineDataSet2 = new LineDataSet(y2, "추세그래프");
        lineDataSet2 = setDefaultLineDataSet(lineDataSet2);
        lineDataSet2.setColor(ColorTheme.getThemeColorRGB(getActivity(), R.attr.theme_color_type1));

        lineDataSets.add(lineDataSet1);
        lineDataSets.add(lineDataSet2);

        chart.setData(new LineData(x,lineDataSets));
        chart.invalidate();
    }
}