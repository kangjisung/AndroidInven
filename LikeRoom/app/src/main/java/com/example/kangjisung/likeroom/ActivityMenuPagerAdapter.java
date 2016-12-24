package com.example.kangjisung.likeroom;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.kangjisung.likeroom.FragmentInfo.FragmentInfoMain;
import com.example.kangjisung.likeroom.FragmentNotice.FragmentNoticeMain;
import com.example.kangjisung.likeroom.FragmentStamp.FragmentStampMain;

//ActivityMenu와 연결되어 있으며 실제 어플에서 맨 아래 하단 3개의 아이콘(스탬프,공지사항,매장정보)를
//각각 눌렀을 때 창을 이동시켜주는 부분을 담당한다.

public class ActivityMenuPagerAdapter extends FragmentStatePagerAdapter
{
    int mNumOfTabs;

    public ActivityMenuPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position)
    {
        switch (position) {
            case 0:
                FragmentStampMain tab1 = new FragmentStampMain();
                return tab1;
            case 1:
                FragmentNoticeMain tab2 = new FragmentNoticeMain();
                return tab2;
            case 2:
                FragmentInfoMain tab3 = new FragmentInfoMain();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}