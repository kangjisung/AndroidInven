package ex14.stories2.com.ex14.FragmentPackage;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import ex14.stories2.com.ex14.CustomStampView.CustomStampViewAdapter;
import ex14.stories2.com.ex14.CustomStampView.EachStampViewItem;
import ex14.stories2.com.ex14.R;

/**
 * Created by stories2 on 2016. 11. 30..
 */

public class EachStoreStampStatusView extends Fragment {
    List<EachStampViewItem> eachStampViewItemList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_each_store_stamp_status_view, container, false);

        eachStampViewItemList = new ArrayList<EachStampViewItem>();

        GridView myStampStatusView = (GridView)view.findViewById(R.id.stampGridLayout);
        CustomStampViewAdapter customStampViewAdapter = new CustomStampViewAdapter(getActivity().getApplicationContext(), eachStampViewItemList);
        myStampStatusView.setAdapter(customStampViewAdapter);
        customStampViewAdapter.AddNewMyStamp(ContextCompat.getDrawable(getActivity(), R.mipmap.ic_launcher), "helloWorld");

        return view;
        //return super.onCreateView(inflater, container, savedInstanceState);
    }
}
