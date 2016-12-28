package com.example.kangjisung.likeroom.inventory.statistics.ChangeStat.Graph;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.inventory.calc;
import com.example.kangjisung.likeroom.inventory.statistics.ChangeStat.InvenActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class Graph1 extends Fragment implements View.OnClickListener{
    View mView;
    calc c;
    int newFD;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.graph1, container, false);
        mView=view;

        TextView graph1_txt1 = (TextView)view.findViewById(R.id.graph1_txt1);
        TextView graph1_txt2 = (TextView)view.findViewById(R.id.graph1_txt2);
        TextView graph1_txt3 = (TextView)view.findViewById(R.id.graph1_txt3);
        TextView graph1_graphtxt = (TextView)view.findViewById(R.id.graph1_graphtxt);

        Button graph1_change = (Button)view.findViewById(R.id.graph1_change);
        graph1_change.setOnClickListener(this);

        LineChart chart = (LineChart)view.findViewById(R.id.graph1);
        c = calc.getInstance();

        chart.setTouchEnabled(false);
        chart.setDoubleTapToZoomEnabled(false);

        chart.getAxisLeft().setDrawGridLines(false);
        chart.getXAxis().setDrawGridLines(false);

        ArrayList<String> x = new ArrayList<>();
        ArrayList<Entry> y1 = new ArrayList<>();
        ArrayList<Entry> y2 = new ArrayList<>();

        float tmp=0;
        int count = 7;
        int SumD=0,SumFD=0;

        String[] label = new String[c.Recent100_Sale.length];
        for(int i=0; i<c.Recent100_Sale.length; i++){
            y1.add(new Entry(c.Recent100_Sale[i],i));
            y2.add(new Entry(c.Recent100_FD[i],i));
            SumD+=c.Recent100_Sale[i];
            SumFD+=c.Recent100_FD[i];
            x.add(i,"item"+i);
            label[i]=x.get(i).toString();
        }

        newFD = (int)(c.FD*((float)SumD/SumFD));

        graph1_txt1.setText(String.valueOf((int)c.FD));
        graph1_txt3.setText(String.valueOf(newFD));

        String str;
        if(SumD>SumFD) str="과소예측";
        else if(SumD==SumFD) str="같습니다";
        else str="과다예측";
        graph1_graphtxt.setText(str);

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

        LineDataSet lineDataSet1 = new LineDataSet(y1,"실제판매량");
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setColor(Color.BLUE);
        //lineDataSet1.setDrawCubic(true);

        LineDataSet lineDataSet2 = new LineDataSet(y2,"예상판매량");
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setColor(Color.RED);

        lineDataSets.add(lineDataSet1);
        lineDataSets.add(lineDataSet2);

        chart.setData(new LineData(x,lineDataSets));

        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id){
            case R.id.graph1_change:
                c.FD=newFD;
                startActivity(new Intent(super.getActivity(), InvenActivity.class));
                super.getActivity().overridePendingTransition(R.anim.anim_slide_in_top, R.anim.anim_slide_out_bottom);
                super.getActivity().finish();
        }
    }
}