package com.teamdk.android.bakery.fragments.product;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.teamdk.android.bakery.fragments.product.adapter.FragmentSellTodayRecyclerViewAdapter;
import com.teamdk.android.bakery.R;
<<<<<<< HEAD:StoreApp/app/src/main/java/com/teamdk/android/bakery/fragments/product/FragmentSellToday.java
=======
import com.teamdk.android.bakery.objectmanager.ProductObjectManager;
>>>>>>> refs/remotes/origin/store-app-byeongmun:StoreApp/app/src/main/java/com/teamdk/android/bakery/fragments/product/FragmentSellToday.java

public class FragmentSellToday extends Fragment {
    public static RecyclerView rvFragmentSellToday;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
<<<<<<< HEAD:StoreApp/app/src/main/java/com/teamdk/android/bakery/fragments/product/FragmentSellToday.java
    public LinearLayout linearLayout;
    int sortingStatus;
=======
>>>>>>> refs/remotes/origin/store-app-byeongmun:StoreApp/app/src/main/java/com/teamdk/android/bakery/fragments/product/FragmentSellToday.java

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_sell_today, container, false);

<<<<<<< HEAD:StoreApp/app/src/main/java/com/teamdk/android/bakery/fragments/product/FragmentSellToday.java
        rvFragmentSellToday = (RecyclerView) rootView.findViewById(R.id.rv_fragment_sell_today);
        mLayoutManager = new LinearLayoutManager(rootView.getContext());
        rvFragmentSellToday.setLayoutManager(mLayoutManager);

        mAdapter = new FragmentSellTodayRecyclerViewAdapter(getContext());

=======
        mAdapter = new FragmentSellTodayRecyclerViewAdapter(getContext());
        mLayoutManager = new LinearLayoutManager(rootView.getContext());
        rvFragmentSellToday = (RecyclerView) rootView.findViewById(R.id.rv_fragment_sell_today);
        rvFragmentSellToday.setLayoutManager(mLayoutManager);
>>>>>>> refs/remotes/origin/store-app-byeongmun:StoreApp/app/src/main/java/com/teamdk/android/bakery/fragments/product/FragmentSellToday.java
        rvFragmentSellToday.setAdapter(mAdapter);
        rvFragmentSellToday.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (ProductMain.noScrollViewPager.getCurrentItem() == 0)
                    FragmentMuchStore.rvFragmentMuchStore.scrollBy(0, dy);
            }
        });
<<<<<<< HEAD:StoreApp/app/src/main/java/com/teamdk/android/bakery/fragments/product/FragmentSellToday.java
=======
        ProductObjectManager.addRecyclerView(rvFragmentSellToday, mAdapter);
>>>>>>> refs/remotes/origin/store-app-byeongmun:StoreApp/app/src/main/java/com/teamdk/android/bakery/fragments/product/FragmentSellToday.java
        return rootView;
    }
}