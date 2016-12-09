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

//import ex14.stories2.com.ex14.R;

/**
 * Created by stories2 on 2016. 12. 6..
 */

public class EachStoreInfoView extends Fragment implements OnMapReadyCallback{

    //GoogleMap googleMapFragment;
    SupportMapFragment googleMapFragment;
    static final LatLng SEOUL = new LatLng(37.56, 126.97);
    CustomersSavedInfoFromDevice customersSavedInfoFromDevice;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View eachStoreInfoLayout = null;
        try {
            eachStoreInfoLayout = inflater.inflate(R.layout.layout_each_store_info_view, container, false);

            customersSavedInfoFromDevice = new CustomersSavedInfoFromDevice();

            if(customersSavedInfoFromDevice.GetCustomersDeviceBuildVersion() < 5.0F) {//롤리팝 이전
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

    @Override
    public void onMapReady(GoogleMap googleMap) {

        Marker pinMarkAtTargetPlace = googleMap.addMarker(new MarkerOptions().position(SEOUL).title("서울 테스트"));

        googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(5));
    }
}
