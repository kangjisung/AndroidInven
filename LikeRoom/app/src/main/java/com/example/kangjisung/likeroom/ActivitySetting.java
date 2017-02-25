package com.example.kangjisung.likeroom;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.kangjisung.likeroom.Util.ColorTheme;

public class ActivitySetting extends AppCompatActivity
{

    LinearLayout settingLayout;
    Button settingFontSize,settingTheme,settingHelp,settingAsk,settingPro;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setTheme(ColorTheme.getTheme(ColorTheme.basicTheme));
        setContentView(R.layout.activity_setting);
        settingLayout = (LinearLayout)findViewById(R.id.layout_setting);
        settingFontSize = (Button)findViewById(R.id.buttonSettingFontSize);
        settingTheme = (Button)findViewById(R.id.buttonSettingTheme);
        settingHelp = (Button)findViewById(R.id.buttonSettingHelp);
        settingAsk = (Button)findViewById(R.id.buttonSettingAsk);
        settingPro = (Button)findViewById(R.id.buttonSettingPro);

        //글씨크기변경-> 안함
        settingFontSize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar.make(v, "기능 준비중입니다", 20000).setAction("확인", new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v)
                    {
                        //할거
                    }
                }).show();

            }
        });

        //테마-> 2월4일 구현 끝. 추후에 어플이 자동으로 재시작되도록 바꾸면 됨
        settingTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence colors[] = new CharSequence[]{"simple", "strawberry", "ocean"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySetting.this);
                builder.setTitle("테마변경");
                builder.setItems(colors, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which) {
                            case 0 :
                                ColorTheme.basicTheme=1;
                                ColorTheme.getTheme(ColorTheme.basicTheme);
/*
                                Intent mStartActivity = new Intent(ActivitySetting.this, MainActivity.class);
                                int mPendingIntentId = 123456;
                                PendingIntent mPendingIntent = PendingIntent.getActivity(ActivitySetting.this, mPendingIntentId,    mStartActivity, PendingIntent.FLAG_CANCEL_CURRENT);
                                AlarmManager mgr = (AlarmManager)ActivitySetting.this.getSystemService(Context.ALARM_SERVICE);
                                mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 100, mPendingIntent);
                                System.exit(0);
*/
                                Toast.makeText(ActivitySetting.this,"simple - 어플을 재시작 해주세요",Toast.LENGTH_LONG).show();
                                break;
                            case 1:
                                ColorTheme.basicTheme=2;
                                ColorTheme.getTheme(ColorTheme.basicTheme);
                                Toast.makeText(ActivitySetting.this,"strawberry - 어플을 재시작 해주세요",Toast.LENGTH_LONG).show();
                                break;
                            case 2:
                                ColorTheme.basicTheme=3;
                                ColorTheme.getTheme(ColorTheme.basicTheme);
                                Toast.makeText(ActivitySetting.this,"ocean - 어플을 재시작 해주세요",Toast.LENGTH_LONG).show();
                                break;
                        }
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
            });

        //도움말
        final HelpDialog helpDialog = new HelpDialog(ActivitySetting.this);

        settingHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpDialog.show();
            }
        });


        //문의하기-> 디자인을 새로 만들어야 할 것 같은데 아직 디자인 구현 안된상태.
        settingAsk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CharSequence ask[] = new CharSequence[]{"문의하기", "문의확인"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ActivitySetting.this);
                builder.setItems(ask, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch(which) {
                            case 0 :
                                Snackbar.make(settingAsk, "기능 준비중입니다", 20000).setAction("확인", new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        //할거
                                    }
                                }).show();
                                break;

                            case 1:
                                Snackbar.make(settingAsk, "기능 준비중입니다", 20000).setAction("확인", new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {
                                        //할거
                                    }
                                }).show();
                                break;
                        }
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });


        //만든이. 2월4일 구현완료 -> 추후에 들어가는 내용만! 바꾸면 된다.

        final ProDialog proDialog = new ProDialog(ActivitySetting.this);

        settingPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proDialog.show();
            }
        });

    }

}