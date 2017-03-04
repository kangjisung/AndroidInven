package com.example.kangjisung.likeroom.FragmentUser;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kangjisung.likeroom.MemberListItem;
import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase;

import com.example.kangjisung.likeroom.Util.ColorTheme;
import com.example.kangjisung.likeroom.Util.SingleToast;
import com.example.kangjisung.likeroom.Util.Utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class UserEditDialog extends Dialog {
    private String mode;
    private MemberListItem modifyItem;
    private int dismissMessage;
    private String selectedDate;
    private String modifyDate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("y-M-d", Locale.KOREA);

    private EditText UserAddName;
    private EditText UserAddPhone;
    private TextView UserAddBirth;

    UserEditDialog(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        mode = "ADD";
    }

    UserEditDialog(Context context, MemberListItem modifyItem) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        mode = "MODIFY";
        this.modifyItem = modifyItem;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.user_edit_dialog);
        initializeDialogTitleBar();

        Button mOKButton = (Button)findViewById(R.id.button_ok);
        Button mBackButton = (Button)findViewById(R.id.button_dialog_back);

        UserAddName = (EditText)findViewById(R.id.editText_name);
        UserAddPhone = (EditText)findViewById(R.id.editText_phone);
        UserAddBirth = (TextView)findViewById(R.id.textView_birth);

        mOKButton.setOnClickListener(onClickListenerButtonOk);

        if(mode == "ADD") {
            //NetworkModule networkModule=new NetworkModule();
            String email="email";//이메일추가하기기
            //networkModule.InsertNewCustomerInfo(UserAddName.getText().toString(),UserAddPhone.getText().toString(),email,UserAddBirth.getText().toString());
            selectedDate = dateFormat.format(new Date());
            UserAddBirth.setText(selectedDate);
        }
        else{
            selectedDate = dateFormat.format(modifyItem.getBirth());
            UserAddName.setText(modifyItem.getName());
            UserAddPhone.setText(modifyItem.getPhone());
            UserAddBirth.setText(selectedDate);
        }
        UserAddBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View onClickView) {
                Context contextThemeWrapper = new ContextThemeWrapper(getContext(), android.R.style.Theme_Holo_Light);
                LayoutInflater localInflater = getLayoutInflater().cloneInContext(contextThemeWrapper);
                View view = localInflater.inflate(R.layout.alert_datepicker, null);

                DatePicker mDatePicker = (DatePicker) view.findViewById(R.id.datePicker);

                try{
                    int year = dateFormat.parse(selectedDate).getYear()+1900;
                    int month = dateFormat.parse(selectedDate).getMonth();
                    int day = dateFormat.parse(selectedDate).getDate();

                    mDatePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
                        @Override
                        public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            modifyDate = String.valueOf(year) + "-" + String.valueOf(monthOfYear+1) + "-" + String.valueOf(dayOfMonth);
                        }
                    });

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setView(view);
                    builder.setCustomTitle(Utility.getAlertDialogTitle("생일 설정", getContext()));
                    builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            selectedDate = modifyDate;
                            UserAddBirth.setText(selectedDate);
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
                catch (Exception e){
                    e.printStackTrace();
                }
                /*
                Calendar c = Calendar.getInstance();
                int cyear = c.get(Calendar.YEAR);
                int cmonth = c.get(Calendar.MONTH);
                int cday = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String date_selected = String.valueOf(year) + "-" + String.valueOf(monthOfYear+1) + "-" + String.valueOf(dayOfMonth);
                        UserAddBirth.setText(date_selected);
                    }
                };
                DatePickerDialog alert = new DatePickerDialog(getContext(), android.R.style.Theme_Holo_Light_Dialog, mDateSetListener, cyear, cmonth, cday);
                alert.show();
                */
            }
        });
    }

    public Button.OnClickListener onClickListenerButtonOk = new Button.OnClickListener() {
        @Override
        public void onClick(View onClickView) {
            if(UserAddName.length() == 0){
                SingleToast.show(getContext(), "이름을 입력하셔야 합니다.", Toast.LENGTH_LONG);
                return;
            }
            if(mode == "ADD") {
                String query = "INSERT INTO `회원정보` (`이름`, `전화번호`, `생년월일`) VALUES('"
                        + UserAddName.getText().toString() + "', '"
                        + UserAddPhone.getText().toString() + "', '"
                        + selectedDate + "');";
                new ClientDataBase(query, 2, 0, getContext());
                dismiss();
            }
            else{
                String query = String.format("UPDATE `회원정보` SET `이름` = \"%s\", `전화번호` = \"%s\", `생년월일` = \"%s\" WHERE `고유회원등록번호` = %d;" + "" + "",
                        UserAddName.getText().toString(),
                        UserAddPhone.getText().toString(),
                        UserAddBirth.getText().toString(),
                        modifyItem.getNum());
                new ClientDataBase(query, 2, 0, getContext());
                dismissMessage = 1;
                dismiss();
            }
        }
    };

    private void initializeDialogTitleBar()
    {
        TextView mTextViewTitle = (TextView)findViewById(R.id.textView_title);
        Button mBackButton = (Button)findViewById(R.id.button_dialog_back);
        Button mOKButton = (Button)findViewById(R.id.button_dialog_ok);
        mOKButton.setVisibility(View.GONE);

        if(mode == "ADD"){
            mTextViewTitle.setText("고객 추가");
        }
        else{
            mTextViewTitle.setText("고객 수정");
        }
        mBackButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View onClickView){
                dismissMessage = 0;
                dismiss();
            }
        });
    }

    public int getDismissMessage()
    {
        return dismissMessage;
    }
}