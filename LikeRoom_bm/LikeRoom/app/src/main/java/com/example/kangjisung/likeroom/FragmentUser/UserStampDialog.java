package com.example.kangjisung.likeroom.FragmentUser;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.kangjisung.likeroom.R;

public class UserStampDialog extends Dialog
{
    private TextView mTitleView;
    private TextView mContentView;
    private Button mBackButton;
    private Button mOKButton;
    private String mTitle;
    private String mContent;

    private View.OnClickListener mBackClickListener;
    private View.OnClickListener mOKClickListener;

    // 클릭버튼이 확인과 취소 두개일때 생성자 함수로 이벤트를 받는다
    public UserStampDialog(Context context, String title, String content, View.OnClickListener backListener, View.OnClickListener okListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mTitle = title;
        this.mContent = content;
        this.mBackClickListener = backListener;
        this.mOKClickListener = okListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.user_stamp_dialog);

        mBackButton = (Button)findViewById(R.id.button_back);
        mOKButton = (Button)findViewById(R.id.button_ok);

        // 클릭 이벤트 셋팅
        if (mBackClickListener != null && mOKClickListener != null) {
            mBackButton.setOnClickListener(mBackClickListener);
            mOKButton.setOnClickListener(mOKClickListener);
        }
        else if (mBackClickListener != null && mOKClickListener == null) {
        }
    }
}