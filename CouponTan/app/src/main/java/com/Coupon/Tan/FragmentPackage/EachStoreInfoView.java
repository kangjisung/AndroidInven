package com.Coupon.Tan.FragmentPackage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Coupon.Tan.R;

//import ex14.stories2.com.ex14.R;

/**
 * Created by stories2 on 2016. 12. 6..
 */

public class EachStoreInfoView extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View eachStoreInfoLayout = null;
        try {
            eachStoreInfoLayout = inflater.inflate(R.layout.layout_each_store_info_view, container, false);
        }
        catch (Exception err) {
            Log.d(getString(R.string.app_name), "Error in onCreateView: " + err.getMessage());
        }
        return eachStoreInfoLayout;//super.onCreateView(inflater, container, savedInstanceState);
    }
}
