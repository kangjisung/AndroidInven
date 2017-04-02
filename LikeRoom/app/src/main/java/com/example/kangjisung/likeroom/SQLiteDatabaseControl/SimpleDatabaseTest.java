package com.example.kangjisung.likeroom.SQLiteDatabaseControl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kangjisung.likeroom.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.example.kangjisung.likeroom.DefineManager.customerDatabaseName;
import static com.example.kangjisung.likeroom.DefineManager.databaseShopAddressSavedPoint;
import static com.example.kangjisung.likeroom.DefineManager.databaseShopNameSavedPoint;
import static com.example.kangjisung.likeroom.DefineManager.databaseShopPhoneNumberSavedPoint;

/**
 * Created by stories2 on 2016. 12. 29..
 */

public class SimpleDatabaseTest {
    final int storeIdSavedPoint = 0, isStoreRegisteredToMe = 8; // 코드 가독성을 위해서
    Context context;
    LocalHostDatabaseManager localHostDatabaseManager;
    SQLiteDatabase sqLiteDatabase;
    String databaseSavedPath;

    //테스트
    String[][] allRegisteredStoreInfo = new String[][]{
            {"" + 1, "고덕리엔파크점", "서울특별시 강동구 상일동 75-2", "024268758", "N/A", "N/A", "" + 37.5537472, "" + 127.1716884, "" + 1},
            {"" + 2, "상일세종점", "서울특별시 강동구 상일동 67-2", "024417833", "N/A", "N/A", "" + 37.5509792, "" + 127.1738538, "" + 1},
            {"" + 3, "전화로 세운 건물", "콜로세움", "000000000", "08:30", "15:30", "" + 41.8902102, "" + 12.4922309, "" + 1},
    };
    String[][] eachStoreNoticeInfo = new String[][]{
     {"" + 1, "" + 1, "hello world", "this is test", "2015 2 1", "2016 1 30"},
        {"" + 1, "" + 2, "hi", "can u see me?", "2016 2 22", "2017 8 19"},
        {"" + 1, "" + 2, "max length test aaaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "2016 2 22", "2017 8 19"},
        {"" + 2, "" + 3, "test", "test notice", "2016 1 1", "2017 1 1"},
        {"" + 3, "" + 4, "콜로세움 공지", "빵 안팔아", "0080 1 1", "9999 1 1"},
    };

    public SimpleDatabaseTest() {

    }

    public SimpleDatabaseTest(Context context) {
        this.context = context;
        databaseSavedPath = context.getApplicationInfo().dataDir + "/databases/";
        localHostDatabaseManager = new LocalHostDatabaseManager(context, databaseSavedPath, customerDatabaseName);
        sqLiteDatabase = localHostDatabaseManager.OpenSQLiteDatabase();
        Cursor sqlResult = sqLiteDatabase.rawQuery("select * from `매장`;", null);
        while(sqlResult.moveToNext()) {
            int storeId = sqlResult.getInt(0);
            String storeName = sqlResult.getString(4);
            Log.d(context.getString(R.string.app_name), "data: " + storeId + " " + storeName);
        }
        sqLiteDatabase.close();
    }
    // all에 한 열 그거를 전체를 다 인덱스에 대입 인덱스에 store번지값을 비교를해서(타겟스토어 넘버:찾고자 하는 스토어) 넘버 맞으면 리턴을한다.
    public String[] GetSelectedStoreInfo(int targetStoreNumber) {
        localHostDatabaseManager = new LocalHostDatabaseManager(context, databaseSavedPath, customerDatabaseName);
        sqLiteDatabase = localHostDatabaseManager.OpenSQLiteDatabase();
        Cursor sqlResult = sqLiteDatabase.rawQuery("select * from `매장공지` where `매장번호`=" + targetStoreNumber + ";", null);
        int i=0;
        while(sqlResult.moveToNext()){
            /*allRegisteredStoreInfo[i][0] = sqlResult.getString(sqlResult.getColumnIndex("제목"));
            allRegisteredStoreInfo[i][1] = sqlResult.getString(sqlResult.getColumnIndex("내용"));
            allRegisteredStoreInfo[i][2] = sqlResult.getString(sqlResult.getColumnIndex("공지시작날짜"));
            allRegisteredStoreInfo[i][3] = sqlResult.getString(sqlResult.getColumnIndex("공지마감날짜"));
            allRegisteredStoreInfo[i][4] = sqlResult.getString(sqlResult.getColumnIndex("삭제"));
            allRegisteredStoreInfo[i][5] = sqlResult.getString(sqlResult.getColumnIndex("매장공지이미저장경로"));
            allRegisteredStoreInfo[i][5] = sqlResult.getString(sqlResult.getColumnIndex("읽음여부"));
            i++;*/
            String[] dataTemp = new String[] {"" + targetStoreNumber, sqlResult.getString(sqlResult.getColumnIndex("이름")),
                    sqlResult.getString(sqlResult.getColumnIndex("주소")), sqlResult.getString(sqlResult.getColumnIndex("전화번호")),
                    sqlResult.getString(sqlResult.getColumnIndex("매장개장시간")), sqlResult.getString(sqlResult.getColumnIndex("매장마감시간")),
                    sqlResult.getString(sqlResult.getColumnIndex("위도")), sqlResult.getString(sqlResult.getColumnIndex("경도")),
                    sqlResult.getString(sqlResult.getColumnIndex("회원탈퇴여부"))};
            sqLiteDatabase.close();
            return dataTemp;
        }
        return null;
/*
        for (String[] indexOfStoreInfo: allRegisteredStoreInfo) {
            if(indexOfStoreInfo[storeIdSavedPoint].equals("" + targetStoreNumber)) {
                return indexOfStoreInfo;
            }
        };
        return null;*/
    }

    public ArrayList<String[]> GetSelectedStoreNoticeInfo(int targetStoreId, int length) {
        ArrayList<String[]> selectedStoreNoticeData = new ArrayList<String[]>();
        localHostDatabaseManager = new LocalHostDatabaseManager(context, databaseSavedPath, customerDatabaseName);
        sqLiteDatabase = localHostDatabaseManager.OpenSQLiteDatabase();
        Cursor sqlResult = sqLiteDatabase.rawQuery("select `매장번호`, `공지번호`, `제목`, `내용`, `공지시작날짜`, `공지마감날짜` from `매장공지` where `매장번호` = " + targetStoreId + ";", null);
        while(sqlResult.moveToNext()) {
            String[] dataTemp = new String[]{
                                            sqlResult.getString(sqlResult.getColumnIndex("매장번호")), sqlResult.getString(sqlResult.getColumnIndex("공지번호")),
                                            sqlResult.getString(sqlResult.getColumnIndex("제목")), sqlResult.getString(sqlResult.getColumnIndex("내용")),
                                            sqlResult.getString(sqlResult.getColumnIndex("공지시작날짜")), sqlResult.getString(sqlResult.getColumnIndex("공지마감날짜"))
                                            };
            selectedStoreNoticeData.add(dataTemp);
        }
        sqLiteDatabase.close();
        return selectedStoreNoticeData;
        /*
        for (String[] indexOfStoreNotice: eachStoreNoticeInfo) {
            if(indexOfStoreNotice[storeIdSavedPoint].equals("" + targetStoreId)) {
                selectedStoreNoticeData.add(indexOfStoreNotice);
            }
        };
        return selectedStoreNoticeData;*/
    }

    public ArrayList<String[]> GetStoreWhichIRegistered() {
        ArrayList<String[]> registeredToMe = new ArrayList<String[]>();
        localHostDatabaseManager = new LocalHostDatabaseManager(context, databaseSavedPath, customerDatabaseName);
        sqLiteDatabase = localHostDatabaseManager.OpenSQLiteDatabase();
        Cursor sqlResult = sqLiteDatabase.rawQuery("select `매장번호`, `이름`, `주소`, `전화번호`, `매장개장시간`, `매장마감시간`, `위도`, `경도`, `회원탈퇴여부` from `매장` where `회원탈퇴여부` = 0;", null);

        while(sqlResult.moveToNext()){
            String[] dataTemp = new String[] {sqlResult.getString(sqlResult.getColumnIndex("매장번호")), sqlResult.getString(sqlResult.getColumnIndex("이름")),
                    sqlResult.getString(sqlResult.getColumnIndex("주소")), sqlResult.getString(sqlResult.getColumnIndex("전화번호")),
                    sqlResult.getString(sqlResult.getColumnIndex("매장개장시간")), sqlResult.getString(sqlResult.getColumnIndex("매장마감시간")),
                    sqlResult.getString(sqlResult.getColumnIndex("위도")), sqlResult.getString(sqlResult.getColumnIndex("경도")),
                    sqlResult.getString(sqlResult.getColumnIndex("회원탈퇴여부"))};
            registeredToMe.add(dataTemp);
        }
        sqLiteDatabase.close();
        return registeredToMe;
        /*for(String[] test: allRegisteredStoreInfo) {
            Log.d("LikeRoom", "get: " + Arrays.toString(test));
        }
        for(String[] indexOfStoreInfo: allRegisteredStoreInfo) {
            if(indexOfStoreInfo[isStoreRegisteredToMe].equals("0")) {
                Log.d("LikeRoom", "get registered store: " + Arrays.toString(indexOfStoreInfo));
                registeredToMe.add(indexOfStoreInfo);
            }
        }
        return registeredToMe;*/
    }

    public void AddSelectedShop(int targetStoreId) {

        localHostDatabaseManager = new LocalHostDatabaseManager(context, databaseSavedPath, customerDatabaseName);
        sqLiteDatabase = localHostDatabaseManager.OpenSQLiteDatabase();
        sqLiteDatabase.execSQL("update `매장` set `회원탈퇴여부` = 0 where `매장번호` = " + targetStoreId);

        sqLiteDatabase.close();
        return ;
        /*for(String[] indexOfStoreInfo: allRegisteredStoreInfo) {
            if(indexOfStoreInfo[storeIdSavedPoint].equals("" + targetStoreId)) {
                indexOfStoreInfo[isStoreRegisteredToMe] = "0";
                return;
            }
        }*/
    }

    public void AddStoreAndCustomerUniqueId(int targetStoreId, int uniqueId) {
        localHostDatabaseManager = new LocalHostDatabaseManager(context, databaseSavedPath, customerDatabaseName);
        sqLiteDatabase = localHostDatabaseManager.OpenSQLiteDatabase();
        sqLiteDatabase.execSQL("update `매장` set `고유등록번호` = " + uniqueId + " where `매장번호` = " + targetStoreId);

        sqLiteDatabase.close();
    }

    public void DeleteSelectedShop(int targetStoreId) {
        Log.d("LikeRoom", "delete order accepted");
        localHostDatabaseManager = new LocalHostDatabaseManager(context, databaseSavedPath, customerDatabaseName);
        sqLiteDatabase = localHostDatabaseManager.OpenSQLiteDatabase();
        sqLiteDatabase.execSQL("update `매장` set `회원탈퇴여부` = 1 where `매장번호` = " + targetStoreId);

        sqLiteDatabase.close();
        /*for(String[] indexOfStoreInfo: allRegisteredStoreInfo) {
            if(indexOfStoreInfo[storeIdSavedPoint].equals("" + targetStoreId)) {
                indexOfStoreInfo[isStoreRegisteredToMe] = "1";
                Log.d("LikeRoom", "deleted " + targetStoreId);
            }
        }
        for(String[] test: allRegisteredStoreInfo) {
            Log.d("LikeRoom", "del: " + Arrays.toString(test));
        }*/
    }

    public ArrayList<String[]> GetNotRegisteredStoreList() {
        ArrayList<String[]> notRegisteredStoreList = new ArrayList<String[]>();

        localHostDatabaseManager = new LocalHostDatabaseManager(context, databaseSavedPath, customerDatabaseName);
        sqLiteDatabase = localHostDatabaseManager.OpenSQLiteDatabase();
        Cursor sqlResult = sqLiteDatabase.rawQuery("select `매장번호`, `이름`, `주소`, `전화번호`, `매장개장시간`, `매장마감시간`, `위도`, `경도`, `회원탈퇴여부` from `매장` where `회원탈퇴여부` = 1;", null);

        while(sqlResult.moveToNext()){
            String[] dataTemp = new String[] {sqlResult.getString(sqlResult.getColumnIndex("매장번호")), sqlResult.getString(sqlResult.getColumnIndex("이름")),
                    sqlResult.getString(sqlResult.getColumnIndex("주소")), sqlResult.getString(sqlResult.getColumnIndex("전화번호")),
                    sqlResult.getString(sqlResult.getColumnIndex("매장개장시간")), sqlResult.getString(sqlResult.getColumnIndex("매장마감시간")),
                    sqlResult.getString(sqlResult.getColumnIndex("위도")), sqlResult.getString(sqlResult.getColumnIndex("경도")),
                    sqlResult.getString(sqlResult.getColumnIndex("회원탈퇴여부"))};
            notRegisteredStoreList.add(dataTemp);
        }
        sqLiteDatabase.close();
        /*for(String[] indexOfStoreInfo: allRegisteredStoreInfo) {
            if(indexOfStoreInfo[isStoreRegisteredToMe].equals("1")) {
                notRegisteredStoreList.add(indexOfStoreInfo);
            }
        }*/
        return notRegisteredStoreList;
    }

    public ArrayList<String[]> GetSimillarStoreInfoSearched(String typeAndFind) {
        ArrayList<String[]> searchedStoreList = GetNotRegisteredStoreList(),
        resultOfSearchedStoreList = new ArrayList<String[]>();



        if(searchedStoreList == null)
            return null;
        for(String[] indexOfStoreInfo: searchedStoreList) {
            if(indexOfStoreInfo[databaseShopNameSavedPoint].contains(typeAndFind) || indexOfStoreInfo[databaseShopAddressSavedPoint].contains(typeAndFind)
                    || indexOfStoreInfo[databaseShopPhoneNumberSavedPoint].contains(typeAndFind)) {
                resultOfSearchedStoreList.add(indexOfStoreInfo);
            }
        }
        return resultOfSearchedStoreList;
    }

    public void SetAllStoreInfoData(List<String[]> allStoreData) {
        try {
            localHostDatabaseManager = new LocalHostDatabaseManager(context, databaseSavedPath, customerDatabaseName);
            sqLiteDatabase = localHostDatabaseManager.OpenSQLiteDatabase();

            for(String[] eachStoreInfoData: allStoreData) {
                if(eachStoreInfoData[2].equals("null")) {
                    eachStoreInfoData[2] = "0";
                }
                if(eachStoreInfoData[3].equals("null")) {
                    eachStoreInfoData[3] = "0";
                }
                String eachStoreInsertQuery = "insert into `매장` (`매장번호`, `주소`, `위도`, `경도`, `이름`, `전화번호`, `매장개장시간`, `매장마감시간`) select " +
                        eachStoreInfoData[0] + " as `매장번호`, '" + eachStoreInfoData[1] + "' as `주소`, " +
                        eachStoreInfoData[2] + " as `위도`, " + eachStoreInfoData[3] + " as `경도`, '" +
                        eachStoreInfoData[4] + "' as `이름`, '" + eachStoreInfoData[5] + "' as `전화번호`, '" +
                        eachStoreInfoData[6] + "' as `매장개장시간`, '" + eachStoreInfoData[7] + "' as `매장마감시간` " +
                        "where not exists (select " + eachStoreInfoData[0] + " from " +
                        "`매장` where `매장번호` = " + eachStoreInfoData[0] + ");";
                Log.d("test", eachStoreInsertQuery);
                sqLiteDatabase.execSQL(eachStoreInsertQuery);
            }
            sqLiteDatabase.close();
        }
        catch (Exception err) {
            Log.d("test", "Error in SetAllStoreInfoData: " + err.getMessage());
        }
    }

    public void InsertCustomerInfo(String[] customerInfo) {
        try {
            localHostDatabaseManager = new LocalHostDatabaseManager(context, databaseSavedPath, customerDatabaseName);
            sqLiteDatabase = localHostDatabaseManager.OpenSQLiteDatabase();

            String customerInfoInsertQuery = "insert into `회원정보` (`회원번호`, `이름`, `전화번호`, `이메일`, `생년월일`) select " +
                    customerInfo[0] + " as `회원번호`, '" + customerInfo[1] + "' as `이름`, '" + customerInfo[2] +
                    "' as `전화번호`, '" + customerInfo[3] + "' as `이메일`, '" + customerInfo[4] +
                    "' as `생년월일` where not exists (select " + customerInfo[0] + " from `회원정보` where `회원번호` = " + customerInfo[0] + ");";
            sqLiteDatabase.execSQL(customerInfoInsertQuery);

            sqLiteDatabase.close();
        }
        catch (Exception err) {
            Log.d("test", "Error in InsertCustomerInfo: " + err.getMessage());
        }
    }

    public String[] GetCustomerInfo() {
        String[] customerInfo = null;
        try {
            localHostDatabaseManager = new LocalHostDatabaseManager(context, databaseSavedPath, customerDatabaseName);
            sqLiteDatabase = localHostDatabaseManager.OpenSQLiteDatabase();

            String gettingCustomerInfoQuery = "select * from `회원정보` limit 1;";
            Cursor sqlResult = sqLiteDatabase.rawQuery(gettingCustomerInfoQuery, null);

            //sqlResult.getString(sqlResult.getColumnIndex(
            sqlResult.moveToFirst();
            customerInfo = new String[5];
            customerInfo[0] = sqlResult.getString(sqlResult.getColumnIndex("회원번호"));
            customerInfo[1] = sqlResult.getString(sqlResult.getColumnIndex("이름"));
            customerInfo[2] = sqlResult.getString(sqlResult.getColumnIndex("전화번호"));
            customerInfo[3] = sqlResult.getString(sqlResult.getColumnIndex("이메일"));
            customerInfo[4] = sqlResult.getString(sqlResult.getColumnIndex("삭제"));
            Log.d("test", "customer info: " + Arrays.toString(customerInfo));
            sqLiteDatabase.close();
        }
        catch (Exception err) {
            Log.d("test", "Error in GetCustomerInfo: " + err.getMessage());
        }
        return customerInfo;
    }

    public void SaveStoreNoticeData(List<String[]> storeNoticeData) {
        try {
            localHostDatabaseManager = new LocalHostDatabaseManager(context, databaseSavedPath, customerDatabaseName);
            sqLiteDatabase = localHostDatabaseManager.OpenSQLiteDatabase();

            for(String[] eachNoticeData : storeNoticeData) {
                Log.d(context.getString(R.string.app_name), "Save notice: " + Arrays.toString(eachNoticeData));
                sqLiteDatabase.execSQL("insert into `매장공지` (`매장번호`, `공지번호`, `제목`, `내용`, `공지시작날짜`, `공지마감날짜`) select "+
                        eachNoticeData[0] + " as `매장번호`, " + eachNoticeData[1] + " as `공지번호`, '" + eachNoticeData[2] +
                        "' as `제목`, '" + eachNoticeData[3] + "' as `내용`, '" + eachNoticeData[4] + "' as `공지시작날짜`, '" +
                        eachNoticeData[5] + "' as `공지마감날짜` where not exists (select " + eachNoticeData[1] +
                        " from `매장공지` where `공지번호` = " + eachNoticeData[1] + ");");
            }

            sqLiteDatabase.close();
        }
        catch (Exception err) {
            Log.d("test", "Error in SaveStoreNoticeData: " + err.getMessage());
        }
    }
}
