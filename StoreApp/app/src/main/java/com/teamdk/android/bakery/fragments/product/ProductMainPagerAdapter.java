package com.teamdk.android.bakery.fragments.product;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ProductMainPagerAdapter extends FragmentStatePagerAdapter {

        public ProductMainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
    public Fragment getItem(int position)
    {
        switch (position) {
            case 0:
                return new FragmentSellToday();
            case 1:
                return new FragmentMuchStore();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}