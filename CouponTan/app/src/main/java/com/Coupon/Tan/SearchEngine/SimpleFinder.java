package com.Coupon.Tan.SearchEngine;

import com.Coupon.Tan.DataIOManager.DemoDataPusher;

/**
 * Created by stories2 on 2016. 12. 10..
 */

public class SimpleFinder {

    DemoDataPusher demoDataPusher;
    final String unregisteredStore = "1", registeredStore = "0";
    final int zeroRow = 0, isRegisteredStoreColumn = 5, storeAddressInfo = 1, storeNameInfo = 4;
    final boolean iFoundThis = true;

    public SimpleFinder() {
        demoDataPusher = new DemoDataPusher();
    }

    public boolean[] DemoFindDataThatContainsString(String findThisData) {
        String[][] savedData = demoDataPusher.GetAllSavedData();
        int savedDataLength = savedData.length, i;
        boolean[] whatUserFinding = new boolean[savedDataLength];
        for(i = zeroRow; i < savedDataLength; i += 1) {
            if(savedData[i][isRegisteredStoreColumn].equals(unregisteredStore)) {//등록되지 않은 상점
                if(savedData[i][storeAddressInfo].contains(findThisData) || savedData[i][storeNameInfo].contains(findThisData)) {
                    whatUserFinding[i] = iFoundThis;
                }
            }
        }
        return whatUserFinding;
    }

    public boolean[] DemoFindDataThatUserRegisteredStore() {
        String[][] savedStoreData = demoDataPusher.GetAllSavedData();
        int savedDataLenth = savedStoreData.length, i;
        boolean[] userRegisteredStore = new boolean[savedDataLenth];
        for(i = zeroRow; i < savedDataLenth; i += 1) {
            if(savedStoreData[i][isRegisteredStoreColumn].equals(registeredStore)) {
                userRegisteredStore[i] = iFoundThis;
            }
        }
        return userRegisteredStore;
    }
}
