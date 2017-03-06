package com.example.kangjisung.likeroom.Setting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.Util.ColorTheme;

public class SettingApplication extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setTheme(ColorTheme.getTheme());
        setContentView(R.layout.setting_application);

        Button mButtonBack = (Button) findViewById(R.id.btn_back);
        mButtonBack.setOnClickListener(onButtonSettingClickListener);
    }

    Button.OnClickListener onButtonSettingClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View onClickView) {
            switch(onClickView.getId()){
                case R.id.btn_back:
                    finish();
                    break;
                default:
                    break;
            }
        }
    };
}