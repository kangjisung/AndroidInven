package com.Coupon.Tan.FragmentPackage;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.Coupon.Tan.R;

/**
 * Created by stories2 on 2016. 11. 28..
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    PlaceholderFragment placeholderFragment;
    Context appContext;
    int numberOfTabs;

    public SectionsPagerAdapter(FragmentManager fm, PlaceholderFragment placeholderFragment, Context appContext) {
        super(fm);
        this.placeholderFragment = placeholderFragment;
        this.appContext = appContext;

        numberOfTabs = 3;
        // Show 3 total pages.
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).

        //탭의 번호당 어떤 레이아웃을 그려낼지에 대한 내용을 여기서 정함
        //원하는 탭 번호에 fragment를 상속받는 클래스를 리턴해주면 됨

        switch (position) {
            case 0:
                return new EachStoreStampStatusView();
            case 1:
                return new EachStoreNoticeListView();
            case 2:
                return new EachStoreInfoView();
        }
        return null;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        return super.instantiateItem(container, position);
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        //뭐가 연결됬는지 모르겠으면 해당 구문에 마우스 오른쪽 클릭해서 정의로 이동해 볼것
        switch (position) {
            case 0:
                return appContext.getString(R.string.eachStoreTabNameAboutMyStampStatus);
            case 1:
                return appContext.getString(R.string.eachStoreTabNameAboutStoreNotice);
            case 2:
                return appContext.getString(R.string.eachStoreTabNameAboutStoreInfo);
        }
        return null;
    }
}
