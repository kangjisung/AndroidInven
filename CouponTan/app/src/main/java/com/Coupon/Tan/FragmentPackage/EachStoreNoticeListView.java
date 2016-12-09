package com.Coupon.Tan.FragmentPackage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.Coupon.Tan.CustomStoreListView.CustomStoreListViewAdapter;
import com.Coupon.Tan.CustomStoreListView.EachStoreListViewItem;
import com.Coupon.Tan.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stories2 on 2016. 11. 30..
 */

public class EachStoreNoticeListView extends Fragment {
    List<EachStoreListViewItem> eachStoreNoticeList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        try {

            view = inflater.inflate(R.layout.layout_each_store_notice_list_view, container, false);

            eachStoreNoticeList = new ArrayList<EachStoreListViewItem>();

            ListView myStoreNoticeList = (ListView) view.findViewById(R.id.listOfEachStoreNoti);
            CustomStoreListViewAdapter customNoticeListViewAdapter = new CustomStoreListViewAdapter();
            myStoreNoticeList.setAdapter(customNoticeListViewAdapter);
            customNoticeListViewAdapter.AddNewCustomStoreListItem(null, "notice1", "subNotice");
        }
        catch (Exception err) {
            Log.d("ex14", "Error in noticeOnCreateView: " + err.getMessage());
        }

        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}
