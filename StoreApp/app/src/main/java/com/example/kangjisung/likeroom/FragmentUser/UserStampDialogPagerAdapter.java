package com.example.kangjisung.likeroom.FragmentUser;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.os.Parcelable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.Util.ColorTheme;

import java.util.ArrayList;

public class UserStampDialogPagerAdapter extends PagerAdapter
{
    private LayoutInflater mInflater;
    private Context context;
    private int pageCount;
    private View selectedView;

    private UserStampListAdapter mAdapter;
    private GridView gridView;

    private ArrayList<Integer> userStampListItem;
    private int selectedItem = 0;

    UserStampDialogPagerAdapter(Context context, int numOfPage){
        super();
        mInflater = LayoutInflater.from(context);
        this.context = context;
        this.pageCount = numOfPage;

        userStampListItem = new ArrayList<Integer>() ;
        userStampListItem.add(R.mipmap.icon_menu_item);
        userStampListItem.add(R.mipmap.icon_menu_point);
        userStampListItem.add(R.mipmap.icon_menu_user);
        userStampListItem.add(R.mipmap.icon_floating_stamp);
    }

    @Override
    public int getCount(){
        return pageCount;
    }

    public int getSelectedItem(){
        return userStampListItem.get(0);
    }

    @Override
    public Object instantiateItem(View pager, int position) {
        View view;

        switch(position){
            default:
            case 0:
                view = mInflater.inflate(R.layout.user_stamp_dialog_page1, null);
                view.findViewById(R.id.layout_background).getBackground().setColorFilter(ColorTheme.getThemeColorRGB(context, R.attr.theme_color_L3), PorterDuff.Mode.SRC_IN);
                mAdapter = new UserStampListAdapter(userStampListItem);
                gridView = (GridView)view.findViewById(R.id.gridView);
                gridView.setAdapter(mAdapter);
                gridView.setOnItemClickListener(new GridView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View onClickView, int position, long id) {
                        RelativeLayout parentView = (RelativeLayout)parent.getParent().getParent().getParent().getParent().getParent();
                        AppCompatImageView imageViewEmblem = (AppCompatImageView)parentView.findViewById(R.id.imageView_stamp);
                        imageViewEmblem.setBackgroundResource((Integer)mAdapter.getItem(position));
                        imageViewEmblem.setSupportBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(context, R.color.white)));
                        if (selectedView != null){
                            selectedView.setBackgroundColor(ContextCompat.getColor(context, R.color.transparent));
                        }
                        selectedView = onClickView.findViewById(R.id.view_select);
                        selectedView.setBackgroundColor(ContextCompat.getColor(context, R.color.alpha40));
                        selectedItem = position;
                    }
                });
                break;
            case 1:
                view = mInflater.inflate(R.layout.user_stamp_dialog_page2, null);
                view.findViewById(R.id.layout_background).getBackground().setColorFilter(ColorTheme.getThemeColorRGB(context, R.attr.theme_color_L3), PorterDuff.Mode.SRC_IN);
                break;
            case 2:
                view = mInflater.inflate(R.layout.user_stamp_dialog_page3, null);
                view.findViewById(R.id.layout_background).getBackground().setColorFilter(ColorTheme.getThemeColorRGB(context, R.attr.theme_color_L3), PorterDuff.Mode.SRC_IN);
                break;
        }

        ((ViewPager)pager).addView(view);

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