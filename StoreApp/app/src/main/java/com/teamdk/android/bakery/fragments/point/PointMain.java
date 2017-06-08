package com.teamdk.android.bakery.fragments.point;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.teamdk.android.bakery.ActivityMenu;
import com.teamdk.android.bakery.fragments.point.adapter.PointMainListAdapter;
import com.teamdk.android.bakery.R;
import com.teamdk.android.bakery.fragments.user.UserEditDialog;
import com.teamdk.android.bakery.objectmanager.MemberListItem;
import com.teamdk.android.bakery.objectmanager.MemberObjectManager;
import com.teamdk.android.bakery.utility.Interfaces;

public class PointMain extends Fragment
{
    private PointMainListAdapter mAdapter;
    private EditText mEditTextSearch;
    private PointSaveDialog pointSaveDialog;
    private String searchString;
    private Interfaces mCallback;
    private DrawerLayout mDrawerLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.point_main, container, false);

        try {
            mCallback = (Interfaces) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + " must implement TextClicked");
        }

        mAdapter = new PointMainListAdapter(getActivity());

        ListView pointListView = (ListView) fragmentView.findViewById((R.id.listView));
        pointListView.setAdapter(mAdapter);
        pointListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final MemberListItem mMemberListItem = (MemberListItem) mAdapter.getItem(position);
                pointSaveDialog = new PointSaveDialog(getContext(), mMemberListItem);
                pointSaveDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        if(((PointSaveDialog)dialog).getDismissMessage() > 0) {
                            Toast.makeText(getContext(), mMemberListItem.getName() + " 고객님의 포인트 적립\n" + mMemberListItem.getPoint() + "p -> " + ((PointSaveDialog)dialog).getDismissMessage() + "p", Toast.LENGTH_LONG).show();
                            MemberObjectManager.load(getContext());
                            mAdapter.getFilter().filter(searchString);
                            mAdapter.notifyDataSetChanged();
                            mCallback.PointListClicked(1);
                        }
                    }
                });
                pointSaveDialog.show();
            }
        });

        mEditTextSearch = (EditText) fragmentView.findViewById(R.id.et_search);
        final View acivClear = fragmentView.findViewById(R.id.iv_clear);
        mEditTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchString = s.toString();
                mAdapter.getFilter().filter(searchString);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 0){
                    acivClear.setVisibility(View.GONE);
                }
                else{
                    acivClear.setVisibility(View.VISIBLE);
                }
            }
        });
        mEditTextSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus == true) {
                    mEditTextSearch.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            InputMethodManager mInputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            mInputMethodManager.showSoftInput(mEditTextSearch, InputMethodManager.SHOW_IMPLICIT);
                        }
                    }, 200);
                }
            }
        });

        acivClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View onClickView) {
                mEditTextSearch.setText("");
            }
        });

        mDrawerLayout = (DrawerLayout) fragmentView.findViewById(R.id.layout_drawer);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mDrawerLayout.setDrawerListener(new DrawerLayout.DrawerListener(){
            @Override
            public void onDrawerClosed(View drawerView) {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        return fragmentView;
    }

    @Override
    public void onDetach() {
        mCallback = null;
        super.onDetach();
    }
}