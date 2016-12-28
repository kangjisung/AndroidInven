package com.example.kangjisung.likeroom.PermissionManager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

/**
 * Created by stories2 on 2016. 12. 27..
 */

public class AndroidVersionController {

    Context context;

    public AndroidVersionController() {

    }

    public AndroidVersionController(Context context) {
        this.context = context;
    }

    public int GetAndroidBuildSDKVersion() {
        return Build.VERSION.SDK_INT;
    }

    public void GoToApplicationPermissionSettingScreen() {

        Intent moveToPermissionScreen = new Intent();
        moveToPermissionScreen.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        moveToPermissionScreen.addCategory(Intent.CATEGORY_DEFAULT);
        moveToPermissionScreen.setData(Uri.parse("package:" + context.getPackageName()));
        moveToPermissionScreen.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        moveToPermissionScreen.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        moveToPermissionScreen.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
        context.startActivity(moveToPermissionScreen);
    }
}
