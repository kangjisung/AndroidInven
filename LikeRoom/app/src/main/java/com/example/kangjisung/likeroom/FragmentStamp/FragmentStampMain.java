package com.example.kangjisung.likeroom.FragmentStamp;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.kangjisung.likeroom.DefineManager;
import com.example.kangjisung.likeroom.R;

public class FragmentStampMain extends Fragment {
    //스탬프 현황 레이아웃을 그려야할 것
    //또한 스탬프 사용시 팝업도 띄워야 함
    //스탬프 레이아웃에서 선택된 매장에서 현제 나의 스탬프 상황을 봄
    View stampLayout;
    Button btnShowSpecialStamp;
    String[] selectedShopInfoData;
    TextView txtShopPhoneNumber, txtShopName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /* 초기화 코드는 여기서 */
        stampLayout = inflater.inflate(R.layout.fragment_stamp_main, container, false);
        btnShowSpecialStamp = (Button)stampLayout.findViewById(R.id.btnShowSpecialStamp);
        txtShopPhoneNumber = (TextView) stampLayout.findViewById(R.id.txtShopPhoneNumber);
        txtShopName = (TextView) stampLayout.findViewById(R.id.txtShopName);

        selectedShopInfoData = getArguments().getStringArray("shopInfoData");

        txtShopName.setText(selectedShopInfoData[DefineManager.shopNameSavedPoint]);
        txtShopPhoneNumber.setText(selectedShopInfoData[DefineManager.shopPhoneNumberSavedPoint]);

        //쿠폰<->스탬프 레이아웃을 전환하면서 나의 쿠폰과 스탬프 상태를 봄
        btnShowSpecialStamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, getString(R.string.featureLoadFail), Snackbar.LENGTH_SHORT).show();
            }
        });

        return stampLayout;
    }

    /* 이벤트 코드는 여기서 */
}