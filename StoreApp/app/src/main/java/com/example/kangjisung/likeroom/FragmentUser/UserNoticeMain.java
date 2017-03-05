package com.example.kangjisung.likeroom.FragmentUser;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
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
import com.example.kangjisung.likeroom.Util.Utility;

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
        String query = "SELECT `코드`, `제목`, `내용`, `공지시작날짜`, `공지마감날짜`, `작성시간`, `공지사항종류`, `삭제` FROM `매장공지`;";
        new ClientDataBase(query, 1, 8, getContext());
        mAdapter = new UserNoticeListAdapter();

        int cnt=0;

        DateFormat dateFormat = new SimpleDateFormat("y-M-d", Locale.KOREA);
        DateFormat dateTimeFormat = new SimpleDateFormat("y-M-d HH:mm:ss", Locale.KOREA);
        while(DBstring[cnt] != null)
        {
            UserNoticeListItem addListItem = new UserNoticeListItem();
            try {
                addListItem.setNum(Integer.parseInt(DBstring[cnt]));
                addListItem.setTitle(DBstring[cnt+1]);
                addListItem.setBody(DBstring[cnt+2]);
                addListItem.setStartDate(dateFormat.parse(DBstring[cnt+3]));
                addListItem.setEndDate(dateFormat.parse(DBstring[cnt+4]));
                addListItem.setMakeDate(dateTimeFormat.parse(DBstring[cnt+5]));
                addListItem.setType(Integer.parseInt(DBstring[cnt+6]));
                addListItem.setDelete(Integer.parseInt(DBstring[cnt+7]));
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            if(addListItem.getDelete() == 0) {
                mAdapter.addItem(addListItem);
            }
            //mAdapter.addItem(DBstring[cnt], DBstring[cnt + 1], stDate , eDate, Integer.parseInt(DBstring[cnt+4]));
            cnt += 8;
        }
        mAdapter.sort();
        listView.setAdapter(mAdapter);
        registerForContextMenu(listView);
        mAdapter.notifyDataSetChanged();
        listViewHeightSet(mAdapter, listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("작업 선택");
        menu.add(Menu.NONE, Utility.MENU_NOTICE_DELETE, Menu.NONE, "삭제");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        final UserNoticeListItem listItem = (UserNoticeListItem)mAdapter.getItem(info.position);
        switch(item.getItemId()) {
            case Utility.MENU_NOTICE_DELETE:
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                dialogBuilder.setMessage("선택한 항목을 삭제하시겠습니까?");
                dialogBuilder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String query = String.format("DELETE FROM `매장공지` WHERE `코드` = %d;", listItem.getNum());
                        new ClientDataBase(query, 1, 0, getContext());
                        reloadRecyclerView();
                    }
                });
                dialogBuilder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog mAlertDialog = dialogBuilder.create();
                mAlertDialog.show();
                return true;
            default:
                return super.onContextItemSelected(item);
        }

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