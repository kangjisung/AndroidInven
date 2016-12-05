package com.Coupon.Tan.CustomStampView;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.Coupon.Tan.R;

import java.util.List;

//import ex14.stories2.com.ex14.R;

/**
 * Created by stories2 on 2016. 11. 28..
 */

public class CustomStampViewAdapter extends BaseAdapter {
    LayoutInflater layoutInflater;
    List<EachStampViewItem> listOfMyTargetStoresStamp;
    Context context;

    @Override
    public int getCount() {
        return listOfMyTargetStoresStamp.size();
    }

    @Override
    public Object getItem(int i) {
        int targetRowPosition = i;
        return listOfMyTargetStoresStamp.get(targetRowPosition);
    }

    @Override
    public long getItemId(int i) {
        int targetRowPosition = i;
        return targetRowPosition;
    }

    public void AddNewMyStamp(Drawable myStampIcon, String myStampSubTitle) {
        EachStampViewItem eachStampViewItem = new EachStampViewItem();
        eachStampViewItem.SetEachStampIcon(myStampIcon);
        eachStampViewItem.SetEachStampSubTitle(myStampSubTitle);

        listOfMyTargetStoresStamp.add(eachStampViewItem);

    }

    public void DeleteMyStamp(int targetRowPosition) {
        listOfMyTargetStoresStamp.remove(targetRowPosition);
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        int viewPosition = i;
        Context viewContext = viewGroup.getContext();

        if(view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) viewContext.getSystemService(viewContext.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.my_stamp_item, viewGroup, false);
        }

        ImageView myStampImage = (ImageView)view.findViewById(R.id.stampIcon);
        TextView myStampSubText = (TextView)view.findViewById(R.id.txtEachIconSubTitle);

        EachStampViewItem eachStampViewItem = listOfMyTargetStoresStamp.get(viewPosition);

        myStampImage.setImageDrawable(eachStampViewItem.GetEachStampIcon());
        myStampSubText.setText(eachStampViewItem.GetEachStampSubTitle());

        return view;
    }

    public CustomStampViewAdapter(Context context, List<EachStampViewItem> listOfMyTargetStoresStamp) {
        super();

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = context;
        this.listOfMyTargetStoresStamp = listOfMyTargetStoresStamp;


    }

    @Override
    public boolean hasStableIds() {
        return super.hasStableIds();
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        super.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        super.unregisterDataSetObserver(observer);
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    @Override
    public void notifyDataSetInvalidated() {
        super.notifyDataSetInvalidated();
    }

    @Override
    public boolean areAllItemsEnabled() {
        return super.areAllItemsEnabled();
    }

    @Override
    public boolean isEnabled(int position) {
        return super.isEnabled(position);
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return super.getDropDownView(position, convertView, parent);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount();
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }
}
