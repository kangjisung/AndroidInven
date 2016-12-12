package com.Coupon.Tan.CustomStoreListView;

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

import java.util.ArrayList;

//import ex14.stories2.com.ex14.R;

/**
 * Created by stories2 on 2016. 11. 28..
 */

public class CustomStoreListViewAdapter extends BaseAdapter{

    ArrayList<EachStoreListViewItem> customStoreListView;

    public CustomStoreListViewAdapter() {
        super();

        customStoreListView = new ArrayList<EachStoreListViewItem>();
    }

    public void AddNewCustomStoreListItem(Drawable eachStoreIcon, String eachStoreTitle, String eachStoreSubTitle) {
        EachStoreListViewItem eachStoreListViewItem = new EachStoreListViewItem();

        eachStoreListViewItem.SetEachStoreIcon(eachStoreIcon);
        eachStoreListViewItem.SetEachStoreTitle(eachStoreTitle);
        eachStoreListViewItem.SetEachStoreSubTitle(eachStoreSubTitle);

        customStoreListView.add(eachStoreListViewItem);
        //customStoreListView.notifyAll();
    }

    public void AddNewCustomStoreListItem(Drawable eachStoreIcon, String eachStoreTitle, String eachStoreSubTitle, String eachStoreId) {
        EachStoreListViewItem eachStoreListViewItem = new EachStoreListViewItem();

        eachStoreListViewItem.SetEachStoreIcon(eachStoreIcon);
        eachStoreListViewItem.SetEachStoreTitle(eachStoreTitle);
        eachStoreListViewItem.SetEachStoreSubTitle(eachStoreSubTitle);
        eachStoreListViewItem.SetEachStoreId(eachStoreId);

        customStoreListView.add(eachStoreListViewItem);
        //customStoreListView.notifyAll();
    }

    public void DeleteTargetStoreListItem(int targetRowPosition) {
        customStoreListView.remove(targetRowPosition);
        //customStoreListView.notifyAll();
    }

    @Override
    public int getCount() {
        return customStoreListView.size();
    }

    @Override
    public Object getItem(int i) {
        int rowPosition = i;

        return customStoreListView.get(rowPosition);
    }

    @Override
    public long getItemId(int i) {
        int rowPosition = i;
        return rowPosition;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        int viewPosition = i;
        Context viewContext = viewGroup.getContext();

        if(view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) viewContext.getSystemService(viewContext.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.my_store_list_view_item, viewGroup, false);
        }

        ImageView storeIconView = (ImageView)view.findViewById(R.id.eachStorePicture);
        TextView eachStoreTitleText = (TextView)view.findViewById(R.id.txtOfStoreTitle);
        TextView eachStoreSubTitleText = (TextView)view.findViewById(R.id.txtOfStoreSubTitle);

        EachStoreListViewItem oneOfTheSoreCustomListItem = customStoreListView.get(viewPosition);

        storeIconView.setImageDrawable(oneOfTheSoreCustomListItem.GetEachStoreIcon());
        eachStoreTitleText.setText(oneOfTheSoreCustomListItem.GetEachStoreTitle());
        eachStoreSubTitleText.setText(oneOfTheSoreCustomListItem.GetEachStoreSubTitle());
        return view;
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
