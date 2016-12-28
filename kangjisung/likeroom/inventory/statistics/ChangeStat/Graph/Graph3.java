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

public class Graph3 extends Fragment {

    double slope,intercept;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.graph3, container, false);


        LineChart chart = (LineChart)view.findViewById(R.id.graph3);
        calc c = calc.getInstance();
        int[] tmp = new int[c.Recent100_Sale.length];
        for(int i=0; i<c.Recent100_Sale.length; i++) tmp[i]=i+1;
        calc.LinearRegression linearRegression = c.calcT();
        slope = linearRegression.slope();            //"a"x+b
        intercept = linearRegression.intercept();    //ax+"b"

        chart.setTouchEnabled(false);
        chart.setDoubleTapToZoomEnabled(false);

        chart.getAxisLeft().setDrawGridLines(false);
        chart.getXAxis().setDrawGridLines(false);

        ArrayList<String> x = new ArrayList<>();
        ArrayList<Entry> y1 = new ArrayList<>();
        ArrayList<Entry> y2 = new ArrayList<>();

        String[] label = new String[c.Recent100_Sale.length];
        for(int i=0; i<c.Recent100_Sale.length; i++){
            y1.add(new Entry(c.Recent100_Sale[i],i));
            y2.add(new Entry((float)LinearRegressionFunc(i),i));
            x.add(i,"item"+i);
            label[i]=x.get(i).toString();
        }

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

        LineDataSet lineDataSet1 = new LineDataSet(y1,"최근14일그래프");
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setColor(Color.BLUE);
        //lineDataSet1.setDrawCubic(true);

        LineDataSet lineDataSet2 = new LineDataSet(y2,"추세그래프");
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setColor(Color.RED);

        lineDataSets.add(lineDataSet1);
        lineDataSets.add(lineDataSet2);

        chart.setData(new LineData(x,lineDataSets));

        return view;
    }

    double LinearRegressionFunc(int i){
        return slope*i+intercept;
    }
}