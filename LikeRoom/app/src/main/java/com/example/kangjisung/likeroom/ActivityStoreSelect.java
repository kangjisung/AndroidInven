package com.example.kangjisung.likeroom;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.kangjisung.likeroom.FragmentNotice.NoticeRecyclerViewAdapter;
import com.example.kangjisung.likeroom.NetworkManager.HttpCommunicationProcess;
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
import static com.example.kangjisung.likeroom.DefineManager.isStoreListNeedsRefresh;

public class ActivityStoreSelect extends AppCompatActivity {

    Button btnRegisterNewStore;//새로운 상점을 등록하는 버튼을 눌렀을 때
    LinearLayout eachStoreListItemSample;//기존에 등록해 놓은 상점을 눌렀을 때
    RecyclerView registeredStoreList;
    NoticeRecyclerViewAdapter registeredStoreListViewAdapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    HttpCommunicationProcess httpCommunicationProcess;
    StoreAddDialog mStoreAddDialog;
    SimpleDatabaseTest simpleDatabaseTest;
    ArrayList<String[]> storeWhichIRegistered;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(getString(R.string.app_name), "store detail activity end");
        if(requestCode == isStoreListNeedsRefresh) {
            if(resultCode == RESULT_OK) {
                Log.d(getString(R.string.app_name), "resultCode: ok");
                Log.d(getString(R.string.app_name), data.getExtras().getString("deleteTargetStoreId"));
            }
            else {
                Log.d(getString(R.string.app_name), "resultCode: fail");
            }
        }
    }

    //맨처음에 매장선택해서 들어가는 부분.
    //레이아웃에서는 activity_store_select로 디자인되어 있다.
    //아마 CouponTan에서 만들어진 부분을 여기로 일부 옮겨올 수 있을 듯하다.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_select);

        registeredStoreListViewAdapter = new NoticeRecyclerViewAdapter(DefineManager.showStoreList, this);
        recyclerViewLayoutManager = new LinearLayoutManager(this);
        simpleDatabaseTest = new SimpleDatabaseTest();

        btnRegisterNewStore = (Button)findViewById(R.id.btnRegisterNewStore);
        //eachStoreListItemSample = (LinearLayout) findViewById(R.id.eachStoreListItemSample);
        registeredStoreList = (RecyclerView) findViewById(R.id.registeredStoreList);

        registeredStoreList.setAdapter(registeredStoreListViewAdapter);
        registeredStoreList.setLayoutManager(recyclerViewLayoutManager);

        storeWhichIRegistered = simpleDatabaseTest.GetStoreWhichIRegistered();

        int i;
        for(i = 0; i < storeWhichIRegistered.size(); i += 1) {
            String[] storeInfo = storeWhichIRegistered.get(i);
            registeredStoreListViewAdapter.addItem(getResources().getDrawable(R.mipmap.shop),storeInfo[databaseShopIdSavedPoint],
                    storeInfo[databaseShopNameSavedPoint], storeInfo[databaseShopAddressSavedPoint],
                    storeInfo[databaseShopPhoneNumberSavedPoint], storeInfo[databaseShopOpenTimeSavedPoint],
                    storeInfo[databaseShopCloseTimeSavedPoint], Double.parseDouble(storeInfo[databaseShopLatitudeSavedPoint]),
                    Double.parseDouble(storeInfo[databaseShopLongtitudedSavedPoint]));
        }



/*
        registeredStoreListViewAdapter.addItem(getResources().getDrawable(R.mipmap.shop), "고덕리엔파크점",
                "서울특별시 강동구 상일동 75-2", "02-426-8758", "N/A", "N/A", 37.5537472, 127.1716884);

        registeredStoreListViewAdapter.addItem(getResources().getDrawable(R.mipmap.shop), "상일세종점",
                "서울특별시 강동구 상일동 67-2", "02-441-7833", "N/A", "N/A", 37.5509792, 127.1738538);*/


        httpCommunicationProcess = new HttpCommunicationProcess(getApplicationContext());
        try {
            String internetConnectionTest = httpCommunicationProcess.execute("http://lamb.kangnam.ac.kr").get();
            if(internetConnectionTest == null) {
                Snackbar.make(this.findViewById(android.R.id.content), getString(R.string.internetConnectionFail), Snackbar.LENGTH_LONG).show();
            }
            Log.d(getString(R.string.app_name), "internetConnectionTest: " + internetConnectionTest);
        }
        catch (Exception err) {
            Log.d(getString(R.string.app_name), "Error in onCreate: " + err.getMessage());
        }

        btnRegisterNewStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mStoreAddDialog = new StoreAddDialog(ActivityStoreSelect.this,
                            "[다이얼로그 제목]", // 제목
                            "다이얼로그 내용 표시하기", // 내용
                            leftListener, // 왼쪽 버튼 이벤트
                            rightListener); // 오른쪽 버튼 이벤트
                    mStoreAddDialog.show();
                }
                catch (Exception err) {
                    Log.d(getString(R.string.app_name), "Error in setOnClickListener: " + err.getMessage());
                }
            }
        });


        /*eachStoreListItemSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, getString(R.string.featureLoadFail), Snackbar.LENGTH_SHORT).show();
                //등록된 상점을 클릭했을 시 해당 상점에 관한 화면으로 전환
                Intent showDetailTargetStore = new Intent(getApplicationContext(), ActivityMenu.class);
                startActivity(showDetailTargetStore);
            }
        });*/
    }


    private View.OnClickListener leftListener = new View.OnClickListener() {
        public void onClick(View v) {
            /*Toast.makeText(getApplicationContext(), "왼쪽버튼 클릭",
                    Toast.LENGTH_SHORT).show();*/
            mStoreAddDialog.dismiss();
        }
    };

    private View.OnClickListener rightListener = new View.OnClickListener() {
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "오른쪽버튼 클릭",
                    Toast.LENGTH_SHORT).show();
        }
    };

}
