package com.example.kangjisung.likeroom.FragmentNotice;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.kangjisung.likeroom.R;

public class NoticeReadDialog extends Dialog
{
    private Button mLeftButton;
    private String mTitle;
    private String mContent;

    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;

    public NoticeReadDialog(Context context, String title, View.OnClickListener singleListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mTitle = title;
        this.mLeftClickListener = singleListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        View addNewStoreDialogView = View.inflate(getContext(), R.layout.notice_read_dialog, null);
        setContentView(addNewStoreDialogView);
        //setContentView(R.layout.store_add_dialog);

        mLeftButton = (Button) addNewStoreDialogView.findViewById(R.id.button_back);

        // 클릭 이벤트 셋팅
        mLeftButton.setOnClickListener(mLeftClickListener);
    }
}
