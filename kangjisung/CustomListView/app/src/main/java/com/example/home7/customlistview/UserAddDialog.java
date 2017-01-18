package com.example.home7.customlistview;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;


import com.example.home7.customlistview.SQLiteDatabaseControl.ClientDataBase;
import com.example.locallistview.R;

import java.util.Date;

import static android.R.attr.name;
import static android.R.string.no;
import static com.example.locallistview.R.id.noticeBody;
import static com.example.locallistview.R.id.noticeName;
import static com.example.locallistview.R.id.textViewEnd;
import static com.example.locallistview.R.id.textViewStart;
import static com.example.locallistview.R.layout.view;

/**
 * Created by kangjisung on 2017-01-16.
 */

public class UserAddDialog extends Dialog implements View.OnClickListener {
    String nameStr,noticeBd;
    Date noticeStDate,noticeEDate;
    int type;
    public UserAddDialog(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
    }
////////////////////////////////////////////////////////////////////////////////공지사항 불러오는 생성자
    public UserAddDialog(String name, String noBd, Date noStDate, Date noEDate, int type1,Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        nameStr = name;//공지사항제목
        noticeBd = noBd;//공지사항내용
        noticeStDate = noStDate;//시작날짜
        noticeEDate = noEDate;//마감날짜
        type = type1;//공지사항종류
    }

    TextView imageButtonDate;
    TextView textViewStart;
    TextView textViewEnd;
    EditText noticeName;
    EditText noticeBody;
    ToggleButton alram;
    ToggleButton eventbox;
    ToggleButton newProduct;
    String textStDate;
    String textEDate;

    private Date modifyStartDate;
    private Date modifyEndDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.notice);

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
        noticeName=(EditText) findViewById(R.id.noticeName);
        noticeBody=(EditText) findViewById(R.id.noticeBody);
        alram=(ToggleButton) findViewById(R.id.toggleButton4);
        eventbox=(ToggleButton) findViewById(R.id.toggleButton5);
        newProduct=(ToggleButton) findViewById(R.id.toggleButton6);

        /////////////데이트값 스트링으로 세팅
        textStDate = String.valueOf(noticeStDate.getYear()+1900) + "-"
                + String.valueOf(noticeStDate.getMonth()+1)+ "-"
                + String.valueOf(noticeStDate.getDate());
        textEDate=String.valueOf(noticeEDate.getYear()+1900) + "-"
                + String.valueOf(noticeEDate.getMonth()+1) + "-"
                + String.valueOf(noticeEDate.getDate());

            noticeName.setText(nameStr);
            noticeBody.setText(noticeBd);
            textViewStart.setText(textStDate);
            textViewEnd.setText(textEDate);

    }

    public UserNoticeListItem ppp()
    {
        UserNoticeListItem modifyData = new UserNoticeListItem();

        modifyData.setTitle(noticeName.getText().toString());
        modifyData.setBody(noticeBody.getText().toString());
        modifyData.setStartDate(noticeStDate);
        modifyData.setEndDate(noticeEDate);
        modifyData.setType(type);

        return modifyData;
    }

    @Override
    public void onClick(View view) {
        if (view instanceof TextView) {
            if (view.getId() == R.id.textViewStart) {
                final DatePickerDialog datePickerDialog = new DatePickerDialog(this.getContext());
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
            final DatePickerDialog datePickerDialog = new DatePickerDialog(this.getContext());
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
                this.cancel();
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
                new ClientDataBase("UPDATE `매장공지` SET `제목`=\""+noticeName.getText().toString()+"\",`내용`=\""+noticeBody.getText().toString()+"\",`공지 시작 날짜`=\""+textViewStart.getText().toString()+"\",`공지 마감 날짜`=\""+textViewEnd.getText().toString()+"\",`공지사항종류`="+noticeNum+" WHERE `제목`=\"" + nameStr + "\" and `내용`=\"" + noticeBd + "\"", 3, 0, this.getContext());
                this.dismiss();
                break;
            }
        }
    }
}