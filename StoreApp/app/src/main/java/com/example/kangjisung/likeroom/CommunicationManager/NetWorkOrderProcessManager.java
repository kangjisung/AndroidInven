package com.example.kangjisung.likeroom.CommunicationManager;

import android.util.Log;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

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

    public boolean InsertNewStoreInfoData(String storeName, String storePhoneNumber, String introduceString, String storeAddress, String openTime,
                                       String closeTime, double storePlaceLatitude, double storePlaceLongtitude, String storeCountryCode){
        /*address=<매장 주소>&latitude=<매장 위도>&longtitude=<매장 경도>&name=<매장 이름>&phone=<매장 전화번호>&introduce=<매장 소개글>
        &countryCode=<매장 국가 코드>&serivceRegisterDate=<매장 등록 날짜>&updateInfoDate=<매장 정보 마지막 수정 날짜>&openTime=<매장 개장 시간>
        closeTime=<매장 마감 시간>&disable=<서비스 탈퇴 여부>*/
        networkModule = new NetworkModule();
        String responseRawData = null, serverStatus = null;
        Date storeRegisteredDate = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            responseRawData = networkModule.execute("http://" + hostName + apiName + "/InsertNewStoreInfoData/?address=" + storeAddress +
                    "&latitude=" + storePlaceLatitude + "&longtitude=" + storePlaceLongtitude + "&name=" + storeName +
                    "&phone=" + storePhoneNumber + "&introduce=" + introduceString + "&countryCode=" + storeCountryCode +
                    "&serivceRegisterDate=" + simpleDateFormat.format(storeRegisteredDate) + "&updateInfoDate=" + simpleDateFormat.format(storeRegisteredDate) +
                    "&openTime=" + openTime + "closeTime=" + closeTime).get();
            JSONObject jsonObject = new JSONObject(responseRawData);
            serverStatus = jsonObject.getString("Result");
            if(serverStatus.equals("Ok")) {
                return true;
            }
        }
        catch (Exception err) {
            Log.d(logCatTag, "Error in InsertNewStoreInfoData: " + err.getMessage());
        }
        return false;
    }
}
