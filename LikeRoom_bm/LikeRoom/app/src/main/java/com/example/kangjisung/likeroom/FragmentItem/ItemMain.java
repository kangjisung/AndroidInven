package com.example.kangjisung.likeroom.FragmentItem;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kangjisung.likeroom.R;

public class ItemMain extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /* 초기화 코드는 여기서 */
        return inflater.inflate(R.layout.item_main, container, false);
    }

    /* 이벤트 코드는 여기서 */
}
/*
import com.example.kangjisung.likeroom.Garbage.UserNoticeRecyclerViewAdapter;
import com.example.kangjisung.likeroom.R;

import java.util.GregorianCalendar;

public class FragmentItemMain extends Fragment
{
    private View view;
    private RecyclerView noticeRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private UserNoticeRecyclerViewAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.item_main, container, false);

        mAdapter = new UserNoticeRecyclerViewAdapter();
        mLayoutManager = new LinearLayoutManager(getActivity());

        noticeRecyclerView = (RecyclerView)view.findViewById((R.id.recyclerView));
        noticeRecyclerView.setAdapter(mAdapter);
        noticeRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter.addItem("제목1", "내용1", new GregorianCalendar(2016, 1, 1), new GregorianCalendar(2016, 12,30), 1);
        mAdapter.addItem("제목2", "내용2", new GregorianCalendar(2015, 1, 1), new GregorianCalendar(2015, 12,30), 2);
        mAdapter.addItem("제목3", "내용3", new GregorianCalendar(2014, 1, 1), new GregorianCalendar(2014, 12,30), 3);

        return view;
    }
}
*/