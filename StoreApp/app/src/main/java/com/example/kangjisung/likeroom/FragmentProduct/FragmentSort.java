package com.example.kangjisung.likeroom.FragmentProduct;

import android.content.DialogInterface;
import android.media.projection.MediaProjection;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.kangjisung.likeroom.FragmentProduct.ListView.FragmentSortRecyclerViewAdapter;
import com.example.kangjisung.likeroom.FragmentProduct.ListView.ProductListItem;
import com.example.kangjisung.likeroom.FragmentUser.UserEditDialog;
import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.Util.FirstPageFragmentListener;
import com.example.kangjisung.likeroom.Util.Utility;

public class FragmentSort extends Fragment implements View.OnClickListener{
    public static FirstPageFragmentListener firstPageListener;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int sortingStatus;
    private Button btName,btRegister,btModify;
    public FragmentSort() {
    }
    public FragmentSort(FirstPageFragmentListener listener) {
        firstPageListener=listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_sort, container, false);
        recyclerView=(RecyclerView)rootView.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(mLayoutManager);
        registerForContextMenu(recyclerView);

        mAdapter = new FragmentSortRecyclerViewAdapter(getContext());

        recyclerView.setAdapter(mAdapter);

        ProductObjManager.addRecyclerView(recyclerView,mAdapter);
        sortingStatus=ProductObjManager.sort();

        btModify=(Button)rootView.findViewById(R.id.buttonSortByModify);
        btName=(Button)rootView.findViewById(R.id.buttonSortByName);
        btRegister=(Button)rootView.findViewById(R.id.buttonSortByRegister);

        btModify.setOnClickListener(this);
        btName.setOnClickListener(this);
        btRegister.setOnClickListener(this);
        return rootView;
    }

    public static FragmentSort createInstance(FirstPageFragmentListener listener){
        FragmentSort fragmentSort=new FragmentSort();
        fragmentSort.firstPageListener=listener;
        return fragmentSort;
    }

    @Override
    public void onClick(View view) {
        if(view==btName){
            ProductObjManager.sortByName();
        }
        else if(view==btModify){
            //일일판매량 갱신날짜순
            ProductObjManager.sortByModifySellToday();
            //최적재고량 갱신날짜순
            //ProductObjManager.sortByModifyMuchStore();
        }
        else if(view==btRegister){
            ProductObjManager.sortByModifySellToday();
        }
    }
}
