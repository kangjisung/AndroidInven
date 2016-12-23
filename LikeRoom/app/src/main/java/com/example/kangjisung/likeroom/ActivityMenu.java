package com.example.kangjisung.likeroom;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class ActivityMenu extends AppCompatActivity
{
    private int[] tabMipmapResIds = {
            R.mipmap.icon_menu_item,
            R.mipmap.icon_menu_user,
            R.mipmap.icon_menu_point
    };
    private int[] tabStringResIds = {
            R.string.menu_stamp_string,
            R.string.menu_notice_string,
            R.string.menu_info_string
    };
    private ImageView imageViewSetting;
    private TabLayout tabLayout;
    private TextView textViewTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        imageViewSetting = (ImageView)findViewById(R.id.imageViewSetting);
        textViewTitle = (TextView)findViewById(R.id.textViewTitle);

        tabLayoutInitialize(tabLayout);
        colorInitialize();

        final ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        final ActivityMenuPagerAdapter adapter = new ActivityMenuPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.clrMenuIconSelected), PorterDuff.Mode.SRC_IN);
                textViewTitle.setText(tabStringResIds[tab.getPosition()]);
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.clrMenuIcon), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.getTabAt(2).select();
    }

    public void tabLayoutInitialize(TabLayout tabLayout)
    {
        for (int i = 0; i < tabMipmapResIds.length; i++) {
            tabLayout.addTab(tabLayout.newTab().setIcon(tabMipmapResIds[i]));
        }

        for (int i = 0; i < tabMipmapResIds.length; i++)
        {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            if (tab != null) {
                View view = getLayoutInflater().inflate(R.layout.include_tabitem, null);
                ((ImageView) view.findViewById(R.id.icon)).setImageResource(tabMipmapResIds[i]);
                tab.setCustomView(view);
            }
        }
    }

    public void colorInitialize()
    {
        tabLayout.getTabAt(0).getIcon().setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.clrMenuIconSelected), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).getIcon().setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.clrMenuIcon), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(2).getIcon().setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.clrMenuIcon), PorterDuff.Mode.SRC_IN);
        imageViewSetting.setColorFilter(ContextCompat.getColor(getBaseContext(), R.color.clrTextColorDeepDark), PorterDuff.Mode.SRC_IN);
    }

    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    */
}