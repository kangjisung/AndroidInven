package com.example.kangjisung.likeroom;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.kangjisung.likeroom.FragmentInfo.FragmentInfoMain;
import com.example.kangjisung.likeroom.FragmentNotice.FragmentNoticeMain;
import com.example.kangjisung.likeroom.FragmentStamp.FragmentStampMain;


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