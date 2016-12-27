package com.example.kangjisung.likeroom.PermissionManager;

import android.Manifest;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.kangjisung.likeroom.R;

import static android.content.Context.ACCOUNT_SERVICE;

/**
 * Created by stories2 on 2016. 12. 27..
 */

public class UserAccountCrawler {
    Activity applicationActivity;
    final int PERMISSIONS_REQUEST_GET_ACCOUNTS = 0;

    public UserAccountCrawler(Activity applicationActivity) {
        this.applicationActivity = applicationActivity;
    }

    /*String GetEmail() {
        accountManager = AccountManager.get(context);
        Account account = getAccount(accountManager);

        if (account == null) {
            return null;
        } else {
            return account.name;
        }
    }

    private static Account getAccount(AccountManager accountManager) {
        Account[] accounts = accountManager.getAccountsByType("com.google");
        Account account;
        if (accounts.length > 0) {
            account = accounts[0];
        } else {
            account = null;
        }
        return account;
    }*/

    public String CheckPermissionGranted() {
        Account[] list = null;
        if(new AndroidVersionController().GetAndroidBuildSDKVersion() > 22) {

            if (ContextCompat.checkSelfPermission(applicationActivity.getApplicationContext(), Manifest.permission.GET_ACCOUNTS) != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?
                if (ActivityCompat.shouldShowRequestPermissionRationale(applicationActivity, Manifest.permission.GET_ACCOUNTS)) {

                } else {

                    // No explanation needed, we can request the permission.

                    ActivityCompat.requestPermissions(applicationActivity, new String[]{Manifest.permission.GET_ACCOUNTS}, PERMISSIONS_REQUEST_GET_ACCOUNTS);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            }
            else {
                AccountManager manager = (AccountManager) applicationActivity.getSystemService(ACCOUNT_SERVICE);
                list = manager.getAccounts();

                return GetUserGmailAccount(list);
            }
        }
        else {
            AccountManager manager = (AccountManager) applicationActivity.getSystemService(ACCOUNT_SERVICE);
            list = manager.getAccounts();
            return GetUserGmailAccount(list);
        }
        return null;
    }

    public String GetUserGmailAccount(Account[] accountList) {
        for (Account account : accountList) {
            if (account.type.equals("com.google")) {
                Log.d(applicationActivity.getString(R.string.app_name), "account = " + account.name);
                return account.name;
            }
        }
        return null;
    }
}
