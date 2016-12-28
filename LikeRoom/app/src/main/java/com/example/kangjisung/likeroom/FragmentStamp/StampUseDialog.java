package com.example.kangjisung.likeroom.FragmentStamp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.kangjisung.likeroom.R;

public class StampUseDialog extends Dialog
{
    private Button mLeftButton;
    private Button mRightButton;

    private String mTitle;
    private String mContent;

    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;

    public StampUseDialog(Context context, String title, View.OnClickListener singleListener, View.OnClickListener useListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mTitle = title;
        this.mLeftClickListener = singleListener;
        this.mRightClickListener= useListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        View addNewStoreDialogView = View.inflate(getContext(), R.layout.stamp_use_dialog, null);
        setContentView(addNewStoreDialogView);
        //setContentView(R.layout.store_add_dialog);

        mLeftButton = (Button) addNewStoreDialogView.findViewById(R.id.button_back);
        mRightButton = (Button) addNewStoreDialogView.findViewById(R.id.button_use);

        // 클릭 이벤트 셋팅
        mLeftButton.setOnClickListener(mLeftClickListener);
        mRightButton.setOnClickListener(mRightClickListener);
    }
}
