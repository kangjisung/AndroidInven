package com.example.kangjisung.likeroom.FragmentInfo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kangjisung.likeroom.DefineManager;
import com.example.kangjisung.likeroom.PermissionManager.AndroidVersionController;
import com.example.kangjisung.likeroom.PermissionManager.PhoneCallBridge;
import com.example.kangjisung.likeroom.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import static com.example.kangjisung.likeroom.DefineManager.androidVersionLollipop;
import static com.example.kangjisung.likeroom.DefineManager.googleMapCameraZoomScale;
import static com.example.kangjisung.likeroom.DefineManager.shopIdSavedPoint;

public class FragmentInfoMain extends Fragment implements OnMapReadyCallback{

    SupportMapFragment googleMapFragment;
    ImageView btnCallButton;
    TextView txtStorePhone, txtStoreName, storeLocation, txtStoreManageTime;
    String registeredStorePhoneNumber;
    PhoneCallBridge phoneCallBridge;
    String[] selectedShopInfoData;
    Button btnDeleteThisStore;
    LatLng targetLocationInfo;
    //SimpleDatabaseTest simpleDatabaseTest;
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
        //simpleDatabaseTest = new SimpleDatabaseTest();

        selectedShopInfoData = getArguments().getStringArray("shopInfoData");
        registeredStorePhoneNumber = selectedShopInfoData[DefineManager.shopPhoneNumberSavedPoint];
        targetLocationInfo = SetGoogleMapPosition(selectedShopInfoData[DefineManager.shopLatitudeSavedPoint], selectedShopInfoData[DefineManager.shopLongtitudedSavedPoint]);

        txtStorePhone.setText(selectedShopInfoData[DefineManager.shopPhoneNumberSavedPoint]);
        txtStoreName.setText(selectedShopInfoData[DefineManager.shopNameSavedPoint]);
        storeLocation.setText(selectedShopInfoData[DefineManager.shopAddressSavedPoint]);
        txtStoreManageTime.setText(selectedShopInfoData[DefineManager.shopOpenTimeSavedPoint] + " ~ "
        + selectedShopInfoData[DefineManager.shopCloseTimeSavedPoint] );

        if(new AndroidVersionController().GetAndroidBuildSDKVersion() < androidVersionLollipop) {//롤리팝 이전
            googleMapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.googleMap);
        }
        else {
            googleMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.googleMap);
        }
        googleMapFragment.getMapAsync(this);

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
                //Snackbar.make(view, getString(R.string.featureLoadFail), Snackbar.LENGTH_SHORT).show();
                //simpleDatabaseTest.DeleteSelectedShop(Integer.parseInt(selectedShopInfoData[shopIdSavedPoint]));

                Intent storeDetailViewInfoTab = new Intent();
                storeDetailViewInfoTab.putExtra("deleteTargetStoreId", selectedShopInfoData[shopIdSavedPoint]);
                //getActivity().setResult(Activity.RESULT_OK);
                getActivity().setResult(Activity.RESULT_OK, storeDetailViewInfoTab);
                getActivity().finish();
            }
        });

        return registeredStoreInfoView;
    }

    public LatLng SetGoogleMapPosition(String positionX, String positionY) {
        LatLng googleMapPositionData = new LatLng(0, 0);
        try {
            Log.d(getString(R.string.app_name), "x: " + positionX + " y: " + positionY);
            Double mapPositionX = Double.parseDouble(positionX), mapPositionY = Double.parseDouble(positionY);
            googleMapPositionData = new LatLng(mapPositionX, mapPositionY);
        }
        catch (Exception err) {
            Log.d(getString(R.string.app_name), "Erro in SetGoogleMapPosition: " + err.getMessage());
        }
        return googleMapPositionData;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Marker pinMarkAtTargetPlace = googleMap.addMarker(new MarkerOptions().position(targetLocationInfo).title(selectedShopInfoData[DefineManager.shopNameSavedPoint]));

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(targetLocationInfo));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(googleMapCameraZoomScale));
    }


    /* 이벤트 코드는 여기서 */
}