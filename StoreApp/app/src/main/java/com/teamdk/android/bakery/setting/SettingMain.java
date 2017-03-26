package com.teamdk.android.bakery.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.teamdk.android.bakery.R;
import com.teamdk.android.bakery.setting.adapter.SettingStartListAdapter;
import com.teamdk.android.bakery.setting.adapter.SettingThemeListAdapter;
import com.teamdk.android.bakery.utility.ColorTheme;
import com.teamdk.android.bakery.utility.LayoutManager;
import com.teamdk.android.bakery.utility.SharedPreferenceManager;
import com.teamdk.android.bakery.utility.SingleToast;
import com.teamdk.android.bakery.utility.Utility;

public class SettingMain extends AppCompatActivity
{
    private SharedPreferenceManager mSharedPreferenceManager = new SharedPreferenceManager();
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setTheme(ColorTheme.getTheme());
        setContentView(R.layout.activity_setting);

        findViewById(R.id.btn_info_createdby).setOnClickListener(onButtonSettingClickListener);
        findViewById(R.id.btn_info_application).setOnClickListener(onButtonSettingClickListener);
        findViewById(R.id.btn_set_theme).setOnClickListener(onButtonSettingClickListener);
        findViewById(R.id.btn_set_start).setOnClickListener(onButtonSettingClickListener);
        findViewById(R.id.btn_set_menu).setOnClickListener(onButtonSettingClickListener);
        findViewById(R.id.btn_store_modify).setOnClickListener(onButtonSettingClickListener);
        findViewById(R.id.btn_store_delete).setOnClickListener(onButtonSettingClickListener);

        LayoutManager.setActivityTitle(findViewById(R.id.layout_title), true, false, "설정 및 정보");
        findViewById(R.id.inc_btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    Button.OnClickListener onButtonSettingClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View onClickView) {
            ListView mListView;
            AlertDialog.Builder builder;
            switch(onClickView.getId()){
                case R.id.btn_set_start:
                    mListView = new ListView(getBaseContext());
                    mListView.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, ListView.LayoutParams.MATCH_PARENT));
                    mListView.setDividerHeight(0);

                    final SettingStartListAdapter mAdapter1 = new SettingStartListAdapter(SettingMain.this, "set_start");
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

                    builder = new AlertDialog.Builder(SettingMain.this);
                    builder.setView(mListView);
                    builder.setCustomTitle(Utility.getAlertDialogTitle("시작 화면 설정", SettingMain.this));
                    dialog = builder.create();
                    dialog.show();
                    break;
                case R.id.btn_set_menu:
                    mListView = new ListView(getBaseContext());
                    mListView.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, ListView.LayoutParams.MATCH_PARENT));
                    mListView.setDividerHeight(0);

                    SettingStartListAdapter mAdapter3 = new SettingStartListAdapter(SettingMain.this, "set_menu");
                    mAdapter3.addItem("팝업 메뉴");
                    mAdapter3.addItem("팝업 메뉴 없음");
                    mListView.setAdapter(mAdapter3);
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            if(mSharedPreferenceManager.getInt("set_menu", getApplicationContext()) != position){
                                setResult(1002);
                            }
                            mSharedPreferenceManager.putInt("set_menu", position, getApplicationContext());
                            dialog.cancel();
                        }
                    });

                    builder = new AlertDialog.Builder(SettingMain.this);
                    builder.setView(mListView);
                    builder.setCustomTitle(Utility.getAlertDialogTitle("메뉴 스타일 설정", SettingMain.this));
                    dialog = builder.create();
                    dialog.show();
                    break;
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
                            SingleToast.show(SettingMain.this, "테마 변경은 앱 재시작 후에 적용됩니다.", Toast.LENGTH_LONG);
                            dialog.cancel();
                        }
                    });

                    builder = new AlertDialog.Builder(SettingMain.this);
                    builder.setView(mListView);
                    builder.setCustomTitle(Utility.getAlertDialogTitle("테마 선택", SettingMain.this));
                    dialog = builder.create();
                    dialog.show();
                    break;
                case R.id.btn_info_application:
                    startActivity(new Intent(getApplicationContext(), SettingApplication.class));
                    overridePendingTransition(0, 0);
                    break;
                case R.id.btn_info_createdby:
                    startActivity(new Intent(getApplicationContext(), SettingCreatedby.class));
                    overridePendingTransition(0, 0);
                    break;
                case R.id.btn_store_modify:
                    startActivity(new Intent(getApplicationContext(), SettingStoreModify.class));
                    break;
                case R.id.btn_store_delete:
                    break;
                default:
                    break;
            }
        }
    };
}