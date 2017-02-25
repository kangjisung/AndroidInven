package com.example.kangjisung.likeroom;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
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
                Toast toast = Toast.makeText(getApplicationContext(),"기능 준비중입니다",Toast.LENGTH_LONG);
                toast.show();
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

        //도움말-> 팝업 창 하나만 띄울지, 새로운 창으로 바꿔서 많은 양을 담을지 고민중. 일단 팝업창 하나만 띄움
        //현재 문제되는 부분: 글씨 크기,색상을 변경하려고 하면 view를 띄워야 하는데
        //도움말을 여러번 누르게 되면 view가 여러번 뜨게 되므로 오류가 발생한다.

        final View helpview = this.getLayoutInflater().inflate(R.layout.setting_help_dialog,null);
        final TextView title = (TextView)helpview.findViewById(R.id.title);
        title.setTextSize(30);
        title.setText("타이틀");

       final TextView message = (TextView) helpview.findViewById(R.id.message);
        message.setTextSize(20);
        message.setText( "\n본 어플리케이션은 " +
                "서울대 '문일경' 교수의\n" +
                "'자유분포 뉴스벤더 모형'을 활용하여\n" +
                "최고 이익을 얻을 수 있는\n" +
                "재고관리기능과 고객관리 기능을 제공합니다\n");

        final AlertDialog.Builder dlg = new AlertDialog.Builder(ActivitySetting.this);
        dlg.setView(helpview);

        settingHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /* 일단 안씀 dlg.setPositiveButton("확인",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
*/

                AlertDialog dialog = dlg.create();
                dialog.show();


            }

        });

        settingHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //view closeing하고 다시 열기 전 이미 열려있던 뷰를 제거하지 않아 발생하던 버그를 픽스

        dlg.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                //Log.d("test","dsmiss");
                if(helpview != null) {
                    ViewGroup popUpViewGroup = (ViewGroup)helpview.getParent();
                    popUpViewGroup.removeView(helpview);
                }
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
                                Toast.makeText(ActivitySetting.this,"문의하기",Toast.LENGTH_LONG).show();
                                break;
                            case 1:
                                Toast.makeText(ActivitySetting.this,"문의확인",Toast.LENGTH_LONG).show();
                                break;
                        }
                        dialog.dismiss();
                    }
                });
                builder.show();
            }
        });


        //만든이. 2월4일 구현완료 -> 추후에 들어가는 내용만! 바꾸면 된다.
        settingPro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(ActivitySetting.this);
                dlg.setTitle("만든이");
                dlg.setMessage(
                        "대건 9인"
                );

                dlg.setPositiveButton("확인", null);
                dlg.show();
            }
        });

    }

}