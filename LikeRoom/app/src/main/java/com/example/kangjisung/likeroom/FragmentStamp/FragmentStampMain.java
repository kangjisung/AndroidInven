package com.example.kangjisung.likeroom.FragmentStamp;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kangjisung.likeroom.DefineManager;
import com.example.kangjisung.likeroom.R;

public class FragmentStampMain extends Fragment {
    //스탬프 현황 레이아웃을 그려야할 것
    //또한 스탬프 사용시 팝업도 띄워야 함
    //스탬프 레이아웃에서 선택된 매장에서 현제 나의 스탬프 상황을 봄

    View stampLayout;
    Button btnShowSpecialStamp;
    String[] selectedShopInfoData;
    TextView txtShopPhoneNumber, txtShopName;
    StampUseDialog stampUseDialog;
    TabLayout tabLayout;
    //나중에 갯수 수정
    int numOfStamp = 18;

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

        Button buttontest = (Button)stampLayout.findViewById(R.id.buttontest);
        buttontest.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                stampUseDialog = new StampUseDialog(getActivity(),
                        "[다이얼로그 제목]", // 제목
                        leftListener,
                        rightListener); // 오른쪽 버튼 이벤트
                stampUseDialog.show();
            }
        });


        /*
        tabLayout = (TabLayout)stampLayout.findViewById(R.id.tabLayout);


        final ViewPager viewPager = (ViewPager)stampLayout.findViewById(R.id.viewPager);
        final StampPagerAdapter mAdapter = new StampPagerAdapter(getFragmentManager(), tabLayout.getTabCount());
        tabLayoutInitialize(tabLayout, mAdapter.getCount());
        viewPager.setAdapter(mAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(ContextCompat.getColor(getActivity(), R.color.clrMenuIconSelected), PorterDuff.Mode.SRC_IN);
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(ContextCompat.getColor(getActivity(), R.color.clrMenuIcon), PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        tabLayout.getTabAt(0).select();
        */
        return stampLayout;
    }

    private View.OnClickListener leftListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getContext(), "왼쪽버튼 클릭",
                    Toast.LENGTH_SHORT).show();
            stampUseDialog.dismiss();
        }
    };

    private View.OnClickListener rightListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getContext(), "오른쪽버튼 클릭",
                    Toast.LENGTH_SHORT).show();
        }
    };

    public void tabLayoutInitialize(TabLayout tabLayout, int numOfPage)
    {
        for (int i = 0; i < numOfPage; i++) {
            tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.icon_menu_point));
        }
    }

    /* 이벤트 코드는 여기서 */
}