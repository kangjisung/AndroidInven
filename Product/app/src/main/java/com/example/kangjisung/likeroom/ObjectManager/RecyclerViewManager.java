package com.example.kangjisung.likeroom.ObjectManager;

import android.support.v7.widget.RecyclerView;

import com.example.kangjisung.likeroom.Adapter.FragmentSortRecyclerViewAdapter;

/**
 * Created by user on 2017-01-04.
 */

public class RecyclerViewManager {
    RecyclerView recyclerView;
    RecyclerView.Adapter<FragmentSortRecyclerViewAdapter.ViewHolder> recyclerViewAdapter;

    public RecyclerViewManager(RecyclerView recyclerView, RecyclerView.Adapter<FragmentSortRecyclerViewAdapter.ViewHolder> recyclerViewAdapter) {
        this.recyclerView = recyclerView;
        this.recyclerViewAdapter = recyclerViewAdapter;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public RecyclerView.Adapter<FragmentSortRecyclerViewAdapter.ViewHolder> getRecyclerViewAdapter() {
        return recyclerViewAdapter;
    }

    public void setRecyclerViewAdapter(RecyclerView.Adapter<FragmentSortRecyclerViewAdapter.ViewHolder> recyclerViewAdapter) {
        this.recyclerViewAdapter = recyclerViewAdapter;
    }
}
