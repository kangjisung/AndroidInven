package com.example.kangjisung.likeroom.FragmentUser;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kangjisung.likeroom.Util.ColorTheme;
import com.example.kangjisung.likeroom.R;
import com.github.clans.fab.FloatingActionMenu;

public class UserMain extends Fragment
{
    private View view;
    private RecyclerView userRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private UserMainListAdapter mAdapter;

    private CheckBox checkBoxStampAll;
    private FloatingActionMenu famMenu;
    private LinearLayout layoutStamp;

    private UserAddDialog userAddDialog;

    private int sortStateId;
    private String sortStateOrder;
    private boolean stampSendMode = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.user_main, container, false);

        mAdapter = new UserMainListAdapter(view);
        mLayoutManager = new LinearLayoutManager(getActivity());

        userRecyclerView = (RecyclerView) view.findViewById((R.id.recyclerView));
        userRecyclerView.setAdapter(mAdapter);
        userRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter.addItem("Kim Soyeon", "01063192456", "4278 p");
        mAdapter.addItem("Kim Charles", "01012345678", "762 p");
        mAdapter.addItem("Hong Gildong", "01052512963", "0 p");
        for (int i = 0; i < 10; i++) {
            mAdapter.addItem("Name", "01010101010", "0 p");
        }

        RelativeLayout layoutSortByName = (RelativeLayout) view.findViewById(R.id.layout_sort_by_name);
        RelativeLayout layoutSortByPhone = (RelativeLayout) view.findViewById(R.id.layout_sort_by_phone);
        RelativeLayout layoutSortByPoint = (RelativeLayout) view.findViewById(R.id.layout_sort_by_point);

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

        famMenu = (FloatingActionMenu) view.findViewById(R.id.menu);
        view.findViewById(R.id.fab_add_user).setOnClickListener(onFabClickListener);
        view.findViewById(R.id.fab_stamp).setOnClickListener(onFabClickListener);
        view.findViewById(R.id.fab_notice).setOnClickListener(onFabClickListener);

        layoutStamp = (LinearLayout)view.findViewById(R.id.layout_stamp);
        layoutStamp.setVisibility(View.INVISIBLE);

        checkBoxStampAll = (CheckBox) view.findViewById(R.id.checkBoxStampAll);
        checkBoxStampAll.setVisibility(View.GONE);

        ((Button)view.findViewById(R.id.button_stamp_cancel)).setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                famMenu.close(true);
                checkBoxStampAll.setVisibility(View.GONE);
                layoutStamp.setVisibility(View.INVISIBLE);
                famMenu.setVisibility(View.VISIBLE);
                mAdapter.updateCheckboxState(false);
            }
        });

        ((Button)view.findViewById(R.id.button_stamp_ok)).setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View view){
                // TODO : 이 코드에 스탬프 발송 화면을 띄움
                famMenu.close(true);
                checkBoxStampAll.setVisibility(View.GONE);
                layoutStamp.setVisibility(View.INVISIBLE);
                famMenu.setVisibility(View.VISIBLE);
                mAdapter.updateCheckboxState(false);
            }
        });

        EditText editTextSearch = (EditText)view.findViewById(R.id.editText_search);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                view.findViewById(R.id.textView_search).setVisibility((s.length() == 0)?(View.VISIBLE):(View.INVISIBLE));
            }
        });

        return view;
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
        textSort.setTextColor(ContextCompat.getColor(getContext(), R.color.white));
    }

    private Button.OnClickListener onButtonSortClickListener = new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            RelativeLayout includingLayout = (RelativeLayout) view.getParent().getParent();
            if (includingLayout != null) {
                if (sortStateId != includingLayout.getId()) {
                    setSortOn(includingLayout, "ASC");
                    setSortWhite((RelativeLayout) getActivity().findViewById(sortStateId));
                    sortStateId = includingLayout.getId();
                } else {
                    RelativeLayout parentLayout = (RelativeLayout) view.getParent();
                    if (parentLayout.findViewById(R.id.view_asc).getVisibility() == View.GONE) {
                        setSortOn(includingLayout, "ASC");
                    } else {
                        setSortOn(includingLayout, "DESC");
                    }
                }
                // TODO : sortStateId, sortStateOrder을 이용해 정렬을 수행
                switch (sortStateId) {
                    default:
                    case R.id.layout_sort_by_name:
                        break;
                    case R.id.layout_sort_by_phone:
                        break;
                    case R.id.layout_sort_by_point:
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
                    userAddDialog = new UserAddDialog(getActivity(),
                            "", // 제목
                            "", // 내용
                            addDialogBackListener, // 왼쪽 버튼 이벤트
                            addDialogOKListener); // 오른쪽 버튼 이벤트
                    userAddDialog.show();
                    break;
                case R.id.fab_stamp:
                    checkBoxStampAll.setVisibility(View.VISIBLE);
                    layoutStamp.setVisibility(View.VISIBLE);
                    famMenu.setVisibility(View.INVISIBLE);
                    mAdapter.updateCheckboxState(true);
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

    private View.OnClickListener addDialogBackListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getActivity().getApplicationContext(), "취소 버튼 클릭", Toast.LENGTH_SHORT).show();
            userAddDialog.dismiss();
        }
    };

    private View.OnClickListener addDialogOKListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getActivity().getApplicationContext(), "완료 버튼 클릭", Toast.LENGTH_SHORT).show();
        }
    };
}