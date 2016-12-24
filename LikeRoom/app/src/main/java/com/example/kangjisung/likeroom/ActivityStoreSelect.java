package com.example.kangjisung.likeroom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ActivityStoreSelect extends AppCompatActivity {

    //맨처음에 매장선택해서 들어가는 부분.
    //레이아웃에서는 activity_store_select로 디자인되어 있다.
    //아마 CouponTan에서 만들어진 부분을 여기로 일부 옮겨올 수 있을 듯하다.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_select);
    }
}
