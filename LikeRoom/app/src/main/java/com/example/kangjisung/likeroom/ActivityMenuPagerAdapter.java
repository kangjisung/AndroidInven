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
                FragmentInfoMain shopInfoManager = new FragmentInfoMain();
                shopInfoManager.setArguments(dataTransferManager);
                return shopInfoManager;
            case 1:
                FragmentPointMain tab2 = new FragmentPointMain();
                return tab2;
            case 2:
                FragmentStampMain shopStampManager = new FragmentStampMain();
                shopStampManager.setArguments(dataTransferManager);
                return shopStampManager;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
