package com.teamdk.android.bakery.fragments.product;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ProductMainPagerAdapter extends FragmentStatePagerAdapter {

<<<<<<< HEAD:StoreApp/app/src/main/java/com/teamdk/android/bakery/fragments/product/ProductMainPagerAdapter.java
        public ProductMainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
=======
    public ProductMainPagerAdapter(FragmentManager fm) {
            super(fm);
        }

    @Override
>>>>>>> refs/remotes/origin/store-app-byeongmun:StoreApp/app/src/main/java/com/teamdk/android/bakery/fragments/product/ProductMainPagerAdapter.java
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