package com.teamdk.android.bakery;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.teamdk.android.bakery.fragments.product.FragmentMuchStore;
import com.teamdk.android.bakery.fragments.product.FragmentSellToday;
import com.teamdk.android.bakery.fragments.product.ProductMain;
import com.teamdk.android.bakery.fragments.user.UserNoticeMain;
import com.teamdk.android.bakery.objectmanager.ProductObjectManager;
import com.teamdk.android.bakery.utility.Interfaces;
import com.teamdk.android.bakery.fragments.user.UserMain;
import com.teamdk.android.bakery.setting.SettingMain;
import com.teamdk.android.bakery.utility.ColorTheme;
import com.teamdk.android.bakery.utility.NoScrollViewPager;
import com.teamdk.android.bakery.utility.SharedPreferenceManager;
import com.teamdk.android.bakery.utility.Utility;

public class ActivityMenu extends AppCompatActivity implements Interfaces
{
    private int[] tabStringResIds = {R.string.menu_point_string, R.string.menu_user_string, R.string.menu_item_string};
    private TabLayout tabLayout;
    private TextView textViewTitle;

    private ActivityMenuPagerAdapter mAdapter;
    private NoScrollViewPager viewPager;
    private FragmentMuchStore mFragmentMuchStore;
    private FragmentSellToday mFragmentSellToday;

    private int REQUEST_SETTING = 1001;
    private int nowPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        this.setTheme(ColorTheme.getTheme());
        setContentView(R.layout.activity_menu);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

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

    protected void onActivityResult(int requestCode, int resultCode, Intent Data) {
        super.onActivityResult(requestCode, resultCode, Data);

        if(requestCode == REQUEST_SETTING) {
            if (resultCode == 1002) {
                ProductMain productFragment = (ProductMain) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + 2);
                UserMain userFragment = (UserMain) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + 1);
                productFragment.setFloatingMenu();
                userFragment.setFloatingMenu();
            }
            else if (resultCode == 1003){
            }
        }
    }

    public void reloadViewPager()
    {
        mAdapter = new ActivityMenuPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager = (NoScrollViewPager)findViewById(R.id.viewPager);
        viewPager.setAdapter(mAdapter);
        viewPager.setPagingDisabled();
        viewPager.setOffscreenPageLimit(3);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                RelativeLayout view = (RelativeLayout)tabLayout.getTabAt(tab.getPosition()).getCustomView();
                view.findViewById(R.id.icon_selected).setVisibility(View.VISIBLE);
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
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.setCurrentItem(nowPosition);
        tabLayout.getTabAt(nowPosition).select();
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
    }

    // 포인트 적립 리스트 클릭
    @Override
    public void PointListClicked(int message){
        if(message != 0){
            UserMain page = (UserMain) getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.viewPager + ":" + 1);
            page.refreshRecyclerView();
        }
    }

    @Override
    public void SellTodayMoved(int dx, int dy){
        if(mFragmentMuchStore == null) {
            for (int p = 0; p < getSupportFragmentManager().getFragments().size(); p++) {
                Fragment nowFragment = getSupportFragmentManager().getFragments().get(p);
                if (nowFragment instanceof FragmentMuchStore) {
                    mFragmentMuchStore = (FragmentMuchStore) nowFragment;
                }
            }
        }
        mFragmentMuchStore.setScroll(dx, dy);
    }

    @Override
    public void MuchStoreMoved(int dx, int dy){
        if(mFragmentSellToday == null){
            for(int p=0; p<getSupportFragmentManager().getFragments().size(); p++){
                Fragment nowFragment = getSupportFragmentManager().getFragments().get(p);
                if(nowFragment instanceof FragmentSellToday){
                    mFragmentSellToday = (FragmentSellToday) nowFragment;
                }
            }
        }
        mFragmentSellToday.setScroll(dx, dy);
    }

    @Override
    public void onBackPressed()
    {
        for(int p=0; p<getSupportFragmentManager().getFragments().size(); p++){
            Fragment nowFragment = getSupportFragmentManager().getFragments().get(p);
            if(nowFragment instanceof UserNoticeMain){
                getSupportFragmentManager().popBackStack();
                return;
            }
        }
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
}