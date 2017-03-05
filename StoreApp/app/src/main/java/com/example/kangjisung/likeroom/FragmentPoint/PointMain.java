package com.example.kangjisung.likeroom.FragmentPoint;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

import com.example.kangjisung.likeroom.MemberListItem;
import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase;
import com.example.kangjisung.likeroom.Util.SharedPreferenceManager;
import com.example.kangjisung.likeroom.Util.Utility;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import static com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase.DBstring;

public class PointMain extends Fragment
{
    private View fragmentView;
    private ListView pointListView;
    private PointMainListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.point_main, container, false);

        String query = "SELECT `회원정보`.`고유회원등록번호`, `회원정보`.`이름`, `회원정보`.`전화번호`, `포인트`.`포인트`, `회원정보`.`생년월일`, `회원정보`.`이메일`, `회원정보`.`삭제`" +
                "FROM `회원정보` LEFT JOIN `포인트` ON `회원정보`.`고유회원등록번호`= `포인트`.`고유회원등록번호`;";

        new ClientDataBase(query, 1, 7, getContext());
        int count = 0;

        DateFormat dateFormat = new SimpleDateFormat("y-M-d", Locale.KOREA);
        ArrayList<MemberListItem> addList = new ArrayList<>();
        while(DBstring[count] != null) {
            MemberListItem addListItem = new MemberListItem();
            try {
                addListItem.setNum(Integer.parseInt(DBstring[count]));
                addListItem.setName(DBstring[count+1]);
                addListItem.setPhone(DBstring[count+2]);
                addListItem.setPoint((DBstring[count+3]==null)?("0"):(DBstring[count+3]));
                addListItem.setBirth(dateFormat.parse(DBstring[count+4]));
                addListItem.setEmail(DBstring[count+5]);
                addListItem.setDelete(Integer.parseInt(DBstring[count+6]));
                addList.add(addListItem);
            }
            catch (Exception e) {
                e.printStackTrace();
                continue;
            }
            count += 7;
        }
        mAdapter = new PointMainListAdapter(getActivity(), addList);
        /*
        String query = "SELECT `고유회원등록번호`, `이름`, `전화번호` FROM `회원정보` WHERE `삭제` = 0;";

        new ClientDataBase(query, 1, 3, getContext());
        int count = 0;

        ArrayList<MemberListItem> addList = new ArrayList<MemberListItem>();
        while(DBstring[count] != null) {
            addList.add(new MemberListItem(Integer.parseInt(DBstring[count]), DBstring[count+1], DBstring[count+2]));
            count += 3;
        }
        mAdapter = new PointMainListAdapter(getActivity(), addList);
        */

        pointListView = (ListView) fragmentView.findViewById((R.id.listView));
        pointListView.setAdapter(mAdapter);

        final EditText mEditTextSearch = (EditText) fragmentView.findViewById(R.id.ed_search);
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
                if(hasFocus == true) {
                    mEditTextSearch.post(new Runnable() {
                        @Override
                        public void run() {
                            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                            imm.showSoftInput(mEditTextSearch, InputMethodManager.SHOW_IMPLICIT);
                        }
                    });
                }
            }
        });

        return fragmentView;
    }
}