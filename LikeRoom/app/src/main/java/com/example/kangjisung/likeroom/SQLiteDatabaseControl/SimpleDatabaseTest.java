package com.example.kangjisung.likeroom.SQLiteDatabaseControl;

import java.util.ArrayList;

/**
 * Created by stories2 on 2016. 12. 29..
 */

public class SimpleDatabaseTest {
    final int storeIdSavedPoint = 0, isStoreRegisteredToMe = 8;

    String[][] allRegisteredStoreInfo = new String[][]{
            {"" + 1, "고덕리엔파크점", "서울특별시 강동구 상일동 75-2", "02-426-8758", "N/A", "N/A", "" + 37.5537472, "" + 127.1716884, "" + 0},
            {"" + 2, "상일세종점", "서울특별시 강동구 상일동 67-2", "02-441-7833", "N/A", "N/A", "" + 37.5509792, "" + 127.1738538, "" + 0},
            {"" + 3, "전화로 세운 건물", "콜로세움", "00-000-0000", "08:30", "15:30", "" + 41.8902102, "" + 12.4922309, "" + 1},
    };
    String[][] eachStoreNoticeInfo = new String[][]{
        {"" + 1, "" + 1, "hello world", "this is test", "2015 2 1", "2016 1 30"},
        {"" + 1, "" + 2, "hi", "can u see me?", "2016 2 22", "2017 8 19"},
        {"" + 1, "" + 2, "max length test aaaaaaaaaaaaaaaaaaaaa", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa", "2016 2 22", "2017 8 19"},
        {"" + 2, "" + 3, "test", "test notice", "2016 1 1", "2017 1 1"},
        {"" + 3, "" + 4, "콜로세움 공지", "빵 안팔아", "0080 1 1", "9999 1 1"},
    };

    public String[] GetSelectedStoreInfo(int targetStoreNumber) {
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
        ArrayList<String[]> registeredToMe = new ArrayList<String[]>();
        for(String[] indexOfStoreInfo: allRegisteredStoreInfo) {
            if(indexOfStoreInfo[isStoreRegisteredToMe].equals("0")) {
                registeredToMe.add(indexOfStoreInfo);
            }
        }
        return registeredToMe;
    }

    public void DeleteSelectedShop(int targetStoreId) {
        for(String[] indexOfStoreInfo: allRegisteredStoreInfo) {
            if(indexOfStoreInfo[storeIdSavedPoint].equals("" + targetStoreId)) {
                indexOfStoreInfo[isStoreRegisteredToMe] = "1";
            }
        }
    }
}
