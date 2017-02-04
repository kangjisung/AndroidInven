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

    public void GetMileageSum(int uniqueRegisteredId) {
        httpCommunicationProcess = new HttpCommunicationProcess();
        String responseRawData = null;
        try {
            responseRawData = httpCommunicationProcess.execute("http://" + hostName + apiName + "/GetMileageSum/?customerAndStoreRegisteredId=" + uniqueRegisteredId).get();
            Log.d(logCatTag, responseRawData);
            JSONObject jsonObject = new JSONObject(responseRawData);
            if(jsonObject.isNull("Result")) {
                Log.d(logCatTag, "ok");
            }
            else {
                Log.d(logCatTag, "fail");
            }
        }
        catch (Exception err) {
            Log.d(logCatTag, "Error in GetMileageSum: " + err.getMessage());
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
    ///////////내 고유코드 받기
    public void LoadCustomerInfo(String customerEmail){
        httpCommunicationProcess=new HttpCommunicationProcess();
        String responseRawDate=null;
        String uniCode=null;//고유코드
        try{
            responseRawDate=httpCommunicationProcess.execute("http://"+hostName+apiName+"/LoadCustomerInfo/?email="+customerEmail+"").get();
            Log.d(logCatTag,responseRawDate);
            JSONObject jsonObject=new JSONObject(responseRawDate);
            uniCode=jsonObject.getString("회원번호");
            Log.d(logCatTag,uniCode);
        }catch (Exception err){
            Log.d(logCatTag,"Error in LoadCustomerInfo: "+err.getMessage());
        }
    }
    /*//////쿠폰 사용기능
    public void UseTargetCoupon(String customerAndStoreId,String updateDate,String couponId){
        httpCommunicationProcess=new HttpCommunicationProcess();
        String responseRawDate=null;
        try{
            responseRawDate=httpCommunicationProcess.execute("http://"+hostName+apiName+"/UseTargetCoupon/?customerAndStoreId="+customerAndStoreId+"&updateDate="+updateDate+"&couponId="+couponId+"").get();
            Log.d(logCatTag,responseRawDate);
            JSONObject jsonObject=new JSONObject(responseRawDate);
            if(jsonObject.getString("Result").equals("OK")){
                Log.d(logCatTag, "ok");
            }
            else Log.d(logCatTag, jsonObject.getString("Result"));
        }catch (Exception err){
            Log.d(logCatTag,"Error in UseTargetCoupon: "+err.getMessage());
        }
    }*/
    /////공지 리스트
    public void ShowTargetStoreNoticeList(String shopId){
        httpCommunicationProcess=new HttpCommunicationProcess();
        String responseRawDate=null;
        try{
            responseRawDate=httpCommunicationProcess.execute("http://"+hostName+apiName+"/ShowTargetStoreNoticeList/?shopId="+shopId+"").get();
            Log.d(logCatTag,responseRawDate);
        }catch (Exception err){
            Log.d(logCatTag,"Error in ShowTargetStoreNoticeList: "+err.getMessage());
        }
    }



}
