package com.example.kangjisung.likeroom.FragmentStamp;

import android.app.TabActivity;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kangjisung.likeroom.DefineManager;
import com.example.kangjisung.likeroom.R;

import java.util.ArrayList;
import java.util.List;

public class FragmentStampMain extends Fragment {
    //스탬프 현황 레이아웃을 그려야할 것
    //또한 스탬프 사용시 팝업도 띄워야 함
    //스탬프 레이아웃에서 선택된 매장에서 현제 나의 스탬프 상황을 봄

    View stampLayout;
    Button btnShowSpecialStamp;
    Button buttonStampLeft;
    Button buttonStampRight;
    String[] selectedShopInfoData;
    TextView txtShopPhoneNumber, txtShopName;
    StampUseDialog stampUseDialog;
    TabLayout tabLayout;
    //나중에 갯수 수정
    int numOfStamp = 35;
    StampPagerAdapter pagerAdapter;
    ViewPager pager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /* 초기화 코드는 여기서 */
        stampLayout = inflater.inflate(R.layout.fragment_stamp_main, container, false);
        btnShowSpecialStamp = (Button)stampLayout.findViewById(R.id.btnShowSpecialStamp);
        txtShopPhoneNumber = (TextView) stampLayout.findViewById(R.id.txtShopPhoneNumber);
        txtShopName = (TextView) stampLayout.findViewById(R.id.txtShopName);

        selectedShopInfoData = getArguments().getStringArray("shopInfoData");

        txtShopName.setText(selectedShopInfoData[DefineManager.shopNameSavedPoint]);
        txtShopPhoneNumber.setText(selectedShopInfoData[DefineManager.shopPhoneNumberSavedPoint]);

        //쿠폰<->스탬프 레이아웃을 전환하면서 나의 쿠폰과 스탬프 상태를 봄
        btnShowSpecialStamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, getString(R.string.featureLoadFail), Snackbar.LENGTH_SHORT).show();
            }
        });

        // 스탬프 목록 띄우기
        pager = (ViewPager)stampLayout.findViewById(R.id.viewPager);
        pagerAdapter = new StampPagerAdapter(getActivity(), numOfStamp);
        pager.setAdapter(pagerAdapter);

        tabLayout = (TabLayout)stampLayout.findViewById(R.id.tabLayout);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayoutInitialize(tabLayout, pagerAdapter.getCount());
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getCustomView().findViewById(R.id.icon_selected).setVisibility(View.VISIBLE);
                tab.getCustomView().findViewById(R.id.icon_unselected).setVisibility(View.INVISIBLE);
                pager.setCurrentItem(tab.getPosition());
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

        buttonStampLeft = (Button)stampLayout.findViewById(R.id.button_stamp_left);
        buttonStampLeft.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                int nowTab = tabLayout.getSelectedTabPosition();
                if(nowTab > 0){
                    pager.setCurrentItem(nowTab-1);
                }
            }
        });
        buttonStampRight = (Button)stampLayout.findViewById(R.id.button_stamp_right);
        buttonStampRight.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                int nowTab = tabLayout.getSelectedTabPosition();
                if(nowTab < tabLayout.getTabCount()-1){
                    pager.setCurrentItem(nowTab+1);
                }
            }
        });

        return stampLayout;
    }

    public Button.OnClickListener openUseDialogListener = new Button.OnClickListener() {
        @Override
        public void onClick(View onClickView){

        }
    };

    private View.OnClickListener leftListener = new View.OnClickListener() {
        public void onClick(View v) {
            /*Toast.makeText(getContext(), "왼쪽버튼 클릭",
                    Toast.LENGTH_SHORT).show();*/
            //pop up close button
            stampUseDialog.dismiss();
        }
    };

    private View.OnClickListener rightListener = new View.OnClickListener() {
        public void onClick(View v) {
            /*Toast.makeText(getContext(), "오른쪽버튼 클릭",
                    Toast.LENGTH_SHORT).show();*/
            //stamp use button
            Snackbar.make(v, getString(R.string.featureLoadFail), Snackbar.LENGTH_SHORT).show();
            Log.d(getString(R.string.app_name), "use btn clicked");
        }
    };

    public void tabLayoutInitialize(TabLayout tabLayout, int numOfPage)
    {
        for (int i = 0; i < numOfPage; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setCustomView(R.layout.include_tabitem_circle);
            tab.getCustomView().findViewById((i==0)?(R.id.icon_unselected):(R.id.icon_selected)).setVisibility(View.INVISIBLE);
            tabLayout.addTab(tab);
        }
    }
}