package com.teamdk.android.bakery.inventory.fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.teamdk.android.bakery.R;
import com.teamdk.android.bakery.inventory.calc;

import io.apptik.widget.MultiSlider;

public class Fragment1 extends Fragment implements View.OnClickListener{
    View mView;
    calc c;

    MultiSlider multiSlider;
    TextView mTextViewStoreSelect;
    TextView mTextViewStoreRec;
    TextView mTextViewSellSelect;
    TextView mTextViewSellRec;
    ImageView chart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.inventory_fragment1, container, false);
        mView = view;

        multiSlider=(MultiSlider)view.findViewById(R.id.graph1_multiSlider);
        chart=(ImageView)view.findViewById(R.id.chart1);

        TextView tv_bdname = (TextView)view.findViewById(R.id.bdName);

        mTextViewStoreSelect = (TextView)view.findViewById(R.id.tv_store_select);
        mTextViewStoreRec = (TextView)view.findViewById(R.id.tv_store_rec);
        mTextViewSellSelect = (TextView)view.findViewById(R.id.tv_sell_select);
        mTextViewSellRec = (TextView)view.findViewById(R.id.tv_sell_rec);
        Button changeBtn = (Button)view.findViewById(R.id.graph1_changeBtn);
        Button button = (Button)view.findViewById(R.id.button3);

        changeBtn.setOnClickListener(this);

        c = calc.getInstance();
        mTextViewStoreSelect.setText(String.valueOf(c.calcQ2()));

        tv_bdname.setText(c.name);

        //////비관, 예상, 낙관 초기화/////
        /*
        et1.setText(String.valueOf(c.min));                 //비관
        et2.setText(String.valueOf((int)c.FD));             //예상
        et3.setText(String.valueOf(c.max));                 //낙관
        */
        ////////////////////////////////////

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogSimple();
            }
        });

        int tday= c.tDay;

        multiSlider.setOnThumbValueChangeListener(new MultiSlider.OnThumbValueChangeListener() {
            @Override
            public void onValueChanged(MultiSlider multiSlider, MultiSlider.Thumb thumb, int thumbIndex, int value) {

                        if (value == c.min) thumb.setValue(++value);
                        if (value == c.max) thumb.setValue(--value);
                        mTextViewSellSelect.setText("" + value);
                        //RefreshGraph(c.min, value, c.max, false);

                /*
                if (et1.getText().length() == 0 || et2.getText().length() == 0 || et3.getText().length() == 0) {
                }
                else {
                    int v1 = Integer.parseInt(et1.getText().toString());
                    int v2 = Integer.parseInt(et2.getText().toString());
                    int v3 = Integer.parseInt(et3.getText().toString());

                    if (!(v1 < v2 && v2 < v3)) {

                    } else {
                        c.min = v1;
                        c.FD = v2;
                        c.max = v3;
                        mTextViewStoreSelect.setText(String.valueOf(c.calcQ2()));
                        //RefreshGraph(c.min, (int) c.FD, c.max, true);
                    }
                    //c.updateFD();//FD변경
                    //c.updateQ();//Q변경
                }
                */
            }
        });

        return view;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id){
            case R.id.graph1_changeBtn:
                /*
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
                        mTextViewStoreSelect.setText(String.valueOf(c.calcQ2()));
                        Toast.makeText(super.getContext(),"변경되었습니다",Toast.LENGTH_SHORT).show();
                    }
                    c.updateFD();//FD변경
                    c.updateQ();//Q변경
                }
                */
                break;
            case R.id.button3:
                DialogSimple();
                break;
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