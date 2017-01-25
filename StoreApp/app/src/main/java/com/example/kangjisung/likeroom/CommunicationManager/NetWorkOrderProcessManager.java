package com.example.kangjisung.likeroom.CommunicationManager;

import android.util.Log;

/**
 * Created by stories2 on 2017. 1. 24..
 */

public class NetWorkOrderProcessManager {
    NetworkModule networkModule;

    String hostName = "lamb.kangnam.ac.kr:8000";

    public NetWorkOrderProcessManager() {
        networkModule = new NetworkModule();
        try {
            Log.d("test", "Result: " + networkModule.execute("http://" + hostName).get());
        }
        catch (Exception err) {
            Log.d("test", "Result: Fail");
        }
    }

    public void LoadAllStoreInfo(){
        networkModule = new NetworkModule();
        try {
            Log.d("test", networkModule.execute("http://" + hostName + "/LoadAllStoreInfo/").get());
        }
        catch (Exception err) {
            Log.d("test", "Error in LoadAllStoreInfo: " + err.getMessage());
        }
    }
}
