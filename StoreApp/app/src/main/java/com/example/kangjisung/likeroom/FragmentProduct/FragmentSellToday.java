package com.example.kangjisung.likeroom.FragmentProduct;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.kangjisung.likeroom.FragmentProduct.Adapter.FragmentSellTodayRecyclerViewAdapter;
import com.example.kangjisung.likeroom.ObjectManager.ProductObjManager;
import com.example.kangjisung.likeroom.R;

public class FragmentSellToday extends Fragment {
    public static RecyclerView rvFragmentSellToday;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    public LinearLayout linearLayout;
    int sortingStatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sell_today, container, false);

        rvFragmentSellToday = (RecyclerView) rootView.findViewById(R.id.rv_fragment_sell_today);
        mLayoutManager = new LinearLayoutManager(rootView.getContext());
        rvFragmentSellToday.setLayoutManager(mLayoutManager);


        mAdapter = new FragmentSellTodayRecyclerViewAdapter(getContext());

        rvFragmentSellToday.setAdapter(mAdapter);

        rvFragmentSellToday.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (ProductMain.noScrollViewPager.getCurrentItem() == 0)
                    FragmentMuchStore.rvFragmentMuchStore.scrollBy(0, dy);
            }
        });
        ProductObjManager.addRecyclerView(rvFragmentSellToday, mAdapter);
        sortingStatus = ProductObjManager.sort();
        return rootView;
    }
}