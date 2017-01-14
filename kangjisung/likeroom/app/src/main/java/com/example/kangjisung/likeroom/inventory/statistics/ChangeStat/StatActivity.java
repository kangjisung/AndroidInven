package com.example.kangjisung.likeroom.inventory.statistics.ChangeStat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.inventory.calc;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.util.ArrayList;

public class StatActivity extends AppCompatActivity {

    LineChart chart;
    calc c;
    int newFD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        TextView graph1_txt1 = (TextView) findViewById(R.id.graph1_txt1);
        TextView graph1_txt2 = (TextView) findViewById(R.id.graph1_txt2);
        TextView graph1_txt3 = (TextView) findViewById(R.id.graph1_txt3);
        TextView graph1_graphtxt = (TextView) findViewById(R.id.graph1_graphtxt);

        LineChart chart = (LineChart) findViewById(R.id.graph1);
        c = calc.getInstance();

        chart.setTouchEnabled(false);
        chart.setDoubleTapToZoomEnabled(false);

        chart.getXAxis().setDrawGridLines(false);
        chart.getXAxis().setDrawLabels(false);
        chart.getAxisRight().setDrawGridLines(false);
        chart.getAxisRight().setDrawLabels(false);
        chart.getAxisLeft().setDrawGridLines(false);
        chart.getAxisLeft().setDrawLabels(false);

        ArrayList<String> x = new ArrayList<>();
        ArrayList<Entry> y1 = new ArrayList<>();
        ArrayList<Entry> y2 = new ArrayList<>();

        float tmp = 0;
        int count = 7;
        int SumD = 0, SumFD = 0;

        String[] label = new String[c.Recent100_Sale.length];
        for (int i = 0; i < c.Recent100_Sale.length; i++) {
            if(c.Recent100_Sale[i]==-1) break;
            y1.add(new Entry(c.Recent100_Sale[i], i));
            y2.add(new Entry(c.Recent100_FD[i], i));
            SumD += c.Recent100_Sale[i];
            SumFD += c.Recent100_FD[i];
            x.add(i, "item" + i);
            label[i] = x.get(i).toString();
        }

        newFD = (int) (c.FD * ((float) SumD / SumFD));
        if(newFD>=c.max) newFD=c.max-1;
        if(newFD<=c.min) newFD=c.min+1;

        graph1_txt1.setText(String.valueOf((int) c.FD));
        graph1_txt3.setText(String.valueOf(newFD));

        // 예상값을 낮춰주세요 // 높여주세요

        String str;
        String str2;
        if (newFD > c.FD){
            str = "과소예측";
            str2 = "예상값을 높여주세요\n==>";
        }
        else if (newFD == c.FD){
            str = "같습니다";
            str2 = "예상값과 같습니다\n==>";
        }
        else{
            str = "과다예측";
            str2 = "예상값을 낮춰주세요\n==>";
        }
        graph1_graphtxt.setText(str);
        graph1_txt2.setText(str2);

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

        LineDataSet lineDataSet1 = new LineDataSet(y1, "실제판매량");
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setColor(Color.BLUE);
        lineDataSet1.setDrawValues(false);
        //lineDataSet1.setDrawCubic(true);

        LineDataSet lineDataSet2 = new LineDataSet(y2, "예상판매량");
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setColor(Color.RED);
        lineDataSet2.setDrawValues(false);

        lineDataSets.add(lineDataSet1);
        lineDataSets.add(lineDataSet2);

        chart.setData(new LineData(x, lineDataSets));
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction()==KeyEvent.ACTION_DOWN && event.getKeyCode() == KeyEvent.KEYCODE_BACK) { // 백 버튼
            //Toast.makeText(this, "Back키를 누르셨군요", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, InvenActivity.class));
            overridePendingTransition(R.anim.anim_slide_in_top, R.anim.anim_slide_out_bottom);
            this.finish();
            return false;
        }
        return true;
    }

    public void OnBtnClick(View v) {
        switch (v.getId()) {
            case R.id.graph1_change:
                if (!c.isChange) {
                    c.isChange = true;
                    c.FD = newFD;
                    Toast.makeText(getApplicationContext(), "변경되었습니다.", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(StatActivity.this, InvenActivity.class));
                    overridePendingTransition(R.anim.anim_slide_in_top, R.anim.anim_slide_out_bottom);
                    c.updateFD();
                    finish();
                }
                else Toast.makeText(getApplicationContext(), "이미 변경되었습니다.", Toast.LENGTH_SHORT).show();
                break;
            case R.id.showInvenBtn:
                startActivity(new Intent(StatActivity.this, InvenActivity.class));
                overridePendingTransition(R.anim.anim_slide_in_top, R.anim.anim_slide_out_bottom);
                finish();
                break;
        }
    }
}