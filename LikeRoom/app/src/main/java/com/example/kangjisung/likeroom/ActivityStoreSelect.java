package com.example.kangjisung.likeroom;

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

public class ActivityStoreSelect extends AppCompatActivity {

    Button btnRegisterNewStore;//새로운 상점을 등록하는 버튼을 눌렀을 때
    LinearLayout eachStoreListItemSample;//기존에 등록해 놓은 상점을 눌렀을 때
    RecyclerView registeredStoreList;
    NoticeRecyclerViewAdapter registeredStoreListViewAdapter;
    RecyclerView.LayoutManager recyclerViewLayoutManager;
    HttpCommunicationProcess httpCommunicationProcess;
    StoreAddDialog mStoreAddDialog;

    //맨처음에 매장선택해서 들어가는 부분.
    //레이아웃에서는 activity_store_select로 디자인되어 있다.
    //아마 CouponTan에서 만들어진 부분을 여기로 일부 옮겨올 수 있을 듯하다.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_select);

        registeredStoreListViewAdapter = new NoticeRecyclerViewAdapter(DefineManager.showStoreList, getApplicationContext());
        recyclerViewLayoutManager = new LinearLayoutManager(this);

        btnRegisterNewStore = (Button)findViewById(R.id.btnRegisterNewStore);
        //eachStoreListItemSample = (LinearLayout) findViewById(R.id.eachStoreListItemSample);
        registeredStoreList = (RecyclerView) findViewById(R.id.registeredStoreList);

        registeredStoreList.setAdapter(registeredStoreListViewAdapter);
        registeredStoreList.setLayoutManager(recyclerViewLayoutManager);

        registeredStoreListViewAdapter.addItem(getResources().getDrawable(R.mipmap.shop), "테스트 매장 이름",
                "테스트 매장 주소", "테스트 매장 번호");

        registeredStoreListViewAdapter.addItem(getResources().getDrawable(R.mipmap.shop), "가나다라",
                "마바사", "아자차카");

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
                mStoreAddDialog = new StoreAddDialog(ActivityStoreSelect.this,
                        "[다이얼로그 제목]", // 제목
                        "다이얼로그 내용 표시하기", // 내용
                        leftListener, // 왼쪽 버튼 이벤트
                        rightListener); // 오른쪽 버튼 이벤트
                mStoreAddDialog.show();

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
            Toast.makeText(getApplicationContext(), "왼쪽버튼 클릭",
                    Toast.LENGTH_SHORT).show();
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
