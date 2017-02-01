package com.example.kangjisung.likeroom.FragmentUser;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase;

public class UserAddDialog extends Dialog
{
    UserAddDialog(Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
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

        Button mOKButton = (Button)findViewById(R.id.button_ok);
        Button mBackButton = (Button)findViewById(R.id.button_back);

        final EditText UserAddName = (EditText)findViewById(R.id.editText_name);
        final EditText UserAddPhone = (EditText)findViewById(R.id.editText_phone);
        final EditText UserAddBirth = (EditText)findViewById(R.id.textView_birth);

        mOKButton.setOnClickListener(new Button.OnClickListener(){
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
        mBackButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                cancel();
            }
        });
    }
}