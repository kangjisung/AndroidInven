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
////내 정보 등록
    public void InsertNewCustomerInfo(String customerName,String customerPhone,String customerEmail,String customerBirth){
        httpCommunicationProcess=new HttpCommunicationProcess();
        String responseRawDate=null;
        try{
            responseRawDate=httpCommunicationProcess.execute("http://"+hostName+apiName+"/InsertNewCustomerInfo/?name="+customerName+"&phone="+customerPhone+"&email="+customerEmail+"&birthday="+customerBirth+"").get();
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
