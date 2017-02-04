package com.example.kangjisung.likeroom.FragmentUser;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kangjisung.likeroom.FragmentUser.ListView.UserMainListAdapter;
import com.example.kangjisung.likeroom.FragmentUser.ListView.UserMainListItem;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase;
import com.example.kangjisung.likeroom.Util.ColorTheme;
import com.example.kangjisung.likeroom.R;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import static com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase.DBstring;

public class UserMain extends Fragment
{
    private View contentView;
    private RecyclerView userRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private UserMainListAdapter mAdapter;

    private CheckBox checkBoxStampAll;
    private FloatingActionMenu famMenu;
    private FloatingActionButton fabStampOk;
    private FloatingActionButton fabStampCancel;
    private LinearLayout layoutStamp;

    private UserEditDialog userEditDialog;
    private UserStampDialog userStampDialog;

    private int sortStateId;
    private String sortStateOrder;
    private boolean stampSendMode = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.user_main, container, false);

        mAdapter = new UserMainListAdapter(contentView);
        mLayoutManager = new LinearLayoutManager(getContext());

        userRecyclerView = (RecyclerView) contentView.findViewById((R.id.recyclerView));
        userRecyclerView.setAdapter(mAdapter);
        userRecyclerView.setLayoutManager(mLayoutManager);

        reloadRecyclerView();
        setTextViewSearchResult(false);
        registerForContextMenu(userRecyclerView);

        RelativeLayout layoutSortByName = (RelativeLayout) contentView.findViewById(R.id.layout_sort_by_name);
        RelativeLayout layoutSortByPhone = (RelativeLayout) contentView.findViewById(R.id.layout_sort_by_phone);
        RelativeLayout layoutSortByPoint = (RelativeLayout) contentView.findViewById(R.id.layout_sort_by_point);

        sortStateId = R.id.layout_sort_by_name;
        setSortOn(layoutSortByName, "ASC");
        setSortWhite(layoutSortByPhone);
        setSortWhite(layoutSortByPoint);

        layoutSortByName.findViewById(R.id.button).setOnClickListener(onButtonSortClickListener);
        layoutSortByPhone.findViewById(R.id.button).setOnClickListener(onButtonSortClickListener);
        layoutSortByPoint.findViewById(R.id.button).setOnClickListener(onButtonSortClickListener);
        ((TextView)layoutSortByName.findViewById(R.id.view_text)).setText(R.string.user_sort_name);
        ((TextView)layoutSortByPhone.findViewById(R.id.view_text)).setText(R.string.user_sort_phone);
        ((TextView)layoutSortByPoint.findViewById(R.id.view_text)).setText(R.string.user_sort_point);

        famMenu = (FloatingActionMenu) contentView.findViewById(R.id.menu);
        contentView.findViewById(R.id.fab_add_user).setOnClickListener(onFabClickListener);
        contentView.findViewById(R.id.fab_stamp).setOnClickListener(onFabClickListener);
        contentView.findViewById(R.id.fab_notice).setOnClickListener(onFabClickListener);
        fabStampOk = (FloatingActionButton) contentView.findViewById(R.id.fab_stamp_ok);
        fabStampOk.setVisibility(View.INVISIBLE);
        fabStampOk.setEnabled(false);
        fabStampCancel = (FloatingActionButton) contentView.findViewById(R.id.fab_stamp_cancel);
        fabStampCancel.setVisibility(View.INVISIBLE);

        layoutStamp = (LinearLayout) contentView.findViewById(R.id.layout_stamp);
        layoutStamp.setVisibility(View.INVISIBLE);

        checkBoxStampAll = (CheckBox) contentView.findViewById(R.id.checkBoxStampAll);
        checkBoxStampAll.setVisibility(View.GONE);
        checkBoxStampAll.setOnClickListener(new CompoundButton.OnClickListener(){
            @Override
            public void onClick(View onClickView){
                mAdapter.setCheckAll(checkBoxStampAll.isChecked());
                mAdapter.setTextViewSearchResult((TextView)contentView.findViewById(R.id.textView_search_result));
            }
        });

        fabStampCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                famMenu.close(true);
                checkBoxStampAll.setVisibility(View.GONE);
                fabStampCancel.setVisibility(View.INVISIBLE);
                fabStampOk.setVisibility(View.INVISIBLE);
                famMenu.setVisibility(View.VISIBLE);
                mAdapter.updateCheckboxState(false);
                setTextViewSearchResult(false);
            }
        });

        fabStampOk.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                userStampDialog = new UserStampDialog(mAdapter.getListItemToStampDialog(), getActivity());
                userStampDialog.show();
                famMenu.close(true);
                checkBoxStampAll.setVisibility(View.GONE);
                fabStampCancel.setVisibility(View.INVISIBLE);
                fabStampOk.setVisibility(View.INVISIBLE);
                famMenu.setVisibility(View.VISIBLE);
                mAdapter.updateCheckboxState(false);
                setTextViewSearchResult(false);
            }
        });

        EditText editTextSearch = (EditText) contentView.findViewById(R.id.editText_search);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                contentView.findViewById(R.id.textView_search).setVisibility((s.length() == 0)?(View.VISIBLE):(View.INVISIBLE));
            }
        });

        return contentView;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()){
            default:
            case 0:
                // TODO : 수정을 눌렀을 경우
                UserMainListItem userItem = mAdapter.getLongClickPosition();
                userEditDialog = new UserEditDialog(getContext(), userItem);
                userEditDialog.show();
                userEditDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if(((UserEditDialog)dialog).getDismissMessage() == 1) {
                            reloadRecyclerView();
                        }
                    }
                });
                break;
            case 1:
                // TODO : 삭제를 눌렀을 경우
                break;
        }

        return super.onContextItemSelected(item);
    }

    private void reloadRecyclerView() {
        String query = "SELECT `회원정보`.`이름`,`회원정보`.`전화번호`, `포인트`.`포인트` FROM `회원정보` " +
                       "LEFT JOIN `포인트` ON `회원정보`.`고유회원등록번호`= `포인트`.`고유회원등록번호`;";

        new ClientDataBase(query, 1, 3, getContext());
        int cnt=0;
        mAdapter.clearData();
        while(DBstring[cnt] != null) {
            mAdapter.addItem(DBstring[cnt], DBstring[cnt+1], (DBstring[cnt+2]==null)?("0"):(DBstring[cnt+2]));
            cnt += 3;
        }
        mAdapter.notifyDataSetChanged();

    }

    private void setTextViewSearchResult(boolean StampMode)
    {
        TextView textViewSearchResult = (TextView)contentView.findViewById(R.id.textView_search_result);
        if(StampMode == false){
            textViewSearchResult.setText("등록된 회원 수 : " + mAdapter.getItemCount());
        }
        else{
            textViewSearchResult.setText(mAdapter.getItemCount() + "명 중 0명 선택됨");
        }
    }

    private void setSortWhite(RelativeLayout layout) {
        layout.findViewById(R.id.view_body_white).setVisibility(View.VISIBLE);
        layout.findViewById(R.id.view_body_color).setVisibility(View.INVISIBLE);
        layout.findViewById(R.id.view_asc).setVisibility(View.GONE);
        layout.findViewById(R.id.view_desc).setVisibility(View.GONE);
        TextView textSort = (TextView)layout.findViewById(R.id.view_text);
        textSort.setTextColor(ColorTheme.getThemeColorRGB(getContext(), R.attr.theme_color_D3));
    }

    private void setSortOn(RelativeLayout layout, String state) {
        layout.findViewById(R.id.view_body_white).setVisibility(View.INVISIBLE);
        layout.findViewById(R.id.view_body_color).setVisibility(View.VISIBLE);
        if (state == "ASC") {
            layout.findViewById(R.id.view_asc).setVisibility(View.VISIBLE);
            layout.findViewById(R.id.view_desc).setVisibility(View.GONE);
        } else {
            layout.findViewById(R.id.view_asc).setVisibility(View.GONE);
            layout.findViewById(R.id.view_desc).setVisibility(View.VISIBLE);
        }
        sortStateOrder = state;
        TextView textSort = (TextView)layout.findViewById(R.id.view_text);
        textSort.setTextColor(getResources().getColor(R.color.white));
    }

    private Button.OnClickListener onButtonSortClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View onClickView) {
            RelativeLayout includingLayout = (RelativeLayout)onClickView.getParent().getParent();
            if (includingLayout != null) {
                if (sortStateId != includingLayout.getId()) {
                    setSortOn(includingLayout, "ASC");
                    setSortWhite((RelativeLayout) contentView.findViewById(sortStateId));
                    sortStateId = includingLayout.getId();
                } else {
                    RelativeLayout parentLayout = (RelativeLayout)onClickView.getParent();
                    if (parentLayout.findViewById(R.id.view_asc).getVisibility() == View.GONE) {
                        setSortOn(includingLayout, "ASC");
                    } else {
                        setSortOn(includingLayout, "DESC");
                    }
                }
                switch (sortStateId) {
                    default:
                    case R.id.layout_sort_by_name:
                        mAdapter.sort("NAME", sortStateOrder);
                        break;
                    case R.id.layout_sort_by_phone:
                        mAdapter.sort("PHONE", sortStateOrder);
                        break;
                    case R.id.layout_sort_by_point:
                        mAdapter.sort("POINT", sortStateOrder);
                        break;
                }
            }
        }
    };

    private View.OnClickListener onFabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                default:
                case R.id.fab_add_user:
                    famMenu.close(true);
                    userEditDialog = new UserEditDialog(getContext());
                    userEditDialog.show();
                    userEditDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            reloadRecyclerView();
                        }
                    });
                    break;
                case R.id.fab_stamp:
                    checkBoxStampAll.setVisibility(View.VISIBLE);
                    fabStampCancel.setVisibility(View.VISIBLE);
                    fabStampOk.setVisibility(View.VISIBLE);
                    famMenu.setVisibility(View.INVISIBLE);
                    mAdapter.updateCheckboxState(true);
                    setTextViewSearchResult(true);
                    break;
                case R.id.fab_notice:
                    famMenu.close(true);
                    UserNoticeMain fragmentNotice = new UserNoticeMain();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.user_main_layout, fragmentNotice);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    break;
            }
        }
    };
}