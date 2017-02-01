package com.example.kangjisung.likeroom;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kangjisung.likeroom.Fragment.FragmentSort;
import com.example.kangjisung.likeroom.FragmentItem.FragmentItemMain;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

public class ActivityMenu extends AppCompatActivity
{
    private int[] tabMipmapResIds = {
            R.mipmap.icon_menu_item,
            R.mipmap.icon_menu_user,
            R.mipmap.icon_menu_point
    };
    private int[] tabStringResIds = {
            R.string.menu_item_string,
            R.string.menu_user_string,
            R.string.menu_point_string
    };
    private ImageView imageViewSetting;
    private TabLayout tabLayout;
    private TextView textViewTitle;
    private FloatingActionMenu floatingActionMenu;
    private FloatingActionButton floatingActionButton;
    ViewPager viewPager;
    ActivityMenuPagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        imageViewSetting = (ImageView)findViewById(R.id.imageViewSetting);
        textViewTitle = (TextView)findViewById(R.id.textViewTitle);
        floatingActionMenu = (FloatingActionMenu)findViewById(R.id.menu);

        floatingActionButton = (FloatingActionButton)findViewById(R.id.fab_sort);


        tabLayoutInitialize(tabLayout);
        colorInitialize();

        viewPager = (ViewPager)findViewById(R.id.viewPager);
        adapter = new ActivityMenuPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getResources().getColor(R.color.clrMenuIconSelected), PorterDuff.Mode.SRC_IN);
                textViewTitle.setText(tabStringResIds[tab.getPosition()]);
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getResources().getColor(R.color.clrMenuIcon), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fr = adapter.getItem(0);
                if(fr instanceof FragmentItemMain){
                    ((FragmentItemMain)fr).firstPageListener.onSwitchToNextFragment();
                }
                floatingActionMenu.close(true);
                floatingActionMenu.hideMenu(true);
            }
        });
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==0 && !(adapter.getItem(0) instanceof FragmentSort)){
                    floatingActionMenu.showMenu(true);
                }else{
                    floatingActionMenu.hideMenu(true);

                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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

    @Override
    public void onBackPressed() {
        if(viewPager.getCurrentItem()==0 && adapter.getItem(0) instanceof FragmentSort){
            ((FragmentSort)adapter.getItem(0)).firstPageListener.onSwitchToNextFragment();
            floatingActionMenu.showMenu(true);

        }
        else{
            super.onBackPressed();
        }
    }

    public void colorInitialize()
    {
        tabLayout.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.clrMenuIconSelected), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).getIcon().setColorFilter(getResources().getColor(R.color.clrMenuIcon), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(2).getIcon().setColorFilter(getResources().getColor(R.color.clrMenuIcon), PorterDuff.Mode.SRC_IN);
        imageViewSetting.setColorFilter(getResources().getColor(R.color.clrTextColorDeepDark), PorterDuff.Mode.SRC_IN);
    }
}