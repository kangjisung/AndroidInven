package com.teamdk.android.bakery.fragments.user;

import android.app.Dialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.teamdk.android.bakery.objectmanager.MemberListItem;
import com.teamdk.android.bakery.R;
import com.teamdk.android.bakery.utility.LayoutManager;

import java.util.ArrayList;

public class UserStampDialog extends Dialog
{
    TabLayout tabLayout;
    ViewPager viewPager;
    UserStampDialogPagerAdapter pagerAdapter;
    ArrayList<MemberListItem> addItemList;

    public UserStampDialog(ArrayList<MemberListItem> addItemList, Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);

        this.addItemList = addItemList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        setContentView(R.layout.user_stamp_dialog);

        LayoutManager.setDialogTitle(findViewById(R.id.layout_title), true, false, "스탬프 발송");
        findViewById(R.id.inc_btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        viewPager = (ViewPager)findViewById(R.id.viewPager);

        pagerAdapter = new UserStampDialogPagerAdapter(getContext(), 3, addItemList.size());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setOffscreenPageLimit(3);

        tabLayoutInitialize(tabLayout, pagerAdapter.getCount());
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.icon_selected).setVisibility(View.VISIBLE);
                tab.getCustomView().findViewById(R.id.icon_unselected).setVisibility(View.INVISIBLE);
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.icon_selected).setVisibility(View.INVISIBLE);
                tab.getCustomView().findViewById(R.id.icon_unselected).setVisibility(View.VISIBLE);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.getTabAt(0).select();

        AppCompatImageView imageViewEmblem = (AppCompatImageView)findViewById(R.id.imageView_stamp);
        imageViewEmblem.setBackgroundResource(pagerAdapter.getSelectedItem());
        imageViewEmblem.setSupportBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.white)));

        Button mOKButton = (Button)findViewById(R.id.button_ok_down);
        mOKButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View onClickView){
                dismiss();
            }
        });
    }

    private void tabLayoutInitialize(TabLayout tabLayout, int numOfPage)
    {
        for (int i = 0; i < numOfPage; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setCustomView(R.layout.include_tabcircle);
            tab.getCustomView().findViewById((i==0)?(R.id.icon_unselected):(R.id.icon_selected)).setVisibility(View.INVISIBLE);
            tabLayout.addTab(tab);
        }
    }
}