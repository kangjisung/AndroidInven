package com.teamdk.android.bakery.fragments.user;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.teamdk.android.bakery.objectmanager.NoticeListItem;
import com.teamdk.android.bakery.R;
<<<<<<< HEAD:StoreApp/app/src/main/java/com/teamdk/android/bakery/fragments/user/UserNoticeEditDialog.java
import com.teamdk.android.bakery.utility.NetworkManager.NetworkModule;
=======
>>>>>>> refs/remotes/origin/store-app-byeongmun:StoreApp/app/src/main/java/com/teamdk/android/bakery/fragments/user/UserNoticeEditDialog.java
import com.teamdk.android.bakery.utility.SQLiteDatabaseControl.ClientDataBase;
import com.teamdk.android.bakery.utility.ColorTheme;
import com.teamdk.android.bakery.utility.LayoutManager;
import com.teamdk.android.bakery.utility.SingleToast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static com.teamdk.android.bakery.MainActivity.PriNum;
import static com.teamdk.android.bakery.utility.SQLiteDatabaseControl.ClientDataBase.DBstring;

public class UserNoticeEditDialog extends Dialog
{
    private TextView mTextViewStart;
    private TextView mTextViewEnd;
    private EditText mEditTextTitle;
    private TextView mEditTextBody;
    private AppCompatButton[] mButtonType;
    private int selectedType;
    private String[] listType = {"알림", "신제품", "이벤트"};

    private NoticeListItem userNoticeItemBeforeModify;
    private String query;
    private String mode;

    private boolean isOpened = false;

    public UserNoticeEditDialog(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        userNoticeItemBeforeModify = new NoticeListItem(-1, "", "", new Date(), new Date(), new Date(), 0, 0, 0);
        mode = "ADD";
    }

    public UserNoticeEditDialog(NoticeListItem userNoticeItem, Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        userNoticeItemBeforeModify = userNoticeItem;
        mode = "MODIFY";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.user_notice_edit_dialog);

        mTextViewStart = (TextView) findViewById(R.id.tv_start);
        mTextViewEnd = (TextView) findViewById(R.id.tv_end);
        mEditTextTitle = (EditText) findViewById(R.id.tv_title);
        mEditTextBody = (TextView) findViewById(R.id.tv_body);
        mEditTextBody.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context contextThemeWrapper = new ContextThemeWrapper(getContext(), ColorTheme.getTheme());
                LayoutInflater localInflater = getLayoutInflater().cloneInContext(contextThemeWrapper);
                View view = localInflater.inflate(R.layout.alert_multiline_edit, null);
                final EditText editTextInput = (EditText)view.findViewById(R.id.editText);
                editTextInput.setText(mEditTextBody.getText());
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(view);
                builder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mEditTextBody.setText(editTextInput.getText());
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
                dialog.show();
            }
        });
        mEditTextBody.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                findViewById(R.id.tv_body_show).setVisibility((s.length() == 0) ? (View.VISIBLE) : (View.INVISIBLE));
            }
        });

        mEditTextTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                findViewById(R.id.tv_title_show).setVisibility((s.length() == 0)?(View.VISIBLE):(View.INVISIBLE));
            }
        });

        mButtonType = new AppCompatButton[3];
        mButtonType[0] = (AppCompatButton) findViewById(R.id.button_type1);
        mButtonType[1] = (AppCompatButton) findViewById(R.id.button_type2);
        mButtonType[2] = (AppCompatButton) findViewById(R.id.button_type3);

        mButtonType[0].setOnClickListener(onButtonTypeClickListener);
        mButtonType[1].setOnClickListener(onButtonTypeClickListener);
        mButtonType[2].setOnClickListener(onButtonTypeClickListener);

        mEditTextTitle.setText(userNoticeItemBeforeModify.getTitle());
        mEditTextBody.setText(userNoticeItemBeforeModify.getBody());
        mTextViewStart.setText((new SimpleDateFormat("yyyy년 M월 d일", Locale.KOREA)).format(userNoticeItemBeforeModify.getStartDate().getTime()));
        mTextViewEnd.setText((new SimpleDateFormat("yyyy년 M월 d일", Locale.KOREA)).format(userNoticeItemBeforeModify.getEndDate().getTime()));
        mTextViewStart.setOnClickListener(onTextViewDateClickListener);
        mTextViewEnd.setOnClickListener(onTextViewDateClickListener);
        if(mode == "ADD"){
            LayoutManager.setDialogTitle(findViewById(R.id.layout_title), true, true, "공지사항 추가");
        }
        else{
            LayoutManager.setDialogTitle(findViewById(R.id.layout_title), true, true, "공지사항 수정");
        }
        findViewById(R.id.inc_btn_back).setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View onClickView){
                dismiss();
            }
        });
        findViewById(R.id.inc_btn_ok).setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View onClickView){
                try{
                    Date startDate = (new SimpleDateFormat("y년 M월 d일", Locale.KOREA)).parse(mTextViewStart.getText().toString());
                    Date endDate = (new SimpleDateFormat("y년 M월 d일", Locale.KOREA)).parse(mTextViewEnd.getText().toString());
                    String startDateString = (new SimpleDateFormat("y-M-d", Locale.KOREA)).format(startDate);
                    String endDateString = (new SimpleDateFormat("y-M-d", Locale.KOREA)).format(endDate);
                    String makeDateString = (new SimpleDateFormat("y-M-d HH:mm:ss", Locale.KOREA)).format(userNoticeItemBeforeModify.getMakeDate());

                    if(endDate.getTime() < startDate.getTime()){
                        SingleToast.show(getContext(), "시작 날짜가 종료 날짜보다 이후에 있습니다.", Toast.LENGTH_LONG);
                        return;
                    }
                    if(mEditTextTitle.length() == 0){
                        SingleToast.show(getContext(), "제목을 입력하셔야 합니다.", Toast.LENGTH_LONG);
                        return;
                    }
                    if(mEditTextBody.length() == 0){
                        SingleToast.show(getContext(), "내용을 입력하셔야 합니다.", Toast.LENGTH_LONG);
                        return;
                    }

                    if(mode.equals("ADD")) {
                        query = String.format("INSERT INTO `매장공지`" +
                                " (`제목`, `내용`, `공지시작날짜`, `공지마감날짜`, `작성시간`, `공지사항종류`, `삭제`)" +
                                " VALUES (\"%s\", \"%s\", \"%s\", \"%s\", \"%s\", %d, %d);",
                                mEditTextTitle.getText().toString(),
                                mEditTextBody.getText().toString(),
                                startDateString,
                                endDateString,
                                makeDateString,
                                selectedType,
                                0);
                        new ClientDataBase(query, 2, 0, getContext());
                        new ClientDataBase("select `공지번호` from `매장공지` where `제목`=\""+mEditTextTitle.getText().toString()+"\"",1,1,getContext());
                        NetworkModule networkModule=new NetworkModule();//서버에 공지 추가
                        networkModule.InsertNewStoreNoticeInfo(Integer.parseInt(PriNum),Integer.parseInt(DBstring[0]),mEditTextTitle.getText().toString(),mEditTextBody.getText().toString(),startDateString,endDateString,makeDateString);
                    }
                    else if(mode.equals("MODIFY")) {
                        query = String.format("UPDATE `매장공지`" +
                                " SET `제목` = \"%s\", `내용` = \"%s\", `공지시작날짜` = \"%s\", `공지마감날짜` = \"%s\", `공지사항종류` = %d WHERE `코드` = %d;" + "",
                                mEditTextTitle.getText().toString(),
                                mEditTextBody.getText().toString(),
                                startDateString,
                                endDateString,
                                selectedType,
                                userNoticeItemBeforeModify.getNum());
                        new ClientDataBase(query, 3, 0, getContext());
                        NetworkModule networkModule=new NetworkModule();//서버에 공지 업데이트
                        networkModule.UpdateStoreNoticeInfo(Integer.parseInt(PriNum),userNoticeItemBeforeModify.getNum(),mEditTextTitle.getText().toString(),mEditTextBody.getText().toString(),startDateString,endDateString);
                    }
                }
                catch(Exception ex){
                    ex.printStackTrace();
                }
                dismiss();
            }
        });
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        final LinearLayout rootView = (LinearLayout) findViewById(R.id.layout_type);
        rootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                rootView.getViewTreeObserver().removeGlobalOnLayoutListener(this);

                selectedType = userNoticeItemBeforeModify.getType();
                setButtonType(selectedType, selectedType);
            }
        });
    }

    private Button.OnClickListener onButtonTypeClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View onClickView) {
            switch(onClickView.getId()){
                default:
                case R.id.button_type1:
                    setButtonType(selectedType, 0);
                    break;
                case R.id.button_type2:
                    setButtonType(selectedType, 1);
                    break;
                case R.id.button_type3:
                    setButtonType(selectedType, 2);
                    break;
            }
        }
    };

    private void setButtonType(int start, int end)
    {
        AppCompatImageView imageViewDot = (AppCompatImageView) findViewById(R.id.imageView_dot);
        LinearLayout layoutParent = (LinearLayout) findViewById(R.id.layout_type);

        Rect sViewRect = new Rect();
        Rect eViewRect = new Rect();
        Rect rViewRect = new Rect();
        mButtonType[start].getGlobalVisibleRect(sViewRect);
        mButtonType[end].getGlobalVisibleRect(eViewRect);
        layoutParent.getGlobalVisibleRect(rViewRect);

        float xStart = sViewRect.exactCenterX() - rViewRect.left - (imageViewDot.getLayoutParams().height / (float)2);
        float xEnd = eViewRect.exactCenterX() - rViewRect.left - (imageViewDot.getLayoutParams().height / (float)2);
        TranslateAnimation moveLefttoRight = new TranslateAnimation(xStart, xEnd, 0, 0);
        moveLefttoRight.setDuration(500);
        moveLefttoRight.setFillAfter(true);
        imageViewDot.startAnimation(moveLefttoRight);

        mButtonType[start].getBackground().setColorFilter(ContextCompat.getColor(getContext(), R.color.gray200), PorterDuff.Mode.SRC_IN);
        mButtonType[end].getBackground().setColorFilter(ColorTheme.getThemeColorRGB(getContext(), R.attr.theme_color_D3), PorterDuff.Mode.SRC_IN);

        TextView mTextViewType = (TextView) findViewById(R.id.textView_type);
        mTextViewType.setText(listType[end]);

        selectedType = end;
    }

    private Calendar nowDate = Calendar.getInstance();
    private Calendar selectedDate = Calendar.getInstance();

    private View.OnClickListener onTextViewDateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View onClickView) {
            try{
                final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("y년 M월 d일", Locale.KOREA);
                final TextView mTextViewDate;
                switch(onClickView.getId()){
                    default:
                    case R.id.tv_start:
                        nowDate.setTime(simpleDateFormat.parse(mTextViewStart.getText().toString()));
                        mTextViewDate = (TextView)findViewById(R.id.tv_start);
                        break;
                    case R.id.tv_end:
                        nowDate.setTime(simpleDateFormat.parse(mTextViewEnd.getText().toString()));
                        mTextViewDate = (TextView)findViewById(R.id.tv_end);
                        break;
                }

                Context contextThemeWrapper = new ContextThemeWrapper(getContext(), ColorTheme.getTheme());
                LayoutInflater localInflater = getLayoutInflater().cloneInContext(contextThemeWrapper);
                View view = localInflater.inflate(R.layout.alert_calendar, null);
                DatePicker mDatePicker = (DatePicker) view.findViewById(R.id.calendarView);
                selectedDate = nowDate;

                int year = selectedDate.get(Calendar.YEAR);
                int month = selectedDate.get(Calendar.MONTH);
                int day = selectedDate.get(Calendar.DAY_OF_MONTH);

                mDatePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        selectedDate = new GregorianCalendar(year, monthOfYear, dayOfMonth);
                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(view);
                builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nowDate = selectedDate;
                        mTextViewDate.setText(simpleDateFormat.format(nowDate.getTime()));
                        dialog.dismiss();
                    }
                });
                builder.setNegativeButton("아니오", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    };
}