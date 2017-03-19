package com.teamdk.android.bakery.fragments.user;

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
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.teamdk.android.bakery.fragments.user.adapter.UserNoticeListAdapter;
import com.teamdk.android.bakery.objectmanager.NoticeListItem;
import com.teamdk.android.bakery.objectmanager.NoticeObjectManager;
import com.teamdk.android.bakery.R;
import com.teamdk.android.bakery.utility.SQLiteDatabaseControl.ClientDataBase;
import com.teamdk.android.bakery.utility.LayoutManager;

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
                UserNoticeEditDialog userNoticeEditDialog = new UserNoticeEditDialog((NoticeListItem)mAdapter.getItem(position), getContext());
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

        LayoutManager.setActivityTitle(fragmentView.findViewById(R.id.layout_title), true, false, R.attr.theme_color_D3, "공지사항");
        fragmentView.findViewById(R.id.inc_btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return fragmentView;

    }

    private void reloadRecyclerView() {
        mAdapter = new UserNoticeListAdapter();
        NoticeObjectManager.load(getContext());
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
        menu.add(Menu.NONE, LayoutManager.MENU_NOTICE_DELETE, Menu.NONE, "삭제");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        final NoticeListItem listItem = (NoticeListItem)mAdapter.getItem(info.position);
        switch(item.getItemId()) {
            case LayoutManager.MENU_NOTICE_DELETE:
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
                dialogBuilder.setMessage("선택한 항목을 삭제하시겠습니까?");
                dialogBuilder.setPositiveButton("확인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String query = String.format("DELETE FROM `매장공지` WHERE `코드` = %d;" + "" + "", listItem.getNum());
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