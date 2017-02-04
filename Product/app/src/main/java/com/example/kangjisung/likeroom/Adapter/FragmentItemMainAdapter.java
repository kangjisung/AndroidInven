package com.example.kangjisung.likeroom.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.kangjisung.likeroom.Fragment.FragmentMuchStore;
import com.example.kangjisung.likeroom.Fragment.FragmentSellToday;
import com.example.kangjisung.likeroom.FragmentItem.FragmentItemMain;
import com.example.kangjisung.likeroom.FragmentPoint.FragmentPointMain;
import com.example.kangjisung.likeroom.FragmentUser.FragmentUserMain;

public class FragmentItemMainAdapter extends FragmentStatePagerAdapter {

        public FragmentItemMainAdapter(FragmentManager fm) {
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