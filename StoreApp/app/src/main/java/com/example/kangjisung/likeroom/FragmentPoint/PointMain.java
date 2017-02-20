package com.example.kangjisung.likeroom.FragmentPoint;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.kangjisung.likeroom.FragmentPoint.ListView.PointMainListAdapter;
import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase;
import com.example.kangjisung.likeroom.Util.ColorTheme;

import static com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase.DBstring;

public class PointMain extends Fragment
{
    private View fragmentView;
    private RecyclerView pointRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private PointMainListAdapter mAdapter;

    private LinearLayout layoutStamp;

    private PointSaveDialog mPointSaveDialog;

    private int sortStateId;
    private String sortStateOrder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.point_main, container, false);

        mAdapter = new PointMainListAdapter(fragmentView);
        mLayoutManager = new LinearLayoutManager(getContext());

        pointRecyclerView = (RecyclerView) fragmentView.findViewById((R.id.recyclerView));
        pointRecyclerView.setAdapter(mAdapter);
        pointRecyclerView.setLayoutManager(mLayoutManager);

        reloadRecyclerView();
        setTextViewSearchResult(false);
        registerForContextMenu(pointRecyclerView);

        RelativeLayout layoutSortByName = (RelativeLayout) fragmentView.findViewById(R.id.layout_sort_by_name);
        RelativeLayout layoutSortByPhone = (RelativeLayout) fragmentView.findViewById(R.id.layout_sort_by_phone);
        RelativeLayout layoutSortByPoint = (RelativeLayout) fragmentView.findViewById(R.id.layout_sort_by_point);

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

        EditText editTextSearch = (EditText) fragmentView.findViewById(R.id.editText_search);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                fragmentView.findViewById(R.id.textView_search).setVisibility((s.length() == 0)?(View.VISIBLE):(View.INVISIBLE));
            }
        });

        return fragmentView;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        switch(item.getItemId()){
            case 0:
                // TODO : 수정을 눌렀을 경우
                break;
            case 1:
                // TODO : 삭제를 눌렀을 경우
                break;
            default:
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
        TextView textViewSearchResult = (TextView) fragmentView.findViewById(R.id.textView_search_result);
        textViewSearchResult.setText("등록된 회원 수 : " + mAdapter.getItemCount());
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
                    setSortWhite((RelativeLayout) fragmentView.findViewById(sortStateId));
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
}