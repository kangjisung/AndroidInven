package com.example.kangjisung.likeroom.Setting;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.Util.ColorTheme;
import com.example.kangjisung.likeroom.Util.SharedPreferenceManager;

import java.util.ArrayList;

public class SettingStartListAdapter extends BaseAdapter {
    private ArrayList<String> mStartListItem = new ArrayList<>();
    private SharedPreferenceManager mSharedPreferenceManager = new SharedPreferenceManager();
    private Context context;
    private Context appContext;

    public SettingStartListAdapter(Context appContext) {
        super();
        this.appContext = appContext;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.setting_listitem, parent, false);
        }

        RelativeLayout mLayoutTheme = (RelativeLayout) convertView.findViewById(R.id.layout_theme);
        ImageView mImageViewColor = (ImageView) convertView.findViewById(R.id.iv_color);
        ImageView mImageViewSelect = (ImageView) convertView.findViewById(R.id.iv_select);
        AppCompatImageView mImageViewCheck = (AppCompatImageView) convertView.findViewById(R.id.iv_check);
        TextView mTextViewName = (TextView) convertView.findViewById(R.id.tv_name);

        mLayoutTheme.setVisibility(View.GONE);

        mTextViewName.setText(mStartListItem.get(position));
        if(mSharedPreferenceManager.getInt("set_start", context) == position){
            mTextViewName.setTextColor(ColorTheme.getThemeColorRGB(appContext, R.attr.theme_color_type1));
            mImageViewCheck.setVisibility(View.VISIBLE);
        }
        else{
            mTextViewName.setTextColor(ContextCompat.getColor(appContext.getApplicationContext(), R.color.gray100));
            mImageViewCheck.setVisibility(View.INVISIBLE);
        }
        return convertView;
    }

    @Override
    public int getCount() {
        return mStartListItem.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return mStartListItem.get(position);
    }

    public void addItem(String name){
        mStartListItem.add(name);
    }
}