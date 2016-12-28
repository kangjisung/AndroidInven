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

    calc c;
    TextView tv1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.graph4, container, false);

        MultiSlider multiSlider = (MultiSlider)view.findViewById(R.id.graph4_multiSlider);
        c = calc.getInstance();
        tv1 = (TextView)view.findViewById(R.id.graph4_txt1);

        multiSlider.setMax(c.max);
        multiSlider.setMin(c.min);
        multiSlider.getThumb(1).setValue(c.Q);
        multiSlider.getThumb(0).setValue((c.Q+c.min)/2);

        multiSlider.setOnThumbValueChangeListener(new MultiSlider.OnThumbValueChangeListener() {
            @Override
            public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int thumbIndex, int value) {
                if(thumbIndex==0){
                    tv1.setText(String.valueOf(c.Q)+"\n"+String.valueOf(value));
                }
                else thumb.setValue(value-1);
            }
        });

        return view;
    }
}