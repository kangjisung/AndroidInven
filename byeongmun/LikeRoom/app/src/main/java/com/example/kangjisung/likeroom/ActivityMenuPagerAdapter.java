package com.example.kangjisung.likeroom;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.kangjisung.likeroom.FragmentProduct.ProductMain;
import com.example.kangjisung.likeroom.FragmentPoint.PointMain;
import com.example.kangjisung.likeroom.FragmentUser.UserMain;

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
                PointMain tab1 = new PointMain();
                return tab1;
            case 1:
                UserMain tab2 = new UserMain();
                return tab2;
            case 2:
                ProductMain tab3 = new ProductMain();
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