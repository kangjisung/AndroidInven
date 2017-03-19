package com.teamdk.android.bakery.utility.NetworkManager;

import android.util.Log;

import com.teamdk.android.bakery.MainActivity;
import com.teamdk.android.bakery.utility.SQLiteDatabaseControl.ClientDataBase;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.teamdk.android.bakery.MainActivity.PriNum;
import static com.teamdk.android.bakery.objectmanager.ProductObjectManager.context;
import static com.teamdk.android.bakery.utility.SQLiteDatabaseControl.ClientDataBase.DBstring;

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

    //매장에 등록된 고객 불러오기
    public void GetCustomerRegisteredInfo(int storeId,String date) {//매장번호,최신 업뎃 날짜
        httpCommunicationProcess = new HttpCommunicationProcess();
        String responseRawDate = null;
        int i;
        try {
            responseRawDate = httpCommunicationProcess.execute("http://" + hostName + apiName + "/GetCustomerRegisteredInfo/?storeId=" + storeId + "&date=" + date + "").get();
            Log.d(logCatTag, responseRawDate);
            JSONObject jsonObject = new JSONObject(responseRawDate);
            JSONObject indexOfZeroStoreInfoData = jsonObject.getJSONObject("0");

        } catch (Exception err) {
            Log.d(logCatTag, "Error in LoadAllStoreInfo: " + err.getMessage());
<<<<<<< HEAD:StoreApp/app/src/main/java/com/teamdk/android/bakery/utility/NetworkManager/NetworkModule.java
        }
    }

    //매장에 등록된 고객 불러오기
    public List<String[]> GetCustomerRegisteredInfo(int storeId, String date){//매장번호,최신 업뎃 날짜
        if(date==null)
        httpCommunicationProcess = new HttpCommunicationProcess();
        String responseRawData = null;
        String[] eachStoreInfoData = new String[10];
        List<String[]> allCustomerInfoData = new ArrayList<String[]>();
        int i;
        try {
            responseRawData = httpCommunicationProcess.execute("http://" + hostName + apiName + "/GetCustomerRegisteredInfo/?storeId=" + storeId + "&date=" + date + "").get();
            Log.d(logCatTag, responseRawData);
            //JSONArray serverResponseArrayData = new JSONArray("{'test':1}");
            JSONObject jsonObject = new JSONObject(responseRawData);
            //Log.d(logCatTag, "test: " + jsonObject.getJSONObject("0"));
            int indexOfStoreNumber = 0;
            while(true) {
                if(jsonObject.isNull("" + indexOfStoreNumber)) {
                    break;
                }
                JSONObject indexOfZeroStoreInfoData = jsonObject.getJSONObject("" + indexOfStoreNumber);
                allCustomerInfoData.add(new String[]{
                        indexOfZeroStoreInfoData.getString("전화번호"),
                        indexOfZeroStoreInfoData.getString("회원번호"),
                        indexOfZeroStoreInfoData.getString("생일"),
                        indexOfZeroStoreInfoData.getString("회원탈퇴여부"),
                        indexOfZeroStoreInfoData.getString("이메일"),
                        indexOfZeroStoreInfoData.getString("회원비활성화"),
                        indexOfZeroStoreInfoData.getString("정보 변경 날짜"),
                        indexOfZeroStoreInfoData.getString("고유등록번호"),
                        indexOfZeroStoreInfoData.getString("이름"),
                });
                Log.d(logCatTag, "전화번호: " + indexOfZeroStoreInfoData.getString("전화번호") +
                        "\n회원번호: " + indexOfZeroStoreInfoData.getString("회원번호") +
                        "\n생일: " + indexOfZeroStoreInfoData.getString("생일") +
                        "\n회원탈퇴여부: " + indexOfZeroStoreInfoData.getString("회원탈퇴여부") +
                        "\n이메일: " + indexOfZeroStoreInfoData.getString("이메일") +
                        "\n회원비활성화" + indexOfZeroStoreInfoData.getString("회원비활성화") +
                        "\n정보 변경 날짜: " + indexOfZeroStoreInfoData.getString("정보 변경 날짜") +
                        "\n고유등록번호: " + indexOfZeroStoreInfoData.getString("고유등록번호") +
                        "\n이름: " + indexOfZeroStoreInfoData.getString("이름"));
                new ClientDataBase("select `회원탈퇴여부` from `회원정보` where `고유회원등록번호`="+indexOfZeroStoreInfoData.getString("고유회원등록번호")+"",1,1, MainActivity.con);
                if(DBstring[0]==null)//회원이 없을때 넣기
                {
                    new ClientDataBase("insert into `회원정보` (`고유회원등록번호`,`이름`,`전화번호`,`생년월일`,`이메일`,`수정일`) values(\""+indexOfZeroStoreInfoData.getString("고유등록번호")+"\"," +
                            "\""+indexOfZeroStoreInfoData.getString("이름")+"\",\""+indexOfZeroStoreInfoData.getString("전화번호")+"\",\""+indexOfZeroStoreInfoData.getString("생일")+"\"," +
                            "\""+indexOfZeroStoreInfoData.getString("이메일")+"\",\""+indexOfZeroStoreInfoData.getString("정보 변경 날짜")+"\")",2,0,MainActivity.con);
                    new ClientDataBase("insert into `회원매장등록정보` (`고유회원등록번호`,`회원번호`,`매장번호`,`탈퇴여부`) values(\""+indexOfZeroStoreInfoData.getString("고유회원등록번호")+"\"," +
                            "\""+indexOfZeroStoreInfoData.getString("회원번호")+"\",\""+PriNum+"\",\""+indexOfZeroStoreInfoData.getString("회원탈퇴여부")+"\")",2,0,MainActivity.con);
                }
                else if(DBstring[0]=="0")//회원이 있을떄 업데이트
                {
                    new ClientDataBase("update `회원정보` set `이름`=\""+indexOfZeroStoreInfoData.getString("이름")+"\",`전화번호`=\""+indexOfZeroStoreInfoData.getString("전화번호")+"\"," +
                            "`생년월일`=\""+indexOfZeroStoreInfoData.getString("생일")+"\",`이메일`=\""+indexOfZeroStoreInfoData.getString("이메일")+"\"," +
                            "`수정일`=\""+indexOfZeroStoreInfoData.getString("정보 변경 날짜")+"\" where `고유회원등록번호`=\""+indexOfZeroStoreInfoData.getString("고유회원등록번호")+"\"",2,0,MainActivity.con);
                }
                else//회원이 탈퇴했을때
                {
                    new ClientDataBase("delete from `회원정보` where `고유회원등록번호`=\""+indexOfZeroStoreInfoData.getString("고유회원등록번호")+"\"",2,0, MainActivity.con);
                    new ClientDataBase("delete from `회원매장등록정보` where `고유회원등록번호`=\""+indexOfZeroStoreInfoData.getString("고유회원등록번호")+"\"",2,0, MainActivity.con);
                }
                indexOfStoreNumber = indexOfStoreNumber + 1;
            }
        }
        catch (Exception err) {
            Log.d(logCatTag, "Error in GetCustomerRegisteredInfo: " + err.getMessage());
=======
>>>>>>> refs/remotes/origin/store-app-byeongmun:StoreApp/app/src/main/java/com/teamdk/android/bakery/utility/NetworkManager/NetworkModule.java
        }
        return allCustomerInfoData;
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
    public void InsertNewStoreNoticeInfo(int shopId,int noticeId,String noticeTitle,String noticeBody,String noticeStartDate, String noticeStopDate, String noticeLastUpdateDate) { //샵 아이디,공지번호,공지제목, 공지내용, 공지시작날짜,공지마감날짜,공지마지막편집날짜
        httpCommunicationProcess = new HttpCommunicationProcess();
        String responseRawDate = null;
        try {
            Log.d("test", "http://" + hostName + apiName + "/InsertNewStoreNoticeInfo/?shopId=" + shopId +"&noticeId="+noticeId+"&noticeTitle="+noticeTitle+"&noticeBody="+noticeBody+"&noticeStartDate="+noticeStartDate+"&noticeStopDate="+noticeStopDate+"&noticeLastUpdateDate="+noticeLastUpdateDate);
            responseRawDate = httpCommunicationProcess.execute("http://" + hostName + apiName + "/InsertNewStoreNoticeInfo/?shopId=" + shopId +"&noticeId="+noticeId+"&noticeTitle="+noticeTitle+"&noticeBody="+noticeBody+"&noticeStartDate="+noticeStartDate+"&noticeStopDate="+noticeStopDate+"&noticeLastUpdateDate="+noticeLastUpdateDate+"").get();
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
    public void UpdateStoreNoticeInfo(int shopId,int noticeId,String noticeTitle,String noticeBody,String noticeStartDate, String noticeStopDate) { //샵 아이디,공지번호,공지제목, 공지내용, 공지시작날짜,공지마감날짜
        httpCommunicationProcess = new HttpCommunicationProcess();
        String responseRawDate = null;
        try {
            responseRawDate = httpCommunicationProcess.execute("http://" + hostName + apiName + "/UpdateStoreNoticeInfo/?shopId=" + shopId +"&noticeId="+noticeId+"&noticeTitle="+noticeTitle+"&noticeBody="+noticeBody+"&noticeStartDate="+noticeStartDate+"&noticeStopDate="+noticeStopDate+"").get();
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
    public void InsertNewStoreInfoData(String address, String name, String phone) { //매장주소,매장이름,매장전화번호,String serivceRegisterDate매장등록날짜
        httpCommunicationProcess = new HttpCommunicationProcess();
        String responseRawDate = null;
        try {
            responseRawDate = httpCommunicationProcess.execute("http://" + hostName + apiName + "/InsertNewStoreInfoData/?address=" + address +"&name="+name+"&phone="+phone+"").get();
            //&serivceRegisterDate="+serivceRegisterDate+"
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

}

