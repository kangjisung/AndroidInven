package com.teamdk.android.bakery;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.teamdk.android.bakery.fragments.product.ProductMain;
import com.teamdk.android.bakery.fragments.point.PointMain;
import com.teamdk.android.bakery.fragments.user.UserMain;

public class ActivityMenuPagerAdapter extends FragmentPagerAdapter
{
    private int mNumOfTabs;
    private Fragment mCurrentFragment;

    public ActivityMenuPagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    public Fragment getCurrentFragment() {
        return mCurrentFragment;
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
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            mCurrentFragment = ((Fragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}