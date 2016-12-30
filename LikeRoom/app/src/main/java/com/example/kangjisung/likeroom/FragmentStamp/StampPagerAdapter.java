package com.example.kangjisung.likeroom.FragmentStamp;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.kangjisung.likeroom.R;

import java.util.ArrayList;
import java.util.List;

/**
 * PagerAdapter
 */
public class StampPagerAdapter extends PagerAdapter
{
    private LayoutInflater mInflater;
    private ArrayList<Integer> stampNumList;
    private int numOfStamp;
    private int numOfPage;

    public StampPagerAdapter(Context c, int _numOfStamp){
        super();
        mInflater = LayoutInflater.from(c);
        numOfStamp = _numOfStamp;
        numOfPage = (numOfStamp > 0)? ((numOfStamp - 1) / 10 + 1):(1);
        stampNumList = new ArrayList<Integer>();

        int nowStamp;

        for(int p = 0; p< numOfPage; p++)
        {
            if((p + 1) * 10 < numOfStamp){
                nowStamp = 10;
            }
            else {
                nowStamp = numOfStamp % 10;
            }
            stampNumList.add(p, nowStamp);
        }
    }

    @Override
    public int getCount() {
        return numOfPage;
    }

    @Override
    public Object instantiateItem(View pager, int position) {
        View v = null;
        v = mInflater.inflate(R.layout.stamp_page, null);

        TextView v1 = (TextView)v.findViewById(R.id.textViewTemp1);
        TextView v2 = (TextView)v.findViewById(R.id.textViewTemp2);
        v1.setText("페이지 번호 : " + position);
        v2.setText("이 페이지의 스탬프 갯수 : " + stampNumList.get(position));

        ((ViewPager)pager).addView(v, 0);

        return v;
    }

    @Override
    public void destroyItem(View pager, int position, Object view) {
        ((ViewPager)pager).removeView((View)view);
    }

    @Override
    public boolean isViewFromObject(View pager, Object obj) {
        return pager == obj;
    }

    @Override public void restoreState(Parcelable arg0, ClassLoader arg1) {}
    @Override public Parcelable saveState() { return null; }
    @Override public void startUpdate(View arg0) {}
    @Override public void finishUpdate(View arg0) {}
}