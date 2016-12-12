package com.Coupon.Tan.FragmentPackage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.Coupon.Tan.R;
import com.Coupon.Tan.UserDeviceInfo.CustomersSavedInfoFromDevice;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Arrays;

//import ex14.stories2.com.ex14.R;

/**
 * Created by stories2 on 2016. 12. 6..
 */

public class EachStoreInfoView extends Fragment implements OnMapReadyCallback{

    //GoogleMap googleMapFragment;
    SupportMapFragment googleMapFragment;
    LatLng targetLocationInfo;
    final int androidVersionLollipop = 21, latitudeSavedPoint = 2, longitudeSavedPoint = 3;
    final float googleMapCameraZoomScale = 15;
    CustomersSavedInfoFromDevice customersSavedInfoFromDevice;
    String[] targetStoreInfoData;

    /*public EachStoreInfoView() {
        Log.d(getString(R.string.app_name), "can u see me?");
    }*/

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View eachStoreInfoLayout = null;
        try {
            eachStoreInfoLayout = inflater.inflate(R.layout.layout_each_store_info_view, container, false);

            customersSavedInfoFromDevice = new CustomersSavedInfoFromDevice();

            savedInstanceState = getArguments();

            Log.d(getString(R.string.app_name), "bundle get test: " + savedInstanceState.getString("test"));
            targetStoreInfoData = savedInstanceState.getStringArray("storeInfoData");
            Log.d(getString(R.string.app_name), "store Info: " + Arrays.toString(targetStoreInfoData));
            targetLocationInfo = SetGoogleMapPosition(targetStoreInfoData[latitudeSavedPoint], targetStoreInfoData[longitudeSavedPoint]);

            if(customersSavedInfoFromDevice.GetCustomersDeviceBuildVersion() < androidVersionLollipop) {//롤리팝 이전
                googleMapFragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.googleMap);
            }
            else {
                googleMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.googleMap);
            }
            googleMapFragment.getMapAsync(this);
            //googleMapFragment = ((SupportMapFragment)getActivity().getSupportFragmentManager().findFragmentById(R.id.googleMap)).getMap();
        }
        catch (Exception err) {
            Log.d(getString(R.string.app_name), "Error in onCreateView: " + err.getMessage());
        }
        return eachStoreInfoLayout;//super.onCreateView(inflater, container, savedInstanceState);
    }

    public LatLng SetGoogleMapPosition(String positionX, String positionY) {
        LatLng googleMapPositionData = new LatLng(0, 0);
        try {
            float mapPositionX = Float.parseFloat(positionX), mapPositionY = Float.parseFloat(positionY);
            googleMapPositionData = new LatLng(mapPositionX, mapPositionY);
        }
        catch (Exception err) {
            Log.d(getString(R.string.app_name), "Erro in SetGoogleMapPosition: " + err.getMessage());
        }
        return googleMapPositionData;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Marker pinMarkAtTargetPlace = googleMap.addMarker(new MarkerOptions().position(targetLocationInfo).title("서울 테스트"));

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(targetLocationInfo));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(googleMapCameraZoomScale));
    }
}
