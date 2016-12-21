package com.example.kangjisung.likeroom;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

public class ActivityMenu extends AppCompatActivity
{
    int MAX_PAGE=3;
    Fragment cur_fragment=new Fragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager())
        {
            @Override
            public Fragment getItem(int position) {
                if(position<0 || MAX_PAGE<=position)
                    return null;
                switch (position){
                    case 0:
                        cur_fragment=new FragmentItemMain();
                        break;

                    case 1:
                        cur_fragment=new FragmentUserMain();
                        break;

                    case 2:
                        cur_fragment=new FragmentPointMain();
                        break;
                }

                return cur_fragment;
            }

            @Override
            public int getCount() {
                return 0;
            }
        });
    }
}