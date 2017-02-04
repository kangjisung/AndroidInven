package com.example.kangjisung.likeroom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;

import com.example.kangjisung.likeroom.CommunicationManager.NetWorkOrderProcessManager;
import com.example.kangjisung.likeroom.FragmentProduct.ProductListItem;
import com.example.kangjisung.likeroom.FragmentProduct.ProductObjManager;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.DatabaseHelper;
import com.example.kangjisung.likeroom.Util.ColorTheme;

import java.util.Date;

public class MainActivity extends AppCompatActivity
{
    static DatabaseHelper databaseHelperTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setTheme(ColorTheme.getTheme());
        setContentView(R.layout.activity_main);
        databaseHelperTest = new DatabaseHelper(getApplicationContext(), ClientDataBase.testDatabaseName);

        NetWorkOrderProcessManager netWorkOrderProcessManager = new NetWorkOrderProcessManager();
        netWorkOrderProcessManager.LoadAllStoreInfo();
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run(){
                CheckTypesTask task = new CheckTypesTask();
                task.execute();
            }
        }, 500);
    }

    // 로딩창 구현
    private class CheckTypesTask extends AsyncTask<Void, Void, Void>
    {
        ProgressDialog asyncDialog = new ProgressDialog(MainActivity.this);

        @Override
        protected void onPreExecute() {
            asyncDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            asyncDialog.setMessage("로딩중입니다..");

            // show dialog
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            //////////////////////////////////////////요기서, 제품 정보 배열 추가.
            ProductObjManager.add(new ProductListItem("소보로빵", true, new Date(2011,10,20), new Date(2000,2,3), new Date(2010,12,30), 100, 200));
            ProductObjManager.add(new ProductListItem("바게트빵", false, new Date(2012,9,20), new Date(2050,2,3), new Date(2015,12,30), 100, 200));
            ProductObjManager.add(new ProductListItem("맛있는빵", false, new Date(2012,9,20), new Date(2050,2,3), new Date(2015,12,30), 100, 200));
            ProductObjManager.add(new ProductListItem("크림빵", false, new Date(2012,10,20), new Date(2020,2,3), new Date(2002,12,30), 100, 200));
            ProductObjManager.add(new ProductListItem("케익", false, new Date(2013,10,20), new Date(2030,2,3), new Date(2004,12,30), 100, 200));
            ProductObjManager.add(new ProductListItem("우유", true, new Date(2014,10,20), new Date(2040,2,3), new Date(2020,12,30), 100, 200));
            ProductObjManager.add(new ProductListItem("사탕", false, new Date(2012,9,20), new Date(2050,2,3), new Date(2015,12,30), 100, 200));
            ProductObjManager.context=getApplicationContext();

            SystemClock.sleep(500);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            asyncDialog.dismiss();
            super.onPostExecute(result);
            Intent intent = new Intent(getApplicationContext(), ActivityMenu.class);
            startActivity(intent);

            finish();
        }
    }
}