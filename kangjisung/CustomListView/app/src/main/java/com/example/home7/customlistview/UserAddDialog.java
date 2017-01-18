package com.example.home7.customlistview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.home7.customlistview.SQLiteDatabaseControl.ClientDataBase;
import com.example.locallistview.R;

import java.util.Date;

/**
 * Created by kangjisung on 2017-01-16.
 */

public class UserAddDialog extends Dialog {
    public UserAddDialog(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
    }

    public UserAddDialog(String name, String noBd, Date noStDate, Date noEDate, int type1,Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        String nameStr = name;
        String noticeBody = noBd;
        Date noticeStDate = noStDate;
        Date noticeEDate = noEDate;
        int type = type1;

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.notice);
    }
}