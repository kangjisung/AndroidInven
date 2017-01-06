package com.example.kangjisung.likeroom;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kangjisung.likeroom.Util.ColorTheme;
import com.example.kangjisung.likeroom.Util.NoScrollViewPager;

public class ActivityMenu extends AppCompatActivity
{
    private int[] tabStringResIds = {
            R.string.menu_item_string,
            R.string.menu_user_string,
            R.string.menu_point_string
    };
    private ImageView imageViewSetting;
    private TabLayout tabLayout;
    private TextView textViewTitle;

    public interface onKeyBackPressedListener {public void onBack();}
    private onKeyBackPressedListener mOnKeyBackPressedListener;
    public void setOnKeyBackPressedListener(onKeyBackPressedListener listener) {
        mOnKeyBackPressedListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setTheme(R.style.LikeRoomTheme_StrawBerryTheme);
        setContentView(R.layout.activity_menu);

        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        imageViewSetting = (ImageView)findViewById(R.id.imageViewSetting);
        textViewTitle = (TextView)findViewById(R.id.textViewTitle);

        tabLayoutInitialize(tabLayout);
        initializeColor();

        final NoScrollViewPager viewPager = (NoScrollViewPager)findViewById(R.id.viewPager);
        final ActivityMenuPagerAdapter adapter = new ActivityMenuPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setPagingDisabled();
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LinearLayout view = (LinearLayout)tabLayout.getTabAt(tab.getPosition()).getCustomView();
                view.findViewById(R.id.icon).getBackground().setColorFilter(ColorTheme.getThemeColorRGB(view.getContext(), R.attr.theme_color_D3), PorterDuff.Mode.SRC_IN);
                textViewTitle.setText(tabStringResIds[tab.getPosition()]);
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                LinearLayout view = (LinearLayout)tabLayout.getTabAt(tab.getPosition()).getCustomView();
                view.findViewById(R.id.icon).getBackground().setColorFilter(ColorTheme.getThemeColorRGB(view.getContext(), R.attr.theme_color_D1), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.getTabAt(2).select();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void tabLayoutInitialize(TabLayout tabLayout)
    {
        int[] tabMipmapResIds = {
            R.mipmap.icon_menu_item,
            R.mipmap.icon_menu_user,
            R.mipmap.icon_mileage
        };

        for (int i = 0; i < tabMipmapResIds.length; i++)
        {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = getLayoutInflater().inflate(R.layout.include_tabitem, null);
            view.findViewById(R.id.icon).setBackground(ContextCompat.getDrawable(this, tabMipmapResIds[i]));
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
        // 둥근 모서리 색상 변경
        findViewById(R.id.corner_lt).getBackground().setColorFilter(ColorTheme.getThemeColorRGB(this, R.attr.theme_color_N), PorterDuff.Mode.SRC_IN);
        findViewById(R.id.corner_lb).getBackground().setColorFilter(ColorTheme.getThemeColorRGB(this, R.attr.theme_color_N), PorterDuff.Mode.SRC_IN);
        findViewById(R.id.corner_rt).getBackground().setColorFilter(ColorTheme.getThemeColorRGB(this, R.attr.theme_color_N), PorterDuff.Mode.SRC_IN);
        findViewById(R.id.corner_rb).getBackground().setColorFilter(ColorTheme.getThemeColorRGB(this, R.attr.theme_color_N), PorterDuff.Mode.SRC_IN);

        // 탭 아이콘 색상 변경
        LinearLayout view;
        view = (LinearLayout)tabLayout.getTabAt(0).getCustomView();
        view.findViewById(R.id.icon).getBackground().setColorFilter(ColorTheme.getThemeColorRGB(this, R.attr.theme_color_D3), PorterDuff.Mode.SRC_IN);
        view = (LinearLayout)tabLayout.getTabAt(1).getCustomView();
        view.findViewById(R.id.icon).getBackground().setColorFilter(ColorTheme.getThemeColorRGB(this, R.attr.theme_color_D1), PorterDuff.Mode.SRC_IN);
        view = (LinearLayout)tabLayout.getTabAt(2).getCustomView();
        view.findViewById(R.id.icon).getBackground().setColorFilter(ColorTheme.getThemeColorRGB(this, R.attr.theme_color_D1), PorterDuff.Mode.SRC_IN);

        imageViewSetting.setColorFilter(ColorTheme.getThemeColorRGB(this, R.attr.theme_color_D3), PorterDuff.Mode.SRC_IN);

        /*
        LayerDrawable layers = (LayerDrawable)ContextCompat.getDrawable(getBaseContext(), R.);
        layers.setColorFilter(ColorTheme.getThemeColorRGB(this, R.attr.theme_color_N), PorterDuff.Mode.SRC_IN);

        tabLayout.getTabAt(0).getIcon().setColorFilter(ColorTheme.getThemeColorRGB(this, R.attr.theme_color_D3), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).getIcon().setColorFilter(ColorTheme.getThemeColorRGB(this, R.attr.theme_color_D1), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(2).getIcon().setColorFilter(ColorTheme.getThemeColorRGB(this, R.attr.theme_color_D1), PorterDuff.Mode.SRC_IN);
        */
    }
}