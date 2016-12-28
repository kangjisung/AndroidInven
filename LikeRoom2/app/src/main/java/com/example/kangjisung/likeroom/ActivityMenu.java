package com.example.kangjisung.likeroom;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

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
    String[] selectedShopInfoData;

    private ImageView imageViewSetting;
    private TabLayout tabLayout;
    private TextView textViewTitle;

    final int firstShowTabPageNumber = 0;

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

        selectedShopInfoData = new String[selectedShopInfoDataKey.length];
        int i;
        for(i = 0; i < selectedShopInfoDataKey.length; i += 1) {
            selectedShopInfoData[i] = getIntent().getStringExtra(selectedShopInfoDataKey[i]);
        }
        Log.d(getString(R.string.app_name), Arrays.toString(selectedShopInfoData));

        final ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        final ActivityMenuPagerAdapter adapter = new ActivityMenuPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), selectedShopInfoData);
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
        tabLayout.getTabAt(firstShowTabPageNumber).select();

        imageViewSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, getString(R.string.featureLoadFail), Snackbar.LENGTH_SHORT).show();
                //환경설정으로 넘어가는 화면을 구현해야함
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