package com.example.kangjisung.likeroom.PermissionManager;

import android.os.Build;

/**
 * Created by stories2 on 2016. 12. 27..
 */

public class AndroidVersionController {

    public int GetAndroidBuildSDKVersion() {
        return Build.VERSION.SDK_INT;
    }
}
