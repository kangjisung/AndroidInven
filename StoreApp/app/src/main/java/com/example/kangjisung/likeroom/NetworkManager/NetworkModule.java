package com.example.kangjisung.likeroom.NetworkManager;

import android.util.Log;

import org.json.JSONObject;

import static android.R.attr.name;

/**
 * Created by stories2 on 2017. 2. 4..
 */

public class NetworkModule {
    HttpCommunicationProcess httpCommunicationProcess;

    String hostName = "stories2.iptime.org:4200", apiName = "/Smoothie/2", logCatTag = "test";

    public NetworkModule() {
    }

    public void TestConnection() {
        httpCommunicationProcess = new HttpCommunicationProcess();
        try {
            Log.d(logCatTag, "Result: " + httpCommunicationProcess.execute("http://" + hostName).get());
        } catch (Exception err) {
            Log.d(logCatTag, "Result: Fail");
        }
    }

    //매장에 고객추가
    public void AddToStoreAsNewMember(int customerId, int storeId) {//유저아이디,매장아이디
        httpCommunicationProcess = new HttpCommunicationProcess();
        String responseRawDate = null;
        try {
            responseRawDate = httpCommunicationProcess.execute("http://" + hostName + apiName + "/AddToStoreAsNewMember/?customerId="+customerId+"&storeId="+storeId+"").get();
            Log.d(logCatTag, responseRawDate);
            JSONObject jsonObject = new JSONObject(responseRawDate);
            if (jsonObject.getString("Result").equals("OK")) {
                Log.d(logCatTag, "ok");
            } else Log.d(logCatTag, jsonObject.getString("Result"));

        } catch (Exception err) {
            Log.d(logCatTag, "Error in AddToStoreAsNewMember: " + err.getMessage());
        }
    }
    //매장에서 고객 삭제
    public void DelMemberFromStore(int customerAndStoreRegisteredId) {//고유등록번호
        httpCommunicationProcess = new HttpCommunicationProcess();
        String responseRawDate = null;
        try {
            responseRawDate = httpCommunicationProcess.execute("http://" + hostName + apiName + "/AddToStoreAsNewMember/?customerAndStoreRegisteredId="+customerAndStoreRegisteredId+"").get();
            Log.d(logCatTag, responseRawDate);
            JSONObject jsonObject = new JSONObject(responseRawDate);
            if (jsonObject.getString("Result").equals("OK")) {
                Log.d(logCatTag, "ok");
            } else Log.d(logCatTag, jsonObject.getString("Result"));

        } catch (Exception err) {
            Log.d(logCatTag, "Error in DelMemberFromStore: " + err.getMessage());
        }
    }

    ///마일리지 업데이트
    public void InsertMileageLog(String customerAndStoreRegisteredId, String mileageSize, String changeDate) { //고유등록번호,마일리지량,바뀐날짜
        httpCommunicationProcess = new HttpCommunicationProcess();
        String responseRawDate = null;
        try {
            responseRawDate = httpCommunicationProcess.execute("http://" + hostName + apiName + "/InsertMileageLog/?customerAndStoreRegisteredId=" + customerAndStoreRegisteredId + "&mileageSize=" + mileageSize + "&changeDate=" + changeDate +"").get();
            Log.d(logCatTag, responseRawDate);
            JSONObject jsonObject = new JSONObject(responseRawDate);
            if (jsonObject.getString("Result").equals("OK")) {
                Log.d(logCatTag, "ok");
            } else Log.d(logCatTag, jsonObject.getString("Result"));

        } catch (Exception err) {
            Log.d(logCatTag, "Error in InsertMileageLog: " + err.getMessage());
        }
    }
    //제품등록
    public void InsertNewProductName(int shopId,int productId, String productName, int primeCost,int cellCost, int remainCost) { //샵 아이디,이름,원가,판매가,잔존가
        httpCommunicationProcess = new HttpCommunicationProcess();
        String responseRawDate = null;
        try {
            responseRawDate = httpCommunicationProcess.execute("http://" + hostName + apiName + "/InsertNewProductName/?shopId=" + shopId +"&productId" +productId+ "&productName=" + productName + "&primeCost=" + primeCost +"&cellCost="+cellCost+"&remainCost"+remainCost+"").get();
            Log.d(logCatTag, responseRawDate);
            JSONObject jsonObject = new JSONObject(responseRawDate);
            if (jsonObject.getString("Result").equals("OK")) {
                Log.d(logCatTag, "ok");
            } else Log.d(logCatTag, jsonObject.getString("Result"));

        } catch (Exception err) {
            Log.d(logCatTag, "Error in InsertNewProductName: " + err.getMessage());
        }
    }

    //제품수정
    public void UpdateRegisteredProductName(int shopId,int productId, String productName, int primeCost,int cellCost, int remainCost) { //샵 아이디,제품코드,이름,원가,판매가,잔존가
        httpCommunicationProcess = new HttpCommunicationProcess();
        String responseRawDate = null;
        try {
            responseRawDate = httpCommunicationProcess.execute("http://" + hostName + apiName + "/InsertNewProductName/?shopId=" + shopId +"&productId="+productId+ "&productName=" + productName + "&primeCost=" + primeCost +"&cellCost="+cellCost+"&remainCost"+remainCost+"").get();
            Log.d(logCatTag, responseRawDate);
            JSONObject jsonObject = new JSONObject(responseRawDate);
            if (jsonObject.getString("Result").equals("OK")) {
                Log.d(logCatTag, "ok");
            } else Log.d(logCatTag, jsonObject.getString("Result"));

        } catch (Exception err) {
            Log.d(logCatTag, "Error in UpdateRegisteredProductName: " + err.getMessage());
        }
    }
    //제품비활성화
    public void UpdateRegisteredProductName(int shopId,int productId) { //샵 아이디,제품코드
        httpCommunicationProcess = new HttpCommunicationProcess();
        String responseRawDate = null;
        try {
            responseRawDate = httpCommunicationProcess.execute("http://" + hostName + apiName + "/InsertNewProductName/?shopId=" + shopId +"&productId="+productId+"").get();
            Log.d(logCatTag, responseRawDate);
            JSONObject jsonObject = new JSONObject(responseRawDate);
            if (jsonObject.getString("Result").equals("OK")) {
                Log.d(logCatTag, "ok");
            } else Log.d(logCatTag, jsonObject.getString("Result"));

        } catch (Exception err) {
            Log.d(logCatTag, "Error in UpdateRegisteredProductName: " + err.getMessage());
        }
    }
    //공지 업로드
    public void InsertNewStoreNoticeInfo(int shopId,String noticeTitle,String noticeBody,String noticeStartDate, String noticeStopDate, String noticeLastUpdateDate) { //샵 아이디,공지제목, 공지내용, 공지시작날짜,공지마감날짜,공지마지막편집날짜(날짜date,string정하기)
        httpCommunicationProcess = new HttpCommunicationProcess();
        String responseRawDate = null;
        try {
            responseRawDate = httpCommunicationProcess.execute("http://" + hostName + apiName + "/InsertNewProductName/?shopId=" + shopId +"&noticeTitle="+noticeTitle+"&noticeBody"+noticeBody+"&noticeStartDate"+noticeStartDate+"&noticeStopDate"+noticeStopDate+"&noticeLastUpdateDate"+noticeLastUpdateDate+"").get();
            Log.d(logCatTag, responseRawDate);
            JSONObject jsonObject = new JSONObject(responseRawDate);
            if (jsonObject.getString("Result").equals("OK")) {
                Log.d(logCatTag, "ok");
            } else Log.d(logCatTag, jsonObject.getString("Result"));

        } catch (Exception err) {
            Log.d(logCatTag, "Error in InsertNewStoreNoticeInfo: " + err.getMessage());
        }
    }

    //공지업데이트
    public void UpdateStoreNoticeInfo(int shopId,int noticeId,String noticeTitle,String noticeBody,String noticeStartDate, String noticeStopDate, String noticeLastUpdateDate) { //샵 아이디,공지번호,공지제목, 공지내용, 공지시작날짜,공지마감날짜,공지마지막편집날짜(날짜date,string정하기)
        httpCommunicationProcess = new HttpCommunicationProcess();
        String responseRawDate = null;
        try {
            responseRawDate = httpCommunicationProcess.execute("http://" + hostName + apiName + "/UpdateStoreNoticeInfo/?shopId=" + shopId +"&noticeId="+noticeId+"&noticeTitle="+noticeTitle+"&noticeBody"+noticeBody+"&noticeStartDate"+noticeStartDate+"&noticeStopDate"+noticeStopDate+"&noticeLastUpdateDate"+noticeLastUpdateDate+"").get();
            Log.d(logCatTag, responseRawDate);
            JSONObject jsonObject = new JSONObject(responseRawDate);
            if (jsonObject.getString("Result").equals("OK")) {
                Log.d(logCatTag, "ok");
            } else Log.d(logCatTag, jsonObject.getString("Result"));

        } catch (Exception err) {
            Log.d(logCatTag, "Error in UpdateStoreNoticeInfo: " + err.getMessage());
        }
    }
    //공지 삭제
    public void DelStoreNoticeInfo(int shopId,int noticeId) { //매장번호,공지번호
        httpCommunicationProcess = new HttpCommunicationProcess();
        String responseRawDate = null;
        try {
            responseRawDate = httpCommunicationProcess.execute("http://" + hostName + apiName + "/DelStoreNoticeInfo/?shopId=" + shopId +"&noticeId="+noticeId+"").get();
            Log.d(logCatTag, responseRawDate);
            JSONObject jsonObject = new JSONObject(responseRawDate);
            if (jsonObject.getString("Result").equals("OK")) {
                Log.d(logCatTag, "ok");
            } else Log.d(logCatTag, jsonObject.getString("Result"));

        } catch (Exception err) {
            Log.d(logCatTag, "Error in DelStoreNoticeInfo: " + err.getMessage());
        }
    }
    //매장추가
    public void InsertNewStoreInfoData(String address, String name, String phone, String serivceRegisterDate) { //매장주소,매장이름,매장전화번호,매장등록날짜
        httpCommunicationProcess = new HttpCommunicationProcess();
        String responseRawDate = null;
        try {
            responseRawDate = httpCommunicationProcess.execute("http://" + hostName + apiName + "/InsertNewStoreInfoData/?address=" + address +"&name="+name+"&phone="+phone+"&serivceRegisterDate="+serivceRegisterDate+"").get();
            Log.d(logCatTag, responseRawDate);
            JSONObject jsonObject = new JSONObject(responseRawDate);
            if (jsonObject.getString("Result").equals("OK")) {
                Log.d(logCatTag, "ok");
            } else Log.d(logCatTag, jsonObject.getString("Result"));

        } catch (Exception err) {
            Log.d(logCatTag, "Error in InsertNewStoreInfoData: " + err.getMessage());
        }
    }

    //매장 업데이트
    public void UpdateStoreInfoData(int storeId,String address, String name, String phone, String updateInfoDate) { //매장번호, 매장주소,매장이름,매장전화번호,매장정보수정날짜(서비스탈퇴여부)
        httpCommunicationProcess = new HttpCommunicationProcess();
        String responseRawDate = null;
        try {
            responseRawDate = httpCommunicationProcess.execute("http://" + hostName + apiName + "/InsertNewStoreInfoData/?storeId="+storeId+"address=" + address +"&name="+name+"&phone="+phone+"&updateInfoDate="+updateInfoDate+"").get();
            Log.d(logCatTag, responseRawDate);
            JSONObject jsonObject = new JSONObject(responseRawDate);
            if (jsonObject.getString("Result").equals("OK")) {
                Log.d(logCatTag, "ok");
            } else Log.d(logCatTag, jsonObject.getString("Result"));

        } catch (Exception err) {
            Log.d(logCatTag, "Error in UpdateStoreInfoData: " + err.getMessage());
        }
    }

    //최적재고량 넣기
    public void InsertProductOptimalStock(int productCode,int optimalStock, String date) { //제품번호, 최적재고량, 계산한 날짜
        httpCommunicationProcess = new HttpCommunicationProcess();
        String responseRawDate = null;
        try {
            responseRawDate = httpCommunicationProcess.execute("http://" + hostName + apiName + "/InsertProductOptimalStock/?productCode="+productCode+"optimalStock=" + optimalStock +"&date="+date+"").get();
            Log.d(logCatTag, responseRawDate);
            JSONObject jsonObject = new JSONObject(responseRawDate);
            if (jsonObject.getString("Result").equals("OK")) {
                Log.d(logCatTag, "ok");
            } else Log.d(logCatTag, jsonObject.getString("Result"));

        } catch (Exception err) {
            Log.d(logCatTag, "Error in UpdateStoreInfoData: " + err.getMessage());
        }
    }
    //예상판매량 넣기
    public void InsertSalesVolume(int productCode,int salesVolume, String date,int projectedSales) { //제품번호, 판매량, 날짜, 예상 판매량
        httpCommunicationProcess = new HttpCommunicationProcess();
        String responseRawDate = null;
        try {
            responseRawDate = httpCommunicationProcess.execute("http://" + hostName + apiName + "/InsertSalesVolume/?productCode="+productCode+"salesVolume=" + salesVolume +"&date="+date+"&projectedSales"+projectedSales+"").get();
            Log.d(logCatTag, responseRawDate);
            JSONObject jsonObject = new JSONObject(responseRawDate);
            if (jsonObject.getString("Result").equals("OK")) {
                Log.d(logCatTag, "ok");
            } else Log.d(logCatTag, jsonObject.getString("Result"));

        } catch (Exception err) {
            Log.d(logCatTag, "Error in UpdateStoreInfoData: " + err.getMessage());
        }
    }
    ////예제
    /*public void LoadAllStoreInfo() {
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
 }


        } catch (Exception err) {
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
            if (jsonObject.getString("Result").equals("Ok")) {
                Log.d(logCatTag, "ok");
            } else {
                Log.d(logCatTag, "fail");
            }
        } catch (Exception err) {
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
            if (jsonObject.getString("Result").equals("Ok")) {
                Log.d(logCatTag, "ok");
            } else {
                Log.d(logCatTag, "fail");
            }
        } catch (Exception err) {
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
            try {
                if (jsonObject.isNull("Result")) {
                    Log.d(logCatTag, "ok");
                } else {
                    Log.d(logCatTag, "fail");
                }
            } catch (Exception err) {
                Log.d(logCatTag, "Error in GetStoreAndCustomerRegisteredInfo: " + err.getMessage());
            }
        } catch (Exception err) {
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
            if (jsonObject.getString("Result").equals("Ok")) {
                Log.d(logCatTag, "ok");
            } else {
                Log.d(logCatTag, "fail");
            }
        } catch (Exception err) {
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
            if (jsonObject.isNull("Result")) {
                Log.d(logCatTag, "ok");
            } else {
                Log.d(logCatTag, "fail");
            }
        } catch (Exception err) {
            Log.d(logCatTag, "Error in GetMileageSum: " + err.getMessage());
        }
    }

    ////내 정보 등록
    public void InsertNewCustomerInfo(String customerName, String customerPhone, String customerEmail, String customerBirth) {
        httpCommunicationProcess = new HttpCommunicationProcess();
        String responseRawDate = null;
        try {
            responseRawDate = httpCommunicationProcess.execute("http://" + hostName + apiName + "/InsertNewCustomerInfo/?name=" + customerName + "&phone=" + customerPhone + "&email=" + customerEmail + "&birthday=" + customerBirth + "").get();
            Log.d(logCatTag, responseRawDate);
            JSONObject jsonObject = new JSONObject(responseRawDate);
            if (jsonObject.getString("Result").equals("OK")) {
                Log.d(logCatTag, "ok");
            } else Log.d(logCatTag, jsonObject.getString("Result"));

        } catch (Exception err) {
            Log.d(logCatTag, "Error in InsertNewCustomerInfo: " + err.getMessage());
        }
    }

    ///////////내 고유코드 받기
    public void LoadCustomerInfo(String customerEmail) {
        httpCommunicationProcess = new HttpCommunicationProcess();
        String responseRawDate = null;
        String uniCode = null;//고유코드
        try {
            responseRawDate = httpCommunicationProcess.execute("http://" + hostName + apiName + "/LoadCustomerInfo/?email=" + customerEmail + "").get();
            Log.d(logCatTag, responseRawDate);
            JSONObject jsonObject = new JSONObject(responseRawDate);
            uniCode = jsonObject.getString("회원번호");
            Log.d(logCatTag, uniCode);
        } catch (Exception err) {
            Log.d(logCatTag, "Error in LoadCustomerInfo: " + err.getMessage());
        }
    }
    //////쿠폰 사용기능
    public void UseTargetCoupon(String customerAndStoreId, String updateDate, String couponId) {
        httpCommunicationProcess = new HttpCommunicationProcess();
        String responseRawDate = null;
        try {
            responseRawDate = httpCommunicationProcess.execute("http://" + hostName + apiName + "/UseTargetCoupon/?customerAndStoreId=" + customerAndStoreId + "&updateDate=" + updateDate + "&couponId=" + couponId + "").get();
            Log.d(logCatTag, responseRawDate);
            JSONObject jsonObject = new JSONObject(responseRawDate);
            if (jsonObject.getString("Result").equals("OK")) {
                Log.d(logCatTag, "ok");
            } else Log.d(logCatTag, jsonObject.getString("Result"));
        } catch (Exception err) {
            Log.d(logCatTag, "Error in UseTargetCoupon: " + err.getMessage());
        }
    }

    /////공지 리스트
    public void ShowTargetStoreNoticeList(String shopId) {
        httpCommunicationProcess = new HttpCommunicationProcess();
        String responseRawDate = null;
        try {
            responseRawDate = httpCommunicationProcess.execute("http://" + hostName + apiName + "/ShowTargetStoreNoticeList/?shopId=" + shopId + "").get();
            Log.d(logCatTag, responseRawDate);
        } catch (Exception err) {
            Log.d(logCatTag, "Error in ShowTargetStoreNoticeList: " + err.getMessage());
        }
    }*/

}

