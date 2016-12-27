package com.example.kangjisung.likeroom.FragmentStamp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class StampPagerAdapter extends FragmentStatePagerAdapter
{
    int mNumOfPage;
    ArrayList<StampPage> stampPageList;

    public StampPagerAdapter(FragmentManager fm, int mNumOfStamp) {
        super(fm);
        mNumOfPage = (mNumOfStamp > 0)?((mNumOfStamp - 1) / 10 + 1):(1);
        int nowStamp = 0;

        for(int p = 0; p< mNumOfPage; p++)
        {
            if((p + 1) * 10 < mNumOfStamp){
                nowStamp = 10;
            }
            else {
                nowStamp = mNumOfStamp % 10;
            }
            StampPage addPage = new StampPage();
            addPage.newInstance(nowStamp, p);
            stampPageList.add(addPage);
        }
    }

    @Override
    public Fragment getItem(int position)
    {
        return stampPageList.get(position);
    }

    @Override
    public int getCount() {
        return mNumOfPage;
    }
}