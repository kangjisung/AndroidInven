package com.example.kangjisung.likeroom;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
                CharSequence colors[] = new CharSequence[] {"1","2","3","4"};
            AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySetting.this);
                builder.setTitle("1");
                builder.setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();



            }

        });




        /*
        CharSequence colors[] = new CharSequence[] {"red", "green", "blue", "black"};

AlertDialog.Builder builder = new AlertDialog.Builder(this);
builder.setTitle("Pick a color");
builder.setItems(colors, new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
        // the user clicked on colors[which]
    }
});
builder.show();
         */
    }

}