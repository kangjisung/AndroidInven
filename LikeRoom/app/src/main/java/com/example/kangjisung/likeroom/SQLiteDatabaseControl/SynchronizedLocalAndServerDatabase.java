package com.example.kangjisung.likeroom.SQLiteDatabaseControl;

import android.content.Context;

import com.example.kangjisung.likeroom.NetworkManager.NetworkModule;

import java.util.ArrayList;

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
        return networkModule.GetMileageSum(1);
    }

    public void UseMileageFromTargetStore(int uniqueRegisteredId, int mileageSize) {
        networkModule.InsertMileageLog(uniqueRegisteredId, mileageSize);
    }

    public void GetStoreNoticeFromServer(String shopId) {
        simpleDatabaseTest.SaveStoreNoticeData(networkModule.ShowTargetStoreNoticeList(shopId));
    }
}