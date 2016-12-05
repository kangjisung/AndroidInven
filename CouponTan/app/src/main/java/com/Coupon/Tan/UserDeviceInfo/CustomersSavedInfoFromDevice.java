package com.Coupon.Tan.UserDeviceInfo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.Coupon.Tan.R;

/**
 * Created by stories2 on 2016. 12. 1..
 */

public class CustomersSavedInfoFromDevice implements ActivityCompat.OnRequestPermissionsResultCallback {
    Context androidContext;
    String logCatTag, customersSimSerialNumber;
    float runningThisDeviceAndroidVersion;
    Activity androidTargetActivity;
    final int REQUEST_READ_PHONE_STATE = 0;
    boolean readPhoneStatePermissionStatus;

    public CustomersSavedInfoFromDevice(Context androidContext, Activity androidTargetActivity) {
        super();
        this.androidContext = androidContext;
        logCatTag = androidContext.getString(R.string.app_name);
        this.androidTargetActivity = androidTargetActivity;
        CheckRunningAndroidVersion();
    }

    public CustomersSavedInfoFromDevice() {
        CheckRunningAndroidVersion();
    }

    public float GetCustomersDeviceBuildVersion() {
        return runningThisDeviceAndroidVersion;
    }

    public void StoreDevicePhoneNumber() {
        if(readPhoneStatePermissionStatus) {
            TelephonyManager deviceTelephonyManager = (TelephonyManager) androidContext.getSystemService(androidContext.TELEPHONY_SERVICE);
            customersSimSerialNumber = deviceTelephonyManager.getSimSerialNumber();
        }
    }

    public String GetCustomersPhoneNumber() {
        return customersSimSerialNumber;
    }

    public void TelephonyPermissionChecker() {
        if(runningThisDeviceAndroidVersion >= 6.0f) {
            int permissionCheck = ContextCompat.checkSelfPermission(androidContext, Manifest.permission.READ_PHONE_STATE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(androidTargetActivity, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
                readPhoneStatePermissionStatus = false;
            }
            else {
                Log.d(logCatTag, "test phone permission");
            }
        }
    }

    public void CheckRunningAndroidVersion() {
        try {
            runningThisDeviceAndroidVersion = Float.parseFloat(Build.VERSION.RELEASE);
            Log.d(logCatTag, "" + runningThisDeviceAndroidVersion);
        }
        catch (Exception err) {
            Log.d(logCatTag, "Error in CheckRunningAndroidVersion: " + err.getMessage());
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE:
                if((grantResults.length > 0) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(logCatTag, "phone permission accept");
                    readPhoneStatePermissionStatus = true;
                }
                break;
        }
    }
}
