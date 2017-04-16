package com.example.kangjisung.likeroom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kangjisung.likeroom.Util.ColorTheme;
import com.example.kangjisung.likeroom.Util.NoScrollViewPager;

import java.util.Arrays;

import static com.example.kangjisung.likeroom.DefineManager.selectedShopInfoDataKey;

public class ActivityMenu extends AppCompatActivity
{
    //처음으로 보여지는 탭의 번호를 변경
    //activity_menu 에서 디자인 된 창이 뜬다.
    //activity_menu -> 하단에 스탬프,공지사항,매장정보 창을 누르면 각각
    //스탬프(fragment_stamp_main), 공지사항(fragment_notice_main), 매장정보(fragment_info_main)으로 디자인된 창이 뜬다.

    //ActivityMenu는 ActivityMenuPagerAdapter와 연결되어 있으며
    //ActivityMenuPagerAdapter는 스탬프,공지사항,매장정보 아이콘을 눌렀을 때 실제로 이동시켜주는 부분을 담당한다.

    private int[] tabStringResIds = {
            R.string.menu_info_string,
            R.string.menu_notice_string,
            R.string.menu_stamp_string
    };
    String[] selectedShopInfoData;

    private ImageView imageViewSetting;
    private TabLayout tabLayout;
    private TextView textViewTitle;

    private int selectedTabColor;
    private int unselectedTabColor;

    final int firstShowTabPageNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.d(this.getClass().getSimpleName(), "onCreate()");

        this.setTheme(ColorTheme.getTheme());
        setContentView(R.layout.activity_menu);
        selectedTabColor = ContextCompat.getColor(this, R.color.gray80);
        unselectedTabColor = ContextCompat.getColor(this, R.color.gray160);

        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        textViewTitle = (TextView)findViewById(R.id.textViewTitle);

        tabLayoutInitialize(tabLayout);

        selectedShopInfoData = new String[selectedShopInfoDataKey.length];
        int i;
        for(i = 0; i < selectedShopInfoDataKey.length; i += 1) {
            selectedShopInfoData[i] = getIntent().getStringExtra(selectedShopInfoDataKey[i]);
        }
        Log.d(getString(R.string.app_name), Arrays.toString(selectedShopInfoData));

        final NoScrollViewPager viewPager = (NoScrollViewPager)findViewById(R.id.viewPager);
        final ActivityMenuPagerAdapter adapter = new ActivityMenuPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), selectedShopInfoData);
        viewPager.setAdapter(adapter);
        viewPager.setPagingDisabled();
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                RelativeLayout view = (RelativeLayout)tabLayout.getTabAt(tab.getPosition()).getCustomView();
                view.findViewById(R.id.icon_selected).setVisibility(View.VISIBLE);
                textViewTitle.setText(tabStringResIds[tab.getPosition()]);
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                RelativeLayout view = (RelativeLayout)tabLayout.getTabAt(tab.getPosition()).getCustomView();
                view.findViewById(R.id.icon_selected).setVisibility(View.INVISIBLE);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.getTabAt(2).select();

        findViewById(R.id.btn_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivitySetting.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
    }

    public void tabLayoutInitialize(TabLayout tabLayout)
    {
        int[] tabMipmapResIds = {
                R.mipmap.icon_menu_tab3,
                R.mipmap.icon_menu_tab2,
                R.mipmap.icon_menu_tab1
        };

        tabLayout.removeAllTabs();
        for (int i = 0; i < tabMipmapResIds.length; i++)
        {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = getLayoutInflater().inflate(R.layout.include_tabitem, null);
            view.findViewById(R.id.icon_selected).setBackground(ContextCompat.getDrawable(this, tabMipmapResIds[i]));
            view.findViewById(R.id.icon_unselected).setBackground(ContextCompat.getDrawable(this, tabMipmapResIds[i]));
            tab.setCustomView(view);
            tabLayout.addTab(tab);
        }

        tabLayout.getTabAt(0).getCustomView().findViewById(R.id.icon_selected).setVisibility(View.VISIBLE);
        tabLayout.getTabAt(1).getCustomView().findViewById(R.id.icon_selected).setVisibility(View.INVISIBLE);
        tabLayout.getTabAt(2).getCustomView().findViewById(R.id.icon_selected).setVisibility(View.INVISIBLE);
    }
}