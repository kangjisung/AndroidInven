package com.teamdk.android.bakery.setting;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.teamdk.android.bakery.R;
import com.teamdk.android.bakery.utility.ColorTheme;
import com.teamdk.android.bakery.utility.LayoutManager;
import com.teamdk.android.bakery.utility.SQLiteDatabaseControl.ClientDataBase;
import com.teamdk.android.bakery.utility.Utility;

import static com.teamdk.android.bakery.utility.SQLiteDatabaseControl.ClientDataBase.DBstring;

public class SettingStoreModify extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setTheme(ColorTheme.getTheme());
        setContentView(R.layout.setting_store_modify);

        EditText mEditTextName = (EditText) findViewById(R.id.et_name);
        EditText mEditTextAddress = (EditText) findViewById(R.id.et_address);
        EditText mEditTextPhone = (EditText) findViewById(R.id.et_phone);

        String query = "SELECT `매장번호`, `이름`, `주소`, `전화번호` FROM `매장`;";
        new ClientDataBase(query, 1, 4, getApplicationContext());

        if (DBstring[0] != null) {
            mEditTextName.setText(DBstring[1]);
            mEditTextAddress.setText(DBstring[2]);
            mEditTextPhone.setText(DBstring[3]);
        }
        else if(DBstring[0]==null) {
            Toast.makeText(getApplicationContext(), "매장 정보를 불러올 수 없습니다.", Toast.LENGTH_LONG).show();
        }

        Button mButtonOk = (Button) findViewById(R.id.btn_ok);
        mButtonOk.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View onClickView) {
                // TODO : 수정하기 버튼 동작 삽입
            }
        });

        Button mButtonCancel = (Button) findViewById(R.id.btn_cancel);
        mButtonCancel.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View onClickView) {
                // TODO : 완료 버튼 동작 삽입
                finish();
            }
        });
    }
}