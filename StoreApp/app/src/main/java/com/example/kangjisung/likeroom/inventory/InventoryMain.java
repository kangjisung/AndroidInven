package com.example.kangjisung.likeroom.inventory;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.Util.ColorTheme;
import com.example.kangjisung.likeroom.Util.LayoutManager;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;

public class InventoryMain extends AppCompatActivity {
    ViewPager viewPager;
    InventoryMainPagerAdapter mAdapter;
    Button mButtonPrevPager;
    Button mButtonNextPager;
    SlidingUpPanelLayout layoutSliding;

    LineChart chart;
    calc c;
    int newFD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTheme(ColorTheme.getTheme());
        setContentView(R.layout.activity_inven);

        //Intent mil = getIntent(); ////user클래스에서 눌렀던 사용자의 이름을 가져옴
        //final String name1 = mil.getExtras().getString("name");  //가져온 사용자의 이름을 넣음
        //calc c=calc.getInstance();
        //c.setName(name1);
        //c.InitCalc();

        viewPager = (ViewPager)findViewById(R.id.pager);

        mAdapter = new InventoryMainPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);

        LayoutManager.setActivityTitle(findViewById(R.id.layout_title), false, true, R.attr.theme_color_D3, "최적재고량");
        findViewById(R.id.inc_btn_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mButtonPrevPager = (Button) findViewById(R.id.btn_prev_pager);
        mButtonNextPager = (Button) findViewById(R.id.btn_next_pager);

        mButtonPrevPager.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View onClickView) {
                int position = viewPager.getCurrentItem();
                viewPager.setCurrentItem(position - 1);
                setButtonPager(position - 1);
            }
        });
        mButtonNextPager.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View onClickView) {
                int position = viewPager.getCurrentItem();
                viewPager.setCurrentItem(position + 1);
                setButtonPager(position + 1);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                setButtonPager(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        TextView graph1_txt1 = (TextView) findViewById(R.id.graph1_txt1);
        TextView graph1_txt2 = (TextView) findViewById(R.id.graph1_txt2);
        TextView graph1_txt3 = (TextView) findViewById(R.id.graph1_txt3);
        TextView graph1_graphtxt = (TextView) findViewById(R.id.graph1_graphtxt);

        chart = (LineChart) findViewById(R.id.graph1);
        c = calc.getInstance();
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
            x.add(i, i + "일");
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
        AppCompatImageView mImageViewUp = (AppCompatImageView)findViewById(R.id.iv_up);
        AppCompatImageView mImageViewDown = (AppCompatImageView)findViewById(R.id.iv_down);
        AppCompatImageView mImageViewEqual = (AppCompatImageView)findViewById(R.id.iv_equal);
        if (newFD > c.FD){
            str = "과소예측";
            str2 = "예상값을 높여주세요";
            mImageViewDown.setVisibility(View.VISIBLE);
        }
        else if (newFD == c.FD){
            str = "같습니다";
            str2 = "예상값과 같습니다";
            mImageViewEqual.setVisibility(View.VISIBLE);
        }
        else{
            str = "과다예측";
            str2 = "예상값을 낮춰주세요";
            mImageViewUp.setVisibility(View.VISIBLE);
        }
        graph1_graphtxt.setText(str);
        graph1_txt2.setText(str2);

        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

        LineDataSet lineDataSet1 = new LineDataSet(y1, "실제판매량");
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setColor(ColorTheme.getThemeColorRGB(this, R.attr.theme_color_type2));
        lineDataSet1.setDrawValues(false);
        //lineDataSet1.setDrawCubic(true);

        LineDataSet lineDataSet2 = new LineDataSet(y2, "예상판매량");
        lineDataSet2.setDrawCircles(false);
        lineDataSet2.setColor(ColorTheme.getThemeColorRGB(this, R.attr.theme_color_type1));
        lineDataSet2.setDrawValues(false);

        lineDataSets.add(lineDataSet1);
        lineDataSets.add(lineDataSet2);

        chart.setData(new LineData(x, lineDataSets));

        Button mButtonChangeCancel = (Button) findViewById(R.id.btn_change_cancel);
        Button mButtonChangeOk = (Button) findViewById(R.id.btn_change_ok);
        layoutSliding = (SlidingUpPanelLayout) findViewById(R.id.layout_sliding);

        mButtonChangeCancel.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View onClickView) {
                layoutSliding.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
            }
        });

        mButtonChangeOk.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View onClickView) {
                if (!c.isChange) {
                    c.isChange = true;
                    c.FD = newFD;
                    Toast.makeText(getApplicationContext(), "변경되었습니다.", Toast.LENGTH_SHORT).show();
                    c.updateFD();
                    layoutSliding.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                    mAdapter = new InventoryMainPagerAdapter(getSupportFragmentManager());
                    viewPager.setAdapter(mAdapter);
                    //mAdapter.notifyDataSetChanged();
                    //viewPager.invalidate();
                }
                else{
                    Toast.makeText(getApplicationContext(), "이미 변경되었습니다.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void setButtonPager(int position)
    {
        if(position >= mAdapter.getCount() - 1){
            mButtonNextPager.setVisibility(View.INVISIBLE);
            mButtonPrevPager.setVisibility(View.VISIBLE);
            return;
        }
        if(position <= 0){
            mButtonPrevPager.setVisibility(View.INVISIBLE);
            mButtonNextPager.setVisibility(View.VISIBLE);
            return;
        }
        mButtonPrevPager.setVisibility(View.VISIBLE);
        mButtonNextPager.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if(layoutSliding.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED){
            layoutSliding.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        }
        else{
            super.onBackPressed();
        }
    }
}