package com.example.kangjisung.likeroom.FragmentNotice;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.Util.LayoutManager;

import java.util.Calendar;

public class NoticeReadDialog extends Dialog
{
    private String mTitle;
    private String mContent;
    private Calendar startDate;
    private Calendar endDate;
    private int noticeType;

    private String listTypeText[] = {"알림", "신제품", "이벤트"};
    private int listTypeImage[] = {R.mipmap.icon_notice1_notification, R.mipmap.icon_notice2_event, R.mipmap.icon_notice3_newproduct};

    public NoticeReadDialog(Context context, String mTitle, String mContent, Calendar startDate, Calendar endDate, int noticeType) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mTitle = mTitle;
        this.mContent = mContent;
        this.startDate = startDate;
        this.endDate = endDate;
        this.noticeType = noticeType;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.notice_read_dialog_new);

        LayoutManager.setDialogTitle(findViewById(R.id.layout_title), true, false, "공지사항");
        findViewById(R.id.inc_btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        ((TextView) findViewById(R.id.txtNoticeTitle)).setText(mTitle);
        ((TextView) findViewById(R.id.txtNoticeBody)).setText(mContent);
        ((TextView) findViewById(R.id.tv_start_year)).setText(String.valueOf(startDate.get(Calendar.YEAR)));
        ((TextView) findViewById(R.id.tv_start_month)).setText(String.valueOf(startDate.get(Calendar.MONTH)+1));
        ((TextView) findViewById(R.id.tv_start_day)).setText((String.valueOf(startDate.get(Calendar.DAY_OF_MONTH)) + " - "));
        ((TextView) findViewById(R.id.tv_end_year)).setText(String.valueOf(endDate.get(Calendar.YEAR)));
        ((TextView) findViewById(R.id.tv_end_month)).setText(String.valueOf(endDate.get(Calendar.MONTH)+1));
        ((TextView) findViewById(R.id.tv_end_day)).setText(String.valueOf(endDate.get(Calendar.DAY_OF_MONTH)));
        ((TextView) findViewById(R.id.txtNoticeType)).setText(listTypeText[noticeType-1]);
        findViewById(R.id.imgNoticeType).setBackgroundResource(listTypeImage[noticeType-1]);
    }
}