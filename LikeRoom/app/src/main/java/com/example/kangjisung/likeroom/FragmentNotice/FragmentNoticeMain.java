package com.example.kangjisung.likeroom.FragmentNotice;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kangjisung.likeroom.DefineManager;
import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.SimpleDatabaseTest;

import java.util.ArrayList;
import java.util.Arrays;

import static com.example.kangjisung.likeroom.DefineManager.noticeBodySavedPoint;
import static com.example.kangjisung.likeroom.DefineManager.noticeCloseDateSavedPoint;
import static com.example.kangjisung.likeroom.DefineManager.noticeStartDateSavedPoint;
import static com.example.kangjisung.likeroom.DefineManager.noticeTitleSavedPoint;
import static com.example.kangjisung.likeroom.DefineManager.shopIdSavedPoint;
import static com.example.kangjisung.likeroom.DefineManager.synchronizedLocalAndServerDatabase;

public class FragmentNoticeMain extends Fragment {
//스탬프,공지사항,매장정보 중 공지사항 부분.
    private View view;
    private RecyclerView noticeRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private NoticeRecyclerViewAdapter mAdapter;
    String[] selectedShopInfoData;
    TextView txtStoreName;
    ImageView hamburgerMenu;
    ArrayList<String[]> noticeDataList;
    SimpleDatabaseTest simpleDatabaseTest;

    private NoticeReadDialog noticeReadDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notice_main, container, false);

        mAdapter = new NoticeRecyclerViewAdapter(DefineManager.showNoticeList, getActivity().getApplicationContext());
        mLayoutManager = new LinearLayoutManager(getActivity());
        simpleDatabaseTest = new SimpleDatabaseTest(getContext());

        txtStoreName = (TextView) view.findViewById(R.id.txtStoreName);
        noticeRecyclerView = (RecyclerView) view.findViewById((R.id.recyclerView));
        hamburgerMenu = (ImageView) view.findViewById(R.id.hamburgerMenu);

        hamburgerMenu.setVisibility(View.VISIBLE);
        selectedShopInfoData = getArguments().getStringArray("shopInfoData");

        noticeRecyclerView.setAdapter(mAdapter);
        noticeRecyclerView.setLayoutManager(mLayoutManager);
        txtStoreName.setText(selectedShopInfoData[DefineManager.shopNameSavedPoint]);
        //new GregorianCalendar();

        noticeDataList = simpleDatabaseTest.GetSelectedStoreNoticeInfo(Integer.parseInt(selectedShopInfoData[shopIdSavedPoint]), 2);

        synchronizedLocalAndServerDatabase.GetStoreNoticeFromServer(selectedShopInfoData[shopIdSavedPoint]);

        int i;
        for(i = 0; i < noticeDataList.size(); i += 1) {
            String[] noticeData = noticeDataList.get(i);
            try{
                Log.d(getContext().getString(R.string.app_name), "notice data: " + Arrays.toString(noticeData));
                mAdapter.addItem(noticeData[noticeTitleSavedPoint], noticeData[noticeBodySavedPoint], noticeData[noticeStartDateSavedPoint],
                        noticeData[noticeCloseDateSavedPoint], 1);
            }
            catch (Exception err) {
                Log.d(getContext().getString(R.string.app_name), "Error in AddNoticeItem: " + err.getMessage());
            }
        }
        /*mAdapter.addItem("제목1", "내용1", new GregorianCalendar(2016, 1, 1), new GregorianCalendar(2016, 12, 30), 1);
        mAdapter.addItem("제목2", "내용2", new GregorianCalendar(2015, 1, 1), new GregorianCalendar(2015, 12, 30), 2);
        mAdapter.addItem("제목3", "내용3", new GregorianCalendar(2014, 1, 1), new GregorianCalendar(2014, 12, 30), 3);*/


        return view;
    }

    private View.OnClickListener leftListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getActivity(), "왼쪽버튼 클릭", Toast.LENGTH_SHORT).show();
            noticeReadDialog.dismiss();
        }
    };
}
