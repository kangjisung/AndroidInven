package com.example.kangjisung.likeroom.FragmentNotice;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.kangjisung.likeroom.R;

public class NoticeReadDialog extends Dialog
{
    private Button mLeftButton;
    private String mTitle, mContent, readableDate;

    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;

    int modeOfDialog;
    TextView txtNoticeTitle, txtNoticeBody, txtNoticeDate;

    public NoticeReadDialog(Context context, String title, View.OnClickListener singleListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mTitle = title;
        this.mLeftClickListener = singleListener;
    }

    public NoticeReadDialog(Context context, String mTitle, String noticeBody, String readableDate) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mTitle = mTitle;
        this.mContent = noticeBody;
        this.readableDate = readableDate;
        modeOfDialog = 1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        View noticeListDialogView = View.inflate(getContext(), R.layout.notice_read_dialog, null);
        setContentView(noticeListDialogView);
        //setContentView(R.layout.store_add_dialog);

        txtNoticeTitle = (TextView) noticeListDialogView.findViewById(R.id.txtNoticeTitle);
        txtNoticeBody = (TextView) noticeListDialogView.findViewById(R.id.txtNoticeBody);
        txtNoticeDate = (TextView) noticeListDialogView.findViewById(R.id.txtNoticeDate);
        Log.d(getContext().getString(R.string.app_name), "title: " + txtNoticeTitle + " body: " + txtNoticeBody + " date: " + txtNoticeDate);
        txtNoticeTitle.setText(mTitle);
        txtNoticeBody.setText(mContent);
        txtNoticeDate.setText(readableDate);

        mLeftButton = (Button) noticeListDialogView.findViewById(R.id.button_back);

        Log.d(getContext().getString(R.string.app_name), "lbtn: " + mLeftButton);

        mLeftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, getContext().getString(R.string.featureLoadFail), Snackbar.LENGTH_SHORT).show();
                dismiss();
            }
        });

        // 클릭 이벤트 셋팅
        if(modeOfDialog == 0)
            mLeftButton.setOnClickListener(mLeftClickListener);
    }
}
