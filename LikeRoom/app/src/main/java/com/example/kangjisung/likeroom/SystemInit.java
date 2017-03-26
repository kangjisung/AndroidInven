package com.example.kangjisung.likeroom;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by stories2 on 2017. 3. 26..
 */

public class SystemInit extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
