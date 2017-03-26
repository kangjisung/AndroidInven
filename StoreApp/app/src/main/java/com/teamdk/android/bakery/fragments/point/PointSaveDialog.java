package com.teamdk.android.bakery.fragments.point;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.teamdk.android.bakery.MainActivity;
import com.teamdk.android.bakery.objectmanager.MemberListItem;
import com.teamdk.android.bakery.R;
import com.teamdk.android.bakery.utility.NetworkManager.NetworkModule;
import com.teamdk.android.bakery.utility.SQLiteDatabaseControl.ClientDataBase;
import com.teamdk.android.bakery.utility.LayoutManager;
import com.teamdk.android.bakery.utility.Utility;

import static android.R.attr.name;
import static com.teamdk.android.bakery.utility.SQLiteDatabaseControl.ClientDataBase.DBstring;

public class PointSaveDialog extends Dialog {
    private TextView mTextViewValue;
    private TextView mTextViewGuide;
    private int layoutInputBoxSize = -1;
    private int maxLength = 8;
    private double pointRate = 0.05;
    private int dismissMessage = 0;

    private MemberListItem modifyItem;

    private String strValue = "";
    private int value = 0;
    int nowPoint = 0;

    public PointSaveDialog(Context context, MemberListItem object) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        this.modifyItem = object;
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

        LayoutManager.setDialogTitle(findViewById(R.id.layout_title), true, false, "포인트 적립");
        findViewById(R.id.inc_btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        final TextView mTextViewName = (TextView) findViewById(R.id.tv_name);
        TextView mTextViewPhone = (TextView) findViewById(R.id.tv_phone);
        mTextViewGuide = (TextView) findViewById(R.id.tv_guide);
        mTextViewValue = (TextView) findViewById(R.id.tv_value);
        mTextViewName.setText(modifyItem.getName());
        mTextViewPhone.setText(Utility.convertPhoneNumber(modifyItem.getPhone()));

        ((Button) findViewById(R.id.btn_1)).setOnClickListener(onButtonNumberClickListener);
        ((Button) findViewById(R.id.btn_2)).setOnClickListener(onButtonNumberClickListener);
        ((Button) findViewById(R.id.btn_3)).setOnClickListener(onButtonNumberClickListener);
        ((Button) findViewById(R.id.btn_4)).setOnClickListener(onButtonNumberClickListener);
        ((Button) findViewById(R.id.btn_5)).setOnClickListener(onButtonNumberClickListener);
        ((Button) findViewById(R.id.btn_6)).setOnClickListener(onButtonNumberClickListener);
        ((Button) findViewById(R.id.btn_7)).setOnClickListener(onButtonNumberClickListener);
        ((Button) findViewById(R.id.btn_8)).setOnClickListener(onButtonNumberClickListener);
        ((Button) findViewById(R.id.btn_9)).setOnClickListener(onButtonNumberClickListener);
        ((Button) findViewById(R.id.btn_0)).setOnClickListener(onButtonNumberClickListener);
        ((Button) findViewById(R.id.btn_cor)).setOnClickListener(onButtonNumberClickListener);
        ((Button) findViewById(R.id.btn_del)).setOnClickListener(onButtonNumberClickListener);
        ((Button) findViewById(R.id.button_ok)).setOnClickListener(new Button.OnClickListener() {

            int point;//최종 포인트

            public void onClick(View onClickView) {
                point = nowPoint + (int)(value * pointRate);
                new ClientDataBase("SELECT `고유회원등록번호` FROM `포인트` WHERE `고유회원등록번호` = " + modifyItem.getNum() + ";", 1, 1, getContext()); //포인트 있는지 검색
                if(DBstring[0]==null){//////////////////없다면 insert
                    new ClientDataBase("INSERT INTO `포인트` (`고유회원등록번호`,`포인트`,`포인트갱신날짜`) VALUES (" + modifyItem.getNum() + ", " + point + ", " + "(select date('now')));", 2, 0, getContext());
                }
                else {////////////////있으면 계산해서 update
                    new ClientDataBase("UPDATE `포인트` SET `포인트`=" + point + ", `포인트갱신날짜`=(select date('now')) WHERE `고유회원등록번호` = " + modifyItem.getNum() + ";", 3, 0, getContext());
                }
                dismissMessage = point;
                // TODO : 완료 버튼 동작 삽입
                NetworkModule networkModule=new NetworkModule();
                networkModule.InsertMileageLog(String.valueOf(UserPriNum), String.valueOf((int)(value*pointRate)));//서버 마일리지 업데이트 (유저코드,올라간 포인트양)
                dismiss();
            }
        });

        setPoint();
    }

    public Button.OnClickListener onButtonNumberClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View onClickView) {
            if(strValue.length() < maxLength || onClickView.getId() == R.id.btn_del || onClickView.getId() == R.id.btn_cor) {
                switch (onClickView.getId()) {
                    case R.id.btn_1:
                        strValue = strValue + "1";
                        break;
                    case R.id.btn_2:
                        strValue = strValue + "2";
                        break;
                    case R.id.btn_3:
                        strValue = strValue + "3";
                        break;
                    case R.id.btn_4:
                        strValue = strValue + "4";
                        break;
                    case R.id.btn_5:
                        strValue = strValue + "5";
                        break;
                    case R.id.btn_6:
                        strValue = strValue + "6";
                        break;
                    case R.id.btn_7:
                        strValue = strValue + "7";
                        break;
                    case R.id.btn_8:
                        strValue = strValue + "8";
                        break;
                    case R.id.btn_9:
                        strValue = strValue + "9";
                        break;
                    case R.id.btn_0:
                        if(strValue.length() > 0){
                            strValue = strValue + "0";
                        }
                        break;
                    case R.id.btn_cor:
                        strValue = "";
                        break;
                    case R.id.btn_del:
                        if (strValue.length() > 0) {
                            strValue = strValue.substring(0, strValue.length() - 1);
                        }
                        break;
                }
                if(strValue.length() > 0) {
                    value = Integer.parseInt(strValue);
                    mTextViewValue.setText(String.format("%,d", value));
                }
                else{
                    value = 0;
                    mTextViewValue.setText("0");
                }
            }
           setPoint();
        }
    };

    private void setPoint()
    {
        if(modifyItem.getPoint() != "" && modifyItem.getPoint() != null){
            nowPoint = Integer.parseInt(modifyItem.getPoint());
        }
        mTextViewGuide.setText(String.format("%,dP 적립 : %,d → %,d", (int)(value * pointRate), nowPoint, nowPoint + (int)(value * pointRate)));
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

    public int getDismissMessage()
    {
        return dismissMessage;
    }
}