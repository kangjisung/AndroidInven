package com.example.kangjisung.likeroom.SQLiteDatabaseControl;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.kangjisung.likeroom.MainActivity;
import com.example.kangjisung.likeroom.R;

import java.util.ArrayList;
import java.util.Arrays;

import static android.R.attr.max;
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

    //테스트
    String[][] allRegisteredStoreInfo = new String[][]{
            /*{"" + 1, "고덕리엔파크점", "서울특별시 강동구 상일동 75-2", "024268758", "N/A", "N/A", "" + 37.5537472, "" + 127.1716884, "" + 1},
            {"" + 2, "상일세종점", "서울특별시 강동구 상일동 67-2", "024417833", "N/A", "N/A", "" + 37.5509792, "" + 127.1738538, "" + 1},
            {"" + 3, "전화로 세운 건물", "콜로세움", "000000000", "08:30", "15:30", "" + 41.8902102, "" + 12.4922309, "" + 1},*/
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
        localHostDatabaseManager = new LocalHostDatabaseManager(context, context.getApplicationInfo().dataDir + "/databases/", customerDatabaseName);
        sqLiteDatabase = localHostDatabaseManager.OpenSQLiteDatabase();
        Cursor sqlResult = sqLiteDatabase.rawQuery("select * from `매장`;", null);
        while(sqlResult.moveToNext()) {
            int storeId = sqlResult.getInt(0);
            String storeName = sqlResult.getString(4);
            Log.d(context.getString(R.string.app_name), "data: " + storeId + " " + storeName);
        }
    }
    // all에 한 열 그거를 전체를 다 인덱스에 대입 인덱스에 store번지값을 비교를해서(타겟스토어 넘버:찾고자 하는 스토어) 넘버 맞으면 리턴을한다.
    public String[] GetSelectedStoreInfo(int targetStoreNumber) {
        localHostDatabaseManager = new LocalHostDatabaseManager(context, context.getApplicationInfo().dataDir+ "/databases/", customerDatabaseName);
        sqLiteDatabase = localHostDatabaseManager.OpenSQLiteDatabase();
        Cursor sqlResult = sqLiteDatabase.rawQuery("select * from `매장공지` where `매장번호`=targetStoreId;", null);
        int i=0;
        while(sqlResult.moveToNext()){
            allRegisteredStoreInfo[i][0] = sqlResult.getString(sqlResult.getColumnIndex("제목"));
            allRegisteredStoreInfo[i][1] = sqlResult.getString(sqlResult.getColumnIndex("내용"));
            allRegisteredStoreInfo[i][2] = sqlResult.getString(sqlResult.getColumnIndex("공지시작날짜"));
            allRegisteredStoreInfo[i][3] = sqlResult.getString(sqlResult.getColumnIndex("공지마감날짜"));
            allRegisteredStoreInfo[i][4] = sqlResult.getString(sqlResult.getColumnIndex("삭제"));
            allRegisteredStoreInfo[i][5] = sqlResult.getString(sqlResult.getColumnIndex("매장공지이미저장경로"));
            allRegisteredStoreInfo[i][5] = sqlResult.getString(sqlResult.getColumnIndex("읽음여부"));
            i++;
        }

        for (String[] indexOfStoreInfo: allRegisteredStoreInfo) {
            if(indexOfStoreInfo[storeIdSavedPoint].equals("" + targetStoreNumber)) {
                return indexOfStoreInfo;
            }
        };
        return null;
    }

    public ArrayList<String[]> GetSelectedStoreNoticeInfo(int targetStoreId, int length) {
        ArrayList<String[]> selectedStoreNoticeData = new ArrayList<String[]>();
        for (String[] indexOfStoreNotice: eachStoreNoticeInfo) {
            if(indexOfStoreNotice[storeIdSavedPoint].equals("" + targetStoreId)) {
                selectedStoreNoticeData.add(indexOfStoreNotice);
            }
        };
        return selectedStoreNoticeData;
    }

    public ArrayList<String[]> GetStoreWhichIRegistered() {
        for(String[] test: allRegisteredStoreInfo) {
            Log.d("LikeRoom", "get: " + Arrays.toString(test));
        }
        ArrayList<String[]> registeredToMe = new ArrayList<String[]>();
        for(String[] indexOfStoreInfo: allRegisteredStoreInfo) {
            if(indexOfStoreInfo[isStoreRegisteredToMe].equals("0")) {
                Log.d("LikeRoom", "get registered store: " + Arrays.toString(indexOfStoreInfo));
                registeredToMe.add(indexOfStoreInfo);
            }
        }
        return registeredToMe;
    }

    public void AddSelectedShop(int targetStoreId) {
        for(String[] indexOfStoreInfo: allRegisteredStoreInfo) {
            if(indexOfStoreInfo[storeIdSavedPoint].equals("" + targetStoreId)) {
                indexOfStoreInfo[isStoreRegisteredToMe] = "0";
                return;
            }
        }
    }

    public void DeleteSelectedShop(int targetStoreId) {
        Log.d("LikeRoom", "delete order accepted");
        for(String[] indexOfStoreInfo: allRegisteredStoreInfo) {
            if(indexOfStoreInfo[storeIdSavedPoint].equals("" + targetStoreId)) {
                indexOfStoreInfo[isStoreRegisteredToMe] = "1";
                Log.d("LikeRoom", "deleted " + targetStoreId);
            }
        }
        for(String[] test: allRegisteredStoreInfo) {
            Log.d("LikeRoom", "del: " + Arrays.toString(test));
        }
    }

    public ArrayList<String[]> GetNotRegisteredStoreList() {
        ArrayList<String[]> notRegisteredStoreList = new ArrayList<String[]>();
        for(String[] indexOfStoreInfo: allRegisteredStoreInfo) {
            if(indexOfStoreInfo[isStoreRegisteredToMe].equals("1")) {
                notRegisteredStoreList.add(indexOfStoreInfo);
            }
        }
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
}
