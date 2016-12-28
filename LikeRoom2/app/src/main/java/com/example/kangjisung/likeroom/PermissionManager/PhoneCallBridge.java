package com.example.kangjisung.likeroom.PermissionManager;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.example.kangjisung.likeroom.R;

import static com.example.kangjisung.likeroom.DefineManager.androidVersionLollipop;

/**
 * Created by stories2 on 2016. 12. 28..
 */

public class PhoneCallBridge {
    final int PERMISSIONS_REQUEST_CALL_PHONE = 0;

    Activity applicationActivity;

    public PhoneCallBridge(Activity applicationActivity) {
        this.applicationActivity = applicationActivity;
    }

    public boolean PermissionChecker() {

        if(new AndroidVersionController().GetAndroidBuildSDKVersion() <= 22)
            return true;
        if(IsPermissionNotGranted()) {
            ActivityCompat.requestPermissions(
                    applicationActivity,
                    new String[]{Manifest.permission.CALL_PHONE},
                    PERMISSIONS_REQUEST_CALL_PHONE);
        } else {
            //applicationActivity.startActivity(new Intent(Intent.ACTION_CALL).setData(Uri.parse("tel:12345678901")));

            return true;
        }
        return false;
    }

    public boolean IsPermissionNotGranted() {

        int permissionCheck = ContextCompat.checkSelfPermission(applicationActivity, Manifest.permission.CALL_PHONE);

        return permissionCheck != PackageManager.PERMISSION_GRANTED;
    }

    public void CallToTargetStorePhoneNumber(String registeredStorePhoneNumber) {
        String phoneNumberData = "tel:" + registeredStorePhoneNumber.trim();

        Intent callingIntent = new Intent(Intent.ACTION_CALL, Uri.parse(phoneNumberData));
        //callingIntent.setData(Uri.parse(phoneNumberData));
        if(new AndroidVersionController().GetAndroidBuildSDKVersion() > androidVersionLollipop) {
            if(IsPermissionNotGranted())
                applicationActivity.startActivity(callingIntent);
                Log.d(applicationActivity.getString(R.string.app_name), "call to: " + registeredStorePhoneNumber);
        }
        else {
            applicationActivity.startActivity(callingIntent);
            Log.d(applicationActivity.getString(R.string.app_name), "call to: " + registeredStorePhoneNumber);
        }
    }

    public void AlertPopUp() {
        final AlertDialog.Builder alertNoticeBuilder = new AlertDialog.Builder(applicationActivity);
        alertNoticeBuilder.setMessage(applicationActivity.getString(R.string.phoneCallFail));
        alertNoticeBuilder.setPositiveButton(applicationActivity.getString(R.string.positiveButtonMessage),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        new AndroidVersionController(applicationActivity.getApplicationContext()).GoToApplicationPermissionSettingScreen();
                    }
                });

        AlertDialog alertDialog = alertNoticeBuilder.create();
        alertDialog.show();
    }
}
