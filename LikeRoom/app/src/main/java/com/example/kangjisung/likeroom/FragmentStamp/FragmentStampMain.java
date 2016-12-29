package com.example.kangjisung.likeroom.FragmentStamp;

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
    String[] selectedShopInfoData;
    TextView txtShopPhoneNumber, txtShopName;
    StampUseDialog stampUseDialog;
    TabLayout tabLayout;
    //나중에 갯수 수정
    int numOfStamp = 18;
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
        final StampPagerAdapter mAdapter = new StampPagerAdapter(getFragmentManager(), numOfStamp);
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
        */

        // 스탬프 목록 띄우기
        pagerAdapter = new StampPagerAdapter();
        pager = (ViewPager)stampLayout.findViewById(R.id.viewPager);
        pager.setAdapter (pagerAdapter);


        // Create an initial view to display; must be a subclass of FrameLayout.
        LayoutInflater inflater1 = getActivity().getLayoutInflater();
        FrameLayout v0 = (FrameLayout)inflater1.inflate (R.layout.stamp_page, null);
        pagerAdapter.addView (v0, 0);
        pagerAdapter.addView (v0, 1);
        pagerAdapter.addView (v0, 2);
        pagerAdapter.notifyDataSetChanged();

        tabLayout = (TabLayout)stampLayout.findViewById(R.id.tabLayout);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayoutInitialize(tabLayout, pagerAdapter.getCount());
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.getIcon().setColorFilter(ContextCompat.getColor(getActivity(), R.color.clrMenuIconSelected), PorterDuff.Mode.SRC_IN);
                //pager.setCurrentItem(tab.getPosition());
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

        return stampLayout;
    }

    public void addView (View newPage)
    {
        int pageIndex = pagerAdapter.addView (newPage);
        // You might want to make "newPage" the currently displayed page:
        pager.setCurrentItem (pageIndex, true);
    }

    //-----------------------------------------------------------------------------
    // Here's what the app should do to remove a view from the ViewPager.
    public void removeView (View defunctPage)
    {
        int pageIndex = pagerAdapter.removeView (pager, defunctPage);
        // You might want to choose what page to display, if the current page was "defunctPage".
        if (pageIndex == pagerAdapter.getCount())
            pageIndex--;
        pager.setCurrentItem (pageIndex);
    }

    //-----------------------------------------------------------------------------
    // Here's what the app should do to get the currently displayed page.
    public View getCurrentPage ()
    {
        return pagerAdapter.getView (pager.getCurrentItem());
    }

    //-----------------------------------------------------------------------------
    // Here's what the app should do to set the currently displayed page.  "pageToShow" must
    // currently be in the adapter, or this will crash.
    public void setCurrentPage (View pageToShow)
    {
        pager.setCurrentItem (pagerAdapter.getItemPosition (pageToShow), true);
    }


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
            tabLayout.addTab(tabLayout.newTab().setIcon(R.mipmap.icon_menu_point));
        }
    }

    /* 이벤트 코드는 여기서 */
}