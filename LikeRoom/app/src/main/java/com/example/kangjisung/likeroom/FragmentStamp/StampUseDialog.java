package com.example.kangjisung.likeroom.FragmentStamp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.kangjisung.likeroom.R;

public class StampUseDialog extends Dialog
{
    private Button mLeftButton;
    private Button mRightButton;
    Switch switchLockUnlockCoupon;

    private String mTitle;
    private String mContent;

    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;

    public StampUseDialog(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
    }

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

        View useStampDialogView = View.inflate(getContext(), R.layout.stamp_use_dialog, null);
        setContentView(useStampDialogView);
        //setContentView(R.layout.store_add_dialog);

        mLeftButton = (Button) useStampDialogView.findViewById(R.id.button_back);
        mRightButton = (Button) useStampDialogView.findViewById(R.id.button_use);
        switchLockUnlockCoupon = (Switch) useStampDialogView.findViewById(R.id.switchLockUnlockCoupon);

        mRightButton.setEnabled(false);//blocking user cant use stamp at first

        switchLockUnlockCoupon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.d(getContext().getString(R.string.app_name), "status: " + b);
                mRightButton.setEnabled(b);
            }
        });

        // 클릭 이벤트 셋팅
        mLeftButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View onClickView){
                cancel();
            }
        });
        mRightButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View onClickView){
                dismiss();
            }
        });
    }
}