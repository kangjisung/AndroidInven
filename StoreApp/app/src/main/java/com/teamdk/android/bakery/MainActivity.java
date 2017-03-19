package com.teamdk.android.bakery;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.teamdk.android.bakery.objectmanager.ProductMuchStoreListItem;
import com.teamdk.android.bakery.objectmanager.ProductObjectManager;
import com.teamdk.android.bakery.objectmanager.ProductSellTodayListItem;
import com.teamdk.android.bakery.objectmanager.MemberObjectManager;
import com.teamdk.android.bakery.objectmanager.NoticeObjectManager;
import com.teamdk.android.bakery.utility.NetworkManager.NetworkModule;
import com.teamdk.android.bakery.utility.SQLiteDatabaseControl.ClientDataBase;
import com.teamdk.android.bakery.utility.SQLiteDatabaseControl.DatabaseHelper;
import com.teamdk.android.bakery.utility.ColorTheme;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;

import static com.teamdk.android.bakery.utility.SQLiteDatabaseControl.ClientDataBase.DBstring;

public class MainActivity extends AppCompatActivity {
    static DatabaseHelper databaseHelperTest;
    public static Context con;
    public static String PriNum;

    ArrayList<ProductSellTodayListItem> sellTodayArrayList = new ArrayList<>();
    ArrayList<ProductMuchStoreListItem> muchStoreArrayList = new ArrayList<>();
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

            // show dialog
            asyncDialog.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            Date date = new Date();

            ProductObjectManager.getContext(getApplicationContext());
            ProductObjectManager.productLoad(date);

            new MemberObjectManager();
            MemberObjectManager.load(getApplicationContext());

            new NoticeObjectManager();
            NoticeObjectManager.load(getApplicationContext());

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
                new ClientDataBase("select max(`수정일`) from `회원정보`;",1,1,getApplicationContext());
                NetworkModule networkModule = new NetworkModule();
                networkModule.GetCustomerRegisteredInfo(Integer.parseInt(PriNum), DBstring[0]);
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