package com.teamdk.android.bakery.fragments.product;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.teamdk.android.bakery.fragments.product.adapter.FragmentMuchStoreRecyclerViewAdapter;
import com.teamdk.android.bakery.R;

public class FragmentMuchStore extends Fragment {
    public static RecyclerView rvFragmentMuchStore;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    boolean flag=true;
    int sortingStatus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_much_store, container, false);
        /* 초기화 코드는 여기서 */
        rvFragmentMuchStore=(RecyclerView)rootView.findViewById(R.id.rv_fragment_much_store);
        mLayoutManager = new LinearLayoutManager(rootView.getContext());
        rvFragmentMuchStore.setLayoutManager(mLayoutManager);

        mAdapter = new FragmentMuchStoreRecyclerViewAdapter(getContext());
        rvFragmentMuchStore.setAdapter(mAdapter);
        rvFragmentMuchStore.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                System.out.println(""+ ProductMain.noScrollViewPager.getCurrentItem());
                if(ProductMain.noScrollViewPager.getCurrentItem()==1) {
                    FragmentSellToday.rvFragmentSellToday.scrollBy(0, dy);
                }
            }
        });

        return rootView;
    }
}