package com.teamdk.android.bakery.fragments.product;

import android.support.v7.widget.RecyclerView;

public class RecyclerViewManager {
    RecyclerView recyclerView;
    RecyclerView.Adapter<RecyclerView.ViewHolder> recyclerViewAdapter;

    public RecyclerViewManager(RecyclerView recyclerView, RecyclerView.Adapter<RecyclerView.ViewHolder> recyclerViewAdapter) {
        this.recyclerView = recyclerView;
        this.recyclerViewAdapter = recyclerViewAdapter;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public RecyclerView.Adapter<RecyclerView.ViewHolder> getRecyclerViewAdapter() {
        return recyclerViewAdapter;
    }

    public void setRecyclerViewAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> recyclerViewAdapter) {
        this.recyclerViewAdapter = recyclerViewAdapter;
    }
}