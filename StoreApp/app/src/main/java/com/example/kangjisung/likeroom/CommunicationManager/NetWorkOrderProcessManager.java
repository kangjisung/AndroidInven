package com.example.kangjisung.likeroom.CommunicationManager;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by stories2 on 2017. 1. 24..
 */

public class NetWorkOrderProcessManager {
    NetworkModule networkModule;

    String hostName = "lamb.kangnam.ac.kr:4200", apiName = "/Smoothie/2", logCatTag = "test";

    public NetWorkOrderProcessManager() {
    }

    public void TestConnection() {
        networkModule = new NetworkModule();
        try {
            Log.d(logCatTag, "Result: " + networkModule.execute("http://" + hostName).get());
        }
        catch (Exception err) {
            Log.d(logCatTag, "Result: Fail");
        }
    }

    public void LoadAllStoreInfo(){
        networkModule = new NetworkModule();
        String responseRawData = null;
        int i = 0;
        try {
            responseRawData = networkModule.execute("http://" + hostName + apiName + "/LoadAllStoreInfo/").get();
            Log.d(logCatTag, responseRawData);
            //JSONArray serverResponseArrayData = new JSONArray("{'test':1}");
            JSONObject jsonObject = new JSONObject(responseRawData);
            //Log.d(logCatTag, "test: " + jsonObject.getJSONObject("0"));
            /*for(i = 0; i < serverResponseArrayData.length(); i += 1){
                JSONObject eachObjectData = serverResponseArrayData.getJSONObject(i);
                Log.d(logCatTag, "store id : " + eachObjectData);
            }*/
            while(true) {
                try {
                    JSONObject indexOfZeroStoreInfoData = jsonObject.getJSONObject("" + i);
                    Log.d(logCatTag, "매장 번호: " + indexOfZeroStoreInfoData.getString("매장번호"));
                    i += 1;
                }
                catch (Exception err) {
                    Log.d(logCatTag, "Error in find json object: " + err.getMessage());
                    break;
                }
            }
        }
        catch (Exception err) {
            Log.d(logCatTag, "Error in LoadAllStoreInfo: " + err.getMessage());
        }
    }
}
