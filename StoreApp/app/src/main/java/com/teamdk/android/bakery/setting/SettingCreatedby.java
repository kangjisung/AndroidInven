package com.teamdk.android.bakery.setting;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.teamdk.android.bakery.R;
import com.teamdk.android.bakery.utility.ColorTheme;
import com.teamdk.android.bakery.utility.LayoutManager;

public class SettingCreatedby extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setTheme(ColorTheme.getTheme());
        setContentView(R.layout.setting_createdby);

        findViewById(R.id.btn_homepage).setOnClickListener(onButtonSettingClickListener);

        LayoutManager.setActivityTitle(findViewById(R.id.layout_title), true, false, "만든이");
        findViewById(R.id.inc_btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }

    Button.OnClickListener onButtonSettingClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View onClickView) {
            switch(onClickView.getId()){
                case R.id.btn_homepage:
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://stories3.iptime.org:4200/"));
                    startActivity(browserIntent);
                    break;
                default:
                    break;
            }
        }
    };
}