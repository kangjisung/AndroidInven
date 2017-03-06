package com.example.kangjisung.likeroom;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kangjisung.likeroom.Setting.SettingMain;
import com.example.kangjisung.likeroom.Util.ColorTheme;
import com.example.kangjisung.likeroom.Util.NoScrollViewPager;
import com.example.kangjisung.likeroom.Util.SharedPreferenceManager;

public class ActivityMenu extends AppCompatActivity
{
    private int[] tabStringResIds = {
            R.string.menu_point_string,
            R.string.menu_user_string,
            R.string.menu_item_string
    };
    private TabLayout tabLayout;
    private TextView textViewTitle;

    private int selectedTabColor ;
    private int unselectedTabColor;

    private ActivityMenuPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setTheme(ColorTheme.getTheme());
        setContentView(R.layout.activity_menu);
        selectedTabColor = ContextCompat.getColor(this, R.color.gray80);
        unselectedTabColor = ContextCompat.getColor(this, R.color.gray160);

        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        textViewTitle = (TextView)findViewById(R.id.textView_title);
        textViewTitle.setText("포인트 적립");

        tabLayoutInitialize(tabLayout);
        initializeColor();

        Button mButtonSetting = (Button) findViewById(R.id.btn_setting);
        mButtonSetting.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SettingMain.class));
            }
        });

        final NoScrollViewPager viewPager = (NoScrollViewPager)findViewById(R.id.viewPager);
        mAdapter = new ActivityMenuPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(mAdapter);
        viewPager.setPagingDisabled();
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                RelativeLayout view = (RelativeLayout)tabLayout.getTabAt(tab.getPosition()).getCustomView();
                view.findViewById(R.id.icon_selected).setVisibility(View.VISIBLE);
                //view.findViewById(R.id.icon).getBackground().setColorFilter(selectedTabColor, PorterDuff.Mode.SRC_IN);
                textViewTitle.setText(tabStringResIds[tab.getPosition()]);
                viewPager.setCurrentItem(tab.getPosition());

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                RelativeLayout view = (RelativeLayout)tabLayout.getTabAt(tab.getPosition()).getCustomView();
                view.findViewById(R.id.icon_selected).setVisibility(View.INVISIBLE);
                //view.findViewById(R.id.icon).getBackground().setColorFilter(unselectedTabColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayout.getTabAt(2 - (new SharedPreferenceManager()).getInt("set_start", this)).select();
    }

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("정말 종료하시겠습니까?");
        dialog.setPositiveButton("예", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        dialog.setNegativeButton("아니요", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();
        //super.onBackPressed();
    }

    public void tabLayoutInitialize(TabLayout tabLayout)
    {
        int[] tabMipmapResIds = {
                R.mipmap.icon_mileage,
                R.mipmap.icon_menu_user,
                R.mipmap.icon_menu_item
        };

        for (int i = 0; i < tabMipmapResIds.length; i++)
        {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = getLayoutInflater().inflate(R.layout.include_tabitem, null);
            view.findViewById(R.id.icon_selected).setBackground(ContextCompat.getDrawable(this, tabMipmapResIds[i]));
            view.findViewById(R.id.icon_unselected).setBackground(ContextCompat.getDrawable(this, tabMipmapResIds[i]));
            tab.setCustomView(view);
            tabLayout.addTab(tab);
        }

        /*
        for (int i = 0; i < tabMipmapResIds.length; i++)
        {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                View view = getLayoutInflater().inflate(R.layout.include_tabitem, null);
                ((ImageView)view.findViewById(R.id.icon)).setImageResource(tabMipmapResIds[i]);
                ((ImageView)view.findViewById(R.id.icon)).setBackground(ContextCompat.getDrawable(this, tabMipmapResIds[i]));
                tab.setCustomView(view);
            }
        }
        */
    }

    public void initializeColor()
    {
        LinearLayout view;

        //view = (LinearLayout)tabLayout.getTabAt(0).getCustomView();
        //view.findViewById(R.id.icon_selected).setVisibility(View.VISIBLE);
        //view = (LinearLayout)tabLayout.getTabAt(1).getCustomView();
        //.findViewById(R.id.icon_selected).setVisibility(View.INVISIBLE);
        tabLayout.getTabAt(0).getCustomView().findViewById(R.id.icon_selected).setVisibility(View.VISIBLE);
        tabLayout.getTabAt(1).getCustomView().findViewById(R.id.icon_selected).setVisibility(View.INVISIBLE);
        tabLayout.getTabAt(2).getCustomView().findViewById(R.id.icon_selected).setVisibility(View.INVISIBLE);
        //view.findViewById(R.id.icon_selected).setVisibility(View.INVISIBLE);

        /*
        view = (LinearLayout)tabLayout.getTabAt(0).getCustomView();
        view.findViewById(R.id.icon).getBackground().setColorFilter(selectedTabColor, PorterDuff.Mode.SRC_IN);
        view = (LinearLayout)tabLayout.getTabAt(1).getCustomView();
        view.findViewById(R.id.icon).getBackground().setColorFilter(unselectedTabColor, PorterDuff.Mode.SRC_IN);
        view = (LinearLayout)tabLayout.getTabAt(2).getCustomView();
        view.findViewById(R.id.icon).getBackground().setColorFilter(unselectedTabColor, PorterDuff.Mode.SRC_IN);
        */

        // 둥근 모서리 색상 변경
        /*
        RelativeLayout layoutCorner = (RelativeLayout)findViewById(R.id.layout_corner);
        ((AppCompatImageView)layoutCorner.findViewById(R.id.corner_lt)).setSupportBackgroundTintList(ColorStateList.valueOf(ColorTheme.getThemeColorRGB(this, R.attr.theme_color_N)));
        ((AppCompatImageView)layoutCorner.findViewById(R.id.corner_lb)).setSupportBackgroundTintList(ColorStateList.valueOf(ColorTheme.getThemeColorRGB(this, R.attr.theme_color_N)));
        ((AppCompatImageView)layoutCorner.findViewById(R.id.corner_rt)).setSupportBackgroundTintList(ColorStateList.valueOf(ColorTheme.getThemeColorRGB(this, R.attr.theme_color_N)));
        ((AppCompatImageView)layoutCorner.findViewById(R.id.corner_rb)).setSupportBackgroundTintList(ColorStateList.valueOf(ColorTheme.getThemeColorRGB(this, R.attr.theme_color_N)));
        */

        // 탭 아이콘 색상 변경
        /*
        LinearLayout view;
        view = (LinearLayout)tabLayout.getTabAt(0).getCustomView();
        view.findViewById(R.id.icon).getBackground().setColorFilter(ColorTheme.getThemeColorRGB(this, R.attr.theme_color_D3), PorterDuff.Mode.SRC_IN);
        view = (LinearLayout)tabLayout.getTabAt(1).getCustomView();
        view.findViewById(R.id.icon).getBackground().setColorFilter(ColorTheme.getThemeColorRGB(this, R.attr.theme_color_D1), PorterDuff.Mode.SRC_IN);
        view = (LinearLayout)tabLayout.getTabAt(2).getCustomView();
        view.findViewById(R.id.icon).getBackground().setColorFilter(ColorTheme.getThemeColorRGB(this, R.attr.theme_color_D1), PorterDuff.Mode.SRC_IN);
        */

        /*
        LayerDrawable layers = (LayerDrawable)ContextCompat.getDrawable(getBaseContext(), R.);
        layers.setColorFilter(ColorTheme.getThemeColorRGB(this, R.attr.theme_color_N), PorterDuff.Mode.SRC_IN);

        tabLayout.getTabAt(0).getIcon().setColorFilter(ColorTheme.getThemeColorRGB(this, R.attr.theme_color_D3), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).getIcon().setColorFilter(ColorTheme.getThemeColorRGB(this, R.attr.theme_color_D1), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(2).getIcon().setColorFilter(ColorTheme.getThemeColorRGB(this, R.attr.theme_color_D1), PorterDuff.Mode.SRC_IN);
        */
    }
}