package com.example.kangjisung.likeroom.FragmentPoint;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.kangjisung.likeroom.R;

public class PointSaveDialog extends Dialog {
    private String mTitle;
    private String mContent;

    private Button mBackButton;
    private Button mOKButton;
    private int layoutInputBoxSize = -1;

    private View.OnClickListener mBackClickListener;
    private View.OnClickListener mOKClickListener;
    private View.OnClickListener mBackIdentifyClickListener;
    private View.OnClickListener mOKIdentifyClickListener;

    PointSaveDialog(Context context, String title, String content, View.OnClickListener backListener, View.OnClickListener okListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mTitle = title;
        this.mContent = content;
        this.mBackClickListener = backListener;
        this.mOKIdentifyClickListener = okListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.point_save_dialog);

        mBackButton = (Button)findViewById(R.id.button_back);
        mOKButton = (Button)findViewById(R.id.button_ok);
        final RelativeLayout layoutInputBox = (RelativeLayout)findViewById(R.id.layout_inputbox);

        mBackIdentifyClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playInputBoxAnimation(0, layoutInputBoxSize);

                mBackButton.setOnClickListener(mBackClickListener);
                mOKButton.setOnClickListener(mOKClickListener);
            }
        };
        mOKClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playInputBoxAnimation(layoutInputBoxSize, 0);

                mBackButton.setOnClickListener(mBackIdentifyClickListener);
                mOKButton.setOnClickListener(mOKIdentifyClickListener);
            }
        };
        mBackButton.setOnClickListener(mBackClickListener);
        mOKButton.setOnClickListener(mOKClickListener);
    }

    private void playInputBoxAnimation(int start, int end) {
        final RelativeLayout layoutInputBox = (RelativeLayout)findViewById(R.id.layout_inputbox);
        if(layoutInputBoxSize == -1){
            layoutInputBoxSize = start = layoutInputBox.getHeight();
        }

        ValueAnimator slideAnimator = ValueAnimator.ofInt(start, end).setDuration(200);
        slideAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer)animation.getAnimatedValue();
                layoutInputBox.getLayoutParams().height = value.intValue();
                layoutInputBox.requestLayout();
            }
        });

        AnimatorSet set = new AnimatorSet();
        set.play(slideAnimator);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.start();
    }
}