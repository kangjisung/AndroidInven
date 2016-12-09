package com.Coupon.Tan.FragmentPackage;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Coupon.Tan.R;

/**
 * Created by stories2 on 2016. 11. 28..
 */

public class PlaceholderFragment extends Fragment {

    //List<EachStampViewItem> eachStampViewItemList;
    //List<EachStoreListViewItem> eachStoreNoticeList;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */

    String logCatTag;
    private static final String ARG_SECTION_NUMBER = "section_number";

    public PlaceholderFragment() {

        //eachStampViewItemList = new ArrayList<EachStampViewItem>();
        //eachStoreNoticeList = new ArrayList<EachStoreListViewItem>();


    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public PlaceholderFragment newInstance(int sectionNumber) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = null;
        try {
            rootView = inflater.inflate(R.layout.fragment_coupon_tan_store_info, container, false);
            logCatTag = getString(R.string.app_name);
        }
        catch (Exception err) {
            Log.d(logCatTag, "Error in onCreateView: " + err.getMessage());
        }
        return rootView;
    }
}