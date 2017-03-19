package com.teamdk.android.bakery;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.teamdk.android.bakery.setting.SettingMain;
import com.teamdk.android.bakery.utility.ColorTheme;
import com.teamdk.android.bakery.utility.NoScrollViewPager;
import com.teamdk.android.bakery.utility.SharedPreferenceManager;
import com.teamdk.android.bakery.utility.Utility;

public class ActivityMenu extends AppCompatActivity
{
    private int[] tabStringResIds = {
            R.string.menu_point_string,
            R.string.menu_user_string,
            R.string.menu_item_string
    };
    private TabLayout tabLayout;
    private TextView textViewTitle;
    private ActivityMenuPagerAdapter mAdapter;
    private NoScrollViewPager viewPager;

    private int REQUEST_SETTING = 1001;
    private int nowPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setTheme(ColorTheme.getTheme());
        setContentView(R.layout.activity_menu);

        tabLayout = (TabLayout)findViewById(R.id.tabLayout);

        textViewTitle = (TextView)findViewById(R.id.textView_title);

        nowPosition = 2 - (new SharedPreferenceManager()).getInt("set_start", this);
        tabLayoutInitialize();
        reloadViewPager();

        Button mButtonSetting = (Button) findViewById(R.id.btn_setting);
        mButtonSetting.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingMain.class);
                startActivityForResult(intent, REQUEST_SETTING);
            }
        });
    }

    public void reloadViewPager()
    {
        viewPager = null;
        viewPager = (NoScrollViewPager)findViewById(R.id.viewPager);
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
                if(tab.getPosition() != 0) {
                    Utility.hideSoftKeyboard(ActivityMenu.this);
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                }
                else{
                    getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                }
                nowPosition = viewPager.getCurrentItem();
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
        viewPager.setCurrentItem(nowPosition);
        tabLayout.getTabAt(nowPosition).select();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent Data) {
        super.onActivityResult(requestCode, resultCode, Data);

        if(requestCode == REQUEST_SETTING) {
            if (resultCode == RESULT_OK) {
                reloadViewPager();
            }
        }
    }

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage("앱을 종료하시겠습니까?");
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

    public void tabLayoutInitialize()
    {
        int[] tabMipmapResIds = {
            R.mipmap.icon_menu_point,
            R.mipmap.icon_menu_user,
            R.mipmap.icon_menu_item
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