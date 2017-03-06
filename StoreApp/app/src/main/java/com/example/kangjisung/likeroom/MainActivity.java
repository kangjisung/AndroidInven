package com.example.kangjisung.likeroom;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.example.kangjisung.likeroom.FragmentProduct.ListView.ProductMuchStoreListItem;
import com.example.kangjisung.likeroom.FragmentProduct.ListView.ProductSellTodayListItem;
import com.example.kangjisung.likeroom.FragmentProduct.ProductObjManager;
import com.example.kangjisung.likeroom.NetworkManager.NetworkModule;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase;
import com.example.kangjisung.likeroom.SQLiteDatabaseControl.DatabaseHelper;
import com.example.kangjisung.likeroom.Util.ColorTheme;

import java.util.Comparator;
import java.util.Date;

import static com.example.kangjisung.likeroom.SQLiteDatabaseControl.ClientDataBase.DBstring;

public class MainActivity extends AppCompatActivity {
    static DatabaseHelper databaseHelperTest;
    public static Context con;
    public static String PriNum;

    //networkmodule 예제
    //networkModule.InsertNewCustomerInfo("강지성");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ColorTheme.initTheme(this);
        this.setTheme(ColorTheme.getTheme());
        setContentView(R.layout.activity_main);
        con = getApplicationContext();
        databaseHelperTest = new DatabaseHelper(getApplicationContext(), ClientDataBase.testDatabaseName);


        /*
        NetWorkOrderProcessManager netWorkOrderProcessManager = new NetWorkOrderProcessManager();
        netWorkOrderProcessManager.LoadAllStoreInfo();
        */

        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run(){
                MainActivity.CheckTypesTask task = new MainActivity.CheckTypesTask();
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

            NetworkModule networkModule=new NetworkModule();
            //networkModule.InsertNewStoreInfoData("서울시","강지성","01012345678","2017-02-25");

            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            Date date=new Date();
            ProductObjManager.getContext(getApplicationContext());
            ProductObjManager.productLoad(date);

            //SystemClock.sleep(500);

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            asyncDialog.dismiss();
            super.onPostExecute(result);
            //////매장이 클라이언트 디비에 있는지 검사
            new ClientDataBase("select `매장번호` from `매장`;",1,1,getApplicationContext());
            int cnt=0;
            while(true) {
                if (DBstring[cnt] != null) {
                    PriNum=DBstring[cnt];
                    cnt++;
                }
                else if(DBstring[cnt]==null) break;
            }
            if(PriNum==null) {
                startActivity(new Intent(getApplicationContext(), ActivityStoreAdd.class));
            }
            else {
                // show dialog

                startActivity(new Intent(getApplicationContext(),ActivityMenu.class));
            }
            finish();
        }
    }

    static class SellNameAscCompare implements Comparator<ProductSellTodayListItem> {
        @Override
        public int compare(ProductSellTodayListItem arg0, ProductSellTodayListItem arg1) {
            // TODO Auto-generated method stub
            return arg0.getName().compareTo(arg1.getName());
        }
    }

    static class MuchNameAscCompare implements Comparator<ProductMuchStoreListItem> {
        @Override
        public int compare(ProductMuchStoreListItem arg0, ProductMuchStoreListItem arg1) {
            // TODO Auto-generated method stub
            return arg0.getName().compareTo(arg1.getName());
        }
    }
}