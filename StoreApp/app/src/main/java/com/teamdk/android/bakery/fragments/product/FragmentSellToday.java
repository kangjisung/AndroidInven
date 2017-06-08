package com.teamdk.android.bakery.fragments.product;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamdk.android.bakery.fragments.product.adapter.ProductMainListAdapter;
import com.teamdk.android.bakery.R;
import com.teamdk.android.bakery.utility.Interfaces;
import com.teamdk.android.bakery.objectmanager.ProductObjectManager;

public class FragmentSellToday extends Fragment {
    private RecyclerView rvFragmentSellToday;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Interfaces mCallback;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.product_fragment, container, false);

        try {
            mCallback = (Interfaces) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement TextClicked");
        }

        mAdapter = new ProductMainListAdapter(getContext(), "SellToday");
        mLayoutManager = new LinearLayoutManager(rootView.getContext());
        rvFragmentSellToday = (RecyclerView) rootView.findViewById(R.id.rv_product);
        rvFragmentSellToday.setLayoutManager(mLayoutManager);
        rvFragmentSellToday.setAdapter(mAdapter);
        rvFragmentSellToday.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(((ViewPager)container).getCurrentItem() == 0){{
                    mCallback.SellTodayMoved(dx, dy);
                }}
            }
        });
        ProductObjectManager.addRecyclerView(rvFragmentSellToday, mAdapter);
        return rootView;
    }

    public void setScroll(int dx, int dy)
    {
        rvFragmentSellToday.scrollBy(dx, dy);
    }
}