package com.example.kangjisung.likeroom;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kangjisung.likeroom.Util.ColorTheme;
import com.example.kangjisung.likeroom.Util.LayoutManager;
import com.example.kangjisung.likeroom.Util.SharedPreferenceManager;
import com.example.kangjisung.likeroom.Util.SingleToast;
import com.example.kangjisung.likeroom.Util.Utility;

public class ActivitySetting extends AppCompatActivity
{
    private SharedPreferenceManager mSharedPreferenceManager = new SharedPreferenceManager();
    private AlertDialog dialog;

    LinearLayout settingLayout;
    Button settingFontSize,settingTheme,settingHelp,settingAsk,settingPro;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setTheme(ColorTheme.getTheme());
        setContentView(R.layout.activity_setting_new);

        findViewById(R.id.btn_info_createdby).setOnClickListener(onButtonSettingClickListener);
        findViewById(R.id.btn_info_application).setOnClickListener(onButtonSettingClickListener);
        findViewById(R.id.btn_set_theme).setOnClickListener(onButtonSettingClickListener);
        findViewById(R.id.btn_set_start).setOnClickListener(onButtonSettingClickListener);

        LayoutManager.setActivityTitle(findViewById(R.id.layout_title), true, false, "설정 및 정보");
        findViewById(R.id.inc_btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /*
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
            */
/*
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
        dlg.setView(helpview);*/


        /*settingHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               일단 안씀 dlg.setPositiveButton("확인",new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                Log.d("test", "clicked");
                helpDialog.show();

            }

        });*/
/*
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
*/
        //문의하기-> 디자인을 새로 만들어야 할 것 같은데 아직 디자인 구현 안된상태.
        /*
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
                                //Toast.makeText(ActivitySetting.this,"문의하기",Toast.LENGTH_LONG).show();
                                Snackbar.make(settingAsk, "기능 준비중입니다", 20000).setAction("확인", new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {

                                    }
                                }).show();
                                break;
                            case 1:
                                //Toast.makeText(ActivitySetting.this,"문의확인",Toast.LENGTH_LONG).show();
                                Snackbar.make(settingAsk, "기능 준비중입니다", 20000).setAction("확인", new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View v)
                                    {

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

        final ProDialog proDialog = new ProDialog(ActivitySetting.this);
        */

        //만든이. 2월4일 구현완료 -> 추후에 들어가는 내용만! 바꾸면 된다.
        /*
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
                proDialog.show();
            }
        });
        */
    }

    Button.OnClickListener onButtonSettingClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View onClickView) {
            ListView mListView;
            AlertDialog.Builder builder;
            switch(onClickView.getId()){
                case R.id.btn_set_start:
                    /*
                    mListView = new ListView(getBaseContext());
                    mListView.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, ListView.LayoutParams.MATCH_PARENT));
                    mListView.setDividerHeight(0);

                    final SettingStartListAdapter mAdapter1 = new SettingStartListAdapter(ActivitySetting.this, "set_start");
                    mAdapter1.addItem("제품 정보");
                    mAdapter1.addItem("고객 정보");
                    mAdapter1.addItem("포인트 적립");
                    mListView.setAdapter(mAdapter1);
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if(mSharedPreferenceManager.getInt("set_start", getApplicationContext()) != position){
                                setResult(1001);
                            }
                            mSharedPreferenceManager.putInt("set_start", position, getApplicationContext());
                            dialog.cancel();
                        }
                    });

                    builder = new AlertDialog.Builder(ActivitySetting.this);
                    builder.setView(mListView);
                    builder.setCustomTitle(Utility.getAlertDialogTitle("시작 화면 설정", ActivitySetting.this));
                    dialog = builder.create();
                    dialog.show();
                    break;
                */
                case R.id.btn_set_theme:
                    mListView = new ListView(getBaseContext());
                    mListView.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, ListView.LayoutParams.MATCH_PARENT));
                    mListView.setDividerHeight(0);

                    final SettingThemeListAdapter mAdapter2 = new SettingThemeListAdapter();
                    mAdapter2.addItem(R.style.LikeRoomTheme_StrawBerryTheme, "딸기", "#e2768c");
                    mAdapter2.addItem(R.style.LikeRoomTheme_BreadTheme, "빵", "#caad80");
                    mAdapter2.addItem(R.style.LikeRoomTheme_MintTheme, "민트", "#79be5c");
                    mAdapter2.addItem(R.style.LikeRoomTheme_OceanTheme, "바다", "#5597bf");
                    mAdapter2.addItem(R.style.LikeRoomTheme_GrapeTheme, "포도", "#be6eda");
                    mAdapter2.addItem(R.style.LikeRoomTheme_DarkTheme, "흑백", "#939393");
                    mAdapter2.addItem(R.style.LikeRoomTheme_NavyBlueTheme, "군청", "#7f75cf");
                    mListView.setAdapter(mAdapter2);
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if(mSharedPreferenceManager.getInt("set_theme", getApplicationContext()) != mAdapter2.getThemeId(position)){
                                setResult(1003);
                            }
                            mSharedPreferenceManager.putInt("set_theme", mAdapter2.getThemeId(position), getApplicationContext());
                            SingleToast.show(ActivitySetting.this, "테마 변경은 앱 재시작 후에 적용됩니다.", Toast.LENGTH_LONG);
                            dialog.cancel();
                        }
                    });

                    builder = new AlertDialog.Builder(ActivitySetting.this);
                    builder.setView(mListView);
                    builder.setCustomTitle(Utility.getAlertDialogTitle("테마 선택", ActivitySetting.this));
                    dialog = builder.create();
                    dialog.show();
                    break;
                case R.id.btn_info_application:
                    overridePendingTransition(0, 0);
                    break;
                case R.id.btn_info_createdby:
                    overridePendingTransition(0, 0);
                    break;
                default:
                    break;
            }
        }
    };
}