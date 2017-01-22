package com.example.kangjisung.likeroom.FragmentUser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.kangjisung.likeroom.R;

import java.util.GregorianCalendar;

public class UserNoticeMain extends Fragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.user_notice_main, container, false);

        UserNoticeListAdapter mAdapter = new UserNoticeListAdapter();

        ListView listView = (ListView)view.findViewById(R.id.listView);
        listView.setAdapter(mAdapter);

        mAdapter.addItem("제목1", "내용1", new GregorianCalendar(2016, 0, 1), new GregorianCalendar(2016, 11, 25), 1);
        mAdapter.addItem("제목2", "내용2", new GregorianCalendar(2015, 1, 1), new GregorianCalendar(2015, 11, 25), 2);
        mAdapter.addItem("제목3", "내용3", new GregorianCalendar(2014, 2, 1), new GregorianCalendar(2014, 11, 25), 3);
        mAdapter.addItem("제목4", "내용4", new GregorianCalendar(2013, 3, 1), new GregorianCalendar(2013, 11, 25), 1);

        listViewHeightSet(mAdapter, listView);

        Button buttonBack = (Button)view.findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });

        return view;
    }

    public void listViewHeightSet(BaseAdapter listAdapter, ListView listView){
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++){
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}