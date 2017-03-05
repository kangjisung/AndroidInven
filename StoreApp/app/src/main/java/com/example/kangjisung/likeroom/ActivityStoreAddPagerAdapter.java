package com.example.kangjisung.likeroom;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kangjisung.likeroom.FragmentUser.ListView.UserStampListAdapter;
import com.example.kangjisung.likeroom.Util.ColorTheme;
import com.example.kangjisung.likeroom.Util.NoScrollViewPager;

import java.util.ArrayList;

public class ActivityStoreAddPagerAdapter extends PagerAdapter
{
    private LayoutInflater mInflater;
    private Context context;
    private int countPage;
    private boolean isConfirm;
    private int pageList[] = {
            R.id.layout_page1,
            R.id.layout_page2};

    ActivityStoreAddPagerAdapter(Context context){
        super();
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.countPage = pageList.length;
    }

    @Override
    public int getCount(){
        return countPage;
    }

    @Override
    public Object instantiateItem(final View pager, int position) {
        View view = mInflater.inflate(R.layout.activity_store_add_page, null);

        for(int page = 0; page < countPage; page++) {
            (view.findViewById(pageList[page])).setVisibility((position == page)?(View.VISIBLE):(View.INVISIBLE));
        }

        switch(position){
            case 0:
                Button mButtonStart = (Button) view.findViewById(R.id.btn_start);
                mButtonStart.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View onClickView) {
                        ((NoScrollViewPager)pager).setCurrentItem(1);
                    }
                });
                break;
            case 1:
                Button mButtonComplete = (Button) view.findViewById(R.id.btn_complete);
                mButtonComplete.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View onClickView) {
                        // TODO : 여기서 예외처리
                        isConfirm = true;
                        if(isConfirm == true) {
                            // TODO : 여기서 DB에 입력
                            AppCompatActivity activity = (AppCompatActivity) context;
                            Intent intent = new Intent(activity, ActivityMenu.class);
                            activity.startActivity(intent);
                            activity.finish();
                        }
                        else{

                        }
                    }
                });
                break;
            default:
                break;
        }

        ((NoScrollViewPager)pager).addView(view);

        return view;
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