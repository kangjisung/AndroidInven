package com.example.kangjisung.likeroom.inventory.statistics.ChangeStat.Graph;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.inventory.calc;

import io.apptik.widget.MultiSlider;

public class Graph4 extends Fragment {
    calc mCalc;
    TextView mTextViewTitleQ;
    TextView mTextViewOptinumQ;
    TextView mTextViewOptinumV;
    TextView mTextViewSelectQ;
    TextView mTextViewSelectV;
    TextView mTextViewCost;
    TextView mTextViewPrice;
    int slider_width = 0;
    int s_max, s_min, s_val;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.graph4, container, false);

        MultiSlider multiSlider = (MultiSlider)rootView.findViewById(R.id.graph4_multiSlider);

        mCalc = calc.getInstance();
        mTextViewTitleQ = (TextView) rootView.findViewById(R.id.tv_title_q);
        mTextViewOptinumQ = (TextView) rootView.findViewById(R.id.tv_optinum_q);
        mTextViewOptinumV = (TextView) rootView.findViewById(R.id.tv_optinum_v);
        mTextViewSelectQ = (TextView) rootView.findViewById(R.id.tv_select_q);
        mTextViewSelectV = (TextView) rootView.findViewById(R.id.tv_select_v);
        mTextViewCost = (TextView) rootView.findViewById(R.id.tv_cost);
        mTextViewPrice = (TextView) rootView.findViewById(R.id.tv_price);

        s_max = (int)(mCalc.Q*3/(float)2);
        s_min = (int)(mCalc.Q/(float)2);
        s_val = (int)(mCalc.Q*2/(float)3);

        multiSlider.setMax(s_max);
        multiSlider.setMin(s_min);
        //multiSlider.getThumb(1).setValue(mCalc.max+10);
        multiSlider.getThumb(0).setValue(s_val);
        mTextViewTitleQ.setText(String.format("%,d 원", mCalc.calcProfit(mCalc.Q)));
        mTextViewOptinumQ.setText(mCalc.Q + "개");
        mTextViewOptinumV.setText(String.format("%,d 원", mCalc.calcProfit(mCalc.Q)));
        mTextViewSelectQ.setText(String.valueOf(s_val) + "개");
        mTextViewSelectV.setText(String.format("%,d 원", mCalc.calcProfit(mCalc.Q)));
        mTextViewCost.setText(String.format("%,d 원", mCalc.c));
        mTextViewPrice.setText(String.format("%,d 원", mCalc.p));
        //mTextViewSelectQ.setX();
        slider_width = multiSlider.getWidth();

        multiSlider.setOnThumbValueChangeListener(new MultiSlider.OnThumbValueChangeListener() {
            @Override
            public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int thumbIndex, int value) {
                if(thumbIndex == 0){
                    //float val = -13+(multiSlider.getWidth()-60)*((value-s_min)/(float)(s_max-s_min));
                    mTextViewSelectV.setText(String.format("%,d 원", mCalc.calcProfit(value)));
                    //mTextViewSelectV.setText(String.valueOf(mCalc.calcProfit(mCalc.Q))+"\n"+String.valueOf(mCalc.calcProfit(value)));
                    //mTextViewSelectV.setText(String.valueOf(val)+"\n"+String.valueOf(value));
                    mTextViewSelectQ.setText(String.valueOf(value) + "개");
                    //mTextViewSelectQ.setX(val);
                }
            }
        });

        return rootView;
    }
}