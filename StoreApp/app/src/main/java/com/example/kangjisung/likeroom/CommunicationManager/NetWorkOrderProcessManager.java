package com.example.kangjisung.likeroom.CommunicationManager;

import android.util.Log;

/**
 * Created by stories2 on 2017. 1. 24..
 */

public class NetWorkOrderProcessManager {
    public NetWorkOrderProcessManager() {
        NetworkModule networkModule = new NetworkModule();
        try {
            Log.d("test", "Result: " + networkModule.execute("test").get());
        }
        catch (Exception err) {
            Log.d("test", "Result: Fail");
        }
    }
}
