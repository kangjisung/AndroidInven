package com.example.kangjisung.likeroom.SQLiteDatabaseControl;

import android.content.Context;
import android.util.Log;

import com.example.kangjisung.likeroom.NetworkManager.NetworkModule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by stories2 on 2017. 2. 11..
 */

public class SynchronizedLocalAndServerDatabase extends Thread{

    SimpleDatabaseTest simpleDatabaseTest;
    Context context;
    NetworkModule networkModule;

    public SynchronizedLocalAndServerDatabase(Context context, NetworkModule networkModule) {
        this.networkModule = networkModule;
        this.context = context;
        simpleDatabaseTest = new SimpleDatabaseTest(context);
    }

    public void SettingAllStoreDataFromServer() {
        //simpleDatabaseTest.SetAllStoreInfoData();
        simpleDatabaseTest.SetAllStoreInfoData(networkModule.LoadAllStoreInfo());
    }

    public void RegisterMyInfoToServer(String customerName, String customerPhone, String customerEmail, String customerBirth) {
        networkModule.InsertNewCustomerInfo(customerName, customerPhone, customerEmail, customerBirth);
        simpleDatabaseTest.InsertCustomerInfo(networkModule.LoadCustomerInfo(customerEmail));
    }

    public void RegisterCustomerToStore() {
        String[] customerInfo = simpleDatabaseTest.GetCustomerInfo();
        ArrayList<String[]> storeInfoWhichIRegistered = simpleDatabaseTest.GetStoreWhichIRegistered();
        for(String[] eachStoreRegistered : storeInfoWhichIRegistered) {
            networkModule.AddToStoreAsNewMember(Integer.parseInt(customerInfo[0]), Integer.parseInt(eachStoreRegistered[0]));
        }
    }

    public int GetMileageStatusFromTargetStore(int storeId) {
        return networkModule.GetMileageSum(storeId);
    }

    public void UseMileageFromTargetStore(int uniqueRegisteredId, int mileageSize) {
        networkModule.InsertMileageLog(uniqueRegisteredId, mileageSize);
    }

    public void GetStoreNoticeFromServer(String shopId) {
        simpleDatabaseTest.SaveStoreNoticeData(networkModule.ShowTargetStoreNoticeList(shopId));
    }

    public void GetStoreAndCustomerRegisteredInfo() {
        int shopId = 1, uniqueId = 2, myId = 0;
        String[] myInfo = simpleDatabaseTest.GetCustomerInfo();
        Log.d("infoTest", Arrays.toString(myInfo));
        List<String[]> userRegisteredStoreInfo = networkModule.GetStoreAndCustomerRegisteredInfo(Integer.parseInt(myInfo[myId]));
        for(String[] eachRegisteredStoreInfo : userRegisteredStoreInfo) {
            simpleDatabaseTest.AddSelectedShop(Integer.parseInt(eachRegisteredStoreInfo[shopId]));
            simpleDatabaseTest.AddStoreAndCustomerUniqueId(Integer.parseInt(eachRegisteredStoreInfo[shopId]), Integer.parseInt(eachRegisteredStoreInfo[uniqueId]));
        }
    }

    public int GetStoreUniqueId(int targetStoreId) {
        return simpleDatabaseTest.GetSelectedUniqueId(targetStoreId);
    }
}