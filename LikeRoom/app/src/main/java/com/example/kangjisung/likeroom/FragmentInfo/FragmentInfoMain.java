package com.example.kangjisung.likeroom.FragmentInfo;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kangjisung.likeroom.DefineManager;
import com.example.kangjisung.likeroom.PermissionManager.PhoneCallBridge;
import com.example.kangjisung.likeroom.R;

public class FragmentInfoMain extends Fragment {

    ImageView btnCallButton;
    TextView txtStorePhone, txtStoreName, storeLocation, txtStoreManageTime;
    String registeredStorePhoneNumber;
    PhoneCallBridge phoneCallBridge;
    String[] selectedShopInfoData;
    Button btnDeleteThisStore;
//매장정보에 관한 부분이다. 아마 지도 등 CouponTan에서 만들어진 부분들을 여기로 옮겨올 수 있을 듯하다.
    //지도, 사진, 주소등이 필요
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /* 초기화 코드는 여기서 */
        View registeredStoreInfoView = inflater.inflate(R.layout.fragment_info_main, container, false);

        btnCallButton = (ImageView) registeredStoreInfoView.findViewById(R.id.btnCallButton);
        txtStorePhone = (TextView) registeredStoreInfoView.findViewById(R.id.txtStorePhone);
        txtStoreName = (TextView) registeredStoreInfoView.findViewById(R.id.storeName);
        btnDeleteThisStore = (Button) registeredStoreInfoView.findViewById(R.id.deleteButton);
        storeLocation = (TextView) registeredStoreInfoView.findViewById(R.id.storeLocation);
        txtStoreManageTime = (TextView) registeredStoreInfoView.findViewById(R.id.txtStoreManageTime);

        phoneCallBridge = new PhoneCallBridge(getActivity());

        selectedShopInfoData = getArguments().getStringArray("shopInfoData");
        registeredStorePhoneNumber = selectedShopInfoData[DefineManager.shopPhoneNumberSavedPoint];

        txtStorePhone.setText(selectedShopInfoData[DefineManager.shopPhoneNumberSavedPoint]);
        txtStoreName.setText(selectedShopInfoData[DefineManager.shopNameSavedPoint]);
        storeLocation.setText(selectedShopInfoData[DefineManager.shopAddressSavedPoint]);
        txtStoreManageTime.setText(selectedShopInfoData[DefineManager.shopOpenTimeSavedPoint] + " ~ "
        + selectedShopInfoData[DefineManager.shopCloseTimeSavedPoint] );

        btnCallButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phoneCallBridge.PermissionChecker()) {
                    phoneCallBridge.CallToTargetStorePhoneNumber(registeredStorePhoneNumber);
                }
                else {
                    phoneCallBridge.AlertPopUp();
                }
            }
        });

        txtStorePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phoneCallBridge.PermissionChecker()) {
                    phoneCallBridge.CallToTargetStorePhoneNumber(registeredStorePhoneNumber);
                }
                else {
                    phoneCallBridge.AlertPopUp();
                }
            }
        });

        btnDeleteThisStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, getString(R.string.featureLoadFail), Snackbar.LENGTH_SHORT).show();
            }
        });

        return registeredStoreInfoView;
    }


    /* 이벤트 코드는 여기서 */
}