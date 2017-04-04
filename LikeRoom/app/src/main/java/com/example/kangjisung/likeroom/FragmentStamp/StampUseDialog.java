package com.example.kangjisung.likeroom.FragmentStamp;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.Util.ColorTheme;

import java.util.Arrays;

import static com.example.kangjisung.likeroom.DefineManager.shopIdSavedPoint;
import static com.example.kangjisung.likeroom.DefineManager.synchronizedLocalAndServerDatabase;

public class StampUseDialog extends Dialog
{
    private Button mLeftButton;
    private Button mRightButton;
    Switch switchLockUnlockCoupon;

    private String mTitle;
    private String mContent;

    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;

    int mileageUseage;
    String[] selectedShopInfoData;
    private int dismissMessage = 0;

    public StampUseDialog(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
    }

    public StampUseDialog(Context context, int mileageUseage, String[] selectedShopInfoData) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        Log.d(context.getString(R.string.app_name), "Mileage About to use: " + mileageUseage);
        this.mileageUseage = mileageUseage;
        this.selectedShopInfoData = selectedShopInfoData;
        Log.d("mileage use info", Arrays.toString(selectedShopInfoData));
    }

    public StampUseDialog(Context context, String title, View.OnClickListener singleListener, View.OnClickListener useListener, String[] selectedShopInfoData) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mTitle = title;
        this.mLeftClickListener = singleListener;
        this.mRightClickListener= useListener;
        this.selectedShopInfoData = selectedShopInfoData;
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

        View layoutNormal = findViewById(R.id.layout_normal);
        layoutNormal.setVisibility(View.GONE);
        findViewById(R.id.view_off).setVisibility(View.INVISIBLE);
        ((TextView) findViewById(R.id.view_text)).setText(String.valueOf(mileageUseage));

        mRightButton.setEnabled(false);//blocking user cant use stamp at first

        switchLockUnlockCoupon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if(switchLockUnlockCoupon.isChecked()==true) {
                mRightButton.setBackgroundColor(ColorTheme.getThemeColorRGB(getContext(), R.attr.theme_color_D3));
            }
                else {
                mRightButton.setBackgroundColor(Color.GRAY);
            }

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
                int uniqueId = 0;
                uniqueId = synchronizedLocalAndServerDatabase.GetStoreUniqueId(Integer.parseInt(selectedShopInfoData[shopIdSavedPoint]));
                synchronizedLocalAndServerDatabase.UseMileageFromTargetStore(uniqueId, -mileageUseage);
                dismissMessage = 1;
                dismiss();
            }
        });
    }

    public int getDismissMessage()
    {
        return dismissMessage;
    }
}