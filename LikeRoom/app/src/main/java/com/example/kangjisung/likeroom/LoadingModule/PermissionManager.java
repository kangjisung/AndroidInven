package com.example.kangjisung.likeroom.LoadingModule;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;

import com.example.kangjisung.likeroom.DefineManager;
import com.example.kangjisung.likeroom.MainActivity;
import com.example.kangjisung.likeroom.R;
import com.example.kangjisung.likeroom.Util.LogManager;

import java.util.Arrays;

import static com.example.kangjisung.likeroom.DefineManager.ANDROID_VERSION_OF_MARSHMALLOW;
import static com.example.kangjisung.likeroom.DefineManager.PERMISSION_REQUESTED_ORDER;
import static org.apache.commons.logging.impl.SimpleLog.LOG_LEVEL_INFO;

/**
 * Created by stories2 on 2017. 4. 23..
 */

public class PermissionManager extends AppCompatActivity implements Animation.AnimationListener{

    String[] needPermissionList;
    PermissionManagerProcesser permissionManagerProcesser;
    //Animation loadingScreenFadeOut;
    //LinearLayout loadingScreen;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.permission_manager);

        LogManager.PrintLog("PermissionManager", "onCreate", "Android Version: " + Build.VERSION.SDK_INT, DefineManager.LOG_LEVEL_INFO);

        permissionManagerProcesser = new PermissionManagerProcesser();

        /*loadingScreenFadeOut = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_out);
        loadingScreenFadeOut.setAnimationListener(this);

        loadingScreen = (LinearLayout) findViewById(R.id.loadingScreen);*/

        if(Build.VERSION.SDK_INT >= ANDROID_VERSION_OF_MARSHMALLOW) {
            needPermissionList = new String[] {
                    Manifest.permission.INTERNET, Manifest.permission.ACCESS_NETWORK_STATE, Manifest.permission.GET_ACCOUNTS,
                    Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE,
                    Manifest.permission.READ_CONTACTS, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION
            };
            if(!permissionManagerProcesser.CheckNeedPermissionStatus(getApplicationContext(), needPermissionList)) {
                ActivityCompat.requestPermissions(this, needPermissionList, PERMISSION_REQUESTED_ORDER);
            }
            else {
                MoveToMainActivity();
                //loadingScreen.setAnimation(loadingScreenFadeOut);
            }
        }
        else {
            MoveToMainActivity();
            //loadingScreen.setAnimation(loadingScreenFadeOut);
        }
    }

    void MoveToMainActivity() {
        Intent moveToMainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(moveToMainActivityIntent);
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        boolean isPermissionGrantedSuccessfully = true;
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUESTED_ORDER:
                LogManager.PrintLog("PermissionManager", "onRequestPermissionsResult", "permissions: " + Arrays.toString(permissions), LOG_LEVEL_INFO);
                LogManager.PrintLog("PermissionManager", "onRequestPermissionsResult", "grantResults: " + Arrays.toString(grantResults), LOG_LEVEL_INFO);
                for(int indexOfGrantResult : grantResults) {
                    if(indexOfGrantResult == -1) {
                        isPermissionGrantedSuccessfully = false;
                    }
                }
                if(isPermissionGrantedSuccessfully) {
                    MoveToMainActivity();
                }
                else {
                    finish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        MoveToMainActivity();
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}