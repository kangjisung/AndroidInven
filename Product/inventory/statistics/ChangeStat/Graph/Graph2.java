package com.example.kangjisung.likeroom.inventory.statistics.ChangeStat.Graph;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.inventory.calc;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class Graph2 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.graph2, container, false);

        LineChart chart = (LineChart)view.findViewById(R.id.graph2);
        LineChart chart2 = (LineChart)view.findViewById(R.id.graph2_1);

        chart.setTouchEnabled(false);
        chart.setDoubleTapToZoomEnabled(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getXAxis().setDrawGridLines(false);
        chart.getAxisLeft().setDrawLabels(false);
        chart.getAxisRight().setDrawLabels(false);

        chart.setDrawGridBackground(false);

        chart2.setTouchEnabled(false);
        chart2.setDoubleTapToZoomEnabled(false);

        chart2.getAxisLeft().setDrawGridLines(false);
        chart2.getXAxis().setDrawGridLines(false);

        chart2.getAxisLeft().setDrawLabels(false);
        chart2.getAxisRight().setDrawLabels(false);

        ArrayList<String> x1 = new ArrayList<>();
        ArrayList<String> x2 = new ArrayList<>();
        ArrayList<Entry> y1 = new ArrayList<>();
        ArrayList<Entry> y2 = new ArrayList<>();
        String[] label1 = new String[7];
        String[] label2 = new String[12];

        calc c = calc.getInstance();

        float tmp=0;
        int count = 7;

        for(int i=0; i<c.dAvg.length; i++) y1.add(new Entry((float)c.dAvg[i],i));
        x1.add(0,"월");
        x1.add(1,"화");
        x1.add(2,"수");
        x1.add(3,"목");
        x1.add(4,"금");
        x1.add(5,"토");
        x1.add(6,"일");

        for(int i=0; i<7; i++) label1[i]=x1.get(i).toString();

        for(int i=0; i<c.monAvg.length; i++) y2.add(new Entry((float)c.monAvg[i],i));
        x2.add(0,"1월");
        x2.add(1,"2월");
        x2.add(2,"3월");
        x2.add(3,"4월");
        x2.add(4,"5월");
        x2.add(5,"6월");
        x2.add(6,"7월");
        x2.add(7,"8월");
        x2.add(8,"9월");
        x2.add(9,"10월");
        x2.add(10,"11월");
        x2.add(11,"12월");

        for(int i=0; i<12; i++) label2[i]=x2.get(i).toString();

        ArrayList<ILineDataSet> lineDataSets1 = new ArrayList<>();
        ArrayList<ILineDataSet> lineDataSets2 = new ArrayList<>();

        LineDataSet lineDataSet1 = new LineDataSet(y1,"요일별그래프");
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setColor(Color.BLUE);
        lineDataSet1.setDrawValues(false);
        //lineDataSet1.setDrawCubic(true);

        LineDataSet lineDataSet2 = new LineDataSet(y2,"월별그래프");
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setColor(Color.RED);
        lineDataSet2.setDrawValues(false);

        lineDataSets1.add(lineDataSet1);
        lineDataSets2.add(lineDataSet2);

        chart.setData(new LineData(x1,lineDataSets1));
        chart2.setData(new LineData(x2,lineDataSets2));

        return view;
    }
}