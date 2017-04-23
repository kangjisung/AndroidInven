package com.example.kangjisung.likeroom;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.kangjisung.likeroom.FragmentNotice.NoticeRecyclerViewAdapter;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.SimpleDatabaseTest;

import java.util.ArrayList;

import static com.example.kangjisung.likeroom.DefineManager.databaseShopAddressSavedPoint;
import static com.example.kangjisung.likeroom.DefineManager.databaseShopCloseTimeSavedPoint;
import static com.example.kangjisung.likeroom.DefineManager.databaseShopIdSavedPoint;
import static com.example.kangjisung.likeroom.DefineManager.databaseShopLatitudeSavedPoint;
import static com.example.kangjisung.likeroom.DefineManager.databaseShopLongtitudedSavedPoint;
import static com.example.kangjisung.likeroom.DefineManager.databaseShopNameSavedPoint;
import static com.example.kangjisung.likeroom.DefineManager.databaseShopOpenTimeSavedPoint;
import static com.example.kangjisung.likeroom.DefineManager.databaseShopPhoneNumberSavedPoint;
import static com.example.kangjisung.likeroom.DefineManager.synchronizedLocalAndServerDatabase;

public class StoreAddDialog extends Dialog {

    public static String selectedWantRegisterNewStoreId;
    private View mLeftButton;
    private View mRightButton;
    SearchView searchNewStore;

    private View.OnClickListener mLeftClickListener;
    private View.OnClickListener mRightClickListener;

    SimpleDatabaseTest simpleDatabaseTest;
    RecyclerView listOfStoreWitchIsRegisteredByServer;
    NoticeRecyclerViewAdapter unRegisteredStoreListViewAdapter, registeredStoreListViewAdapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 다이얼로그 외부 화면 흐리게 표현
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);

        View addNewStoreDialogView = View.inflate(getContext(), R.layout.store_add_dialog, null);
        setContentView(addNewStoreDialogView);
        ((TextView) findViewById(R.id.inc_tv_title)).setText("매장 검색");
        //setContentView(R.layout.store_add_dialog);

        mLeftButton = addNewStoreDialogView.findViewById(R.id.inc_btn_back);
        mRightButton = addNewStoreDialogView.findViewById(R.id.inc_btn_close);
        searchNewStore = (SearchView) addNewStoreDialogView.findViewById(R.id.searchNewStore);
        listOfStoreWitchIsRegisteredByServer = (RecyclerView) addNewStoreDialogView.findViewById(R.id.listOfStoreWitchIsRegisteredByServer);

        recyclerViewLayoutManager = new LinearLayoutManager(getContext());
        unRegisteredStoreListViewAdapter = new NoticeRecyclerViewAdapter(DefineManager.showStoreList, getContext(), mRightButton);

        unRegisteredStoreListViewAdapter.ChangeListMode(DefineManager.showUnRegisteredStoreList);
        listOfStoreWitchIsRegisteredByServer.setAdapter(unRegisteredStoreListViewAdapter);
        listOfStoreWitchIsRegisteredByServer.setLayoutManager(recyclerViewLayoutManager);
        mRightButton.setEnabled(false);

        selectedWantRegisterNewStoreId = null;
        ArrayList<String[]> unRegisteredAndSearchingSimillarStoreList = simpleDatabaseTest.GetSimillarStoreInfoSearched("");
        unRegisteredStoreListViewAdapter.DeleteAllItems();
        LoadStoreWhichIsImFinding(unRegisteredAndSearchingSimillarStoreList, unRegisteredStoreListViewAdapter);
        unRegisteredStoreListViewAdapter.notifyDataSetChanged();

        // 클릭 이벤트 셋팅
        if (mLeftClickListener != null && mRightClickListener != null) {
            mLeftButton.setOnClickListener(mLeftClickListener);
            //mRightButton.setOnClickListener(mRightClickListener);
        } else if (mLeftClickListener != null
                && mRightClickListener == null) {
            mLeftButton.setOnClickListener(mLeftClickListener);
        } else {

        }

        mRightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, getContext().getString(R.string.featureLoadFail), Snackbar.LENGTH_SHORT).show();
                simpleDatabaseTest.AddSelectedShop(Integer.parseInt(selectedWantRegisterNewStoreId));
                synchronizedLocalAndServerDatabase.RegisterCustomerToStore();
                registeredStoreListViewAdapter.DeleteAllItems();
                registeredStoreListViewAdapter.notifyDataSetChanged();
                ArrayList<String[]> storeWhichIRegistered = simpleDatabaseTest.GetStoreWhichIRegistered();
                registeredStoreListViewAdapter.ChangeListMode(DefineManager.showStoreList);
                LoadIRegisteredStoreList(storeWhichIRegistered, registeredStoreListViewAdapter);
                registeredStoreListViewAdapter.notifyDataSetChanged();
                dismiss();
            }
        });

        final EditText editTextSearch = (EditText) findViewById(R.id.editText_search);
        final View acivClear = findViewById(R.id.iv_clear);
        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 0){
                    acivClear.setVisibility(View.GONE);
                }
                else{
                    acivClear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ArrayList<String[]> unRegisteredAndSearchingSimillarStoreList = simpleDatabaseTest.GetSimillarStoreInfoSearched(s.toString());
                unRegisteredStoreListViewAdapter.DeleteAllItems();
                LoadStoreWhichIsImFinding(unRegisteredAndSearchingSimillarStoreList, unRegisteredStoreListViewAdapter);
                unRegisteredStoreListViewAdapter.notifyDataSetChanged();
            }
        });

        acivClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View onClickView) {
                editTextSearch.setText("");
            }
        });
        /*
        searchNewStore.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(getContext().getString(R.string.app_name), "type: " + newText);
                ArrayList<String[]> unRegisteredAndSearchingSimillarStoreList = simpleDatabaseTest.GetSimillarStoreInfoSearched(newText);
                unRegisteredStoreListViewAdapter.DeleteAllItems();
                LoadStoreWhichIsImFinding(unRegisteredAndSearchingSimillarStoreList, unRegisteredStoreListViewAdapter);
                unRegisteredStoreListViewAdapter.notifyDataSetChanged();
                return true;
            }
        });
        */
    }
    public void LoadIRegisteredStoreList(ArrayList<String[]> storeWhichIRegistered, NoticeRecyclerViewAdapter registeredStoreListViewAdapter) {
        int i;
        for(i = 0; i < storeWhichIRegistered.size(); i += 1) {
            String[] storeInfo = storeWhichIRegistered.get(i);
            registeredStoreListViewAdapter.addItem(getContext().getResources().getDrawable(R.mipmap.shop),storeInfo[databaseShopIdSavedPoint],
                    storeInfo[databaseShopNameSavedPoint], storeInfo[databaseShopAddressSavedPoint],
                    storeInfo[databaseShopPhoneNumberSavedPoint], storeInfo[databaseShopOpenTimeSavedPoint],
                    storeInfo[databaseShopCloseTimeSavedPoint], Double.parseDouble(storeInfo[databaseShopLatitudeSavedPoint]),
                    Double.parseDouble(storeInfo[databaseShopLongtitudedSavedPoint]));
        }
    }

    public void LoadStoreWhichIsImFinding(ArrayList<String[]> storeWhichIRegistered, NoticeRecyclerViewAdapter registeredStoreListViewAdapter) {
        int i;
        for(i = 0; i < storeWhichIRegistered.size(); i += 1) {
            String[] storeInfo = storeWhichIRegistered.get(i);
            registeredStoreListViewAdapter.addItem(getContext().getResources().getDrawable(R.mipmap.shop),storeInfo[databaseShopIdSavedPoint],
                    storeInfo[databaseShopNameSavedPoint], storeInfo[databaseShopAddressSavedPoint],
                    storeInfo[databaseShopPhoneNumberSavedPoint], storeInfo[databaseShopOpenTimeSavedPoint],
                    storeInfo[databaseShopCloseTimeSavedPoint], Double.parseDouble(storeInfo[databaseShopLatitudeSavedPoint]),
                    Double.parseDouble(storeInfo[databaseShopLongtitudedSavedPoint]));
        }
    }

    // 클릭버튼이 하나일때 생성자 함수로 클릭이벤트를 받는다.
    public StoreAddDialog(Context context, String title, View.OnClickListener singleListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mLeftClickListener = singleListener;
    }

    // 클릭버튼이 확인과 취소 두개일때 생성자 함수로 이벤트를 받는다
    public StoreAddDialog(Context context, String title, String content, View.OnClickListener leftListener, View.OnClickListener rightListener) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mLeftClickListener = leftListener;
        this.mRightClickListener = rightListener;
    }

    public StoreAddDialog(Context context, String title, String content, View.OnClickListener leftListener,
                          View.OnClickListener rightListener, SimpleDatabaseTest simpleDatabaseTest,
                          NoticeRecyclerViewAdapter registeredStoreListViewAdapter) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar);
        this.mLeftClickListener = leftListener;
        this.mRightClickListener = rightListener;
        this.simpleDatabaseTest = simpleDatabaseTest;
        this.registeredStoreListViewAdapter = registeredStoreListViewAdapter;
    }
}