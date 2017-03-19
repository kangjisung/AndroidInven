package com.teamdk.android.bakery.fragments.point;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

import com.teamdk.android.bakery.fragments.point.adapter.PointMainListAdapter;
import com.teamdk.android.bakery.R;

public class PointMain extends Fragment
{
    private PointMainListAdapter mAdapter;
    private EditText mEditTextSearch;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.point_main, container, false);

        mAdapter = new PointMainListAdapter(getActivity());

        ListView pointListView = (ListView) fragmentView.findViewById((R.id.listView));
        pointListView.setAdapter(mAdapter);

        mEditTextSearch = (EditText) fragmentView.findViewById(R.id.ed_search);
        mEditTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mAdapter.getFilter().filter(s);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {

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

        return fragmentView;
    }
}