package com.teamdk.android.bakery.inventory;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.teamdk.android.bakery.inventory.fragments.Fragment1;
import com.teamdk.android.bakery.inventory.fragments.Fragment2;
import com.teamdk.android.bakery.inventory.fragments.Fragment3;

public class InventoryMainPagerAdapter extends FragmentStatePagerAdapter {

    public InventoryMainPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new Fragment1();
        else if(position==1)
            return new Fragment2();
        else {
            return new Fragment3();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
