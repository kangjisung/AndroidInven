package com.example.kangjisung.likeroom;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ActivityMenuPagerAdapter extends FragmentStatePagerAdapter {
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
                FragmentItemMain tab1 = new FragmentItemMain();
                return tab1;
            case 1:
                FragmentPointMain tab2 = new FragmentPointMain();
                return tab2;
            case 2:
                FragmentUserMain tab3 = new FragmentUserMain();
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