package com.example.kangjisung.likeroom;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.example.kangjisung.likeroom.CustomClass.NoScrollViewPager;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.selector_menu_item));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.selector_menu_user));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.selector_menu_point));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.getTabAt(0).getIcon().setColorFilter(getResources().getColor(R.color.clrMenuIconSelected), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).getIcon().setColorFilter(getResources().getColor(R.color.clrMenuIcon), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(2).getIcon().setColorFilter(getResources().getColor(R.color.clrMenuIcon), PorterDuff.Mode.SRC_IN);

        final NoScrollViewPager viewPager = (NoScrollViewPager)findViewById(R.id.viewPager);
        final ActivityMenuPagerAdapter adapter = new ActivityMenuPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount(), selectedShopInfoData);
        viewPager.setAdapter(adapter);
        viewPager.setPagingDisabled();
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(getResources().getColor(R.color.clrMenuIconSelected), PorterDuff.Mode.SRC_IN);
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
    }

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
}
