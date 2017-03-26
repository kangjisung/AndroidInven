package com.teamdk.android.bakery.fragments.product;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamdk.android.bakery.R;
import com.teamdk.android.bakery.fragments.product.adapter.ProductMainListAdapter;
import com.teamdk.android.bakery.utility.Interfaces;
import com.teamdk.android.bakery.objectmanager.ProductObjectManager;

public class FragmentMuchStore extends Fragment {
    private RecyclerView rvFragmentMuchStore;
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

        mAdapter = new ProductMainListAdapter(getContext(), "MuchStore");
        mLayoutManager = new LinearLayoutManager(rootView.getContext());
        rvFragmentMuchStore = (RecyclerView) rootView.findViewById(R.id.rv_product);
        rvFragmentMuchStore.setLayoutManager(mLayoutManager);
        rvFragmentMuchStore.setAdapter(mAdapter);
        rvFragmentMuchStore.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if(((ViewPager)container).getCurrentItem() == 1){
                    mCallback.MuchStoreMoved(dx, dy);
                }
            }
        });
        ProductObjectManager.addRecyclerView(rvFragmentMuchStore, mAdapter);
        return rootView;
    }

    public void setScroll(int dx, int dy)
    {
        rvFragmentMuchStore.scrollBy(dx, dy);
    }
}