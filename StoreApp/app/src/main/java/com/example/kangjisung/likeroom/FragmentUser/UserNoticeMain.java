package com.example.kangjisung.likeroom.FragmentUser;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.kangjisung.likeroom.FragmentUser.ListView.UserNoticeListAdapter;
import com.example.kangjisung.likeroom.FragmentUser.ListView.UserNoticeListItem;
import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import static com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase.DBstring;

public class UserNoticeMain extends Fragment
{
    UserNoticeListAdapter mAdapter;
    ListView listView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View fragmentView = inflater.inflate(R.layout.user_notice_main, container, false);

        listView = (ListView)fragmentView.findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l_position) {
                UserNoticeEditDialog userNoticeEditDialog = new UserNoticeEditDialog((UserNoticeListItem)mAdapter.getItem(position), getContext());
                userNoticeEditDialog.show();
                userNoticeEditDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        reloadRecyclerView();
                    }
                });
            }
        });

        Button mButtonNew = (Button) fragmentView.findViewById(R.id.btn_new);
        mButtonNew.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View onClickView){
                UserNoticeEditDialog userNoticeEditDialog = new UserNoticeEditDialog(getContext());
                userNoticeEditDialog.show();
                userNoticeEditDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        reloadRecyclerView();
                    }
                });
            }
        });

        reloadRecyclerView();

        Button buttonBack = (Button)fragmentView.findViewById(R.id.button_back);
        buttonBack.setOnClickListener(new Button.OnClickListener(){
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return fragmentView;
    }

    private void reloadRecyclerView() {
        String query = "SELECT `제목`, `내용`, `공지 시작 날짜`, `공지 마감 날짜`,`공지사항종류` FROM `매장공지`;";
        new ClientDataBase(query, 1, 5, getContext());
        mAdapter = new UserNoticeListAdapter();

        int cnt=0;
        String startDate;
        String EndDate;
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.KOREA);
        Date stDate=new Date();
        Date eDate=new Date();
        while(true) {
            if (DBstring[cnt] != null) {
                startDate=DBstring[cnt+2];
                EndDate=DBstring[cnt+3];
                try {
                    stDate = sdf.parse(startDate);
                    eDate = sdf.parse(EndDate);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
                mAdapter.addItem(DBstring[cnt], DBstring[cnt + 1], stDate , eDate, Integer.parseInt(DBstring[cnt+4]));
                cnt += 5;
            }
            else if(DBstring[cnt]==null) break;
        }
        mAdapter.sort();
        listView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        listViewHeightSet(mAdapter, listView);
    }

    public void listViewHeightSet(BaseAdapter listAdapter, ListView listView){
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++){
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount()));
        listView.setLayoutParams(params);
    }
}