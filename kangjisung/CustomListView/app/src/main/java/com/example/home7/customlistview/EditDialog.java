package com.example.home7.customlistview;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.home7.customlistview.SQLiteDatabaseControl.ClientDataBase;
import com.example.locallistview.R;

import static com.example.locallistview.R.layout.notice;

/**
 * Created by park1 on 2016-12-27.
 */

public class EditDialog extends Activity implements View.OnClickListener {
    TextView imageButtonDate;
    TextView textViewStart;
    TextView textViewEnd;
    EditText noticeName=(EditText) findViewById(R.id.noticeName);
    EditText noticeBody=(EditText) findViewById(R.id.noticeBody);
    ToggleButton alram=(ToggleButton) findViewById(R.id.toggleButton4);
    ToggleButton eventbox=(ToggleButton) findViewById(R.id.toggleButton5);
    ToggleButton newProduct=(ToggleButton) findViewById(R.id.toggleButton6);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //view setting
        setContentView(notice);

        //startDateButton init
        imageButtonDate = (TextView) findViewById(R.id.textViewStart);
        imageButtonDate.setOnClickListener(this);
        imageButtonDate = (TextView) findViewById(R.id.textViewEnd);
        imageButtonDate.setOnClickListener(this);

        //starttextview init
        textViewStart = (TextView) findViewById(R.id.textViewStart);
        //ensetextview init
        textViewEnd = (TextView) findViewById(R.id.textViewEnd);
        findViewById(R.id.button_back).setOnClickListener(this);
        findViewById(R.id.button_ok).setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        if (view instanceof TextView) {
            if (view.getId() == R.id.textViewStart) {
                final DatePickerDialog datePickerDialog = new DatePickerDialog(this);
                datePickerDialog.show();
                //여기서 달력 선택한거 가져올겁니다.
                datePickerDialog.setOnCancelListener(new Dialog.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        textViewStart.setText(datePickerDialog.getDate());
                    }
                });
            }
        }
        if (view.getId() == R.id.textViewEnd) {
            final DatePickerDialog datePickerDialog = new DatePickerDialog(this);
            datePickerDialog.show();
            //여기서 달력 선택한거 가져올겁니다.
            datePickerDialog.setOnCancelListener(new Dialog.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    textViewEnd.setText(datePickerDialog.getDate());
                }
            });
        }
        switch (view.getId()) {
            case R.id.button_back:
                this.finish();
                break;
            //뒤로가기 버튼을 눌렀을때 현재 액티비티를 끄고 바로 전 화면으로 돌아간다
            case R.id.button_ok: {
                int noticeNum=1;
                /////////noticeNum은 공지사항 종류
                if(alram.isChecked())
                    noticeNum=1;
                else if(eventbox.isChecked())
                    noticeNum=2;
                else if(newProduct.isChecked())
                    noticeNum=3;
                new ClientDataBase("insert into `매장공지` (`매장번호`,`제목`,`내용`,`공지 시작 날짜`,`공지 마감 날짜`,`공지사항종류`) values(1,\""+noticeName.getText().toString()+"\",\""+noticeBody.getText().toString()+"\",\""+textViewStart.getText().toString()+"\",\""+textViewEnd.getText().toString()+"\",(select datetime('now','localtime')),"+noticeNum+");",2,0,getApplicationContext());
                this.finish();
                break;
            }
        }
    }
}