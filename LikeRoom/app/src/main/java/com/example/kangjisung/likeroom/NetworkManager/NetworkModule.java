package com.example.kangjisung.likeroom.NetworkManager;

import android.util.Log;

import org.json.JSONObject;

/**
 * Created by stories2 on 2017. 2. 4..
 */

public class NetworkModule {
    HttpCommunicationProcess httpCommunicationProcess;

    String hostName = "lamb.kangnam.ac.kr:4200", apiName = "/Smoothie/2", logCatTag = "test";

    public NetworkModule() {
    }

    public void TestConnection() {
        httpCommunicationProcess = new HttpCommunicationProcess();
        try {
            Log.d(logCatTag, "Result: " + httpCommunicationProcess.execute("http://" + hostName).get());
        }
        catch (Exception err) {
            Log.d(logCatTag, "Result: Fail");
        }
    }

    public void LoadAllStoreInfo(){
        httpCommunicationProcess = new HttpCommunicationProcess();
        String responseRawData = null;
        int i;
        try {
            responseRawData = httpCommunicationProcess.execute("http://" + hostName + apiName + "/LoadAllStoreInfo/").get();
            Log.d(logCatTag, responseRawData);
            //JSONArray serverResponseArrayData = new JSONArray("{'test':1}");
            JSONObject jsonObject = new JSONObject(responseRawData);
            //Log.d(logCatTag, "test: " + jsonObject.getJSONObject("0"));
            JSONObject indexOfZeroStoreInfoData = jsonObject.getJSONObject("0");
            Log.d(logCatTag, "매장 번호: " + indexOfZeroStoreInfoData.getString("매장번호"));
            /*for(i = 0; i < serverResponseArrayData.length(); i += 1){
                JSONObject eachObjectData = serverResponseArrayData.getJSONObject(i);
                Log.d(logCatTag, "store id : " + eachObjectData);
            }*/
        }
        catch (Exception err) {
            Log.d(logCatTag, "Error in LoadAllStoreInfo: " + err.getMessage());
        }
    }

    public void AddToStoreAsNewMember(int customerId, int targetStoreId) {
        httpCommunicationProcess = new HttpCommunicationProcess();
        String responseRawData = null;
        try {
            responseRawData = httpCommunicationProcess.execute("http://" + hostName + apiName + "/AddToStoreAsNewMember/?customerId=" + customerId
                                                                + "&storeId=" + targetStoreId).get();
            Log.d(logCatTag, responseRawData);
            JSONObject jsonObject = new JSONObject(responseRawData);
            if(jsonObject.getString("Result").equals("Ok")) {
                Log.d(logCatTag, "ok");
            }
            else{
                Log.d(logCatTag, "fail");
            }
        }
        catch (Exception err) {
            Log.d(logCatTag, "Error in AddToStoreAsNewMember: " + err.getMessage());
        }
    }

    public void DelMemberFromStore(int uniqueRegisteredId) {
        httpCommunicationProcess = new HttpCommunicationProcess();
        String responseRawData = null;
        try {
            responseRawData = httpCommunicationProcess.execute("http://" + hostName + apiName + "/DelMemberFromStore/?customerAndStoreRegisteredId=" + uniqueRegisteredId).get();
            Log.d(logCatTag, responseRawData);
            JSONObject jsonObject = new JSONObject(responseRawData);
            if(jsonObject.getString("Result").equals("Ok")) {
                Log.d(logCatTag, "ok");
            }
            else {
                Log.d(logCatTag, "fail");
            }
        }
        catch (Exception err) {
            Log.d(logCatTag, "Error in DelMemberFromStore: " + err.getMessage());
        }
    }

    public void GetStoreAndCustomerRegisteredInfo(int customerId) {
        httpCommunicationProcess = new HttpCommunicationProcess();
        String responseRawData = null;
        try {
            responseRawData = httpCommunicationProcess.execute("http://" + hostName + apiName + "/GetStoreAndCustomerRegisteredInfo/?customerId=" + customerId).get();
            Log.d(logCatTag, responseRawData);
            JSONObject jsonObject = new JSONObject(responseRawData);
            try{
                if(jsonObject.isNull("Result")) {
                    Log.d(logCatTag, "ok");
                }
                else {
                    Log.d(logCatTag, "fail");
                }
            }
            catch (Exception err) {
                Log.d(logCatTag, "Error in GetStoreAndCustomerRegisteredInfo: " + err.getMessage());
            }
        }
        catch (Exception err) {
            Log.d(logCatTag, "Error in GetStoreAndCustomerRegisteredInfo: " + err.getMessage());
        }
    }

    public void InsertMileageLog(int uniqueRegisteredId, int mileageSize) {
        httpCommunicationProcess = new HttpCommunicationProcess();
        String responseRawData = null;
        try {
            responseRawData = httpCommunicationProcess.execute("http://" + hostName + apiName + "/InsertMileageLog/?customerAndStoreRegisteredId=" + uniqueRegisteredId +
                                                                "&mileageSize=" + mileageSize + "&changeDate=0000-00-00").get();
            Log.d(logCatTag, responseRawData);
            JSONObject jsonObject = new JSONObject(responseRawData);
            if(jsonObject.getString("Result").equals("Ok")) {
                Log.d(logCatTag, "ok");
            }
            else {
                Log.d(logCatTag, "fail");
            }
        }
        catch (Exception err) {
            Log.d(logCatTag, "Error in InsertMileageLog: " + err.getMessage());
        }
    }

    public void InsertNewCustomerInfo(String customerName){
        httpCommunicationProcess=new HttpCommunicationProcess();
        String responseRawDate=null;
        try{
            responseRawDate=httpCommunicationProcess.execute("http://"+hostName+apiName+"/InsertNewCustomerInfo/?name="+customerName+"").get();
            ///?name=<고객 이름>&phone=<고객의 전화번호>&email=<고객의 이메일 주소>&birthday=<고객의 생일>&countryCode=<고객이 살고 있는 나라 >&level=<고객의 등급>&updateInfoDate=<마지막 정보 수정 날짜>&androidSDKLevel=<고객이 사용중인 디바이스 안드로이드 api 레벨>&deviceName=<고객이 사용중인 디바이스 이름>
            Log.d(logCatTag,responseRawDate);
            JSONObject jsonObject=new JSONObject(responseRawDate);
            if(jsonObject.getString("Result").equals("OK")){
                Log.d(logCatTag, "ok");
            }
            else Log.d(logCatTag, jsonObject.getString("Result"));

        }catch (Exception err){
            Log.d(logCatTag,"Error in InsertNewCustomerInfo: "+err.getMessage());
        }
    }
}
