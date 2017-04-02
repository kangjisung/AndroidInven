package com.example.kangjisung.likeroom.FragmentStamp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kangjisung.likeroom.DefineManager;
import com.example.kangjisung.likeroom.R;

import static com.example.kangjisung.likeroom.DefineManager.standardMileage;
import static com.example.kangjisung.likeroom.DefineManager.synchronizedLocalAndServerDatabase;

public class FragmentStampMain extends Fragment {
    //스탬프 현황 레이아웃을 그려야할 것
    //또한 스탬프 사용시 팝업도 띄워야 함
    //스탬프 레이아웃에서 선택된 매장에서 현제 나의 스탬프 상황을 봄

    View stampLayout;
    Button btnShowSpecialStamp;
    String[] selectedShopInfoData;
    TextView txtShopPhoneNumber, txtShopName;
    StampPagerAdapter pagerAdapter;

    //나중에 갯수 수정
    int numOfStamp = 35;
    String cardMode = "NORMAL";

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

        numOfStamp = synchronizedLocalAndServerDatabase.GetMileageStatusFromTargetStore(1);
        if(numOfStamp < 0) {
            numOfStamp = 0;
        }
        else {
            numOfStamp = numOfStamp / (standardMileage / 5);
        }

        //쿠폰<->스탬프 레이아웃을 전환하면서 나의 쿠폰과 스탬프 상태를 봄
        btnShowSpecialStamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View onClickView) {
                Button button = (Button)stampLayout.findViewById(R.id.btnShowSpecialStamp);
                if(cardMode == "NORMAL"){
                    /*stampLayout.findViewById(R.id.layout_normal).setVisibility(View.INVISIBLE);
                    stampLayout.findViewById(R.id.layout_event).setVisibility(View.VISIBLE);
                    cardMode = "EVENT";
                    button.setText("포인트 스탬프 카드 보기");*/
                    Snackbar.make(stampLayout, "기능 준비중입니다", 20000).setAction("확인", new View.OnClickListener()
                    {
                        @Override
                        public void onClick(View v)
                        {

                        }
                    }).show();
                }
                else if(cardMode == "EVENT"){
                    stampLayout.findViewById(R.id.layout_normal).setVisibility(View.VISIBLE);
                    stampLayout.findViewById(R.id.layout_event).setVisibility(View.INVISIBLE);
                    cardMode = "NORMAL";
                    button.setText("이벤트 스탬프 카드 보기");
                }
            }
        });
        View p = stampLayout.findViewById(R.id.textView2);
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //numOfStamp = 48;
                //initializeLayout("NORMAL");
            }
        });

        CheckTypesTask task = new CheckTypesTask();
        task.execute();
        return stampLayout;
    }

    public void initializeLayout(String mode)
    {
        RelativeLayout layout;
        Button buttonStampLeft;
        Button buttonStampRight;
        final ViewPager viewPager;
        final TabLayout tabLayout;

        switch(mode){
            default:
            case "NORMAL":
                layout = (RelativeLayout)stampLayout.findViewById(R.id.layout_normal);
                pagerAdapter = new StampPagerAdapter(getActivity(), numOfStamp);
                break;
            case "EVENT":
                layout = (RelativeLayout)stampLayout.findViewById(R.id.layout_event);
                pagerAdapter = new StampPagerAdapter(getActivity());
                break;
        }
        viewPager = (ViewPager)layout.findViewById(R.id.viewPager);
        viewPager.setAdapter(pagerAdapter);

        tabLayout = (TabLayout)layout.findViewById(R.id.tabLayout);
        tabLayout.removeAllTabs();
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setOffscreenPageLimit(pagerAdapter.getCount());
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
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        tabLayout.getTabAt(0).select();

        buttonStampLeft = (Button)layout.findViewById(R.id.button_stamp_left);
        buttonStampLeft.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                int nowTab = tabLayout.getSelectedTabPosition();
                if(nowTab > 0){
                    viewPager.setCurrentItem(nowTab-1);
                }
            }
        });
        buttonStampRight = (Button)layout.findViewById(R.id.button_stamp_right);
        buttonStampRight.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                int nowTab = tabLayout.getSelectedTabPosition();
                if(nowTab < tabLayout.getTabCount()-1){
                    viewPager.setCurrentItem(nowTab+1);
                }
            }
        });
    }

    public void tabLayoutInitialize(TabLayout tabLayout, int numOfPage)
    {
        for (int i = 0; i < numOfPage; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            tab.setCustomView(R.layout.include_tabitem_circle);
            tab.getCustomView().findViewById((i==0)?(R.id.icon_unselected):(R.id.icon_selected)).setVisibility(View.INVISIBLE);
            tabLayout.addTab(tab);
        }
    }

    private class CheckTypesTask extends AsyncTask<Void, Void, Void>
    {
        ProgressDialog asyncDialog = new ProgressDialog(getActivity());

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("로딩중입니다..");

            // show dialog
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {


            initializeLayout("NORMAL");
            initializeLayout("EVENT");

            asyncDialog.dismiss();
            super.onPostExecute(result);
        }
    }
}