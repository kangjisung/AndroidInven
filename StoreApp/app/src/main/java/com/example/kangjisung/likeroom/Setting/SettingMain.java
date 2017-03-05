package com.example.kangjisung.likeroom.Setting;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kangjisung.likeroom.ActivityStoreAdd;
import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.Util.ColorTheme;
import com.example.kangjisung.likeroom.Util.SharedPreferenceManager;
import com.example.kangjisung.likeroom.Util.SingleToast;
import com.example.kangjisung.likeroom.Util.Utility;

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

        findViewById(R.id.btn_back).setOnClickListener(onButtonSettingClickListener);
        findViewById(R.id.btn_set_theme).setOnClickListener(onButtonSettingClickListener);
        findViewById(R.id.btn_info_createdby).setOnClickListener(onButtonSettingClickListener);
        findViewById(R.id.btn_info_application).setOnClickListener(onButtonSettingClickListener);
    }

    Button.OnClickListener onButtonSettingClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View onClickView) {

            switch(onClickView.getId()){
                case R.id.btn_set_theme:
                    ListView mListView = new ListView(getApplicationContext());
                    mListView.setLayoutParams(new ListView.LayoutParams(ListView.LayoutParams.MATCH_PARENT, ListView.LayoutParams.MATCH_PARENT));
                    mListView.setDividerHeight(0);

                    final SettingThemeListAdapter mAdapter = new SettingThemeListAdapter();
                    mAdapter.addItem(R.style.LikeRoomTheme_StrawBerryTheme, "딸기", "#e2768c");
                    mAdapter.addItem(R.style.LikeRoomTheme_BreadTheme, "빵", "#caad80");
                    mAdapter.addItem(R.style.LikeRoomTheme_MintTheme, "민트", "#79be5c");
                    mAdapter.addItem(R.style.LikeRoomTheme_OceanTheme, "바다", "#5597bf");
                    mAdapter.addItem(R.style.LikeRoomTheme_GrapeTheme, "포도", "#be6eda");
                    mAdapter.addItem(R.style.LikeRoomTheme_DarkTheme, "흑백", "#939393");
                    mListView.setAdapter(mAdapter);
                    mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            mSharedPreferenceManager.putInt("theme", mAdapter.getThemeId(position), getApplicationContext());

                            SingleToast.show(SettingMain.this, "테마 변경은 앱 재시작 후에 적용됩니다.", Toast.LENGTH_LONG);
                            dialog.cancel();
                        }
                    });


                    AlertDialog.Builder builder = new AlertDialog.Builder(SettingMain.this);

                    builder.setView(mListView);
                    builder.setCustomTitle(Utility.getAlertDialogTitle("테마 선택", SettingMain.this));
                    dialog = builder.create();
                    dialog.show();
                    break;
                case R.id.btn_info_application:
                    startActivity(new Intent(getApplicationContext(), SettingApplication.class));
                    break;
                case R.id.btn_info_createdby:
                    startActivity(new Intent(getApplicationContext(), SettingCreatedby.class));
                    break;
                case R.id.btn_back:
                    finish();
                    break;
                default:
                    break;
            }
        }
    };
}