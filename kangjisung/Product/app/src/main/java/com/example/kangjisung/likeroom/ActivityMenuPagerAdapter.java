package com.example.kangjisung.likeroom;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.kangjisung.likeroom.Fragment.FragmentSort;
import com.example.kangjisung.likeroom.FragmentItem.FragmentItemMain;
import com.example.kangjisung.likeroom.FragmentPoint.FragmentPointMain;
import com.example.kangjisung.likeroom.FragmentUser.FragmentUserMain;
import com.example.kangjisung.likeroom.Util.FirstPageFragmentListener;

public class ActivityMenuPagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;
        FragmentManager fm;
        public Fragment fragmentFirst;
        public ActivityMenuPagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.fm=fm;
            this.mNumOfTabs = NumOfTabs;
        }

    @Override
    public Fragment getItem(int position)
    {
        switch (position) {
            case 0:
               if(fragmentFirst==null){
                   fragmentFirst=new FragmentItemMain(listener);
               }
                return fragmentFirst;
            case 1:
                FragmentUserMain tab2 = new FragmentUserMain();
                return tab2;
            case 2:
                FragmentPointMain tab3 = new FragmentPointMain();
                return tab3;
            default:
                return null;
        }
    }
    FirstPageListener listener = new FirstPageListener();
    private final class FirstPageListener implements
            FirstPageFragmentListener {
        public void onSwitchToNextFragment() {
            fm.beginTransaction().remove(fragmentFirst)
                    .commit();
            if (fragmentFirst instanceof FragmentItemMain){
                fragmentFirst = FragmentSort.createInstance(listener);
            }else{
               fragmentFirst = FragmentItemMain.createInstance(listener);
            }
            notifyDataSetChanged();
        }
    }
    @Override
    public int getCount() {
        return mNumOfTabs;
    }
    @Override
    public int getItemPosition(Object object)
    {
        if (object instanceof FragmentItemMain &&
                fragmentFirst instanceof FragmentSort) {
            return POSITION_NONE;
        }
        if (object instanceof FragmentSort &&
                fragmentFirst instanceof FragmentItemMain) {
            return POSITION_NONE;
        }
        return POSITION_UNCHANGED;
    }

}