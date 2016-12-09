package com.Coupon.Tan.FragmentPackage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.Coupon.Tan.CustomStampView.CustomStampViewAdapter;
import com.Coupon.Tan.CustomStampView.EachStampViewItem;
import com.Coupon.Tan.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by stories2 on 2016. 11. 30..
 */

public class EachStoreStampStatusView extends Fragment {
    int nowMileageStatus = 0, eachMileageStampMeasure = 100, eachStampViewColumnSize = 5;
    List<EachStampViewItem> eachStampViewItemList;
    GridView myStampStatusView;
    CustomStampViewAdapter customStampViewAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_each_store_stamp_status_view, container, false);

        eachStampViewItemList = new ArrayList<EachStampViewItem>();

        myStampStatusView = (GridView)view.findViewById(R.id.stampGridLayout);
        customStampViewAdapter = new CustomStampViewAdapter(getActivity().getApplicationContext(), eachStampViewItemList);
        myStampStatusView.setAdapter(customStampViewAdapter);

        //customStampViewAdapter.AddNewMyStamp(ContextCompat.getDrawable(getActivity(), R.mipmap.ic_launcher), "helloWorld");
        PrintNowMileageStamp(100, 0);

        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void PrintNowMileageStamp(int nowMileageStatus, int eachMileageStampMeasure) {
        int i;
        String stampTitle = "";
        for(i = 0; i < eachStampViewColumnSize * eachStampViewColumnSize; i += 1) {
            if(i % eachStampViewColumnSize == 4)
                stampTitle = "mileage change";
            else
                stampTitle = "";
            customStampViewAdapter.AddNewMyStamp(ContextCompat.getDrawable(getActivity(), R.mipmap.ic_launcher), stampTitle);
        }
    }
}
