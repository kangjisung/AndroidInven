package com.example.kangjisung.likeroom.LoadingModule;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

/**
 * Created by stories2 on 2017. 4. 23..
 */

public class PermissionManagerProcesser {

    boolean CheckNeedPermissionStatus(Context context, String[] needPermissionList) {
        for(String indexOfPermission : needPermissionList) {
            if(ActivityCompat.checkSelfPermission(context, indexOfPermission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}
