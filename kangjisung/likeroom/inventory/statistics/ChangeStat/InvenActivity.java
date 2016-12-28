package com.example.kangjisung.likeroom.inventory.statistics.ChangeStat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.inventory.calc;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class InvenActivity extends AppCompatActivity {

    LineChart chart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inven);

        EditText editText = (EditText)findViewById(R.id.editText3);

        calc c = calc.getInstance();

        editText.setText(String.valueOf(c.FD));

        int tday= c.tDay;
        chart = (LineChart)findViewById(R.id.chart1);

        chart.setTouchEnabled(false);
        chart.setDoubleTapToZoomEnabled(false);

        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getAxisLeft().setDrawLabels(false);
        chart.getAxisRight().setDrawLabels(false);

        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setDrawLabels(false);

        ArrayList<String> x = new ArrayList<>();
        ArrayList<Entry> y1 = new ArrayList<>();

        double yy;

        for(int i=0; i<=100; i++){
            yy = 1 / Math.sqrt(Math.PI * 2 * 25) * Math.pow(Math.E, -(Math.pow((i - 50), 2) / (1.0 * Math.pow(75, 2))));
            y1.add(new Entry((float)yy,i));
            x.add(i,"item"+String.valueOf(i));
        }

        String[] label = new String[x.size()];

        for(int i=0; i<x.size(); i++){
            label[i]=x.get(i).toString();
        }

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

        LineDataSet lineDataSet1 = new LineDataSet(y1,"y1");
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setColor(Color.BLUE);
        lineDataSet1.setDrawCubic(true);
        lineDataSet1.setDrawValues(false);

        lineDataSets.add(lineDataSet1);

        chart.setData(new LineData(x,lineDataSets));

        chart.invalidate();


    }

    public void OnBtnClick(View v){
        switch(v.getId()){
            case R.id.showStatBtn:
                startActivity(new Intent(InvenActivity.this, StatActivity.class));
                overridePendingTransition(R.anim.anim_slide_in_bottom, R.anim.anim_slide_out_top);
                finish();
                break;
        }
    }
}
