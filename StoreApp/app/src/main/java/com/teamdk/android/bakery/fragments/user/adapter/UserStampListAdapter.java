package com.teamdk.android.bakery.fragments.user.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.teamdk.android.bakery.R;
import com.teamdk.android.bakery.utility.ColorTheme;

import java.util.ArrayList;

public class UserStampListAdapter extends BaseAdapter {
    private ArrayList<Integer> userStampListItem;

    public UserStampListAdapter(ArrayList<Integer> userStampListItem) {
        super();

        this.userStampListItem = userStampListItem;
    }

    @Override
    public int getCount() {
        return userStampListItem.size() ;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent)
    {
        final Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.user_stamp_listitem, parent, false);
        }
        AppCompatImageView imageView = (AppCompatImageView)convertView.findViewById(R.id.icon);
        imageView.setBackgroundResource(userStampListItem.get(position));
        imageView.setSupportBackgroundTintList(ColorStateList.valueOf(ColorTheme.getThemeColorRGB(convertView.getContext(), R.attr.theme_color_N)));

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return userStampListItem.get(position) ;
    }
}