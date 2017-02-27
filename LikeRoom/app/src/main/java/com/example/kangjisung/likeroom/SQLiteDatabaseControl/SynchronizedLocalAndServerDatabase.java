package com.example.kangjisung.likeroom.SQLiteDatabaseControl;

import android.content.Context;

import com.example.kangjisung.likeroom.NetworkManager.NetworkModule;

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
}
