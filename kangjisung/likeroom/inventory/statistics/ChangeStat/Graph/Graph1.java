package com.example.kangjisung.likeroom.inventory.statistics.ChangeStat.Graph;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.inventory.calc;
import com.example.kangjisung.likeroom.inventory.statistics.ChangeStat.InvenActivity;
import com.example.kangjisung.likeroom.inventory.statistics.ChangeStat.StatActivity;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import io.apptik.widget.MultiSlider;

public class Graph1 extends Fragment implements View.OnClickListener{
    View mView;
    calc c;

    MultiSlider multiSlider;
    EditText editText,et1,et2,et3;
    ImageView chart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.graph1, container, false);
        mView=view;

        multiSlider=(MultiSlider)view.findViewById(R.id.graph1_multiSlider);
        chart=(ImageView)view.findViewById(R.id.chart1);

        editText = (EditText)view.findViewById(R.id.editText3);
        Button showStatBtn = (Button)view.findViewById(R.id.showStatBtn);
        Button changeBtn = (Button)view.findViewById(R.id.graph1_changeBtn);
        Button button = (Button)view.findViewById(R.id.button3);

        et1=(EditText)view.findViewById(R.id.SeekBarTxt1);
        et2=(EditText)view.findViewById(R.id.SeekBarTxt2);
        et3=(EditText)view.findViewById(R.id.SeekBarTxt3);

        showStatBtn.setOnClickListener(this);
        changeBtn.setOnClickListener(this);

        c = calc.getInstance();
        editText.setText(String.valueOf(c.calcQ2()));

        //////비관, 예상, 낙관 초기화/////
        et1.setText(String.valueOf(c.min));                 //비관
        et2.setText(String.valueOf((int)c.FD));             //예상
        et3.setText(String.valueOf(c.max));                 //낙관
        ////////////////////////////////////

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogSimple();
            }
        });

        RefreshGraph(c.min,(int)c.FD,c.max,true);

        int tday= c.tDay;

        multiSlider.setOnThumbValueChangeListener(new MultiSlider.OnThumbValueChangeListener() {
            @Override
            public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int thumbIndex, int value) {
                switch(thumbIndex){
                    case 0:if(value!=c.min) thumb.setValue(c.min);break;
                    case 1:
                        if(value==c.min) thumb.setValue(++value);
                        if(value==c.max) thumb.setValue(--value);
                        et2.setText(""+value);
                        RefreshGraph(c.min,value,c.max,false);
                        break;
                    case 2:if(value!=c.max) thumb.setValue(c.max);break;
                }
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id){
            case R.id.showStatBtn:
                startActivity(new Intent(super.getActivity(), StatActivity.class));
                super.getActivity().overridePendingTransition(R.anim.anim_slide_in_bottom, R.anim.anim_slide_out_top);
                super.getActivity().finish();
                break;
            case R.id.graph1_changeBtn:
                if(et1.getText().length()==0 || et2.getText().length()==0 || et3.getText().length()==0) Toast.makeText(super.getContext(),"비관, 예상, 낙관 값을 모두 입력해주세요",Toast.LENGTH_LONG).show();
                else {
                    int v1 = Integer.parseInt(et1.getText().toString());
                    int v2 = Integer.parseInt(et2.getText().toString());
                    int v3 = Integer.parseInt(et3.getText().toString());

                    if(!(v1<v2 && v2<v3)) Toast.makeText(super.getContext(),"비관값 < 예상값 < 낙관값 이어야 합니다",Toast.LENGTH_LONG).show();
                    else{
                        c.min=v1;
                        c.FD=v2;
                        c.max=v3;
                        editText.setText(String.valueOf(c.calcQ2()));
                        RefreshGraph(c.min,(int)c.FD,c.max,true);
                        Toast.makeText(super.getContext(),"변경되었습니다",Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.button3:
                DialogSimple();
                break;
        }
    }

    public void RefreshGraph(int min, int val, int max, boolean op)
    {
        if(op) {
            multiSlider.setMin(min);
            multiSlider.setMax(max);
            multiSlider.getThumb(0).setValue(min);
            multiSlider.getThumb(1).setValue(val);
            multiSlider.getThumb(2).setValue(max);
        }
        int file_num=0;
        float gap = (float)(c.max-c.min)/12;

        for(int i=1; i<=12; i++){
            if(val<=min+gap*i){
                file_num=i;
                break;
            }
        }
        //change img

        switch(file_num){
            case 1:chart.setImageResource(R.drawable.graph1);break;
            case 2:chart.setImageResource(R.drawable.graph2);break;
            case 3:chart.setImageResource(R.drawable.graph3);break;
            case 4:chart.setImageResource(R.drawable.graph4);break;
            case 5:chart.setImageResource(R.drawable.graph5);break;
            case 6:chart.setImageResource(R.drawable.graph6);break;
            case 7:chart.setImageResource(R.drawable.graph7);break;
            case 8:chart.setImageResource(R.drawable.graph8);break;
            case 9:chart.setImageResource(R.drawable.graph9);break;
            case 10:chart.setImageResource(R.drawable.graph10);break;
            case 11:chart.setImageResource(R.drawable.graph11);break;
            case 12:chart.setImageResource(R.drawable.graph12);break;
            default:chart.setImageResource(R.drawable.graph6);break;
        }
    }

    private void DialogSimple(){
        AlertDialog.Builder alt_bld = new AlertDialog.Builder(mView.getContext());
        alt_bld.setMessage("경고 내용").setCancelable(
                false).setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // 예 버튼 눌릴시 (화면 전환)
                    }
                }).setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // 아니요 버튼 눌릴시 (창닫기)
                        dialog.cancel();
                    }
                });
        AlertDialog alert = alt_bld.create();
        // Title for AlertDialog
        alert.setTitle("Title");
        // Icon for AlertDialog
        alert.show();
    }
}