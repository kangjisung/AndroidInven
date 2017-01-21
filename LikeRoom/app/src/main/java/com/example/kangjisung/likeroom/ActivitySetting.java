package com.example.kangjisung.likeroom;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.kangjisung.likeroom.Util.ColorTheme;

public class ActivitySetting extends AppCompatActivity
{

    LinearLayout settingLayout;
    Button settingFontSize;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setTheme(ColorTheme.getTheme());
        setContentView(R.layout.activity_setting);
        settingLayout = (LinearLayout)findViewById(R.id.layout_setting);
        settingFontSize = (Button)findViewById(R.id.buttonSettingFontSize);

        settingFontSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}