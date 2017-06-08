package com.example.kangjisung.likeroom.inventory.statistics.ChangeStat;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.kangjisung.likeroom.inventory.statistics.ChangeStat.Graph.Graph1;
import com.example.kangjisung.likeroom.inventory.statistics.ChangeStat.Graph.Graph2;
import com.example.kangjisung.likeroom.inventory.statistics.ChangeStat.Graph.Graph3;
import com.example.kangjisung.likeroom.inventory.statistics.ChangeStat.Graph.Graph4;

public class CustomAdapter extends FragmentStatePagerAdapter {

    public CustomAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new Graph1();
        else if(position==1)
            return new Graph3();
        else {
            return new Graph4();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
