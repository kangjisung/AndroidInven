package com.example.kangjisung.likeroom.Setting;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kangjisung.likeroom.FragmentUser.ListView.UserNoticeListItem;
import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.Util.ColorTheme;
import com.example.kangjisung.likeroom.Util.SharedPreferenceManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class SettingThemeListAdapter extends BaseAdapter {
    public class ThemeListItem {
        private int id;
        private String name;
        private String color;

        ThemeListItem() {}
        ThemeListItem(int id, String name, String color)
        {
            this.id = id;
            this.name = name;
            this.color = color;
        }

        public void setId(int id) {this.id = id;}
        public void setName(String name) {this.name = name;}
        public void setColor(String color) {this.color = color;}

        public int getId() {return id;}
        public String getName() {return name;}
        public String getColor() {return color;}
    };

    private ArrayList<ThemeListItem> mThemeListItem = new ArrayList<ThemeListItem>();
    private SharedPreferenceManager mSharedPreferenceManager = new SharedPreferenceManager();
    private Context context;
    private int p;

    public SettingThemeListAdapter() {
        super();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.setting_theme_listitem, parent, false);
        }

        ImageView mImageViewColor = (ImageView) convertView.findViewById(R.id.iv_color);
        ImageView mImageViewSelect = (ImageView) convertView.findViewById(R.id.iv_select);
        TextView mTextViewName = (TextView) convertView.findViewById(R.id.tv_name);

        mImageViewColor.setBackgroundColor(Color.parseColor(mThemeListItem.get(position).getColor()));
        if (mSharedPreferenceManager.getInt("theme", context) == mThemeListItem.get(position).getId()) {
            mImageViewSelect.setVisibility(View.VISIBLE);
        }
        else {
            mImageViewSelect.setVisibility(View.INVISIBLE);
        }
        mTextViewName.setText(mThemeListItem.get(position).getName());

        return convertView;
    }

    @Override
    public int getCount() {
        return mThemeListItem.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return mThemeListItem.get(position);
    }

    public void addItem(int id, String name, String color){
        ThemeListItem addListItem = new ThemeListItem();

        addListItem.setId(id);
        addListItem.setName(name);
        addListItem.setColor(color);

        mThemeListItem.add(addListItem);
    }

    public int getThemeId(int position){
        return mThemeListItem.get(position).getId();
    }
}