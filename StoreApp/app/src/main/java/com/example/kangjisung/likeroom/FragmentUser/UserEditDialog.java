package com.example.kangjisung.likeroom.FragmentUser;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.kangjisung.likeroom.FragmentUser.ListView.UserMainListItem;
import com.example.kangjisung.likeroom.NetworkManager.HttpCommunicationProcess;
import com.example.kangjisung.likeroom.NetworkManager.NetworkModule;
import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase;

import java.util.Calendar;

public class UserEditDialog extends Dialog {
    private String mode;
    private UserMainListItem modifyItem;
    private int dismissMessage;


    UserEditDialog(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        mode = "ADD";
    }

    UserEditDialog(Context context, UserMainListItem modifyItem) {
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

        final EditText UserAddName = (EditText)findViewById(R.id.editText_name);
        final EditText UserAddPhone = (EditText)findViewById(R.id.editText_phone);
        final TextView UserAddBirth = (TextView)findViewById(R.id.textView_birth);


        if(mode == "ADD") {
            NetworkModule networkModule=new NetworkModule();
            String email="email";//이메일추가하기기
            networkModule.InsertNewCustomerInfo(UserAddName.getText().toString(),UserAddPhone.getText().toString(),email,UserAddBirth.getText().toString());
            mOKButton.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String query = "INSERT INTO 회원정보 (이름, 전화번호, 생년월일) VALUES('"
                            + UserAddName.getText().toString() + "', '"
                            + UserAddPhone.getText().toString() + "', '"
                            + UserAddBirth.getText().toString() + "');";

                    new ClientDataBase(query, 2, 0, getContext());
                    dismiss();
                }
            });
        }
        else{
            UserAddName.setText(modifyItem.getName());
            UserAddPhone.setText(modifyItem.getPhone());

            mOKButton.setOnClickListener(new Button.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dismissMessage = 1;
                    dismiss();
                }
            });
        }
        UserAddBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View onClickView) {
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
            }
        });
    }

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